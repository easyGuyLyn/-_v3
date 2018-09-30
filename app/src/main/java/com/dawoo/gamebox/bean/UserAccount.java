package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * Created by benson on 18-1-3.
 */

public class UserAccount {


    /**
     * link : [{"code":"deposit","name":"存款","link":"/wallet/deposit/index.html"},{"code":"withDraw","name":"取款","link":"/wallet/withdraw/index.html"},{"code":"transfer","name":"额度转换","link":"/transfer/index.html"},{"code":"record","name":"资金记录","link":"/fund/record/index.html"},{"code":"betting","name":"投注记录","link":"/fund/betting/index.html"},{"code":"myPromo","name":"优惠记录","link":"/promo/myPromo.html"},{"code":"bankCard","name":"银行卡","link":"/bankCard/page/addCard.html"},{"code":"btc","name":"比特币钱包","link":"/bankCard/page/addBtc.html"},{"code":"gameNotice","name":"申请优惠","link":"/message/gameNotice.html?isSendMessage=true"},{"code":"securityPassword","name":"修改安全密码","link":"/passport/securityPassword/edit.html"},{"code":"loginPassword","name":"修改登录密码","link":"/my/password/editPassword.html"},{"code":"help","name":"常见问题","link":"/help/firstType.html"},{"code":"terms","name":"注册条款","link":""},{"code":"about","name":"关于我们","link":""}]
     * user : {"autoPay":true,"isBit":true,"isCash":true,"totalAssets":734,"walletBalance":0,"withdrawAmount":null,"transferAmount":null,"preferentialAmount":null,"btc":{"btcNum":"*","btcNumber":"333"},"bankcard":null,"recomdAmount":null,"username":"be****on","avatarUrl":null,"lastLoginTime":"2018-02-12 19:55:40","loginTime":null,"currency":"￥","realName":"d*d","apis":[{"balance":384,"apiId":"19","apiName":"沙巴体育","status":"maintain"},{"balance":0,"apiId":"22","apiName":"天天彩票","status":"maintain"},{"balance":0,"apiId":"6","apiName":"PT","status":"maintain"},{"balance":0,"apiId":"20","apiName":"BSG电子","status":"maintain"},{"balance":0,"apiId":"3","apiName":"MG","status":"maintain"},{"balance":0,"apiId":"15","apiName":"HABA","status":"maintain"},{"balance":0,"apiId":"7","apiName":"OG","status":"maintain"},{"balance":0,"apiId":"5","apiName":"GD","status":"maintain"},{"balance":0,"apiId":"1","apiName":"DS","status":"maintain"},{"balance":0,"apiId":"12","apiName":"皇冠","status":"maintain"},{"balance":0,"apiId":"34","apiName":"KY棋牌","status":"maintain"},{"balance":0,"apiId":"21","apiName":"新皇冠体育","status":"maintain"},{"balance":0,"apiId":"23","apiName":"欧普斯体育","status":"maintain"},{"balance":0,"apiId":"25","apiName":"新霸电子","status":"maintain"},{"balance":0,"apiId":"28","apiName":"GG捕鱼","status":"maintain"},{"balance":0,"apiId":"10","apiName":"BBIN游戏大厅","status":"maintain"},{"balance":0,"apiId":"16","apiName":"EBET","status":"maintain"},{"balance":0,"apiId":"2","apiName":"KG","status":"maintain"},{"balance":0,"apiId":"9","apiName":"AG","status":"maintain"},{"balance":0,"apiId":"17","apiName":"SG","status":"maintain"},{"balance":0,"apiId":"24","apiName":"欧普斯真人","status":"maintain"},{"balance":0,"apiId":"27","apiName":"DT电子","status":"maintain"},{"balance":0,"apiId":"26","apiName":"PNG电子","status":"maintain"},{"balance":0,"apiId":"30","apiName":"SC电子","status":"maintain"},{"balance":0,"apiId":"31","apiName":"GNS电子","status":"maintain"},{"balance":0,"apiId":"32","apiName":"PP电子","status":"maintain"},{"balance":0,"apiId":"35","apiName":"MW电子","status":"maintain"},{"balance":0,"apiId":"36","apiName":"YSB体育","status":"maintain"}]}
     * bankList : [{"text":"中国工商银行","value":"icbc"},{"text":"中国建设银行","value":"ccb"},{"text":"中国农业银行","value":"abc"},{"text":"中国银行","value":"boc"},{"text":"招商银行 ","value":"cmb"},{"text":"中国民生银行","value":"cmbc"},{"text":"中国光大银行","value":"ceb"},{"text":"兴业银行","value":"cib"},{"text":"上海浦东发展银行","value":"spdb"},{"text":"交通银行","value":"comm"},{"text":"平安银行","value":"spabank"},{"text":"北京银行","value":"bjbank"},{"text":"中信银行","value":"citic"},{"text":"广东发展银行","value":"gdb"},{"text":"中国邮政储蓄银行","value":"psbc"},{"text":"江苏银行","value":"jsbank"},{"text":"华夏银行","value":"hxbank"},{"text":"上海银行","value":"shbank"},{"text":"渤海银行","value":"bohaib"},{"text":"东亚银行","value":"hkbea"},{"text":"宁波银行","value":"nbbank"},{"text":"浙商银行","value":"czbank"},{"text":"杭州银行","value":"hzcb"},{"text":"广州银行","value":"gcb"},{"text":"福建农商银行","value":"fjnx"},{"text":"恒生银行","value":"hangseng"},{"text":"台州银行","value":"tzb"},{"text":" 其它银行","value":"other_bank"}]
     */

