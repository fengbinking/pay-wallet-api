package com.ma.wallet.service;

import com.ma.wallet.enums.TradType;
import com.ma.wallet.model.OrderIdOrNumber;

/**
 * Created by fengbin on 2017-08-14.
 */
public interface GetOrderIdOrNumberService {
    /**
     *
     * @param tradType 交易类型
     * @param num 将按什么规则生成流水号(生成规则有：年月日、年月日时分秒、年月日时分三种),可选的num有：8、12、14
     * @return
     */
    OrderIdOrNumber getOrderIdOrNumber(TradType tradType, int num);
}
