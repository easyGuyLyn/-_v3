package com.dawoo.gamebox.bean;

import java.util.List;

public class AuditBean {

    boolean success;
    String code;
    String title;
    String message;
    Data data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        String currencySign;
        List<WithdrawAudit> withdrawAudit;

        public String getCurrencySign() {
            return currencySign;
        }

        public void setCurrencySign(String currencySign) {
            this.currencySign = currencySign;
        }

        public List<WithdrawAudit> getWithdrawAudit() {
            return withdrawAudit;
        }

        public void setWithdrawAudit(List<WithdrawAudit> withdrawAudit) {
            this.withdrawAudit = withdrawAudit;
        }
    }

    public static class WithdrawAudit{

        /**
         *  "createTime": 1528110629149,     //订单创建时间，0时区时间
         *                 "rechargeAmount": 1,                    //存款金额
         *                 "rechargeAudit": 4.7,                     //存款稽核
         *                 "rechargeRemindAudit": 5,            //剩余存款稽核
         *                 "rechargeFee": 0,                           //行政费用，如果为0显示通过，其他直接展示费用
         *                 "favorableAmount": null,                //优惠金额
         *                 "favorableAudit": null,                    //优惠稽核
         *                 "favorableRemindAudit": null,        //剩余优惠稽核
         *                 "favorableFee": null                       //优惠扣除，如果为0显示通过，其他直接展示费用
         */
        long createTime;
        String rechargeAmount;
        String rechargeAudit;
        String rechargeRemindAudit;
        Double rechargeFee;
        String favorableAmount;
        String favorableAudit;
        String favorableRemindAudit;
        Double favorableFee;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getRechargeAmount() {
            return rechargeAmount;
        }

        public void setRechargeAmount(String rechargeAmount) {
            this.rechargeAmount = rechargeAmount;
        }

        public String getRechargeAudit() {
            return rechargeAudit;
        }

        public void setRechargeAudit(String rechargeAudit) {
            this.rechargeAudit = rechargeAudit;
        }

        public String getRechargeRemindAudit() {
            return rechargeRemindAudit;
        }

        public void setRechargeRemindAudit(String rechargeRemindAudit) {
            this.rechargeRemindAudit = rechargeRemindAudit;
        }

        public Double getRechargeFee() {
            return rechargeFee;
        }

        public void setRechargeFee(Double rechargeFee) {
            this.rechargeFee = rechargeFee;
        }

        public String getFavorableAmount() {
            return favorableAmount;
        }

        public void setFavorableAmount(String favorableAmount) {
            this.favorableAmount = favorableAmount;
        }

        public String getFavorableAudit() {
            return favorableAudit;
        }

        public void setFavorableAudit(String favorableAudit) {
            this.favorableAudit = favorableAudit;
        }

        public String getFavorableRemindAudit() {
            return favorableRemindAudit;
        }

        public void setFavorableRemindAudit(String favorableRemindAudit) {
            this.favorableRemindAudit = favorableRemindAudit;
        }

        public Double getFavorableFee() {
            return favorableFee;
        }

        public void setFavorableFee(Double favorableFee) {
            this.favorableFee = favorableFee;
        }
    }

}
