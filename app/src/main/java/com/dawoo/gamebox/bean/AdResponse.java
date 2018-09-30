package com.dawoo.gamebox.bean;

/**
 * archar  天纵神武
 **/
public class AdResponse {

    private boolean success;
    private int code;
    private String message;
    private String version;
    private AdPicUrl data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public AdPicUrl getData() {
        return data;
    }

    public void setData(AdPicUrl data) {
        this.data = data;
    }

    public static class AdPicUrl {
        private String initAppAd;

        public String getInitAppAd() {
            return initAppAd;
        }

        public void setInitAppAd(String initAppAd) {
            this.initAppAd = initAppAd;
        }
    }

}
