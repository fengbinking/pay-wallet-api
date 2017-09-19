package com.ma.wallet.service;

import com.ma.wallet.core.service.Service;
import com.ma.wallet.core.vo.Result;
import com.ma.wallet.model.WalletPayBill;
import com.ma.wallet.vo.WalletPayVo;


/**
 * 钱包支付接口
 * Created by FengBin on 2017-08-15.
 */
public interface WalletPayBillService extends Service<WalletPayBill> {
    public Result pay(WalletPayVo walletPayVo);
}
