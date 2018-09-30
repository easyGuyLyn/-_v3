package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * Created by jack on 18-3-25.
 */

public class HelpDetailsBean {

    /**
     * list : [{"id":472,"helpDocumentId":206,"helpTitle":"简介","helpContent":"&lt;p&gt;我们提供的是欧式蒙地卡罗轮盘，转轮有 37个槽，号码为0，1到36。下注结束后，荷官会把轮盘向一个方向转动，然后把象牙制滚球抛到轮盘的外侧，让滚球在轮盘内转动多周后慢慢停下来，并降落在其中一个细沟内为该局结果。&lt;/p&gt;","local":"zh_CN"}]
     * name : 轮盘
     */

    private String name;
    private List<ListBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 472
         * helpDocumentId : 206
         * helpTitle : 简介
         * helpContent : &lt;p&gt;我们提供的是欧式蒙地卡罗轮盘，转轮有 37个槽，号码为0，1到36。下注结束后，荷官会把轮盘向一个方向转动，然后把象牙制滚球抛到轮盘的外侧，让滚球在轮盘内转动多周后慢慢停下来，并降落在其中一个细沟内为该局结果。&lt;/p&gt;
         * local : zh_CN
         */

        private int id;
        private int helpDocumentId;
        private String helpTitle;
        private String helpContent;
        private String local;
        private boolean childOpen;  //子view是否打开

        public boolean isChildOpen() {
            return childOpen;
        }

        public void setChildOpen(boolean childOpen) {
            this.childOpen = childOpen;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getHelpDocumentId() {
            return helpDocumentId;
        }

        public void setHelpDocumentId(int helpDocumentId) {
            this.helpDocumentId = helpDocumentId;
        }

        public String getHelpTitle() {
            return helpTitle;
        }

        public void setHelpTitle(String helpTitle) {
            this.helpTitle = helpTitle;
        }

        public String getHelpContent() {
            return helpContent;
        }

        public void setHelpContent(String helpContent) {
            this.helpContent = helpContent;
        }

        public String getLocal() {
            return local;
        }

        public void setLocal(String local) {
            this.local = local;
        }
    }
}
