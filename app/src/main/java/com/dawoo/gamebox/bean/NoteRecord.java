package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * 投资记录
 * Created by benson on 18-1-7.
 */

public class NoteRecord {
    /**
     * minDate : 1512662400000
     * maxDate : 1515288801237
     * list : [{"id":10111337,"apiId":12,"gameId":120001,"terminal":null,"betTime":1515326008000,"singleAmount":32,"orderState":"settle","actionIdJson":null}]
     * statisticsData : {"single":32,"effective":32,"contribution":0,"winning":0,"totalamount":63.68,"currency":"￥","profit":31.68}
     */

    private long minDate;
    private long maxDate;
    private StatisticsDataBean statisticsData;
    private List<ListBean> list;
    private int totalSize;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
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

    public StatisticsDataBean getStatisticsData() {
        return statisticsData;
    }

    public void setStatisticsData(StatisticsDataBean statisticsData) {
        this.statisticsData = statisticsData;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class StatisticsDataBean {
        /**
         * single : 32.0
         * effective : 32.0
         * contribution : 0
         * winning : 0
         * totalamount : 63.68
         * currency : ￥
         * profit : 31.68
         */

        private double single;
        private double effective;
        private String contribution;
        private String winning;
        private double totalamount;
        private String currency;
        private double profit;

        public double getSingle() {
            return single;
        }

        public void setSingle(double single) {
            this.single = single;
        }

        public double getEffective() {
            return effective;
        }

        public void setEffective(double effective) {
            this.effective = effective;
        }

        public String getContribution() {
            return contribution;
        }

        public void setContribution(String contribution) {
            this.contribution = contribution;
        }

        public String getWinning() {
            return winning;
        }

        public void setWinning(String winning) {
            this.winning = winning;
        }

        public double getTotalamount() {
            return totalamount;
        }

        public void setTotalamount(double totalamount) {
            this.totalamount = totalamount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public double getProfit() {
            return profit;
        }

        public void setProfit(double profit) {
            this.profit = profit;
        }
    }

    public static class ListBean {
        /**
         * id : 10111337
         * apiId : 12
         * gameId : 120001
         * terminal : null
         * betTime : 1515326008000
         * singleAmount : 32.0
         * orderState : settle
         * actionIdJson : null
         */

        private String id;
        private int apiId;
        private int gameId;
        private Object terminal;
        private String apiName;
        private String gameName;
        private String url;
        private String profitAmount;
        private long betTime;
        private double singleAmount;
        private String orderState;
        private Object actionIdJson;

        public String getApiName() {
            return apiName;
        }

        public void setApiName(String apiName) {
            this.apiName = apiName;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getProfitAmount() {
            return profitAmount;
        }

        public void setProfitAmount(String profitAmount) {
            this.profitAmount = profitAmount;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getApiId() {
            return apiId;
        }

        public void setApiId(int apiId) {
            this.apiId = apiId;
        }

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public Object getTerminal() {
            return terminal;
        }

        public void setTerminal(Object terminal) {
            this.terminal = terminal;
        }

        public long getBetTime() {
            return betTime;
        }

        public void setBetTime(long betTime) {
            this.betTime = betTime;
        }

        public double getSingleAmount() {
            return singleAmount;
        }

        public void setSingleAmount(double singleAmount) {
            this.singleAmount = singleAmount;
        }

        public String getOrderState() {
            return orderState;
        }

        public void setOrderState(String orderState) {
            this.orderState = orderState;
        }

        public Object getActionIdJson() {
            return actionIdJson;
        }

        public void setActionIdJson(Object actionIdJson) {
            this.actionIdJson = actionIdJson;
        }
    }
}
