package com.ma.test;

import com.google.common.collect.Maps;
import com.ma.wallet.core.utils.SignUtils;
import com.ma.wallet.enums.PayType;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * Created by fengbin on 2017-08-16.
 */
public class SuperveneRequestTest {
    public static void main(String[] args) {
        /*try {
            System.out.println(post("http://localhost:8080/wallet/recharge", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        for (int i = 0; i < 1; i++)
            new Thread() {
                public void run() {
                    try {
                        System.out.println(userBalancePost());
//                        System.out.println(payPost());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
    }

    /**
     * 充值压力测试
     * @return
     * @throws IOException
     */
    static String rechargePost() throws IOException {
        String url="http://localhost:8080/wallet/recharge";
        Map<String, String> params = Maps.newHashMap();
//        String buyerId = (new Random().nextInt(10000) + 1)+"";
        String buyerId = "1043";
        params.put("buyerId", buyerId);
        String orderNo = "12017081603523" + (new Random().nextInt(10000) + 1);
        params.put("orderNo", orderNo);
        String payId = "12012" + (new Random().nextInt(10000) + 1);
        params.put("payId", payId);
        String tradeNo = "20170252102125521" + (new Random().nextInt(10000) + 1);
        params.put("tradeNo", tradeNo);
        String platform = "1";
        params.put("platform", platform);
        String amount = ((new Random().nextInt(10000) + 1)) + "";
        params.put("amount", amount);
        String rechargeChannel = "1";
        params.put("rechargeChannel", rechargeChannel);
        String ip = "172.32.80.87";
        params.put("ip", ip);

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder()
                .add("buyerId", buyerId)
                .add("orderNo", orderNo)

                .add("payId", payId)
                .add("tradeNo", tradeNo)
                .add("platform", platform)
                .add("amount", amount)
                .add("rechargeChannel", rechargeChannel)

                .add("ip", ip)
                .add("sign", SignUtils.getSign(params, "982Ykswe6dqpgdfsevds"))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
    /**
     * 支付压力测试
     * @return
     * @throws IOException
     */
    static String payPost() throws IOException {
        String url="http://localhost:8080/wallet/pay";
        Map<String, String> params = Maps.newHashMap();
        String payType= PayType.COMBINE_PAY.getValue()+"";
        params.put("payType", payType);
        String buyerId = "1043";
        params.put("buyerId", buyerId);
        String orderNo = "12017081603523" + (new Random().nextInt(10000) + 1);
        params.put("orderNo", orderNo);
        String payId="";
        String tradeNo="";
        if (payType.equals(PayType.COMBINE_PAY.getValue()+"")){
            payId = "12012" + (new Random().nextInt(10000) + 1);
            params.put("payId", payId);
            tradeNo = "20170252102125521" + (new Random().nextInt(10000) + 1);
            params.put("tradeNo", tradeNo);
        }
        String platform = "1";
        params.put("platform", platform);
        String uncashAmount = "100";
        params.put("uncashAmount", uncashAmount);
        String ip = "172.32.80.87";
        params.put("ip", ip);

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder()
                .add("buyerId", buyerId)
                .add("orderNo", orderNo)
                .add("payType",payType)
                .add("payId", payId)
                .add("tradeNo", tradeNo)
                .add("platform", platform)
                .add("uncashAmount", uncashAmount)
                .add("ip", ip)
                .add("sign", SignUtils.getSign(params, "982Ykswe6dqpgdfsevds"))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 退款压力测试
     * @return
     * @throws IOException
     */
    static String refundPost() throws IOException {
        String url="http://localhost:8080/wallet/refund";
        Map<String, String> params = Maps.newHashMap();
        String payNo= "PB2017081815272000010026";
        params.put("payNo", payNo);
        String buyerId = "1043";
        params.put("buyerId", buyerId);
        String orderNo = "12017081603523476";
        params.put("orderNo", orderNo);
        String uncashAmount = "100";
        params.put("uncashAmount", uncashAmount);
        String ip = "172.32.80.87";
        params.put("ip", ip);

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder()
                .add("payNo", payNo)
                .add("orderNo", orderNo)
                .add("buyerId",buyerId)
                .add("uncashAmount", uncashAmount)
                .add("ip", ip)
                .add("sign", SignUtils.getSign(params, "982Ykswe6dqpgdfsevds"))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 余额查询
     * @return
     * @throws IOException
     */
    static String userBalancePost() throws IOException {
        String url="http://localhost:8888/wallet/getUserBalance";
        Map<String, String> params = Maps.newHashMap();
        String buyerId = "10431";
        params.put("buyerId", buyerId);
        String ip = "127.0.0.1";
        params.put("ip", ip);

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder()
                .add("buyerId",buyerId)
                .add("ip", ip)
                .add("sign", SignUtils.getSign(params, "982Ykswe6dqpgdfsevds"))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
