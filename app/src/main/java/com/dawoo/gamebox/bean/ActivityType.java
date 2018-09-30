package com.dawoo.gamebox.bean;

/**
 * 优惠活动
 * Created by benson on 18-1-2.
 */

public class ActivityType {
    /**
     * activityKey : 012e9ffd-07f8-429b-b733-404a087ac8c5
     * activityTypeName : 测试二
     */



    private String activityKey;
    private String activityTypeName;

    public ActivityType() {
    }

    public ActivityType(String activityKey, String activityTypeName) {
        this.activityKey = activityKey;
        this.activityTypeName = activityTypeName;
    }

    public String getActivityKey() {
        return activityKey;
    }

    public void setActivityKey(String activityKey) {
        this.activityKey = activityKey;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

//
//    /**
//     * id : 3020
//     * module : operation
//     * type : operate_activity_classify
//     * key : 012e9ffd-07f8-429b-b733-404a087ac8c5
//     * locale : zh_CN
//     * value : 测试二
//     * siteId : 21
//     * remark : null
//     * defaultValue : null
//     * builtIn : false
//     * cacheKey : 21:operation:operate_activity_classify
//     * cacheKeyLocale : 012e9ffd-07f8-429b-b733-404a087ac8c5:zh_CN
//     * typeMessageMap : {"list":[{"id":556,"name":"救济金","activityState":"release","startTime":1515034517000,"endTime":1515340800000,"activityClassifyKey":"012e9ffd-07f8-429b-b733-404a087ac8c5","activityName":"啊啊啊啊啊","activityVersion":"zh_CN","isDisplay":true,"activityCover":"gb/21/headImage/1/1515034602101.jpg","acount":0,"code":"relief_fund","activityDescription":" 撒打算  ","isAudit":true,"logo":"/images/activity/events-img-05.jpg","introduce":"针对玩家总资产过低时发起的优惠活动","states":"processing","isDeleted":false,"checkStatus":"1","rankid":"325,316,397,298,382,365,389,363,394,300,387,366,412,399,388,356,360,378,409,413,411,367,307,415,417,419,421,425,426,418,-1,","createTime":1515034603501,"preferentialAmountLimit":null,"isAllRank":true,"activityAffiliated":"/fserver/files/gb/21/headImage/1/1515034602101.jpg","orderNum":null,"depositWay":null,"isRead":false,"isRemove":false,"rankName":null,"classifyKeyName":null,"hasUseRank":true,"preferential":false,"approvalStatus":"success","activityStatesShow":{"cssClass":"label label-success","activityState":"processing"},"activityTimeStatus":"processing"}]}
//     */
//
//    private int id;
//    private String module;
//    private String type;
//    private String key;
//    private String locale;
//    private String value;
//    private int siteId;
//    private Object remark;
//    private Object defaultValue;
//    private boolean builtIn;
//    private String cacheKey;
//    private String cacheKeyLocale;
//    private TypeMessageMapBean typeMessageMap;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getModule() {
//        return module;
//    }
//
//    public void setModule(String module) {
//        this.module = module;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    public String getLocale() {
//        return locale;
//    }
//
//    public void setLocale(String locale) {
//        this.locale = locale;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//
//    public int getSiteId() {
//        return siteId;
//    }
//
//    public void setSiteId(int siteId) {
//        this.siteId = siteId;
//    }
//
//    public Object getRemark() {
//        return remark;
//    }
//
//    public void setRemark(Object remark) {
//        this.remark = remark;
//    }
//
//    public Object getDefaultValue() {
//        return defaultValue;
//    }
//
//    public void setDefaultValue(Object defaultValue) {
//        this.defaultValue = defaultValue;
//    }
//
//    public boolean isBuiltIn() {
//        return builtIn;
//    }
//
//    public void setBuiltIn(boolean builtIn) {
//        this.builtIn = builtIn;
//    }
//
//    public String getCacheKey() {
//        return cacheKey;
//    }
//
//    public void setCacheKey(String cacheKey) {
//        this.cacheKey = cacheKey;
//    }
//
//    public String getCacheKeyLocale() {
//        return cacheKeyLocale;
//    }
//
//    public void setCacheKeyLocale(String cacheKeyLocale) {
//        this.cacheKeyLocale = cacheKeyLocale;
//    }
//
//    public TypeMessageMapBean getTypeMessageMap() {
//        return typeMessageMap;
//    }
//
//    public void setTypeMessageMap(TypeMessageMapBean typeMessageMap) {
//        this.typeMessageMap = typeMessageMap;
//    }
//
//    public static class TypeMessageMapBean {
//        private List<ListBean> list;
//
//        public List<ListBean> getList() {
//            return list;
//        }
//
//        public void setList(List<ListBean> list) {
//            this.list = list;
//        }
//
//        public static class ListBean {
//            /**
//             * id : 556
//             * name : 救济金
//             * activityState : release
//             * startTime : 1515034517000
//             * endTime : 1515340800000
//             * activityClassifyKey : 012e9ffd-07f8-429b-b733-404a087ac8c5
//             * activityName : 啊啊啊啊啊
//             * activityVersion : zh_CN
//             * isDisplay : true
//             * activityCover : gb/21/headImage/1/1515034602101.jpg
//             * acount : 0
//             * code : relief_fund
//             * activityDescription :  撒打算
//             * isAudit : true
//             * logo : /images/activity/events-img-05.jpg
//             * introduce : 针对玩家总资产过低时发起的优惠活动
//             * states : processing
//             * isDeleted : false
//             * checkStatus : 1
//             * rankid : 325,316,397,298,382,365,389,363,394,300,387,366,412,399,388,356,360,378,409,413,411,367,307,415,417,419,421,425,426,418,-1,
//             * createTime : 1515034603501
//             * preferentialAmountLimit : null
//             * isAllRank : true
//             * activityAffiliated : /fserver/files/gb/21/headImage/1/1515034602101.jpg
//             * orderNum : null
//             * depositWay : null
//             * isRead : false
//             * isRemove : false
//             * rankName : null
//             * classifyKeyName : null
//             * hasUseRank : true
//             * preferential : false
//             * approvalStatus : success
//             * activityStatesShow : {"cssClass":"label label-success","activityState":"processing"}
//             * activityTimeStatus : processing
//             */
//
//            private int id;
//            private String name;
//            private String activityState;
//            private long startTime;
//            private long endTime;
//            private String activityClassifyKey;
//            private String activityName;
//            private String activityVersion;
//            private boolean isDisplay;
//            private String activityCover;
//            private int acount;
//            private String code;
//            private String activityDescription;
//            private boolean isAudit;
//            private String logo;
//            private String introduce;
//            private String states;
//            private boolean isDeleted;
//            private String checkStatus;
//            private String rankid;
//            private long createTime;
//            private Object preferentialAmountLimit;
//            private boolean isAllRank;
//            private String activityAffiliated;
//            private Object orderNum;
//            private Object depositWay;
//            private boolean isRead;
//            private boolean isRemove;
//            private Object rankName;
//            private Object classifyKeyName;
//            private boolean hasUseRank;
//            private boolean preferential;
//            private String approvalStatus;
//            private ActivityStatesShowBean activityStatesShow;
//            private String activityTimeStatus;
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public String getActivityState() {
//                return activityState;
//            }
//
//            public void setActivityState(String activityState) {
//                this.activityState = activityState;
//            }
//
//            public long getStartTime() {
//                return startTime;
//            }
//
//            public void setStartTime(long startTime) {
//                this.startTime = startTime;
//            }
//
//            public long getEndTime() {
//                return endTime;
//            }
//
//            public void setEndTime(long endTime) {
//                this.endTime = endTime;
//            }
//
//            public String getActivityClassifyKey() {
//                return activityClassifyKey;
//            }
//
//            public void setActivityClassifyKey(String activityClassifyKey) {
//                this.activityClassifyKey = activityClassifyKey;
//            }
//
//            public String getActivityName() {
//                return activityName;
//            }
//
//            public void setActivityName(String activityName) {
//                this.activityName = activityName;
//            }
//
//            public String getActivityVersion() {
//                return activityVersion;
//            }
//
//            public void setActivityVersion(String activityVersion) {
//                this.activityVersion = activityVersion;
//            }
//
//            public boolean isIsDisplay() {
//                return isDisplay;
//            }
//
//            public void setIsDisplay(boolean isDisplay) {
//                this.isDisplay = isDisplay;
//            }
//
//            public String getActivityCover() {
//                return activityCover;
//            }
//
//            public void setActivityCover(String activityCover) {
//                this.activityCover = activityCover;
//            }
//
//            public int getAcount() {
//                return acount;
//            }
//
//            public void setAcount(int acount) {
//                this.acount = acount;
//            }
//
//            public String getCode() {
//                return code;
//            }
//
//            public void setCode(String code) {
//                this.code = code;
//            }
//
//            public String getActivityDescription() {
//                return activityDescription;
//            }
//
//            public void setActivityDescription(String activityDescription) {
//                this.activityDescription = activityDescription;
//            }
//
//            public boolean isIsAudit() {
//                return isAudit;
//            }
//
//            public void setIsAudit(boolean isAudit) {
//                this.isAudit = isAudit;
//            }
//
//            public String getLogo() {
//                return logo;
//            }
//
//            public void setLogo(String logo) {
//                this.logo = logo;
//            }
//
//            public String getIntroduce() {
//                return introduce;
//            }
//
//            public void setIntroduce(String introduce) {
//                this.introduce = introduce;
//            }
//
//            public String getStates() {
//                return states;
//            }
//
//            public void setStates(String states) {
//                this.states = states;
//            }
//
//            public boolean isIsDeleted() {
//                return isDeleted;
//            }
//
//            public void setIsDeleted(boolean isDeleted) {
//                this.isDeleted = isDeleted;
//            }
//
//            public String getCheckStatus() {
//                return checkStatus;
//            }
//
//            public void setCheckStatus(String checkStatus) {
//                this.checkStatus = checkStatus;
//            }
//
//            public String getRankid() {
//                return rankid;
//            }
//
//            public void setRankid(String rankid) {
//                this.rankid = rankid;
//            }
//
//            public long getCreateTime() {
//                return createTime;
//            }
//
//            public void setCreateTime(long createTime) {
//                this.createTime = createTime;
//            }
//
//            public Object getPreferentialAmountLimit() {
//                return preferentialAmountLimit;
//            }
//
//            public void setPreferentialAmountLimit(Object preferentialAmountLimit) {
//                this.preferentialAmountLimit = preferentialAmountLimit;
//            }
//
//            public boolean isIsAllRank() {
//                return isAllRank;
//            }
//
//            public void setIsAllRank(boolean isAllRank) {
//                this.isAllRank = isAllRank;
//            }
//
//            public String getActivityAffiliated() {
//                return activityAffiliated;
//            }
//
//            public void setActivityAffiliated(String activityAffiliated) {
//                this.activityAffiliated = activityAffiliated;
//            }
//
//            public Object getOrderNum() {
//                return orderNum;
//            }
//
//            public void setOrderNum(Object orderNum) {
//                this.orderNum = orderNum;
//            }
//
//            public Object getDepositWay() {
//                return depositWay;
//            }
//
//            public void setDepositWay(Object depositWay) {
//                this.depositWay = depositWay;
//            }
//
//            public boolean isIsRead() {
//                return isRead;
//            }
//
//            public void setIsRead(boolean isRead) {
//                this.isRead = isRead;
//            }
//
//            public boolean isIsRemove() {
//                return isRemove;
//            }
//
//            public void setIsRemove(boolean isRemove) {
//                this.isRemove = isRemove;
//            }
//
//            public Object getRankName() {
//                return rankName;
//            }
//
//            public void setRankName(Object rankName) {
//                this.rankName = rankName;
//            }
//
//            public Object getClassifyKeyName() {
//                return classifyKeyName;
//            }
//
//            public void setClassifyKeyName(Object classifyKeyName) {
//                this.classifyKeyName = classifyKeyName;
//            }
//
//            public boolean isHasUseRank() {
//                return hasUseRank;
//            }
//
//            public void setHasUseRank(boolean hasUseRank) {
//                this.hasUseRank = hasUseRank;
//            }
//
//            public boolean isPreferential() {
//                return preferential;
//            }
//
//            public void setPreferential(boolean preferential) {
//                this.preferential = preferential;
//            }
//
//            public String getApprovalStatus() {
//                return approvalStatus;
//            }
//
//            public void setApprovalStatus(String approvalStatus) {
//                this.approvalStatus = approvalStatus;
//            }
//
//            public ActivityStatesShowBean getActivityStatesShow() {
//                return activityStatesShow;
//            }
//
//            public void setActivityStatesShow(ActivityStatesShowBean activityStatesShow) {
//                this.activityStatesShow = activityStatesShow;
//            }
//
//            public String getActivityTimeStatus() {
//                return activityTimeStatus;
//            }
//
//            public void setActivityTimeStatus(String activityTimeStatus) {
//                this.activityTimeStatus = activityTimeStatus;
//            }
//
//            public static class ActivityStatesShowBean {
//                /**
//                 * cssClass : label label-success
//                 * activityState : processing
//                 */
//
//                private String cssClass;
//                private String activityState;
//
//                public String getCssClass() {
//                    return cssClass;
//                }
//
//                public void setCssClass(String cssClass) {
//                    this.cssClass = cssClass;
//                }
//
//                public String getActivityState() {
//                    return activityState;
//                }
//
//                public void setActivityState(String activityState) {
//                    this.activityState = activityState;
//                }
//            }
//        }
//    }


}
