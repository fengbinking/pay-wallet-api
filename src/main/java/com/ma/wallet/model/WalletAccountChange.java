package com.ma.wallet.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "WALLET_ACCOUNT_CHANGE")
public class WalletAccountChange {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select SEQ_WALLET_ACCOUNT_CHANGE.nextval from dual")
    private Long id;

    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "BUYER_ID")
    private Long buyerId;

    @Column(name = "CHANGE_TYPE")
    private Short changeType;

    @Column(name = "PRE_AMOUNT")
    private BigDecimal preAmount;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CASH_AMOUNT")
    private BigDecimal cashAmount;

    @Column(name = "UNCASH_AMOUNT")
    private BigDecimal uncashAmount;

    @Column(name = "REF_ID")
    private Long refId;

    @Column(name = "CREATE_TIME")
    private Date createTime;

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
     * @return ACCOUNT_ID
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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
     * @return CHANGE_TYPE
     */
    public Short getChangeType() {
        return changeType;
    }

    /**
     * @param changeType
     */
    public void setChangeType(Short changeType) {
        this.changeType = changeType;
    }

    /**
     * @return PRE_AMOUNT
     */
    public BigDecimal getPreAmount() {
        return preAmount;
    }

    /**
     * @param preAmount
     */
    public void setPreAmount(BigDecimal preAmount) {
        this.preAmount = preAmount;
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
     * @return REF_ID
     */
    public Long getRefId() {
        return refId;
    }

    /**
     * @param refId
     */
    public void setRefId(Long refId) {
        this.refId = refId;
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