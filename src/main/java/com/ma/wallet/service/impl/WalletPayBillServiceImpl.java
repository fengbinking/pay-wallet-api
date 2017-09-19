package com.ma.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.ma.wallet.core.service.AbstractService;
import com.ma.wallet.core.utils.ResultGenerator;
import com.ma.wallet.core.utils.SignUtils;
import com.ma.wallet.core.vo.Result;
import com.ma.wallet.enums.PayStatus;
import com.ma.wallet.enums.PayType;
import com.ma.wallet.enums.TradType;
import com.ma.wallet.model.OrderIdOrNumber;
import com.ma.wallet.model.WalletAccount;
import com.ma.wallet.model.WalletPayBill;
import com.ma.wallet.service.GetOrderIdOrNumberService;
import com.ma.wallet.service.WalletAccountChangeService;
import com.ma.wallet.service.WalletAccountService;
import com.ma.wallet.service.WalletPayBillService;
import com.ma.wallet.vo.WalletPayVo;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;


/**
 * 钱包支付业务类
 * Created by FengBin on 2017-08-15.
 */
@Log4j
@Service
public class WalletPayBillServiceImpl extends AbstractService<WalletPayBill> implements WalletPayBillService {
    @Autowired
    private GetOrderIdOrNumberService getOrderIdOrNumberService;
    @Autowired
    private WalletAccountChangeService walletAccountChangeService;
    @Autowired
    private WalletAccountService walletAccountService;
    @Value("${pay.wallet.request.key}")
    private String requestKey;
    @Transactional
    @Override
    public Result pay(WalletPayVo walletPayVo) {
        /**
         * 1.增加支付交易流水
         * 2.判断余额是否足够
         * 3.增加账户资金变动流水
         */
        boolean isPaySuccess=false;
        //获取支付交易流水号
        OrderIdOrNumber payIdOrNumber=getOrderIdOrNumberService.getOrderIdOrNumber(TradType.PAY,14);
        //插入支付流水
        walletPayVo.setStatus((short)PayStatus.IN_PAY.getValue());
        if(this.saveWalletPayBill(walletPayVo,payIdOrNumber)==1) {
            /**
             * 修改用户钱包余额
             * 总金额＝可用金额＋不可用金额
             * 可用金额＝可提现金额＋不可提现金额
             * 不可用金额＝可提现冻结金额＋不可提现冻结金额
             **/
            WalletAccount walletAccount = new WalletAccount();
            walletAccount.setBuyerId(walletPayVo.getBuyerId());
            //账号类型(1.ios 2.android 3.小程序-卡奴 4.小程序-01men)
            walletAccount.setAccountType((short) walletPayVo.getPlatform());
            //不可提现金额
            walletAccount.setUncashAmount(walletPayVo.getUncashAmount().negate());//支付取负数
            log.info("判断钱包余额是否足够，参数-WalletAccount=>" + JSON.toJSONString(walletAccount));
            int walletAccountRet = walletAccountService.updateAccountBalance(walletAccount);
            if (walletAccountRet == 1) {//余额足够
                //查询资金流水记录所需要字段
                walletAccount = walletAccountService.findBy("buyerId", walletAccount.getBuyerId());
                //插入资金变动流水
                int accontChangeRet = walletAccountChangeService.saveWalletAccountChange(walletAccount,
                        walletPayVo.getUncashAmount(), payIdOrNumber.getId());
                if (accontChangeRet == 1) {
                    //更改支付流水状态
                    WalletPayBill walletPayBill=new WalletPayBill();
                    walletPayBill.setId(payIdOrNumber.getId());
                    walletPayBill.setStatus((short)PayStatus.SUCCESS.getValue());
                    if(this.update(walletPayBill)==1)
                        isPaySuccess = true;
                    else
                        this.throwException("更改支付流水状态失败！");
                } else {
                    this.throwException("插入资金变动流水失败！");
                }
            } else {//余额不足
                this.throwException("账户余额不足，支付失败！");
            }
        }else {
            this.throwException("插入支付流水记录失败！");
        }
        if(isPaySuccess){
            walletPayVo.setNonce_str(UUID.randomUUID().toString().replaceAll("-",""));
            walletPayVo.setPayNo(payIdOrNumber.getOrderNo());
            //签名
            try {
                walletPayVo.setSign(SignUtils.getSign(walletPayVo,requestKey));
            } catch (Exception e) {
                this.throwException("签名失败！");
            }
            walletPayVo.setStatus((short) PayStatus.SUCCESS.getValue());
            return ResultGenerator.genSuccessResult(walletPayVo);
        }else {
            this.throwException("支付失败！");
            return null;
        }

    }
    /**
     * 保存支付流水记录
     * @param walletPayVo
     * @param payIdOrNumber
     * @return
     */
    private int saveWalletPayBill(WalletPayVo walletPayVo,OrderIdOrNumber payIdOrNumber){
        WalletPayBill walletPayBill=new WalletPayBill();
        walletPayBill.setId(payIdOrNumber.getId());
        //支付流水号（钱包支付网关生成）
        walletPayBill.setPayNo(payIdOrNumber.getOrderNo());
        //用户id
        walletPayBill.setBuyerId(walletPayVo.getBuyerId());
        //销售订单号（来源电商平台）
        walletPayBill.setOrderNo(walletPayVo.getOrderNo());
        //电商平台（1.ios 2.android 3.小程序-卡奴 4.小程序-01men）
        walletPayBill.setPlatform(walletPayVo.getPlatform());
        //支付状态（0.待支付 1.支付中 2.成功 3.失败）
        walletPayBill.setStatus(walletPayVo.getStatus());
        //支付类型（0.钱包支付 1.钱包+微信组合支付）
        short payType=walletPayVo.getPayType();
        walletPayBill.setPayType(payType);
        if (payType== PayType.COMBINE_PAY.getValue()){
            //校验字段数据
            if(StringUtils.isEmpty(walletPayVo.getTradeNo())){
                this.throwException("第三方交易流水为空，钱包支付失败！");
            }
            if (walletPayVo.getPayId()==null||walletPayVo.getPayId()==0){
                this.throwException("支付网关流水ID为空，钱包支付失败！");
            }
            walletPayBill.setTradeNo(walletPayVo.getTradeNo());
            walletPayBill.setPayId(walletPayVo.getPayId());
        }
        //不可提现金额,默认这种方式，暂不支持提现
        walletPayBill.setUncashAmount(walletPayVo.getUncashAmount());
        walletPayBill.setIp(walletPayVo.getIp());
        Date dateTime=new Date();
        walletPayBill.setCreateTime(dateTime);
        walletPayBill.setUpdateTime(dateTime);
        log.info("增加支付流水记录，参数-WalletPayBill=>"+ JSON.toJSONString(walletPayBill));
        return this.save(walletPayBill);
    }
}
