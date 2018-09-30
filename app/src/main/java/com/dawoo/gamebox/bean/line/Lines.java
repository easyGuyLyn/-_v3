package com.dawoo.gamebox.bean.line;

import java.util.List;

/**
 * Created by benson on 18-4-17.
 */

public class Lines {
    /**
     * domain : 71.hongtubet.com
     * ips : ["47.90.51.75"]
     */

    private String domain;
    private List<String> ips;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }
}
