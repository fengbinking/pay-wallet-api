/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2017-08-15 14:52:44                          */
/*==============================================================*/


drop index unq_account_buyer_id;

drop table wallet_account cascade constraints;

drop index unq_account_change_ref_id;

drop table wallet_account_change cascade constraints;

drop index unq_pay_bill_no;

drop table wallet_pay_bill cascade constraints;

drop index unq_recharge_bill_pay_id;

drop index unq_recharge_bill_trade_no;

drop index unq_recharge_bill_no;

drop table wallet_recharge_bill cascade constraints;

drop index unq_refund_bill_pay_no;

drop index unq_refund_bill_no;

drop table wallet_refund_bill cascade constraints;

drop sequence SEQ_PAY_BILL;

drop sequence SEQ_RECHARGE_BILL;

drop sequence SEQ_REFUND_BILL;

drop sequence SEQ_WALLET_ACCOUNT;

drop sequence SEQ_WALLET_ACCOUNT_CHANGE;

create sequence SEQ_PAY_BILL
increment by 1
start with 10001
 maxvalue 99999999999
 minvalue 10001
 cache 2;

create sequence SEQ_RECHARGE_BILL
increment by 1
start with 10001
 maxvalue 999999999999
 minvalue 10001
 cache 2;

create sequence SEQ_REFUND_BILL
increment by 1
start with 10001
 maxvalue 99999999999
 minvalue 10001
 cache 2;

create sequence SEQ_WALLET_ACCOUNT
increment by 1
start with 10001
 maxvalue 99999999999
 minvalue 10001
 cache 2;

create sequence SEQ_WALLET_ACCOUNT_CHANGE
increment by 1
start with 10001
 maxvalue 99999999999
 minvalue 10001
 cache 2;

/*==============================================================*/
/* Table: wallet_account                                        */
/*==============================================================*/
create table wallet_account 
(
   id                   NUMBER(11)           not null,
   buyer_id             NUMBER(11)           not null,
   account_type         NUMBER(2)            not null,
   total_amount         NUMBER(15,2)         not null,
   cash_amount          NUMBER(15,2),
   uncash_amount        NUMBER(15,2),
   freeze_cash_amount   NUMBER(15,2),
   freeze_uncash_amount NUMBER(15,2),
   status               NUMBER(2)            not null,
   create_time          DATE                 not null,
   update_time          DATE                 not null,
   remark               VARCHAR(100),
   constraint PK_WALLET_ACCOUNT primary key (id)
);

comment on table wallet_account is
'钱包支付-客户账户表';

comment on column wallet_account.id is
'主键';

comment on column wallet_account.buyer_id is
'用户id';

comment on column wallet_account.account_type is
'账号类型(1.ios 2.android 3.小程序-卡奴 4.小程序-01men)';

comment on column wallet_account.total_amount is
'总金额';

comment on column wallet_account.cash_amount is
'可提现金额';

comment on column wallet_account.uncash_amount is
'不可提现金额';

comment on column wallet_account.freeze_uncash_amount is
'不可提现冻结金额';

comment on column wallet_account.status is
'账户状态（1.生效 2.冻结 3.注销）';

comment on column wallet_account.create_time is
'创建时间';

comment on column wallet_account.update_time is
'修改时间';

comment on column wallet_account.remark is
'备注';

/*==============================================================*/
/* Index: unq_account_buyer_id                                  */
/*==============================================================*/
create unique index unq_account_buyer_id on wallet_account (
   buyer_id ASC
);

/*==============================================================*/
/* Table: wallet_account_change                                 */
/*==============================================================*/
create table wallet_account_change 
(
   id                   NUMBER(11)           not null,
   account_id           NUMBER(11)           not null,
   buyer_id             NUMBER(11)           not null,
   change_type          NUMBER(2)            not null,
   pre_amount           NUMBER(15,2)         not null,
   amount               NUMBER(15,2)         not null,
   cash_amount          NUMBER(15,2),
   uncash_amount        NUMBER(15,2),
   ref_id               NUMBER(11)           not null,
   create_time          DATE                 not null,
   remark               VARCHAR(100),
   constraint PK_WALLET_ACCOUNT_CHANGE primary key (id)
);

