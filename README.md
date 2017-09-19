# 钱包支付、余额管理
## 流程图
- **钱包充值流程图**
![image](https://github.com/fengbinking/pay-wallet-api/blob/master/src/main/resources/static/wallet_charge.jpg?raw=true)
- **钱包消费（包括二种情况 1.钱包与微信搭配支付 2.钱包单独支付）流程图：**
![image](https://github.com/fengbinking/pay-wallet-api/blob/master/src/main/resources/static/wallet_pay.jpg?raw=true)

## 项目部署
1.初始化数据库脚本(doc目录下wallet.sql)

2.配置数据库、redis连接,根据不同环境配置对应application前缀属性文件

3.生产环境需要在wallet_white_ip表中增加白名单IP

## 接口测试
* 项目测试类SuperveneRequestTest中已编写测试用例