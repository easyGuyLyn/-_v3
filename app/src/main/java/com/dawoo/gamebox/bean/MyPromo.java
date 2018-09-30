package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * Created by jack on 18-1-14.
 */

public class MyPromo {

    /**
     * totalCount : 24
     * list : [{"id":6827,"applyTime":1515743285943,"preferentialAudit":10,"preferentialAuditName":"倍稽核","activityName":"111","preferentialValue":500,"userId":111014,"checkState":"success","checkStateName":"已发放"},{"id":6767,"applyTime":1515739287903,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":100,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6766,"applyTime":1515739273107,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":1,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6765,"applyTime":1515739265495,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":1,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6764,"applyTime":1515739243728,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":1,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6763,"applyTime":1515739213268,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":1,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6762,"applyTime":1515738788107,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":1,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6761,"applyTime":1515738781692,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":1,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6760,"applyTime":1515738776370,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":1,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6759,"applyTime":1515738760078,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":5,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6758,"applyTime":1515738756223,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":1,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6757,"applyTime":1515738751420,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":1,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6756,"applyTime":1515738744933,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":5,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6755,"applyTime":1515738738520,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":10,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6754,"applyTime":1515738731461,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":100,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6753,"applyTime":1515738699234,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":10,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6752,"applyTime":1515738669232,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":1,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6751,"applyTime":1515738528094,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":1,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6750,"applyTime":1515737936564,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":5,"userId":111014,"checkState":"2","checkStateName":"已发放"},{"id":6748,"applyTime":1515737907214,"preferentialAudit":0,"preferentialAuditName":"免稽核","activityName":"开胃早餐","preferentialValue":10,"userId":111014,"checkState":"2","checkStateName":"已发放"}]
     */

    private int totalCount;
    private List<ListBean> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 6827
         * applyTime : 1515743285943
         * preferentialAudit : 10.0
         * preferentialAuditName : 倍稽核
         * activityName : 111
         * preferentialValue : 500.0
         * userId : 111014
         * checkState : success
         * checkStateName : 已发放
         */

        private int id;
        private long applyTime;
        private double preferentialAudit;
        private String preferentialAuditName;
        private String activityName;
        private double preferentialValue;
        private int userId;
        private String checkState;
        private String checkStateName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(long applyTime) {
            this.applyTime = applyTime;
        }

        public double getPreferentialAudit() {
            return preferentialAudit;
        }

        public void setPreferentialAudit(double preferentialAudit) {
            this.preferentialAudit = preferentialAudit;
        }

        public String getPreferentialAuditName() {
            return preferentialAuditName;
        }

        public void setPreferentialAuditName(String preferentialAuditName) {
            this.preferentialAuditName = preferentialAuditName;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public double getPreferentialValue() {
            return preferentialValue;
        }

        public void setPreferentialValue(double preferentialValue) {
            this.preferentialValue = preferentialValue;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getCheckState() {
            return checkState;
        }

        public void setCheckState(String checkState) {
            this.checkState = checkState;
        }

        public String getCheckStateName() {
            return checkStateName;
        }

        public void setCheckStateName(String checkStateName) {
            this.checkStateName = checkStateName;
        }
    }
}