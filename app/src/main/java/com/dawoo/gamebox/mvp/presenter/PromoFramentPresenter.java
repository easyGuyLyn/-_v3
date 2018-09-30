package com.dawoo.gamebox.mvp.presenter;

import android.content.Context;

import com.dawoo.gamebox.mvp.model.record.RecordModel;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.IPromoFragmentView;
import com.dawoo.gamebox.net.rx.ProgressSubscriber;

import rx.Subscription;


/**
 * 优惠presenter
 */

public class PromoFramentPresenter<T extends IBaseView> extends BasePresenter {

    private final Context mContext;
    private T mView;
    private final RecordModel mModel;

    public PromoFramentPresenter(Context context, T mView) {
        super(context, mView);
        mContext = context;
        this.mView = mView;
        mModel = new RecordModel();
    }

    /**
     * 获取优惠类别和列表
     */
    public void getActivityType() {
        Subscription subscription = mModel.getActivitysType(new ProgressSubscriber(o -> ((IPromoFragmentView) mView).onPromoResult(o), mContext));
        subList.add(subscription);
    }

    /**
     * 获取下面列表的数据
     */
    public void getLoadMoreListDate(int pageNumber, int pageSize, String activityClassifyKey) {
        Subscription subscription = mModel.getActivityTypeList(new ProgressSubscriber(o ->
                        ((IPromoFragmentView) mView).loadMoreListDate(o), mContext),
                pageNumber,
                pageSize,
                activityClassifyKey
        );
        subList.add(subscription);
    }

    /**
     * 获取下面列表的数据
     */
    public void getPromoListResult(int pageNumber, int pageSize, String activityClassifyKey) {
        Subscription subscription = mModel.getActivityTypeList(new ProgressSubscriber(o ->
                        ((IPromoFragmentView) mView).onPromoListResult(o), mContext),
                pageNumber,
                pageSize,
                activityClassifyKey
        );
        subList.add(subscription);
    }


    @Override
    public void onDestory() {
        super.onDestory();
    }


}
