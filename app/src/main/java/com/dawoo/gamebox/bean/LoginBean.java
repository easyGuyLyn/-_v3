package com.dawoo.gamebox.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by benson on 17-12-29.
 */

public class LoginBean {
    /**
     * success : true
     * message : null
     * username : null
     * password : null
     * isOpenCaptcha : false
     * propMessages : {}
     */

    private boolean success;
    private Object message;
    private Object username;
    private Object password;
    private boolean isOpenCaptcha;
    private PropMessagesBean propMessages;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public boolean isIsOpenCaptcha() {
        return isOpenCaptcha;
    }

    public void setIsOpenCaptcha(boolean isOpenCaptcha) {
        this.isOpenCaptcha = isOpenCaptcha;
    }

    public PropMessagesBean getPropMessages() {
        return propMessages;
    }

    public void setPropMessages(PropMessagesBean propMessages) {
        this.propMessages = propMessages;
    }

    public static class PropMessagesBean {

        private String captcha;
        private String location;
        @SerializedName("gb.token")
        private String gbToken;

        public String getGbToken() {
            return gbToken;
        }

        public void setGbToken(String gbToken) {
            this.gbToken = gbToken;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }


        public String getCaptcha() {
            return captcha;
        }

        public void setCaptcha(String captcha) {
            this.captcha = captcha;
        }
    }
}
