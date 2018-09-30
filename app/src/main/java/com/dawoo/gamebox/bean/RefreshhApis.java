package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * Created by benson on 18-1-16.
 */

public class RefreshhApis {
    /**
     * apis : [{"apiName":"新霸电子","balance":0,"apiId":"25","status":"normal"},{"apiName":"BBIN游戏大厅","balance":0,"apiId":"10","status":"normal"},{"apiName":"DS贵宾厅1","balance":0,"apiId":"1","status":"normal"},{"apiName":"GD豪华厅1","balance":0,"apiId":"5","status":"normal"},{"apiName":"HABA电子11","balance":0,"apiId":"15","status":"normal"},{"apiName":"IM体育","balance":0,"apiId":"4","status":"normal"},{"apiName":"KG彩票112","balance":0,"apiId":"2","status":"normal"},{"apiName":"OG东方厅1","balance":0,"apiId":"7","status":"normal"},{"apiName":"PT电子11","balance":0,"apiId":"6","status":"normal"},{"apiName":"皇冠体育11","balance":0,"apiId":"12","status":"normal"},{"apiName":"AG","balance":0,"apiId":"9","status":"normal"},{"apiName":"MG","balance":0,"apiId":"3","status":"normal"},{"apiName":"BSG电子","balance":0,"apiId":"20","status":"normal"},{"apiName":"GNS电子","balance":0,"apiId":"31","status":"normal"},{"apiName":"PP电子","balance":0,"apiId":"32","status":"normal"},{"apiName":"新皇冠体育","balance":0,"apiId":"21","status":"normal"},{"apiName":"天天彩票","balance":0,"apiId":"22","status":"normal"},{"apiName":"申博","balance":0,"apiId":"33","status":"normal"},{"apiName":"开元","balance":0,"apiId":"34","status":"normal"},{"apiName":"MW电子","balance":0,"apiId":"35","status":"normal"}]
     * username : benson
     * currSign : ￥
     * balance : null
     * assets : 1,758.00
     */

    private String username;
    private String currSign;
    private String balance;
    private String assets;
    private List<ApisBean> apis;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrSign() {
        return currSign;
    }

    public void setCurrSign(String currSign) {
        this.currSign = currSign;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public List<ApisBean> getApis() {
        return apis;
    }

    public void setApis(List<ApisBean> apis) {
        this.apis = apis;
    }

    public static class ApisBean {
        /**
         * apiName : 新霸电子
         * balance : 0.0
         * apiId : 25
         * status : normal
         */

        private String apiName;
        private double balance;
        private String apiId;
        private String status;

        public String getApiName() {
            return apiName;
        }

        public void setApiName(String apiName) {
            this.apiName = apiName;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getApiId() {
            return apiId;
        }

        public void setApiId(String apiId) {
            this.apiId = apiId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
