package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * 公告
 * Created by benson on 17-12-29.
 */

public class Notice {

    private List<AnnouncementBean> announcement;

    public List<AnnouncementBean> getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(List<AnnouncementBean> announcement) {
        this.announcement = announcement;
    }

    public static class AnnouncementBean {
        /**
         * id : 398
         * title : null
         * content : ★尊敬的玩家：月月大返利，端午六一双享大礼包，系统已随机派送完成，感谢您对UEDBET一直以来的支持与厚爱，祝您生活愉快~
         * language : zh_CN
         * createUser : 6
         * createTime : 1496736825173
         * updateUser : null
         * updateTime : null
         * isTask : false
         * publishTime : 1496736825172
         * code : 5af9763d-cbb1-49f0-996c-058ade0c20c1
         * announcementType : 1
         * display : true
         * orderNum : 1
         */

        private int id;
        private Object title;
        private String content;
        private String language;
        private int createUser;
        private long createTime;
        private Object updateUser;
        private Object updateTime;
        private boolean isTask;
        private long publishTime;
        private String code;
        private String announcementType;
        private boolean display;
        private int orderNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public int getCreateUser() {
            return createUser;
        }

        public void setCreateUser(int createUser) {
            this.createUser = createUser;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(Object updateUser) {
            this.updateUser = updateUser;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isIsTask() {
            return isTask;
        }

        public void setIsTask(boolean isTask) {
            this.isTask = isTask;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getAnnouncementType() {
            return announcementType;
        }

        public void setAnnouncementType(String announcementType) {
            this.announcementType = announcementType;
        }

        public boolean isDisplay() {
            return display;
        }

        public void setDisplay(boolean display) {
            this.display = display;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }
    }
}
