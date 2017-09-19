package com.ma.wallet.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "WALLET_RECHARGE_BILL")
public class WalletRechargeBill {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "RECHARGE_NO")
    private String rechargeNo;

    @Column(name = "BUYER_ID")
    private Long buyerId;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "PAY_ID")
    private Long payId;

    @Column(name = "TRADE_NO")
    private String tradeNo;

    @Column(name = "PLATFORM")
    private Short platform;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "STATUS")
    private Short status;

    @Column(name = "RECHARGE_CHANNEL")
    private Short rechargeChannel;

    @Column(name = "IP")
    private String ip;

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
     * @return RECHARGE_NO
     */
    public String getRechargeNo() {
        return rechargeNo;
    }

    /**
     * @param rechargeNo
     */
    public void setRechargeNo(String rechargeNo) {
        this.rechargeNo = rechargeNo;
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
     * @return AMOUNT
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
     * @return RECHARGE_CHANNEL
     */
    public Short getRechargeChannel() {
        return rechargeChannel;
    }

    /**
     * @param rechargeChannel
     */
    public void setRechargeChannel(Short rechargeChannel) {
        this.rechargeChannel = rechargeChannel;
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
}