package com.ma.wallet.model;

/**
 * Created by fengbin on 2017-08-14.
 */
public class OrderIdOrNumber {
    //订单主键
    private Long id;
    //订单流水号
    private String orderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