comment on table wallet_account_change is
'钱包支付-账户资金变动流水表';

comment on column wallet_account_change.id is
'主键';

comment on column wallet_account_change.account_id is
'账户表主键ID';

comment on column wallet_account_change.buyer_id is
'用户id';

comment on column wallet_account_change.change_type is
'类型(1.充值 2.支付 3.退款 4.提现 5.内部调账)';

comment on column wallet_account_change.pre_amount is
'变动前总金额';

comment on column wallet_account_change.amount is
'变动后总金额';

comment on column wallet_account_change.cash_amount is
'可提现发生金额';

comment on column wallet_account_change.uncash_amount is
'不可提发生金额';

comment on column wallet_account_change.ref_id is
'关联流水ID(change_type不同，对应不同流水表，如充值、支付、提现流水)';

comment on column wallet_account_change.create_time is
'创建时间';

comment on column wallet_account_change.remark is
'备注';

/*==============================================================*/
/* Index: unq_account_change_ref_id                             */
/*==============================================================*/
create unique index unq_account_change_ref_id on wallet_account_change (
   ref_id ASC
);

/*==============================================================*/
/* Table: wallet_pay_bill                                       */
/*==============================================================*/
create table wallet_pay_bill 
(
   id                   NUMBER(11)           not null,
   pay_no               VARCHAR(32)          not null,
   buyer_id             NUMBER(11)           not null,
   order_no             VARCHAR(32)          not null,
   platform             NUMBER(2)            not null,
   cash_amout           NUMBER(15,2),
   uncash_amount        NUMBER(15,2),
   status               NUMBER(2)            not null,
   pay_type             NUMBER(2)            not null,
   trade_no             VARCHAR(64),
   pay_id               NUMBER(11),
   ip                   VARCHAR(64)          not null,
   create_time          DATE                 not null,
   update_time          DATE                 not null,
   remark               VARCHAR(100),
   constraint PK_WALLET_PAY_BILL primary key (id)
);

comment on table wallet_pay_bill is
'钱包支付-支付交易流水表';

comment on column wallet_pay_bill.order_no is
'销售订单号（来源电商平台）';

comment on column wallet_pay_bill.platform is
'电商平台（1.ios 2.android 3.小程序-卡奴 4.小程序-01men）';

comment on column wallet_pay_bill.status is
'支付状态（0.待支付 1.支付中 2.成功 3.失败）';

comment on column wallet_pay_bill.pay_type is
'支付类型（0.钱包支付 1.钱包+微信组合支付）';

comment on column wallet_pay_bill.trade_no is
'第三方交易流水号（如微信、支付宝支付流水号）';

comment on column wallet_pay_bill.pay_id is
'支付网关流水主键ID（只有钱包+微信组合支付才有）';

comment on column wallet_pay_bill.ip is
'接口调用方ip';

/*==============================================================*/
/* Index: unq_pay_bill_no                                       */
/*==============================================================*/
create unique index unq_pay_bill_no on wallet_pay_bill (
   pay_no ASC
);

/*==============================================================*/
/* Table: wallet_recharge_bill                                  */
/*==============================================================*/
create table wallet_recharge_bill 
(
   id                   NUMBER(11)           not null,
   recharge_no          VARCHAR(32)          not null,
   buyer_id             NUMBER(11)           not null,
   order_no             VARCHAR(32)          not null,
   pay_id               NUMBER(11)           not null,
   trade_no             VARCHAR(64)          not null,
   platform             NUMBER(2)            not null,
   amount               NUMBER(15,2)         not null,
   status               NUMBER(2)            not null,
   recharge_channel     NUMBER(2)            not null,
   ip                   VARCHAR(64)          not null,
   create_time          DATE                 not null,
   update_time          DATE                 not null,
   constraint PK_WALLET_RECHARGE_BILL primary key (id)
);

