package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * Created by archar on 18-1-15.
 */

public class SiteMyNotice {
    private int error;
    private int code;
    private String message;
    private DataBean data;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> dataList;

        public List<ListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<ListBean> dataList) {
            this.dataList = dataList;
        }
    }

    public static class ListBean {
        private String id;
        private String advisoryContent;
        private long advisoryTime;
        private String link;
        private String advisoryTitle;
        private Boolean read;
        private String searchId;
        private String replyTitle;

        private Boolean isSelected = false;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAdvisoryContent() {
            return advisoryContent;
        }

        public void setAdvisoryContent(String advisoryContent) {
            this.advisoryContent = advisoryContent;
        }

        public long getAdvisoryTime() {
            return advisoryTime;
        }

        public void setAdvisoryTime(long advisoryTime) {
            this.advisoryTime = advisoryTime;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getAdvisoryTitle() {
            return advisoryTitle;
        }

        public void setAdvisoryTitle(String advisoryTitle) {
            this.advisoryTitle = advisoryTitle;
        }

        public Boolean getRead() {
            return read;
        }

        public void setRead(Boolean read) {
            this.read = read;
        }

        public String getSearchId() {
            return searchId;
        }

        public void setSearchId(String searchId) {
            this.searchId = searchId;
        }

        public String getReplyTitle() {
            return replyTitle;
        }

        public void setReplyTitle(String replyTitle) {
            this.replyTitle = replyTitle;
        }

        public Boolean getSelected() {
            return isSelected;
        }

        public void setSelected(Boolean selected) {
            isSelected = selected;
        }
    }

}
