package com.ma.wallet.service;

import com.ma.wallet.core.service.Service;
import com.ma.wallet.model.WalletAccount;
import com.ma.wallet.model.WalletAccountChange;

import java.math.BigDecimal;


/**
 * Created by FengBin on 2017-08-15.
 */
public interface WalletAccountChangeService extends Service<WalletAccountChange> {
    /**
     * 保存资金变动流水
     *
     * @param walletAccount
     * @param rechargeAmount 充值金额
     * @param refId          关联流水ID
     * @return
     */
    public int saveWalletAccountChange(WalletAccount walletAccount, BigDecimal rechargeAmount, Long refId);
}
