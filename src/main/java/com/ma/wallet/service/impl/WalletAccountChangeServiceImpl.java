package com.ma.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.ma.wallet.core.service.AbstractService;
import com.ma.wallet.dao.WalletAccountChangeMapper;
import com.ma.wallet.enums.TradType;
import com.ma.wallet.model.WalletAccount;
import com.ma.wallet.model.WalletAccountChange;
import com.ma.wallet.service.WalletAccountChangeService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by FengBin on 2017-08-15.
 */
@Log4j
@Service
@Transactional
public class WalletAccountChangeServiceImpl extends AbstractService<WalletAccountChange> implements WalletAccountChangeService {
    @Resource
    private WalletAccountChangeMapper walletAccountChangeMapper;

    @Override
    /**
     * 保存资金变动流水
     * @param walletAccount
     * @param rechargeAmount 充值金额
     * @param refId 关联流水ID
     * @return
     */
    public int saveWalletAccountChange(WalletAccount walletAccount, BigDecimal rechargeAmount, Long refId){
        WalletAccountChange walletAccountChange=new WalletAccountChange();
        //账户表主键ID
        walletAccountChange.setAccountId(walletAccount.getId());
        walletAccountChange.setBuyerId(walletAccount.getBuyerId());
        //类型(1.充值 2.支付 3.退款 4.提现 5.内部调账)
        walletAccountChange.setChangeType((short) (TradType.RECHARGE.getValue()));
        //变动前总金额
        walletAccountChange.setPreAmount(walletAccount.getTotalAmount().subtract(rechargeAmount));
        //变动后总金额
        walletAccountChange.setAmount(walletAccount.getTotalAmount());
        //不可提发生金额
        walletAccountChange.setUncashAmount(rechargeAmount);
        //关联流水ID(change_type不同，对应不同流水表，如充值、支付、提现流水)
        walletAccountChange.setRefId(refId);
        walletAccountChange.setCreateTime(new Date());
        log.info("增加资金变动流水，参数-WalletAccountChange=>"+ JSON.toJSONString(walletAccountChange));
        return this.save(walletAccountChange);
    }
}
