package com.dawoo.gamebox.bean;

import com.dawoo.gamebox.net.HttpResult;

/**
 * Created by b on 18-3-27.
 */

public class QuotaConversionBean{

    /**
     * msg : 游戏接口繁忙，请稍后再试。
     * state : false
     * result: 0
     * token : 8b7ceb86-7671-4a66-8331-2b2bbf40d43a
     * orderId : ssss
     */
    private boolean success;
    private int code;
    private String message;
    private String version;
    DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{
    private String msg;
    private boolean state;
    private int result;
    private String apiId;
    private String token;
    private String orderId;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    }
}
