package com.dawoo.gamebox.mvp.presenter;

import android.content.Context;

import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.net.TlsSniSocketFactory;
import com.dawoo.gamebox.net.TrueHostnameVerifier;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SSLUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.dawoo.gamebox.util.NetUtil.getOkHttpClientBuilderForHttps;

public class ForgetPasswordPresenter<T extends IBaseView> extends  BasePresenter {
    public ForgetPasswordPresenter(Context mContext, T view) {
        super(mContext, view);
    }



    public Response getReslut(String url , RequestBody body){

        final Response[] responsed = {null};

        OkHttpClient.Builder builder = getOkHttpClientBuilderForHttps();

        Request request = new Request.Builder().url(url)
                .headers(Headers.of(NetUtil.setHeaders()))
                .post(body)
                .build();

        Call call = builder.build().newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responsed[0] = response;
            }
        });

        return responsed[0];
    }
}
