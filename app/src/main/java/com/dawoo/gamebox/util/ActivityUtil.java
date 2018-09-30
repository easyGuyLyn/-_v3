package com.dawoo.gamebox.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.dawoo.coretool.util.SPTool;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.util.line.LTHostCheckUtil;
import com.dawoo.gamebox.view.activity.LoginActivity;
import com.dawoo.gamebox.view.activity.MainActivity;
import com.dawoo.gamebox.view.activity.MaintenanceActivity;
import com.dawoo.gamebox.view.activity.webview.WebViewActivity;
import com.hwangjr.rxbus.RxBus;
import com.tencent.smtt.sdk.CookieManager;

import java.util.List;

/**
 * 一些页面的跳转
 * Created by benson on 18-1-14.
 */

public class ActivityUtil {
    private static Context mContext;

    public static void setContext(Context context) {
        mContext = context;
    }


    public static void gotoLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    /**
     * @param url               全路径
     * @param msg               错误信息
     * @param type              类型
     * @param ScreenOrientation 携带屏幕方向  1 必须竖屏  2 必须横屏  3 动态切换
     * @param apiId             游戏api 没有传“”
     */
    public static void startWebView(String url, String msg, String type, int ScreenOrientation, String apiId) {
        if (TextUtils.isEmpty(url) && TextUtils.isEmpty(msg)) {
            ToastUtil.showToastShort(mContext, mContext.getString(R.string.game_maintenance));
            return;
        }

        if (TextUtils.isEmpty(url)) {
            ToastUtil.showToastShort(mContext, msg + "");
            return;
        }

        if (!url.contains("http")) {
            url = DataCenter.getInstance().getIp() + "/" + url;
        }
        NetUtil.setHeaders();
        Log.e("apiId", apiId + "*******");
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(ConstantValue.WEBVIEW_URL, NetUtil.replaceIp2Domain(url));
        intent.putExtra(ConstantValue.WEBVIEW_TYPE, type);
        intent.putExtra(WebViewActivity.SCREEN_ORITATION, ScreenOrientation);
        intent.putExtra(WebViewActivity.GAME_ID, apiId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 用平台域名 访问webview
     */
    public static void startLtDomainWebview(String url_, Context context, String webType, String apiId) {
        final String[] url = {url_};
        String domain = DataCenter.getInstance().getLTDomain();
        if (TextUtils.isEmpty(domain)) {
            SingleToast.showMsg("正在获取可用线路 请稍后重试");
            LTHostCheckUtil.getInstance().startTask();
            LTHostCheckUtil.getInstance().setOnFinishDomain(new LTHostCheckUtil.OnFinishDomain() {
                @Override
                public void onFinish(int state) {
                    switch (state) {
                        case LTHostCheckUtil.Line_Success:
//                            if (!TextUtils.isEmpty(DataCenter.getInstance().getCookie())) {
//                                CookieManager.getInstance().setCookie(domain, DataCenter.getInstance().getCookie());
//                            }
//                            url[0] = "http://" + DataCenter.getInstance().getLTDomain() + url[0];
//                            ActivityUtil.startLTWebView(url[0], "", webType, 1, apiId);
                            break;
                        case LTHostCheckUtil.Line_Faliure:
                            SingleToast.showMsg("网络异常!");
                            break;
                        case LTHostCheckUtil.Line_No_one_Avaliable:
                            SingleToast.showMsg("您所在区域无法获取可用域名");
                            break;
                    }
                }
            });
        } else {
            if (!TextUtils.isEmpty(DataCenter.getInstance().getCookie())) {
                DataCenter.getInstance().getCookie();
                CookieManager.getInstance().setCookie(domain, DataCenter.getInstance().getCookie());
            }
            url[0] = "http://" + domain + url[0];
            ActivityUtil.startLTWebView(url[0], "", webType, 1, apiId);
        }

    }


    /**
     * 彩票专属webView通道
     *
     * @param url               全路径
     * @param msg               错误信息
     * @param type              类型
     * @param ScreenOrientation 携带屏幕方向  1 必须竖屏  2 必须横屏  3 动态切换
     * @param apiId             游戏api 没有传“”
     */
    public static void startLTWebView(String url, String msg, String type, int ScreenOrientation, String apiId) {
        if (TextUtils.isEmpty(url) && TextUtils.isEmpty(msg)) {
            ToastUtil.showToastShort(mContext, mContext.getString(R.string.game_maintenance));
            return;
        }

        if (TextUtils.isEmpty(url)) {
            ToastUtil.showToastShort(mContext, msg + "");
            return;
        }

        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(ConstantValue.WEBVIEW_URL, url);
        intent.putExtra(ConstantValue.WEBVIEW_TYPE, type);
        intent.putExtra(WebViewActivity.SCREEN_ORITATION, ScreenOrientation);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(WebViewActivity.GAME_ID, apiId);
        mContext.startActivity(intent);
    }

    /**
     * 登出
     */
    public static void logout() {
        AutoLogin.deleteJpushAlias(DataCenter.getInstance().getUserName());
        DataCenter.getInstance().setLogin(false);
        DataCenter.getInstance().setCookie("");
        DataCenter.getInstance().setUserName("");
        DataCenter.getInstance().setPassword("");
        SPTool.remove(BoxApplication.getContext(), ConstantValue.KEY_PASSWORD_AUTO_LOGIN);
        RxBus.get().post(ConstantValue.EVENT_TYPE_LOGOUT, "logout");
        ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
    }

    public static void startOtherapp(String packageName) {
        if (isInstalled(packageName)) {
            try {
                PackageManager packageManager
                        = BoxApplication.getContext().getPackageManager();
                Intent intent = packageManager.
                        getLaunchIntentForPackage(packageName);
                mContext.startActivity(intent);
            } catch (Exception e) {
                SingleToast.showMsg("您的手机未安装该应用");
            }
        } else {
            SingleToast.showMsg("您的手机未安装该应用");
        }

    }

    private static boolean isInstalled(String packageName) {
        final PackageManager packageManager = BoxApplication.getContext().getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            Log.e("PackageInfo", pinfo.get(i).packageName);
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * 开启维护界面
     */
    public static void startMaintenanceActivity(int code) {
        Intent intent = new Intent(mContext, MaintenanceActivity.class);
        intent.putExtra(MaintenanceActivity.maintenance_code, code);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
