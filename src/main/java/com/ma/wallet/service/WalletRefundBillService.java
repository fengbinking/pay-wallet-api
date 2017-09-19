package com.ma.wallet.service;

import com.ma.wallet.core.service.Service;
import com.ma.wallet.core.vo.Result;
import com.ma.wallet.model.WalletRefundBill;
import com.ma.wallet.vo.WalletRefundVo;


/**
 * Created by FengBin on 2017-08-15.
 */
public interface WalletRefundBillService extends Service<WalletRefundBill> {
    public Result refund(WalletRefundVo walletRefundVo);
}