    private UserBean user;
    private List<LinkBean> link;
    private List<BankListBean> bankList;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<LinkBean> getLink() {
        return link;
    }

    public void setLink(List<LinkBean> link) {
        this.link = link;
    }

    public List<BankListBean> getBankList() {
        return bankList;
    }

    public void setBankList(List<BankListBean> bankList) {
        this.bankList = bankList;
    }

    public static class UserBean {
        /**
         * autoPay : true
         * isBit : true
         * isCash : true
         * totalAssets : 734.0
         * walletBalance : 0.0
         * withdrawAmount : null
         * transferAmount : null
         * preferentialAmount : null
         * btc : {"btcNum":"*","btcNumber":"333"}
         * bankcard : null
         * recomdAmount : null
         * username : be****on
         * avatarUrl : null
         * lastLoginTime : 2018-02-12 19:55:40
         * loginTime : null
         * currency : ￥
         * realName : d*d
         * uncoverRealName : ddd
         * apis : [{"balance":384,"apiId":"19","apiName":"沙巴体育","status":"maintain"},{"balance":0,"apiId":"22","apiName":"天天彩票","status":"maintain"},{"balance":0,"apiId":"6","apiName":"PT","status":"maintain"},{"balance":0,"apiId":"20","apiName":"BSG电子","status":"maintain"},{"balance":0,"apiId":"3","apiName":"MG","status":"maintain"},{"balance":0,"apiId":"15","apiName":"HABA","status":"maintain"},{"balance":0,"apiId":"7","apiName":"OG","status":"maintain"},{"balance":0,"apiId":"5","apiName":"GD","status":"maintain"},{"balance":0,"apiId":"1","apiName":"DS","status":"maintain"},{"balance":0,"apiId":"12","apiName":"皇冠","status":"maintain"},{"balance":0,"apiId":"34","apiName":"KY棋牌","status":"maintain"},{"balance":0,"apiId":"21","apiName":"新皇冠体育","status":"maintain"},{"balance":0,"apiId":"23","apiName":"欧普斯体育","status":"maintain"},{"balance":0,"apiId":"25","apiName":"新霸电子","status":"maintain"},{"balance":0,"apiId":"28","apiName":"GG捕鱼","status":"maintain"},{"balance":0,"apiId":"10","apiName":"BBIN游戏大厅","status":"maintain"},{"balance":0,"apiId":"16","apiName":"EBET","status":"maintain"},{"balance":0,"apiId":"2","apiName":"KG","status":"maintain"},{"balance":0,"apiId":"9","apiName":"AG","status":"maintain"},{"balance":0,"apiId":"17","apiName":"SG","status":"maintain"},{"balance":0,"apiId":"24","apiName":"欧普斯真人","status":"maintain"},{"balance":0,"apiId":"27","apiName":"DT电子","status":"maintain"},{"balance":0,"apiId":"26","apiName":"PNG电子","status":"maintain"},{"balance":0,"apiId":"30","apiName":"SC电子","status":"maintain"},{"balance":0,"apiId":"31","apiName":"GNS电子","status":"maintain"},{"balance":0,"apiId":"32","apiName":"PP电子","status":"maintain"},{"balance":0,"apiId":"35","apiName":"MW电子","status":"maintain"},{"balance":0,"apiId":"36","apiName":"YSB体育","status":"maintain"}]
         */

