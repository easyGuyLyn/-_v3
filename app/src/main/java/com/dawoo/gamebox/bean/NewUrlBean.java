package com.dawoo.gamebox.bean;

/**
 * Created by benson on 18-2-28.
 */

public class NewUrlBean {

    private boolean success;
    private int code;
    private String message;
    private String version;
    private UrlBean data;
    private boolean is_native;

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

    public UrlBean getData() {
        return data;
    }

    public void setData(UrlBean data) {
        this.data = data;
    }

    public boolean isIs_native() {
        return is_native;
    }

    public void setIs_native(boolean is_native) {
        this.is_native = is_native;
    }
}
