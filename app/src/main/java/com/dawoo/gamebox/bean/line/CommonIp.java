package com.dawoo.gamebox.bean.line;

/**
 * archar  天纵神武
 **/
public class CommonIp {

    /**
     * ip : 61.28.172.6
     * ip_decimal : 1025289222
     * country : Philippines
     * country_iso : PH
     * city : Makati City
     * hostname : 6.172.28.61.unassigned.static.eastern-tele.com
     */

    private String ip;
    private int ip_decimal;
    private String country;
    private String country_iso;
    private String city;
    private String hostname;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getIp_decimal() {
        return ip_decimal;
    }

    public void setIp_decimal(int ip_decimal) {
        this.ip_decimal = ip_decimal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_iso() {
        return country_iso;
    }

    public void setCountry_iso(String country_iso) {
        this.country_iso = country_iso;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
