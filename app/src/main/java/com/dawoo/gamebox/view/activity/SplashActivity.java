package com.dawoo.gamebox.view.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.RegularTool;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.packageref.PackageInfoUtil;
import com.dawoo.gamebox.BuildConfig;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.line.CheckLog;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.util.AutoLogin;
import com.dawoo.gamebox.util.SSLUtil;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.util.line.UploadErrorLinesUtil;
import com.dawoo.gamebox.view.view.numberprogressbar.NumberProgressBar;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.dawoo.gamebox.ConstantValue.DEVELOPE;
import static com.dawoo.gamebox.ConstantValue.fecthUrl;
import static com.dawoo.gamebox.util.line.LineHelperUtil.progress_finish_CheckDomain;
import static com.dawoo.gamebox.util.line.LineHelperUtil.progress_finish_CheckLine;


/**
 * 过渡页
 * 检查线路
 * Created by benson on 17-12-27.
 */

public class SplashActivity extends BaseActivity {
    public static final int progress_finish_line_1 = 10;//第一条线路---结束
    public static final int progress_finish_line_2 = 20;//第二条线路---结束
    public static final int progress_finish_line_3 = 30;//第三条线路---结束
    public static final String TAG = "SplashActivity";
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;
    @BindView(R.id.tvLoading)
    TextView mTvLoading;
    @BindView(R.id.ivLogo)
    ImageView mIvLogo;
    @BindView(R.id.tvCopyright)
    TextView mTvCopyright;
    @BindView(R.id.log_recyclerview)
    RecyclerView mLogRecyclerView;
    @BindView(R.id.numberBar)
    NumberProgressBar mNumberBar;
    @BindView(R.id.b_reGet)
    Button mBReGet;
    private SplashActivity mContext;
    private boolean isHttps;
    private String invalidUrl;  // 是否有效域名
    private boolean isGoon = true;  // 是否继续Task
    private List<FetchLineTask> mFetchLineTaskList = new ArrayList<>();
    private LogQuickAdapter mLogQuickAdapter;
    private volatile boolean isFirstTime = true;//如果是第一次进入，不设置端口直接check。
    private int CHECKING = 0;
    private int CHECK_FAIL = 1;
    private int CHECK_SUCCESS = 2;
    private int GET_FAIL = 3;
    private int GET_SUCCESS = 4;
    private int STOP_GET = 5;

    private StringBuilder mDomains = new StringBuilder();//错误的线路集合
    private StringBuilder mErrorMessages = new StringBuilder();//错误的msg集合
    private StringBuilder mCodes = new StringBuilder();//错误的code集合
    private String mark; //上传错误信息时的辨认值

    private volatile boolean mIsJumpActivity;
    private int mGetLinesFaileCount;
    private Response domainResponse;
    String HTTP_PORT = ":8787";
    String HTTPS_PORT = ":8989";


    @Override
    protected void createLayoutView() {
        setContentView(R.layout.acitivity_splash);
    }

