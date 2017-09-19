package com.ma.wallet.vo;

import com.alibaba.fastjson.JSON;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
/**
 * Created by fengbin on 2017-08-21.
 */
public class WalletRechargeVo extends WalletBaseVo {
    private String rechargeNo;
    @NotNull(message = "用户ID不能为空")
    private Long buyerId;
    @NotNull(message = "销售订单号不能为空")
    private String orderNo;
    @NotNull(message = "支付流水payId不能为空")
    @Min(value = 100, message = "支付流水payId不能小于100")
    @DecimalMax(value = "99999999999", inclusive = true, message = "支付流水payId不能大于11位")
    private Long payId;
    @NotNull(message = "第三方交易流水号不能为空")
    private String tradeNo;
    @NotNull(message = "平台类型不能为空")
    private Short platform;
    @NotNull(message = "充值金额不能为空")
    @DecimalMin(value = "0",inclusive = false,message = "充值金额必需大于零")
    private BigDecimal amount;
    private Short status;
    @NotNull(message = "充值渠道不能为空")
    private Short rechargeChannel;
    private String ip;

    public String getRechargeNo() {
        return rechargeNo;
    }

    public void setRechargeNo(String rechargeNo) {
        this.rechargeNo = rechargeNo;
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

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Short getPlatform() {
        return platform;
    }

    public void setPlatform(Short platform) {
        this.platform = platform;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getRechargeChannel() {
        return rechargeChannel;
    }

    public void setRechargeChannel(Short rechargeChannel) {
        this.rechargeChannel = rechargeChannel;
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