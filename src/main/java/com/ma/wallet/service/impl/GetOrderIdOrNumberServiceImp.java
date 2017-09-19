package com.ma.wallet.service.impl;

import com.ma.wallet.enums.TradType;
import com.ma.wallet.model.OrderIdOrNumber;
import com.ma.wallet.service.GetOrderIdOrNumberService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fengbin on 2017-08-14.
 */
@Log4j
@Service
public class GetOrderIdOrNumberServiceImp implements GetOrderIdOrNumberService{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public OrderIdOrNumber getOrderIdOrNumber(TradType tradType, int num) {
        String sql="select "+ tradType.getSequence()+".nextval as id from dual";
        log.info("获取订单流水号与主键sql:"+sql);
        return jdbcTemplate.queryForObject(sql, new RowMapper<OrderIdOrNumber>() {
            @Override
            public OrderIdOrNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrderIdOrNumber orderIdOrNumber = new OrderIdOrNumber();
                Long id=rs.getLong("id");
                orderIdOrNumber.setId(id);
                String format=null;
                if(num==8)//根据年月日生成订单编号,如20170814
                    format=new SimpleDateFormat("yyyyMMdd").format(new Date());
                else if(num==14)//根据年月日时分秒生成订单编号
                    format=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                else//根据年月日，如170814
                    format=new SimpleDateFormat("yyMMdd").format(new Date());
                String postfix=id.toString();
                if (postfix.length()<8)//不足8位，用零补齐
                    postfix=String.format("%0" + 8 + "d", Integer.parseInt(postfix) + 1);
                else if (postfix.length()>8)//大于8位截取高位
                    postfix=postfix.substring(postfix.length()-8);
                String orderNo=tradType.getPrefix() + format + postfix;
                orderIdOrNumber.setOrderNo(orderNo);
                return orderIdOrNumber;
            }
        });
    }
}
