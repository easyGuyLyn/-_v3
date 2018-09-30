package com.dawoo.gamebox.mvp.model.mine;

import rx.Subscriber;
import rx.Subscription;

/**
 * 我的页面model
 * Created by benson on 18-1-4.
 */

public interface IMineModel {
    Subscription getLink(Subscriber subscriber);

    Subscription getUserPlayerRecommend(Subscriber subscriber);

    Subscription terms(Subscriber subscriber);

    Subscription about(Subscriber subscriber);


    Subscription helpFirstType(Subscriber subscriber);

    Subscription secondType(Subscriber subscriber, String searchId);

    Subscription helpDetail(Subscriber subscriber, String searchId);


    Subscription gradientTempArrayList(Subscriber subscriber, String startTime,
                                       String endTime,
                                       Integer pageNumber,
                                       Integer pageSize);


}
