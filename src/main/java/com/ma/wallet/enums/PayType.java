package com.ma.wallet.enums;

/**
 * 支付方式
 * Created by fengbin on 2017-08-14.
 */
public enum PayType {
    //0.钱包支付 1.钱包+微信组合支付
    WECHAT(0,"钱包支付"),COMBINE_PAY(1,"钱包+微信组合支付");
    private int value;
    private String name;

    PayType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
