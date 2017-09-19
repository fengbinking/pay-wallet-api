package com.ma.wallet.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "WALLET_REFUND_BILL")
public class WalletRefundBill {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "REFUND_NO")
    private String refundNo;

    @Column(name = "PAY_NO")
    private Object payNo;

    @Column(name = "REFUND_TYPE")
    private Short refundType;

    @Column(name = "BUYER_ID")
    private Long buyerId;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "ORDER_NOTE")
    private String orderNote;

    @Column(name = "CASH_AMOUNT")
    private BigDecimal cashAmount;

    @Column(name = "UNCASH_AMOUNT")
    private BigDecimal uncashAmount;

    @Column(name = "STATUS")
    private Short status;

    @Column(name = "IP")
    private String ip;

    @Column(name = "REFUND_NOTE")
    private String refundNote;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return REFUND_NO
     */
    public String getRefundNo() {
        return refundNo;
    }

    /**
     * @param refundNo
     */
    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    /**
     * @return PAY_NO
     */
    public Object getPayNo() {
        return payNo;
    }

    /**
     * @param payNo
     */
    public void setPayNo(Object payNo) {
        this.payNo = payNo;
    }

    /**
     * @return REFUND_TYPE
     */
    public Short getRefundType() {
        return refundType;
    }

    /**
     * @param refundType
     */
    public void setRefundType(Short refundType) {
        this.refundType = refundType;
    }

    /**
     * @return BUYER_ID
     */
    public Long getBuyerId() {
        return buyerId;
    }

    /**
     * @param buyerId
     */
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    /**
     * @return ORDER_NO
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return ORDER_NOTE
     */
    public String getOrderNote() {
        return orderNote;
    }

    /**
     * @param orderNote
     */
    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    /**
     * @return CASH_AMOUNT
     */
    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    /**
     * @param cashAmount
     */
    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    /**
     * @return UNCASH_AMOUNT
     */
    public BigDecimal getUncashAmount() {
        return uncashAmount;
    }

    /**
     * @param uncashAmount
     */
    public void setUncashAmount(BigDecimal uncashAmount) {
        this.uncashAmount = uncashAmount;
    }

    /**
     * @return STATUS
     */
    public Short getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * @return IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return REFUND_NOTE
     */
    public String getRefundNote() {
        return refundNote;
    }

    /**
     * @param refundNote
     */
    public void setRefundNote(String refundNote) {
        this.refundNote = refundNote;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}