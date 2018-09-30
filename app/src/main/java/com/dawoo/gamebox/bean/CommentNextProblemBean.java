package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * Created by jack on 18-3-25.
 */

public class CommentNextProblemBean {

    /**
     * list : [{"name":"真实姓名","ids":"[{\"id\":110},{\"id\":109},{\"id\":111}]","id":"3e2815371c66ef253b5378b71bc5a17f"},{"name":"找回用户名","ids":"[{\"id\":106}]","id":"c65e5a140b69f159b4cb7274a28c9561"},{"name":"注册与登录","ids":"[{\"id\":90},{\"id\":91},{\"id\":92},{\"id\":93},{\"id\":94}]","id":"7a763d6421d70e145b7b335894ebee44"},{"name":"技术相关","ids":"[{\"id\":112},{\"id\":113},{\"id\":114},{\"id\":391},{\"id\":392},{\"id\":393},{\"id\":394},{\"id\":395},{\"id\":396},{\"id\":115}]","id":"7485b45d4634ece4a7cf95d6920bc062"},{"name":"银行卡绑定","ids":"[{\"id\":95},{\"id\":96},{\"id\":97},{\"id\":98}]","id":"2809d1fecd6104f2b267b9228117b81c"},{"name":"找回登录密码","ids":"[{\"id\":107}]","id":"59bdd7e04ce47b7b5f70ea3a7f162dad"},{"name":"转账帮助","ids":"[{\"id\":99},{\"id\":100},{\"id\":101},{\"id\":102},{\"id\":103}]","id":"760a69069c21314712f74c6c81c6c70e"},{"name":"找回安全密码","ids":"[{\"id\":108}]","id":"b1ff1ef933004c97fde15bb2a9d0bfb9"},{"name":"优惠活动","ids":"[{\"id\":306},{\"id\":307},{\"id\":308},{\"id\":309},{\"id\":310},{\"id\":311}]","id":"8014dda7f6b8a7ae3f04b1d4b4e92a78"}]
     * name : 常见问题
     */

    private String name;
    private List<ListBean> list;
    /**
     * ids : null
     * id : 3e2815371c66ef253b5378b71bc5a17f
     */

    private Object ids;
    private String id;

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

    public Object getIds() {
        return ids;
    }

    public void setIds(Object ids) {
        this.ids = ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class ListBean {
        /**
         * name : 真实姓名
         * ids : [{"id":110},{"id":109},{"id":111}]
         * id : 3e2815371c66ef253b5378b71bc5a17f
         */

        private String name;
        private String ids;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
