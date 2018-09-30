package com.dawoo.gamebox.util.line;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.line.BaseLineBean;
import com.dawoo.gamebox.bean.line.LineBean;
import com.dawoo.gamebox.bean.line.LineErrorDialogBean;
import com.dawoo.gamebox.bean.line.Lines;
import com.dawoo.gamebox.net.TlsSniSocketFactory;
import com.dawoo.gamebox.net.TrueHostnameVerifier;
import com.dawoo.gamebox.util.FastJsonUtils;
import com.dawoo.gamebox.util.NetTool;
import com.dawoo.gamebox.util.SSLUtil;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.util.SingleToast;
import com.dawoo.gamebox.view.view.numberprogressbar.LineTaskProgressListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.dawoo.gamebox.ConstantValue.BASE_URL_1_IP;
import static com.dawoo.gamebox.ConstantValue.BASE_URL_2_IP;
import static com.dawoo.gamebox.ConstantValue.BASE_URL_3_IP;
import static com.dawoo.gamebox.ConstantValue.BASE_URL_HEAD;
import static com.dawoo.gamebox.ConstantValue.BOSS_API_1;
import static com.dawoo.gamebox.ConstantValue.LINE_URL;
import static com.dawoo.gamebox.net.RetrofitHelper.DEFAULT_READ_TIMEOUT_SECONDS;
import static com.dawoo.gamebox.net.RetrofitHelper.DEFAULT_TIMEOUT_SECONDS;
import static com.dawoo.gamebox.net.RetrofitHelper.DEFAULT_WRITE_TIMEOUT_SECONDS;
import static com.dawoo.gamebox.util.line.LineProgressString.CODE_001;
import static com.dawoo.gamebox.util.line.LineProgressString.CODE_002;
import static com.dawoo.gamebox.util.line.LineProgressString.CODE_003;
import static com.dawoo.gamebox.util.line.LineProgressString.LINE_RESULT_CHECK_IP_UNPASS;
import static com.dawoo.gamebox.util.line.LineProgressString.LINE_RESULT_GET_ALIYUN_FAILURE;
import static com.dawoo.gamebox.util.line.LineProgressString.LINE_RESULT_GET_FAILURE;
import static com.dawoo.gamebox.util.line.LineProgressString.LINE_RESULT_RETURN_EMPTY;
import static com.dawoo.gamebox.util.line.LineProgressString.LINE_RESULT_RETURN_EXCEPTION;
import static com.dawoo.gamebox.util.line.LineProgressString.LINE_RESULT_RETURN_IPS_EMPTY;

/**
 * 线路选择工具类
 * Created by benson on 18-4-24.
 */

public class LineHelperUtil {
    public static final String TAG = "LineHelperUtil  ";
    public static final int progress_finish_GetBaseLine = 20;//获取线路服务器 分配量
    public static final int progress_on_GettingLine = 19;//正在拉取线路 分配量
    public static final int progress_start_CheckDomain = 40;//检测域名确定端口---开始
    public static final int progress_finish_CheckDomain = 50;//检测域名确定端口---结束
    public static final int progress_finish_CheckLine = 80;//检测域名---结束


    private final Context mContext;
    private StringBuffer mDomains = new StringBuffer();//错误的域名集合
    private StringBuffer mIps = new StringBuffer();//错误的ip集合
    private StringBuffer mErrorMessages = new StringBuffer();//错误的msg集合
    private StringBuffer mCodes = new StringBuffer();//错误的code集合

    private String mark; //上传错误信息时的辨认值

