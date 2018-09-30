package com.dawoo.gamebox.view.activity.webview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dawoo.coretool.util.CleanLeakUtils;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.SPTool;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.dawoo.coretool.util.activity.DensityUtil;
import com.dawoo.coretool.util.date.DateTool;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.CapitalRecord;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.SchemeEnum;
import com.dawoo.gamebox.mvp.view.IWebView;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.FileTool;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.view.activity.BaseActivity;
import com.dawoo.gamebox.view.activity.LoginActivity;
import com.dawoo.gamebox.view.activity.MainActivity;
import com.dawoo.gamebox.view.activity.PromoRecordActivity;
import com.dawoo.gamebox.view.activity.message.MessageCenterActivity;
import com.dawoo.gamebox.view.view.CustomProgressDialog;
import com.dawoo.gamebox.view.view.DragViewLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

import static com.dawoo.gamebox.ConstantValue.WEBVIEW_TYPE_GAME;
import static com.dawoo.gamebox.ConstantValue.WEBVIEW_TYPE_GAME_FULLSCREEN_ALWAYS;
import static com.dawoo.gamebox.ConstantValue.WEBVIEW_TYPE_ORDINARY;
import static com.dawoo.gamebox.ConstantValue.WEBVIEW_TYPE_THIRD_ORDINARY;


public class WebViewActivity extends BaseActivity implements IWebView, View.OnClickListener {
    public static final String SCREEN_ORITATION = "ScreenOrientation";
    public static final String IS_IP_CONNECT = "is_ip_connect";
    public static final String GAME_ID = "GAME_ID";

    private ImmersionBar mImmersionBar;
    private String apiId;

