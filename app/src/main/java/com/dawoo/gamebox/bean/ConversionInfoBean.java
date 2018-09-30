package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * Created by b on 18-3-26.
 */

public class ConversionInfoBean {

    /**
     * select : [{"text":"我的钱包","value":"wallet"},{"text":"新霸电子5","value":25},{"text":"BBIN游戏大厅","value":10},{"text":"DS贵宾厅4","value":1},{"text":"EBET-8","value":16},{"text":"GD豪华厅3","value":5},{"text":"HB电子6","value":15},{"text":"KG彩票","value":2},{"text":"OG东方厅5","value":7},{"text":"PT电子2","value":6},{"text":"沙巴体育","value":19},{"text":"皇冠体育","value":12},{"text":"AG","value":9},{"text":"SA-9","value":17},{"text":"MG","value":3},{"text":"欧普斯真人7","value":24},{"text":"欧普斯体育","value":23},{"text":"DT电子14","value":27},{"text":"GG捕鱼12","value":28},{"text":"PNG电子11","value":26},{"text":"SC电子13","value":30},{"text":"皇家体育","value":21},{"text":"天天彩票","value":22},{"text":"MW电子8","value":35},{"text":"YSB体育","value":36},{"text":"BC体育","value":37},{"text":"KY棋牌","value":34},{"text":"PP电子","value":38}]
     * transferPendingAmount : 0
     * currency : ￥
     * token : 634486ed-10ae-4062-a334-f1c2c8a7a8da
     */
    private boolean autoPay; //是否为免转
    private double transferPendingAmount;
    private String currency;
    private String token;
    private List<SelectBean> select;

    public double getTransferPendingAmount() {
        return transferPendingAmount;
    }

    public void setTransferPendingAmount(double transferPendingAmount) {
        this.transferPendingAmount = transferPendingAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<SelectBean> getSelect() {
        return select;
    }

    public void setSelect(List<SelectBean> select) {
        this.select = select;
    }

    public boolean isAutoPay() {
        return autoPay;
    }

    public void setAutoPay(boolean autoPay) {
        this.autoPay = autoPay;
    }

    public static class SelectBean {
        /**
         * text : 我的钱包
         * value : wallet
         */

        private String text;
        private String value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
