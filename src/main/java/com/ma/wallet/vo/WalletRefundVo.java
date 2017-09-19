package com.ma.wallet.vo;

import com.alibaba.fastjson.JSON;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by fengbin on 2017-08-21.
 */
public class WalletRefundVo extends WalletBaseVo {
    //退款流水号
    private String refundNo;
    @NotNull(message = "支付流水号不能为空")
    private Object payNo;
    @Column(name = "用户账户ID不能为空")
    private Long buyerId;
    @Column(name = "原销售订单号不能为空")
    private String orderNo;
    //订单退款备注
    private String orderNote;
    //退款到可提现金额
    private BigDecimal cashAmount;
    //退款到不可提现金额（目前只支持这一种）
    @NotNull(message = "退款金额不能为空")
    private BigDecimal uncashAmount;
    //退款状态（0.退款申请 1.退款拒绝 3.退款处理中 4.退款成功 5.退款失败）
    private Short status;
    //接口调用方ip
    @NotNull(message = "接口调用方IP不能为空")
    private String ip;
    //退款说明
    private String refundNote;

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public Object getPayNo() {
        return payNo;
    }

    public void setPayNo(Object payNo) {
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

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRefundNote() {
        return refundNote;
    }

    public void setRefundNote(String refundNote) {
        this.refundNote = refundNote;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}