package com.dawoo.gamebox.bean.payInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rain on 18-3-25.
 */

public class PopPayBean implements Serializable {
    /*  "msg": "免手续费",     ／／是否有手续费信息
    "fee": 0.0,　　　　　　／／手续费金额（负数则为返手续费）
            "counterFee": "￥0.00",　／／手续费金额格式化
    "failureCount": 2,　　　　／／该渠道连续存款未成功次数
    "sales": [　　　　　　　	／／优惠活动列表(为空则无优惠)
    {
        "id": 246,　　　　　　　／／活动ｉｄ
        "preferential": true,　　／／是否可参与该活动
        "activityName": "公司入款100%贴心回馈"　　／／活动名称
    },*/

    private String msg;
    private double fee;
    private String counterFee;
    private int failureCount;
    private List<SaleBean> sales = new ArrayList<>();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getCounterFee() {
        return counterFee;
    }

    public void setCounterFee(String counterFee) {
        this.counterFee = counterFee;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public List<SaleBean> getSales() {
        return sales;
    }

    public void setSales(List<SaleBean> sales) {
        this.sales = sales;
    }
}

