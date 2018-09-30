package com.dawoo.gamebox.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.SPTool;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.system.SystemUtil;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.ServiceBean;
import com.dawoo.gamebox.mvp.presenter.UserPresenter;
import com.dawoo.gamebox.mvp.view.IServiceFragmentView;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SingleToast;
import com.dawoo.gamebox.view.activity.MainActivity;
import com.dawoo.gamebox.view.view.CustomProgressDialog;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

public class ServiceFragment extends BaseFragment implements IServiceFragmentView {
    //    @BindView(R.id.head_view)
//    WebHeaderView mHeadView;
//    @BindView(R.id.progressBar)
//    ProgressBar mProgressBar;
//    @BindView(R.id.swipe_refresh_layout)
//    SwipeRefreshLayout mRefreshLayout;
//    @BindView(R.id.webview_fl)
//    RelativeLayout mWebviewFL;
    @BindView(R.id.id_webview)
    WebView mWebview;
    Unbinder unbinder;
    @BindView(R.id.left_btn)
    FrameLayout mLeftBtn;
    private CustomProgressDialog mProgressDialog;
    private boolean isInitData = false;
    private Handler mHandler;
    private String imgUrl;
    private boolean mIsIsInlay;
    private boolean mIsUserBrower;
    private String mUrl;
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
    protected static final int REQUEST_CODE_FROM_HOME = 5100;
    protected static final int REQUEST_CODE_FROM_DEPOSIT = 5101;
    protected static final int REQUEST_CODE_FROM_PROMO = 5102;
    protected static final int REQUEST_CODE_FROM_MINE = 5104;
    protected String mUploadableFileTypes = "image/*";
    private UserPresenter mPresenter;

    public ServiceFragment() {
    }

    public static ServiceFragment newInstance() {
        ServiceFragment fragment = new ServiceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        View v = inflater.inflate(R.layout.fragment_service, container, false);
        unbinder = ButterKnife.bind(this, v);
        initViews();
        initData();
        return v;
    }

    @Override
    public void onDestroyView() {
        RxBus.get().unregister(this);
        mPresenter.onDestory();
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected void loadData() {
        mPresenter.getCustomerService();
    }

    public void initViews() {
        createWebView();
        createProgressDialog(mContext);
//        mRefreshLayout.setEnabled(false);
//        mRefreshLayout.setOnRefreshListener(this);
//        //改变加载显示的颜色
//        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorAccent));
//        mHeadView.setLeftBackListener(new OnLeftBackClickListener());
        webSetting(mWebview);
        mWebview.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        mWebview.setBackgroundResource(R.color.fragment_bg);
    }

    @Override
    public void onCustomerService(Object o) {
        if (o != null && o instanceof ServiceBean) {
            ServiceBean serviceBean = (ServiceBean) o;
            if (mWebview != null) {
                String url = serviceBean.getCustomerUrl();
                mUrl = url;
                Log.e("ServiceFragment", "getService ==> " + url);

                // 如果第三方客服不支持或者显示问题跳转浏览器
                if (intercepter()) {
                    serviceBean.setIsInlay(false);
                }
                mIsIsInlay = serviceBean.isIsInlay();
                SPTool.remove(mContext, ConstantValue.KEY_CUSTOMER_SERVICE);
                SPTool.put(mContext, ConstantValue.KEY_CUSTOMER_SERVICE, url);
                if (serviceBean.isIsInlay()) {
                    mWebview.loadUrl(url);
                } else {
                    gotoBrower("onCustomerService");
                }
            }
        } else {
            String url = (String) SPTool.get(mContext, ConstantValue.KEY_CUSTOMER_SERVICE, "");
            if (!TextUtils.isEmpty(url)) {
                mWebview.loadUrl(url);
            }
        }
    }

    private boolean intercepter() {
        // 270站点
//        if (!TextUtils.isEmpty(mUrl) && mUrl.contains("chatserver.comm100.com")) {
//            mCoverView = new View(mContext);
//            mCoverView.setVisibility(View.GONE);
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(DensityUtil.dp2px(mContext, 80), DensityUtil.dp2px(mContext, 30), Gravity.END);
//            setlavorColor();
//            mCoverView.setLayoutParams(params);
//            ((FrameLayout)mViwe).addView(mCoverView);
//        }

        // oppo
        if ("A37fw".equals(SystemUtil.getDeviceModel()) || "R9".equals(SystemUtil.getDeviceModel())) {
            return true;
        }
        return false;
    }

//    private void setlavorColor() {
//        String flavor = BuildConfig.FLAVOR;
//        if ("app_57h0".equals(flavor)) { // 270
//            mCoverView.setBackgroundColor(Color.parseColor("#16940d"));
//        } else if ("app_nu9r".equals(flavor)) { // 119
//            mCoverView.setBackgroundColor(Color.parseColor("#339ed8"));
//        }
//    }


    /**
     * 回调去客服
     */
    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_GOTOTAB_SERVICE)})
    public void gotoBrower(String s) {
        if (!TextUtils.isEmpty(mUrl)) {
            // 调用浏览器
            if (!mIsIsInlay) {
                mIsUserBrower = true;
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(mUrl);
                intent.setData(content_url);
                startActivity(intent);
            }
        }
    }

    @OnClick(R.id.left_btn)
    public void onViewClicked() {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            SingleToast.showMsg(getString(R.string.is_end_page));
        }
    }


    private class OnLeftBackClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (mWebview.canGoBack()) {
                mWebview.goBack();
            } else {
                ToastUtil.showResShort(mContext, R.string.is_end_page);
            }
        }
    }


    private void initData() {
        RxBus.get().register(this);
        mHandler = new Handler();
        mPresenter = new UserPresenter(mContext, this);
    }

    private void createWebView() {
//        mWebview = new WebView(mContext);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        mWebviewFL.addView(mWebview, layoutParams);
    }

    /*  *//**
     * 登录成功后，回调加载账户
     *//*
    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_LOGINED)})
    public void loginedReload(String s) {
        if (isInitData) {
            loadData();
        }
    }*/

    /**
     * 设置WebView
     */
    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    protected void webSetting(final WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setDomStorageEnabled(true);        //设置支持DomStorage
        // webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
//        webSettings.setSupportMultipleWindows(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        CookieManager.getInstance().setAcceptCookie(true);

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());

    }
