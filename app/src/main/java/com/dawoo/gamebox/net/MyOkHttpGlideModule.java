package com.dawoo.gamebox.net;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.BuildConfig;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SSLUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by benson on 18-5-6.
 */
@GlideModule
public class MyOkHttpGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(interceptor) // 添加httplog
                .sslSocketFactory(new TlsSniSocketFactory(), new SSLUtil.TrustAllManager())
                .hostnameVerifier(new TrueHostnameVerifier())
                .build();

        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(client);
        registry.replace(GlideUrl.class, InputStream.class, factory);
    }

    //isManifestParsingEnabled 设置清单解析，设置为false，避免添加相同的modules两次
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }


    private class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder()
                    .headers(Headers.of(NetUtil.setHeaders()));
            request = builder.build();
            return chain.proceed(request);
        }
    }
}
