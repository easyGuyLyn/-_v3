package com.dawoo.gamebox.util.line;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.LtHostsResponse;
import com.dawoo.gamebox.bean.line.LineBean;
import com.dawoo.gamebox.bean.line.LineErrorDialogBean;
import com.dawoo.gamebox.net.RetrofitHelper;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.PushControlUtils;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.view.view.numberprogressbar.LineTaskProgressListener;
import com.dawoo.pushsdk.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.dawoo.gamebox.util.line.LineHelperUtil.getStatusCode;


/**
 * archar  天纵神武
 **/
public class LTHostCheckUtil {
    Context mContext;
    private LineHelperUtil mHelperUtil;
    public static final String TAG = "LTHostCheckUtil  ";
    public static final int Line_Success = 1; //返回成功
    public static final int Line_Faliure = 2; //返回异常
    public static final int Line_No_one_Avaliable = 3; //check结束没有可用域名

    private List<Call> mDoCheckLineList = Collections.synchronizedList(new ArrayList<>());
    //  装入域名，ip，请求花费时间 状态
    private List<LineBean> mLineList = Collections.synchronizedList(new ArrayList<>());
    private int mTotalLines;
    private volatile boolean mIsDoing;
    private int mCurrentTime = 0;

    private static LTHostCheckUtil mInstance;
    private OnFinishDomain mOnFinishDomain;
    private volatile boolean isGetHostsAllError;//全部拉取下来的host check不过   就必要拉了

    public static LTHostCheckUtil getInstance() {
        if (mInstance == null) {
            synchronized (LTHostCheckUtil.class) {
                if (mInstance == null) {
                    mInstance = new LTHostCheckUtil();
                }
            }
        }
        return mInstance;
    }

    public LTHostCheckUtil() {
        mContext = BoxApplication.getContext();
        mHelperUtil = new LineHelperUtil();
        mHelperUtil.setLineTaskBaseListener(new LineTaskProgressListener() {
            @Override
            public void onProgressBarChange(int current, int max) {

            }

            @Override
            public void onErrorSimpleReason(String result) {
                mIsDoing = false;
            }

            @Override
            public void onErrorComplexReason(LineErrorDialogBean lineErrorDialogBean) {
                mIsDoing = false;
            }

            @Override
            public void onSpalshGetLineSuccess(String domain, String ip, LineTaskProgressListener lineTaskProgressListener) {
            }

            @Override
            public void onGetLineSuccess(String domain, String ip) {
                RetrofitHelper.getInstance().reSetRetrofitHelper();
                BoxApplication.initOkHttpUtils();
                PushControlUtils.connect();
                checkLocalLtHost(DataCenter.getInstance().getDomain(), true);
            }
        });
    }

    public void setOnFinishDomain(OnFinishDomain onFinishDomain) {
        mOnFinishDomain = onFinishDomain;
    }

    /**
     * 开始获取彩票host
     */
    public void startTask() {
        if (mIsDoing) return;
        mIsDoing = true;
        if (isGetHostsAllError) {
            mHelperUtil.getLinesFromBossServer("");
            return;
        }
        mCurrentTime = 0;
        String ltDomain = SharePreferenceUtil.getLTDomain(mContext);
        if (!TextUtils.isEmpty(ltDomain)) {
            checkLocalLtHost(ltDomain, false);
        } else {
            checkLocalLtHost(DataCenter.getInstance().getDomain(), true);
        }
    }

    public void reStartTask() {
        if (mIsDoing) return;
        mIsDoing = true;
        getLtHosts();
    }


    private void reset() {
        for (Call call : mDoCheckLineList) {
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
        }
        mDoCheckLineList.clear();
        mLineList.clear();
        mTotalLines = 0;
    }

    /**
     * 获取彩票host
     */

