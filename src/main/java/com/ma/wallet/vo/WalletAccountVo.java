package com.ma.wallet.vo;

import java.math.BigDecimal;

public class WalletAccountVo extends WalletBaseVo{

    private Long buyerId;

    private Short accountType;

    private BigDecimal totalAmount;

    private BigDecimal cashAmount;

    private BigDecimal uncashAmount;

    private BigDecimal freezeCashAmount;

    private BigDecimal freezeUncashAmount;

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Short getAccountType() {
        return accountType;
    }

    public void setAccountType(Short accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public BigDecimal getFreezeCashAmount() {
        return freezeCashAmount;
    }

    public void setFreezeCashAmount(BigDecimal freezeCashAmount) {
        this.freezeCashAmount = freezeCashAmount;
    }

    public BigDecimal getFreezeUncashAmount() {
        return freezeUncashAmount;
    }

    public void setFreezeUncashAmount(BigDecimal freezeUncashAmount) {
        this.freezeUncashAmount = freezeUncashAmount;
    }
}