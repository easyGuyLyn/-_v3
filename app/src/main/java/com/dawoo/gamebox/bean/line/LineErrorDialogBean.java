package com.dawoo.gamebox.bean.line;

/**
 * archar  天纵神武
 *
 * 当线路进不去   要输出一个dialog
 **/
public class LineErrorDialogBean {
    private String code;//协议好的code码
    private String msg;//具体的补充信息

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
