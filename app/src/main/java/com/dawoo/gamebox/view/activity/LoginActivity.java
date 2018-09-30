package com.dawoo.gamebox.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.SPTool;
import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.LoginBean;
import com.dawoo.gamebox.bean.VerifyRealNameBean;
import com.dawoo.gamebox.mvp.view.ILoginView;
import com.dawoo.gamebox.util.AutoLogin;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SingleToast;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.view.HeaderView;
import com.dawoo.gamebox.view.view.InputBoxDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hwangjr.rxbus.RxBus;
import com.tencent.smtt.sdk.CookieManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.dawoo.gamebox.util.AutoLogin.CheckHasMainEnanceError;
import static com.dawoo.gamebox.util.NetUtil.getOkHttpClientBuilderForHttps;

/**
 * Created by benson on 17-12-21.
 */

public class LoginActivity extends BaseActivity implements ILoginView {
    @BindView(R.id.head_view)
    HeaderView mHeaderView;
    @BindView(R.id.etUsername)
    EditText mEtUsername;
    @BindView(R.id.etPassword)
    EditText mEtPassword;
    @BindView(R.id.iv3)
    ImageView mIv3;
    @BindView(R.id.etCaptcha)
    EditText mEtCaptcha;
    @BindView(R.id.ivCaptcha)
    ImageView mIvCaptcha;
    @BindView(R.id.tv_captcha)
    TextView mTvCaptcha;
    @BindView(R.id.rlCaptcha)
    RelativeLayout mRlCaptcha;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;
    @BindView(R.id.btnRegister)
    Button mBtnRegister;
    @BindView(R.id.forget_pwd_tv)
    TextView mForgetPwdTv;
    @BindView(R.id.remember_pwd_switch)
    SwitchCompat mSwitchCompat;
    private String mIp;
    private boolean needCaptcha;
    private int mMCode;  //登录返回状态码
    private String successUserName = "";
    private String successUserPwd = "";
    private boolean isRememberPwd = true;
    private Handler mHandler;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initViews() {
        setSwitchCompat();
    }

