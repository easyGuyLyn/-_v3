package com.dawoo.gamebox.bean;

/**
 * Created by archar on 17-12-20.
 */

public class CommonRequestResult {
    //    [{
//        "error":"0",  --->错误数量
//        "code":"",	 --->状态码
//        "message":"",    --->消息框
//        "version":"", --->版本信息
//        "data":[{"key":"value"}]	--->返回数据
//    }]
    private int error;
    private int code;
    private String message;
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}