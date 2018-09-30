package com.dawoo.gamebox.bean;

/**
 * Created by b on 18-3-27.
 */

public class ApiBean {
    /**
     * apiId : 12
     * status :
     * apiMoney : 0.00
     */

    private int apiId;
    private String status;
    private String apiMoney;

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApiMoney() {
        return apiMoney;
    }

    public void setApiMoney(String apiMoney) {
        this.apiMoney = apiMoney;
    }
}
