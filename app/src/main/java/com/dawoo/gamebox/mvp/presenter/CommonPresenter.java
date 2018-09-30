package com.dawoo.gamebox.mvp.presenter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.mvp.model.mine.MineModel;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.ICommonProblemView;
import com.dawoo.gamebox.mvp.view.IGetUserPlayerRecommendView;
import com.dawoo.gamebox.mvp.view.IMineAboutView;
import com.dawoo.gamebox.mvp.view.IMineFragmentView;
import com.dawoo.gamebox.mvp.view.IMineHelpDerailsView;
import com.dawoo.gamebox.mvp.view.IMineTeamsView;
import com.dawoo.gamebox.mvp.view.ISeconundTypeView;
import com.dawoo.gamebox.mvp.view.IShareRuleRecordView;
import com.dawoo.gamebox.mvp.view.IShareRuleView;
import com.dawoo.gamebox.net.rx.ProgressSubscriber;
import com.dawoo.gamebox.util.StringTool;
import com.dawoo.gamebox.view.fragment.ShareRecodingFragment;

import java.util.Calendar;

import rx.Subscription;


/**
 * 我的页面presenter
 */

public class CommonPresenter<T extends IBaseView> extends BasePresenter {

    private final Context mContext;
    private T mView;
    private final MineModel mMineModel;
    private DatePickerDialog mDialog;

    public CommonPresenter(Context context, T mView) {
        super(context, mView);
        mContext = context;
        this.mView = mView;

        mMineModel = new MineModel();
    }


    /**
     * 我的页面获取相关数据
     */
    public void getLink() {
        Subscription subscription = mMineModel.getLink(new ProgressSubscriber<>(o -> ((IMineFragmentView) mView).onLinkResult(o), mContext));
        subList.add(subscription);
    }


    public void getUserPlayerRecommend() {
        Subscription subscription = mMineModel.getUserPlayerRecommend(new ProgressSubscriber<>(o -> ((IGetUserPlayerRecommendView) mView).onResult(o), mContext));
        subList.add(subscription);
    }

    /**
     * 分享规则
     */
    public void gradientTempArrayList(String startTime,
                                      String endTime,
                                      Integer pageNumber,
                                      Integer pageSize) {
        String startTim = startTime + ShareRecodingFragment.DATE_MIN_TIME;
        String endTim = endTime + ShareRecodingFragment.DATE_MAX_TIME;
        Subscription subscription = mMineModel.gradientTempArrayList(

                new ProgressSubscriber(o -> ((IShareRuleView) mView).onResult(o), mContext),
                startTim,
                endTim,
                pageNumber,
                pageSize);
        subList.add(subscription);
    }

    /**
     * 加载更多的分享规则
     */
    public void gradientTempLoadMore(String startTime,
                                     String endTime,
                                     Integer pageNumber,
                                     Integer pageSize) {
        String startTim = startTime + ShareRecodingFragment.DATE_MIN_TIME;
        String endTim = endTime + ShareRecodingFragment.DATE_MAX_TIME;
        Subscription subscription = mMineModel.gradientTempArrayList(
                new ProgressSubscriber(o -> ((IShareRuleView) mView).LoadMore(o), mContext),
                startTim,
                endTim,
                pageNumber,
                pageSize);
        subList.add(subscription);
    }


    public void getShareUserPlayerRecommend() {
        Subscription subscription = mMineModel.getUserPlayerRecommend(new ProgressSubscriber<>(o -> ((IShareRuleRecordView) mView).onResult(o), mContext));
        subList.add(subscription);
    }

    /**
     * 关于我们
     */
    public void getAbout() {
        Subscription subscription = mMineModel.about(new ProgressSubscriber<>(o -> ((IMineAboutView) mView).onAboutResult(o), mContext));
        subList.add(subscription);
    }


    /**
     * 详细条款
     */
    public void getTears() {
        Subscription subscription = mMineModel.terms(new ProgressSubscriber<>(o -> ((IMineTeamsView) mView).onResult(o), mContext));
        subList.add(subscription);
    }

    /**
     * 常见问题
     * onErrorSimpleReason
     */
    public void getCommentProblem() {
        Subscription subscription = mMineModel.helpFirstType(new ProgressSubscriber<>(o -> ((ICommonProblemView) mView).onResult(o), mContext));
        subList.add(subscription);
    }


    /**
     * 常见问题二级
     */
    public void getSeconundType(String searchId) {
        Subscription subscription = mMineModel.secondType(new ProgressSubscriber<>(o -> ((ISeconundTypeView) mView).onResult(o), mContext), searchId);
        subList.add(subscription);
    }

    /**
     * 常见问题三级
     */

    public void helpDetail(String searchId) {
        Subscription subscription = mMineModel.helpDetail(new ProgressSubscriber<>(o -> ((IMineHelpDerailsView) mView).onResult(o), mContext), searchId);
        subList.add(subscription);
    }

    /**
     * 時間選擇器
     *
     * @param type 類型：０　開始　　，１　結束
     */
    public void selectTime(int type) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        mDialog = new DatePickerDialog(mContext, (view, year1, month1, dayOfMonth) -> {
            String month_ = month1 + 1 + "";
            String day1 = "" + dayOfMonth;
            if ((month1 + 1) < 10)
                month_ = "0" + month_;
            if (dayOfMonth < 10)
                day1 = "0" + day1;
            if (type == 0)
                ((IShareRuleView) mView).chooseEndTime(year1 + "-" + month_ + "-" + day1);
            else
                ((IShareRuleView) mView).chooseStartTime(year1 + "-" + month_ + "-" + day1);
        }, year, month, day);
        mDialog.setCustomTitle(null);
        DatePicker dp = mDialog.getDatePicker();
        mDialog.show();

    }


    @Override
    public void onDestory() {
        super.onDestory();
    }
}