comment on table wallet_recharge_bill is
'充值交易流水表';

comment on column wallet_recharge_bill.recharge_no is
'充值交易流水号（钱包支付网关生成）';

comment on column wallet_recharge_bill.buyer_id is
'用户id';

comment on column wallet_recharge_bill.order_no is
'销售订单号';

comment on column wallet_recharge_bill.pay_id is
'支付网关流水表主键ID';

comment on column wallet_recharge_bill.trade_no is
'第三方交易流水号（如微信、支付宝支付流水号）';

comment on column wallet_recharge_bill.platform is
'充值平台（1.ios 2.android 3.小程序-卡奴 4.小程序-01men）';

comment on column wallet_recharge_bill.status is
'充值状态（0.待充值 1.充值中 2.成功 3.失败）';

comment on column wallet_recharge_bill.recharge_channel is
'充值渠道(1.微信 2.支付宝)';

comment on column wallet_recharge_bill.ip is
'接口调用方ip';

comment on column wallet_recharge_bill.create_time is
'创建时间';

comment on column wallet_recharge_bill.update_time is
'修改时间';

/*==============================================================*/
/* Index: unq_recharge_bill_no                                  */
/*==============================================================*/
create unique index unq_recharge_bill_no on wallet_recharge_bill (
   recharge_no ASC
);

/*==============================================================*/
/* Index: unq_recharge_bill_trade_no                            */
/*==============================================================*/
create unique index unq_recharge_bill_trade_no on wallet_recharge_bill (
   trade_no ASC
);

/*==============================================================*/
/* Index: unq_recharge_bill_pay_id                              */
/*==============================================================*/
create unique index unq_recharge_bill_pay_id on wallet_recharge_bill (
   pay_id ASC
);

/*==============================================================*/
/* Table: wallet_refund_bill                                    */
/*==============================================================*/
create table wallet_refund_bill 
(
   id                   NUMBER(11)           not null,
   refund_no            VARCHAR(32)          not null,
   pay_no               VARCHAR(32)           not null,
   refund_type          NUMBER(2)            not null,
   buyer_id             NUMBER(11)           not null,
   order_no             VARCHAR(32)          not null,
   order_note           VARCHAR(100),
   cash_amount          NUMBER(15,2),
   uncash_amount        NUMBER(15,2),
   status               NUMERIC(2)           not null,
   ip                   VARCHAR(64)          not null,
   refund_note          VARCHAR(100),
   create_time          DATE                 not null,
   update_time          DATE                 not null,
   constraint PK_WALLET_REFUND_BILL primary key (id)
);

comment on table wallet_refund_bill is
'钱包支付-退款交易流水表';

comment on column wallet_refund_bill.pay_no is
'原支付流水号（wallet_pay_bill表支付流水号）';

comment on column wallet_refund_bill.refund_type is
'退款类型（1.人工审核退款 2.系统自动退款）';

/*==============================================================*/
/* Index: unq_refund_bill_no                                    */
/*==============================================================*/
create unique index unq_refund_bill_no on wallet_refund_bill (
   refund_no ASC
);

/*==============================================================*/
/* Index: unq_refund_bill_pay_no                                */
/*==============================================================*/
create unique index unq_refund_bill_pay_no on wallet_refund_bill (
   pay_no ASC
);

create table wallet_white_ip(
id number(3),
ip varchar2(64) not null,
usable number(1) default 2,
create_by number(10),
create_time date default sysdate,
update_by number(10),
update_time date default sysdate,
primary key(id)
);
comment on table wallet_white_ip is '钱包支付IP白名单';
comment on column wallet_white_ip.ip is '白名单IP';
comment on column wallet_white_ip.usable is '1.有效 2.无效';

