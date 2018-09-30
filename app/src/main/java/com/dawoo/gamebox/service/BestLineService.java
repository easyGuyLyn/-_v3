package com.dawoo.gamebox.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.net.RetrofitHelper;
import com.dawoo.gamebox.util.PushControlUtils;
import com.dawoo.gamebox.util.line.LineHelperUtil;
import com.dawoo.gamebox.util.line.LineTaskBaseListener;
import com.hwangjr.rxbus.RxBus;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * 最优线路服务
 * Created by benson on 18-4-24.
 */

public class BestLineService extends Service {
    private LineHelperUtil mHelperUtil;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BestLineBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RxBus.get().register(this);
        mHelperUtil = new LineHelperUtil();
        mHelperUtil.setLineTaskBaseListener(new LineTaskBaseListener() {
            @Override
            public void onGetLineSuccess(String domain, String ip) {
                RetrofitHelper.getInstance().reSetRetrofitHelper();
                BoxApplication.initOkHttpUtils();
                PushControlUtils.connect();
            }
        });
    }

    public class BestLineBinder extends Binder {

        private Subscription mSubscription;

        public void startGetBestLine(Activity activity) {
            mSubscription = Observable.interval(5, TimeUnit.MINUTES)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            if (mHelperUtil != null) {
                                Log.e("LineHelperUtil", "service check");
                                mHelperUtil.checkSp();
                            }
                        }
                    });

        }

        public void stopGetBestLine() {
            if (mSubscription != null && !mSubscription.isUnsubscribed()) {
                mSubscription.unsubscribe();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
