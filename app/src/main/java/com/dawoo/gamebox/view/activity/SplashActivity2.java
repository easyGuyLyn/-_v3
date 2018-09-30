package com.dawoo.gamebox.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.coretool.util.RegularTool;
import com.dawoo.coretool.util.packageref.PackageInfoUtil;
import com.dawoo.gamebox.BuildConfig;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.line.CheckLog;
import com.dawoo.gamebox.bean.line.CommonIp;
import com.dawoo.gamebox.bean.line.LineErrorDialogBean;
import com.dawoo.gamebox.net.GlideApp;
import com.dawoo.gamebox.util.AutoLogin;
import com.dawoo.gamebox.util.SpecialSiteEnum;
import com.dawoo.gamebox.util.cache.CacheUtils;
import com.dawoo.gamebox.util.line.LineHelperUtil;
import com.dawoo.gamebox.util.line.LineProgressString;
import com.dawoo.gamebox.view.view.CustomDialog;
import com.dawoo.gamebox.view.view.numberprogressbar.LineTaskProgressListener;
import com.dawoo.gamebox.view.view.numberprogressbar.NumberProgressBar;
import com.dawoo.pushsdk.util.GsonUtil;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.dawoo.gamebox.util.line.LineHelperUtil.progress_finish_CheckDomain;
import static com.dawoo.gamebox.util.line.LineHelperUtil.progress_finish_CheckLine;
import static com.dawoo.gamebox.util.line.LineHelperUtil.progress_finish_GetBaseLine;
import static com.dawoo.gamebox.util.line.LineHelperUtil.progress_start_CheckDomain;


/**
 * 过渡页
 * 检查线路
 * Created by benson on 18-04-08.
 */

public class SplashActivity2 extends BaseActivity {
    public static final String TAG = "SplashActivity";
    private AVLoadingIndicatorView mAvi;
    private TextView mTvLoading;
    private ImageView mIvLogo;
    private TextView mTvCopyright;
    private RecyclerView mLogRecyclerView;
    private NumberProgressBar mNumberProgressBar;
    private Button mReGet;
    private Button mTv_error_detail;
    private SplashActivity2 mContext;
    private LogQuickAdapter mLogQuickAdapter;
    private LineHelperUtil mHelperUtil;
    private CustomDialog mCustomDialog;
    private LineErrorDialogBean mLineErrorDialogBean;
    private String mOutIp;
    private RelativeLayout mbgGuide;
    private LinearLayout mConnectLin;
    private RelativeLayout mProgress;
    private TextView mTvTimer;

    private Handler mHandler = new Handler();
    private Timer mTimer;
    private TimerTask mTimerTask;
    private int mLeftTime;
    private Bitmap mBitmapAd;//广告图


    @Override
    protected void createLayoutView() {
        if (!BuildConfig.IPConnection) {
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        } else {
            setContentView(R.layout.acitivity_splash2);
        }
        mAvi = findViewById(R.id.avi);
        mTvLoading = findViewById(R.id.tvLoading);
        mIvLogo = findViewById(R.id.ivLogo);
        mTvCopyright = findViewById(R.id.tvCopyright);
        mLogRecyclerView = findViewById(R.id.log_recyclerview);
        mNumberProgressBar = findViewById(R.id.numberBar);
        mReGet = findViewById(R.id.b_reGet);
        mTv_error_detail = findViewById(R.id.tv_error_detail);
        mbgGuide = findViewById(R.id.bg_guide);
        mConnectLin = findViewById(R.id.ll_1);
        mProgress = findViewById(R.id.ll_progress);
        mTvTimer = findViewById(R.id.tv_timer);
        hiddenImg(false);
        mTvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimerTask();
                Intent intent = new Intent(SplashActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initViews() {
        if (!BuildConfig.IPConnection) {
            return;
        }
        RxBus.get().register(this);
        mContext = this;
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 设置applogo
        String codeName = mContext.getResources().getString(R.string.app_code);
        int code = SpecialSiteEnum.getCodeByCodeName(codeName);
        if (code == 0) {
            GlideApp.with(this).load(R.mipmap.app_logo).into(mIvLogo);
        }

        //  设置Copyright
        mTvCopyright.setText(String.format("Copyright © %s Reserved. v%s", getResources().getString(R.string.app_name), PackageInfoUtil.getVersionName(this)));

        mLogQuickAdapter = new LogQuickAdapter(R.layout.log_check_splash_acitivity_list_item_view);
        mLogRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true));
        mLogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLogRecyclerView.setAdapter(mLogQuickAdapter);
        mCustomDialog = new CustomDialog(mContext, R.style.customIosDialog, R.layout.dialog_ios_line_error);
        mCustomDialog.setCanceledOnTouchOutside(false);
        mCustomDialog.setCancelable(false);
    }

