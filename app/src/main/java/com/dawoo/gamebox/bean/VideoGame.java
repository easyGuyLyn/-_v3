package com.dawoo.gamebox.bean;

import java.util.List;

/**
 * Created by benson on 18-1-9.
 */

public class VideoGame {

    /**
     * casinoGames : [{"gameId":100302,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":1,"status":"normal","apiTypeId":2,"code":"5901","name":"连环夺宝呗","cover":"/fserver/files/game/BBIN/MOBILE/bbegame113.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100383,"siteId":21,"apiId":10,"gameType":"Fish","orderNum":1,"status":"normal","apiTypeId":2,"code":"38001","name":"捕鱼大师","cover":"/fserver/files/game/BBIN/MOBILE/MasterFishing.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100381,"siteId":21,"apiId":10,"gameType":"Fish","orderNum":1,"status":"normal","apiTypeId":2,"code":"30599","name":"捕鱼达人","cover":"/fserver/files/game/BBIN/MOBILE/Fishingpeople.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100303,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":2,"status":"normal","apiTypeId":2,"code":"5902","name":"糖果派对","cover":"/fserver/files/game/BBIN/MOBILE/bbegame114.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100225,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":3,"status":"normal","apiTypeId":2,"code":"5058","name":"疯狂水果盘","cover":"/fserver/files/game/BBIN/MOBILE/bbegame36.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100250,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":4,"status":"normal","apiTypeId":2,"code":"5095","name":"斗鸡","cover":"/fserver/files/game/BBIN/MOBILE/bbegame61.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100268,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":5,"status":"normal","apiTypeId":2,"code":"5404","name":"沙滩排球","cover":"/fserver/files/game/BBIN/MOBILE/bbegame79.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100248,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":6,"status":"normal","apiTypeId":2,"code":"5093","name":"金瓶梅","cover":"/fserver/files/game/BBIN/MOBILE/bbegame59.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100198,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":7,"status":"normal","apiTypeId":2,"code":"5013","name":"传统","cover":"/fserver/files/game/BBIN/MOBILE/bbegame09.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100244,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":8,"status":"normal","apiTypeId":2,"code":"5088","name":"斗大","cover":"/fserver/files/game/BBIN/MOBILE/bbegame55.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100226,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":9,"status":"normal","apiTypeId":2,"code":"5059","name":"马戏团","cover":"/fserver/files/game/BBIN/MOBILE/bbegame37.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100236,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":10,"status":"normal","apiTypeId":2,"code":"5076","name":"数字大转轮","cover":"/fserver/files/game/BBIN/MOBILE/bbegame47.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100190,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":14,"status":"normal","apiTypeId":2,"code":"5005","name":"惑星战记","cover":"/fserver/files/game/BBIN/MOBILE/bbegame01.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100191,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":15,"status":"nor 01-09 15:15:51.049 20100-21510/? D/OkHttp: /MOBILE/bbegame03.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100193,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":17,"status":"normal","apiTypeId":2,"code":"5008","name":"猴子爬树","cover":"/fserver/files/game/BBIN/MOBILE/bbegame04.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100194,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":18,"status":"normal","apiTypeId":2,"code":"5009","name":"金刚爬楼","cover":"/fserver/files/game/BBIN/MOBILE/bbegame05.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100195,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":19,"status":"normal","apiTypeId":2,"code":"5010","name":"外星战记","cover":"/fserver/files/game/BBIN/MOBILE/bbegame06.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100196,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":20,"status":"normal","apiTypeId":2,"code":"5011","name":"西游记","cover":"/fserver/files/game/BBIN/MOBILE/bbegame07.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true},{"gameId":100197,"siteId":21,"apiId":10,"gameType":"Casino","orderNum":21,"status":"normal","apiTypeId":2,"code":"5012","name":"外星争霸","cover":"/fserver/files/game/BBIN/MOBILE/bbegame08.png","systemStatus":"normal","gameLink":"","gameMsg":"暂时无法登录，请稍候再试!","autoPay":true}]
     * page : {"pageTotal":116}
     */

    private PageBean page;
    private List<CasinoGamesBean> casinoGames;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<CasinoGamesBean> getCasinoGames() {
        return casinoGames;
    }

    public void setCasinoGames(List<CasinoGamesBean> casinoGames) {
        this.casinoGames = casinoGames;
    }

    public static class PageBean {
        /**
         * pageTotal : 116
         */

        private int pageTotal;

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }
    }

    public static class CasinoGamesBean {
        /**
         * gameId : 100302
         * siteId : 21
         * apiId : 10
         * gameType : Casino
         * orderNum : 1
         * status : normal
         * apiTypeId : 2
         * code : 5901
         * name : 连环夺宝呗
         * cover : /fserver/files/game/BBIN/MOBILE/bbegame113.png
         * systemStatus : normal
         * gameLink :
         * gameMsg : 暂时无法登录，请稍候再试!
         * autoPay : true
         */

        private int gameId;
        private int siteId;
        private int apiId;
        private String gameType;
        private int orderNum;
        private String status;
        private int apiTypeId;
        private String code;
        private String name;
        private String cover;
        private String systemStatus;
        private String gameLink;
        private String gameMsg;
        private boolean autoPay;

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public int getSiteId() {
            return siteId;
        }

        public void setSiteId(int siteId) {
            this.siteId = siteId;
        }

        public int getApiId() {
            return apiId;
        }

        public void setApiId(int apiId) {
            this.apiId = apiId;
        }

        public String getGameType() {
            return gameType;
        }

        public void setGameType(String gameType) {
            this.gameType = gameType;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getApiTypeId() {
            return apiTypeId;
        }

        public void setApiTypeId(int apiTypeId) {
            this.apiTypeId = apiTypeId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getSystemStatus() {
            return systemStatus;
        }

        public void setSystemStatus(String systemStatus) {
            this.systemStatus = systemStatus;
        }

        public String getGameLink() {
            return gameLink;
        }

        public void setGameLink(String gameLink) {
            this.gameLink = gameLink;
        }

        public String getGameMsg() {
            return gameMsg;
        }

        public void setGameMsg(String gameMsg) {
            this.gameMsg = gameMsg;
        }

        public boolean isAutoPay() {
            return autoPay;
        }

        public void setAutoPay(boolean autoPay) {
            this.autoPay = autoPay;
        }
    }
}
