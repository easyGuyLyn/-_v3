package com.dawoo.gamebox.bean.line;

/**
 * 检查线路日志
 * Created by benson on 18-1-4.
 */

public class CheckLog {
    int status; // 0 正在检测，1检测失败，2检测成功, 3获取线路失败, 4获取线路成功, 5停止获取线路
    String logStr;
    String code;

    public CheckLog() {
    }

    public CheckLog(int status, String logStr, String code) {
        this.status = status;
        this.logStr = logStr;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLogStr() {
        return logStr;
    }

    public void setLogStr(String logStr) {
        this.logStr = logStr;
    }
}
