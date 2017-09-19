package com.ma.wallet.vo;

import com.alibaba.fastjson.JSON;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
/**
 * Created by fengbin on 2017-08-21.
 */
public class WalletPayVo extends WalletBaseVo {
    private String payNo;
    @NotNull(message = "用户ID不能为空")
    private Long buyerId;
    @NotNull(message = "销售订单号不能为空")
    private String orderNo;
    @NotNull(message = "平台类型不能为空")
    private Short platform;
    private BigDecimal cashAmout;//可提现金额
    @DecimalMin(value = "0",inclusive = false,message = "不可提现金额不能小于或等于零")
    @NotNull(message = "不可提现金额不能为空")
    private BigDecimal uncashAmount;//不可提现金额
    private Short status;//支付状态（0.待支付 1.支付中 2.成功 3.失败）
    @NotNull(message = "支付类型不能为空")
    private Short payType;//支付类型（0.钱包支付 1.钱包+微信组合支付）
    private String tradeNo;//第三方交易流水号（如微信、支付宝支付流水号）
    private Long payId;//支付网关流水主键ID（只有钱包+微信组合支付才有）
    private String ip;
    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Short getPlatform() {
        return platform;
    }

    public void setPlatform(Short platform) {
        this.platform = platform;
    }

    public BigDecimal getCashAmout() {
        return cashAmout;
    }

    public void setCashAmout(BigDecimal cashAmout) {
        this.cashAmout = cashAmout;
    }

    public BigDecimal getUncashAmount() {
        return uncashAmount;
    }

    public void setUncashAmount(BigDecimal uncashAmount) {
        this.uncashAmount = uncashAmount;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getPayType() {
        return payType;
    }

    public void setPayType(Short payType) {
        this.payType = payType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}