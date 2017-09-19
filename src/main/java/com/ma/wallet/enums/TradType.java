package com.ma.wallet.enums;

/**
 * 交易类型列表
 * Created by fengbin on 2017-08-14.
 */
public enum TradType {
    RECHARGE(1, "RC", "SEQ_RECHARGE_BILL", "充值"), PAY(2, "PB", "SEQ_PAY_BILL", "支付"),
           REFUND(3, "RF", "SEQ_REFUND_BILL", "退款"), CASH(4, "CH", "", "提现");
    private int value;//数据库字段保存值
    private String prefix;//流水号前缀
    private String sequence;//序列名称
    private String name;//类型描述

    private TradType(int value, String prefix, String sequence, String name) {
        this.value = value;
        this.prefix = prefix;
        this.sequence = sequence;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSequence() {
        return sequence;
    }

    public String getName() {
        return name;
    }
}
