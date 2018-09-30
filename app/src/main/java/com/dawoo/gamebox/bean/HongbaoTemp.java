package com.dawoo.gamebox.bean;

/**
 * 打开红包临时传值
 * Created by benson on 18-1-11.
 */

public class HongbaoTemp {
    private String activityMessageId;
    private String token;

    public HongbaoTemp() {
    }

    public HongbaoTemp(String activityMessageId, String token) {
        this.activityMessageId = activityMessageId;
        this.token = token;
    }

    public String getActivityMessageId() {
        return activityMessageId;
    }

    public void setActivityMessageId(String activityMessageId) {
        this.activityMessageId = activityMessageId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
