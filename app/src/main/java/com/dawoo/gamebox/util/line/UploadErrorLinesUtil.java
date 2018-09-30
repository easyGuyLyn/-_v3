package com.dawoo.gamebox.util.line;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.dawoo.coretool.util.SPTool;
import com.dawoo.coretool.util.packageref.PackageInfoUtil;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.BuildConfig;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.SysInfo;
import com.dawoo.gamebox.util.SSLUtil;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by archar on 18-1-25.
 * <p>
 * 上传所有的错误线路信息
 */

public class UploadErrorLinesUtil {

    private static int count = 0;

    /**
     * 上传app崩溃日志
     *
     * @param errorMessages
     * @param mark
     * @param isApp         2:为app崩溃bug  1为域名bug
     */
    public static void uploadAppCrashLog(String errorMessages, String codes, String mark, boolean isApp) {
        Log.e("UploadErrorLinesUtil", "errorMessages -> " + errorMessages);
        Log.e("UploadErrorLinesUtil", "mark -> " + mark);
        String siteId = resolvePackgeName();
        String userName = DataCenter.getInstance().getUserName();
        String lastLoginTime = "";
        String domain = DataCenter.getInstance().getDomain();
        String ip = DataCenter.getInstance().getIp();
        doUpload(siteId, userName, lastLoginTime, domain, ip, errorMessages, codes, mark, isApp);
    }


    /**
     * 上传域名检测
     *
     * @param domains
     * @param ip
     * @param errorMessages
     * @param codes
     * @param mark
     */
    public static void upload(String domains, String ip, String errorMessages, String codes, String mark) {
        String siteId = resolvePackgeName();
        String userName = (String) SPTool.get(BoxApplication.getContext(), ConstantValue.KEY_USERNAME, "");
        String lastLoginTime = "";
        doUpload(siteId, userName, lastLoginTime, domains, ip, errorMessages, codes, mark, false);
    }


    /**
     * 发起请求
     *
     * @param siteId
     * @param userName
     * @param lastLoginTime
     * @param domain
     * @param ip
     * @param errorMessages
     * @param codes
     * @param mark
     * @param isApp
     */
    public static void doUpload(String siteId, String userName, String lastLoginTime, String domain, String ip,
                                String errorMessages, String codes, String mark, boolean isApp) {
        if (TextUtils.isEmpty(domain)) {
            return;
        }
        if (count > 0) return;
        String url = ConstantValue.BASE_URL + ConstantValue.COLLECT_APP_DOMAINS;


        Log.e("UploadErrorLinesUtil", "siteId -> " + siteId);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().sslSocketFactory(SSLUtil.createSSLSocketFactory(), new SSLUtil.TrustAllManager());

        String errorType;
        if (isApp) {
            errorType = "错误类型: crash 闪退";
        } else {
            errorType = "错误类型: 线路错误信息";
        }

        RequestBody body = new FormBody.Builder()
                .add("siteId", siteId) // 站点
                .add("username", userName + "  |版本号: " + PackageInfoUtil.getVersionCode(BoxApplication.getContext()))
                .add("lastLoginTime", lastLoginTime) // 最后登录时间
                .add("domain", domain) // 域名
                .add("ip", ip) // ip
                .add("errorMessage", errorType + "\n" + errorMessages) // 错误信息
                .add("code", codes) // 站点标示 或者错误域名的code
                .add("mark", mark) // 标记随机码
                .add("type", isApp ? "2" : "1")//1域名 2crash
                .add("versionName", PackageInfoUtil.getVersionName(BoxApplication.getContext()) +
                        " / " + PackageInfoUtil.getVersionCode(BoxApplication.getContext())) // 版本号
                .add("channel", "android")// 不同系统
                .add("sysCode", Build.VERSION.RELEASE)// 系统版本号
                .add("brand", Build.BRAND)//手机品牌
                .add("model", Build.MODEL)// 手机型号
                .build();
        Request request = new Request.Builder().url(url).post(body).build();
        count++;
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("UploadErrorLinesUtil", "上传错误线路信息失败: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("UploadErrorLinesUtil  ", "上传错误线路信息结果 response:" + response.toString());
                Log.e("UploadErrorLinesUtil  ", "上传错误线路信息结果 code:" + response.code());
            }
        });
    }


    /**
     * 处理站点id
     *
     * @return
     */
    public static String resolvePackgeName() {
        String packageName = BoxApplication.getContext().getPackageName();

        String subStr = packageName.substring(packageName.indexOf("sid"));
        if (subStr.contains("debug")) {
            return subStr.substring(3, subStr.lastIndexOf("."));
        } else {
            return subStr.substring(3);
        }
    }


    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
