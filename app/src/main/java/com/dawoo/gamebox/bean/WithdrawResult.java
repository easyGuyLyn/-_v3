package com.dawoo.gamebox.bean;

/**
 * Created by b on 18-1-16.
 */

public class WithdrawResult {
    /**
     * error : 1
     * code : 102
     * msg : 取款金额最少为50.00元
     * data : null
     * version : app_01
     */

    private int error;
    private int code;
    private String message;
    private Withdraw data;
    private String version;

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
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public Withdraw getData() {
        return data;
    }

    public void setData(Withdraw data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
