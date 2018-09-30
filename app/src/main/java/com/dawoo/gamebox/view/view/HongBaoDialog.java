package com.dawoo.gamebox.view.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.FAB;
import com.dawoo.gamebox.bean.GetPacket;
import com.dawoo.gamebox.bean.HongbaoCount;
import com.dawoo.gamebox.bean.HongbaoTemp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by benson on 18-1-1.
 */

public class HongBaoDialog {

    private static final int START = 0;
    private static final int SUCCESS = 1;
    private static final int FAILE = 2;
    private static final int REGULAR = 3;
    private static int STATUS = START; // 记录当前状态
    private static int STATUS_BEFORE = START; // 记录前一个状态

    private final HongbaoCount mHongbaoCount;
    private final View.OnClickListener mListener;
    private final Context mContext;
    private final FAB.ActivityBean mActivityBean;
    @BindView(R.id.open_tip_tv)
    TextView mOpenTipTv;
    @BindView(R.id.open_hongbao_iv)
    ImageView mOpenHongbaoIv;
    @BindView(R.id.ramian_count_lable_tv)
    TextView mRamianCountLableTv;
    @BindView(R.id.ramian_count_value_tv)
    TextView mRamianCountValueTv;
    @BindView(R.id.remain_count_ll)
    LinearLayout mRemainCountLl;
    @BindView(R.id.time_lable_tv)
    TextView mTimeLableTv;
    @BindView(R.id.time_value_tv)
    TextView mTimeValueTv;
    @BindView(R.id.open_ll)
    LinearLayout mOpenLl;
    @BindView(R.id.success_tip_tv)
    TextView mSuccessTipTv;
    @BindView(R.id.success_hongbao_iv)
    ImageView mSuccessHongbaoIv;
    @BindView(R.id.success_sure_iv)
    ImageView mSuccessSureIv;
    @BindView(R.id.success_ll)
    RelativeLayout mSuccessLl;
    @BindView(R.id.faile_tip_tv)
    TextView mFaileTipTv;
    @BindView(R.id.faile_chai_iv)
    ImageView mFaileChaiIv;
    @BindView(R.id.faile_again_iv)
    ImageView mFaileAgainIv;
    @BindView(R.id.faile_ll)
    RelativeLayout mFaileLl;
    @BindView(R.id.open_close_iv)
    ImageView mOpenCloseIv;
    private Dialog mDialog;


    public HongBaoDialog(@NonNull Context context, HongbaoCount hongbaoCount, FAB.ActivityBean activityBean, View.OnClickListener listener) {
        mContext = context;
        mHongbaoCount = hongbaoCount;
        mActivityBean = activityBean;
        mListener = listener;

        mDialog = new Dialog(context, R.style.CustomDialogStyle);
        mDialog.setContentView(R.layout.hongbao_dialog);
        mDialog.setCancelable(false);
        ButterKnife.bind(this, mDialog);
        setLayoutParams();
        initData();


        mDialog.show();
    }

    private void setLayoutParams() {
        Window win = mDialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    public void showDialog() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


    /**
     * 初始化红包
     */
    void initData() {
        STATUS = START;
        STATUS_BEFORE = START;

        if (mHongbaoCount == null) {
            return;
        }
        // 初始化红包数据
        mTimeLableTv.setText(mContext.getString(R.string.next_time));
        mTimeValueTv.setText(mHongbaoCount.getNextLotteryTime());


        if (mHongbaoCount == null) {
            return;
        }

        if ("true".equals(mHongbaoCount.getIsEnd())) {
            setInitNoData(mContext.getResources().getString(R.string.hongbao_end));
//                if (TextUtils.isEmpty(mHongbaoCount.getNextLotteryTime())) {
//                    mNextTv.setVisibility(View.GONE);
//                    mTimeTv.setVisibility(View.GONE);
//                    mCountTv.setVisibility(View.VISIBLE);
//                }
        } else if (-1 == mHongbaoCount.getDrawTimes()) {
            setInitNoData(mContext.getResources().getString(R.string.hongbao_end));

        } else if (-5 == mHongbaoCount.getDrawTimes()) {
            setInitNoData(mContext.getResources().getString(R.string.haobao_qiangguang));

        } else if (0 == mHongbaoCount.getDrawTimes()) {
            setInitNoData(mContext.getResources().getString(R.string.haobao_times_0));

        } else if (mListener != null && mHongbaoCount != null && mActivityBean != null) {
            mOpenTipTv.setVisibility(View.INVISIBLE);
            mTimeLableTv.setVisibility(View.GONE);
            mTimeValueTv.setVisibility(View.GONE);
            mOpenHongbaoIv.setImageResource(R.mipmap.hongbao_chai_click);

            mRamianCountLableTv.setText(mContext.getResources().getString(R.string.haobao_remian_count));
            mRamianCountValueTv.setText("" + mHongbaoCount.getDrawTimes());

            if (0 == mHongbaoCount.getDrawTimes()) {

                mOpenHongbaoIv.setEnabled(false);
                mOpenHongbaoIv.setImageResource(R.mipmap.hongbao_chai_unclick);

                if (TextUtils.isEmpty(mHongbaoCount.getNextLotteryTime())) {
                    mTimeLableTv.setVisibility(View.GONE);
                    mTimeValueTv.setVisibility(View.GONE);
                }
            }

            HongbaoTemp temp = new HongbaoTemp(mActivityBean.getActivityId(), mHongbaoCount.getToken());
            mOpenHongbaoIv.setTag(R.id.list_item_data_id, temp);

            mOpenHongbaoIv.setOnClickListener(mListener);
        }
    }


    void setInitNoData(String title) {
        mOpenTipTv.setText(title);
        mOpenHongbaoIv.setEnabled(false);
        mOpenHongbaoIv.setImageResource(R.mipmap.hongbao_chai_unclick);
        mRamianCountLableTv.setText(mContext.getResources().getString(R.string.haobao_chance));
        mRamianCountValueTv.setText("0");

        mOpenTipTv.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(mHongbaoCount.getNextLotteryTime())) {
            mTimeLableTv.setVisibility(View.GONE);
            mTimeValueTv.setVisibility(View.GONE);
        } else {
            mTimeLableTv.setVisibility(View.VISIBLE);
            mTimeValueTv.setVisibility(View.VISIBLE);

        }
    }

