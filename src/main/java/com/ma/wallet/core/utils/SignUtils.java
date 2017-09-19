package com.ma.wallet.core.utils;

import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by fengbin on 2017-08-16.
 */
@Log4j
public class SignUtils {
    public static String getSign(Object object,String signKey) throws Exception{
        Map map=Maps.newHashMap();
        org.apache.commons.beanutils.BeanUtils.populate(object, map);
        List<String> keys = new ArrayList<String>(map.keySet());
        keys.remove("sign");//排除sign参数
        Collections.sort(keys);//排序
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String paramValue=(String) map.get(key);
            if(StringUtils.isEmpty(paramValue))//参数为空字符串就不用拼接
                continue;
            sb.append(key).append("=").append(paramValue).append("&");//拼接字符串
        }
        String linkString = sb.toString();
        linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);//去除最后一个'&'
        String sign = DigestUtils.md5Hex(linkString + signKey);//混合密钥md5
        return sign;
    }
    public static String getSign(Map<String,String> params,String requestKey){
        List<String> keys = new ArrayList<String>(params.keySet());
        keys.remove("sign");//排除sign参数
        Collections.sort(keys);//排序

        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String paramValue=params.get(key);
            if(StringUtils.isEmpty(paramValue))//参数为空字符串就不用拼接
                continue;
            sb.append(key).append("=").append(paramValue).append("&");//拼接字符串
        }
        String linkString = sb.toString();
        linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);//去除最后一个'&'
        log.info("待签名字段串："+linkString);
        return DigestUtils.md5Hex(linkString + requestKey);//混合密钥md5
    }
}
