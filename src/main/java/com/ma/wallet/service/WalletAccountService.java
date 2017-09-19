package com.ma.wallet.service;

import com.ma.wallet.core.service.Service;
import com.ma.wallet.model.WalletAccount;
import com.ma.wallet.vo.WalletAccountVo;


/**
 * Created by FengBin on 2017-08-15.
 */
public interface WalletAccountService extends Service<WalletAccount> {
    public int updateAccountBalance(WalletAccount walletAccount);
    public WalletAccountVo getUserBalance(long buyerId);
}
