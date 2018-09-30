package com.dawoo.gamebox.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.SPTool;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.bean.AdResponse;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.LoginBean;
import com.dawoo.gamebox.update.AppUpdate;
import com.dawoo.gamebox.util.cache.CacheUtils;
import com.dawoo.gamebox.view.activity.MainActivity;
import com.dawoo.gamebox.view.view.numberprogressbar.LineTaskProgressListener;
import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.tencent.smtt.sdk.CookieManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.dawoo.gamebox.BoxApplication.initOkHttpUtils;
import static com.dawoo.gamebox.util.TagAliasOperatorHelper.ACTION_DELETE;
import static com.dawoo.gamebox.util.TagAliasOperatorHelper.ACTION_SET;
import static com.dawoo.gamebox.util.TagAliasOperatorHelper.sequence;

/**
 * Created by archar on 18-2-1.
 * <p>
 * 自动登录
 */

public class AutoLogin {

    private static long interval_checke_APP_UPDATE = 60 * 1000 * 60 * 1;

    public static boolean isSuccessLogin = false;


    /**
     * 站点不能用 相关
     * 605 ip限制
     * 607 站点维护
     */
    public static boolean CheckHasMainEnanceError(Response response) {
        String headerStatusCode = response.header("headerStatus");
        if (headerStatusCode != null) {
            if (headerStatusCode.equals("605") || headerStatusCode.equals("607")) {
                ActivityUtil.startMaintenanceActivity(Integer.parseInt(headerStatusCode));
                return false;
            }
        }

        if (response.code() == 605 || response.code() == 607) {
            ActivityUtil.startMaintenanceActivity(response.code());
            return false;
        }
        return true;
    }


    public static void loginOrGoMain(Activity activity) {
        initOkHttpUtils();//重新初始化下okhttpUtils 兼容https
        // checkAppUpdate(activity, lineTaskProgressListener) return;
        if (checkHasUserRecord()) {
            login(activity, null);
        } else {
            getHasAdData(activity, null);
        }
    }

    public static void loginOrGoMain(Activity activity, LineTaskProgressListener lineTaskProgressListener) {
        initOkHttpUtils();//重新初始化下okhttpUtils 兼容https
        // checkAppUpdate(activity, lineTaskProgressListener) return;
        if (checkHasUserRecord()) {
            login(activity, lineTaskProgressListener);
        } else {
            getHasAdData(activity, lineTaskProgressListener);
        }
    }

    /**
     * 检测app 更新   并进行下一步
     *
     * @param activity
     * @param lineTaskProgressListener
     */
    private static void checkAppUpdate(Activity activity, LineTaskProgressListener lineTaskProgressListener) {
        long time = SharePreferenceUtil.getTimeAppUpdate(activity);
        long currentTime = System.currentTimeMillis();
        if (currentTime - time > interval_checke_APP_UPDATE) {
            AppUpdate mAppUpdate = new AppUpdate(activity);
            mAppUpdate.setIsNeedUpdateCallBack(new AppUpdate.UpdateResult() {
                @Override
                public void callBack(boolean result) {
                    if (result) {
                        SharePreferenceUtil.saveTimeAppUpdate(activity, System.currentTimeMillis());
                        if (checkHasUserRecord()) {
                            login(activity, lineTaskProgressListener);
                        } else {
                            getHasAdData(activity, lineTaskProgressListener);
                        }
                    }
                }
            });
            mAppUpdate.checkUpdate();
        } else {
            if (checkHasUserRecord()) {
                login(activity, lineTaskProgressListener);
            } else {
                getHasAdData(activity, lineTaskProgressListener);
            }
        }
    }

    /**
     * 登录
     */

