package com.dawoo.gamebox.bean;

/**
 * Created by b on 18-1-26.
 */

public class VerifyRealNameResponse {


    private String error;
    private int code;
    private String message;
    private VerifyRealNameBean data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    public VerifyRealNameBean getData() {
        return data;
    }

    public void setData(VerifyRealNameBean data) {
        this.data = data;
    }


}
