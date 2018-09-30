package com.dawoo.gamebox.bean.line;

import android.support.annotation.NonNull;

/**
 * 对应一个域名一个ip
 * 可以准入最优线路
 * Created by benson on 18-4-23.
 */

public class LineBean implements Comparable<LineBean> {
    private String domain = "";
    private String ip = "";
    private long time;
    private int state; // 状态1 失败 ，2成功

    public LineBean() {
    }

    public LineBean(String domain, String ip) {
        this.domain = domain;
        this.ip = ip;
    }

    public LineBean(String domain, String ip, long time, int state) {
        this.domain = domain;
        this.ip = ip;
        this.time = time;
        this.state = state;
    }
    public LineBean(String domain, long time, int state) {
        this.domain = domain;
        this.time = time;
        this.state = state;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    @Override
    public int compareTo(@NonNull LineBean o) {
        return (int) (this.time - o.getTime());
    }


//    @Override
//    public int compare(LineBean o1, LineBean o2) {
//        return (int) (o1.getTime() - o2.getTime());
//    }
}