    private void getLtHosts() {
        if (isGetHostsAllError) {
            mIsDoing = false;
            callBackState(Line_No_one_Avaliable);
            return;
        }
        reset();
        OkHttpUtils
                .post()
                .headers(NetUtil.setHeaders())
                .url(DataCenter.getInstance().getIp() + ConstantValue.LT_HOST_URL)
                .addParams("times", mCurrentTime + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "获取彩票host onError" + e.getLocalizedMessage() + "");
                        mIsDoing = false;
                        callBackState(Line_Faliure);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            Log.e(TAG, "  返回的域名json串:  " + response);
                            LtHostsResponse ltHostsResponse = GsonUtil.GsonToBean(response, LtHostsResponse.class);
                            if (ltHostsResponse.getData() != null && ltHostsResponse.getData().size() > 0) {
                                checkLtHost(ltHostsResponse.getData());
                            } else {
                                mIsDoing = false;
                                callBackState(Line_No_one_Avaliable);
                                isGetHostsAllError = true;
                            }
                        } else {
                            mIsDoing = false;
                            callBackState(Line_Faliure);
                        }
                    }
                });
    }


    /**
     * check 彩票专属域名
     */
    private void checkLtHost(List<String> hosts) {
        mTotalLines = hosts.size();
        for (int i = 0; i < hosts.size(); i++) {
            String domain = hosts.get(i);
            String url = "http://" + domain + "/__check";
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                    .followRedirects(false)
//                    .followSslRedirects(false)
//                    .sslSocketFactory(new TlsSniSocketFactory(domain), new SSLUtil.TrustAllManager())
//                    .hostnameVerifier(new TrueHostnameVerifier(domain))
                    .connectTimeout(3, TimeUnit.SECONDS);
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Connection", "close")
                    .build();
            Call call = builder.build().newCall(request);
            mDoCheckLineList.add(call);
            long startTime = System.currentTimeMillis();
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, url + " check onFailure: " + e.getLocalizedMessage());
                    long time = System.currentTimeMillis() - startTime;
                    solveLines(domain, time, 1);
                }

                @Override
                public void onResponse(Call call, Response response) {
                    String code = getStatusCode(response);
                    Log.e(TAG, "检测LT彩票域名 onResponse  code " + code);
                    long time = System.currentTimeMillis() - startTime;
                    if (LineHelperUtil.CodeEnum.OK.getCode().equals(code)) {
                        Log.e(TAG, "检测彩票域名 onResponse  OK--> ip =  " + url);
                        if (TextUtils.isEmpty(DataCenter.getInstance().getLTDomain())) {
                            DataCenter.getInstance().setLTDomain(domain);
                            Log.e(TAG, "彩票域名确认: " + domain);
                            callBackState(Line_Success);
                        }
                        solveLines(domain, time, 2);
                    } else {
                        solveLines(domain, time, 1);
                        Log.e(TAG, "检测彩票域名  onResponse = " + response);
                    }
                }
            });

        }

    }

    /**
     * check 彩票本地专属域名  sp中或默认host
     */
    private void checkLocalLtHost(String hosts, boolean isDefaltHost) {
        String domain = hosts;
        String url = "http://" + domain + "/__check";
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                    .sslSocketFactory(new TlsSniSocketFactory(domain), new SSLUtil.TrustAllManager())
//                    .hostnameVerifier(new TrueHostnameVerifier(domain))
                .connectTimeout(3, TimeUnit.SECONDS);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Connection", "close")
                .build();
        Call call = builder.build().newCall(request);
        mDoCheckLineList.add(call);
        long startTime = System.currentTimeMillis();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!isDefaltHost) {
                    Log.e(TAG, "检测sp彩票域名 onError" + e.getLocalizedMessage() + "");
                    checkLocalLtHost(DataCenter.getInstance().getDomain(), true);
                } else {
                    Log.e(TAG, "检测默认彩票域名 onError" + e.getLocalizedMessage() + "");
                    getLtHosts();
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                String code = getStatusCode(response);
                Log.e(TAG, "检测本地缓存LT彩票域名 onResponse  code " + code);
                long time = System.currentTimeMillis() - startTime;
                if (LineHelperUtil.CodeEnum.OK.getCode().equals(code)) {
                    Log.e(TAG, "检测本地缓存LT彩票域名 onResponse  OK--> ip =  " + url);
                    if (TextUtils.isEmpty(DataCenter.getInstance().getLTDomain())) {
                        DataCenter.getInstance().setLTDomain(domain);
                        Log.e(TAG, "彩票域名确认: " + domain);
                        callBackState(Line_Success);
                    }
                } else {
                    if (!isDefaltHost) {
                        checkLocalLtHost(DataCenter.getInstance().getDomain(), true);
                    } else {
                        getLtHosts();
                    }
                    Log.e(TAG, "检测本地缓存LT彩票域名  onResponse = " + response);
                }
            }
        });


    }


    /**
     * 处理所检测过的线路
     *
     * @param domain
     * @param time
     * @param state
     */

    private synchronized void solveLines(String domain, long time, int state) {

        mLineList.add(new LineBean(domain, time, state));
        Log.e(TAG, mTotalLines + " /" + mLineList.size());
        if (mTotalLines == mLineList.size()) { // 最后一个检查到齐
            Collections.sort(mLineList);
            int count = mLineList.size();
            int errCount = 0;
            for (int i = 0; i < count; i++) {
                LineBean bean = mLineList.get(i);
                int state2 = bean.getState();
                if (2 == state2) {
                    if (TextUtils.isEmpty(DataCenter.getInstance().getLTDomain())) {
                        DataCenter.getInstance().setLTDomain(bean.getDomain());
                    }
                    SharePreferenceUtil.saveLTDomain(mContext, bean.getDomain());
                    break;
                } else {
                    errCount++;
                }
            }

            mIsDoing = false;
            if (errCount == mTotalLines) {
                Log.e(TAG, "第 " + mCurrentTime + " 次   全部LT彩票域名check不过");
                if (mTotalLines == 10) {
                    mCurrentTime = mCurrentTime + 1;
                    reStartTask();
                } else {
                    reset();
                    isGetHostsAllError = true;
                    callBackState(Line_No_one_Avaliable);
                }
            } else {
                reset();
                isGetHostsAllError = true;
                callBackState(Line_No_one_Avaliable);
            }

        }
    }

    public interface OnFinishDomain {
        void onFinish(int state);
    }

    public synchronized void callBackState(int state) {
        if (mOnFinishDomain != null) {
            mOnFinishDomain.onFinish(state);
            mOnFinishDomain = null;
        }
    }
}
