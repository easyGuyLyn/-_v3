package com.dawoo.gamebox.bean.payInfo;

import java.io.Serializable;

/**
 * Created by rain on 18-3-23.
 */

public class PayItemBean implements Serializable {
   /*  "id": null,
             "payName": "tsett",       //渠道名称
             "account": "www-type-cc",  //账号
             "fullName": "ddsdfsd",      //姓名
             "code": "C043",
             "type": "1",                //支付方式
             "accountType": "2",       //渠道类型
             "bankCode": "wechatpay",
             "bankName": null,
             "singleDepositMin": 3,       //单笔最小存款金额
             "singleDepositMax": 300,    //单笔最大存款金额
             "openAcountName": "",      //开户行
             "qrCodeUrl": "",              //二维码URL
             "remark": "",                //前段备注
             "randomAmount": false,    //是否开启随机额度
             "aliasName": "微信电子支付1",     //别名
             "customBankName": "微信支付爱仕达撒所多",  //第三方自定义名称
             "accountInformation": null,        //自定义账号信息
             "accountPrompt": null,           //自定义账号提示
             "rechargeType": "wechatpay_fast",     //存款类型
             "depositWay": "wechatpay_fast",      //获取优惠类型
             "payType": null,
             "searchId": "dda57b021f0584d769c4a94dcad153b9",     //提交订单id
             "imgUrl": null                          //渠道图片路径*/


   private String searchId;
    private String payName;
    private String account;
    private String fullName;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private int type;
    private int accountType;
    private String bankCode;
    private int singleDepositMin;
    private int singleDepositMax;
    private String customBankName;
    private String openAcountName;
    private String qrCodeUrl;
    private String remark;
    private boolean randomAmount;
    private String aliasName;
    private String accountInformation;
    private String accountPrompt;
    private String depositWay;
    private String rechargeType;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }


    public String getDepositWay() {
        return depositWay;
    }

    public void setDepositWay(String depositWay) {
        this.depositWay = depositWay;
    }


    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public int getSingleDepositMin() {
        return singleDepositMin;
    }

    public void setSingleDepositMin(int singleDepositMin) {
        this.singleDepositMin = singleDepositMin;
    }

    public int getSingleDepositMax() {
        if (singleDepositMax == 0) {
            singleDepositMax = 300;
        }
        return singleDepositMax;
    }

    public void setSingleDepositMax(int singleDepositMax) {
        this.singleDepositMax = singleDepositMax;
    }

    public String getCustomBankName() {
        return customBankName;
    }

    public void setCustomBankName(String customBankName) {
        this.customBankName = customBankName;
    }

    public String getOpenAcountName() {
        return openAcountName;
    }

    public void setOpenAcountName(String openAcountName) {
        this.openAcountName = openAcountName;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isRandomAmount() {
        return randomAmount;
    }

    public void setRandomAmount(boolean randomAmount) {
        this.randomAmount = randomAmount;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getAccountInformation() {
        return accountInformation;
    }

    public void setAccountInformation(String accountInformation) {
        this.accountInformation = accountInformation;
    }

    public String getAccountPrompt() {
        return accountPrompt;
    }

    public void setAccountPrompt(String accountPrompt) {
        this.accountPrompt = accountPrompt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
