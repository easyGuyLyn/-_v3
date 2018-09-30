package com.dawoo.gamebox.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.RDSMSBean;
import com.dawoo.gamebox.bean.RegisterBean;
import com.dawoo.gamebox.bean.RegisterInfoBean;
import com.dawoo.gamebox.mvp.service.IUserService;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.IRegisterView;
import com.dawoo.gamebox.net.RetrofitHelper;
import com.dawoo.gamebox.util.EasyCountDownTimer;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SingleToast;
import com.tencent.smtt.sdk.CookieManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Callback;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.dawoo.gamebox.util.AutoLogin.CheckHasMainEnanceError;

/**
 * Created by alex on 18-3-22.
 *
 * @author alex
 */

public class RegisterPresenter<T extends IRegisterView> extends BasePresenter {
    private IRegisterView iRegisterView;
    private Context context;
    private EasyCountDownTimer easyCountDownTimer;
    private String mTrue = "true";
    private String mIp;

    public RegisterPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        iRegisterView = (IRegisterView) view;
        this.context = mContext;
        mIp = DataCenter.getInstance().getIp();
    }

    public void getRegisterInfo() {

        CookieManager.getInstance().removeAllCookie();
        String url = mIp + ConstantValue.GET_SID;
        OkHttpClient.Builder builder = NetUtil.getOkHttpClientBuilderForHttps();
        Request request = NetUtil.getOkhttpRequest(url);
        Call call = builder.build().newCall(request);
        try {

            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtils.e("register getSidResponse:" + e.getMessage());
                    if (context == null) return;
                    if (((Activity) context).isFinishing()) return;
                    if (((Activity) context).isDestroyed()) return;
                    iRegisterView.registerError(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!CheckHasMainEnanceError(response)) {
                        return;
                    }
                    NetUtil.setCookie(response);
                    IUserService service = RetrofitHelper.getService(IUserService.class);
                    service.getRegisterInfo().enqueue(new Callback<RegisterInfoBean>() {
                        @Override
                        public void onResponse(retrofit2.Call<RegisterInfoBean> call, retrofit2.Response<RegisterInfoBean> response) {
                            if (context == null) return;
                            if (((Activity) context).isFinishing()) return;
                            if (((Activity) context).isDestroyed()) return;
                            Activity activity = (Activity) context;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    iRegisterView.getRegisterInfoSuccess(response.body());
                                }
                            });
                        }

                        @Override
                        public void onFailure(retrofit2.Call<RegisterInfoBean> call, Throwable t) {
                            if (context == null) return;
                            if (((Activity) context).isFinishing()) return;
                            if (((Activity) context).isDestroyed()) return;
                            LogUtils.e("register getRegisterInfoResponse:" + t.getMessage());
                            iRegisterView.registerError(t.getMessage());

                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void register(Map<String, String> params) {
        RetrofitHelper.getService(IUserService.class)
                .saveRegister(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (context == null) return;
                        if (((Activity) context).isFinishing()) return;
                        if (((Activity) context).isDestroyed()) return;
                        iRegisterView.registerError(e.getMessage());


                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        if (context == null) return;
                        if (((Activity) context).isFinishing()) return;
                        if (((Activity) context).isDestroyed()) return;
                        if (registerBean.isSuccess()) {
                            iRegisterView.registerSuccess(registerBean);
                        } else {
                            iRegisterView.registerError(registerBean.getMessage());
                        }

                    }
                });


    }


    public void getCaptcha(ImageView imageView) {
        OkHttpUtils.get().url(mIp + ConstantValue.REGISTER_CAPTURE_CODE).addParams("_t", String.valueOf(System.currentTimeMillis()))
                .headers(NetUtil.setHeaders()).build().execute(new BitmapCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e("captcha error ==> " + e.getMessage());
                if (context == null) return;
                if (((Activity) context).isFinishing()) return;
                if (((Activity) context).isDestroyed()) return;
                iRegisterView.registerError(e.getMessage());
            }

            @Override
            public void onResponse(Bitmap response, int id) {
                if (context == null) return;
                if (((Activity) context).isFinishing()) return;
                if (((Activity) context).isDestroyed()) return;
                if (imageView != null && response != null) {
                    imageView.setImageBitmap(response);
                }
            }

            @Override
            public boolean validateReponse(Response response, int id) {
                return super.validateReponse(response, id);
            }
        });
