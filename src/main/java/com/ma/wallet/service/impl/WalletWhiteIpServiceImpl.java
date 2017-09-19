package com.ma.wallet.service.impl;

import com.ma.wallet.core.cache.CacheKeyPrefix;
import com.ma.wallet.core.cache.RedisUtils;
import com.ma.wallet.core.service.AbstractService;
import com.ma.wallet.dao.WalletWhiteIpMapper;
import com.ma.wallet.model.WalletWhiteIp;
import com.ma.wallet.service.WalletWhiteIpService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by FengBin on 2017-08-22.
 */
@Log4j
@Service
public class WalletWhiteIpServiceImpl extends AbstractService<WalletWhiteIp> implements WalletWhiteIpService {
    @Resource
    private WalletWhiteIpMapper walletWhiteIpMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    public void loadWhiteIpToRedis(){
        List<WalletWhiteIp> walletWhiteIpList= this.findWhiteIpAll();
        for (WalletWhiteIp walletWhiteIp:walletWhiteIpList){
            RedisUtils.putStringToMap(CacheKeyPrefix.WHITE_IP_MAP,walletWhiteIp.getIp(),walletWhiteIp.getId().toString());
        }
        boolean isExist=redisTemplate.hasKey(CacheKeyPrefix.WHITE_IP_MAP);
        log.info(CacheKeyPrefix.WHITE_IP_MAP+" is exist=>"+isExist);
        if(isExist)
            redisTemplate.expire(CacheKeyPrefix.WHITE_IP_MAP,10, TimeUnit.MINUTES);
    }
    private List<WalletWhiteIp> findWhiteIpAll(){
        Condition condition=new Condition(WalletWhiteIp.class);
        condition.createCriteria().andEqualTo("usable",1);
        return this.findByCondition(condition);
    }
}