    @BindView(R.id.webview_fl)
    DragViewLayout mWebviewFL;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.videoContainer)
    FrameLayout mVideoContainer;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * Android 5.0以下版本的文件选择回调
     */
    protected ValueCallback<Uri> mFileUploadCallbackFirst;
    /**
     * Android 5.0及以上版本的文件选择回调
     */
    protected ValueCallback<Uri[]> mFileUploadCallbackSecond;
    protected static final int REQUEST_CODE_FILE_PICKER = 51426;
    protected String mUploadableFileTypes = "image/*";
    private WebView mWebview;
    private Handler mHandler = new Handler();
    private WebViewActivity instance;
    private ImageView mHomeIv;
    private ImageView mBackIv;
    private String mWebViewType;
    private LinearLayout mLl;
    private String mImgUrl;
    private String mUrl;
    // private CustomProgressDialog mProgressDialog;
    private boolean misRefreshPage;
    private int mScreenOrientatioType = 3;// 1 必须竖屏  2 必须横屏  3 动态切换
    private FrameLayout.LayoutParams progresslayoutParams;
    private LinearLayout progressll;
    private ProgressBar progressBar;
    private TextView progressText;


    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_web_view);
        initScreenOrientation();
        if (getIntent().getStringExtra(ConstantValue.WEBVIEW_TYPE) != null) {
            mWebViewType = getIntent().getStringExtra(ConstantValue.WEBVIEW_TYPE);
            //除了电子之外的游戏和彩票                   第三方网页
            if (WEBVIEW_TYPE_GAME.equals(mWebViewType) || WEBVIEW_TYPE_THIRD_ORDINARY.equals(mWebViewType)) {
                mImmersionBar = ImmersionBar.with(this).statusBarColor(R.color.black);
                mImmersionBar.init();
            } else if (WEBVIEW_TYPE_GAME_FULLSCREEN_ALWAYS.equals(mWebViewType)) {//电子有些游戏一开始不设置全屏，他会认为你永远不全屏
                setFullScreen(true);
            }
        }
    }

    //屏幕方向
    private void initScreenOrientation() {
        mScreenOrientatioType = getIntent().getIntExtra(SCREEN_ORITATION, 3);
        if (mScreenOrientatioType == 1) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (mScreenOrientatioType == 2) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setFullScreen(true);
        }
    }

    @Override
    protected void initViews() {
        createWebView();
        initWebSetting();
    }

    @Override
    protected void initData() {
        mHandler = new Handler();
        instance = this;
        RxBus.get().register(this);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(ConstantValue.WEBVIEW_URL);
        apiId = intent.getStringExtra(GAME_ID);
        mUrl = NetUtil.handleUrl(mUrl);

        Log.e("Webview ", mUrl);

        // NetUtil.syncCookie(mUrl,DataCenter.getInstance().getCookie());
        mWebview.getSettings().setUserAgentString(mWebview.getSettings()
                .getUserAgentString().replace("app_android", "Android") + "; is_native=true");

        request(mUrl);
    }

    /**
     * 请求网页
     *
     * @param url
     */
    private void request(String url) {
        mWebview.loadUrl(url);
    }

    private void createWebView() {
        mWebview = new WebView(BoxApplication.getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mWebview.setLayoutParams(layoutParams);
        mWebviewFL.addView(mWebview);
        createDragViewButton();
    }

    private void createDragViewButton() {
        //加载框　点击出现黑屏现象　以布局形式展现
        progresslayoutParams = new FrameLayout.LayoutParams(
                DensityUtil.dp2px(this, FrameLayout.LayoutParams.WRAP_CONTENT),
                DensityUtil.dp2px(this, FrameLayout.LayoutParams.WRAP_CONTENT),
                Gravity.CENTER);
        progresslayoutParams.setMargins(0, 0, 0, DensityUtil.dp2px(this, FrameLayout.LayoutParams.WRAP_CONTENT));
        progressll = new LinearLayout(this);
        progressll.setBackgroundResource(R.drawable.bg_loading);
        progressll.setOrientation(LinearLayout.HORIZONTAL);
        progressll.setGravity(Gravity.CENTER);
        progressll.setPadding(20, 20, 20, 20);

        progressBar = new ProgressBar(this);
        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_medium));

        progressText = new TextView(this);
        progressText.setTextColor(getResources().getColor(R.color.white));
        progressText.setText(R.string.loading_tip);

        progressll.addView(progressBar);
        progressll.addView(progressText);
        mWebviewFL.addView(progressll);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                DensityUtil.dp2px(this, 28),
                DensityUtil.dp2px(this, 56),
                Gravity.BOTTOM | Gravity.RIGHT);
        params.setMargins(0, 0, 0, DensityUtil.dp2px(this, 56));
        mLl = new LinearLayout(this);
        mLl.setGravity(Gravity.CENTER);
        mLl.setLayoutParams(params);
        mLl.setBackgroundResource(R.mipmap.drag_view_bg);
        mLl.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(DensityUtil.dp2px(this, 28), DensityUtil.dp2px(this, 28));
        mHomeIv = new ImageView(this);
        mHomeIv.setLayoutParams(childParams);
        mHomeIv.setImageResource(R.mipmap.drag_view_home);
        mHomeIv.setId(R.id.webview_iv_home);
        mHomeIv.setOnClickListener(this);

        mBackIv = new ImageView(this);
        mBackIv.setLayoutParams(childParams);
        mBackIv.setImageResource(R.mipmap.drag_view_back);
        mBackIv.setId(R.id.webview_iv_back);
        mBackIv.setOnClickListener(this);
        mLl.addView(mHomeIv);
        mLl.addView(mBackIv);
        mWebviewFL.addView(mLl);
    }

    void setDragViewLandScapePos() {
        if (mLl == null) {
            return;
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                DensityUtil.dp2px(this, 28),
                DensityUtil.dp2px(this, 56),
                Gravity.END);
        mLl.setLayoutParams(params);
    }

    void setDragViewPortrait() {
        if (mLl == null) {
            return;
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                DensityUtil.dp2px(this, 28),
                DensityUtil.dp2px(this, 56),
                Gravity.BOTTOM | Gravity.RIGHT);
        params.setMargins(0, 0, 0, DensityUtil.dp2px(this, 56));
        mLl.setLayoutParams(params);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("web", "横屏");
            setFullScreen(true);
            // 横屏
            setDragViewLandScapePos();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("web", "竖屏");
            if (!mWebViewType.equals(WEBVIEW_TYPE_GAME_FULLSCREEN_ALWAYS)) {
                setFullScreen(false);
            }
            // 竖屏
            setDragViewPortrait();
        }
    }

    //动态设置全屏与非全屏   只有横屏有全屏
    private void setFullScreen(boolean issFullScreen) {
        if (issFullScreen) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    @Override
    public void initWebSetting() {
        WebSettings webSettings = mWebview.getSettings();

        //支持缩放，默认为true。
        webSettings.setSupportZoom(true);
        //调整图片至适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //设置默认编码
        webSettings.setDefaultTextEncodingName("utf-8");
        //支持插件
        webSettings.setPluginsEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        //多窗口
        webSettings.supportMultipleWindows();
        //获取触摸焦点
        mWebview.requestFocusFromTouch();
        //允许访问文件
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowUniversalAccessFromFileURLs(false);
        webSettings.setAllowContentAccess(true);
        //开启javascript
        webSettings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        webSettings.setDomStorageEnabled(true);        //设置支持DomStorage
        //   String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        //   settings.setAppCachePath(appCacheDir);
//        settings.setAllowContentAccess(true);
//        settings.setAllowFileAccess(true);
//        settings.setAllowFileAccessFromFileURLs(true);
//        settings.setAllowUniversalAccessFromFileURLs(false);
//        settings.setBlockNetworkImage(false);
//        settings.setBlockNetworkLoads(false);
//        settings.setDatabaseEnabled(true);          // 设置支持本地存储
//        settings.setDomStorageEnabled(true);        //设置支持DomStorage
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setJavaScriptEnabled(true);        // 支持JS
//        settings.setLightTouchEnabled(false);
        //图片先不加载最后再加载
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }
        webSettings.setMediaPlaybackRequiresUserGesture(true);
        webSettings.setAppCacheEnabled(true);          // 启用缓存
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //修改硬件加速导致页面渲染闪烁问题
        // mWebview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebview.requestFocus();
        mWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        /**
         * MIXED_CONTENT_ALWAYS_ALLOW：允许从任何来源加载内容，即使起源是不安全的；
         * MIXED_CONTENT_NEVER_ALLOW：不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源；
         * MIXED_CONTENT_COMPATIBILITY_MODE：当涉及到混合式内容时，WebView 会尝试去兼容最新Web浏览器的风格。
         **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebview.getSettings().setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebview.addJavascriptInterface(new InJavaScriptCommon(), "gamebox");

        CookieManager.getInstance().setAcceptCookie(true);


        mWebview.setDownloadListener(new FileDownLoadListener());
        mWebview.setOnTouchListener(new MyWebviewOnTouchListener());
        mWebview.setWebViewClient(new CommonWebViewClient());
        mWebview.setWebChromeClient(new CommonWebChromeClient());
        mWebview.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        mWebview.setBackgroundResource(R.color.black);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == mHomeIv.getId()) {
            ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
            RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_HOME, "gotoHome");
        } else if (v.getId() == mBackIv.getId()) {
            ActivityStackManager.getInstance().finishActivity(this);
        }
    }

    private class CommonWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // setProgressBar(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            //  setWebViewTitleName(title);
        }

        //  Android 2.2 (API level 8)到Android 2.3 (API level 10)版本选择文件时会触发该隐藏方法
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, null);
        }

        // Android 3.0 (API level 11)到 Android 4.0 (API level 15))版本选择文件时会触发，该方法为隐藏方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openFileChooser(uploadMsg, acceptType, null);
        }

        // Android 4.1 (API level 16) -- Android 4.3 (API level 18)版本选择文件时会触发，该方法为隐藏方法
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileInput(uploadMsg, null, false);
        }

        // Android 5.0 (API level 21)以上版本会触发该方法，该方法为公开方法
        @SuppressWarnings("all")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (Build.VERSION.SDK_INT >= 21) {
                final boolean allowMultiple = fileChooserParams.getMode() == FileChooserParams.MODE_OPEN_MULTIPLE;//是否支持多选
                openFileInput(null, filePathCallback, allowMultiple);
                return true;
            } else {
                return false;
            }
        }


        @SuppressLint("NewApi")
        protected void openFileInput(final ValueCallback<Uri> fileUploadCallbackFirst, final ValueCallback<Uri[]> fileUploadCallbackSecond, final boolean allowMultiple) {
            //Android 5.0以下版本
            if (mFileUploadCallbackFirst != null) {
                mFileUploadCallbackFirst.onReceiveValue(null);
            }
            mFileUploadCallbackFirst = fileUploadCallbackFirst;

            //Android 5.0及以上版本
            if (mFileUploadCallbackSecond != null) {
                mFileUploadCallbackSecond.onReceiveValue(null);
            }
            mFileUploadCallbackSecond = fileUploadCallbackSecond;

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);

            if (allowMultiple) {
                if (Build.VERSION.SDK_INT >= 18) {
                    i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
            }

            i.setType(mUploadableFileTypes);

            startActivityForResult(Intent.createChooser(i, "选择文件"), REQUEST_CODE_FILE_PICKER);
        }

        IX5WebChromeClient.CustomViewCallback mCallBack;

        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            fullScreen();
            mWebview.setVisibility(View.GONE);
            mVideoContainer.setVisibility(View.VISIBLE);
            mVideoContainer.addView(view);
            mCallBack = customViewCallback;
            super.onShowCustomView(view, customViewCallback);
        }

        @Override
        public void onShowCustomView(View view, int i, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            fullScreen();
            mWebview.setVisibility(View.GONE);
            mVideoContainer.setVisibility(View.VISIBLE);
            mVideoContainer.addView(view);
            mCallBack = customViewCallback;
            super.onShowCustomView(view, i, customViewCallback);
        }

        @Override
        public void onHideCustomView() {
            fullScreen();
            if (mCallBack != null) {
                mCallBack.onCustomViewHidden();
            }
            mWebview.setVisibility(View.VISIBLE);
            mVideoContainer.removeAllViews();
            mVideoContainer.setVisibility(View.GONE);
            super.onHideCustomView();
        }

//        @Override
//        public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
//            return super.onJsAlert(webView, s, s1, jsResult);
//        }
//
//        @Override
//        public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
//            return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
//        }
//
//        @Override
//        public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
//            return super.onJsConfirm(webView, s, s1, jsResult);
//        }
    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    private class CommonWebViewClient extends WebViewClient {

        @Override
        public void onLoadResource(WebView webView, String s) {
            super.onLoadResource(webView, s);
            Log.e("onPageLoadResource", s);
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //showProgress();
            progressll.setVisibility(View.VISIBLE);
            Log.e("onPageStarted", url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e("onPageFinished", url);
            // dismissProgress();
            progressll.setVisibility(View.GONE);
            setRefreshPageClearHistory();
        }

        @Override
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
        }


        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("onPageShoudOver", url);
            if (url == null) return false;
            //showProgress();
            progressll.setVisibility(View.VISIBLE);
            if (url.contains("login/commonLogin.html")) {
            //    ActivityUtil.gotoLogin();
                return false;
            } else if (url.contains("/lottery/mainIndex.html")) {
                //没办法　都不知道跳转逻辑　只有这样判断了
                return shouldfileterUrl(url);
            } else if (url.contains("/mainIndex.html")) {
                ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
//                RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_HOME, "gotoHome");
            }

            return shouldfileterUrl(url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            switch (errorCode) {
                case ERROR_CONNECT:
                    mWebview.loadUrl("file:///android_asset/html/unNet.html");
                    break;
            }
        }


        /**
         * 拦截WebView网络请求（Android API < 21）
         * 只能拦截网络请求的URL，请求方法、请求内容等无法拦截
         */
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
        }

        /**
         * 拦截WebView网络请求（Android API >= 21）
         * 通过解析WebResourceRequest对象获取网络请求相关信息
         */
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        //    Log.e("WebResourceRequest  ", "Cookie: " + CookieManager.getInstance().getCookie(DataCenter.getInstance().getDomain()) + " \n Method: " + request.getMethod() + "  \n Headers: " + request.getRequestHeaders().toString() + "\n");
            return super.shouldInterceptRequest(view, request);
        }
    }

    private void setRefreshPageClearHistory() {
        if (misRefreshPage) {
            misRefreshPage = false;
            mWebview.clearHistory();
        }
    }


    private class MyWebviewOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mWebview.requestFocus();
            return false;
        }
    }


    private class FileDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            startBrowsers(url);
        }
    }

    private class OnLeftBackClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (mWebview.canGoBack()) {
                mWebview.goBack();
            } else {
                finish();
            }
        }
    }


    /**
     * 返回上一个页面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
            // 返回键退回
            mWebview.goBack();
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /**
     * 调用浏览
     *
     * @param url
     */
    private void startBrowsers(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    /**
     * 设置进度条
     *
     * @param progress
     */
    private void setProgressBar(int progress) {
        if (progress == 100) {
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {
            if (View.INVISIBLE == mProgressBar.getVisibility()) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            mProgressBar.setProgress(progress);
        }
    }


    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (requestCode == REQUEST_CODE_FILE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    //Android 5.0以下版本
                    if (mFileUploadCallbackFirst != null) {
                        mFileUploadCallbackFirst.onReceiveValue(intent.getData());
                        mFileUploadCallbackFirst = null;
                    } else if (mFileUploadCallbackSecond != null) {//Android 5.0及以上版本
                        Uri[] dataUris = null;

                        try {
                            if (intent.getDataString() != null) {
                                dataUris = new Uri[]{Uri.parse(intent.getDataString())};
                            } else {
                                if (Build.VERSION.SDK_INT >= 16) {
                                    if (intent.getClipData() != null) {
                                        final int numSelectedFiles = intent.getClipData().getItemCount();

                                        dataUris = new Uri[numSelectedFiles];

                                        for (int i = 0; i < numSelectedFiles; i++) {
                                            dataUris[i] = intent.getClipData().getItemAt(i).getUri();
                                        }
                                    }
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        mFileUploadCallbackSecond.onReceiveValue(dataUris);
                        mFileUploadCallbackSecond = null;
                    }
                }
            } else {
                //这里mFileUploadCallbackFirst跟mFileUploadCallbackSecond在不同系统版本下分别持有了
                //WebView对象，在用户取消文件选择器的情况下，需给onReceiveValue传null返回值
                //否则WebView在未收到返回值的情况下，无法进行任何操作，文件选择器会失效
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst.onReceiveValue(null);
                    mFileUploadCallbackFirst = null;
                } else if (mFileUploadCallbackSecond != null) {
                    mFileUploadCallbackSecond.onReceiveValue(null);
                    mFileUploadCallbackSecond = null;
                }
            }
        }
    }

    private final class InJavaScriptCommon {
        //*******************************
        //v2兼容
        @JavascriptInterface
        public void gotoFragment(final String targets) {
            LogUtils.e("gotoFragment：" + targets);
            if ("4".equals(targets)) {
                gotoMinePage();
            } else if ("0".equals(targets)) {
                gotoHomePage();
            }
        }

        @JavascriptInterface
        public void gotoBet(final String url) {
            LogUtils.e("gotoBet：" + url);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (TextUtils.isEmpty(url)) {
                        ToastUtil.showResShort(instance, R.string.url_is_null);
                    } else {
                        mWebview.loadUrl(url);
                    }
                }
            });
        }

        @JavascriptInterface
        public void gotoActivity(final String url) {
            LogUtils.d("gotoActivity：" + url);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (TextUtils.isEmpty(url)) {
                        ToastUtil.showResShort(instance, R.string.url_is_null);
                    } else {
                        mWebview.loadUrl(NetUtil.handleUrl(url));
                    }
                }
            });
        }

        // 退出登录
        @JavascriptInterface
        public void logout() {
            LogUtils.d("logout");
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    ActivityUtil.logout();
                }
            });
        }

        @JavascriptInterface
        public void toast(String msg) {
            LogUtils.d("toast");
            ToastUtil.showToastShort(instance, msg);
        }

        // 获取登录状态
        @JavascriptInterface
        public void getLoginState(String state) {
            LogUtils.d("getLoginState");
            if (!state.equals("undefined")) {
                //  登录
            }
        }

        @JavascriptInterface
        public void log(final String url) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (TextUtils.isEmpty(url)) {
                        ToastUtil.showResShort(instance, R.string.url_is_null);
                    } else {
                        mWebview.loadUrl(url);
                    }
                }
            });
        }
