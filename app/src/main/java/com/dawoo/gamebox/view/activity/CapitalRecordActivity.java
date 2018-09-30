package com.dawoo.gamebox.view.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.date.DateTool;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.CapitalRecord;
import com.dawoo.gamebox.bean.CapitalRecordType;
import com.dawoo.gamebox.mvp.presenter.RecordPresenter;
import com.dawoo.gamebox.mvp.view.ICapitalRecordView;
import com.dawoo.gamebox.mvp.view.INoteRecordView;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.util.StringTool;
import com.dawoo.gamebox.view.view.CustomPopupWindow;
import com.dawoo.gamebox.view.view.HeaderView;
import com.dawoo.gamebox.view.view.swipetoloadlayout.LoadMoreFooterView;
import com.dawoo.gamebox.view.view.swipetoloadlayout.RefreshHeaderView;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 资金记录
 * <p>
 * do by  archar
 */
public class CapitalRecordActivity extends BaseActivity implements INoteRecordView, ICapitalRecordView, OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.start_time_tv)
    TextView mStartTimeTv;
    @BindView(R.id.start_time_fl)
    FrameLayout mStartTimeFl;
    @BindView(R.id.fv)
    TextView mFv;
    @BindView(R.id.end_time_tv)
    TextView mEndTimeTv;
    @BindView(R.id.end_time_fl)
    FrameLayout mEndTimeFl;
    @BindView(R.id.fast_choose_btn)
    Button mFastChooseBtn;
    @BindView(R.id.paras_rl)
    RelativeLayout mParasRl;
    @BindView(R.id.type_choose)
    TextView mTypeChoose;
    @BindView(R.id.search_btn)
    Button mSearchBtn;
    @BindView(R.id.title_list_ll)
    LinearLayout mTitleListLl;
    @BindView(R.id.divider_1)
    View mDivider1;
    @BindView(R.id.drawcash_lable)
    TextView mDrawcashLable;
    @BindView(R.id.drawcash_value)
    TextView mDrawcashValue;
    @BindView(R.id.transfer_lable)
    TextView mTransferLable;
    @BindView(R.id.transfer_value)
    TextView mTransferValue;
    @BindView(R.id.doing_rl)
    RelativeLayout mDoingRl;
    @BindView(R.id.total_account)
    TextView mTotalAccount;
    @BindView(R.id.account_banlance)
    TextView mAccountBanlance;
    @BindView(R.id.payout_reward)
    TextView mPayoutReward;
    @BindView(R.id.bottom_1)
    LinearLayout mBottom1;
    @BindView(R.id.effective_betting)
    TextView mEffectiveBetting;
    @BindView(R.id.lottery_bonus)
    TextView mLotteryBonus;
    @BindView(R.id.bottom_2)
    LinearLayout mBottom2;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView mSwipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView mSwipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    @BindView(R.id.user_capital_total)
    TextView mUserCapitalTotal;

    private RecordPresenter mRecordPresenter;
    private CapitalRecordQwuickAdapter mAdapter;
    private int mPageNumber = ConstantValue.RECORD_LIST_PAGE_NUMBER;
    private int mPageSize = ConstantValue.RECORD_LIST_PAGE_SIZE;
    private long mMinTime = 0;
    private long mMaxTime = 0;
    private String mTransactionType = "";//空字符串为 全部类型
    private String[] mTypeArrays; //存放类型的中文名的数组
    private Map<String, String> mTypesMap = new ArrayMap<>(); //存放类型的map,中文-英文,传参要英文
    public static final String CAPITAL_RECORD_ID = "searchId";
    public static final String CAPITAL_RECORD_TYPE = "type";
    private CustomPopupWindow mFastChoosePopupWindow;
    private CustomPopupWindow mTypePopupWindow;
    private boolean mIsInit = true;
    private String mTimezone;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_capital_record);
        RxBus.get().register(this);
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader(getString(R.string.capital_record_acitivity), true);
        mStartTimeTv.setText(DateTool.getTimeFromLong(DateTool.FMT_DATE, System.currentTimeMillis()));
        mEndTimeTv.setText(DateTool.getTimeFromLong(DateTool.FMT_DATE, System.currentTimeMillis()));
        mAdapter = new CapitalRecordQwuickAdapter(R.layout.recyclerview_list_item_capital_activity_view);
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, null));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        // 设置 下拉刷新，加载更多
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setRefreshEnabled(true);
        mSwipeToLoadLayout.setLoadMoreEnabled(true);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        mTimezone = SharePreferenceUtil.getTimeZone(this);
        mRecordPresenter = new RecordPresenter(this, this);
        mRecordPresenter.getCapitalRecord("", "", mTransactionType, mPageNumber, mPageSize);
        mRecordPresenter.getCapitalRecordType();
    }


    @OnClick({R.id.start_time_fl, R.id.end_time_fl, R.id.fast_choose_btn, R.id.search_btn, R.id.type_choose})
    public void onViewClicked(View view) {
        SoundUtil.getInstance().playVoiceOnclick();
        switch (view.getId()) {
            case R.id.start_time_fl:
                mRecordPresenter.selectTime(0, mMinTime, mMaxTime);
                break;
            case R.id.end_time_fl:
                long minTime = mMinTime;
                minTime = DateTool.getLongFromTime("yyyy-MM-dd", mStartTimeTv.getText().toString());
                mRecordPresenter.selectTime(1, minTime, mMaxTime);
                break;
            case R.id.fast_choose_btn:
                //快选
                openFastChoosePopup();
                break;
            case R.id.type_choose:
                if (mTypePopupWindow == null) return;
                mTypePopupWindow.doTogglePopupWindow(mTypeChoose);
                break;
            case R.id.search_btn:
                doSearch();
                break;
        }
    }

    @Override
    public void onRecordResult(Object o) {
        if (mSwipeToLoadLayout.isRefreshing()) {
            mSwipeToLoadLayout.setRefreshing(false);
        }
        CapitalRecord capitalRecord = (CapitalRecord) o;
        setData(capitalRecord);
    }

    private void setData(CapitalRecord capitalRecord) {
        if (capitalRecord.getFundListApps() != null) {
            mAdapter.setNewData(capitalRecord.getFundListApps());
            if (capitalRecord.getFundListApps().size() < mPageSize) {
                mSwipeToLoadLayout.setLoadMoreEnabled(false);
            } else {
                mPageNumber++;
            }
        } else {
            mSwipeToLoadLayout.setLoadMoreEnabled(false);
        }
        if (!StringTool.isEmpty(capitalRecord.getWithdrawSum())) {
            mDrawcashValue.setText(capitalRecord.getCurrency() + " " + capitalRecord.getWithdrawSum());
        }
        if (!StringTool.isEmpty(capitalRecord.getTransferSum())) {
            mTransferValue.setText(capitalRecord.getCurrency() + " " + capitalRecord.getTransferSum());
        }
//        if (!StringTool.isEmpty(capitalRecord.getTotalCount())) {
//            mUserCapitalTotal.setText("￥ " + capitalRecord.getTotalCount());
//            mAccountBanlance.setText(getString(R.string.recharge_total, capitalRecord.getTotalCount()));
//        }
        if (capitalRecord.getSumPlayerMap() != null) {
            mAccountBanlance.setText(getString(R.string.recharge_total, capitalRecord.getSumPlayerMap().getRecharge()));
            mPayoutReward.setText(getString(R.string.withdraw_total, capitalRecord.getSumPlayerMap().getWithdraw()));
            mEffectiveBetting.setText(getString(R.string.favorable_total, capitalRecord.getSumPlayerMap().getFavorable()));
            mLotteryBonus.setText(getString(R.string.rakeback_total, capitalRecord.getSumPlayerMap().getRakeback()));
        }
        if (mIsInit) {
            mStartTimeTv.setText(DateTool.getTimeFromLong(DateTool.FMT_DATE, DateTool.convertTimeInMillisWithTimeZone(capitalRecord.getMinDate(), mTimezone)));
            mEndTimeTv.setText(DateTool.getTimeFromLong(DateTool.FMT_DATE, DateTool.convertTimeInMillisWithTimeZone(capitalRecord.getMaxDate(), mTimezone)));
        }
        mIsInit = false;
        mMinTime = DateTool.convertTimeInMillisWithTimeZone(capitalRecord.getMinDate(), mTimezone);
        mMaxTime = DateTool.convertTimeInMillisWithTimeZone(capitalRecord.getMaxDate(), mTimezone);
    }


    @Override
    public void loadMoreData(Object o) {
        if (mSwipeToLoadLayout.isLoadingMore()) {
            mSwipeToLoadLayout.setLoadingMore(false);
        }
        CapitalRecord capitalRecord = (CapitalRecord) o;
        if (capitalRecord.getFundListApps() == null) {
            mSwipeToLoadLayout.setLoadMoreEnabled(false);
            return;
        }

        if (mAdapter.getData().size() >= capitalRecord.getTotalCount()) {
            ToastUtil.showToastShort(this, getString(R.string.NO_MORE_DATA));
            //     mSwipeToLoadLayout.setLoadMoreEnabled(false);
            return;
        }
        mAdapter.addData(capitalRecord.getFundListApps());
        if (capitalRecord.getFundListApps().size() < mPageSize) {
            // mSwipeToLoadLayout.setLoadMoreEnabled(false);
        } else {
            mPageNumber++;
        }
    }

    @Override
    public void doSearch(Object o) {

    }

    public void doSearch() {
        if (mSwipeToLoadLayout.isRefreshing() || mSwipeToLoadLayout.isLoadingMore()) {
            return;
        }
        mPageNumber = ConstantValue.RECORD_LIST_PAGE_NUMBER;
        mRecordPresenter.getCapitalRecord(mStartTimeTv.getText().toString(), mEndTimeTv.getText().toString(),
                mTransactionType, mPageNumber, mPageSize);
    }

    @Override
    public void chooseStartTime(String time) {
        mStartTimeTv.setText(time);
        mEndTimeTv.setText(DateTool.getTimeFromLong(DateTool.FMT_DATE, mMaxTime));
    }

    @Override
    public void chooseEndTime(String time) {
        mEndTimeTv.setText(time);
    }

    @Override
    public void onRefresh() {
        mPageNumber = ConstantValue.RECORD_LIST_PAGE_NUMBER;
        mRecordPresenter.getCapitalRecordType();
        mRecordPresenter.getCapitalRecord(mStartTimeTv.getText().toString(), mEndTimeTv.getText().toString(),
                mTransactionType, mPageNumber, mPageSize);
    }

    @Override
    public void onLoadMore() {
        mRecordPresenter.getCapitalMoreRecord(mStartTimeTv.getText().toString(), mEndTimeTv.getText().toString(),
                mTransactionType, mPageNumber, mPageSize);
    }

    /**
     * 创建快速选中
     */
    private void openFastChoosePopup() {
        if (mFastChoosePopupWindow == null) {
            String[] array = getResources().getStringArray(R.array.fast_choose_list);
            List<String> list = Arrays.asList(array);
            mFastChoosePopupWindow = new CustomPopupWindow(CapitalRecordActivity.this, new FastPopupAdapter(R.layout.custom_popup_list_item_view, list));
        }
        mFastChoosePopupWindow.doTogglePopupWindow(mFastChooseBtn);
    }

    /**
     * 创建类型选择
     */
    private void initOpenTypeChoosePopup(String[] array) {
        List<String> list = Arrays.asList(array);
        mTypePopupWindow = new CustomPopupWindow(CapitalRecordActivity.this, new TypeChooseAdapter(R.layout.custom_popup_list_item_view, list));
    }


    /**
     * 处理获得的资金记录的所有类型
     *
     * @param o
     */
    @Override
    public void chooseTypeResult(Object o) {
        CapitalRecordType capitalRecordType = (CapitalRecordType) o;
        if (capitalRecordType == null) return;
        mTypeArrays = new String[]{
                getString(R.string.all_type),
                capitalRecordType.getBackwater(),
                capitalRecordType.getDeposit(),
                capitalRecordType.getFavorable(),
                capitalRecordType.getRecommend(),
                capitalRecordType.getTransfers(),
                capitalRecordType.getWithdrawals()};
        initOpenTypeChoosePopup(mTypeArrays);
        mTypesMap.clear();
        mTypesMap.put(getString(R.string.all_type), "");
        mTypesMap.put(capitalRecordType.getBackwater(), "backwater");
        mTypesMap.put(capitalRecordType.getDeposit(), "deposit");
        mTypesMap.put(capitalRecordType.getFavorable(), "favorable");
        mTypesMap.put(capitalRecordType.getRecommend(), "recommend");
        mTypesMap.put(capitalRecordType.getTransfers(), "transfers");
        mTypesMap.put(capitalRecordType.getWithdrawals(), "withdrawals");
    }

    class TypeChooseAdapter extends BaseQuickAdapter {

        public TypeChooseAdapter(int layoutResId, @Nullable List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            helper.setText(R.id.item_tv, String.valueOf(item));
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = helper.getAdapterPosition();
                    if (mTypeArrays != null && mTypesMap.size() > 0) {
                        for (int i = 0; i < mTypeArrays.length; i++) {
                            if (position == i) {
                                mTransactionType = mTypesMap.get(mTypeArrays[i]);
                                mTypeChoose.setText(mTypeArrays[i]);
                                mSwipeToLoadLayout.setLoadMoreEnabled(true);
                                mTypePopupWindow.dissMissPopWindow();
                            }
                        }
                    }
                }
            });
        }
    }


    class FastPopupAdapter extends BaseQuickAdapter {

        public FastPopupAdapter(int layoutResId, @Nullable List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            helper.setText(R.id.item_tv, String.valueOf(item));
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = helper.getAdapterPosition();
                    String[] arr = new String[2];
                    switch (position) {
                        case 0://今天
                            arr = DateTool.getDateOfFastChoose(DateTool.TODAY);
                            break;
                        case 1://昨天
                            arr = DateTool.getDateOfFastChoose(DateTool.YESTERDAY);
                            break;
//                        case 2://本周
//                            arr = DateTool.getDateOfFastChoose(DateTool.THISWEEK);
//                            break;
//                        case 3://上周
//                            arr = DateTool.getDateOfFastChoose(DateTool.LASTWEEK);
//                            break;
//                        case 4://本月
//                            arr = DateTool.getDateOfFastChoose(DateTool.THISMONTH);
//                            break;
                        case 2://最近7天
                            arr = DateTool.getDateOfFastChoose(DateTool.DAYS_7);
                            break;
//                        case 6://最近30天
//                            arr = DateTool.getDateOfFastChoose(DateTool.DAYS_30);
//                            break;
                    }
                    setTimeAndRearch(arr[0], arr[1]);
                    mFastChoosePopupWindow.dissMissPopWindow();
                }
            });
        }
    }

    private void setTimeAndRearch(String startTime, String endTime) {
        mStartTimeTv.setText(startTime);
        mEndTimeTv.setText(endTime);
        doSearch();
    }


    class CapitalRecordQwuickAdapter extends BaseQuickAdapter {

        public CapitalRecordQwuickAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {

            CapitalRecord.FundListBean fundListBean = (CapitalRecord.FundListBean) item;

//            Calendar calendar =Calendar.getInstance();
//            TimeZone timeZone = TimeZone.getTimeZone(mTimezone);
//            calendar.setTimeZone(timeZone);
//            helper.setText(R.id.tv_record_time, calendar.get(Calendar.YEAR)+"-"
//                    +(calendar.get(Calendar.MONTH)+1)+"-"
//                    +calendar.get(Calendar.DAY_OF_MONTH));
            helper.setText(R.id.tv_record_time, DateTool.getTimeFromLong(DateTool.FMT_DATE, DateTool.changeTimeZone(fundListBean.getCreateTime(), mTimezone, DateTool.getWuYong())));
            Log.e("TAG", "aaaaaaaaaaaaaaa" + DateTool.getTimeFromLong(DateTool.FMT_DATE_TIME, fundListBean.getCreateTime()));
            helper.setText(R.id.tv_record_value, fundListBean.getTransactionMoney());
            helper.setText(R.id.tv_record_type, fundListBean.getTransaction_typeName());
            helper.setText(R.id.tv_record_status, fundListBean.getStatusName());
            setStatusColor(helper.getView(R.id.tv_record_status), fundListBean.getStatus());
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SoundUtil.getInstance().playVoiceOnclick();
                    Intent intent = new Intent(CapitalRecordActivity.this, CapitalDetailRecordActivity.class);
                    intent.putExtra(CAPITAL_RECORD_TYPE, fundListBean.getTransactionType());
                    intent.putExtra(CAPITAL_RECORD_ID, fundListBean.getId());
                    startActivity(intent);
                }
            });
        }
    }

    private void setStatusColor(TextView textView, String status) {
        switch (status) {
            case "failure":
                textView.setTextColor(getResources().getColor(R.color.failure));
                break;
            case "pending_pay":
                textView.setTextColor(getResources().getColor(R.color.btn_yellow_normal));
                break;
            case "success":
                textView.setTextColor(getResources().getColor(R.color.sucess));
                break;
            case "process":
                textView.setTextColor(getResources().getColor(R.color.process));
                break;
            case "reject":
                textView.setTextColor(getResources().getColor(R.color.failure));
                break;
            case "pending":
                textView.setTextColor(getResources().getColor(R.color.process));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mRecordPresenter.onDestory();
        RxBus.get().unregister(this);
        super.onDestroy();
    }

    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_NETWORK_EXCEPTION)})
    public void shrinkRefreshView(String s) {
        LogUtils.d(s);
        //  收起刷新
        if (null != mSwipeToLoadLayout && mSwipeToLoadLayout.isRefreshing()) {
            mSwipeToLoadLayout.setRefreshing(false);
        }
    }
}