        private boolean autoPay;
        private boolean isBit;
        private boolean isCash;
        private double totalAssets;
        private double walletBalance;
        private double withdrawAmount;
        private String transferAmount;
        private String preferentialAmount;
        private BtcBean btc;
        private Object bankcard;
        private Object recomdAmount;
        private String username;
        private Object avatarUrl;
        private String lastLoginTime;
        private Object loginTime;
        private String currency;
        private String realName;
        private String uncoverRealName;
        private List<ApisBean> apis;

        public boolean isAutoPay() {
            return autoPay;
        }

        public void setAutoPay(boolean autoPay) {
            this.autoPay = autoPay;
        }

        public boolean isIsBit() {
            return isBit;
        }

        public void setIsBit(boolean isBit) {
            this.isBit = isBit;
        }

        public boolean isIsCash() {
            return isCash;
        }

        public void setIsCash(boolean isCash) {
            this.isCash = isCash;
        }

        public double getTotalAssets() {
            return totalAssets;
        }

        public void setTotalAssets(double totalAssets) {
            this.totalAssets = totalAssets;
        }

        public double getWalletBalance() {
            return walletBalance;
        }

        public void setWalletBalance(double walletBalance) {
            this.walletBalance = walletBalance;
        }

        public double getWithdrawAmount() {
            return withdrawAmount;
        }

        public void setWithdrawAmount(double withdrawAmount) {
            this.withdrawAmount = withdrawAmount;
        }

        public String getTransferAmount() {
            return transferAmount;
        }

        public void setTransferAmount(String transferAmount) {
            this.transferAmount = transferAmount;
        }

        public String getPreferentialAmount() {
            return preferentialAmount;
        }

        public void setPreferentialAmount(String preferentialAmount) {
            this.preferentialAmount = preferentialAmount;
        }

        public BtcBean getBtc() {
            return btc;
        }

        public void setBtc(BtcBean btc) {
            this.btc = btc;
        }

        public Object getBankcard() {
            return bankcard;
        }

        public void setBankcard(Object bankcard) {
            this.bankcard = bankcard;
        }

        public Object getRecomdAmount() {
            return recomdAmount;
        }

        public void setRecomdAmount(Object recomdAmount) {
            this.recomdAmount = recomdAmount;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Object getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(Object avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public Object getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(Object loginTime) {
            this.loginTime = loginTime;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public boolean isBit() {
            return isBit;
        }

        public void setBit(boolean bit) {
            isBit = bit;
        }

        public boolean isCash() {
            return isCash;
        }

        public void setCash(boolean cash) {
            isCash = cash;
        }

        public String getUncoverRealName() {
            return uncoverRealName;
        }

        public void setUncoverRealName(String uncoverRealName) {
            this.uncoverRealName = uncoverRealName;
        }

        public List<ApisBean> getApis() {
            return apis;
        }

        public void setApis(List<ApisBean> apis) {
            this.apis = apis;
        }

        public static class BtcBean {
            /**
             * btcNum : *
             * btcNumber : 333
             */

            private String btcNum;
            private String btcNumber;

            public String getBtcNum() {
                return btcNum;
            }

            public void setBtcNum(String btcNum) {
                this.btcNum = btcNum;
            }

            public String getBtcNumber() {
                return btcNumber;
            }

            public void setBtcNumber(String btcNumber) {
                this.btcNumber = btcNumber;
            }
        }

        public static class ApisBean {
            /**
             * balance : 384.0
             * apiId : 19
             * apiName : 沙巴体育
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

    public static class LinkBean {
        /**
         * code : deposit
         * name : 存款
         * link : /wallet/deposit/index.html
         */

        private String code;
        private String name;
        private String link;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public static class BankListBean {
        /**
         * text : 中国工商银行
         * value : icbc
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
