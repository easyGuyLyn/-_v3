package com.dawoo.gamebox.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 游戏名称及对应游戏种类
 * Created by benson on 17-12-31.
 */

public class SiteApiRelation implements Serializable {


    /**
     * apiType : 4
     * siteApis : [{"name":"KG彩票","apiId":2,"apiTypeId":4,"cover":"/1.0.0.0/rcenter/mobile-api/api/api_logo_2.png","gameLink":"","gameMsg":null,"gameList":[{"gameId":20006,"siteId":null,"apiId":2,"gameType":"Lottery","orderNum":1,"status":"normal","apiTypeId":4,"code":"KENO","name":"快乐彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/2/KENO.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=2&apiTypeId=4&gameId=20006&gameCode=KENO","gameMsg":null,"autoPay":true}],"orderNum":3,"autoPay":true},{"name":"BBIN彩票","apiId":10,"apiTypeId":4,"cover":"/1.0.0.0/rcenter/mobile-api/api/api_logo_10.png","gameLink":"","gameMsg":null,"gameList":[{"gameId":100358,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":170,"status":"normal","apiTypeId":4,"code":"LKPA","name":"BB幸运熊猫","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/LKPA.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100358&gameCode=LKPA","gameMsg":null,"autoPay":true},{"gameId":100345,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":169,"status":"normal","apiTypeId":4,"code":"RDPK","name":"BB雷電PK","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/RDPK.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100345&gameCode=RDPK","gameMsg":null,"autoPay":true},{"gameId":100352,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":169,"status":"normal","apiTypeId":4,"code":"BBQK","name":"BB競速快樂彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BBQK.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100352&gameCode=BBQK","gameMsg":null,"autoPay":true},{"gameId":100357,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":169,"status":"normal","apiTypeId":4,"code":null,"name":"BB射龙门","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/null.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100357","gameMsg":null,"autoPay":true},{"gameId":100344,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":168,"status":"normal","apiTypeId":4,"code":"BCRE","name":"BB百家彩票-E","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BCRE.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100344&gameCode=BCRE","gameMsg":null,"autoPay":true},{"gameId":100343,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":167,"status":"normal","apiTypeId":4,"code":"BCRD","name":"BB百家彩票-D","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BCRD.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100343&gameCode=BCRD","gameMsg":null,"autoPay":true},{"gameId":100342,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":166,"status":"normal","apiTypeId":4,"code":"BCRC","name":"BB百家彩票-C","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BCRC.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100342&gameCode=BCRC","gameMsg":null,"autoPay":true},{"gameId":100341,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":165,"status":"normal","apiTypeId":4,"code":"BCRB","name":"BB百家彩票-B","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BCRB.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100341&gameCode=BCRB","gameMsg":null,"autoPay":true},{"gameId":100340,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":164,"status":"normal","apiTypeId":4,"code":"BCRA","name":"BB百家彩票-A","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BCRA.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100340&gameCode=BCRA","gameMsg":null,"autoPay":true},{"gameId":100339,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":163,"status":"normal","apiTypeId":4,"code":"AHQ3","name":"安徽快3","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/AHQ3.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100339&gameCode=AHQ3","gameMsg":null,"autoPay":true},{"gameId":100338,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":162,"status":"normal","apiTypeId":4,"code":"JSQ3","name":"江苏快3","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/JSQ3.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100338&gameCode=JSQ3","gameMsg":null,"autoPay":true},{"gameId":100337,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":161,"status":"normal","apiTypeId":4,"code":"JLQ3","name":"吉林快3","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/JLQ3.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100337&gameCode=JLQ3","gameMsg":null,"autoPay":true},{"gameId":100336,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":160,"status":"normal","apiTypeId":4,"code":"CQWC","name":"重庆百变王牌","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/CQWC.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100336&gameCode=CQWC","gameMsg":null,"autoPay":true},{"gameId":100335,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":159,"status":"normal","apiTypeId":4,"code":"SDE5","name":"山东十一运夺金","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/SDE5.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100335&gameCode=SDE5","gameMsg":null,"autoPay":true},{"gameId":100334,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":158,"status":"normal","apiTypeId":4,"code":"JXE5","name":"江西11选5","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/JXE5.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100334&gameCode=JXE5","gameMsg":null,"autoPay":true},{"gameId":100333,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":157,"status":"normal","apiTypeId":4,"code":"GDE5","name":"广东11选5","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/GDE5.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100333&gameCode=GDE5","gameMsg":null,"autoPay":true},{"gameId":100332,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":156,"status":"normal","apiTypeId":4,"code":"CAKN","name":"加拿大卑斯","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/CAKN.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100332&gameCode=CAKN","gameMsg":null,"autoPay":true},{"gameId":100331,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":155,"status":"normal","apiTypeId":4,"code":"BJKN","name":"北京快乐8","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BJKN.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100331&gameCode=BJKN","gameMsg":null,"autoPay":true},{"gameId":100330,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":154,"status":"normal","apiTypeId":4,"code":"BJPK","name":"北京PK拾","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BJPK.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100330&gameCode=BJPK","gameMsg":null,"autoPay":true},{"gameId":100329,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":153,"status":"normal","apiTypeId":4,"code":"TJSF","name":"天津十分彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/TJSF.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100329&gameCode=TJSF","gameMsg":null,"autoPay":true},{"gameId":100328,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":152,"status":"normal","apiTypeId":4,"code":"GXSF","name":"广西十分彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/GXSF.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100328&gameCode=GXSF","gameMsg":null,"autoPay":true},{"gameId":100327,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":151,"status":"normal","apiTypeId":4,"code":"CQSF","name":"重庆幸运农场","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/CQSF.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100327&gameCode=CQSF","gameMsg":null,"autoPay":true},{"gameId":100326,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":150,"status":"normal","apiTypeId":4,"code":"XJSC","name":"新疆时时彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/XJSC.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100326&gameCode=XJSC","gameMsg":null,"autoPay":true},{"gameId":100325,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":149,"status":"normal","apiTypeId":4,"code":"TJSC","name":"天津时时彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/TJSC.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100325&gameCode=TJSC","gameMsg":null,"autoPay":true},{"gameId":100324,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":148,"status":"normal","apiTypeId":4,"code":"CQSC","name":"重庆时时彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/CQSC.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100324&gameCode=CQSC","gameMsg":null,"autoPay":true},{"gameId":100323,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":147,"status":"normal","apiTypeId":4,"code":"SH3D","name":"上海时时乐","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/SH3D.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100323&gameCode=SH3D","gameMsg":null,"autoPay":true},{"gameId":100322,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":146,"status":"normal","apiTypeId":4,"code":"BBRB","name":"BB滚球王","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BBRB.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100322&gameCode=BBRB","gameMsg":null,"autoPay":true},{"gameId":100321,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":145,"status":"normal","apiTypeId":4,"code":"BBKN","name":"BB快乐彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BBKN.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100321&gameCode=BBKN","gameMsg":null,"autoPay":true},{"gameId":100320,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":144,"status":"normal","apiTypeId":4,"code":"BB3D","name":"BB3D","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BB3D.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100320&gameCode=BB3D","gameMsg":null,"autoPay":true},{"gameId":100319,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":143,"status":"normal","apiTypeId":4,"code":"BBPK","name":"BB PK3","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BBPK.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100319&gameCode=BBPK","gameMsg":null,"autoPay":true},{"gameId":100318,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":142,"status":"normal","apiTypeId":4,"code":"LDDR","name":"梯子游戏","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/LDDR.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100318&gameCode=LDDR","gameMsg":null,"autoPay":true},{"gameId":100317,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":141,"status":"normal","apiTypeId":4,"code":"PL3D","name":"排列三","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/PL3D.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100317&gameCode=PL3D","gameMsg":null,"autoPay":true},{"gameId":100316,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":140,"status":"normal","apiTypeId":4,"code":"BJ3D","name":"3D彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/BJ3D.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100316&gameCode=BJ3D","gameMsg":null,"autoPay":true},{"gameId":100315,"siteId":null,"apiId":10,"gameType":"Lottery","orderNum":139,"status":"normal","apiTypeId":4,"code":"LT","name":"六合彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/10/LT.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=10&apiTypeId=4&gameId=100315&gameCode=LT","gameMsg":null,"autoPay":true}],"orderNum":2,"autoPay":true},{"name":"天天彩票","apiId":22,"apiTypeId":4,"cover":"/1.0.0.0/rcenter/mobile-api/api/api_logo_22.png","gameLink":"","gameMsg":null,"gameList":[{"gameId":220042,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":21,"status":"normal","apiTypeId":4,"code":"jspk10","name":"极速PK10","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/jspk10.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220042&gameCode=jspk10","gameMsg":null,"autoPay":true},{"gameId":220040,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":20,"status":"normal","apiTypeId":4,"code":"xyft","name":"幸运飞艇","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/xyft.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220040&gameCode=xyft","gameMsg":null,"autoPay":true},{"gameId":220039,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":19,"status":"normal","apiTypeId":4,"code":"wfssc","name":"五分时时彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/wfssc.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220039&gameCode=wfssc","gameMsg":null,"autoPay":true},{"gameId":220038,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":18,"status":"normal","apiTypeId":4,"code":"sfssc","name":"三分时时彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/sfssc.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220038&gameCode=sfssc","gameMsg":null,"autoPay":true},{"gameId":220037,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":17,"status":"normal","apiTypeId":4,"code":"efssc","name":"二分时时彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/efssc.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220037&gameCode=efssc","gameMsg":null,"autoPay":true},{"gameId":220036,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":16,"status":"normal","apiTypeId":4,"code":"ffssc","name":"分分时时彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/ffssc.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220036&gameCode=ffssc","gameMsg":null,"autoPay":true},{"gameId":220035,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":15,"status":"normal","apiTypeId":4,"code":"tcpl3","name":"体彩排列3","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/tcpl3.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220035&gameCode=tcpl3","gameMsg":null,"autoPay":true},{"gameId":220034,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":14,"status":"normal","apiTypeId":4,"code":"fc3d","name":"福彩3D","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/fc3d.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220034&gameCode=fc3d","gameMsg":null,"autoPay":true},{"gameId":220033,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":13,"status":"normal","apiTypeId":4,"code":"bjkl8","name":"北京快乐8","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/bjkl8.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220033&gameCode=bjkl8","gameMsg":null,"autoPay":true},{"gameId":220032,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":12,"status":"normal","apiTypeId":4,"code":"gdkl10","name":"广东快乐十分","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/gdkl10.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220032&gameCode=gdkl10","gameMsg":null,"autoPay":true},{"gameId":220031,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":11,"status":"normal","apiTypeId":4,"code":"cqxync","name":"重庆幸运农场","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/cqxync.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220031&gameCode=cqxync","gameMsg":null,"autoPay":true},{"gameId":220018,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":10,"status":"normal","apiTypeId":4,"code":"gxk3","name":" 广西快３","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/gxk3.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220018&gameCode=gxk3","gameMsg":null,"autoPay":true},{"gameId":220017,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":9,"status":"normal","apiTypeId":4,"code":"hbk3","name":" 湖北快３","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/hbk3.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220017&gameCode=hbk3","gameMsg":null,"autoPay":true},{"gameId":220016,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":8,"status":"normal","apiTypeId":4,"code":"ahk3","name":" 安徽快３","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/ahk3.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220016&gameCode=ahk3","gameMsg":null,"autoPay":true},{"gameId":220012,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":7,"status":"normal","apiTypeId":4,"code":"jsk3","name":"江苏快３","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/jsk3.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220012&gameCode=jsk3","gameMsg":null,"autoPay":true},{"gameId":220011,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":6,"status":"normal","apiTypeId":4,"code":"tjssc","name":"天津时时彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/tjssc.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220011&gameCode=tjssc","gameMsg":null,"autoPay":true},{"gameId":220010,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":5,"status":"normal","apiTypeId":4,"code":"xjssc","name":"新疆时时彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/xjssc.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220010&gameCode=xjssc","gameMsg":null,"autoPay":true},{"gameId":220009,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":4,"status":"normal","apiTypeId":4,"code":"hklhc","name":"香港六合彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/hklhc.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220009&gameCode=hklhc","gameMsg":null,"autoPay":true},{"gameId":220007,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":3,"status":"normal","apiTypeId":4,"code":"cqssc","name":"重庆时时彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/cqssc.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220007&gameCode=cqssc","gameMsg":null,"autoPay":true},{"gameId":220030,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":2,"status":"normal","apiTypeId":4,"code":"xy28","name":"幸运28","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/xy28.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220030&gameCode=xy28","gameMsg":null,"autoPay":true},{"gameId":220008,"siteId":null,"apiId":22,"gameType":"Lottery","orderNum":1,"status":"normal","apiTypeId":4,"code":"bjpk10","name":"北京pk10","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/22/bjpk10.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=22&apiTypeId=4&gameId=220008&gameCode=bjpk10","gameMsg":null,"autoPay":true}],"orderNum":1,"autoPay":true}]
     * apiTypeName : 彩票
     * cover : /1.0.0.0/rcenter/mobile-api/api_type_4.png
     * level : true
     */

