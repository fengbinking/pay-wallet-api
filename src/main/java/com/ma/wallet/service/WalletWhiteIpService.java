package com.ma.wallet.service;
import com.ma.wallet.model.WalletWhiteIp;
import com.ma.wallet.core.service.Service;


/**
 * Created by FengBin on 2017-08-22.
 */
public interface WalletWhiteIpService extends Service<WalletWhiteIp> {
    public void loadWhiteIpToRedis();
}
