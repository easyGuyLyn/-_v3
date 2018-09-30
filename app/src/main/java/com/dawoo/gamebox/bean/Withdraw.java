package com.dawoo.gamebox.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by b on 18-1-15.
 */

public class Withdraw {

    /**
     * hasBank : true
     * currencySign : ￥
     * totalBalance : 0
     * auditLogUrl : /wallet/withdraw/showAuditLog.html
     * bankcardMap : {"2":{"id":88980,"userId":111014,"bankcardMasterName":null,"bankcardNumber":"333","createTime":1513741654055,"useCount":0,"useStauts":true,"isDefault":true,"bankName":"bitcoin","bankDeposit":null,"customBankName":null,"type":"2"}}
     * auditMap : {"actualWithdraw":-500,"recordList":true,"deductFavorable":-500,"transactionNo":null,"administrativeFee":0,"counterFee":"0","withdrawAmount":0,"withdrawFeeMoney":0}
     * isCash : true
     * token : d459b6b6-659f-414f-ba8f-f571f39e2f0b
     * isBit : true
     */

    private boolean hasBank;
    private String currencySign;
    private double totalBalance;
    private String auditLogUrl;
    private BankcardMapBean bankcardMap;
    private AuditMapBean auditMap;
    private boolean isCash;
    private String token;
    private boolean isBit;
    private String bankUrl;
    private WithdrawLimit rank;
    private boolean isSafePassword;

    public boolean isSafePassword() {
        return isSafePassword;
    }

    public void setSafePassword(boolean safePassword) {
        isSafePassword = safePassword;
    }

    public WithdrawLimit getRank() {
        return rank;
    }

