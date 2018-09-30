package com.dawoo.gamebox.mvp.model.mine;

import com.dawoo.gamebox.bean.AboutBean;
import com.dawoo.gamebox.bean.CommentNextProblemBean;
import com.dawoo.gamebox.bean.GradientTemp;
import com.dawoo.gamebox.bean.HelpDetailsBean;
import com.dawoo.gamebox.bean.MineLink;
import com.dawoo.gamebox.bean.MineTeamsBean;
import com.dawoo.gamebox.bean.ProblemBean;
import com.dawoo.gamebox.bean.UserPlayerRecommend;
import com.dawoo.gamebox.mvp.model.BaseModel;
import com.dawoo.gamebox.mvp.service.IMineService;
import com.dawoo.gamebox.net.RetrofitHelper;
import com.dawoo.gamebox.view.fragment.ShareRecodingFragment;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by benson on 18-1-4.
 */

public class MineModel extends BaseModel implements IMineModel {
    @Override
    public Subscription getLink(Subscriber subscriber) {
        Observable<MineLink> observable = RetrofitHelper.getService(IMineService.class).getLink().map(new HttpResultFunc<MineLink>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription getUserPlayerRecommend(Subscriber subscriber) {
        Observable<UserPlayerRecommend> observable = RetrofitHelper.getService(IMineService.class).getUserPlayerRecommend().map(new HttpResultFunc<UserPlayerRecommend>());
        return toSubscribe(observable, subscriber);
    }


    @Override
    public Subscription terms(Subscriber subscriber) {
        Observable<MineTeamsBean> observable = RetrofitHelper.getService(IMineService.class).terms().map(new HttpResultFunc<MineTeamsBean>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription about(Subscriber subscriber) {
        Observable<AboutBean> observable = RetrofitHelper.getService(IMineService.class).about().map(new HttpResultFunc<AboutBean>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription helpFirstType(Subscriber subscriber) {
        Observable<List<ProblemBean>> observable = RetrofitHelper.getService(IMineService.class).helpFirstType().map(new HttpResultFunc<List<ProblemBean>>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription secondType(Subscriber subscriber, String searchId) {
        Observable<CommentNextProblemBean> observable = RetrofitHelper
                .getService(IMineService.class)
                .secondType(searchId)
                .map(new HttpResultFunc<CommentNextProblemBean>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription helpDetail(Subscriber subscriber, String searchId) {
        Observable<HelpDetailsBean> observable = RetrofitHelper
                .getService(IMineService.class)
                .helpDetail(searchId)
                .map(new HttpResultFunc<HelpDetailsBean>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription gradientTempArrayList(Subscriber subscriber, String startTime, String endTime, Integer pageNumber, Integer pageSize) {
        Observable<GradientTemp> observable = RetrofitHelper.getService(IMineService.class).getPlayerRecommendRecord(startTime, endTime, pageNumber, pageSize).map(new HttpResultFunc<GradientTemp>());
        return toSubscribe(observable, subscriber);
    }
}
