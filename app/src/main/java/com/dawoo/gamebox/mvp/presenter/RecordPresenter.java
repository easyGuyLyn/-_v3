package com.dawoo.gamebox.mvp.presenter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.dawoo.coretool.util.date.DateTool;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.mvp.model.record.RecordModel;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.IBettingDetailView;
import com.dawoo.gamebox.mvp.view.ICapitalDetailView;
import com.dawoo.gamebox.mvp.view.ICapitalRecordView;
import com.dawoo.gamebox.mvp.view.IMypromoListView;
import com.dawoo.gamebox.mvp.view.INoteRecordView;
import com.dawoo.gamebox.net.rx.ProgressSubscriber;
import com.dawoo.gamebox.util.SharePreferenceUtil;

import java.util.Calendar;

import rx.Subscription;

import static com.dawoo.gamebox.ConstantValue.RECORD_LIST_PAGE_NUMBER;


/**
 * 记录相关
 * Created by benson on 18-01-07.
 */

public class RecordPresenter<T extends IBaseView> extends BasePresenter {

    private final Context mContext;
    private T mView;
    private final RecordModel mModel;
    private DatePickerDialog mDialog;


    public RecordPresenter(Context context, T mView) {
        super(context, mView);

        mContext = context;
        this.mView = mView;
        mModel = new RecordModel();
    }


    /**
     * 获取投注记录,列表和统计
     */
    public void getNoteRecord(String beginBetTime,
                              String endBetTime,
                              int pageSize,
                              int pageNumber, boolean isShowStatistics) {
        Subscription subscription = mModel.getNoteRecord(
                new ProgressSubscriber(o -> ((INoteRecordView) mView).onRecordResult(o), mContext),
                beginBetTime,
                endBetTime,
                pageSize,
                pageNumber,
                isShowStatistics);
        subList.add(subscription);
    }

    /**
     * 获取资金记录
     */
    public void getCapitalRecord(String beginBetTime, String endBetTime, String transactionType, int pageNumber, int pageSize) {
        Subscription subscription = mModel.getCapitalRecord(new ProgressSubscriber(o -> ((INoteRecordView) mView).onRecordResult(o), mContext)
                , beginBetTime, endBetTime, transactionType, pageNumber, pageSize);
        subList.add(subscription);
    }

    /**
     * 获取更多资金记录
     */
    public void getCapitalMoreRecord(String beginBetTime, String endBetTime, String transactionType, int pageNumber, int pageSize) {
        Subscription subscription = mModel.getCapitalRecord(new ProgressSubscriber(o -> ((INoteRecordView) mView).loadMoreData(o), mContext)
                , beginBetTime, endBetTime, transactionType, pageNumber, pageSize);
        subList.add(subscription);
    }

    /**
     * 获取资金记录类型
     */
    public void getCapitalRecordType() {
        Subscription subscription = mModel.getCapitalRecordType(new ProgressSubscriber(o -> ((ICapitalRecordView) mView).chooseTypeResult(o), mContext));
        subList.add(subscription);
    }

    /**
     * 获取资金记录详情
     */
    public void getCapitalRecordDetail(int id) {
        Subscription subscription = mModel.getCapitalRecordDetail(new ProgressSubscriber(o -> ((ICapitalDetailView) mView).onCapitalDetailResult(o), mContext), id);
        subList.add(subscription);
    }


    /**
     * 時間選擇器
     *
     * @param type 類型：０　開始　　，１　結束
     */
    public void selectTime(int type, long minTime, long maxTime) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        mDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String month_ = month + 1 + "";
                String day = "" + dayOfMonth;
                if ((month + 1) < 10)
                    month_ = "0" + month_;
                if (dayOfMonth < 10)
                    day = "0" + day;
                if (type == 0)
                    ((INoteRecordView) mView).chooseStartTime(year + "-" + month_ + "-" + day);
                else
                    ((INoteRecordView) mView).chooseEndTime(year + "-" + month_ + "-" + day);
            }
        }, year, month, day);
        mDialog.setCustomTitle(null);
        DatePicker dp = mDialog.getDatePicker();
        if (minTime != 0)
            dp.setMinDate(minTime);

        if (maxTime != 0)
            dp.setMaxDate(maxTime);

        mDialog.show();

    }

    /**
     * 按時間搜索
     */
    public void doSearch(String startTime, String endTime) {
//        String zoneTime = SharePreferenceUtil.getTimeZone(mContext);
//        String startStr = DateTool.convertStringWithTimeZone(startTime, "yyyy-MM-dd", zoneTime);
//        String endStr = DateTool.convertStringWithTimeZone(endTime, "yyyy-MM-dd", zoneTime);
        Subscription subscription = mModel.getNoteRecord(
                new ProgressSubscriber(o -> ((INoteRecordView) mView).doSearch(o), mContext),
                startTime,
                endTime,
                ConstantValue.RECORD_LIST_PAGE_SIZE,
                RECORD_LIST_PAGE_NUMBER,
                true);
        subList.add(subscription);

    }

    /**
     * 加载更多
     */
    public void loadMoreData(String beginBetTime,
                             String endBetTime,
                             int pageNumber) {
//        String startStr = DateTool.convertStringWithTimeZone(beginBetTime, "yyyy-MM-dd", SharePreferenceUtil.getTimeZone(mContext));
//        String endStr = DateTool.convertStringWithTimeZone(endBetTime, "yyyy-MM-dd", SharePreferenceUtil.getTimeZone(mContext));
        Subscription subscription = mModel.getNoteRecord(
                new ProgressSubscriber(o -> ((INoteRecordView) mView).loadMoreData(o), mContext),
                beginBetTime,
                endBetTime,
                ConstantValue.RECORD_LIST_PAGE_SIZE,
                pageNumber,
                false);
        subList.add(subscription);


    }

    /**
     * 投资详情
     */
    public void getBettingDetail(String id) {
        Subscription subscription = mModel.getBettingDetail(new ProgressSubscriber<>(o -> ((IBettingDetailView) mView).onBettingDetailResult(o), mContext), id);
        subList.add(subscription);
    }


    /**
     * 优惠记录
     * 加載普通數據
     *
     * @param pageNumber
     * @param pageSize
     */
    public void getMypromoList(int pageNumber, int pageSize) {
        Subscription subscription = mModel.getMyPromo(new ProgressSubscriber(o ->
                        ((IMypromoListView) mView).onLoadResult(o), mContext),
                pageNumber,
                pageSize
        );
        subList.add(subscription);
    }


    /**
     *
     * 优惠记录
     * 加载更多数据
     *
     * @param pageNumber
     * @param pageSize
     */
    public void getMorePromomoreList(int pageNumber, int pageSize) {
        Subscription subscription = mModel.getMyPromo(new ProgressSubscriber(o ->
                        ((IMypromoListView) mView).loadMoreData(o), mContext),
                pageNumber,
                pageSize
        );
        subList.add(subscription);
    }



    @Override
    public void onDestory() {
        super.onDestory();
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
