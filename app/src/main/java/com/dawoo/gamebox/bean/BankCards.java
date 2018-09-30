package com.dawoo.gamebox.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by b on 18-1-16.
 * 银行卡种类
 */

public class BankCards {

    /**
     * link : [{"code":"deposit","name":"存款","link":"/wallet/deposit/index.html"},{"code":"withDraw","name":"取款","link":"/wallet/withdraw/index.html"},{"code":"transfer","name":"额度转换","link":"/transfer/index.html"},{"code":"record","name":"资金记录","link":"/fund/record/index.html"},{"code":"betting","name":"投注记录","link":"/fund/betting/index.html"},{"code":"myPromo","name":"优惠记录","link":"/promo/myPromo.html"},{"code":"bankCard","name":"银行卡","link":"/bankCard/page/addCard.html"},{"code":"btc","name":"比特币钱包","link":"/bankCard/page/addBtc.html"},{"code":"gameNotice","name":"申请优惠","link":"/message/gameNotice.html?isSendMessage=true"},{"code":"securityPassword","name":"修改安全密码","link":"/passport/securityPassword/edit.html"},{"code":"loginPassword","name":"修改登录密码","link":"/my/password/editPassword.html"},{"code":"help","name":"常见问题","link":"/help/firstType.html"},{"code":"terms","name":"注册条款","link":""},{"code":"about","name":"关于我们","link":""}]
     * user : {"autoPay":true,"preferentialAmount":null,"totalAssets":1,"avatarUrl":null,"transferAmount":1,"btc":{"btcNumber":"333"},"lastLoginTime":"2018-01-23 12:06:42","realName":"d*d","walletBalance":0,"recomdAmount":null,"currency":"￥","bankcard":{"bankcardMasterName":"d*d","realName":"ddd","bankName":null,"bankcardNumber":"8888 8888 **** 8888","bankDeposit":"唐山"},"isCash":true,"withdrawAmount":0,"unReadCount":0,"isBit":true,"username":"be****on"}
     * userApi : {"apis":[{"apiName":"新霸电子5","balance":0,"apiId":"25","status":"normal"},{"apiName":"BBIN游戏大厅","balance":0,"apiId":"10","status":"normal"},{"apiName":"DS贵宾厅4","balance":0,"apiId":"1","status":"normal"},{"apiName":"EBET-8","balance":0,"apiId":"16","status":"normal"},{"apiName":"GD豪华厅3","balance":0,"apiId":"5","status":"normal"},{"apiName":"HB电子6","balance":0,"apiId":"15","status":"normal"},{"apiName":"KG彩票3","balance":0,"apiId":"2","status":"normal"},{"apiName":"OG东方厅5","balance":0,"apiId":"7","status":"normal"},{"apiName":"PT电子2","balance":0,"apiId":"6","status":"normal"},{"apiName":"沙巴体育","balance":0,"apiId":"19","status":"normal"},{"apiName":"皇冠体育11","balance":0,"apiId":"12","status":"normal"},{"apiName":"AG","balance":0,"apiId":"9","status":"normal"},{"apiName":"SA-9","balance":0,"apiId":"17","status":"normal"},{"apiName":"MG","balance":0,"apiId":"3","status":"normal"},{"apiName":"欧普斯真人7","balance":0,"apiId":"24","status":"normal"},{"apiName":"欧普斯体育","balance":0,"apiId":"23","status":"normal"},{"apiName":"BSG电子3","balance":0,"apiId":"20","status":"normal"},{"apiName":"DT电子14","balance":0,"apiId":"27","status":"normal"},{"apiName":"GG捕鱼12","balance":0,"apiId":"28","status":"normal"},{"apiName":"PNG电子11","balance":0,"apiId":"26","status":"normal"},{"apiName":"SC电子13","balance":0,"apiId":"30","status":"normal"},{"apiName":"GNS电子10","balance":0,"apiId":"31","status":"normal"},{"apiName":"PP电子9","balance":0,"apiId":"32","status":"normal"},{"apiName":"新皇冠体育","balance":0,"apiId":"21","status":"normal"},{"apiName":"天天彩票1","balance":0,"apiId":"22","status":"normal"},{"apiName":"开元","balance":0,"apiId":"34","status":"normal"},{"apiName":"MW电子8","balance":0,"apiId":"35","status":"normal"}],"username":"b*n","currSign":"￥","balance":"0.00","assets":"1.00"}
     * bankList : [{"text":"中国工商银行","value":"icbc"},{"text":"中国建设银行","value":"ccb"},{"text":"中国农业银行","value":"abc"},{"text":"中国银行","value":"boc"},{"text":"招商银行 ","value":"cmb"},{"text":"中国民生银行","value":"cmbc"},{"text":"中国光大银行","value":"ceb"},{"text":"兴业银行","value":"cib"},{"text":"上海浦东发展银行","value":"spdb"},{"text":"交通银行","value":"comm"},{"text":"平安银行","value":"spabank"},{"text":"北京银行","value":"czbank"},{"text":"杭州银行","value":"hzcb"},{"text":"广州银行","value":"gcb"},{"text":"福建农商银行","value":"fjnx"},{"text":"恒生银行","value":"hangseng"},{"text":"台州银行","value":"tzb"},{"text":"其它银行","value":"other_bank"}]
     */

