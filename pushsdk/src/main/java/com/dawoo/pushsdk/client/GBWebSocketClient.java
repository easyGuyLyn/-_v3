package com.dawoo.pushsdk.client;


import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import com.dawoo.pushsdk.callback.ConnectStatusListener;
import com.dawoo.pushsdk.callback.ReceiveMessageListener;
import com.dawoo.pushsdk.manager.GBOkhttpSocketManager;
import com.dawoo.pushsdk.network.NetBroadcastReceiver;


import okhttp3.OkHttpClient;

/**
 * archar 天纵神武
 * sdk 初始化
 * 接口层
 */
public class GBWebSocketClient implements IGBWeSocketClient {

    private static final String G = "GBWebSocketClient";

    private static GBWebSocketClient mInstance;

    private String mUrl;//socket路径
    private String mSession;//用户seesion
    private String mDomain;//域名
    private String mTopics;//主题订阅
    private OkHttpClient mOkHttpClient;//okhttp


    private GBOkhttpSocketManager mConnManager = new GBOkhttpSocketManager();

    public static GBWebSocketClient getInstance() {
        if (mInstance == null) {
            synchronized (GBWebSocketClient.class) {
                if (mInstance == null) {
                    mInstance = new GBWebSocketClient();
                }
            }
        }
        return mInstance;
    }


    @Override
    public void init(Application application) {
        registerNetReceiver(application);
    }


    @Override
    public void login(String url, String session, String topics, String domain, OkHttpClient client) {
        if (url == null || session == null || topics == null || domain == null)
            return;
        mConnManager.login(url, session, topics, domain, client);
        mUrl = url;
        mSession = session;
        mTopics = topics;
        mDomain = domain;
        mOkHttpClient = client;
    }

    @Override
    public void reLogin() {
        login(mUrl, mSession, mTopics, mDomain, mOkHttpClient);
    }


    @Override
    public void setConnectListener(ConnectStatusListener disconnectLister) {
        if (mConnManager == null) return;
        mConnManager.setConnectStatusListener(disconnectLister);
    }


    @Override
    public void setReceiveMessageLister(ReceiveMessageListener receiveMessageLister) {
        if (mConnManager == null) return;
        mConnManager.setReceiveMessageLister(receiveMessageLister);
    }


    //监听网络状态
    private void registerNetReceiver(Application application) {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetBroadcastReceiver netBroadcastReceiver = new NetBroadcastReceiver();//这应该是个全局变量，为了方便观看，才写成了局部
        application.registerReceiver(netBroadcastReceiver, filter);
        netBroadcastReceiver.setNetContentListener(new NetBroadcastReceiver.NetContentListener() {
            @Override
            public void netContent(boolean isConnected) {
                if (isConnected) {
                    Log.e(G, "---------网络连接了-----");
                    login(mUrl, mSession, mTopics, mDomain, mOkHttpClient);
                } else {
                    Log.e(G, "---------网络断开了-----");
                    logout();
                }
            }
        });
    }

    @Override
    public void logout() {
        if (mConnManager != null) {
            mConnManager.logout();
        }
    }


}
