package com.dawoo.gamebox.bean.push;


import java.io.Serializable;

/**
 * archar
 */
public class NoticeMessage implements Serializable{

    /**
     * subscribeType : 主题
     * msgBody : {"title":"消息标题","content":"消息内容","remindMethod":"收到消息后如何显示"}
     * receiveUserType : null
     */

    private String subscribeType;
    private MsgBodyBean msgBody;
    private Object receiveUserType;

    public String getSubscribeType() {
        return subscribeType;
    }

    public void setSubscribeType(String subscribeType) {
        this.subscribeType = subscribeType;
    }

    public MsgBodyBean getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(MsgBodyBean msgBody) {
        this.msgBody = msgBody;
    }

    public Object getReceiveUserType() {
        return receiveUserType;
    }

    public void setReceiveUserType(Object receiveUserType) {
        this.receiveUserType = receiveUserType;
    }

    public static class MsgBodyBean {
        /**
         * title : 消息标题
         * content : 消息内容
         * remindMethod : 收到消息后如何显示
         */

        private String title;
        private String content;
        private String remindMethod;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRemindMethod() {
            return remindMethod;
        }

        public void setRemindMethod(String remindMethod) {
            this.remindMethod = remindMethod;
        }
    }
}
