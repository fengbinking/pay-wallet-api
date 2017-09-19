package com.ma.wallet.core.init;

import com.ma.wallet.service.WalletWhiteIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 加载白名单到缓存
 * Created by fengbin on 2017-08-22.
 */
@Component
public class RedisLoadData implements CommandLineRunner {
    @Autowired
    private WalletWhiteIpService walletWhiteIpService;
    @Override
    public void run(String... strings) throws Exception {
        walletWhiteIpService.loadWhiteIpToRedis();
    }
}