    private UserBean user;
    private List<BankListBean> bankList;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
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
         * preferentialAmount : null
         * totalAssets : 1
         * avatarUrl : null
         * transferAmount : 1
         * btc : {"btcNumber":"333"}
         * lastLoginTime : 2018-01-23 12:06:42
         * realName : d*d
         * walletBalance : 0
         * recomdAmount : null
         * currency : ￥
         * bankcard : {"bankcardMasterName":"d*d","realName":"ddd","bankName":null,"bankcardNumber":"8888 8888 **** 8888","bankDeposit":"唐山"}
         * isCash : true
         * withdrawAmount : 0
         * unReadCount : 0
         * isBit : true
         * username : be****on
         */

        private boolean autoPay;
        private Object preferentialAmount;
        private String totalAssets;
        private Object avatarUrl;
        private String transferAmount;
        private BtcBean btc;          //比特币
        private String lastLoginTime;
        private String realName;
        private String walletBalance;
        private Object recomdAmount;
        private String currency;
        private BankcardBean bankcard;//银行卡
        private boolean isCash;
        private int withdrawAmount;
        private int unReadCount;
        private boolean isBit;
        private String username;

        public boolean isAutoPay() {
            return autoPay;
        }

        public void setAutoPay(boolean autoPay) {
            this.autoPay = autoPay;
        }

        public Object getPreferentialAmount() {
            return preferentialAmount;
        }

        public void setPreferentialAmount(Object preferentialAmount) {
            this.preferentialAmount = preferentialAmount;
        }

        public String getTotalAssets() {
            return totalAssets;
        }

        public void setTotalAssets(String totalAssets) {
            this.totalAssets = totalAssets;
        }

        public Object getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(Object avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getTransferAmount() {
            return transferAmount;
        }

        public void setTransferAmount(String transferAmount) {
            this.transferAmount = transferAmount;
        }

        public BtcBean getBtc() {
            return btc;
        }

        public void setBtc(BtcBean btc) {
            this.btc = btc;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getWalletBalance() {
            return walletBalance;
        }

        public void setWalletBalance(String walletBalance) {
            this.walletBalance = walletBalance;
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

        public BankcardBean getBankcard() {
            return bankcard;
        }

        public void setBankcard(BankcardBean bankcard) {
            this.bankcard = bankcard;
        }

        public boolean isIsCash() {
            return isCash;
        }

        public void setIsCash(boolean isCash) {
            this.isCash = isCash;
        }

        public int getWithdrawAmount() {
            return withdrawAmount;
        }

        public void setWithdrawAmount(int withdrawAmount) {
            this.withdrawAmount = withdrawAmount;
        }

        public int getUnReadCount() {
            return unReadCount;
        }

        public void setUnReadCount(int unReadCount) {
            this.unReadCount = unReadCount;
        }

        public boolean isIsBit() {
            return isBit;
        }

        public void setIsBit(boolean isBit) {
            this.isBit = isBit;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public static class BtcBean {
            /**
             * btcNumber : 333
             */

            private String btcNumber;

            public String getBtcNumber() {
                return btcNumber;
            }

            public void setBtcNumber(String btcNumber) {
                this.btcNumber = btcNumber;
            }
        }

        public static class BankcardBean implements Parcelable{
            /**
             * bankcardMasterName : d*d
             * realName : ddd
             * bankName : null
             * bankcardNumber : 8888 8888 **** 8888
             * bankDeposit : 唐山
             */

            private String bankcardMasterName;
            private String realName;
            private String bankName;
            private String bankcardNumber;
            private String bankDeposit;
            private String bankUrl;

            public String getBankUrl() {
                return bankUrl;
            }

            public void setBankUrl(String bankUrl) {
                this.bankUrl = bankUrl;
            }

            public String getBankcardMasterName() {
                return bankcardMasterName;
            }

            public void setBankcardMasterName(String bankcardMasterName) {
                this.bankcardMasterName = bankcardMasterName;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getBankcardNumber() {
                return bankcardNumber;
            }

            public void setBankcardNumber(String bankcardNumber) {
                this.bankcardNumber = bankcardNumber;
            }

            public String getBankDeposit() {
                return bankDeposit;
            }

            public void setBankDeposit(String bankDeposit) {
                this.bankDeposit = bankDeposit;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.bankcardMasterName);
                dest.writeString(this.realName);
                dest.writeString(this.bankName);
                dest.writeString(this.bankcardNumber);
                dest.writeString(this.bankDeposit);
            }

            public BankcardBean() {
            }

            protected BankcardBean(Parcel in) {
                this.bankcardMasterName = in.readString();
                this.realName = in.readString();
                this.bankName = in.readString();
                this.bankcardNumber = in.readString();
                this.bankDeposit = in.readString();
            }

            public static final Creator<BankcardBean> CREATOR = new Creator<BankcardBean>() {
                @Override
                public BankcardBean createFromParcel(Parcel source) {
                    return new BankcardBean(source);
                }

                @Override
                public BankcardBean[] newArray(int size) {
                    return new BankcardBean[size];
                }
            };
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