//
//    @Override
//    public void onRefresh() {
//        if (mWebview != null) {
//            mWebview.reload();
//        }
//    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgress();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // webView.goBack() 重设标题
            setTitle(String.valueOf(view.getTitle()));
            dismissProgress();
            // 加载完数据设置为不刷新状态，将下拉进度收起来
//            if (mRefreshLayout.isRefreshing()) {
//                mRefreshLayout.setRefreshing(false);
//            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.i("thirdPay url ==> " + url);
            if (url == null) return false;

            view.loadUrl(url);
            return true;
        }


        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
        }


        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
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
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }

        @Override
        public void onProgressChanged(WebView view, int progress) {
            setProgressBar(progress);
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

//        @Override
//        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
//            WebView newWebView = new WebView(view.getContext());
//            newWebView.setWebViewClient(new WebViewClient() {
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(browserIntent);
//                    return true;
//                }
//            });
//            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
//            transport.setWebView(newWebView);
//            resultMsg.sendToTarget();

//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(view.getUrl()));
//            startActivity(browserIntent);
//            return true;
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
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
        super.onActivityResult(requestCode, resultCode, intent);
    }


    //***************************************


    @Override
    public void onResume() {
        super.onResume();
        if (!mIsIsInlay && mIsUserBrower) {
            mIsUserBrower = false;
            switchBackIItem();
        }
    }

    void switchBackIItem() {
        if (MainActivity.TAB_INDEX_BEFORE == MainActivity.TAB_INDEX_HOME) {
            ((MainActivity) mContext).switchTab(MainActivity.TAB_INDEX_HOME);
        } else if (MainActivity.TAB_INDEX_BEFORE == MainActivity.TAB_INDEX_DEPOSIT) {
            ((MainActivity) mContext).switchTab(MainActivity.TAB_INDEX_DEPOSIT);
        } else if (MainActivity.TAB_INDEX_BEFORE == MainActivity.TAB_INDEX_PROMO) {
            ((MainActivity) mContext).switchTab(MainActivity.TAB_INDEX_PROMO);
        } else if (MainActivity.TAB_INDEX_BEFORE == MainActivity.TAB_INDEX_MINE) {
            ((MainActivity) mContext).switchTab(MainActivity.TAB_INDEX_MINE);
        }
    }


    /**
     * 设置标题
     *
     * @param title
     */
    private void setTitle(String title) {
        if (title != null) {
//            mHeadView.setHeader(title, true, false);
        }
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
//        if (progress == 100) {
//            mProgressBar.setVisibility(View.INVISIBLE);
//        } else {
//            if (View.INVISIBLE == mProgressBar.getVisibility()) {
//                mProgressBar.setVisibility(View.VISIBLE);
//            }
//            mProgressBar.setProgress(progress);
//        }
    }


    @Override
    public void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
        mContext = null;
        if (mWebview != null) {
            //       mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();
            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }


    private void createProgressDialog(Context context) {
        mProgressDialog = new CustomProgressDialog(context, getResources().getString(R.string.loading_tip));
        mProgressDialog.setCancelable(true);
    }

    protected void showProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }


    protected void dismissProgress() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    /**
     * 获取客服地址
     */
    private void getService() {
        OkHttpUtils.get().url(DataCenter.getInstance().getIp() + ConstantValue.SERVICE_URL)
                .headers(NetUtil.setHeaders()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("ServiceFragment", "getService error ==> " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("ServiceFragment", "getService ==> " + response);
                SPTool.remove(mContext, ConstantValue.KEY_CUSTOMER_SERVICE);
                SPTool.put(mContext, ConstantValue.KEY_CUSTOMER_SERVICE, response);
//                if (mHeadView != null) {
//                    mWebview.loadUrl(response);
//                }
                isInitData = true;
            }
        });
    }


}