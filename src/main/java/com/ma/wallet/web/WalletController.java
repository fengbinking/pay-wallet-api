package com.ma.wallet.web;

import com.ma.wallet.core.constant.ResultCode;
import com.ma.wallet.core.utils.ResultGenerator;
import com.ma.wallet.core.vo.Result;
import com.ma.wallet.service.WalletAccountService;
import com.ma.wallet.service.WalletPayBillService;
import com.ma.wallet.service.WalletRechargeBillService;
import com.ma.wallet.service.WalletRefundBillService;
import com.ma.wallet.vo.WalletPayVo;
import com.ma.wallet.vo.WalletRechargeVo;
import com.ma.wallet.vo.WalletRefundVo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by FengBin on 2017-08-15.
 */
@Log4j
@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Resource
    private WalletAccountService walletAccountService;
    @Autowired
    private WalletRechargeBillService walletRechargeBillService;
    @Autowired
    private WalletPayBillService walletPayBillService;
    @Autowired
    private WalletRefundBillService walletRefundBillService;

    /**
     * 钱包充值
     *
     * @param walletRechargeVo
     * @return
     */
    @PostMapping("/recharge")
    public Result recharge(@Valid WalletRechargeVo walletRechargeVo,BindingResult errorRet, HttpServletRequest request) {
        log.info("*********["+Thread.currentThread().getId()
                +"]*******钱包充值接口调起，参数-WalletRechargeVo=>"+walletRechargeVo);
        if (errorRet.hasErrors()) {
            List<ObjectError> list = errorRet.getAllErrors();
            for (ObjectError error : list) {
                Result result=new Result();
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                result.setMessage(error.getDefaultMessage());
                return result;
            }
        }
        walletRechargeVo.setIp((String) request.getAttribute("ip"));
        Result ret=walletRechargeBillService.recharge(walletRechargeVo);
        log.info("<<<<<<<<<<<["+Thread.currentThread().getId()
                +"]<<<<<<钱包充值接口执行完成，响应-WalletRechargeVo=>"+walletRechargeVo);
        return ResultGenerator.genSuccessResult(ret);
    }

    /**
     * 钱包支付
     * @param walletPayVo
     * @param errorRet
     * @param request
     * @return
     */
    @PostMapping("/pay")
    public Result pay(@Valid WalletPayVo walletPayVo,BindingResult errorRet,HttpServletRequest request) {
        log.info("*********["+Thread.currentThread().getId()
                +"]*******钱包支付接口调起，参数-WalletPayVo=>"+walletPayVo);
        if (errorRet.hasErrors()) {
            List<ObjectError> list = errorRet.getAllErrors();
            for (ObjectError error : list) {
                Result result=new Result();
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                result.setMessage(error.getDefaultMessage());
                return result;
            }
        }
        walletPayVo.setIp((String) request.getAttribute("ip"));
        Result ret=walletPayBillService.pay(walletPayVo);
        log.info("<<<<<<<<<<<["+Thread.currentThread().getId()
                +"]<<<<<<钱包支付接口执行完成，响应-WalletPayVo=>"+walletPayVo);
        return ResultGenerator.genSuccessResult(ret);
    }

    /**
     * 退款
     * @param walletRefundVo
     * @param errorRet
     * @param request
     * @return
     */
    @PostMapping("refund")
    public Result refund(@Valid WalletRefundVo walletRefundVo, BindingResult errorRet, HttpServletRequest request){
        log.info("*********["+Thread.currentThread().getId()
                +"]*******钱包退款接口调起，参数-WalletRefundVo=>"+walletRefundVo);
        if (errorRet.hasErrors()) {
            List<ObjectError> list = errorRet.getAllErrors();
            for (ObjectError error : list) {
                Result result=new Result();
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                result.setMessage(error.getDefaultMessage());
                return result;
            }
        }
        walletRefundVo.setIp((String) request.getAttribute("ip"));
        Result ret=walletRefundBillService.refund(walletRefundVo);
        log.info("<<<<<<<<<<<["+Thread.currentThread().getId()
                +"]<<<<<<钱包退款接口执行完成，响应-WalletRefundVo=>"+walletRefundVo);
        return ResultGenerator.genSuccessResult(ret);
    }

    @PostMapping("getUserBalance")
    public Result getUserBalance(long buyerId){
        log.info("*********["+Thread.currentThread().getId()
                +"]*******钱包余额查询接口调起，参数-buyerId=>"+buyerId);
        if(buyerId<=0){
            return ResultGenerator.genFailResult("非法用户！");
        }
        return ResultGenerator.genSuccessResult(
                walletAccountService.getUserBalance(buyerId));
    }
}
