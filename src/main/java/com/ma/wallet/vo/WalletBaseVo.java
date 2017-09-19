package com.ma.wallet.vo;

/**
 * Created by fengbin on 2017-08-15.
 */
public class WalletBaseVo {
    private String sign;
    private String nonce_str;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }
}
