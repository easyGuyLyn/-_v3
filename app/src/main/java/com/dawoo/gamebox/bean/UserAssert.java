package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * 用户资产
 * Created by benson on 18-2-25.
 */

public class UserAssert {
    /**
     * apis : [{"balance":1114.25,"apiId":"22","apiName":"天天彩票","status":"maintain"},{"balance":0,"apiId":"6","apiName":"PT","status":"maintain"},{"balance":0,"apiId":"20","apiName":"BSG电子","status":"maintain"},{"balance":0,"apiId":"33","apiName":"申博","status":"maintain"},{"balance":0,"apiId":"3","apiName":"MG","status":"maintain"},{"balance":0,"apiId":"15","apiName":"HABA","status":"maintain"},{"balance":0,"apiId":"7","apiName":"OG","status":"maintain"},{"balance":0,"apiId":"5","apiName":"GD","status":"maintain"},{"balance":0,"apiId":"1","apiName":"DS","status":"maintain"},{"balance":0,"apiId":"12","apiName":"皇冠","status":"maintain"},{"balance":0,"apiId":"34","apiName":"KY棋牌","status":"maintain"},{"balance":0,"apiId":"21","apiName":"新皇冠体育","status":"maintain"},{"balance":0,"apiId":"23","apiName":"欧普斯体育","status":"maintain"},{"balance":0,"apiId":"25","apiName":"新霸电子","status":"maintain"},{"balance":0,"apiId":"28","apiName":"GG捕鱼","status":"maintain"},{"balance":0,"apiId":"19","apiName":"沙巴体育","status":"maintain"},{"balance":0,"apiId":"9","apiName":"AG","status":"maintain"},{"balance":0,"apiId":"35","apiName":"MW电子","status":"maintain"},{"balance":0,"apiId":"36","apiName":"YSB体育","status":"maintain"},{"balance":0,"apiId":"10","apiName":"BBIN游戏大厅","status":"maintain"},{"balance":0,"apiId":"16","apiName":"EBET","status":"maintain"},{"balance":0,"apiId":"2","apiName":"KG","status":"maintain"},{"balance":0,"apiId":"17","apiName":"SG","status":"maintain"},{"balance":0,"apiId":"24","apiName":"欧普斯真人","status":"maintain"},{"balance":0,"apiId":"27","apiName":"DT电子","status":"maintain"},{"balance":0,"apiId":"26","apiName":"PNG电子","status":"maintain"},{"balance":0,"apiId":"30","apiName":"SC电子","status":"maintain"},{"balance":0,"apiId":"31","apiName":"GNS电子","status":"maintain"},{"balance":0,"apiId":"32","apiName":"PP电子","status":"maintain"}]
     * username : benson
     * currSign : ￥
     * balance : 0.0
     * assets : 1464.25
     */

    private String username;
    private String currSign;
    private double balance;
    private double assets;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAssets() {
        return assets;
    }

    public void setAssets(double assets) {
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
         * balance : 1114.25
         * apiId : 22
         * apiName : 天天彩票
         * status : maintain
         */

        private double balance;
        private String apiId;
        private String apiName;
        private String status;

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

        public String getApiName() {
            return apiName;
        }

        public void setApiName(String apiName) {
            this.apiName = apiName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
