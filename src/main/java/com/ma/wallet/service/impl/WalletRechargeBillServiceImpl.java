package com.ma.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.ma.wallet.core.service.AbstractService;
import com.ma.wallet.core.utils.ResultGenerator;
import com.ma.wallet.core.utils.SignUtils;
import com.ma.wallet.core.vo.Result;
import com.ma.wallet.dao.WalletRechargeBillMapper;
import com.ma.wallet.enums.AccountStatus;
import com.ma.wallet.enums.RechargeStatus;
import com.ma.wallet.enums.TradType;
import com.ma.wallet.model.OrderIdOrNumber;
import com.ma.wallet.model.WalletAccount;
import com.ma.wallet.model.WalletRechargeBill;
import com.ma.wallet.service.GetOrderIdOrNumberService;
import com.ma.wallet.service.WalletAccountChangeService;
import com.ma.wallet.service.WalletAccountService;
import com.ma.wallet.service.WalletRechargeBillService;
import com.ma.wallet.vo.WalletRechargeVo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;


/**
 * 钱包充值
 * Created by FengBin on 2017-08-15.
 */
@Log4j
@Service
public class WalletRechargeBillServiceImpl extends AbstractService<WalletRechargeBill> implements WalletRechargeBillService {
    @Resource
    private WalletRechargeBillMapper walletRechargeBillMapper;
    @Autowired
    private WalletAccountChangeService walletAccountChangeService;
    @Autowired
    private WalletAccountService walletAccountService;
    @Autowired
    private GetOrderIdOrNumberService getOrderIdOrNumberService;
    @Value("${pay.wallet.request.key}")
    private String requestKey;
    @Transactional
    @Override
    public Result recharge(WalletRechargeVo walletRechargeVo) {
        /**
         * 1.增加充值交易流水
         * 2.修改用户钱包余额
         * 3.增加账户资金变动流水
         */
        boolean isRechargeSuccess=false;
        //获取充值流水号
        OrderIdOrNumber rechargeIdOrNumber=getOrderIdOrNumberService.getOrderIdOrNumber(TradType.RECHARGE,14);
        /**保存充值流水记录，若成功则继续下面的业务*/
        if(this.saveWalletRechargeBill(walletRechargeVo,rechargeIdOrNumber)==1){
           /**
            * 修改用户钱包余额
            * 总金额＝可用金额＋不可用金额
            * 可用金额＝可提现金额＋不可提现金额
            * 不可用金额＝可提现冻结金额＋不可提现冻结金额
            **/
            WalletAccount walletAccount=new WalletAccount();
            walletAccount.setBuyerId(walletRechargeVo.getBuyerId());
            //账号类型(1.ios 2.android 3.小程序-卡奴 4.小程序-01men)
            walletAccount.setAccountType((short)walletRechargeVo.getPlatform());
            //不可提现金额
            walletAccount.setUncashAmount(walletRechargeVo.getAmount());
            log.info("修改用户钱包余额，参数-WalletAccount=>"+ JSON.toJSONString(walletAccount));
            int walletAccountRet=walletAccountService.updateAccountBalance(walletAccount);
            if (walletAccountRet==1){//用户不是第一次充值
                walletAccount=walletAccountService.findBy("buyerId",walletAccount.getBuyerId());
                //插入资金变动流水
                int accontChangeRet=walletAccountChangeService.saveWalletAccountChange(walletAccount,
                        walletRechargeVo.getAmount(),rechargeIdOrNumber.getId());
                if (accontChangeRet==1){
                    isRechargeSuccess=true;
                }else {
                    this.throwException("插入资金变动流水失败！");
                }
            }else{//第一次充值，需增加账户记录
                //总金额
                walletAccount.setTotalAmount(walletRechargeVo.getAmount());
                walletAccount.setStatus((short)AccountStatus.USABLE.getValue());
                walletAccount.setCreateTime(new Date());
                walletAccount.setUpdateTime(new Date());
                log.info("增加账户记录，参数-WalletAccount=>"+ JSON.toJSONString(walletAccount));
                //钱包余额更新成功，同步增加资金变动流水
                if(walletAccountService.save(walletAccount)==1){
                    //插入资金变动流水
                    int accontChangeRet=walletAccountChangeService.saveWalletAccountChange(walletAccount,
                            walletRechargeVo.getAmount(),rechargeIdOrNumber.getId());
                    if (accontChangeRet==1){
                        isRechargeSuccess=true;
                    }else {
                        this.throwException("插入资金变动流水失败！");
                    }
                }else {
                    this.throwException("增加账户记录失败！");
                }
            }
        }else {
            this.throwException("增加充值交易流水失败！");
        }
        if(isRechargeSuccess){
            walletRechargeVo.setNonce_str(UUID.randomUUID().toString().replaceAll("-",""));
            walletRechargeVo.setRechargeNo(rechargeIdOrNumber.getOrderNo());
            //签名
            try {
                walletRechargeVo.setSign(SignUtils.getSign(walletRechargeVo,requestKey));
            } catch (Exception e) {
                this.throwException("签名失败！");
            }
            walletRechargeVo.setStatus((short)RechargeStatus.SUCCESS.getValue());
            return ResultGenerator.genSuccessResult(walletRechargeVo);
        }else {
            this.throwException("充值失败！");
            return null;
        }
    }

    /**
     * 保存充值流水记录
     * @param walletRechargeVo
     * @param rechargeIdOrNumber
     * @return
     */
    private int saveWalletRechargeBill(WalletRechargeVo walletRechargeVo,OrderIdOrNumber rechargeIdOrNumber){
        WalletRechargeBill walletRechargeBill=new WalletRechargeBill();
        walletRechargeBill.setId(rechargeIdOrNumber.getId());
        //充值交易流水号
        walletRechargeBill.setRechargeNo(rechargeIdOrNumber.getOrderNo());
        //销售订单号（来源电商平台）
        walletRechargeBill.setOrderNo(walletRechargeVo.getOrderNo());
        //用户id
        walletRechargeBill.setBuyerId(walletRechargeVo.getBuyerId());
        //支付网关流水表主键ID
        walletRechargeBill.setPayId(walletRechargeVo.getPayId());
        //充值金额
        walletRechargeBill.setAmount(walletRechargeVo.getAmount());
        //充值平台（1.ios 2.android 3.小程序-卡奴 4.小程序-01men）
        walletRechargeBill.setPlatform(walletRechargeVo.getPlatform());
        //充值渠道(1.微信 2.支付宝)
        walletRechargeBill.setRechargeChannel(walletRechargeVo.getRechargeChannel());
        walletRechargeBill.setIp(walletRechargeVo.getIp());
        //第三方交易流水号（如微信、支付宝支付流水号）
        walletRechargeBill.setTradeNo(walletRechargeVo.getTradeNo());
        //充值状态（0.待充值 1.充值中 2.成功 3.失败）
        walletRechargeBill.setStatus((short)RechargeStatus.SUCCESS.getValue());
        walletRechargeBill.setIp(walletRechargeVo.getIp());
        Date dateTime=new Date();
        walletRechargeBill.setCreateTime(dateTime);
        walletRechargeBill.setUpdateTime(dateTime);
        log.info("增加充值流水记录，参数-WalletRechargeBill=>"+ JSON.toJSONString(walletRechargeBill));
        return walletRechargeBillMapper.insert(walletRechargeBill);
    }
}
