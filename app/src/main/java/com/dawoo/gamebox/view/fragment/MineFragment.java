package com.dawoo.gamebox.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.math.BigDemicalUtil;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.Logout;
import com.dawoo.gamebox.bean.MineLink;
import com.dawoo.gamebox.bean.SiteMsgUnReadCount;
import com.dawoo.gamebox.bean.UserAccount;
import com.dawoo.gamebox.mvp.presenter.CommonPresenter;
import com.dawoo.gamebox.mvp.presenter.MessagePresenter;
import com.dawoo.gamebox.mvp.presenter.UserPresenter;
import com.dawoo.gamebox.mvp.view.ILoginOutView;
import com.dawoo.gamebox.mvp.view.IMineFragmentView;
import com.dawoo.gamebox.mvp.view.ISiteMsgView;
import com.dawoo.gamebox.push.PushDispatchManager;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.activity.CapitalRecordActivity;
import com.dawoo.gamebox.view.activity.MainActivity;
import com.dawoo.gamebox.view.activity.NoteRecordActivity;
import com.dawoo.gamebox.view.activity.PromoRecordActivity;
import com.dawoo.gamebox.view.activity.QuotaConversionActivity;
import com.dawoo.gamebox.view.activity.SecurityCenterActivity;
import com.dawoo.gamebox.view.activity.SettingActivity;
import com.dawoo.gamebox.view.activity.ShareFriendTipActivity;
import com.dawoo.gamebox.view.activity.WithdrawMoneyActivity;
import com.dawoo.gamebox.view.activity.message.MessageCenterActivity;
import com.dawoo.gamebox.view.activity.more.MoreActivity;
import com.guoqi.iosdialog.IOSDialog;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends BaseFragment implements IMineFragmentView, ILoginOutView, ISiteMsgView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.service_iv)
    ImageView mServiceIv;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.setting_iv)
    ImageView mSettingIv;
    @BindView(R.id.logout_tv)
    TextView mLogoutTv;


    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.time_lable)
    TextView mTimeLable;
    @BindView(R.id.time_value)
    TextView mTimeValue;
    @BindView(R.id.asset_ll)
    LinearLayout mAssetLL;
    @BindView(R.id.total_assets_lable)
    TextView mTotalAssetsLable;
    @BindView(R.id.total_assets_value)
    TextView mTotalAssetsValue;
    @BindView(R.id.purse_balance_lable)
    TextView mPurseBalanceLable;
    @BindView(R.id.purse_balance_value)
    TextView mPurseBalanceValue;
    @BindView(R.id.unlogined_lable)
    TextView mUnLoginedLable;


    @BindView(R.id.recharge_ll)
    LinearLayout mRechargeLl;
    @BindView(R.id.withdraw_label_tv)
    TextView mWithdrawLabelTv;
    @BindView(R.id.withdraw_progress_tv)
    TextView mWithdrawProgressTv;
    @BindView(R.id.drawcash_rl)
    RelativeLayout mDrawcashRl;
    @BindView(R.id.point_view)
    View mPointView;
    @BindView(R.id.note_record_iv)
    ImageView mNoteRecordIv;
    @BindView(R.id.note_record_lable)
    TextView mNoteRecordLable;
    @BindView(R.id.note_record_tip)
    TextView mNoteRecordTip;
    @BindView(R.id.note_record_rl)
    RelativeLayout mNoteRecordRl;
    @BindView(R.id.quota_conversion_point_view)
    View mQuotaConversionPointView;
    @BindView(R.id.quota_conversion_iv)
    ImageView mQuotaConversionIv;
    @BindView(R.id.quota_conversion_lable)
    TextView mQuotaConversionLable;
    @BindView(R.id.quota_conversion_tip)
    TextView mQuotaConversionTip;
    @BindView(R.id.quota_conversion_rl)
    RelativeLayout mQuotaConversionRl;
    @BindView(R.id.captal_record_point_view)
    View mCaptalRecordPointView;
    @BindView(R.id.captal_record_iv)
    ImageView mCaptalRecordIv;
    @BindView(R.id.captal_record_lable)
    TextView mCaptalRecordLable;
    @BindView(R.id.captal_record_tip)
    TextView mCaptalRecordTip;
    @BindView(R.id.captal_record_rl)
    RelativeLayout mCaptalRecordRl;
    @BindView(R.id.security_center_point_view)
    View mSecurityCenterPointView;
    @BindView(R.id.security_center_iv)
    ImageView mSecurityCenterIv;
    @BindView(R.id.security_center_lable)
    TextView mSecurityCenterLable;
    @BindView(R.id.security_center_tip)
    TextView mSecurityCenterTip;
    @BindView(R.id.security_center_level)
    TextView mSecurityCenterLevel;
    @BindView(R.id.security_center_rl)
    RelativeLayout mSecurityCenterRl;
    @BindView(R.id.concession_record_point_view)
    View mConcessionRecordPointView;
    @BindView(R.id.concession_record_iv)
    ImageView mConcessionRecordIv;
    @BindView(R.id.concession_record_lable)
    TextView mConcessionRecordLable;
    @BindView(R.id.concession_record_tip)
    TextView mConcessionRecordTip;
    @BindView(R.id.concession_record_rl)
    RelativeLayout mConcessionRecordRl;
    @BindView(R.id.message_center_point_view)
    View mMessageCenterPointView;
    @BindView(R.id.message_center_iv)
    ImageView mMessageCenterIv;
    @BindView(R.id.message_center_lable)
    TextView mMessageCenterLable;
    @BindView(R.id.qmessage_center_tip)
    TextView mQmessageCenterTip;
    @BindView(R.id.message_center_rl)
    RelativeLayout mMessageCenterRl;
    @BindView(R.id.share_friend_point_view)
    View mShareFriendPointView;
    @BindView(R.id.share_friend_iv)
    ImageView mShareFriendIv;
    @BindView(R.id.share_friend_lable)
    TextView mShareFriendLable;
    @BindView(R.id.share_friend_tip)
    TextView mShareFriendTip;
    @BindView(R.id.share_friend_rl)
    RelativeLayout mShareFriendRl;
    @BindView(R.id.aplication_for_preferential_point_view)
    View mAplicationForPreferentialPointView;
    @BindView(R.id.aplication_for_preferential_iv)
    ImageView mAplicationForPreferentialIv;
    @BindView(R.id.aplication_for_preferential_lable)
    TextView mAplicationForPreferentialLable;
    @BindView(R.id.aplication_for_preferential_tip)
    TextView mAplicationForPreferentialTip;
    @BindView(R.id.qaplication_for_preferential_rl)
    RelativeLayout mQaplicationForPreferentialRl;
    @BindView(R.id.see_more_point_view)
    View mSeeMorePointView;
    @BindView(R.id.see_more_iv)
    ImageView mSeeMoreIv;
    @BindView(R.id.see_more_lable)
    TextView mSeeMoreLable;
    @BindView(R.id.see_more_tip)
    TextView mSeeMoreTip;
    @BindView(R.id.see_more_rl)
    RelativeLayout mSeeMoreRl;
    @BindView(R.id.v_tip_my_notice)
    TextView mVTipMyNotice;

    Unbinder unbinder;
    private CommonPresenter mPresenter;
    private String mNameStr = "***";
    private UserPresenter mUserPresenter;
    private String mWalletBalance = "";
    private MessagePresenter mPresneter;


    public MineFragment() {
    }

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestory();
        mUserPresenter.onDestory();
        RxBus.get().unregister(this);
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        RxBus.get().register(this);
        initViews(view);
        initData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {            //参数是固定写法
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private void initViews(View view) {
        mTitleName.setText(getString(R.string.title_mine));
        mRefreshLayout.setOnRefreshListener(this);
        int[] colors = new int[]{ContextCompat.getColor(mContext, R.color.color_theme_blue),
                ContextCompat.getColor(mContext, R.color.color_theme_red),
                ContextCompat.getColor(mContext, R.color.color_theme_green_black),
                ContextCompat.getColor(mContext, R.color.color_theme_black)};
        mRefreshLayout.setColorSchemeColors(colors);
    }

    private void initData() {
        mPresenter = new CommonPresenter(mContext, this);
        mUserPresenter = new UserPresenter(mContext, this);
        mPresneter = new MessagePresenter(mContext, this);
        getSiteUnReadMsgCount("");
    }

    @Subscribe(tags = {@Tag(PushDispatchManager.EVENT_PUSH_MSG_CENTER)})
    public void getSiteUnReadMsgCount(String s) {
        mPresneter.getUnReadMsgCount();
    }

    @Override
    protected void loadData() {

    }


    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_LOGINED)})
    public void loginedMineCallBack(String s) {
        //  加载账户数据
        //mPresenter.getLink();
    }

    @OnClick({R.id.service_iv})
    public void onUnclickViewClicked(View view) {
        switch (view.getId()) {
            case R.id.service_iv:
                ((MainActivity) mContext).switchTab(MainActivity.TAB_INDEX_SERVICE);
                break;
            default:
        }
    }


    @OnClick({R.id.unlogined_lable, R.id.setting_iv, R.id.profile_image, R.id.total_assets_lable, R.id.total_assets_value, R.id.purse_balance_lable, R.id.purse_balance_value, R.id.recharge_ll, R.id.drawcash_rl, R.id.note_record_rl, R.id.quota_conversion_rl, R.id.captal_record_rl, R.id.security_center_rl, R.id.concession_record_rl, R.id.message_center_rl, R.id.share_friend_rl, R.id.qaplication_for_preferential_rl, R.id.see_more_rl})
    public void onViewClicked(View view) {
        SoundUtil.getInstance().playVoiceOnclick();

        if (!DataCenter.getInstance().isLogin() && view.getId() != R.id.see_more_rl) {
            ActivityUtil.gotoLogin();
            return;
        }

        switch (view.getId()) {
            case R.id.unlogined_lable:
                break;
            case R.id.setting_iv:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.profile_image:
                break;
            case R.id.total_assets_lable:
                break;
            case R.id.total_assets_value:
                break;
            case R.id.purse_balance_lable:
                break;
            case R.id.purse_balance_value:
                break;
            case R.id.recharge_ll: {
                ((MainActivity) mContext).switchTab(MainActivity.TAB_INDEX_DEPOSIT);
                //((MainActivity) mContext).gotoTabDeposit(MainActivity.TAB_INDEX_DEPOSIT+"");
               /* String url = SharePreferenceUtil.getDepositUrl(mContext);
                ActivityUtil.startWebView(url, "", ConstantValue.WEBVIEW_TYPE_ORDINARY);*/
            }
            break;
            case R.id.drawcash_rl:
                startActivity(new Intent(mContext, WithdrawMoneyActivity.class));
//                ((MainActivity) mContext).gotoTabWithdrawMoney(MainActivity.TAB_INDEX_DEPOSIT+"");
                break;
            case R.id.note_record_rl:

                startActivity(new Intent(mContext, NoteRecordActivity.class).putExtra(NoteRecordActivity.RECORD_WALLET_BALANCE, mWalletBalance));
                break;
            case R.id.quota_conversion_rl:
                startActivity(new Intent(mContext, QuotaConversionActivity.class));
 //               ((MainActivity) mContext).gotoTabQuotaConversion(MainActivity.TAB_INDEX_DEPOSIT+"");
                break;
            case R.id.captal_record_rl:
                startActivity(new Intent(mContext, CapitalRecordActivity.class));
                break;
            case R.id.security_center_rl:
                Intent intent = new Intent(mContext, SecurityCenterActivity.class);
                intent.putExtra("name", mNameStr);
                startActivity(intent);
                break;
            case R.id.concession_record_rl:
                startActivity(new Intent(mContext, PromoRecordActivity.class));
                break;
            case R.id.share_friend_rl:
                startActivity(new Intent(mContext, ShareFriendTipActivity.class));
                break;
            case R.id.message_center_rl:
                startActivity(new Intent(mContext, MessageCenterActivity.class));
                break;
            case R.id.qaplication_for_preferential_rl:
                Intent intent1 = new Intent(mContext, MessageCenterActivity.class);
                intent1.putExtra(MessageCenterActivity.WHERE_FROM, MessageCenterActivity.TAB_APPLICATION_PREFERENTAIL);
                startActivity(intent1);
                break;
            case R.id.see_more_rl:
                startActivity(new Intent(mContext, MoreActivity.class));
                break;
            default:

        }
    }

    @Override
    public void onLinkResult(Object o) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }

        if (o != null && o instanceof MineLink) {
            MineLink mineLink = (MineLink) o;
            setViewData(mineLink.getUser());
        }
    }

    private void setViewData(MineLink.UserBean userBean) {
        if (userBean != null) {
            mNameStr = userBean.getUsername();
            mNameTv.setText(mNameStr);
            mTimeValue.setText(userBean.getLastLoginTime());
            mTotalAssetsValue.setText(userBean.getCurrency() + new DecimalFormat("######0.00").format(userBean.getTotalAssets()));
            mWalletBalance = new DecimalFormat("######0.00").format(userBean.getWalletBalance());
            mPurseBalanceValue.setText(userBean.getCurrency() + mWalletBalance);
            mWithdrawProgressTv.setText(userBean.getCurrency() + userBean.getWithdrawAmount());
        }
    }

    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_MINE_LINK)})
    public void receiveUserAccount(UserAccount userAccount) {
//        if (userAccount.getLink() != null) {
//            List<UserAccount.LinkBean> links = userAccount.getLink();
//            for (int i = 0; i < links.size(); i++) {
//                if ("deposit".equals(links.get(i).getCode())) {
//                    SharePreferenceUtil.saveDepositUrl(mContext, links.get(i).getLink());
//                } else if ("transfer".equals(links.get(i).getCode())) {
//                    SharePreferenceUtil.saveQuotaUrl(mContext, links.get(i).getLink());
//                } else if ("help".equals(links.get(i).getCode())) {
//                    SharePreferenceUtil.saveHelpUrl(mContext, links.get(i).getLink());
//                } else if ("terms".equals(links.get(i).getCode())) {
//                    SharePreferenceUtil.saveTermsUrl(mContext, links.get(i).getLink());
//                } else if ("about".equals(links.get(i).getCode())) {
//                    SharePreferenceUtil.saveAboutsUrl(mContext, links.get(i).getLink());
//                }
//            }
//        }

        if (userAccount.getUser() != null) {
            UserAccount.UserBean userBean = userAccount.getUser();
            setViewData(userBean);
        }


        shrinkRefreshView("refresh_user_data");
    }

    private void setViewData(UserAccount.UserBean userBean) {
        if (userBean != null) {
            // 更新ui
            mUnLoginedLable.setVisibility(View.GONE);
            mNameTv.setVisibility(View.VISIBLE);
            mTimeLable.setVisibility(View.VISIBLE);
            mTimeValue.setVisibility(View.VISIBLE);
            mAssetLL.setVisibility(View.VISIBLE);
            //           mLogoutTv.setVisibility(View.VISIBLE);
            mWithdrawProgressTv.setVisibility(View.VISIBLE);


            mNameTv.setText(userBean.getUsername());
            mTimeValue.setText(userBean.getLastLoginTime());
            mTotalAssetsValue.setText(userBean.getCurrency() + BigDemicalUtil.moneyFormat(userBean.getTotalAssets()));
            mWalletBalance = BigDemicalUtil.moneyFormat(userBean.getWalletBalance());
            mPurseBalanceValue.setText(userBean.getCurrency() + mWalletBalance);

            if (0 == userBean.getWithdrawAmount()) {
                mWithdrawProgressTv.setVisibility(View.GONE);
            } else {
                mWithdrawProgressTv.setVisibility(View.VISIBLE);
            }
            mWithdrawProgressTv.setText(getString(R.string.withdraw_label_pattern) + userBean.getCurrency() + BigDemicalUtil.moneyFormat(userBean.getWithdrawAmount()));
        }
    }


    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_LOGOUT)})
    public void logout(String s) {
        LogUtils.d(s);
        mUnLoginedLable.setVisibility(View.VISIBLE);
        mNameTv.setVisibility(View.GONE);
        mTimeLable.setVisibility(View.GONE);
        mTimeValue.setVisibility(View.GONE);
        mAssetLL.setVisibility(View.GONE);
        //    mLogoutTv.setVisibility(View.GONE);
        mWithdrawProgressTv.setVisibility(View.GONE);
    }


    @OnClick({R.id.logout_tv})
    public void onLogoutTvViewClicked() {
        final IOSDialog alertDialog = new IOSDialog(getActivity());
        alertDialog.builder();
        alertDialog.setCancelable(true);
        alertDialog.setMsg("是否确定退出账号?");
        alertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserPresenter.LoginOut();
                alertDialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClickResult(Object o) {
        Logout logout = (Logout) o;
        if (logout.isSuccess()) {
            ActivityUtil.logout();
        }
    }

    @Override
    public void onRefresh() {
        //mPresenter.getLink();
        RxBus.get().post(ConstantValue.EVENT_TYPE_REFRESH_MINE_DATA, "refresh Minefragment data");
    }

    @Subscribe(tags = {@Tag(ConstantValue.EVENT_TYPE_NETWORK_EXCEPTION)})
    public void shrinkRefreshView(String s) {
        LogUtils.d(s);
        //  收起刷新
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onGetUnReadCountResult(Object o) {
        SiteMsgUnReadCount siteMsgUnReadCount = (SiteMsgUnReadCount) o;
        if (mVTipMyNotice == null) return;
        if (siteMsgUnReadCount.getSysMessageUnReadCount() > 0 || siteMsgUnReadCount.getAdvisoryUnReadCount() > 0) {
            mVTipMyNotice.setVisibility(View.VISIBLE);
//            mVTipMyNotice.setText((siteMsgUnReadCount.getSysMessageUnReadCount()
//                    + siteMsgUnReadCount.getAdvisoryUnReadCount()) + "");
        } else {
            mVTipMyNotice.setVisibility(View.INVISIBLE);
        }
    }

}
