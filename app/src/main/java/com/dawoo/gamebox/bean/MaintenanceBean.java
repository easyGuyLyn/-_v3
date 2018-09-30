package com.dawoo.gamebox.bean;

public class MaintenanceBean {

    /**
     * mobileCustomerServiceUrl : https://www11.53kf.com/webCompany.php?arg=10158799&kf_sign=TQzNjMTUyNc0MTEwNTYzNzE5OTAxMDA5&style=1
     * pcCustomerServiceUrl : https://www11.53kf.com/webCompany.php?arg=10158799&kf_sign=TQzNjMTUyNc0MTEwNTYzNzE5OTAxMDA5&style=1
     * logoUrl : gb/567/Logo/1/1525432662151.png
     * flashLogoUrl :
     */

    private String mobileCustomerServiceUrl;
    private String pcCustomerServiceUrl;
    private String logoUrl;
    private String flashLogoUrl;
    private String contentText;

    public String getMobileCustomerServiceUrl() {
        return mobileCustomerServiceUrl;
    }

    public void setMobileCustomerServiceUrl(String mobileCustomerServiceUrl) {
        this.mobileCustomerServiceUrl = mobileCustomerServiceUrl;
    }

    public String getPcCustomerServiceUrl() {
        return pcCustomerServiceUrl;
    }

    public void setPcCustomerServiceUrl(String pcCustomerServiceUrl) {
        this.pcCustomerServiceUrl = pcCustomerServiceUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getFlashLogoUrl() {
        return flashLogoUrl;
    }

    public void setFlashLogoUrl(String flashLogoUrl) {
        this.flashLogoUrl = flashLogoUrl;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
}
