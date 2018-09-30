package com.dawoo.gamebox.mvp.model.home;

import com.dawoo.gamebox.bean.Banner;
import com.dawoo.gamebox.bean.FAB;
import com.dawoo.gamebox.bean.GetPacket;
import com.dawoo.gamebox.bean.HongbaoCount;
import com.dawoo.gamebox.bean.Notice;
import com.dawoo.gamebox.bean.RefreshhApis;
import com.dawoo.gamebox.bean.SiteApiRelation;
import com.dawoo.gamebox.bean.UrlBean;
import com.dawoo.gamebox.bean.UserAccount;
import com.dawoo.gamebox.mvp.model.BaseModel;
import com.dawoo.gamebox.mvp.service.IHomeService;
import com.dawoo.gamebox.net.HttpResult;
import com.dawoo.gamebox.net.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;


/**
 * Created by benson on 17-12-21.
 */

public class HomeModel extends BaseModel implements IHomeModel {


    @Override
    public Subscription getBanner(Subscriber subscriber) {
        Observable<Banner> observable = RetrofitHelper.getService(IHomeService.class).getBanner().map(new HttpResultFunc<Banner>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription getNotice(Subscriber subscriber) {
        Observable<Notice> observable = RetrofitHelper.getService(IHomeService.class).getNotice().map(new HttpResultFunc<Notice>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription getSiteApiRelation(Subscriber subscriber) {
        Observable<List<SiteApiRelation>> observable = RetrofitHelper.getService(IHomeService.class).getSiteApiRelation().map(new HttpResultFunc<>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription getFAB(Subscriber subscriber) {
        Observable<FAB> observable = RetrofitHelper.getService(IHomeService.class).getFAB().map(new HttpResultFunc<FAB>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription countDrawTimes(Subscriber subscriber, String activityMessageId) {
        Observable<HongbaoCount> observable = RetrofitHelper.getService(IHomeService.class).countDrawTimes(activityMessageId).map(new HttpResultFunc<HongbaoCount>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription getPacket(Subscriber subscriber, String activityMessageId, String token) {
        Observable<GetPacket> observable = RetrofitHelper.getService(IHomeService.class).getPacket(activityMessageId, token).map(new HttpResultFunc<GetPacket>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription getAccount(Subscriber subscriber) {
        Observable<UserAccount> observable = RetrofitHelper.getService(IHomeService.class).getUserInfo().map(new HttpResultFunc<UserAccount>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription recovery(Subscriber subscriber) {
        Observable<HttpResult> observable = RetrofitHelper.getService(IHomeService.class).recovery();
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription refresh(Subscriber subscriber) {
        Observable<RefreshhApis> observable = RetrofitHelper.getService(IHomeService.class).refresh().map(new HttpResultFunc<>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription getTimeZone(Subscriber subscribe) {
        Observable<String> observable = RetrofitHelper.getService(IHomeService.class).getTimeZone().map(new HttpResultFunc<>());
        return toSubscribe(observable, subscribe);
    }


    @Override
    public Subscription alwaysRequest(Subscriber subscribe) {
        Observable<String> observable = RetrofitHelper.getService(IHomeService.class).alwaysRequest().map(new HttpResultFunc<>());
        return toSubscribe(observable, subscribe);
    }

    @Override
    public Subscription getAPILink(Subscriber subscribe, String link) {
        Observable<UrlBean> observable = RetrofitHelper.getService(IHomeService.class).getAPILink(link).map(new HttpResultFunc<>());
        return toSubscribe(observable, subscribe);
    }
}
