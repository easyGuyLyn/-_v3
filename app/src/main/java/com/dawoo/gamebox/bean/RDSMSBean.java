package com.dawoo.gamebox.bean;

/**
 * Created by alex on 18-4-3.
 */

public class RDSMSBean {

    /**
     * success : true
     * code : 0
     * title : null
     * message : 请求成功
     * data : null
     * version : app_01
     */

    private boolean success;
    private String code;
    private Object title;
    private String message;
    private Object data;
    private String version;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
