package com.dawoo.gamebox.bean;

/**
 * Created by Archar on 2018
 */
public class ResetSecurityPwd {
    private int error;
    private String code;
    private String message;
    private UpdateLoginData data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public UpdateLoginData getData() {
        return data;
    }

    public void setData(UpdateLoginData data) {
        this.data = data;
    }

    public static class UpdateLoginData {
        private String isOpenCaptcha;//是否需要开启验证码
        private int remindTimes;
        private Boolean hasRealName;// 是否有真实姓名
        private Boolean hasPermissionPwd;//是否有安全码
        private String lockTime;//冻结时间
        private String captChaUrl;
        private String captcha_value;

        public String getCaptcha_value() {
            return captcha_value;
        }

        public void setCaptcha_value(String captcha_value) {
            this.captcha_value = captcha_value;
        }

        public String getCaptChaUrl() {
            return captChaUrl;
        }

        public void setCaptChaUrl(String captChaUrl) {
            this.captChaUrl = captChaUrl;
        }

        public String getIsOpenCaptcha() {
            return isOpenCaptcha;
        }

        public void setIsOpenCaptcha(String isOpenCaptcha) {
            this.isOpenCaptcha = isOpenCaptcha;
        }

        public int getRemindTimes() {
            return remindTimes;
        }

        public void setRemindTimes(int remindTimes) {
            this.remindTimes = remindTimes;
        }

        public Boolean getHasRealName() {
            return hasRealName;
        }

        public void setHasRealName(Boolean hasRealName) {
            this.hasRealName = hasRealName;
        }

        public Boolean getHasPermissionPwd() {
            return hasPermissionPwd;
        }

        public void setHasPermissionPwd(Boolean hasPermissionPwd) {
            this.hasPermissionPwd = hasPermissionPwd;
        }

        public String getLockTime() {
            return lockTime;
        }

        public void setLockTime(String lockTime) {
            this.lockTime = lockTime;
        }
    }
}
