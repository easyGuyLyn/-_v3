package com.dawoo.gamebox.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.dawoo.coretool.util.date.DateTool;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.BettingDetail;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.mvp.presenter.RecordPresenter;
import com.dawoo.gamebox.mvp.view.IBettingDetailView;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.view.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by b on 18-1-12.
 * 投资详情
 */

public class SettingDetailActivity extends BaseActivity implements IBettingDetailView {
    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_betId)
    TextView mTvBetId;
    @BindView(R.id.tv_game_business)
    TextView mTvGameBusiness;
    @BindView(R.id.tv_game_type)
    TextView mTvGameType;
    @BindView(R.id.tv_bet_time)
    TextView mTvBetTime;
    @BindView(R.id.tv_trade_amount)
    TextView mTvTradeAmount;
    @BindView(R.id.tv_effective_trade_amount)
    TextView mTvEffectiveTradeAmount;
    @BindView(R.id.tv_payout_time)
    TextView mTvPayoutTime;
    @BindView(R.id.tv_payout)
    TextView mTvPayout;
    @BindView(R.id.tv_game_type_api)
    TextView mTvGameTypeApi;
    @BindView(R.id.tv_game_number_api)
    TextView mTvGameNumberApi;
    @BindView(R.id.tv_bet_type_api)
    TextView mTvBetTypeApi;
    @BindView(R.id.tv_player_bet_api)
    TextView mTvPlayerBetApi;
    @BindView(R.id.tv_let_ball_api)
    TextView mTvLetBallApi;
    @BindView(R.id.tv_Handicap_api)
    TextView mTvHandicapApi;
    @BindView(R.id.tv_odds_api)
    TextView mTvOddsApi;
    @BindView(R.id.tv_score_api)
    TextView mTvScoreApi;
    @BindView(R.id.tv_contribution_amount)
    TextView mTvContributionAmount;

    public static final String RECORD_DETAIL_ID = "recordDetailID";


    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_setting_detail);
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader(getString(R.string.note_record_detail_activity), true);
    }

    @Override
    protected void initData() {
        String id = getIntent().getStringExtra(RECORD_DETAIL_ID);
        RecordPresenter mRecordPresenter = new RecordPresenter(this, this);
        mRecordPresenter.getBettingDetail(id);
    }

    @Override
    public void onBettingDetailResult(Object o) {
        BettingDetail mBettingDetail = (BettingDetail) o;
        setView(mBettingDetail);
    }

    private void setView(BettingDetail mBettingDetail) {
        //注单号
        if (!TextUtils.isEmpty(mBettingDetail.getBetId())) {
            mTvBetId.setText(mBettingDetail.getBetId());
        }

        //游戏供应商
        if (!TextUtils.isEmpty(mBettingDetail.getApiId())) {
            mTvGameBusiness.setText(mBettingDetail.getApiId());
        }

        //游戏种类
        if (!TextUtils.isEmpty(mBettingDetail.getApiTypeId())) {
            mTvGameType.setText(mBettingDetail.getApiTypeId());
        }

        //游戏时间
        if (!TextUtils.isEmpty(mBettingDetail.getBetTime()+"")) {
            long time = DateTool.convertTimeInMillisWithTimeZone(mBettingDetail.getBetTime(), SharePreferenceUtil.getTimeZone(SettingDetailActivity.this));
            mTvBetTime.setText(DateTool.getTimeFromLong(DateTool.FMT_DATE_TIME, time));
        }
        //投注额
        if (!TextUtils.isEmpty(mBettingDetail.getSingleAmount())) {
            mTvTradeAmount.setText(mBettingDetail.getSingleAmount());
        }
        //有效投注额
        if (!TextUtils.isEmpty(mBettingDetail.getEffectiveTradeAmount())) {
            mTvEffectiveTradeAmount.setText(mBettingDetail.getEffectiveTradeAmount());
        }
        //派彩时间
        if (!TextUtils.isEmpty(mBettingDetail.getPayoutTime())) {
            mTvPayoutTime.setText(mBettingDetail.getPayoutTime());
        }
        //派彩
        if (!TextUtils.isEmpty(mBettingDetail.getProfitAmount())) {
            mTvPayout.setText(mBettingDetail.getProfitAmount());
        }

        ////彩池贡献金
        if (!TextUtils.isEmpty(mBettingDetail.getContributionAmount())) {
            mTvContributionAmount.setText(mBettingDetail.getContributionAmount());
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

