package com.dawoo.gamebox.view.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.dawoo.coretool.util.CleanLeakUtils;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.date.DateTool;
import com.dawoo.coretool.util.math.BigDemicalUtil;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.NoteRecord;
import com.dawoo.gamebox.mvp.presenter.RecordPresenter;
import com.dawoo.gamebox.mvp.view.INoteRecordView;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.view.HeaderView;
import com.dawoo.gamebox.view.view.swipetoloadlayout.LoadMoreFooterView;
import com.dawoo.gamebox.view.view.swipetoloadlayout.RefreshHeaderView;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 投注记录
 */
public class NoteRecordActivity extends BaseActivity implements INoteRecordView, OnLoadMoreListener {


    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.start_time)
    FrameLayout mStartTime;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.end_time_fl)
    FrameLayout mEndTimeFl;
    @BindView(R.id.search_btns)
    Button mSearchBtns;
    @BindView(R.id.count)
    TextView mCount;
    @BindView(R.id.note_account_tv)
    TextView mNoteAccountTv;
    @BindView(R.id.payout_tv)
    TextView mPayoutTv;
    @BindView(R.id.total_account)
    TextView mTotalAccount;
    @BindView(R.id.account_banlance)
    TextView mAccountBanlance;
    @BindView(R.id.payout_reward)
    TextView mPayoutReward;
    @BindView(R.id.effective_betting)
    TextView mEffectiveBetting;
    @BindView(R.id.lottery_bonus)
    TextView mLotteryBonus;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView mSwipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView mSwipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    @BindView(R.id.bg_no_data)
    LinearLayout mNoData;

    private RecordPresenter mRecordPresenter;
    private long minTime = 0;
    private long maxTime = 0;
    private int mPageNumber = ConstantValue.RECORD_LIST_PAGE_NUMBER;
    private List<NoteRecord.ListBean> mListBeans = new ArrayList<>();  //加载出的所有数据
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private NoteRecordAdapter mNoteRecordAdapter;
    private boolean isFirst = true;
    public static String RECORD_WALLET_BALANCE = "RECORD_WALLET_BALANCE";
    private String mTimeZone;

    @Override
    protected void createLayoutView() {
        RxBus.get().register(this);
        setContentView(R.layout.activity_note_record);
    }

    @Override
    protected void initViews() {
        String mWalletBalance = getIntent().getStringExtra(RECORD_WALLET_BALANCE);
        // mAccountBanlance.setText(getString(R.string.account_banlance, mWalletBalance));
        mHeadView.setHeader(getString(R.string.note_record_acitivity), true);
        mSwipeToLoadLayout.setRefreshEnabled(false);
        mSwipeToLoadLayout.setLoadMoreEnabled(true);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        mTimeZone = SharePreferenceUtil.getTimeZone(this);
        mRecordPresenter = new RecordPresenter(this, this);
        long currentTime = DateTool.convertTimeInMillisWithTimeZone(System.currentTimeMillis(), mTimeZone);
        String currentDate = DateTool.convert2String(new Date(currentTime), DateTool.FMT_DATE);
        mTvStartTime.setText(currentDate);
        mTvEndTime.setText(currentDate);
        mRecordPresenter.getNoteRecord(mTvStartTime.getText().toString(), mTvEndTime.getText().toString(), ConstantValue.RECORD_LIST_PAGE_SIZE, mPageNumber, true);
        mPageNumber = 2;
        mNoteRecordAdapter = new NoteRecordAdapter(mListBeans);
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // mSwipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mSwipeTarget.setItemAnimator(new DefaultItemAnimator());
        mSwipeTarget.setAdapter(mNoteRecordAdapter);

    }


    @OnClick({R.id.start_time, R.id.end_time_fl, R.id.search_btns})
    public void onViewClicked(View view) {
        SoundUtil.getInstance().playVoiceOnclick();
        switch (view.getId()) {
            case R.id.start_time:
                try {
                    Date date = formatter.parse(mTvEndTime.getText().toString());
                    mRecordPresenter.selectTime(0, minTime, date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                    mRecordPresenter.selectTime(0, 0, new Date().getTime());
                }
                break;
            case R.id.end_time_fl:
                try {
                    Date date = formatter.parse(mTvStartTime.getText().toString());
                    mRecordPresenter.selectTime(1, date.getTime(), maxTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                    mRecordPresenter.selectTime(1, 0, new Date().getTime());
                }

                break;
            case R.id.search_btns:
                mRecordPresenter.doSearch(mTvStartTime.getText().toString(), mTvEndTime.getText().toString());
                break;
            default:
        }
    }

    @Override
    public void onRecordResult(Object o) {
        NoteRecord noteRecord = (NoteRecord) o;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

//        if (isFirst) {
//            mTvStartTime.setText(DateTool.getTimeFromLong(DateTool.FMT_DATE, DateTool.convertTimeInMillisWithTimeZone(noteRecord.getMinDate(), mTimeZone)));
//            mTvEndTime.setText(DateTool.getTimeFromLong(DateTool.FMT_DATE, DateTool.convertTimeInMillisWithTimeZone(noteRecord.getMaxDate(), mTimeZone)));
//        }
        minTime = DateTool.convertTimeInMillisWithTimeZone(noteRecord.getMinDate(), mTimeZone);
        maxTime = DateTool.convertTimeInMillisWithTimeZone(noteRecord.getMaxDate(), mTimeZone);

        NoteRecord.StatisticsDataBean statisticsDataBean = noteRecord.getStatisticsData();
        if (statisticsDataBean != null) {
            //        mNoteAccountTv.setText(noteRecord.getStatisticsData().getSingle());
            //        mPayoutTv.setText(noteRecord.getStatisticsData().getProfit());
            // 投资总额
            mAccountBanlance.setText(getString(R.string.account_banlance, BigDemicalUtil.moneyFormat(statisticsDataBean.getSingle())));
            // 投注笔数
            mLotteryBonus.setText(getString(R.string.lottery_bonus, String.valueOf(noteRecord.getTotalSize())));
            // 派彩金额
            mPayoutReward.setText(getString(R.string.payout_reward, BigDemicalUtil.moneyFormat(noteRecord.getStatisticsData().getProfit())));
            // 有效投注
            mEffectiveBetting.setText(getString(R.string.effective_betting, BigDemicalUtil.moneyFormat(noteRecord.getStatisticsData().getEffective())));
        }

        if (mSwipeToLoadLayout.isLoadingMore())
            mSwipeToLoadLayout.setLoadingMore(false);
        if (noteRecord == null || noteRecord.getList() == null || noteRecord.getList().size() == 0) {
            if (mListBeans.size() == 0) {
                mSwipeToLoadLayout.setVisibility(View.GONE);
                mNoData.setVisibility(View.VISIBLE);
            }
        } else {
            mSwipeToLoadLayout.setVisibility(View.VISIBLE);
            mNoData.setVisibility(View.GONE);
            if (noteRecord.getTotalSize() > mListBeans.size()) {
                mListBeans.addAll(noteRecord.getList());
                mNoteRecordAdapter.notifyDataSetChanged();
            } else mSwipeToLoadLayout.setLoadMoreEnabled(false);
        }
        isFirst = false;
    }

    @Override
    public void loadMoreData(Object o) {
        mPageNumber += 1;
        onRecordResult(o);
    }


    @Override
    public void doSearch(Object o) {
        mSwipeToLoadLayout.setLoadMoreEnabled(true);
        mPageNumber = 2;
        mListBeans.clear();
        onRecordResult(o);
    }

    @Override
    public void chooseStartTime(String time) {
        mTvStartTime.setText(time);
    }

    @Override
    public void chooseEndTime(String time) {
        mTvEndTime.setText(time);
    }

    @Override
    public void onLoadMore() {
        mRecordPresenter.loadMoreData(mTvStartTime.getText().toString(), mTvEndTime.getText().toString(), mPageNumber);

    }


    class NoteRecordAdapter extends RecyclerView.Adapter<NoteRecordAdapter.NoteRecordViewHolder> {

        private List<NoteRecord.ListBean> listBean;

        public NoteRecordAdapter(List<NoteRecord.ListBean> listBean) {
            this.listBean = listBean;
        }

        @Override
        public NoteRecordAdapter.NoteRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            NoteRecordViewHolder noteRecordViewHolder = new NoteRecordViewHolder(LayoutInflater.from(NoteRecordActivity.this).inflate(R.layout.recyclerview_item_note_recor, parent, false));
            return noteRecordViewHolder;
        }

        @Override
        public void onBindViewHolder(NoteRecordAdapter.NoteRecordViewHolder holder, int position) {

            NoteRecord.ListBean lBean = listBean.get(position);
            long time = DateTool.convertTimeInMillisWithTimeZone(lBean.getBetTime(), SharePreferenceUtil.getTimeZone(NoteRecordActivity.this));
            holder.mGameName.setText(lBean.getApiName()+"\n"+lBean.getGameName());
            holder.mBetTime.setText(DateTool.getTimeFromLong(DateTool.FMT_DATE_TIME, time));

            holder.mBetAmount.setText(BigDemicalUtil.moneyFormat(lBean.getSingleAmount()));

            if (!TextUtils.isEmpty(lBean.getProfitAmount())) {
                holder.mPayout.setText(lBean.getProfitAmount());
            } else {
                holder.mPayout.setText("- -");
            }

            if ("pending_settle".equals(lBean.getOrderState())) {
                holder.mSettlement.setText("未结算");
            } else if ("settle".equals(lBean.getOrderState())) {
                holder.mSettlement.setText("已结算");
            } else if ("cancel".equals(lBean.getOrderState())) {
                holder.mSettlement.setText("取消订单");
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SoundUtil.getInstance().playVoiceOnclick();
//                    String url = DataCenter.getInstance().getIp() + lBean.getUrl();
//                    ActivityUtil.startWebView(url, "", ConstantValue.WEBVIEW_TYPE_ORDINARY,1,"");
                    ActivityUtil.startLtDomainWebview(lBean.getUrl(), NoteRecordActivity.this,ConstantValue.WEBVIEW_TYPE_ORDINARY,"");
                  /*  startActivity(new Intent(NoteRecordActivity.this,SettingDetailActivity.class)
                    .putExtra(SettingDetailActivity.RECORD_DETAIL_ID,lBean.getId()));*/
                }
            });
        }

        @Override
        public int getItemCount() {
            return listBean.size();
        }

        class NoteRecordViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.tv_game_name)
            TextView mGameName;
            @BindView(R.id.tv_bet_time)
            TextView mBetTime;
            @BindView(R.id.tv_bet_Amount)
            TextView mBetAmount;
            @BindView(R.id.tv_Payout)
            TextView mPayout;
            @BindView(R.id.tv_Settlement)
            TextView mSettlement;


            public NoteRecordViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_LOGOUT)})
    public void logout(String s) {
        LogUtils.d(s);
        finish();
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
        mRecordPresenter.onDestory();
        CleanLeakUtils.fixInputMethodManagerLeak(this);
        super.onDestroy();
    }
}
