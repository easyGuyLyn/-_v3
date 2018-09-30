package com.dawoo.pushsdk.manager;

import android.text.TextUtils;
import android.util.Log;

import com.dawoo.pushsdk.callback.ConnCallBackEnum;
import com.dawoo.pushsdk.callback.ConnectStatusListener;
import com.dawoo.pushsdk.callback.ReceiveMessageListener;
import com.dawoo.pushsdk.client.GBWebSocketClient;
import com.dawoo.pushsdk.util.CommonUtils;
import com.dawoo.pushsdk.util.DateTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * archar
 * 连接管理器
 */
public class GBOkhttpSocketManager {

    private static final String G = "GBWebSocketConnManager";

    private static final long RECONNECT_DELAY = 3000;//网络顺畅下，掉线重连间隔
    private static final long PING_DELAY = 10000;//心跳间隔
    private static final int RELOGIN_MAX_COUNT = 5;//最大重连次数

    private android.os.Handler mHandler = new android.os.Handler();
    private Timer mTimer;
    private TimerTask mTimerTask;
    private String mUri;
    private String mSession;
    private String mTopics;
    private String mDomain;
    private ReceiveMessageListener mReceiveMessageListener;
    private ConnectStatusListener mConnectStatusListener;
    private ReconnectLooper mReconnectLooper = new ReconnectLooper();
    private volatile boolean mIsLoginOut;
    private int mReLoginCount = 0;
    private WebSocket mWebSocket = null;


    public GBOkhttpSocketManager() {

    }

    public void login(String uri, String session, String topics, String domain, OkHttpClient client) {
        setParam(uri, session, topics, domain);
        initAndConnectWebSocket(client);
    }

    /**
     * 修改参数
     */
    private void setParam(String uri, String session, String topics, String domain) {
        mUri = CommonUtils.cutUrl(uri);
        mSession = session;
        mTopics = topics;
        mDomain = domain;
        Log.e(G, "push sdk url: " + mUri);
        Log.e(G, "push sdk sid: " + mSession);
        Log.e(G, "push sdk topics: " + mTopics);
        Log.e(G, "push sdk domain: " + mDomain);
    }

    /**
     * 心跳包，不停地发送消息给服务器
     */
    private void initTimerTask() {
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mWebSocket == null) return;
                try {
                    mWebSocket.send("");
                } catch (Exception e) {
                    reConnectDelay();
                }
                Log.i(G, "******************WebSocket*****************Ping******************" + DateTool.getTimeFromLong(DateTool.FMT_DATE_TIME_, System.currentTimeMillis()));
            }
        };
    }

    /**
     * initAndConnectWebSocket
     */
    public void initAndConnectWebSocket(OkHttpClient client) {
        if (null == mUri || null == mSession || null == mTopics || null == client) return;

        mIsLoginOut = false;
        if (mWebSocket != null) {
            mWebSocket.cancel();
        }
        cancelPingTask();
        Map header = new HashMap();
        header.put("User-Agent", "app_android;Android");
        header.put("Host", mDomain);
        header.put("Cookie", mSession);

        Request request = new Request.Builder()
                .headers(Headers.of(header))
                .url(mUri)
                .build();

        Log.e(G, "******************WebSocket 登入*********************");
        callbackConnectStatus(ConnCallBackEnum.logining.getCode(), ConnCallBackEnum.logining.getStatus());

        mWebSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                Log.e(G, " getHttpStatus  " + response.code());
                if (response.code() == 101) {
                    mReLoginCount = 0;
                    mWebSocket.send(mTopics);
                    startPingTask();
                    callbackConnectStatus(ConnCallBackEnum.connected.getCode(), ConnCallBackEnum.connected.getStatus());
                } else {
                    reConnectDelay();
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                //处理
                try {
                    JSONObject responseJson = new JSONObject(text.replace("\\", ""));
                    if (responseJson.has("result") && responseJson.has("_S_COMET")) {
                        if (responseJson.getString("result").equals("success")) {
                            Log.e(G, "推送消息主题订阅成功: " + text);
                        } else {
                            Log.e(G, "推送消息主题订阅异常: " + text);
                        }
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (mReceiveMessageListener != null
                        && !TextUtils.isEmpty(text)) {
                    //具体内容回调给界面
                    mReceiveMessageListener.onReceiveJson(text);
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);

            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);

            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                Log.e(G, " Connection onClose.code: " + code + "   reason: " + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                Log.e(G, "onFailure:   " + t.getMessage());
            }
        });
    }


    /**
     * 异常自动延时重连socket
     */
    private void reConnectDelay() {
        cancelPingTask();
        if (mIsLoginOut) return;
        mHandler.removeCallbacks(mReconnectLooper);
        mHandler.postDelayed(mReconnectLooper, RECONNECT_DELAY);
    }

    private class ReconnectLooper implements Runnable {

        @Override
        public void run() {
            if (mReLoginCount >= RELOGIN_MAX_COUNT) {
                callbackConnectStatus(ConnCallBackEnum.logout.getCode(), ConnCallBackEnum.logout.getStatus());
                return;
            }
            mReLoginCount++;
            GBWebSocketClient.getInstance().reLogin();
        }
    }

    /**
     * 断开 webSocket
     */
    public void logout() {
        if (mWebSocket == null) return;
        Log.e(G, "******************WebSocket 登出*********************");
        mIsLoginOut = true;
        cancelPingTask();
        callbackConnectStatus(ConnCallBackEnum.logout.getCode(), ConnCallBackEnum.logout.getStatus());
        mWebSocket.cancel();
        mHandler.removeCallbacks(mReconnectLooper);
    }


    /**
     * 接受消息的回调
     */
    public void setReceiveMessageLister(ReceiveMessageListener receiveMessageLister) {
        mReceiveMessageListener = receiveMessageLister;
    }

    /**
     * set连接状态实时回调
     */
    public void setConnectStatusListener(ConnectStatusListener connectStatusListener) {
        mConnectStatusListener = connectStatusListener;
    }

    /**
     * 连接状态实时回调
     */
    private void callbackConnectStatus(int code, String status) {
        if (mConnectStatusListener != null) {
            mConnectStatusListener.onConnectStatus(code, status);
        }
    }

    /**
     * 开启心跳包，每一秒发送一次空消息
     */
    public void startPingTask() {
        cancelPingTask();
        mTimer = new Timer();
        initTimerTask();
        mTimer.schedule(mTimerTask, 1000, PING_DELAY);
    }

    /**
     * 关闭心跳包
     */
    public void cancelPingTask() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

}