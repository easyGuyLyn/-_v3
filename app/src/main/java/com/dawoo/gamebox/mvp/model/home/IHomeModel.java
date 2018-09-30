package com.dawoo.gamebox.mvp.model.home;


import android.content.Context;

import com.hwangjr.rxbus.annotation.Subscribe;

import rx.Subscriber;
import rx.Subscription;

/**
 * 主页数据
 * Created by benson on 17-12-21.
 */

public interface IHomeModel {
    Subscription getBanner(Subscriber subscriber);

    Subscription getNotice(Subscriber subscriber);

    Subscription getSiteApiRelation(Subscriber subscriber);

    Subscription getFAB(Subscriber subscriber);

    Subscription countDrawTimes(Subscriber subscriber, String activityMessageId);

    Subscription getPacket(Subscriber subscriber, String activityMessageId, String token);

    Subscription getAccount(Subscriber subscriber);

    Subscription recovery(Subscriber subscriber);

    Subscription refresh(Subscriber subscriber);

    Subscription getTimeZone(Subscriber subscribe);

    Subscription alwaysRequest(Subscriber subscribe);

    Subscription getAPILink(Subscriber subscribe, String link);

}
