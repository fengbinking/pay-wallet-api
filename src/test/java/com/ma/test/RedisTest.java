package com.ma.test;

import com.ma.wallet.Application;
import com.ma.wallet.core.cache.RedisUtils;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * Created by fengbin on 2017-08-22.
 */
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles(profiles = "pro")
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    public void test(){
        RedisUtils.set("key1","JJJJJJJJddddddddddd");
        log.info("key1:"+ RedisUtils.getString("key1"));
        RedisUtils.putStringToMap("a1","f1","bbb");
        log.info(RedisUtils.getStringFromMap("a1","f2"));
        redisTemplate.expire("a1",10, TimeUnit.MINUTES);
    }
}
