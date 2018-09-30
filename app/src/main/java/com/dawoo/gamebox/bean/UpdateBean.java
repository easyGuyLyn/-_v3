package com.dawoo.gamebox.bean;

import java.io.Serializable;

/**
 * archar  天纵神武
 **/
public class UpdateBean implements Serializable {

    /**
     * id : 86
     * appName : 21站点不能修改
     * appType : android
     * versionCode : 501
     * forceVersion : 501
     * siteId : 21
     * versionName : 4.0.0
     * appUrl : /rcenter/android/
     * memo : 1）
     * 2）
     * updateTime : 1520773157509
     * md5 :
     */

    private int id;
    private String appName;
    private String appType;
    private int versionCode = 0;
    private int forceVersion = 0;
    private int siteId;
    private String versionName;
    private String appUrl;
    private String memo;
    private long updateTime;
    private String md5;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getForceVersion() {
        return forceVersion;
    }

    public void setForceVersion(int forceVersion) {
        this.forceVersion = forceVersion;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
