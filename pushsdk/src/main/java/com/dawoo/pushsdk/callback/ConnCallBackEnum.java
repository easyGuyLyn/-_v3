package com.dawoo.pushsdk.callback;

/**
 * archar
 */
public enum ConnCallBackEnum {

    connected(1, "WebSocket已连接"),

    logining(2, "WebSocket正在连接中..."),

    logout(3, "WebSocket已登出");


    private int code;
    private String status;

    ConnCallBackEnum(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