    @Override
    protected void initData() {
        mHandler = new Handler();
        mIp = DataCenter.getInstance().getIp();
        mHeaderView.setHeader(getString(R.string.title_name_Login_activity), true);
        // 填充输入框
        fillInput();
        getSID();
        boolean isNeedVc = (boolean) SPTool.get(this, ConstantValue.KEY_NEED_CAPTCHA, false);
        if (isNeedVc) {
            getCaptcha();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void doOnLogin() {
        if (LoginActivity.this.isFinishing()) {
            return;
        }
        if (LoginActivity.this.isDestroyed()) {
            return;
        }
        String name = mEtUsername.getText().toString().trim();
        String pwd = mEtPassword.getText().toString().trim();
        String captcha = mEtCaptcha.getText().toString().trim();
        successUserName = name;
        successUserPwd = pwd;

        if (validate(name, pwd, captcha)) {
            // mLoginPresenter.doLogin(name, pwd, new ProgressSubscriber(subcriberOnNextListner, this));
            String url = mIp + ConstantValue.LOGIN_URL;
            OkHttpClient.Builder builder = getOkHttpClientBuilderForHttps();

            RequestBody body = new FormBody.Builder()
                    .add("username", name)
                    .add("password", pwd)
                    .add("captcha", captcha).build();

            Request request = new Request.Builder().url(url)
                    .headers(Headers.of(NetUtil.setHeaders()))
                    .post(body)
                    .build();

            Call call = builder.build().newCall(request);
            try {

                call.enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //uiToast("" + e.getMessage());
                        SingleToast.showMsg("网络异常，请点击重试！");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                        LogUtils.e("login Error ==> " + e.getLocalizedMessage());

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!CheckHasMainEnanceError(response)) {
                            return;
                        }
                        final String jsonData = response.body().string();
                        mMCode = response.code();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mMCode < 200 || mMCode > 302) {
                                    LogUtils.e("login 200  302  ==> " + jsonData);
                                    LogUtils.e("login Error ==> " + response.message());
                                    SingleToast.showMsg("" + response.message());
                                    return;
                                }

                                Log.e("登录中返回报文：", jsonData);
                                handleLogin(jsonData, response);
                            }
                        });
                    }
                });
            } catch (Exception e) {
                LogUtils.e("login Error ==> " + e.getLocalizedMessage());
                //ToastUtil.showToastShort(LoginActivity.this, "" + e.getMessage());
                SingleToast.showMsg("网络异常，请点击重试！");
            }
        }

    }


    @Override
    public void verifyRealName(Object o) {


    }

    @NonNull
    private Map<String, String> setParams(String username, String password, String captcha) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("captcha", captcha);
        return params;
    }

    /**
     * 处理登录
     */
    private void handleLogin(String jsonData, Response response) {
        LoginBean loginBean = null;
        try {
            loginBean = new Gson().fromJson(jsonData, LoginBean.class);
            needCaptcha = loginBean.isIsOpenCaptcha();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            SingleToast.showMsg("网络异常，请重试！");
        }

        if (loginBean == null) {
           // SingleToast.showMsg("返回数据为空");
            SingleToast.showMsg("网络繁忙，请稍后重试");
            Log.e("login Error ==>", "登录数据为null：" + jsonData);
            return;
        }

        if (loginBean.isSuccess()) {
            if (mMCode == 302 && !TextUtils.isEmpty(loginBean.getPropMessages().getLocation())) {
                InputBoxDialog inputBoxDialog = new InputBoxDialog(this, R.style.CommonHintDialog);
                inputBoxDialog.show();
                LoginBean finalLoginBean = loginBean;
                inputBoxDialog.setOkonClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String realName = inputBoxDialog.mEtSetRealName.getText().toString().trim();
                        if (!TextUtils.isEmpty(realName)) {
//                            mLoginPresenter.verifyRealName(loginBean.getPropMessages().getGbToken(), realName, successUserName, successUserName, successUserPwd, successUserPwd);
                            inputBoxDialog.dismiss();

                            String url = mIp + ConstantValue.REAL_NAME_URL;
                            OkHttpClient.Builder builder = getOkHttpClientBuilderForHttps();

                            RequestBody body = new FormBody.Builder()
                                    .add("gb.token", finalLoginBean.getPropMessages().getGbToken())
                                    .add("result.realName", realName)
                                    .add("needRealName", "yes")
                                    .add("result.playerAccount", successUserName)
                                    .add("search.playerAccount", successUserName)
                                    .add("tempPass", successUserPwd)
                                    .add("newPassword", successUserPwd)
                                    .add("passLevel", "20").build();

                            Request request = new Request.Builder().url(url)
                                    .headers(Headers.of(NetUtil.setHeaders()))
                                    .post(body)
                                    .build();

                            Call call = builder.build().newCall(request);
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    //uiToast("" + e.getMessage());
                                    SingleToast.showMsg("网络异常，请重试！");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                        }
                                    });

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    mMCode = response.code();
                                    final String jsonData = response.body().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (mMCode != 200) {
                                                SingleToast.showMsg(getString(R.string.verify_name_error));
                                                return;
                                            }
                                            Log.e("验证中返回报文：", jsonData);
                                            if (!TextUtils.isEmpty(jsonData)) {
                                                VerifyRealNameBean verifyRealNameBean = new Gson().fromJson(jsonData, VerifyRealNameBean.class);
                                                if (verifyRealNameBean != null) {
                                                    if (!verifyRealNameBean.getData().isNameSame()) {
                                                        SingleToast.showMsg(getString(R.string.real_name_error));
                                                    } else if (verifyRealNameBean.getData().isConflict())
                                                        SingleToast.showMsg(getString(R.string.account_existence));
                                                    else {
                                                        SingleToast.showMsg(getString(R.string.verify_name_ok));
                                                        needCaptcha = false;
                                                        doOnLogin();
                                                    }

                                                }
                                            } else
                                                SingleToast.showMsg(getString(R.string.verify_name_error));
                                        }
                                    });
                                }
                            });
                        }
                    }
                });

            } else
                loginSuccess(response);
        } else {
            String message = loginBean.getMessage() + "";
            if (loginBean.getPropMessages().getCaptcha() != null) {
                message = loginBean.getPropMessages().getCaptcha();
            }
            SingleToast.showMsg("用户名或者密码错误");
        }
        if (loginBean.isIsOpenCaptcha()) {
            SPTool.put(this, ConstantValue.KEY_NEED_CAPTCHA, true);
            SPTool.put(this, ConstantValue.KEY_CAPTCHA_TIME, new Date().getTime());
            mRlCaptcha.setVisibility(View.VISIBLE);
            mTvCaptcha.setVisibility(View.VISIBLE);
            getCaptcha();
        }
    }
