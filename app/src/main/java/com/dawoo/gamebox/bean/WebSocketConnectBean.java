package com.dawoo.gamebox.bean;

public class WebSocketConnectBean {
    private String url;
    private String session;
    private String domain;

    public WebSocketConnectBean(String url, String session, String domain) {
        this.url = url;
        this.session = session;
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
