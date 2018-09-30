package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * Created by Archar on 2018
 */
public class SiteMsgType {

    private List<StypeListBean> advisoryTypeList;

    private boolean isOpenCaptcha;

    private String captcha_value;

    public boolean isOpenCaptcha() {
        return isOpenCaptcha;
    }

    public void setOpenCaptcha(boolean openCaptcha) {
        isOpenCaptcha = openCaptcha;
    }

    public String getCaptcha_value() {
        return captcha_value;
    }

    public void setCaptcha_value(String captcha_value) {
        this.captcha_value = captcha_value;
    }

    public List<StypeListBean> getAdvisoryTypeList() {
        return advisoryTypeList;
    }

    public void setAdvisoryTypeList(List<StypeListBean> advisoryTypeList) {
        this.advisoryTypeList = advisoryTypeList;
    }

    public static class StypeListBean {
        private String advisoryType;
        private String advisoryName;

        public String getAdvisoryType() {
            return advisoryType;
        }

        public void setAdvisoryType(String advisoryType) {
            this.advisoryType = advisoryType;
        }

        public String getAdvisoryName() {
            return advisoryName;
        }

        public void setAdvisoryName(String advisoryName) {
            this.advisoryName = advisoryName;
        }
    }
}
