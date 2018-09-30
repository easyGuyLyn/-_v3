package com.dawoo.gamebox.bean;

import java.util.List;

public class GradientTemp {
    /**
     * total : 0
     * command : [{"recommendUserName":"ss****09","createTime":1528199837802,"status":"正常","singleCount":null,"rewardCount":null,"rewardAmount":null},{"recommendUserName":"ss****07","createTime":1528199757355,"status":"正常","singleCount":null,"rewardCount":null,"rewardAmount":null},{"recommendUserName":"ss****08","createTime":1528199806577,"status":"正常","singleCount":null,"rewardCount":null,"rewardAmount":null},{"recommendUserName":"ss****11","createTime":1528199872383,"status":"正常","singleCount":null,"rewardCount":null,"rewardAmount":null},{"recommendUserName":"ss****06","createTime":1528199708926,"status":"正常","singleCount":null,"rewardCount":null,"rewardAmount":null}]
     */

    private int total;
    private List<CommandBean> command;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CommandBean> getCommand() {
        return command;
    }

    public void setCommand(List<CommandBean> command) {
        this.command = command;
    }

    public static class CommandBean {
        /**
         * recommendUserName : ss****09
         * createTime : 1528199837802
         * status : 正常
         * singleCount : null
         * rewardCount : null
         * rewardAmount : null
         */

        private String recommendUserName;
        private long createTime;
        private String status;
        private Object singleCount;
        private Object rewardCount;
        private String rewardAmount;

        public String getRecommendUserName() {
            return recommendUserName;
        }

        public void setRecommendUserName(String recommendUserName) {
            this.recommendUserName = recommendUserName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getSingleCount() {
            return singleCount;
        }

        public void setSingleCount(Object singleCount) {
            this.singleCount = singleCount;
        }

        public Object getRewardCount() {
            return rewardCount;
        }

        public void setRewardCount(Object rewardCount) {
            this.rewardCount = rewardCount;
        }

        public String getRewardAmount() {
            return rewardAmount;
        }

        public void setRewardAmount(String rewardAmount) {
            this.rewardAmount = rewardAmount;
        }
    }

//    private List<CommandBean> command;
//
//    public List<CommandBean> getCommand() {
//        return command;
//    }
//
//    public void setCommand(List<CommandBean> command) {
//        this.command = command;
//    }
//
//    public static class CommandBean {
//        /**
//         * recommendUserName : ss****09
//         * createTime : 1528199837802
//         * status : 正常
//         * singleCount : null
//         * rewardCount : null
//         * rewardAmount : null
//         */
//
//        private String recommendUserName;
//        private long createTime;
//        private String status;
//        private Object singleCount;
//        private Object rewardCount;
//        private Object rewardAmount;
//
//        public String getRecommendUserName() {
//            return recommendUserName;
//        }
//
//        public void setRecommendUserName(String recommendUserName) {
//            this.recommendUserName = recommendUserName;
//        }
//
//        public long getCreateTime() {
//            return createTime;
//        }
//
//        public void setCreateTime(long createTime) {
//            this.createTime = createTime;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//        public Object getSingleCount() {
//            return singleCount;
//        }
//
//        public void setSingleCount(Object singleCount) {
//            this.singleCount = singleCount;
//        }
//
//        public Object getRewardCount() {
//            return rewardCount;
//        }
//
//        public void setRewardCount(Object rewardCount) {
//            this.rewardCount = rewardCount;
//        }
//
//        public Object getRewardAmount() {
//            return rewardAmount;
//        }
//
//        public void setRewardAmount(Object rewardAmount) {
//            this.rewardAmount = rewardAmount;
//        }
//    }


}
