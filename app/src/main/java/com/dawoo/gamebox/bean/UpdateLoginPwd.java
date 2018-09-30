package com.dawoo.gamebox.bean;

/**
 * Created by Archar on 2018
 */
public class UpdateLoginPwd {
    private int error;
    private int code;
    private String msg;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UpdateLoginData getData() {
        return data;
    }

    public void setData(UpdateLoginData data) {
        this.data = data;
    }

    public static class UpdateLoginData {
        private String isOpenCaptcha;//是否需要开启验证码
        private int remainTimes;

        public String getIsOpenCaptcha() {
            return isOpenCaptcha;
        }

        public void setIsOpenCaptcha(String isOpenCaptcha) {
            this.isOpenCaptcha = isOpenCaptcha;
        }

        public int getRemainTimes() {
            return remainTimes;
        }

        public void setRemainTimes(int remainTimes) {
            this.remainTimes = remainTimes;
        }
    }
}
