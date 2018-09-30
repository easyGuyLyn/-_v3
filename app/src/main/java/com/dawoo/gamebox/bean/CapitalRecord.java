package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * 资金记录
 * Created by Archar
 */

public class CapitalRecord {

    private long minDate;
    private long maxDate;
    private int totalCount;
    private String currency;
    private String withdrawSum;
    private String transferSum;
    private List<FundListBean> fundListApps;
    private SumPlayer sumPlayerMap;

    public SumPlayer getSumPlayerMap() {
        return sumPlayerMap;
    }

    public void setSumPlayerMap(SumPlayer sumPlayerMap) {
        this.sumPlayerMap = sumPlayerMap;
    }

    public long getMinDate() {
        return minDate;
    }

    public void setMinDate(long minDate) {
        this.minDate = minDate;
    }

    public long getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(long maxDate) {
        this.maxDate = maxDate;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getWithdrawSum() {
        return withdrawSum;
    }

    public void setWithdrawSum(String withdrawSum) {
        this.withdrawSum = withdrawSum;
    }

    public String getTransferSum() {
        return transferSum;
    }

    public void setTransferSum(String transferSum) {
        this.transferSum = transferSum;
    }

    public List<FundListBean> getFundListApps() {
        return fundListApps;
    }

    public void setFundListApps(List<FundListBean> fundListApps) {
        this.fundListApps = fundListApps;
    }

    public static class FundListBean {
        private int id;
        private long createTime;
        private String transactionMoney;//金额
        private String transactionType;
        private String transaction_typeName;//类型名称
        private String status;
        private String statusName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getTransactionMoney() {
            return transactionMoney;
        }

        public void setTransactionMoney(String transactionMoney) {
            this.transactionMoney = transactionMoney;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getTransaction_typeName() {
            return transaction_typeName;
        }

        public void setTransaction_typeName(String transaction_typeName) {
            this.transaction_typeName = transaction_typeName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }
    }

    public static class SumPlayer{
        private String recharge;
        private String rakeback;
        private String favorable;
        private String withdraw;

        public String getRecharge() {
            return recharge;
        }

        public void setRecharge(String recharge) {
            this.recharge = recharge;
        }

        public String getRakeback() {
            return rakeback;
        }

        public void setRakeback(String rakeback) {
            this.rakeback = rakeback;
        }

        public String getFavorable() {
            return favorable;
        }

        public void setFavorable(String favorable) {
            this.favorable = favorable;
        }

        public String getWithdraw() {
            return withdraw;
        }

        public void setWithdraw(String withdraw) {
            this.withdraw = withdraw;
        }
    }
}