    private String MODE_HTTPS_VALUE = "https://";
    private String MODE_HTTP_VALUE = "http://";
    private String MODE_PORT_8989_VALUE = ":8989";
    private String MODE_PORT_8787_VALUE = ":8787";
    private List<Call> mDoCheckLineList = Collections.synchronizedList(new ArrayList<>());
    //  装入域名，ip，请求花费时间 状态
    private List<LineBean> LineList = Collections.synchronizedList(new ArrayList<>());
    private int mTotalLines;
    private long interval = 60 * 1000 * 60 * 24;
    private LineTaskProgressListener mLineTaskProgressListener;
    private LineTaskBaseListener mLineTaskBaseListener;
    private String mAliPlayUrl;//阿里云服务器
    private String mBossUrl;//boss线路服务器
    private String mBossHost;//某boss服务器的host  为空的话  都是走默认boss域名获取线路的
    private String mLineJson;//当前操作中返回的线路json串
    private int mErrorCount = 0;
    private LineErrorDialogBean mLineErrorDialogBean = new LineErrorDialogBean();

    public LineHelperUtil() {
        mContext = BoxApplication.getContext();

        String currentTime = System.currentTimeMillis() + "";
        mark = currentTime.substring(currentTime.length() - 6);


        List<String> list1 = Arrays.asList(new String[]{BASE_URL_1_IP, BASE_URL_2_IP, BASE_URL_3_IP});
        Collections.shuffle(list1);
        //随机取一条
        mAliPlayUrl = list1.get(0);//阿里云服务器


        List<String> list2 = Arrays.asList(ConstantValue.fecthUrl);
        Collections.shuffle(list2);
        //随机取一条
        mBossUrl = list2.get(0);//boss服务器

    }

    public void setLineTaskBaseListener(LineTaskBaseListener lineTaskBaseListener) {
        mLineTaskBaseListener = lineTaskBaseListener;
    }

    public void setLineTaskProgressListener(LineTaskProgressListener lineTaskProgressListener) {
        mLineTaskProgressListener = lineTaskProgressListener;
    }

    /**
     * 检测sp线路
     */
    public void checkSp() {
        mTotalLines = 0;
        mDoCheckLineList.clear();
        LineList.clear();
        mErrorCount = 0;
        // 取出本地域名线路
        String spDomain = SharePreferenceUtil.getDomain(mContext);
        String spIp = SharePreferenceUtil.getIp(mContext);
        long time = SharePreferenceUtil.getTime(mContext);
        long currentTime = System.currentTimeMillis();
        if (TextUtils.isEmpty(spDomain) || TextUtils.isEmpty(spIp)) {
            getLinesFromBossServer("");
        } else if (currentTime - time > interval) {
            getLinesFromBossServer("");
        } else {
            Log.e(TAG, "sp中的最佳线路: domain: " + spDomain + "  ip: " + spIp);
            callBackProgress(progress_finish_CheckDomain);
            doCheckIp(true, spDomain, spIp);
        }
    }

    /**
     * 从默认的boss服务器 获取线路列表
     */

    public synchronized void getLinesFromBossServer(String json_ips) {
        List<String> list2 = Arrays.asList(ConstantValue.fecthUrl);
        Collections.shuffle(list2);
        //随机取一条
        mBossUrl = list2.get(0);//boss服务器
        Log.e(TAG, "从默认 " + mBossUrl + " 获取线路");

        getLinesFromSever("", mBossUrl, json_ips, true);

    }


    /**
     * 从阿里云获取线路服务器列表
     */
    private synchronized void getBossUrlFromAliplay(String json_ips) {
        callBackProgress(0);
        mTotalLines = 0;
        mDoCheckLineList.clear();
        LineList.clear();
        mBossHost = "";

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//失败重连
        Request request = new Request.Builder().url(mAliPlayUrl).get().build();
        builder.build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "getBossUrlFromAliplay onFailure  " + e.getMessage());
                errorPrompt(e.getMessage());
                callBackProgress(progress_finish_GetBaseLine);

