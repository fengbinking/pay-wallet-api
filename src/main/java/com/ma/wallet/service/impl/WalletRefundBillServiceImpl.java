package com.ma.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.ma.wallet.core.service.AbstractService;
import com.ma.wallet.core.utils.ResultGenerator;
import com.ma.wallet.core.utils.SignUtils;
import com.ma.wallet.core.vo.Result;
import com.ma.wallet.dao.WalletRefundBillMapper;
import com.ma.wallet.enums.*;
import com.ma.wallet.model.OrderIdOrNumber;
import com.ma.wallet.model.WalletAccount;
import com.ma.wallet.model.WalletPayBill;
import com.ma.wallet.model.WalletRefundBill;
import com.ma.wallet.service.*;
import com.ma.wallet.vo.WalletRefundVo;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * Created by FengBin on 2017-08-15.
 */
@Log4j
@Service
public class WalletRefundBillServiceImpl extends AbstractService<WalletRefundBill> implements WalletRefundBillService {
    @Resource
    private WalletRefundBillMapper walletRefundBillMapper;
    @Autowired
    private WalletPayBillService walletPayBillService;
    @Autowired
    private GetOrderIdOrNumberService getOrderIdOrNumberService;
    @Autowired
    private WalletAccountService walletAccountService;
    @Autowired
    private WalletAccountChangeService walletAccountChangeService;
    @Value("${pay.wallet.request.key}")
    private String requestKey;
    @Transactional
    @Override
    public Result refund(WalletRefundVo walletRefundVo) {
        boolean isRefundSuccess = false;
        OrderIdOrNumber refundIdOrNumber=null;
        //检查支付流水
        WalletPayBill walletPayBill=walletPayBillService.findBy("payNo",walletRefundVo.getPayNo());
        if (walletPayBill!=null&&walletPayBill.getOrderNo().equals(walletRefundVo.getOrderNo())
                &&walletPayBill.getStatus()== PayStatus.SUCCESS.getValue()) {
            //查看是否已经退过款
            WalletRefundBill walletRefundBill = new WalletRefundBill();
            walletRefundBill.setRefundNote("人工审核，于"
                    + new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date())
                    + "退款");
            Example example = new Example(WalletRefundBill.class);
            example.createCriteria().andEqualTo("payNo", walletRefundVo.getPayNo())
                    .andEqualTo("orderNo",walletRefundVo.getOrderNo());
            int ret = walletRefundBillMapper.updateByExampleSelective(walletRefundBill, example);
            if (ret == 0) {
                /**
                 * 1.增加退款流水
                 * 2.修改用户钱包余额
                 * 3.增加账户资金变动流水
                 */
                //获取退款流水号
                refundIdOrNumber = getOrderIdOrNumberService.getOrderIdOrNumber(TradType.REFUND, 14);
                /**保存退款流水记录，若成功则继续下面的业务*/
                if (this.saveWalletRefundBill(walletRefundVo, refundIdOrNumber) == 1) {
                    /**
                     * 修改用户钱包余额
                     * 总金额＝可用金额＋不可用金额
                     * 可用金额＝可提现金额＋不可提现金额
                     * 不可用金额＝可提现冻结金额＋不可提现冻结金额
                     **/
                    WalletAccount walletAccount = new WalletAccount();
                    walletAccount.setBuyerId(walletRefundVo.getBuyerId());
                    //不可提现金额
                    walletAccount.setUncashAmount(walletRefundVo.getUncashAmount());
                    log.info("修改用户钱包余额，参数-WalletAccount=>" + JSON.toJSONString(walletAccount));
                    int walletAccountRet = walletAccountService.updateAccountBalance(walletAccount);
                    if (walletAccountRet == 1) {//用户有用过钱包支付
                        walletAccount = walletAccountService.findBy("buyerId", walletAccount.getBuyerId());
                        //插入资金变动流水
                        int accontChangeRet = walletAccountChangeService.saveWalletAccountChange(walletAccount,
                                walletRefundVo.getUncashAmount(), refundIdOrNumber.getId());
                        if (accontChangeRet == 1) {
                            //更新退款状态
                            walletRefundBill = new WalletRefundBill();
                            walletRefundBill.setStatus((short)RefundStatus.SUCCESS.getValue());
                            example = new Example(WalletRefundBill.class);
                            example.createCriteria().andEqualTo("id",refundIdOrNumber.getId())
                                    .andEqualTo("refundNo",refundIdOrNumber.getOrderNo())
                                    .andEqualTo("payNo", walletRefundVo.getPayNo())
                                    .andEqualTo("orderNo",walletRefundVo.getOrderNo())
                                    .andNotEqualTo("status",RefundStatus.SUCCESS.getValue());
                            if (walletRefundBillMapper.updateByExampleSelective(walletRefundBill, example)==1)
                                isRefundSuccess = true;
                            else
                                this.throwException("更新退款流水状态失败！");
                        } else {
                            this.throwException("插入资金变动流水失败！");
                        }
                    } else {
                        this.throwException("更新用户余额异常，退款失败！");
                    }
                }else {
                    this.throwException("增加退款流水失败！");
                }
            }else {
                this.throwException("该订单已退款，不能重复操作！");
            }
        }else {
            this.throwException("找不到对应支付单，退款失败！");
        }
        if(isRefundSuccess){
            walletRefundVo.setNonce_str(UUID.randomUUID().toString().replaceAll("-",""));
            walletRefundVo.setRefundNo(refundIdOrNumber.getOrderNo());
            //签名
            try {
                walletRefundVo.setSign(SignUtils.getSign(walletRefundVo,requestKey));
            } catch (Exception e) {
                this.throwException("签名失败！");
            }
            walletRefundVo.setStatus((short) RechargeStatus.SUCCESS.getValue());
            return ResultGenerator.genSuccessResult(walletRefundVo);
        }else {
            this.throwException("退款失败！");
            return null;
        }
    }

    /**
     * 保存退款流水记录
     * @param walletRefundVo
     * @param rechargeIdOrNumber
     * @return
     */
    private int saveWalletRefundBill(WalletRefundVo walletRefundVo, OrderIdOrNumber rechargeIdOrNumber){
        WalletRefundBill walletRefundBill=new WalletRefundBill();
        walletRefundBill.setId(rechargeIdOrNumber.getId());
        //退款交易流水号
        walletRefundBill.setRefundNo(rechargeIdOrNumber.getOrderNo());
        //销售订单号（来源电商平台）
        walletRefundBill.setOrderNo(walletRefundVo.getOrderNo());
        //用户id
        walletRefundBill.setBuyerId(walletRefundVo.getBuyerId());
        //原支付流水号（wallet_pay_bill表支付流水号）
        walletRefundBill.setPayNo(walletRefundVo.getPayNo());
        //退款类型（1.人工审核退款 2.系统自动退款）
        walletRefundBill.setRefundType((short)1);
        //退款状态（0.退款申请 1.退款拒绝 3.退款处理中 4.退款成功 5.退款失败）
        walletRefundBill.setStatus((short)RefundStatus.IN_HAND.getValue());
        //退款到不可提现金额（目前只支持这一种）
        walletRefundBill.setUncashAmount(walletRefundVo.getUncashAmount());
        //订单退款备注
        if(StringUtils.isNotEmpty(walletRefundVo.getOrderNote()))
            walletRefundBill.setOrderNote(walletRefundVo.getOrderNote());
        //退款说明
        if(StringUtils.isNotEmpty(walletRefundVo.getRefundNote()))
            walletRefundBill.setRefundNote(walletRefundVo.getRefundNote());
        //接口调用方ip
        walletRefundBill.setIp(walletRefundVo.getIp());
        Date dateTime=new Date();
        walletRefundBill.setCreateTime(dateTime);
        walletRefundBill.setUpdateTime(dateTime);
        log.info("增加退款流水记录，参数-WalletRefundBill=>"+ JSON.toJSONString(walletRefundBill));
        return this.save(walletRefundBill);
    }

}
