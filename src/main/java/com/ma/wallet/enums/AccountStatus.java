package com.ma.wallet.enums;

/**
 * 账户类型
 * Created by fengbin on 2017-08-14.
 */
public enum AccountStatus {
    //账户状态（1.生效 2.冻结 3.注销）
    USABLE(1,"生效"),FREEZE(2,"冻结"),LOGOUT(3,"注销");
    private int value;
    private String name;

    AccountStatus(int value, String name) {
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
