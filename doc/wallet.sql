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
'Ǯ��֧��-�ͻ��˻���';

comment on column wallet_account.id is
'����';

comment on column wallet_account.buyer_id is
'�û�id';

comment on column wallet_account.account_type is
'�˺�����(1.ios 2.android 3.С����-��ū 4.С����-01men)';

comment on column wallet_account.total_amount is
'�ܽ��';

comment on column wallet_account.cash_amount is
'�����ֽ��';

comment on column wallet_account.uncash_amount is
'�������ֽ��';

comment on column wallet_account.freeze_uncash_amount is
'�������ֶ�����';

comment on column wallet_account.status is
'�˻�״̬��1.��Ч 2.���� 3.ע����';

comment on column wallet_account.create_time is
'����ʱ��';

comment on column wallet_account.update_time is
'�޸�ʱ��';

comment on column wallet_account.remark is
'��ע';

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
'Ǯ��֧��-�˻��ʽ�䶯��ˮ��';

comment on column wallet_account_change.id is
'����';

comment on column wallet_account_change.account_id is
'�˻�������ID';

comment on column wallet_account_change.buyer_id is
'�û�id';

comment on column wallet_account_change.change_type is
'����(1.��ֵ 2.֧�� 3.�˿� 4.���� 5.�ڲ�����)';

comment on column wallet_account_change.pre_amount is
'�䶯ǰ�ܽ��';

comment on column wallet_account_change.amount is
'�䶯���ܽ��';

comment on column wallet_account_change.cash_amount is
'�����ַ������';

comment on column wallet_account_change.uncash_amount is
'�����ᷢ�����';

comment on column wallet_account_change.ref_id is
'������ˮID(change_type��ͬ����Ӧ��ͬ��ˮ�����ֵ��֧����������ˮ)';

comment on column wallet_account_change.create_time is
'����ʱ��';

comment on column wallet_account_change.remark is
'��ע';

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
'Ǯ��֧��-֧��������ˮ��';

comment on column wallet_pay_bill.order_no is
'���۶����ţ���Դ����ƽ̨��';

comment on column wallet_pay_bill.platform is
'����ƽ̨��1.ios 2.android 3.С����-��ū 4.С����-01men��';

comment on column wallet_pay_bill.status is
'֧��״̬��0.��֧�� 1.֧���� 2.�ɹ� 3.ʧ�ܣ�';

comment on column wallet_pay_bill.pay_type is
'֧�����ͣ�0.Ǯ��֧�� 1.Ǯ��+΢�����֧����';

comment on column wallet_pay_bill.trade_no is
'������������ˮ�ţ���΢�š�֧����֧����ˮ�ţ�';

comment on column wallet_pay_bill.pay_id is
'֧��������ˮ����ID��ֻ��Ǯ��+΢�����֧�����У�';

comment on column wallet_pay_bill.ip is
'�ӿڵ��÷�ip';

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
'��ֵ������ˮ��';

comment on column wallet_recharge_bill.recharge_no is
'��ֵ������ˮ�ţ�Ǯ��֧���������ɣ�';

comment on column wallet_recharge_bill.buyer_id is
'�û�id';

comment on column wallet_recharge_bill.order_no is
'���۶�����';

comment on column wallet_recharge_bill.pay_id is
'֧��������ˮ������ID';

comment on column wallet_recharge_bill.trade_no is
'������������ˮ�ţ���΢�š�֧����֧����ˮ�ţ�';

comment on column wallet_recharge_bill.platform is
'��ֵƽ̨��1.ios 2.android 3.С����-��ū 4.С����-01men��';

comment on column wallet_recharge_bill.status is
'��ֵ״̬��0.����ֵ 1.��ֵ�� 2.�ɹ� 3.ʧ�ܣ�';

comment on column wallet_recharge_bill.recharge_channel is
'��ֵ����(1.΢�� 2.֧����)';

comment on column wallet_recharge_bill.ip is
'�ӿڵ��÷�ip';

comment on column wallet_recharge_bill.create_time is
'����ʱ��';

comment on column wallet_recharge_bill.update_time is
'�޸�ʱ��';

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
'Ǯ��֧��-�˿����ˮ��';

comment on column wallet_refund_bill.pay_no is
'ԭ֧����ˮ�ţ�wallet_pay_bill��֧����ˮ�ţ�';

comment on column wallet_refund_bill.refund_type is
'�˿����ͣ�1.�˹�����˿� 2.ϵͳ�Զ��˿';

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
comment on table wallet_white_ip is 'Ǯ��֧��IP������';
comment on column wallet_white_ip.ip is '������IP';
comment on column wallet_white_ip.usable is '1.��Ч 2.��Ч';