    private static void login(Activity activity, LineTaskProgressListener lineTaskProgressListener) {
        String userName = (String) SPTool.get(BoxApplication.getContext(), ConstantValue.KEY_USERNAME_AUTO_LOGIN, "");
        String password = (String) SPTool.get(BoxApplication.getContext(), ConstantValue.KEY_PASSWORD_AUTO_LOGIN, "");
        CookieManager.getInstance().setCookie(DataCenter.getInstance().getDomain(), "");
        String mIP = DataCenter.getInstance().getIp();
        String url = mIP + ConstantValue.LOGIN_URL;
        OkHttpClient.Builder builder = NetUtil.getOkHttpClientBuilderForHttps();

        RequestBody body = new FormBody.Builder()
                .add("username", userName)
                .add("password", password)
                .add("captcha", "").build();
        Request request = new Request.Builder().url(url)
                .headers(Headers.of(NetUtil.setHeaders()))
                .post(body)
                .build();

        Call call = builder.build().newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e("自动登录 Error ==> " + e.getLocalizedMessage());
                showErrorLoginMsg(activity);
                getHasAdData(activity, lineTaskProgressListener);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!CheckHasMainEnanceError(response)) {
                    activity.finish();
                    return;
                }
                String jsonData = response.body().string();
                LoginBean loginBean = null;
                if (jsonData != null) {
                    try {
                        loginBean = new Gson().fromJson(jsonData, LoginBean.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        showErrorLoginMsg(activity);
                    }

                    if (loginBean != null) {
                        if (loginBean.isSuccess()) {
                            if (response.code() == 200) {
                                LoginSuccess(response, userName, password);
                            } else {
                                showErrorLoginMsg(activity);
                            }
                        } else {
                            showErrorLoginMsg(activity);
                        }
                    } else {
                        showErrorLoginMsg(activity);
                    }
                } else {
                    showErrorLoginMsg(activity);
                }
                getHasAdData(activity, lineTaskProgressListener);
            }
        });
    }


    private static void showErrorLoginMsg(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //   ToastUtil.showResLong(activity, R.string.needRealName);
            }
        });
    }

    /**
     * 登录成功后  做的一些初始化
     *
     * @param response
     * @param successUserName
     * @param successUserPwd
     */
    private static void LoginSuccess(Response response, String successUserName, String successUserPwd) {
        isSuccessLogin = true;
        NetUtil.setCookie(response);
        DataCenter.getInstance().setLogin(true);
        DataCenter.getInstance().setUserName(successUserName);
        DataCenter.getInstance().setPassword(successUserPwd);
        setJpushAlias(successUserName);
        LogUtils.e("自动登录  ==> success " + "初始化成功");
    }

    /**
     * 设置极光推送别名
     *
     * @param userName 用户名
     */
    public static void setJpushAlias(String userName) {
        BoxApplication.handler.post(new Runnable() {
            @Override
            public void run() {
                int action = ACTION_SET;
                TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
                tagAliasBean.action = action;
                sequence++;
                tagAliasBean.alias = userName;
                tagAliasBean.isAliasAction = true;
                TagAliasOperatorHelper.getInstance().handleAction(BoxApplication.getContext(), sequence, tagAliasBean);
            }
        });
    }


    /**
     * delete极光推送别名
     *
     * @param userName 用户名
     */
    public static void deleteJpushAlias(String userName) {
        BoxApplication.handler.post(new Runnable() {
            @Override
            public void run() {
                int action = ACTION_DELETE;
                TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
                tagAliasBean.action = action;
                sequence++;
                tagAliasBean.alias = userName;
                tagAliasBean.isAliasAction = true;
                TagAliasOperatorHelper.getInstance().handleAction(BoxApplication.getContext(), sequence, tagAliasBean);
            }
        });
    }



    /**
     * 获取是否有广告页
     *
     * @param activity
     */

    private static void getHasAdData(Activity activity, LineTaskProgressListener lineTaskProgressListener) {

        OkHttpUtils.get().url(DataCenter.getInstance().getIp() + ConstantValue.AD_URL)
                .headers(NetUtil.setHeaders()).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                if (!CheckHasMainEnanceError(response)) {
                    activity.finish();
                    return null;
                }
                String json = response.body().string();
                Log.e("自动登录-广告页接口 response", json);
                if (json == null) {
                    goMain(activity, lineTaskProgressListener);
                    return null;
                }
                try {
                    AdResponse adResponse = FastJsonUtils.toBean(json, AdResponse.class);
                    if (null == adResponse.getData() || TextUtils.isEmpty(adResponse.getData().getInitAppAd())) {
                        goMain(activity, lineTaskProgressListener);
                    } else {
                        goAd(activity, lineTaskProgressListener, DataCenter.getInstance().getIp() + adResponse.getData().getInitAppAd());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    goMain(activity, lineTaskProgressListener);
                }
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("自动登录-广告页接口 onError", e.getLocalizedMessage() + "");
                goMain(activity, lineTaskProgressListener);
            }

            @Override
            public void onResponse(Object response, int id) {

            }
        });
    }

    /**
     * 跳广告页
     *
     * @param activity
     */
    private static void goAd(Activity activity, LineTaskProgressListener lineTaskProgressListener, String picUrl) {
        if (CacheUtils.getInstance().getBitmap(picUrl) != null) {
            Log.e("自动登录-跳广告页 缓存中获取的Bitmap", CacheUtils.getInstance().getBitmap(picUrl).toString());
            if (lineTaskProgressListener != null) {
                lineTaskProgressListener.onProgressBarChange(100, 100);
            }
            RxBus.get().post(ConstantValue.IS_GUIDE_IMG, picUrl);
//            Intent intent = new Intent(activity, AdvertisingPageActivity.class);
//            intent.putExtra(PIC_AD, picUrl);
//            activity.startActivity(intent);
//            activity.finish();
            return;
        }
        OkHttpUtils.get().url(picUrl)
                .headers(NetUtil.setHeaders()).build().execute(new BitmapCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                goMain(activity, lineTaskProgressListener);
            }

            @Override
            public void onResponse(Bitmap bitmap, int id) {
                if (bitmap == null) {
                    goMain(activity, lineTaskProgressListener);
                } else {
                    Log.e("自动登录-跳广告页 重新获取的Bitmap", bitmap.toString());
                    CacheUtils.getInstance().put(picUrl, bitmap);
                    if (lineTaskProgressListener != null) {
                        lineTaskProgressListener.onProgressBarChange(100, 100);
                    }
                    RxBus.get().post(ConstantValue.IS_GUIDE_IMG, picUrl);
//                    Intent intent = new Intent(activity, AdvertisingPageActivity.class);
//                    intent.putExtra(PIC_AD, picUrl);
//                    activity.startActivity(intent);
//                    activity.finish();
                }
            }
        });
    }

    /**
     * 跳主界面
     *
     * @param activity
     */
    public static void goMain(Activity activity, LineTaskProgressListener lineTaskProgressListener) {
        if (lineTaskProgressListener != null) {
            lineTaskProgressListener.onProgressBarChange(100, 100);
        }
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 检测  sp 里的有无老账户
     *
     * @return
     */
    private static boolean checkHasUserRecord() {
        String userName = (String) SPTool.get(BoxApplication.getContext(), ConstantValue.KEY_USERNAME_AUTO_LOGIN, "");
        String password = (String) SPTool.get(BoxApplication.getContext(), ConstantValue.KEY_PASSWORD_AUTO_LOGIN, "");

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            return false;
        }

        // 时间超过30天没有登录 自动登录失效
        long lastTime = (long) SPTool.get(BoxApplication.getContext(), ConstantValue.KEY_TIME_AUTO_LOGIN, 0L);
        long currentTime = System.currentTimeMillis();
        long interval = 30L * 24L * 60L * 60L * 1000L;
        if (currentTime - lastTime > interval) {
            return false;
        }

        return true;
    }
}
