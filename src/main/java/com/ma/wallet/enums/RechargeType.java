package com.ma.wallet.enums;

/**
 * 充值方式
 * Created by fengbin on 2017-08-14.
 */
public enum RechargeType {
    //1.微信 2.支付宝
    WECHAT(1,"微信"),ALIPAY(2,"支付宝");
    private int value;
    private String name;

    RechargeType(int value, String name) {
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
