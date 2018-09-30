package com.dawoo.gamebox.bean;

/**
 * Created by benson on 17-12-21.
 */

public class User {
    /**
     * 域名线路
     */
    public String domain = "";
    /**
     * 彩票域名线路
     */
    public String lt_domain = "";
    /**
     * 域名线路ip
     */
    public String ip;
    /**
     * 协议头cookie
     */
    public String cookie = "";
    /**
     * 是否登录
     */
    public boolean isLogin;
    /**
     * 用户的登录帐户
     */
    public String username;
    /**
     * 用户的登录密码
     */
    public String password;
    /**
     * 用户没加密真实姓名
     */
    public String realName;


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLt_domain() {
        return lt_domain;
    }

    public void setLt_domain(String lt_domain) {
        this.lt_domain = lt_domain;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
