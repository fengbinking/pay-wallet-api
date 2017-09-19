package com.ma.wallet.schedul;

import com.ma.wallet.service.WalletWhiteIpService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务配置类
 * Created by fengbin on 2017-08-22.
 */
@Log4j
@Component
public class SchedulingTask {
    @Autowired
    private WalletWhiteIpService walletWhiteIpService;
    @Scheduled(cron = "0 0/5 * * * ?") // 每5分钟执行一次
    public void loadWhiteIpToRedis() {
        log.info(">>>>>>>>>>>>> loadWhiteIpToRedis ... ");
        walletWhiteIpService.loadWhiteIpToRedis();
    }
}