    @Override
    protected void initViews() {
        mContext = this;
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 设置applogo
        Glide.with(this).load(R.mipmap.app_logo).into(mIvLogo);
        //  设置Copyright
        mTvCopyright.setText(String.format("Copyright © %s Reserved. v%s", getResources().getString(R.string.app_name), PackageInfoUtil.getVersionName(this)));
        mTvLoading.setText("正在获取线路,请稍后");
        mLogQuickAdapter = new LogQuickAdapter(R.layout.log_check_splash_acitivity_list_item_view);
        mLogRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true));
        mLogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLogRecyclerView.setAdapter(mLogQuickAdapter);
    }

    @Override
    protected void initData() {
        String currentTime = System.currentTimeMillis() + "";
        mark = currentTime.substring(currentTime.length() - 6);


        if (DEVELOPE) {
            DataCenter.getInstance().setDomain(BuildConfig.HOST_URL);
            DataCenter.getInstance().setIp(BuildConfig.HOST_URL);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    jumpActivity();
                }
            });
            return;
        }

        // 检测域名
        if (getResources().getString(R.string.app_code).equals(ConstantValue.TEST_APP_CODE)) {
            fetchLines(0);
        } else {
            checkSpLine();
        }
    }


    /**
     * 检测本地保存的线路是否可用
     */
    void checkSpLine() {
        // 取出本地域名线路
        String spDomain = SharePreferenceUtil.getDomain(this);
        LogUtils.e("checkSpLine() --> SharePreferenceUtil.getDomain = " + spDomain);
        if (TextUtils.isEmpty(spDomain)) {
            fetchLines(0);
        } else {
            doCheckLine(true, spDomain);
        }
    }

    void doCheckLine(boolean isSpCheck, String spDomain) {
        String url = spDomain + "/__check";
        // 检查域名是否可用
        //      mLogQuickAdapter.addData(new CheckLog(CHECKING, getString(R.string.checking_line, spDomain), mark));
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().sslSocketFactory(SSLUtil.createSSLSocketFactory(), new SSLUtil.TrustAllManager());
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(null, ""))
                .build();
        //new call
        Call call = okHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, final IOException e) {
                // https检测不过使用http检测一次
                if (url.contains("https") &&
                        (e.getMessage().contains("javax.net.ssl.SSLHandshakeException")
                                || e.getMessage().contains("java.security.cert.CertPathValidatorException")
                                || e.getMessage().contains("Trust anchor for certification path not found"))) {
                    String url1 = url.replace("https", "http");
                    url1 = url1.replace("/__check", "");
                    doCheckLine(true, url1);
                    return;
                }


                String msg = "SP中域名（" + spDomain + "）不可用，将重新检测域名";
                LogUtils.e(msg);
                appendErrorLine(spDomain, e.getMessage(), "000");
                if (isSpCheck) {
                    fetchLines(0);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response == null) {
                    return;
                }
                handleLine(response, spDomain);
            }
        });
    }

    /**
     * 处理域名
     */
    private void handleLine(Response response, String line) {
        String code = getStatusCode(response);
        LogUtils.e("checkLine --> code = " + code);
        // 域名过期
        if (CodeEnum.DUE.getCode().equals(code)) {
            String msg = "SP中域名（" + line + "）已过期，将重新检测域名";
            LogUtils.i(msg);
            ToastUtil.showToastShort(mContext, msg);

            invalidUrl = line;
            SharePreferenceUtil.saveDomain(mContext, "");
            SharePreferenceUtil.saveIp(mContext, "");
            //SPTool.remove(mContext, Const.KEY_DOMAIN);
            appendErrorLine(line, "域名过期", code);

            fetchLines(0);
        } else if (CodeEnum.OK.getCode().equals(code)) {
            LogUtils.i("SP中域名（" + line + "）可用，正在启动应用");
            isGoon = false;
            gotoMain(line);
        } else {
            appendErrorLine(line, "域名发生未知问题,不可用", code);
            LogUtils.e("checkLine 域名发生未知问题,不可用-->  = ");
        }
    }

    /**
     * 收集错误域名的信息
     *
     * @param line
     * @param errMsg
     * @param errCode
     */
    private void appendErrorLine(String line, String errMsg, String errCode) {
        if (mDomains.length() == 0) {
            mDomains.append(line);
        } else {
            mDomains.append(";" + line);
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
    private String getStatusCode(Response response) {
        if (response.priorResponse() != null) {
            String headerStatus = response.priorResponse().header("headerStatus");
            if (CodeEnum.DUE.getCode().equals(headerStatus)) {
                return CodeEnum.DUE.getCode();
            }
        }
        return response.message();
    }

    /**
     * 进入MainActivity
     */
    private void gotoMain(String line) {
        if (!isGoon) {
            // String spLine = SharePreferenceUtil.getDomain(mContext);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mTvLoading != null) {
                        if (mTvLoading != null) {
                            mTvLoading.setText("线路获取成功，正在连接...");
                        }
                        if (mNumberBar != null) {
                            mNumberBar.setProgress(100);
                        }
                        //     mLogQuickAdapter.addData(new CheckLog(CHECK_SUCCESS, getString(R.string.checking_line_success, spLine), ""));
                        //    mTvLoading.setText(getString(R.string.checkOk));
                    }
                }
            });

            line = getSslUrl(line);
            SharePreferenceUtil.saveDomain(mContext, line);
            SharePreferenceUtil.saveIp(mContext, line);
            LogUtils.e("checkSpLine() --> SharePreferenceUtil.saveDomain= " + line);


            DataCenter.getInstance().setDomain(line);
            DataCenter.getInstance().setIp(line);
            if (getResources().getString(R.string.app_code).equals(ConstantValue.TEST_APP_CODE)) {
                DataCenter.getInstance().setDomain(handldPort(BuildConfig.HOST_URL));
                DataCenter.getInstance().setIp(handldPort(BuildConfig.HOST_URL));
            }
            jumpActivity();
        }
    }

    /**
     * 获取SSL链接
     */
    private String getSslUrl(String line) {
        if (isHttps) {
            if (line.startsWith("http:")) {
                line = line.replace("http", "https");
            } else if (!line.startsWith("https:")) {
                if (getResources().getString(R.string.app_code).equals(ConstantValue.TEST_APP_CODE)) {
                    // isDebug
                    line = String.format("http://%s", line);
                } else {
                    line = String.format("https://%s", line);
                }
            }
        } else {
            if (!line.startsWith("http")) {
                line = String.format("http://%s", line);
            }
        }
        return line;
    }

    private String handldPort(String line) {
        if (isFirstTime) {
            return line;
        }
//        if (filterFlavor()) {
//            return line;
//        }

        if (line.contains("https")) {
            line = line + HTTPS_PORT;
        } else {
            line = line + HTTP_PORT;
        }

        return line;
    }


    private void jumpActivity() {
        if (mIsJumpActivity) return;
        mIsJumpActivity = true;
        Log.e(TAG, "gotoMainActivity doman" + DataCenter.getInstance().getDomain());

        // UploadErrorLinesUtil.upload(mDomains.toString(), mErrorMessages.toString(), mCodes.toString());
        //自动登录且跳主界面 或者 跳主界面

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvLoading != null) {
                    mTvLoading.setText("线路获取成功，正在连接...");
                }
                if (mNumberBar != null) {
                    mNumberBar.setProgress(100);
                }
                if (DEVELOPE) {
                    AutoLogin.goMain(SplashActivity.this, null);
                } else {
                    AutoLogin.loginOrGoMain(SplashActivity.this);
                }
            }
        });
    }

    /**
     * 查询线路域名
     */
    private void fetchLines(int index) {
        if (index == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNumberBar.setVisibility(View.VISIBLE);
                    mBReGet.setVisibility(View.INVISIBLE);
                    mNumberBar.setProgress(0);
                    mTvLoading.setText("正在获取线路,请稍后");
                }
            });
        }
