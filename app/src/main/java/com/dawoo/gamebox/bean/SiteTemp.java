package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * 临时传值
 * Created by benson on 18-1-11.
 */

public class SiteTemp {
    private boolean level;
    private SiteApiRelation.SiteApisBean.GameListBean gameListBean;
    private SiteApiRelation.SiteApisBean siteApisBean;
    private int position;
    private List<SiteApiRelation.SiteApisBean> mSiteApisBeans;

    public SiteTemp() {
    }

    public SiteTemp(boolean level, SiteApiRelation.SiteApisBean.GameListBean gameListBean, SiteApiRelation.SiteApisBean siteApisBean, int position) {
        this.level = level;
        this.gameListBean = gameListBean;
        this.siteApisBean = siteApisBean;
        this.position = position;
    }

    public SiteTemp(boolean level, SiteApiRelation.SiteApisBean.GameListBean gameListBean, SiteApiRelation.SiteApisBean siteApisBean, int position, List<SiteApiRelation.SiteApisBean> siteApisBeans) {
        this.level = level;
        this.gameListBean = gameListBean;
        this.siteApisBean = siteApisBean;
        this.position = position;
        mSiteApisBeans = siteApisBeans;
    }

    public boolean isLevel() {
        return level;
    }

    public void setLevel(boolean level) {
        this.level = level;
    }

    public SiteApiRelation.SiteApisBean.GameListBean getGameListBean() {
        return gameListBean;
    }

    public void setGameListBean(SiteApiRelation.SiteApisBean.GameListBean gameListBean) {
        this.gameListBean = gameListBean;
    }

    public SiteApiRelation.SiteApisBean getSiteApisBean() {
        return siteApisBean;
    }

    public void setSiteApisBean(SiteApiRelation.SiteApisBean siteApisBean) {
        this.siteApisBean = siteApisBean;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<SiteApiRelation.SiteApisBean> getSiteApisBeans() {
        return mSiteApisBeans;
    }

    public void setSiteApisBeans(List<SiteApiRelation.SiteApisBean> siteApisBeans) {
        mSiteApisBeans = siteApisBeans;
    }
}