    public void setGetPacketResult(GetPacket getPacket) {
        if (getPacket == null) {
            return;
        }

        // -5：已抢完 -4：条件不满足 -3:红包活动结束 -2：抽奖异常 -1：已抽完 0：已结束
        int gameNum = getPacket.getGameNum();
        mHongbaoCount.setToken(getPacket.getToken());
        HongbaoTemp temp = new HongbaoTemp(mActivityBean.getActivityId(), mHongbaoCount.getToken());
        // mOpenHongbaoIv.setTag(R.id.list_item_data_id, temp);
        switch (gameNum) {
            case -5:
                openHongbao(START);
                setInitNoData(mContext.getResources().getString(R.string.hongbao_snatched_out));
                break;
            case -4:
                openHongbao(START);
                setInitNoData(mContext.getResources().getString(R.string.hongbao_condition_not_satisfied));
                break;
            case -3:
                openHongbao(START);
                setInitNoData(mContext.getResources().getString(R.string.hongbao_activity_end));
                break;
            case -2:
                openHongbao(START);
                setInitNoData(mContext.getResources().getString(R.string.hongbao_excepttion));
                break;
            case -1:
                openHongbao(START);
                setInitNoData(mContext.getResources().getString(R.string.hongbao_chou_wan));
                break;
//            case 0:
//                openHongbao(START);
//                setInitNoData(mContext.getResources().getString(R.string.hongbao_have_end));
//                break;
            default:

                if( mActivityBean == null) {
                    return;
                }
                mHongbaoCount.setToken(getPacket.getToken());
                HongbaoTemp temp1 = new HongbaoTemp(mActivityBean.getActivityId(), mHongbaoCount.getToken());
                mOpenHongbaoIv.setTag(R.id.list_item_data_id, temp1);
                mRamianCountLableTv.setText(mContext.getResources().getString(R.string.haobao_remian_count));
                mRamianCountValueTv.setText("" + getPacket.getGameNum());

                if (!"0".equals(getPacket.getAward()) && !"0.0".equals(getPacket.getAward())) {
                    openHongbao(SUCCESS);
                    mSuccessTipTv.setText(mContext.getResources().getString(R.string.hongbao_get_partten, getPacket.getAward()));
                    if (0 == getPacket.getGameNum()) {
                        mOpenHongbaoIv.setEnabled(false);
                        mOpenHongbaoIv.setImageResource(R.mipmap.hongbao_chai_unclick);

                        mRamianCountLableTv.setText(mContext.getResources().getString(R.string.haobao_chance));
                        mRamianCountValueTv.setText("0");
                    }
                } else {
                    openHongbao(FAILE);
                    mFaileTipTv.setText(mContext.getResources().getString(R.string.hongbao_faile));
                    if (0 == getPacket.getGameNum()) {
                        mOpenHongbaoIv.setEnabled(false);
                        mOpenHongbaoIv.setImageResource(R.mipmap.hongbao_chai_unclick);

                        mRamianCountLableTv.setText(mContext.getResources().getString(R.string.haobao_chance));
                        mRamianCountValueTv.setText("0");
                    }

                }
        }
    }


    public void openHongbao(int status) {
        STATUS = status;
        switchHBStatus(status);
    }


    /**
     * 切换红包状态
     */
    void switchHBStatus(int i) {
        switch (i) {
            case START:
                mOpenLl.setVisibility(View.VISIBLE);
                mSuccessLl.setVisibility(View.GONE);
                mFaileLl.setVisibility(View.GONE);
                break;
            case SUCCESS:
                mOpenLl.setVisibility(View.GONE);
                mSuccessLl.setVisibility(View.VISIBLE);
                mFaileLl.setVisibility(View.GONE);
                break;
            case FAILE:
                mOpenLl.setVisibility(View.GONE);
                mSuccessLl.setVisibility(View.GONE);
                mFaileLl.setVisibility(View.VISIBLE);
                break;
            case REGULAR:
                mOpenLl.setVisibility(View.GONE);
                mSuccessLl.setVisibility(View.GONE);
                mFaileLl.setVisibility(View.GONE);
                break;
        }
    }


    @OnClick({R.id.open_close_iv, R.id.success_sure_iv, R.id.faile_again_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.success_sure_iv:
                openHongbao(START);
                break;
            case R.id.faile_again_iv:
                openHongbao(START);
                break;
            case R.id.open_close_iv:
                dismissDialog();
                break;
        }
    }
}
