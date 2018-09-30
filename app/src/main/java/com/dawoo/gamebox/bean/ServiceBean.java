package com.dawoo.gamebox.bean;

/**
 * Created by benson on 18-4-12.
 */

public class ServiceBean {


    /**
     * isInlay : true
     * customerUrl : https://www.hao123.com
     */

    private boolean isInlay;
    private String customerUrl;

    public boolean isIsInlay() {
        return isInlay;
    }

    public void setIsInlay(boolean isInlay) {
        this.isInlay = isInlay;
    }

    public String getCustomerUrl() {
        return customerUrl;
    }

    public void setCustomerUrl(String customerUrl) {
        this.customerUrl = customerUrl;
    }
}