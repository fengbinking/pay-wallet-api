package com.ma.test;

import com.google.common.collect.Maps;
import com.ma.wallet.Application;
import com.ma.wallet.core.utils.SignUtils;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by fengbin on 2017-08-16.
 */
@Log4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class WalletRechargeBillTest {
    @Autowired
    private WebApplicationContext context;
    @Value("${pay.wallet.request.key}")
    private String requestKey;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test() {
        /*for (int i = 0; i < 1; i++)
            new Thread() {
                public void run() {
                    System.out.println("*****************");
                    WalletRechargeBillTest.this.recharge();
                }
            }.start();*/
        this.recharge();
    }
    public void recharge() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        List<String> buyerIds = new ArrayList<>();
        buyerIds.add("1");
        List<String> orderNos = new ArrayList<>();
        orderNos.add("1201708160344523" + (new Random().nextInt(10) + 1));
        List<String> payIds = new ArrayList<>();
        payIds.add("1201782" + (new Random().nextInt(10) + 1));
        List<String> tradeNos = new ArrayList<>();
        tradeNos.add("201702521021235521" + (new Random().nextInt(10) + 1));
        List<String> platforms = new ArrayList<>();
        platforms.add("1");
        List<String> amounts = new ArrayList<>();
        amounts.add(((new Random().nextInt(100) + 1)) + "");
        List<String> rechargeChannels = new ArrayList<>();
        rechargeChannels.add("1");
        List<String> ips = new ArrayList<>();
        ips.add("127.0.0.1");

        //生成签名
        Map<String, String> fields = Maps.newHashMap();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            fields.put(key, params.get(key).get(0));
        }
        List<String> signs = new ArrayList<>();
        signs.add(SignUtils.getSign(fields, requestKey));
        params.put("sign", signs);

        params.put("buyerId", buyerIds);
        params.put("orderNo", orderNos);
        params.put("payId", payIds);
        params.put("tradeNo", tradeNos);
        params.put("platform", platforms);
        params.put("amount", amounts);
        params.put("buyerId", buyerIds);
        params.put("rechargeChannel", rechargeChannels);
        params.put("ip", ips);

        //调用接口，传入添加的用户参数
        try {
            mockMvc.perform(post("/wallet/recharge")
                    .contentType(MediaType.APPLICATION_JSON_UTF8).params(params))
                    //判断返回值，是否达到预期，测试示例中的返回值的结构如下{"errcode":0,"errmsg":"OK","p2pdata":null}
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