    public void setRank(WithdrawLimit rank) {
        this.rank = rank;
    }

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }

    public boolean isHasBank() {
        return hasBank;
    }

    public void setHasBank(boolean hasBank) {
        this.hasBank = hasBank;
    }

    public String getCurrencySign() {
        return currencySign;
    }

    public void setCurrencySign(String currencySign) {
        this.currencySign = currencySign;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getAuditLogUrl() {
        return auditLogUrl;
    }

    public void setAuditLogUrl(String auditLogUrl) {
        this.auditLogUrl = auditLogUrl;
    }

    public BankcardMapBean getBankcardMap() {
        return bankcardMap;
    }

    public void setBankcardMap(BankcardMapBean bankcardMap) {
        this.bankcardMap = bankcardMap;
    }

    public AuditMapBean getAuditMap() {
        return auditMap;
    }

    public void setAuditMap(AuditMapBean auditMap) {
        this.auditMap = auditMap;
    }

    public boolean isIsCash() {
        return isCash;
    }

    public void setIsCash(boolean isCash) {
        this.isCash = isCash;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isIsBit() {
        return isBit;
    }

    public void setIsBit(boolean isBit) {
        this.isBit = isBit;
    }

    public static class WithdrawLimit{
        public String getWithdrawMaxNum() {
            return withdrawMaxNum;
        }

        public void setWithdrawMaxNum(String withdrawMaxNum) {
            this.withdrawMaxNum = withdrawMaxNum;
        }

        public String getWithdrawMinNum() {
            return withdrawMinNum;
        }

        public void setWithdrawMinNum(String withdrawMinNum) {
            this.withdrawMinNum = withdrawMinNum;
        }

        private String withdrawMaxNum;
        private String withdrawMinNum;
    }


    public static class BankcardMapBean {
        /**
         * 2 : {"id":88980,"userId":111014,"bankcardMasterName":null,"bankcardNumber":"333","createTime":1513741654055,"useCount":0,"useStauts":true,"isDefault":true,"bankName":"bitcoin","bankDeposit":null,"customBankName":null,"type":"2"}
         */

        @SerializedName("1")
        private BankCardBean bankCardBean1;

        @SerializedName("2")
        private BankCardBean bankCardBean2;

        public BankCardBean getBankCardBean1() {
            return bankCardBean1;
        }

        public void setBankCardBean1(BankCardBean bankCardBean1) {
            this.bankCardBean1 = bankCardBean1;
        }

        public BankCardBean getBankCardBean2() {
            return bankCardBean2;
        }

        public void setBankCardBean2(BankCardBean bankCardBean2) {
            this.bankCardBean2 = bankCardBean2;
        }


        public static class BankCardBean {
            /**
             * id : 88980
             * userId : 111014
             * bankcardMasterName : null
             * bankcardNumber : 333
             * createTime : 1513741654055
             * useCount : 0
             * useStauts : true
             * isDefault : true
             * bankName : bitcoin
             * bankDeposit : null
             * customBankName : null
             * type : 2
             */

            private int id;
            private int userId;
            private String bankcardMasterName;
            private String bankcardNumber;
            private long createTime;
            private int useCount;
            private boolean useStauts;
            private boolean isDefault;
            private String bankName;
            private String bankDeposit;
            private String customBankName;
            private String type;
            private String bankUrl;

            public String getBankUrl() {
                return bankUrl;
            }

            public void setBankUrl(String bankUrl) {
                this.bankUrl = bankUrl;
            }


            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getBankcardMasterName() {
                return bankcardMasterName;
            }

            public void setBankcardMasterName(String bankcardMasterName) {
                this.bankcardMasterName = bankcardMasterName;
            }

            public String getBankcardNumber() {
                return bankcardNumber;
            }

            public void setBankcardNumber(String bankcardNumber) {
                this.bankcardNumber = bankcardNumber;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getUseCount() {
                return useCount;
            }

            public void setUseCount(int useCount) {
                this.useCount = useCount;
            }

            public boolean isUseStauts() {
                return useStauts;
            }

            public void setUseStauts(boolean useStauts) {
                this.useStauts = useStauts;
            }

            public boolean isIsDefault() {
                return isDefault;
            }

            public void setIsDefault(boolean isDefault) {
                this.isDefault = isDefault;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getBankDeposit() {
                return bankDeposit;
            }

            public void setBankDeposit(String bankDeposit) {
                this.bankDeposit = bankDeposit;
            }

            public String getCustomBankName() {
                return customBankName;
            }

            public void setCustomBankName(String customBankName) {
                this.customBankName = customBankName;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    public static class AuditMapBean {
        /**
         * actualWithdraw : -500
         * recordList : true
         * deductFavorable : -500
         * transactionNo : null
         * administrativeFee : 0
         * counterFee : 0
         * withdrawAmount : 0
         * withdrawFeeMoney : 0
         */

        private String actualWithdraw;
        private boolean recordList;
        private double deductFavorable;  //扣除优惠
        private Object transactionNo;
        private double administrativeFee;//行政费
        private String counterFee;    //手续费
        private String withdrawAmount;
        private double withdrawFeeMoney;

        public String getActualWithdraw() {
            return actualWithdraw;
        }

        public void setActualWithdraw(String actualWithdraw) {
            this.actualWithdraw = actualWithdraw;
        }

        public boolean isRecordList() {
            return recordList;
        }

        public void setRecordList(boolean recordList) {
            this.recordList = recordList;
        }

        public double getDeductFavorable() {
            return deductFavorable;
        }

        public void setDeductFavorable(int deductFavorable) {
            this.deductFavorable = deductFavorable;
        }

        public Object getTransactionNo() {
            return transactionNo;
        }

        public void setTransactionNo(Object transactionNo) {
            this.transactionNo = transactionNo;
        }

        public double getAdministrativeFee() {
            return administrativeFee;
        }

        public void setAdministrativeFee(int administrativeFee) {
            this.administrativeFee = administrativeFee;
        }

        public String getCounterFee() {
            return counterFee;
        }

        public void setCounterFee(String counterFee) {
            this.counterFee = counterFee;
        }

        public String getWithdrawAmount() {
            return withdrawAmount;
        }

        public void setWithdrawAmount(String withdrawAmount) {
            this.withdrawAmount = withdrawAmount;
        }

        public double getWithdrawFeeMoney() {
            return withdrawFeeMoney;
        }

        public void setWithdrawFeeMoney(double withdrawFeeMoney) {
            this.withdrawFeeMoney = withdrawFeeMoney;
        }
    }
}