//        CookieManager.getInstance().setCookie(mIp, "");
//        String url = mIp + ConstantValue.LOGIN_URL;
//        OkHttpClient.Builder builder = NetUtil.getOkHttpClientBuilderForHttps();
//
//        Request request = NetUtil.getOkhttpRequest(url);
//        Call call = builder.build().newCall(request);
//        try {
//            call.enqueue(new okhttp3.Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (!CheckHasMainEnanceError(response)) {
//                        return;
//                    }
//                    NetUtil.setCookie(response);
//                    OkHttpUtils.get().url(mIp + ConstantValue.REGISTER_CAPTURE_CODE).addParams("_t", String.valueOf(System.currentTimeMillis()))
//                            .headers(NetUtil.setHeaders()).build().execute(new BitmapCallback() {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            LogUtils.e("captcha error ==> " + e.getMessage());
//                            iRegisterView.registerError(e.getMessage());
//                        }
//
//                        @Override
//                        public void onResponse(Bitmap response, int id) {
//                            if (imageView != null && response != null) {
//                                imageView.setImageBitmap(response);
//                            }
//                        }
//
//                        @Override
//                        public boolean validateReponse(Response response, int id) {
//                            return super.validateReponse(response, id);
//                        }
//                    });
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public void getSms(TextView textView, String phone) {
        RetrofitHelper.getService(IUserService.class)
                .postRegisterSmsCode(phone)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RDSMSBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (context == null) return;
                        if (((Activity) context).isFinishing()) return;
                        if (((Activity) context).isDestroyed()) return;
                        iRegisterView.registerError(e.getMessage());
                    }

                    @Override
                    public void onNext(RDSMSBean smsBean) {
                        if (context == null) return;
                        if (((Activity) context).isFinishing()) return;
                        if (((Activity) context).isDestroyed()) return;

                        if (smsBean.isSuccess()) {
                            SingleToast.showMsg("短信发送成功");
                            if (easyCountDownTimer == null) {
                                easyCountDownTimer = new EasyCountDownTimer(textView, 90000, 1000);
                            }
                            easyCountDownTimer.start();
                        } else {
                            iRegisterView.registerError(smsBean.getMessage());
                        }


                    }
                });


    }


    /**
     * 动态配置参数
     */
    public HashMap<String, String> getSimpleFiled(String birthday, String sex, String paymentPassword, String defaultTimezone,
                                                  String defaultLocale, String phoneNumber, String realName, String mainCurrency,
                                                  String securityIssues, String email, String qq, String wechat, String username,
                                                  String password, String commitPassword, String verificationCode, String phoneCode,
                                                  String checkPhone, String recommendUserInputCode, String termsOfService, String registCode, String requiredJson) {
        HashMap<String, String> simpleFiledMap = new HashMap<>();
        simpleFiledMap.put("sysUser.birthday", birthday);
        simpleFiledMap.put("sysUser.sex", sex);
        simpleFiledMap.put("sysUser.permissionPwd", paymentPassword);
        simpleFiledMap.put("sysUser.defaultTimezone", defaultTimezone);
        simpleFiledMap.put("sysUser.defaultLocale", defaultLocale);
        simpleFiledMap.put("phone.contactValue", phoneNumber);
        simpleFiledMap.put("sysUser.realName", realName);
        simpleFiledMap.put("sysUser.defaultCurrency", mainCurrency);
        simpleFiledMap.put("sysUserProtection.question1", securityIssues);
        simpleFiledMap.put("email.contactValue", email);
        simpleFiledMap.put("qq.contactValue", qq);
        simpleFiledMap.put("weixin.contactValue", wechat);
        simpleFiledMap.put("sysUser.username", username);
        simpleFiledMap.put("sysUser.password", password);
        simpleFiledMap.put("confirmPassword", commitPassword);
        simpleFiledMap.put("captchaCode", verificationCode);
        simpleFiledMap.put("recommendUserInputCode", recommendUserInputCode);
        simpleFiledMap.put("phoneCode", phoneCode);
        simpleFiledMap.put(checkPhone, checkPhone);
        simpleFiledMap.put("termsOfService", termsOfService);
        simpleFiledMap.put("registCode", registCode);
        simpleFiledMap.put("requiredJson", requiredJson);

        return simpleFiledMap;
    }

    public String gerRequireJson(List<String> dates) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < dates.size(); i++) {
            stringBuilder.append(dates.get(i)).append(",");
        }
        String result = new String(stringBuilder);
        LogUtils.e(result);
        return result;
    }


}
