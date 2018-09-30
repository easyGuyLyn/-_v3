package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * Created by benson on 18-1-17.
 */

public class SiteMyMsgDetailList {
    private int error;
    private int code;
    private String message;
    private List<SiteMyMsgDetail> data;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SiteMyMsgDetail> getData() {
        return data;
    }

    public void setData(List<SiteMyMsgDetail> data) {
        this.data = data;
    }
}
