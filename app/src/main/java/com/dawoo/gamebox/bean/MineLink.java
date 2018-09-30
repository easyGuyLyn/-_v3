package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * 我的页面接口
 * <p>
 * Created by benson on 18-1-4.
 */

public class MineLink {
    /**
     * isCash : true
     * link : [{"code":"deposit","name":"存款","link":"/wallet/deposit/index.html"},{"code":"withdraw","name":"取款","link":"/wallet/withdraw/index.html"},{"code":"transfer","name":"额度转换","link":"/transfer/index.html"},{"code":"record","name":"资金记录","link":"/fund/record/index.html"},{"code":"betting","name":"投注记录","link":"/fund/betting/index.html"},{"code":"myPromo","name":"优惠记录","link":"/promo/myPromo.html"},{"code":"bankCard","name":"银行卡","link":"/bankCard/page/addCard.html"},{"code":"btc","name":"比特币钱包","link":"/bankCard/page/addBtc.html"},{"code":"gameNotice","name":"申请优惠","link":"/message/gameNotice.html?isSendMessage=true"},{"code":"securityPassword","name":"修改安全密码","link":"/passport/securityPassword/edit.html"},{"code":"loginPassword","name":"修改登录密码","link":"/my/password/editPassword.html"}]
     * user : {"preferentialAmount":null,"lastLoginTime":"2018-01-04 16:52:45","totalAssets":0,"walletBalance":0,"avatarUrl":null,"transferAmount":0,"recomdAmount":null,"currency":"￥","withdrawAmount":0,"unReadCount":0,"btcNum":"*333","username":"be****on"}
     * isBit : true
     */

    private boolean isCash;
    private UserBean user;
    private boolean isBit;
    private List<LinkBean> link;

    public boolean isIsCash() {
        return isCash;
    }

    public void setIsCash(boolean isCash) {
        this.isCash = isCash;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public boolean isIsBit() {
        return isBit;
    }

    public void setIsBit(boolean isBit) {
        this.isBit = isBit;
    }

    public List<LinkBean> getLink() {
        return link;
    }

    public void setLink(List<LinkBean> link) {
        this.link = link;
    }

    public static class UserBean {
        /**
         * preferentialAmount : null
         * lastLoginTime : 2018-01-04 16:52:45
         * totalAssets : 0.0
         * walletBalance : 0.0
         * avatarUrl : null
         * transferAmount : 0.0
         * recomdAmount : null
         * currency : ￥
         * withdrawAmount : 0.0
         * unReadCount : 0
         * btcNum : *333
         * username : be****on
         */

        private Object preferentialAmount;
        private String lastLoginTime;
        private double totalAssets;
        private double walletBalance;
        private Object avatarUrl;
        private double transferAmount;
        private Object recomdAmount;
        private String currency;
        private double withdrawAmount;
        private int unReadCount;
        private String btcNum;
        private String username;

        public Object getPreferentialAmount() {
            return preferentialAmount;
        }

        public void setPreferentialAmount(Object preferentialAmount) {
            this.preferentialAmount = preferentialAmount;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
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

        public Object getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(Object avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public double getTransferAmount() {
            return transferAmount;
        }

        public void setTransferAmount(double transferAmount) {
            this.transferAmount = transferAmount;
        }

        public Object getRecomdAmount() {
            return recomdAmount;
        }

        public void setRecomdAmount(Object recomdAmount) {
            this.recomdAmount = recomdAmount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public double getWithdrawAmount() {
            return withdrawAmount;
        }

        public void setWithdrawAmount(double withdrawAmount) {
            this.withdrawAmount = withdrawAmount;
        }

        public int getUnReadCount() {
            return unReadCount;
        }

        public void setUnReadCount(int unReadCount) {
            this.unReadCount = unReadCount;
        }

        public String getBtcNum() {
            return btcNum;
        }

        public void setBtcNum(String btcNum) {
            this.btcNum = btcNum;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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
}
