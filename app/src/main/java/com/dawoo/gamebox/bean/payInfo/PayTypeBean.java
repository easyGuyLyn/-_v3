package com.dawoo.gamebox.bean.payInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rain on 18-3-23.
 */

public class PayTypeBean {

    /*
         "currency":"CNY",    //当前主货币符号
         "customerService":"http://www.sina212.com",  //第三方客服跳转路径
         "quickMoneys":[                 //快选金额
                 100.0,
                 200.0,
                 500.0,
                 1000.0,
                 5000.0
                 ],
         "payerBankcard":null,         //上次支付名称
         "hide":false,                  //是否隐藏账号
         "multipleAccount":true       //是否使用多个收款账号*/
    private List<PayItemBean> arrayList = new ArrayList<>();
    private String currency;
    private String customerService;
    private List<String> quickMoneys;
    private String payerBankcard;
    private List<CounterBean> counterRechargeTypes = new ArrayList<>();
    private boolean hide;
    private boolean multipleAccount;
    private boolean newActivity;//是否开启顶部弹框

    public boolean isNewActivity() {
        return newActivity;
    }

    public void setNewActivity(boolean newActivity) {
        this.newActivity = newActivity;
    }

    public List<PayItemBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<PayItemBean> arrayList) {
        this.arrayList = arrayList;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerService() {
        return customerService;
    }

    public void setCustomerService(String customerService) {
        this.customerService = customerService;
    }

    public List<String> getQuickMoneys() {
        return quickMoneys;
    }

    public void setQuickMoneys(List<String> quickMoneys) {
        this.quickMoneys = quickMoneys;
    }

    public String getPayerBankcard() {
        return payerBankcard;
    }

    public void setPayerBankcard(String payerBankcard) {
        this.payerBankcard = payerBankcard;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public boolean isMultipleAccount() {
        return multipleAccount;
    }

    public void setMultipleAccount(boolean multipleAccount) {
        this.multipleAccount = multipleAccount;
    }

    public List<CounterBean> getCounterRechargeTypes() {
        return counterRechargeTypes;
    }

    public void setCounterRechargeTypes(List<CounterBean> counterRechargeTypes) {
        this.counterRechargeTypes = counterRechargeTypes;
    }
}
