package com.ma.wallet.enums;

/**
 * 充值状态
 * Created by fengbin on 2017-08-14.
 */
public enum RechargeStatus {
    //（0.待充值 1.充值中 2.成功 3.失败）
    WAIT_RECHARGE(0,"待充值"),IN_RECHARGE(1,"充值中"),SUCCESS(2,"成功"),FAIL(3,"失败");
    private int value;
    private String name;
    RechargeStatus(int value,String name){
        this.value=value;
        this.name=name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
