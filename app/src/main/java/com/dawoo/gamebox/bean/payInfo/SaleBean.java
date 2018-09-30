package com.dawoo.gamebox.bean.payInfo;

import java.io.Serializable;

/**
 * Created by rain on 18-3-25.
 */

public class SaleBean implements Serializable {
    /* 　
    {
        "id": 246,　　　　　　　／／活动ｉｄ
        "preferential": true,　　／／是否可参与该活动
        "activityName": "公司入款100%贴心回馈"　　／／活动名称
    },*/
    private boolean preferential;
    private long id=0;
    private String activityName;

    public boolean isPreferential() {
        return preferential;
    }

    public void setPreferential(boolean preferential) {
        this.preferential = preferential;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
