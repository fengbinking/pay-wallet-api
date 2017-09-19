package com.ma.wallet.service.impl;

import com.ma.wallet.core.service.AbstractService;
import com.ma.wallet.core.utils.SignUtils;
import com.ma.wallet.dao.WalletAccountMapper;
import com.ma.wallet.enums.AccountStatus;
import com.ma.wallet.model.WalletAccount;
import com.ma.wallet.service.WalletAccountService;
import com.ma.wallet.vo.WalletAccountVo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


/**
 * Created by FengBin on 2017-08-15.
 */
@Log4j
@Service
@Transactional
public class WalletAccountServiceImpl extends AbstractService<WalletAccount> implements WalletAccountService {
    @Resource
    private WalletAccountMapper walletAccountMapper;
    @Value("${pay.wallet.request.key}")
    private String requestKey;

    @Override
    public int updateAccountBalance(WalletAccount walletAccount) {
        return walletAccountMapper.updateAccountBalance(walletAccount);
    }

    @Override
    public WalletAccountVo getUserBalance(long buyerId) {
        WalletAccountVo walletAccountVo=new WalletAccountVo();
        Condition condition=new Condition(WalletAccount.class);
        condition.createCriteria().andEqualTo("buyerId",buyerId)
                .andEqualTo("status", AccountStatus.USABLE.getValue());
        List<WalletAccount> walletAccountList=this.findByCondition(condition);
        if (walletAccountList==null||walletAccountList.size()==0){
            walletAccountVo.setBuyerId(buyerId);
            walletAccountVo.setTotalAmount(BigDecimal.ZERO);
            log.info("用户["+buyerId+"]找不到对应余额记录!");
        }else{
            WalletAccount walletAccount=walletAccountList.get(0);
            walletAccountVo.setBuyerId(walletAccount.getBuyerId());
            walletAccountVo.setAccountType(walletAccount.getAccountType());
            walletAccountVo.setCashAmount(walletAccount.getCashAmount());
            walletAccountVo.setTotalAmount(walletAccount.getTotalAmount());
            walletAccountVo.setFreezeCashAmount(walletAccount.getFreezeCashAmount());
            walletAccountVo.setFreezeUncashAmount(walletAccount.getFreezeUncashAmount());
        }
        walletAccountVo.setNonce_str(UUID.randomUUID().toString().replaceAll("-",""));
        //签名
        try {
            walletAccountVo.setSign(SignUtils.getSign(walletAccountVo,requestKey));
        } catch (Exception e) {
            this.throwException("签名失败！");
        }
        return walletAccountVo;
    }
}
