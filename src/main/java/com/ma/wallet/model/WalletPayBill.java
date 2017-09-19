package com.ma.wallet.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "WALLET_PAY_BILL")
public class WalletPayBill {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "PAY_NO")
    private String payNo;

    @Column(name = "BUYER_ID")
    private Long buyerId;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "PLATFORM")
    private Short platform;

    @Column(name = "CASH_AMOUT")
    private BigDecimal cashAmout;

    @Column(name = "UNCASH_AMOUNT")
    private BigDecimal uncashAmount;

    @Column(name = "STATUS")
    private Short status;

    @Column(name = "PAY_TYPE")
    private Short payType;

    @Column(name = "TRADE_NO")
    private String tradeNo;

    @Column(name = "PAY_ID")
    private Long payId;

    @Column(name = "IP")
    private String ip;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "REMARK")
    private String remark;

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
     * @return PAY_NO
     */
    public String getPayNo() {
        return payNo;
    }

    /**
     * @param payNo
     */
    public void setPayNo(String payNo) {
        this.payNo = payNo;
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
     * @return PLATFORM
     */
    public Short getPlatform() {
        return platform;
    }

    /**
     * @param platform
     */
    public void setPlatform(Short platform) {
        this.platform = platform;
    }

    /**
     * @return CASH_AMOUT
     */
    public BigDecimal getCashAmout() {
        return cashAmout;
    }

    /**
     * @param cashAmout
     */
    public void setCashAmout(BigDecimal cashAmout) {
        this.cashAmout = cashAmout;
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
     * @return PAY_TYPE
     */
    public Short getPayType() {
        return payType;
    }

    /**
     * @param payType
     */
    public void setPayType(Short payType) {
        this.payType = payType;
    }

    /**
     * @return TRADE_NO
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * @param tradeNo
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    /**
     * @return PAY_ID
     */
    public Long getPayId() {
        return payId;
    }

    /**
     * @param payId
     */
    public void setPayId(Long payId) {
        this.payId = payId;
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

    /**
     * @return REMARK
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}