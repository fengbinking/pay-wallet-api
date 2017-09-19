package com.ma.wallet.enums;

/**
 * 退款状态
 * Created by fengbin on 2017-08-14.
 */
public enum RefundStatus {
    //0.退款申请 1.退款拒绝 2.退款处理中 3.退款成功 4.退款失败
    APPLY(0, "退款申请"), REFUSE(1, "退款拒绝"), IN_HAND(2, "退款处理中"), SUCCESS(3, "退款成功"), FAIL(4, "退款失败");
    private int value;
    private String name;

    RefundStatus(int value, String name) {
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