//
//    private void uiToast(String sring) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                ToastUtil.showToastLong(LoginActivity.this, sring);
//            }
//        });
//    }

    /**
     * 登录成功
     */
    private void loginSuccess(Response response) {

        SPTool.put(this, ConstantValue.KEY_NEED_CAPTCHA, false);
        SPTool.remove(this, ConstantValue.KEY_CAPTCHA_TIME);

        NetUtil.setCookie(response);

        DataCenter.getInstance().setLogin(true);
        DataCenter.getInstance().setUserName(successUserName);
        DataCenter.getInstance().setPassword(successUserPwd);

        // 用来自动登录
        SPTool.put(this, ConstantValue.KEY_USERNAME_AUTO_LOGIN, successUserName);
        SPTool.put(this, ConstantValue.KEY_PASSWORD_AUTO_LOGIN, successUserPwd);
        SPTool.put(this, ConstantValue.KEY_TIME_AUTO_LOGIN, System.currentTimeMillis());

        // 用来记住密码
        if (isRememberPwd) {
            SPTool.put(this, ConstantValue.KEY_USERNAME, successUserName);
            SPTool.put(this, ConstantValue.KEY_PASSWORD, successUserPwd);
        }
        AutoLogin.setJpushAlias(successUserName);

        notifyLogined();
        ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
    }

    void notifyLogined() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RxBus.get().post(ConstantValue.EVENT_TYPE_LOGINED, "login");
            }
        }, 500);
    }


    /**
     * 表单验证
     */
    public boolean validate(String username, String password, String captcha) {
        if (TextUtils.isEmpty(username)) {
            getFocusable(mEtUsername);
            SingleToast.showMsg(getResources().getString(R.string.username_hint));
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            getFocusable(mEtPassword);
            SingleToast.showMsg(getResources().getString(R.string.password_hint));
            return false;
        }

        if (needCaptcha && captcha.length() != 4) {
            getFocusable(mEtCaptcha);
            SingleToast.showMsg(getResources().getString(R.string.enter_captcha));
            if (mRlCaptcha.getVisibility() == View.GONE) {
                mRlCaptcha.setVisibility(View.VISIBLE);
                mTvCaptcha.setVisibility(View.VISIBLE);
            }
            getCaptcha();
            return false;
        }

        return true;
    }

    private void getFocusable(EditText view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    @OnClick({R.id.ivCaptcha, R.id.btnLogin, R.id.btnRegister, R.id.forget_pwd_tv})
    public void onViewClicked(View view) {
        SoundUtil.getInstance().playVoiceOnclick();
        switch (view.getId()) {
            case R.id.ivCaptcha:
                getCaptcha();
                break;
            case R.id.btnLogin:
                doOnLogin();
                break;
            case R.id.btnRegister:
                // 注册
                gotoRegister();
                break;
            case R.id.forget_pwd_tv:
                // 忘记密码
                //  gotoCustomerPage();
                startActivityForResult(new Intent(this, ForgetPasswordSelecteWayActivity.class), ConstantValue.KEY_FIND_PASSWORD);
                break;
        }
    }

    private void gotoRegister() {
        startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), ConstantValue.KEY_REGIST_BACK_LOGIN);

    }

    public void gotoCustomerPage() {
        LogUtils.d("gotoCustomerPage");
        ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
        RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_SERVICE, "gotoService");
    }

    private void fillInput() {
        isRememberPwd = (Boolean) SPTool.get(this, ConstantValue.KEY_REMEMBER_PWD, true);
        mSwitchCompat.setChecked(isRememberPwd);
        if (isRememberPwd) {
            String spUsername = (String) SPTool.get(this, ConstantValue.KEY_USERNAME, "");
            String spPassword = (String) SPTool.get(this, ConstantValue.KEY_PASSWORD, "");
            if (!TextUtils.isEmpty(spPassword)) {
                mEtUsername.setText(spUsername);
                mEtPassword.setText(spPassword);
            } else {
                if (!TextUtils.isEmpty(spUsername)) {
                    mEtUsername.setText(spUsername);
                    mEtPassword.requestFocus();
                }
            }
        }


        needCaptcha = (boolean) SPTool.get(this, ConstantValue.KEY_NEED_CAPTCHA, false);
        if (needCaptcha) {
            long now = System.currentTimeMillis();
            long date = (long) SPTool.get(this, ConstantValue.KEY_CAPTCHA_TIME, now);
            if (now - date < 30 * 60 * 1000) {
                mRlCaptcha.setVisibility(View.VISIBLE);
                mTvCaptcha.setVisibility(View.VISIBLE);
            }
        }
    }

    //获取验证码
    private void getCaptcha() {
        OkHttpUtils.get().url(mIp + ConstantValue.CAPTCHA_URL).addParams("_t", String.valueOf(System.currentTimeMillis()))
                .headers(NetUtil.setHeaders()).build().execute(new BitmapCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e("captcha error ==> " + e.getMessage());
                SingleToast.showMsg(getResources().getString(R.string.getCaptchaFail));
            }

            @Override
            public void onResponse(Bitmap response, int id) {
                if (mIvCaptcha != null && response != null) {
                    mIvCaptcha.setImageBitmap(response);
                }

            }

            @Override
            public boolean validateReponse(Response response, int id) {
                return super.validateReponse(response, id);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantValue.KEY_REGIST_BACK_LOGIN) {
            if (resultCode == ConstantValue.KEY_REGIST_BACK_LOGIN) {
                fillInput();
                doOnLogin();
            }
        }

        if (requestCode == ConstantValue.KEY_FIND_PASSWORD) {
            fillInput();
        }
    }


    private void getSID() {
        CookieManager.getInstance().setCookie(mIp, "");
        String url = mIp + ConstantValue.GET_SID;
        OkHttpClient.Builder builder = getOkHttpClientBuilderForHttps();

        Request request = new Request.Builder().url(url)
                .headers(Headers.of(NetUtil.setHeaders()))
                .get()
                .build();
        Call call = builder.build().newCall(request);
        try {

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    NetUtil.setCookie(response);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSwitchCompat() {
        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRememberPwd = isChecked;
                SPTool.put(LoginActivity.this, ConstantValue.KEY_REMEMBER_PWD, isRememberPwd);
            }
        });
    }

}
