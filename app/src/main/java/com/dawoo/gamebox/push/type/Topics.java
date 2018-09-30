package com.dawoo.gamebox.push.type;

public class Topics {
    //系统公告
    public static final String SYS_ANN = "SYS_ANN";
    //站点公告
    public static final String SITE_ANN = "SITE_ANN";
    //消息公告弹窗提醒
    public static final String MSITE_Player_Announcement_Notice = "MSITE_Player_Announcement_Notice";
    //消息提醒
    public static final String MCENTER_READ_COUNT = "MCENTER_READ_COUNT";
    //线上支付成功提醒
    public static final String MSITE_ONLINERECHARGE = "MSITE_ONLINERECHARGE";
    //自动刷新余额提醒
    public static final String MSITE_DIGICCY_REFRESH_BALANCE = "MSITE_DIGICCY_REFRESH_BALANCE";

    /**
     *主题订阅   动态改变
     */
    public static String SUBSCRIBE_TOPICS = "{\"_S_COMET\":\"R\",\"_S_TYPE\":\"PCENTER-popUp-Notice,SYS_ANN,SITE_ANN,PCENTER-dialog-Notice,MSITE-Player-Announcement-Notice,MCENTER_READ_COUNT,MSITE-ONLINERECHARGE,MSITE_DIGICCY_REFRESH_BALANCE,ECHO\",\"_C_COMET\":null,\"_LOCALE_TYPE\":\"zh_CN\"}";
}