//*********************************************************************


        // 回到主页
        @JavascriptInterface
        public void gotoHome(final String url) {
            LogUtils.d("gotoHome" + url);
            mHandler.post(() -> {
                Intent intent = new Intent(instance, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }


        // 注册后登录
        @JavascriptInterface
        public void gotoLogin(final String _href) {
            LogUtils.d("gotoLogin" + _href);
            mHandler.post(() -> {
                try {
                    if (TextUtils.isEmpty(_href)) {
                        ToastUtil.showToastShort(instance, getString(R.string.url_is_null));
                    } else {
                        String[] params = _href.split(",");
                        DataCenter.getInstance().getUser().username = params[0];
                        DataCenter.getInstance().getUser().password = params[1];

                        // 用来自动登录
                        SPTool.put(WebViewActivity.this, ConstantValue.KEY_USERNAME_AUTO_LOGIN, params[0]);
                        SPTool.put(WebViewActivity.this, ConstantValue.KEY_PASSWORD_AUTO_LOGIN, params[1]);
                        SPTool.put(WebViewActivity.this, ConstantValue.KEY_TIME_AUTO_LOGIN, System.currentTimeMillis());
                        // 用来记住密码
                        SPTool.put(WebViewActivity.this, ConstantValue.KEY_USERNAME, params[0]);
                        SPTool.put(WebViewActivity.this, ConstantValue.KEY_PASSWORD, params[1]);
                        setResult(ConstantValue.KEY_REGIST_BACK_LOGIN);
                        WebViewActivity.this.finish();
                    }
                    // 如果登录操作是因为点击其他url触发，则登陆后跳转到该url

                } catch (Exception e) {
                    ToastUtil.showToastShort(instance, getString(R.string.net_busy_error));
                }
            });
        }

        // 注册后登录
        @JavascriptInterface
        public void gotoLoginNew(String name, String pwd) {
            mHandler.post(() -> {
                try {
                    DataCenter.getInstance().getUser().setUsername(name);
                    DataCenter.getInstance().getUser().setPassword(pwd);

                    SPTool.put(WebViewActivity.this, ConstantValue.KEY_USERNAME, DataCenter.getInstance().getUser().username);
                    SPTool.put(WebViewActivity.this, ConstantValue.KEY_PASSWORD, DataCenter.getInstance().getUser().password);
                    setResult(ConstantValue.KEY_REGIST_BACK_LOGIN);
                    WebViewActivity.this.finish();

                } catch (Exception e) {
                    // 如果登录操作是因为点击其他url触发，则登陆后跳转到该url
                    ToastUtil.showToastShort(instance, getString(R.string.net_busy_error));
                }
            });
        }


        @JavascriptInterface
        public void goLogin() {
            LogUtils.d("gotoLogin");
            mHandler.post(() -> {
                DataCenter.getInstance().setLogin(false);
                Intent intent = new Intent(instance, LoginActivity.class);
                //  intent.putExtra(Constants.RESULT_FLAG, resultFlag);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });

        }


        // 保存图片（二维码）
        @JavascriptInterface
        public void saveImage(final String url) {
            mHandler.post(() -> {

                if (TextUtils.isEmpty(url)) {
                    ToastUtil.showToastShort(WebViewActivity.this, getString(R.string.imageUrlNil));
                    return;
                }
                try {
                    String domain = DataCenter.getInstance().getDomain();
                    if (url.contains("http")) {
                        mImgUrl = url;
                    } else {
                        mImgUrl = url.contains(domain) ? url : domain + "/" + url;
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // 检查该权限是否已经获取
                        int hasAuth = ContextCompat.checkSelfPermission(WebViewActivity.this, permissions[0]);
                        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                        if (hasAuth != PackageManager.PERMISSION_GRANTED) {
                            // 提交请求权限
                            ActivityCompat.requestPermissions(WebViewActivity.this, permissions, 321);
                        } else {
                            handleImage();
                        }
                    } else {
                        handleImage();
                    }
                } catch (Exception e) {
                    ToastUtil.showToastShort(WebViewActivity.this, getString(R.string.saveImgFailed));
                }
            });
        }


        /**
         * 通知账户已经变动
         */
        @JavascriptInterface
        public void notifyAccountChange() {
            RxBus.get().post(ConstantValue.EVENT_TYPE_ACCOUNT, "updateAccount");
        }

        /**
         * 刷新当前页面
         */
        @JavascriptInterface
        public void refreshPage() {
            LogUtils.d("refreshPage");
            if (mWebview != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshCurrentPage("s");
                    }
                });

            }
        }


        /**
         * 如果有上一级页面，就返回上一级
         * 如果没有上一级页面，就不返回，而是提示用户
         */
        @JavascriptInterface
        public void goBackPage() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mWebview == null) {
                        return;
                    }
                    if (mWebview.canGoBack()) {
                        mWebview.goBack();
                    } else {
                        WebViewActivity.this.finish();
                        //  ToastUtil.showResShort(instance, R.string.is_end_page);
                    }
                }
            });
        }

        /**
         * 跳入存款页面
         */
        @JavascriptInterface
        public void gotoDepositPage() {
            LogUtils.d("gotoDepositPage");
            if (mWebview == null) {
                return;
            }
            ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
            RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_DEPOSIT, "gotodeposit");
        }

        /**
         * 跳到资金记录页面
         */
        @JavascriptInterface
        public void gotoCapitalRecordPage() {
            LogUtils.d("gotoCapitalRecordPage");
            startActivity(new Intent(instance, CapitalRecord.class));
        }

        /**
         * 跳到优惠记录页面
         */
        @JavascriptInterface
        public void gotoPromoRecordPage() {
            LogUtils.d("gotoPromoRecordPage");
            startActivity(new Intent(instance, PromoRecordActivity.class));
        }

        /**
         * 跳到优惠申请页面
         */
        @JavascriptInterface
        public void gotoApplyPromoPage() {
            LogUtils.d("gotoApplyPromoPage");
            Intent intent1 = new Intent(instance, MessageCenterActivity.class);
            intent1.putExtra(MessageCenterActivity.WHERE_FROM, MessageCenterActivity.TAB_APPLICATION_PREFERENTAIL);
            startActivity(intent1);
        }

        /**
         * 去登录
         */
        @JavascriptInterface
        public void gotoLoginPage() {
            LogUtils.d("gotoLoginPage");
            startActivity(new Intent(instance, LoginActivity.class));
        }

        /**
         * 去首页
         */
        @JavascriptInterface
        public void gotoHomePage() {
            LogUtils.d("gotoHomePage");
            ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
            RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_HOME, "gotoHome");
        }

        /**
         * 去客服
         */
        @JavascriptInterface
        public void gotoCustomerPage() {
            LogUtils.d("gotoCustomerPage");
            ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
            RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_SERVICE, "gotoService");
        }

        /**
         * 去我的
         */
        @JavascriptInterface
        public void gotoMinePage() {
            LogUtils.d("gotoCustomerPage");
            ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
            RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_MINE, "gotoMine");
        }

        /**
         * 打开新的webview（打开一个新的弹窗，并且根据传入的url进行加载）
         *
         * @param url
         */
        @JavascriptInterface
        public void startNewWebView(String url, String type) {
            LogUtils.d("startNewWebView");
            ActivityUtil.startWebView(url, "", WEBVIEW_TYPE_ORDINARY, 1, apiId);
        }

        /**
         * 注册后登录
         *
         * @param name
         * @param pwd
         */
        @JavascriptInterface
        public void nativeAutoLogin(String name, String pwd) {
            mHandler.post(() -> {
                try {
                    DataCenter.getInstance().getUser().setUsername(name);
                    DataCenter.getInstance().getUser().setPassword(pwd);

                    SPTool.put(WebViewActivity.this, ConstantValue.KEY_USERNAME, DataCenter.getInstance().getUser().username);
                    SPTool.put(WebViewActivity.this, ConstantValue.KEY_PASSWORD, DataCenter.getInstance().getUser().password);
                    setResult(ConstantValue.KEY_REGIST_BACK_LOGIN);
                    WebViewActivity.this.finish();

                } catch (Exception e) {
                    // 如果登录操作是因为点击其他url触发，则登陆后跳转到该url
                    ToastUtil.showToastShort(instance, getString(R.string.net_busy_error));
                }
            });
        }

    }

    private void handleImage() {
        final String[] files = setImage();
        OkHttpUtils.get().url(mImgUrl).build().execute(new FileCallBack(files[0], files[1]) {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e("saveImage error ==> " + e.getMessage());
                ToastUtil.showToastShort(WebViewActivity.this, getString(R.string.saveImgFailed));
            }

            @Override
            public void onResponse(File response, int id) {
                ToastUtil.showToastShort(WebViewActivity.this, getString(R.string.saveImgSuccess));
                fileScan(files[2]);
            }
        });
    }

    private String[] setImage() {
        String[] files = new String[3];

        String saveDir = FileTool.getSDCardDir() + "/Pictures";
        if (!FileTool.dirIsExists(saveDir)) {
            FileTool.makeDir(saveDir, false);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("pay_code_").append(DateTool.getCurrentDate(DateTool.FMT_DATE_TIME_3)).append(".png");
        files[0] = saveDir;
        files[1] = sb.toString();
        files[2] = saveDir + "/" + sb.toString();

        return files;
    }

    // 保存后扫描文件
    private void fileScan(String filePath) {
        Uri data = Uri.parse("file://" + filePath);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handleImage();
            }
        }
    }


    boolean shouldfileterUrl(String url) {
        if (url == null) return false;
        try {
            if (url.startsWith(SchemeEnum.INTENT.getCode()) || url.startsWith(SchemeEnum.QQ.getCode())
                    || url.startsWith(SchemeEnum.ALIPAY.getCode()) || url.startsWith(SchemeEnum.WECHAT.getCode())) {


                if (url.startsWith(SchemeEnum.ALIPAY.getCode()) && !checkAliPayInstalled(this)) {
//                    mWebview.loadUrl("http://ds.alipay.com/?from=mobileweb");
//                    return false;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://ds.alipay.com/?from=mobileweb"));
                    startActivity(intent);
                } else {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setComponent(null);
                    intent.setSelector(null);
                    List<ResolveInfo> resolves = getPackageManager().queryIntentActivities(intent, 0);
                    if (resolves.size() > 0) {
                        startActivityIfNeeded(intent, -1);
                    }
                }

            } else if (url.startsWith("https://payh5.bbnpay.com/") || isZXPay(url) || isGaotongPay(url) || isAimiSenPay(url)) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } else {
                mWebview.loadUrl(url);
            }
        } catch (Exception e) {
            LogUtils.e("跳转支付页面异常 ==> " + e.getMessage());
        }

        return true;
    }

    public boolean checkAliPayInstalled(Context context) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }


    boolean isZXPay(String url) {
        // String code = getString(R.string.app_code);
        if (url.startsWith("https://zhongxin.junka.com/")) {
            return true;
        }
        return false;
    }

    boolean isGaotongPay(String url) {
        //String code = getString(R.string.app_code);
        if (url.startsWith("http://wgtj.gaotongpay.com/")) {
            return true;
        }
        return false;
    }

    boolean isAimiSenPay(String url) {
        //String code = getString(R.string.app_code);
        if (url.startsWith("https://www.joinpay.com")) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        progressll.setVisibility(View.GONE);
        Log.e("apiId", apiId + "///////api");
        if (!TextUtils.isEmpty(apiId)) {
            RxBus.get().post(ConstantValue.EVENT_BACK_MONEY, apiId);
        }

        CleanLeakUtils.fixInputMethodManagerLeak(this);
        if (mWebview != null) {
            RxBus.get().unregister(this);
            //  mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();
            //  mWebview.clearCache(true);
            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，
        super.onDestroy();
    }

    /**
     * 登录成功后，回调加载账户
     */
    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_LOGINED)})
    public void refreshCurrentPage(String s) {
        // 刷新页面
        if (mWebview != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    misRefreshPage = true;
                    String url = mWebview.getUrl();
                    request(url);
                }
            });

        }
    }

    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_LOGOUT)})
    public void logout(String s) {
        LogUtils.d(s);
        //dismissProgress();
        progressll.setVisibility(View.GONE);
        finish();
    }


}
