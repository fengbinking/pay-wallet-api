package com.ma.wallet.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "WALLET_ACCOUNT")
public class WalletAccount {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select SEQ_WALLET_ACCOUNT.nextval from dual")
    private Long id;

    @Column(name = "BUYER_ID")
    private Long buyerId;

    @Column(name = "ACCOUNT_TYPE")
    private Short accountType;

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "CASH_AMOUNT")
    private BigDecimal cashAmount;

    @Column(name = "UNCASH_AMOUNT")
    private BigDecimal uncashAmount;

    @Column(name = "FREEZE_CASH_AMOUNT")
    private BigDecimal freezeCashAmount;

    @Column(name = "FREEZE_UNCASH_AMOUNT")
    private BigDecimal freezeUncashAmount;

    @Column(name = "STATUS")
    private Short status;

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
     * @return ACCOUNT_TYPE
     */
    public Short getAccountType() {
        return accountType;
    }

    /**
     * @param accountType
     */
    public void setAccountType(Short accountType) {
        this.accountType = accountType;
    }

    /**
     * @return TOTAL_AMOUNT
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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
     * @return FREEZE_CASH_AMOUNT
     */
    public BigDecimal getFreezeCashAmount() {
        return freezeCashAmount;
    }

    /**
     * @param freezeCashAmount
     */
    public void setFreezeCashAmount(BigDecimal freezeCashAmount) {
        this.freezeCashAmount = freezeCashAmount;
    }

    /**
     * @return FREEZE_UNCASH_AMOUNT
     */
    public BigDecimal getFreezeUncashAmount() {
        return freezeUncashAmount;
    }

    /**
     * @param freezeUncashAmount
     */
    public void setFreezeUncashAmount(BigDecimal freezeUncashAmount) {
        this.freezeUncashAmount = freezeUncashAmount;
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