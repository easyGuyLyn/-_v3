package com.dawoo.gamebox.bean;

import com.dawoo.gamebox.BuildConfig;
import com.dawoo.gamebox.util.NetUtil;

/**
 * 数据中心
 * Created by benson on 18-1-26.
 */

public class DataCenter {
    private static DataCenter instance;
    private static User mUser = new User();
    private static SysInfo mSysInfo = new SysInfo();

    public static DataCenter getInstance() {
        if (instance == null) {
            synchronized (DataCenter.class) {
                if (instance == null) {
                    instance = new DataCenter();
                }
            }
        }
        return instance;
    }


    public User getUser() {
        return mUser;
    }

    public String getDomain() {
        String domain = mUser.getDomain();
        if (!BuildConfig.IPConnection) {
            domain = NetUtil.leftOutHttpFromDomain(domain);
        }
        return domain;
    }

    public void setDomain(String domain) {
        mUser.setDomain(domain);
    }

    public synchronized String getLTDomain() {
        String domain = mUser.getLt_domain();
        if (!BuildConfig.IPConnection) {
            domain = NetUtil.leftOutHttpFromDomain(domain);
        }
        return domain;
    }

    public synchronized void setLTDomain(String domain) {
        mUser.setLt_domain(domain);
    }

    public void setIp(String ip) {
        mUser.setIp(ip);
    }

    public String getIp() {
        return mUser.getIp();
    }

    public String getCookie() {
        return mUser.getCookie();
    }

    public void setCookie(String cookie) {
        mUser.setCookie(cookie);
    }

    public boolean isLogin() {
        return mUser.isLogin();
    }

    public void setLogin(boolean isLogin) {
        mUser.setLogin(isLogin);
    }


    public String getUserName() {
        return mUser.getUsername();
    }

    public void setUserName(String userName) {
        mUser.setUsername(userName);
    }

    public String getPassword() {
        return mUser.getPassword();
    }

    public void setPassword(String password) {
        mUser.setPassword(password);
    }

    public String getRealName() {
        return mUser.getRealName();
    }

    public void setRealName(String realName) {
        mUser.setRealName(realName);
    }


    public SysInfo getSysInfo() {
        return mSysInfo;
    }


}
