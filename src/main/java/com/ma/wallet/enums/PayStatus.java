package com.ma.wallet.enums;

/**
 * 支付状态
 * Created by fengbin on 2017-08-14.
 */
public enum PayStatus {
    //0.待支付 1.支付中 2.成功 3.失败
    WAIT_PAY(0,"待支付"),IN_PAY(1,"支付中"),SUCCESS(2,"成功"),FAIL(3,"失败");
    private int value;
    private String name;
    PayStatus(int value,String name){
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