    private int apiType;
    private String apiTypeName;
    private String cover;
    private boolean level;
    private List<SiteApisBean> siteApis;

    public int getApiType() {
        return apiType;
    }

    public void setApiType(int apiType) {
        this.apiType = apiType;
    }

    public String getApiTypeName() {
        return apiTypeName;
    }

    public void setApiTypeName(String apiTypeName) {
        this.apiTypeName = apiTypeName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public boolean isLevel() {
        return level;
    }

    public void setLevel(boolean level) {
        this.level = level;
    }

    public List<SiteApisBean> getSiteApis() {
        return siteApis;
    }

    public void setSiteApis(List<SiteApisBean> siteApis) {
        this.siteApis = siteApis;
    }

    public static class SiteApisBean implements Serializable {

        /**
         * name : KG彩票
         * apiId : 2
         * apiTypeId : 4
         * cover : /1.0.0.0/rcenter/mobile-api/api/api_logo_2.png
         * gameLink :
         * gameMsg : null
         * gameList : [{"gameId":20006,"siteId":null,"apiId":2,"gameType":"Lottery","orderNum":1,"status":"normal","apiTypeId":4,"code":"KENO","name":"快乐彩","cover":"/ftl/commonPage/images/game_logo/null/null/zh_CN/2/KENO.png","systemStatus":"normal","gameLink":"/origin/getGameLink.html?apiId=2&apiTypeId=4&gameId=20006&gameCode=KENO","gameMsg":null,"autoPay":true}]
         * orderNum : 3
         * autoPay : true
         */

        private String name;
        private int apiId;
        private int apiTypeId;
        private String cover;
        private String gameLink;
        private Object gameMsg;
        private int orderNum;
        private boolean autoPay;
        private List<GameListBean> gameList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getApiId() {
            return apiId;
        }

        public void setApiId(int apiId) {
            this.apiId = apiId;
        }

        public int getApiTypeId() {
            return apiTypeId;
        }

        public void setApiTypeId(int apiTypeId) {
            this.apiTypeId = apiTypeId;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getGameLink() {
            return gameLink;
        }

        public void setGameLink(String gameLink) {
            this.gameLink = gameLink;
        }

        public Object getGameMsg() {
            return gameMsg;
        }

        public void setGameMsg(Object gameMsg) {
            this.gameMsg = gameMsg;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public boolean isAutoPay() {
            return autoPay;
        }

        public void setAutoPay(boolean autoPay) {
            this.autoPay = autoPay;
        }

        public List<GameListBean> getGameList() {
            return gameList;
        }

        public void setGameList(List<GameListBean> gameList) {
            this.gameList = gameList;
        }

        public static class GameListBean {
            /**
             * gameId : 20006
             * siteId : null
             * apiId : 2
             * gameType : Lottery
             * orderNum : 1
             * status : normal
             * apiTypeId : 4
             * code : KENO
             * name : 快乐彩
             * cover : /ftl/commonPage/images/game_logo/null/null/zh_CN/2/KENO.png
             * systemStatus : normal
             * gameLink : /origin/getGameLink.html?apiId=2&apiTypeId=4&gameId=20006&gameCode=KENO
             * gameMsg : null
             * autoPay : true
             */

            private int gameId;
            private Object siteId;
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
            private Object gameMsg;
            private boolean autoPay;

            public int getGameId() {
                return gameId;
            }

            public void setGameId(int gameId) {
                this.gameId = gameId;
            }

            public Object getSiteId() {
                return siteId;
            }

            public void setSiteId(Object siteId) {
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

            public Object getGameMsg() {
                return gameMsg;
            }

            public void setGameMsg(Object gameMsg) {
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
}
