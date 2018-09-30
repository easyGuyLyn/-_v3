package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * Created by jack on 18-1-16.
 */

public class ActivityTypeList {

    /**
     * list : [{"photo":"/fserver/files/21/headImage/1/1501662622495.jpg","url":"/promo/promoDetail.html?search.id=480","id":480},{"photo":"/fserver/files/gb/21/headImage/1/1516065560111.gif","url":"/promo/promoDetail.html?search.id=563","id":563},{"photo":"/fserver/files/gb/21/headImage/1/1515651179564.jpg","url":"/promo/promoDetail.html?search.id=562","id":562},{"photo":"/fserver/files/gb/21/headImage/1/1515546685788.jpg","url":"/promo/promoDetail.html?search.id=560","id":560},{"photo":"/fserver/files/gb/21/headImage/1/1515485003375.png","url":"/promo/promoDetail.html?search.id=558","id":558},{"photo":"/fserver/files/gb/21/headImage/1/1513580266171.jpg","url":"/promo/promoDetail.html?search.id=529","id":529},{"photo":"/fserver/files/gb/21/headImage/1/1512630954061.jpg","url":"/promo/promoDetail.html?search.id=528","id":528}]
     * total : 7
     */

    private int postion;

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    private int total;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }


    public ActivityTypeList() {
        this.total = total;
        this.list = list;
    }


    public static class ListBean {
        /**
         * photo : /fserver/files/21/headImage/1/1501662622495.jpg
         * url : /promo/promoDetail.html?search.id=480
         * id : 480
         */

        private String photo;
        private String url;
        private int id;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