                callBackComplexReason(CODE_001);
                callBackErrorSimpleReason(LINE_RESULT_GET_ALIYUN_FAILURE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBackProgress(progress_finish_GetBaseLine);
                if (response.code() == 200) {
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        Log.e(TAG, "BossUrlJson :" + json);
                        BaseLineBean baseLineBean = FastJsonUtils.toBean(json, BaseLineBean.class);
                        String host = baseLineBean.getHost();
                        List<String> ips = baseLineBean.getIps();
                        if (null != ips && ips.size() > 0) {
                            List<String> urls = new ArrayList<>();
                            for (String ip : ips) {
                                urls.add(BASE_URL_HEAD + ip + BOSS_API_1 + LINE_URL);
                            }
                            //輸出結果
                            Collections.shuffle(urls);
                            mBossUrl = urls.get(0);
                            mBossHost = host;
                        }
                    } else {
                        callBackComplexReason(CODE_001);
                        callBackErrorSimpleReason(LINE_RESULT_GET_ALIYUN_FAILURE);
                        return;
                    }
                    getLinesFromSever(mBossHost, mBossUrl, json_ips, false);
                    return;
                }
                callBackComplexReason(CODE_001);
                callBackErrorSimpleReason(LINE_RESULT_GET_ALIYUN_FAILURE);
            }
        });
    }

    /**
     * 从某线路服务器 获取线路列表
     */
    private void getLinesFromSever(String host, String url, String json_ips, boolean isFromBoss) {

        if (TextUtils.isEmpty(json_ips)) {
            json_ips = "ips";
        }


        callBackProgress(progress_finish_GetBaseLine);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//失败重连
        builder.sslSocketFactory(SSLUtil.createSSLSocketFactory(), new SSLUtil.TrustAllManager())
                .hostnameVerifier(new SSLUtil.TrustAllHostnameVerifier());

        String parm_code = "code=" + mContext.getResources().getString(R.string.app_code);
        String parm_sid = "s=" + mContext.getResources().getString(R.string.app_sid);
        String parm_type = "type=" + json_ips;

        String parms = "?" + parm_code + "&" + parm_sid + "&" + parm_type;

//        RequestBody body = new FormBody.Builder()
//                .add("code", mContext.getResources().getString(R.string.app_code))
//                .add("s", mContext.getResources().getString(R.string.app_sid))
//                .add("type", json_ips)
//                .build();
        Request request;
        if (mContext.getResources().getString(R.string.app_code).equals(ConstantValue.TEST_APP_CODE)) {//测试环境
            request = new Request.Builder().url(ConstantValue.BASE_URL_4 + LINE_URL + parms).get().build();
        } else {
            if (TextUtils.isEmpty(host)) {
                request = new Request.Builder().url(url + parms).get().build();
            } else {
                Log.e(TAG, "\n mBossHost: " + host);
                Log.e(TAG, "\n mBossUrl: " + url + parms);
                request = new Request.Builder().url(url + parms).get().addHeader("Host", host).build();
            }
        }
        String finalJson_ips = json_ips;
        builder.build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (isFromBoss) {
                    Log.e(TAG, "从默认boss 获取线路 onFailure: " + e.getMessage());
                    getBossUrlFromAliplay(finalJson_ips);
                    return;
                }
                callBackProgress(progress_finish_GetBaseLine + progress_on_GettingLine);

                callBackErrorSimpleReason(LINE_RESULT_GET_FAILURE);
                callBackComplexReason(CODE_002);

                errorPrompt(e.getMessage());
                Log.e(TAG, "获取线路 onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                praseAndCheckAll(response, isFromBoss, finalJson_ips);
            }
        });
    }

    /**
     * 解析和开启循环检查
     *
     * @param response
     */
    private void praseAndCheckAll(Response response, boolean isFromBoss, String json_ips) {

        callBackProgress(progress_finish_GetBaseLine + progress_on_GettingLine);

        if (200 != response.code()) {
            if (isFromBoss) {
                Log.e(TAG, "从默认boss 获取线路 onFailure: code 不是200 ");
                getBossUrlFromAliplay(json_ips);
                return;
            }
            callBackErrorSimpleReason(LINE_RESULT_RETURN_EXCEPTION);
            callBackComplexReason(CODE_002);
            return;
        }

        String data = "";
        try {
            data = response.body().string();
            Log.e(TAG, "获取线路 success:" + data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(data)) {
            SingleToast.showMsg(LINE_RESULT_RETURN_EMPTY);
            callBackErrorSimpleReason(LINE_RESULT_RETURN_EMPTY);
            callBackComplexReason(CODE_002);
            return;
        }
        try {
            Gson gson = new Gson();
            Lines lines = gson.fromJson(data, Lines.class);
            if (lines == null || TextUtils.isEmpty(lines.getDomain())) {
                SingleToast.showMsg(LINE_RESULT_RETURN_EMPTY);
                callBackErrorSimpleReason(LINE_RESULT_RETURN_EMPTY);
                callBackComplexReason(CODE_002);
                return;
            }
            if (lines.getIps() == null || lines.getIps().size() == 0) {
                SingleToast.showMsg(LINE_RESULT_RETURN_EMPTY);
                callBackErrorSimpleReason(LINE_RESULT_RETURN_IPS_EMPTY);
                callBackComplexReason(CODE_002);
                return;
            }
            mLineJson = data;
            SharePreferenceUtil.saveisStartBestLineService(mContext, lines.getIps().size() > 1);
            mTotalLines += lines.getIps().size();
            callBackProgress(progress_start_CheckDomain);
            for (String ip : lines.getIps()) {
                String ip_all = MODE_HTTPS_VALUE + ip + MODE_PORT_8989_VALUE;
                doCheckIp(false, lines.getDomain(), ip_all);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBackErrorSimpleReason(LINE_RESULT_RETURN_EXCEPTION);
            callBackComplexReason(CODE_002);
        }
    }


    /**
     * 确定端口
     * 检测ip
     */
    private void doCheckIp(boolean isSpCheck, String domain, String ip_all) {
        final String[] url = {ip_all + "/__check"};
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(new TlsSniSocketFactory(domain), new SSLUtil.TrustAllManager())
                .hostnameVerifier(new TrueHostnameVerifier(domain))
//                .followRedirects(false)
//                .followSslRedirects(false)
                .connectTimeout(3, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        Request request = new Request.Builder()
                .url(url[0])
                .get()
                .addHeader("Host", domain)
                .addHeader("Connection", "close")
                .build();
        Call call = builder.build().newCall(request);
        mDoCheckLineList.add(call);
        long startTime = System.currentTimeMillis();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, ip_all + " check onFailure: " + e.getLocalizedMessage());
                appendErrorLine(domain, url[0], url[0] + " " + e.getMessage(), ip_all);
                if (isSpCheck) {
                    errorPrompt(e.getMessage());
                    getLinesFromBossServer("");
                    return;
                }

                if (url[0].contains(MODE_HTTPS_VALUE) && url[0].contains(MODE_PORT_8989_VALUE)) {
                    url[0] = url[0].replace(MODE_HTTPS_VALUE, MODE_HTTP_VALUE);
                    url[0] = url[0].replace(MODE_PORT_8989_VALUE, MODE_PORT_8787_VALUE);
                    url[0] = url[0].replace("/__check", "");
                    doCheckIp(isSpCheck, domain, url[0]);
                    return;
                } else if (url[0].contains(MODE_HTTP_VALUE) && url[0].contains(MODE_PORT_8787_VALUE)) {
                    url[0] = url[0].replace(MODE_HTTP_VALUE, MODE_HTTPS_VALUE);
                    url[0] = url[0].replace(MODE_PORT_8787_VALUE, "");
                    url[0] = url[0].replace("/__check", "");
                    doCheckIp(isSpCheck, domain, url[0]);
                    return;
                } else if (url[0].contains(MODE_HTTPS_VALUE)) {
                    url[0] = url[0].replace(MODE_HTTPS_VALUE, MODE_HTTP_VALUE);
                    url[0] = url[0].replace("/__check", "");
                    doCheckIp(isSpCheck, domain, url[0]);
                    return;
                }
                callBackProgress(progress_finish_CheckDomain);
                long time = System.currentTimeMillis() - startTime;
                solveLines(isSpCheck, domain, ip_all, time, 1);
            }

            @Override
            public void onResponse(Call call, Response response) {
                String code = getStatusCode(response);
                Log.e(TAG, "检测ip onResponse  code " + code);
                long time = System.currentTimeMillis() - startTime;
                if (CodeEnum.OK.getCode().equals(code)) {
                    Log.e(TAG, "检测ip onResponse  OK--> ip =  " + url[0]);
                    solveLines(isSpCheck, domain, ip_all, time, 2);
                } else {
                    if (isSpCheck) {
                        getLinesFromBossServer("");
                        return;
                    }
                    solveLines(isSpCheck, domain, ip_all, time, 1);
                    appendErrorLine(domain, url[0], "能check通，但是response.message() 不是OK", code);
                    Log.e(TAG, "检测ip  onResponse = " + response);
                }
            }
        });
    }

    /**
     * 存储
     *
     * @param domain
     * @param ip
     */

    private void setDomainAndIp(String domain, String ip) {
        SharePreferenceUtil.saveDomain(mContext, domain);
        SharePreferenceUtil.saveIp(mContext, ip);
        SharePreferenceUtil.saveTime(mContext, System.currentTimeMillis());
        DataCenter.getInstance().setDomain(domain);
        DataCenter.getInstance().setIp(ip);
        Log.e(TAG, "目前使用的线路 *******domain: " + domain + " ip: " + ip);

    }

    /**
     * 成功获取到线路后
     *
     * @param domain
     * @param ip
     */
    private void afterGetLineSuccess(String domain, String ip) {
        if (TextUtils.isEmpty(domain) || TextUtils.isEmpty(ip)) {
            callBackErrorSimpleReason(LINE_RESULT_RETURN_EXCEPTION);
            callBackComplexReason(CODE_002);
            return;
        }
        setDomainAndIp(domain, ip);

        if (mLineTaskProgressListener != null) {
            callBackProgress(progress_finish_CheckLine);
            mLineTaskProgressListener.onSpalshGetLineSuccess(domain, ip, mLineTaskProgressListener);
        }

        if (mLineTaskBaseListener != null) {
            mLineTaskBaseListener.onGetLineSuccess(domain, ip);
        }
    }


    /**
     * 向外回调进度
     *
     * @param progress
     */
    private void callBackProgress(int progress) {
        if (mLineTaskProgressListener != null) {
            mLineTaskProgressListener.onProgressBarChange(progress, 100);
        }
    }

    /**
     * 向外简单错误原因
     *
     * @param msg
     */
    private void callBackErrorSimpleReason(String msg) {
        if (mLineTaskProgressListener != null) {
            mLineTaskProgressListener.onErrorSimpleReason(msg);
        }
    }

    /**
     * 向外回调错误 bean
     *
     * @param code
     */
    private void callBackComplexReason(String code) {
        if (mLineTaskProgressListener != null) {
            mLineErrorDialogBean.setCode(code);
            mLineTaskProgressListener.onErrorComplexReason(mLineErrorDialogBean);
        }
    }

    /**
     * 收集错误域名的信息
     *
     * @param domain
     * @param ip
     * @param errMsg
     * @param errCode
     */
    private void appendErrorLine(String domain, String ip, String errMsg, String errCode) {
        if (mDomains.length() == 0) {
            mDomains.append(domain);
        } else {
            mDomains.append(";" + domain);
        }

        if (mIps.length() == 0) {
            mIps.append(ip);
        } else {
            mIps.append(";" + ip);
        }

        if (mErrorMessages.length() == 0) {
            mErrorMessages.append(errMsg);
        } else {
            mErrorMessages.append(";" + errMsg);
        }

        if (mCodes.length() == 0) {
            mCodes.append(errCode);
        } else {
            mCodes.append(";" + errCode);
        }
    }

    /**
     * 根据头部信息获取请求状态
     */
    public static String getStatusCode(Response response) {
        if (response.priorResponse() != null) {
            String headerStatus = response.priorResponse().header("headerStatus");
            if (LineHelperUtil.CodeEnum.DUE.getCode().equals(headerStatus)) {
                return LineHelperUtil.CodeEnum.DUE.getCode().trim();

            } else if (LineHelperUtil.CodeEnum.Guo.getCode().equals(headerStatus)) {
                return LineHelperUtil.CodeEnum.Guo.getCode().trim();
            }
        }

        if (response.code() != 200) {
            return "";
        }
//        return response.message();
        try {
            return response.body().string().trim().toUpperCase();
        } catch (IOException e) {
            return "";
        }
    }


    public void upLoadErrorInfo() {
        for (Call call : mDoCheckLineList) {
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
        }
        UploadErrorLinesUtil.upload(mDomains.toString(), mIps.toString(), mErrorMessages.toString(), mCodes.toString(), mark);
    }


    /**
     * 网络请求异常提示
     *
     * @param msg
     */
    private void errorPrompt(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (msg.contains("Failed to connect to") || msg.contains("associated")) {
            if (NetTool.isConnected(mContext)) {
                if (mLineTaskProgressListener != null) {
                    mLineTaskProgressListener.onErrorSimpleReason(LINE_RESULT_GET_FAILURE);
                }
            } else {
                SingleToast.showMsg(mContext.getString(R.string.unNet));
                if (mLineTaskProgressListener != null) {
                    mLineTaskProgressListener.onErrorSimpleReason(mContext.getString(R.string.unNet));
                }
            }
        }
    }


    /**
     * 错误代码
     * Created by fei on 17-7-29.
     */
    public enum CodeEnum {
        OK("OK", "请求正确"),
        SUCCESS("200", "请求成功"),
        S_DUE("600", "Session过期"),
        S_KICK_OUT("606", "Session过期"),
        DUE("604", "域名过期"),
        Guo("309", "国内访问"),
        Domain_error("603", "域名错误");

        private String code;
        private String name;

        CodeEnum(String code, String name) {
            this.code = code;
            this.name = name;
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
    }

    /**
     * 处理所检测过的线路
     *
     * @param isSpCheck
     * @param domain
     * @param ip
     * @param time
     * @param state
     */

    private synchronized void solveLines(boolean isSpCheck, String domain, String ip, long time, int state) {
        if (isSpCheck && 2 == state) {
            afterGetLineSuccess(domain, ip);
            return;
        }

        LineList.add(new LineBean(domain, ip, time, state));
        Log.e(TAG, mTotalLines + " /" + LineList.size());
        if (mTotalLines == LineList.size()) { // 最后一个检查到齐
            callBackProgress(progress_finish_CheckDomain);
            Collections.sort(LineList);
            int count = LineList.size();
            int errCount = 0;
            for (int i = 0; i < count; i++) {
                LineBean bean = LineList.get(i);
                int state2 = bean.getState();
                if (2 == state2) {
                    afterGetLineSuccess(bean.getDomain(), bean.getIp());
                    break;
                } else {
                    errCount++;
                }
            }
            if (errCount == mTotalLines) {
                upLoadErrorInfo();
                mErrorCount++;
                if (mErrorCount < 4) {
                    if (TextUtils.isEmpty(mBossHost)) {
                        getLinesFromSever(mBossHost, mBossUrl, mLineJson, false);
                    } else {
                        getLinesFromSever(mBossHost, mBossUrl, mLineJson, true);
                    }
                    return;
                }
                Log.e(TAG, "errCount " + errCount);
                callBackProgress(progress_finish_CheckDomain + (progress_finish_CheckLine - progress_finish_CheckDomain) / 2);
                callBackErrorSimpleReason(LINE_RESULT_CHECK_IP_UNPASS);
                callBackComplexReason(CODE_003);
            }

        }
    }
}
