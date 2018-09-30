package com.dawoo.pushsdk.client;

import android.app.Application;

import com.dawoo.pushsdk.callback.ConnectStatusListener;
import com.dawoo.pushsdk.callback.ReceiveMessageListener;

import okhttp3.OkHttpClient;

public interface IGBWeSocketClient {

    /**
     * 初始化
     *
     * @param application
     */

    void init(Application application);

    /**
     * 连接服务器
     *
     * @param url     webSocket 全域名。 http或者https  sdk 内部会转ws或者wss
     * @param session 用户标识
     * @param topics  主题订阅
     * @param domain  域名 host
     *    @param client
     */
    void login(String url, String session, String topics, String domain, OkHttpClient client);

    /**
     * 重登
     */
    void reLogin();

    /**
     * 连接状态实时回调
     *
     * @param disconnectLister
     */
    void setConnectListener(ConnectStatusListener disconnectLister);


    /**
     * 设置接受消息监听
     *
     * @param receiveMessageLister
     */
    void setReceiveMessageLister(ReceiveMessageListener receiveMessageLister);


    /**
     * 断开长连接,登出
     */
    void logout();

}
