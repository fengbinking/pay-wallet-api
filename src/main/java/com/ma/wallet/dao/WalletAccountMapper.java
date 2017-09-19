package com.ma.wallet.dao;

import com.ma.wallet.core.MapperPlus;
import com.ma.wallet.model.WalletAccount;

public interface WalletAccountMapper extends MapperPlus<WalletAccount> {
    public int updateAccountBalance(WalletAccount walletAccount);
}