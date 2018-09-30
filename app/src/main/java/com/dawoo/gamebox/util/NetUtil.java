package com.dawoo.gamebox.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.packageref.PackageInfoUtil;
import com.dawoo.gamebox.net.TlsSniSocketFactory;
import com.dawoo.gamebox.net.TrueHostnameVerifier;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;

import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.bean.DataCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.dawoo.gamebox.net.RetrofitHelper.DEFAULT_READ_TIMEOUT_SECONDS;
import static com.dawoo.gamebox.net.RetrofitHelper.DEFAULT_TIMEOUT_SECONDS;
import static com.dawoo.gamebox.net.RetrofitHelper.DEFAULT_WRITE_TIMEOUT_SECONDS;

/**
 * Created by benson on 18-1-3.
 */

public class NetUtil {
    public static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        if (userAgent == null || "".equals(userAgent)) {
            userAgent = android.webkit.WebSettings.getDefaultUserAgent(context);
        }


        String ua = userAgent.replace("Android", "app_android");
        userAgent = ua + "; app_version=v3.0";

        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @NonNull
    public static Map<String, String> setHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", DataCenter.getInstance().getDomain());
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("User-Agent", "app_android;Android;"
                + PackageInfoUtil.getVersionName(BoxApplication.getContext())
                + "  "
                + PackageInfoUtil.getVersionCode(BoxApplication.getContext()));

        headers.put("Cookie", "");
        headers.put("Connection", "close");
        String cookie = DataCenter.getInstance().getCookie();
        LogUtils.e(String.format("请求的Cookie --> %s", cookie));
        CookieManager cookieManager = CookieManager.getInstance();
        if (!TextUtils.isEmpty(cookie)) {
            headers.put("Cookie", cookie);
            if (cookieManager != null) {
                cookieManager.setCookie(DataCenter.getInstance().getDomain(), cookie);
            }
        }
        return headers;
    }

    /**
     * 登录后设置cookie
     */
    public static void setCookie(Response response) {
        List<String> cookies = response.headers().values("Set-Cookie");
        for (String cookie : cookies) {
            if (cookie.contains("SID=") && cookie.length() > 80) {
                LogUtils.e("登录后Cookie ==> " + cookie);
                DataCenter.getInstance().setCookie(cookie);
                CookieManager.getInstance().setCookie(DataCenter.getInstance().getDomain(), cookie);
            }
        }
    }

    /**
     * 这个两个在 API level 21 被抛弃
     * CookieManager.getInstance().removeSessionCookie();
     * CookieManager.getInstance().removeAllCookie();
     * <p>
     * 推荐使用这两个， level 21 新加的
     * CookieManager.getInstance().removeSessionCookies();
     * CookieManager.getInstance().removeAllCookies();
     **/
    public static void removeCookies() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.createInstance(BoxApplication.getContext());
            CookieSyncManager.getInstance().sync();
        }
    }

    /**
     * 将cookie同步到WebView
     *
     * @param url    WebView要加载的url
     * @param cookie 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     */
    public static boolean syncCookie(String url, String cookie) {
        String domain = DataCenter.getInstance().getIp();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            android.webkit.CookieSyncManager cookieSyncManager = android.webkit.CookieSyncManager.createInstance(BoxApplication.getContext());
            cookieSyncManager.sync();
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookie);
        String newCookie = cookieManager.getCookie(domain);
        return !TextUtils.isEmpty(newCookie);
    }

    public static String handleUrl(String url) {
        String ip = DataCenter.getInstance().getIp();
        // 是不是http开头
        // 第一个字符是不是包含反斜杠
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (!url.contains("http") && !url.contains("www.")) {
            if (url.indexOf("/") == 0) {
                return (ip + url);
            } else {
                return (ip + "/" + url);
            }
        }
        return url;
    }


    static String https = "https://";
    static String http = "http://";
    static String port8989 = ":8989";
    static String port8787 = ":8787";

    public static String replaceIp2Domain(String mUrl) {
        String ip = DataCenter.getInstance().getIp();
        String domain = DataCenter.getInstance().getDomain();

        if (ip.contains(https)) {
            domain = https + domain;
        } else if (ip.contains(http)) {
            domain = http + domain;
        }
        if (ip.contains(port8989)) {
          //  domain = domain + port8989;
        } else if (ip.contains(port8787)) {
         //   domain = domain + port8787;
        }
        mUrl = mUrl.replace(ip, domain);
        return mUrl;
    }


    /**
     * 剥离http/https和端口
     *
     * @param domain
     * @return
     */
    public static String leftOutHttpFromDomain(String domain) {
        if (TextUtils.isEmpty(domain)) {
            return "";
        }
        if (domain.contains(https)) {
            domain = domain.replace(https, "");
        } else if (domain.contains(http)) {
            domain = domain.replace(http, "");
        }

        if (domain.contains(port8989)) {
            domain = domain.replace(port8989, "");
        } else if (domain.contains(port8787)) {
            domain = domain.replace(port8787, "");
        }

        return domain;
    }

    /**
     * 创建 OkHttpClient.Builder
     *
     * @return
     */
    public static OkHttpClient.Builder getOkHttpClientBuilderForHttps() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//失败重连
                .sslSocketFactory(new TlsSniSocketFactory(), new SSLUtil.TrustAllManager())
                .hostnameVerifier(new TrueHostnameVerifier());
        return builder;
    }


    /**
     * 统一设置
     *
     * @return
     */
    public static Request getOkhttpRequest(String url) {
        Request request = new Request.Builder().url(url)
                .headers(Headers.of(NetUtil.setHeaders()))
                .get()
                .build();

        return request;
    }

}