//        Map<String, String> params = new HashMap<>(2);
//        params.put("code", getResources().getString(R.string.app_code));
//        params.put("s", getResources().getString(R.string.app_sid));

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().sslSocketFactory(SSLUtil.createSSLSocketFactory(), new SSLUtil.TrustAllManager());
        RequestBody body = new FormBody.Builder()
                .add("code", getResources().getString(R.string.app_code))
                .add("s", getResources().getString(R.string.app_sid))
                .build();
        Request request = new Request.Builder().url(fecthUrl[index]).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (index == 0) {
                            mNumberBar.setProgress(progress_finish_line_1);
                        } else if (index == 1) {
                            mNumberBar.setProgress(progress_finish_line_2);
                        } else if (index == 2) {
                            mNumberBar.setProgress(progress_finish_line_3);
                        }
                        mGetLinesFaileCount++;
                        if (0 == index) {
                            fetchLines(1);
                            return;
                        } else if (1 == index) {
                            fetchLines(2);
                            return;
                        }


                        if (mLogQuickAdapter.getData().size() == 0) {
                            mLogQuickAdapter.addData(new CheckLog(GET_FAIL, getString(R.string.get_checking_line_fail), mark));
                        }
                        errorPrompt(e.getMessage());
                        Log.e(TAG, "mGetLinesFaileCount:" + mGetLinesFaileCount);
                        if (mGetLinesFaileCount % 3 == 0) {
                            mNumberBar.setVisibility(View.INVISIBLE);
                            mBReGet.setVisibility(View.VISIBLE);
                            mTvLoading.setText(getString(R.string.get_checking_line_fail));
                            //  reGetLines();
                        }
                    }
                });
                appendErrorLine(fecthUrl[index], e.getMessage(), "000");
                LogUtils.e(e.getMessage());
                Log.e(TAG, "failute:" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //拉去domain成功
                domainResponse = response;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fillterLines(domainResponse, index);
                    }
                });
            }
        });
    }

    void fillterLines(Response response, int index) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (index == 0) {
                    mNumberBar.setProgress(progress_finish_line_1);
                } else if (index == 1) {
                    mNumberBar.setProgress(progress_finish_line_2);
                } else if (index == 2) {
                    mNumberBar.setProgress(progress_finish_line_3);
                }
            }
        });

        if (getResources().getString(R.string.app_code).equals(ConstantValue.TEST_APP_CODE)) {
            DataCenter.getInstance().setDomain(BuildConfig.HOST_URL);
            DataCenter.getInstance().setIp(BuildConfig.HOST_URL);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    jumpActivity();
                }
            });
            return;
        }

        if (response == null) {
            return;
        }
        if (200 != response.code()) {
            if (0 == index) {
                fetchLines(1);
                return;
            } else if (1 == index) {
                fetchLines(2);
                return;
            }
        }

        String data = "";
        try {
            data = response.body().string();
            Log.e(TAG, "success:" + data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(data)) {
            ToastUtil.showToastShort(mContext, "获取线路为空");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvLoading.setText("获取线路为空");
                    mNumberBar.setVisibility(View.INVISIBLE);
                    mBReGet.setVisibility(View.VISIBLE);
                }
            });
            return;
        }
        Gson gson = new Gson();
        List<String> lines = gson.fromJson(data, List.class);

        if (lines == null || lines.size() == 0) {
            ToastUtil.showToastShort(mContext, "获取线路为空");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvLoading.setText("获取线路为空");
                    mNumberBar.setVisibility(View.INVISIBLE);
                    mBReGet.setVisibility(View.VISIBLE);
                }
            });
            return;
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mLogQuickAdapter.addData(new CheckLog(GET_SUCCESS, getString(R.string.get_checking_line_null), mark));
//                    mLogQuickAdapter.addData(new CheckLog(STOP_GET, getString(R.string.stop_get_check_line), mark));
//                }
//            });
        }


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNumberBar.setProgress(progress_finish_CheckDomain);
                mTvLoading.setText("正在检测线路，请稍后...");
            }
        });

        for (String line : lines) {
            if (!isGoon) break;
            if (invalidUrl != null && invalidUrl.endsWith(line)) continue;

            if (isGoon && line != null) {
                LogUtils.e("检测域名: " + line);
                String finalLine = line;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mLogQuickAdapter.addData(new CheckLog(GET_SUCCESS, getString(R.string.get_checking_line_success, finalLine)));
//                    }
//                });

                if (getResources().getString(R.string.app_code).equals(ConstantValue.TEST_APP_CODE)) {
                    line = String.format("http://%s", line);
                } else {
                    line = String.format("https://%s", line);   // 暂时这样处理，后续需要查询库表是否有启用ssl
                }
                //任务实例只能执行一次
                FetchLineTask task = new FetchLineTask();
                mFetchLineTaskList.add(task);
                line = handldPort(line);
                LogUtils.e("handldPort() --> " + line);
                task.execute(line);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.b_reGet)
    public void onViewClicked() {
        reGetLines();
    }


    @SuppressLint("StaticFieldLeak")
    private class FetchLineTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            if (isCancelled()) {
                return "执行完毕";
            }
            checkLine(params[0]);
            return "执行完毕";
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(String result) {
            //第一次进来获取不加端口的url全部结束
//            if (isCancelled()) {
            //获取不到,一旦之前获取到就会直接进入主界面，asytask消失
            if (isFirstTime && !mIsJumpActivity) {
                isFirstTime = false;
                Log.e(TAG, "http不加端口全部检测不过，开始检测加端口的域名。。。");
                LogUtils.e("http不加端口全部检测不过，开始检测加端口的域名。。。");
                fetchLines(0);
            } else {
                return;
                //            }
            }
            super.onPostExecute(result);
        }

    }

    /**
     * 检测域名是否可用
     */
    private void checkLine(final String line) {
        if (isGoon && !TextUtils.isEmpty(line)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNumberBar.setProgress(progress_finish_CheckDomain);
                    mTvLoading.setText("正在检测线路，请稍后...");
                }
            });
            doCheckLine2(line);
        } else {
            fetchLines(0);
        }
    }

    void doCheckLine2(String line) {
        String url = line + "/__check";
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().sslSocketFactory(SSLUtil.createSSLSocketFactory(), new SSLUtil.TrustAllManager());

        Request request = new Request.Builder().url(url).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // https检测不过使用http检测一次
                        if (!isFirstTime && url.contains("https") &&
                                (e.getMessage().contains("javax.net.ssl.SSLHandshakeException")
                                        || e.getMessage().contains("java.security.cert.CertPathValidatorException")
                                        || e.getMessage().contains("Trust anchor for certification path not found"))) {
                            String url1 = url.replace("https", "http");
                            url1 = url1.replace("/__check", "");
                            checkLine(url1);
                            return;
                        }


                        if (mLogQuickAdapter.getData().size() == 0) {
                            mLogQuickAdapter.addData(new CheckLog(CHECK_FAIL, "域名检测未通过", mark));
                            mNumberBar.setProgress(progress_finish_CheckDomain + (progress_finish_CheckLine - progress_finish_CheckDomain) / 2);
                        }
                        String msg = "1.域名（" + line + "）不可用";
                        LogUtils.e(msg);
                        appendErrorLine(line, e.getMessage(), "000");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response == null) {
                    return;
                }
                if (response.request().isHttps())
                    isHttps = true;
                String code = getStatusCode(response);
                if (CodeEnum.OK.getCode().equals(code)) {
                    isGoon = false;
                    for (FetchLineTask task : mFetchLineTaskList) {
                        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
                            task.cancel(true);
                        }
                    }
                    LogUtils.i("1.域名（" + line + "）可用");
                    gotoMain(line);
                } else {
                    appendErrorLine(line, "域名不可用", code);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mLogQuickAdapter.addData(new CheckLog(CHECK_SUCCESS, response.toString(), mark));
//                            }
//                        });
//                    LogUtils.i("checkLine --> response = " + response);
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAvi.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (FetchLineTask fetchLineTask : mFetchLineTaskList) {
            if (fetchLineTask != null && fetchLineTask.getStatus() == AsyncTask.Status.RUNNING) {
                fetchLineTask.cancel(true);
            }
        }
        if (isGoon) {
            UploadErrorLinesUtil.upload(mDomains.toString(), "", mErrorMessages.toString(), mCodes.toString(), mark);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAvi.hide();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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

        if (msg == null) return;
        if (msg.contains("associated")) {
            ToastUtil.showToastShort(this, getString(R.string.unNet));
        } else {
            ToastUtil.showToastShort(this, msg);
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
        DUE("604", "域名过期");

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

    class LogQuickAdapter extends BaseQuickAdapter {

        public LogQuickAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            CheckLog checkLog = (CheckLog) item;
            if (1 == checkLog.getStatus() || 3 == checkLog.getStatus()) {
                // helper.itemView.setBackgroundColor(getResources().getColor(R.color.red));
            } else {
                helper.itemView.setBackgroundColor(getResources().getColor(R.color.transparent));
                helper.itemView.setVisibility(View.GONE);
            }
            if (checkLog.getLogStr() != null && RegularTool.isHost(checkLog.getLogStr())) {
                helper.setText(R.id.log_tv, "-------------------");
            } else {
                helper.setText(R.id.log_tv, checkLog.getLogStr());
            }
            helper.setText(R.id.log_tv_code, checkLog.getCode());
        }


        /*   @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            super.onBindViewHolder((BaseViewHolder) holder,position);
        }*/
    }


    void reGetLines() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder alertDailog = new AlertDialog.Builder(this);
        alertDailog.setIcon(R.mipmap.app_icon)
                .setTitle("线路获取")
                .setMessage("线路获取失败，再次获取?")
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fetchLines(0);
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                            }
                        });
        // 显示
        alertDailog.show();
    }

    /**
     * 过滤掉不加port的站点
     *
     * @return
     */
    boolean filterFlavor() {
//        124,140,326,112,228,167,135,806,196,198,199，330
//        98ph,ix2i,whk7,SNRM,idr9,hzy3,miv5,ywo4,6rrt,urbr,n5ns,rb4b
        String f = BuildConfig.FLAVOR;
        if (f.equals("98ph") || f.equals("ix2i") || f.equals("whk7") || f.equals("SNRM")
                || f.equals("idr9") || f.equals("hzy3") || f.equals("miv5") || f.equals("ywo4")
                || f.equals("6rrt") || f.equals("urbr") || f.equals("n5ns") || f.equals("rb4b")) {
            return true;
        }
        return false;
    }

}
