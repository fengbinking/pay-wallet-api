package com.ma.wallet.service;

import com.ma.wallet.core.service.Service;
import com.ma.wallet.core.vo.Result;
import com.ma.wallet.model.WalletRechargeBill;
import com.ma.wallet.vo.WalletRechargeVo;


/**
 * 钱包充值接口
 * Created by FengBin on 2017-08-15.
 */
public interface WalletRechargeBillService extends Service<WalletRechargeBill> {
    public Result recharge(WalletRechargeVo walletRechargeVo);
}
