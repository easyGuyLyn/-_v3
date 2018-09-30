package com.dawoo.gamebox.bean;

/**
 * Created by Archar on 2018
 */
public class SiteMsgUnReadCount {
    private int sysMessageUnReadCount;
    private int advisoryUnReadCount;

    public int getSysMessageUnReadCount() {
        return sysMessageUnReadCount;
    }

    public void setSysMessageUnReadCount(int sysMessageUnReadCount) {
        this.sysMessageUnReadCount = sysMessageUnReadCount;
    }

    public int getAdvisoryUnReadCount() {
        return advisoryUnReadCount;
    }

    public void setAdvisoryUnReadCount(int advisoryUnReadCount) {
        this.advisoryUnReadCount = advisoryUnReadCount;
    }
}
