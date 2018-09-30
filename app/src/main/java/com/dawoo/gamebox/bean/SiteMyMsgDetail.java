package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * Created by benson on 18-1-17.
 */

public class SiteMyMsgDetail {
    private String id;
    private String advisoryContent;
    private long advisoryTime;
    private String link;
    private String advisoryTitle;
    private Boolean read;
    private String searchId;
    private String replyTitle;
    private List<ReplayListBean> replyList;

    public List<ReplayListBean> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplayListBean> replyList) {
        this.replyList = replyList;
    }

    public static class ReplayListBean {
        private String replyTitle;
        private long replyTime;
        private String replyContent;

        public String getReplyTitle() {
            return replyTitle;
        }

        public void setReplyTitle(String replyTitle) {
            this.replyTitle = replyTitle;
        }

        public long getReplyTime() {
            return replyTime;
        }

        public void setReplyTime(long replyTime) {
            this.replyTime = replyTime;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }
    }


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
}
