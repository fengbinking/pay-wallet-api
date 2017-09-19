package com.ma.wallet.core;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 定制版MyBatis Mapper插件接口，如需其他接口参考官方文档自行添加。
 */
public interface MapperPlus<T> extends Mapper<T>, MySqlMapper<T> {
}