    @Override
    protected void initData() {
        if (!BuildConfig.IPConnection) {
            return;
        }

//                if (true) {
//            DataCenter.getInstance().setDomain("test01.ccenter.test.so");
//            DataCenter.getInstance().setIp("http://192.168.0.92");
//            AutoLogin.loginOrGoMain(this);
//            mNumberProgressBar.setProgress(100);
//            return;
//        }
//
//        if (true) {
//            DataCenter.getInstance().setDomain("test71.hongtubet.com");
//            DataCenter.getInstance().setIp("https://47.90.51.75:8989");
//            AutoLogin.loginOrGoMain(this);
//            mNumberProgressBar.setProgress(100);
//            return;
//        }

        mHelperUtil = new LineHelperUtil();
        mHelperUtil.checkSp();
        setProgressUI();
        mReGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelperUtil.checkSp();
            }
        });
        mTv_error_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLineErrorDialogBean == null) return;
                oPenErrorDialog(mLineErrorDialogBean);
                getLocalIp();
            }
        });
    }


    private void setProgressUI() {
        mHelperUtil.setLineTaskProgressListener(new LineTaskProgressListener() {
            @Override
            public void onGetLineSuccess(String domain, String ip) {

            }

            @Override
            public void onProgressBarChange(int current, int max) {
                if (!isDestroyed() && mNumberProgressBar != null && mTvLoading != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mReGet.setVisibility(View.GONE);
                            mTv_error_detail.setVisibility(View.GONE);
                            mNumberProgressBar.setProgress(current);
                            if (current < progress_finish_GetBaseLine) {
                                mTvLoading.setText(LineProgressString.LINE_PROGRESS_GETTING_BASE_LINE);
                            } else if (current < progress_start_CheckDomain && current >= progress_finish_GetBaseLine) {
                                mTvLoading.setText(LineProgressString.LINE_PROGRESS_GETTING_LINE);
                            } else if (current == progress_start_CheckDomain) {
                                mTvLoading.setText(LineProgressString.LINE_PROGRESS_SETTING_PORT);
                            } else if (current >= progress_finish_CheckDomain && current < progress_finish_CheckLine) {
                                mTvLoading.setText(LineProgressString.LINE_PROGRESS_CHECKING_LINE);
                            } else if (current == progress_finish_CheckLine) {
                                mTvLoading.setText(LineProgressString.LINE_PROGRESS_CONNECTING);
                            } else if (current == 100) {
                                mTvLoading.setText(LineProgressString.LINE_PROGRESS_CONNECTED);
                            }
                        }
                    });
                }
            }

            @Override
            public void onErrorSimpleReason(String result) {
                if (!isDestroyed() && mTvLoading != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvLoading.setText(result);
                            mReGet.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onErrorComplexReason(LineErrorDialogBean lineErrorDialogBean) {
                if (!isDestroyed() && mTv_error_detail != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLineErrorDialogBean = lineErrorDialogBean;
                            mTv_error_detail.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onSpalshGetLineSuccess(String domain, String ip, LineTaskProgressListener lineTaskProgressListener) {
                Log.e("LineHelperUtil ", "跳到 SplashActivity2 " + domain + " " + ip);
                if (!isDestroyed()) {
                    AutoLogin.loginOrGoMain(SplashActivity2.this, lineTaskProgressListener);
                }
            }


        });
    }

    /**
     * 错误信息dialog
     */
    public void oPenErrorDialog(LineErrorDialogBean lineErrorDialogBean) {
        showErrorDialog();
        initDialog(lineErrorDialogBean);
    }

    private void initDialog(LineErrorDialogBean lineErrorDialogBean) {
        TextView tv_title = mCustomDialog.findViewById(R.id.tv_title);
        TextView tv_msg = mCustomDialog.findViewById(R.id.tv_msg);
        TextView tvOk = mCustomDialog.findViewById(R.id.ok);

        tv_title.setText("线路检测失败");

        String ip = "获取公网IP失败...";
        if (!TextUtils.isEmpty(mOutIp)) {
            ip = mOutIp;
        }
        String tip = "出现未知错误，请联系在线客服并提供以下信息"
                + "\n当前ip: " + ip
                + "\n版本号: Android v" + PackageInfoUtil.getVersionName(mContext) + "  " + PackageInfoUtil.getVersionCode(mContext);

        if (lineErrorDialogBean.getCode().equals(LineProgressString.CODE_001)) {
            tip = "网络连接失败，请确认您的网络连接正常后再次尝试！";
        } else if (lineErrorDialogBean.getCode().equals(LineProgressString.CODE_002)) {
            tip = "线路获取失败，请确认您的网络连接正常后再次尝试！";
        } else if (lineErrorDialogBean.getCode().equals(LineProgressString.CODE_003)) {
            tip = "出现未知错误，请联系在线客服并提供以下信息！"
                    + "\n当前ip: " + ip
                    + "\n版本号: Android v" + PackageInfoUtil.getVersionName(mContext) + "  " + PackageInfoUtil.getVersionCode(mContext);
        }

        tv_msg.setText(tip);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dimissErrorDialog();
            }
        });
    }


    public void showErrorDialog() {
        if (mCustomDialog != null) {
            mCustomDialog.show();
        }
    }

    public void dimissErrorDialog() {
        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
        }
    }

    /**
     * 获取公网ip
     */
    private void getLocalIp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Request request = new Request.Builder().url(ConstantValue.IP_LOCAL_URL).get().build();
        builder.build().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "getLocalIp onError  " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String json = response.body().string();
                    if (TextUtils.isEmpty(json)) return;
                    Log.e(TAG, "getLocalIp response  " + json);
                    try {
                        CommonIp commonIp = GsonUtil.GsonToBean(json, CommonIp.class);
                        Log.e("getLocalIp onError", mOutIp + commonIp);
                        if (commonIp != null) {
                            mOutIp = commonIp.getIp();
                            if (!isDestroyed()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mLineErrorDialogBean != null) {
                                            if (mCustomDialog.findViewById(R.id.tv_msg) != null) {
                                                initDialog(mLineErrorDialogBean);
//                                                ((TextView) mCustomDialog.findViewById(R.id.tv_msg)).setText("错误码: " + mLineErrorDialogBean.getCode()
//                                                        + "\n当前ip: " + mOutIp
//                                                        + "\n版本号: Android v" + PackageInfoUtil.getVersionName(mContext) + "  " + PackageInfoUtil.getVersionCode(mContext)
//                                                        + "\n"
//                                                        + "\n" + "很抱歉，请联系客服并提供以上信息");
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        if(!isDestroyed())
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mLineErrorDialogBean != null) {
                                    if (mCustomDialog.findViewById(R.id.tv_msg) != null) {
                                        initDialog(mLineErrorDialogBean);
//                                        ((TextView) mCustomDialog.findViewById(R.id.tv_msg)).setText("错误码: " + mLineErrorDialogBean.getCode()
//                                                + "\n当前ip: " + "未知"
//                                                + "\n版本号: Android v" + PackageInfoUtil.getVersionName(mContext) + "  " + PackageInfoUtil.getVersionCode(mContext)
//                                                + "\n"
//                                                + "\n" + "很抱歉，请联系客服并提供以上信息");
                                    }
                                }
                            }
                        });

                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAvi == null) return;
        mAvi.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAvi == null) return;
        mAvi.hide();
    }


    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
        cancelTimerTask();
        dimissErrorDialog();
        if (mBitmapAd != null && !mBitmapAd.isRecycled()) {
            mBitmapAd.recycle();
        }
        super.onDestroy();
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
    }


    @Subscribe(tags = {@Tag(ConstantValue.IS_GUIDE_IMG)})
    public void isShowGuide(String picUrl) {
        Log.e("ATGA", "收到的通知" + picUrl);
        mIvLogo.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(picUrl)) {
            hiddenImg(true);
            mBitmapAd = CacheUtils.getInstance().getBitmap(picUrl);
            mbgGuide.setBackground(new BitmapDrawable(mBitmapAd));
            statTimer();
        } else {
            startActivity(new Intent(SplashActivity2.this, MainActivity.class));
            finish();
        }

    }

    /**
     * 开始倒计时
     */
    private void statTimer() {
        cancelTimerTask();
        mLeftTime = 4;
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!isDestroyed() && mTvTimer != null) {
                            mTvTimer.setVisibility(View.VISIBLE);
                            mTvTimer.setText((mLeftTime - 1) + "秒后跳过");
                            mLeftTime--;
                            if (mLeftTime == 1) {
                                cancelTimerTask();
                                Intent intent = new Intent(SplashActivity2.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
            }
        };

        mTimer = new Timer();
        mTimer.schedule(mTimerTask, 1000, 1000);
    }

    public void cancelTimerTask() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    //引导图隐藏 true 正在引导页　falise 线路检测
    public void hiddenImg(boolean isGuide) {
        if (isGuide) {
            mConnectLin.setVisibility(View.GONE);
            mProgress.setVisibility(View.GONE);
            mTvCopyright.setVisibility(View.GONE);
            mLogRecyclerView.setVisibility(View.GONE);
            mNumberProgressBar.setVisibility(View.GONE);
        } else {
            mConnectLin.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.VISIBLE);
            mTvCopyright.setVisibility(View.VISIBLE);
            mLogRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}