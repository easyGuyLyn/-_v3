package com.dawoo.gamebox.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.CleanLeakUtils;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.math.BigDemicalUtil;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.RefreshhApis;
import com.dawoo.gamebox.bean.UserAccount;
import com.dawoo.gamebox.bean.Withdraw;
import com.dawoo.gamebox.bean.WithdrawFee;
import com.dawoo.gamebox.bean.WithdrawResult;
import com.dawoo.gamebox.bean.WithdrawSubmitResult;
import com.dawoo.gamebox.mvp.presenter.WithdrawPresenter;
import com.dawoo.gamebox.mvp.view.IWithdrawView;
import com.dawoo.gamebox.net.GlideApp;
import com.dawoo.gamebox.net.HttpResult;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.activity.AddBankCardActivity;
import com.dawoo.gamebox.view.activity.AddBitcoinActivity;
import com.dawoo.gamebox.view.activity.AuditActivity;
import com.dawoo.gamebox.view.activity.ModifySecurityPwdActivity;
import com.dawoo.gamebox.view.activity.WithdrawMoneyActivity;
import com.dawoo.gamebox.view.view.CommonHintDialog;
import com.dawoo.gamebox.view.view.CommonInputBoxDialog;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 取款碎片
 */
public class WithdrawMoneyFragment extends BaseFragment implements IWithdrawView, View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.il_no_sufficient_funds)
    LinearLayout mIlNoSufficientFunds;
    @BindView(R.id.tv_withdraw_state)
    TextView mTvWithdrawState;
    @BindView(R.id.bt_deposit)
    Button mBtDeposit;
    @BindView(R.id.bt_bank_card_account)
    Button mBtBankCardAccount;
    @BindView(R.id.bt_bitcoin_account)
    Button mBtBitcoinAccount;
    @BindView(R.id.tv_bind_bank_card)
    TextView mTvBindBankCard;
    @BindView(R.id.ll_bank_card)
    LinearLayout LlBankCard;
    @BindView(R.id.iv_bank_icon)
    ImageView mIvBankIcon;
    @BindView(R.id.tv_bank_card)
    TextView mTvBankCard;
    @BindView(R.id.et_withdrawals_amount)
    EditText mEtWithdrawalsAmount;
    @BindView(R.id.tv_service_charge)
    TextView mTvServiceCharge;
    @BindView(R.id.tv_administrative_fee)
    TextView mTvAdministrativeFee;
    @BindView(R.id.tv_discount)
    TextView mTvDiscount;
    @BindView(R.id.tv_end_withdrawals_amount)
    TextView mTvEndWithdrawalsAmount;
    @BindView(R.id.tv_look_record)
    TextView mTvLookRecord;
    @BindView(R.id.bt_submit)
    Button mBtSubmit;
    @BindView(R.id.ll_withdraw)
    LinearLayout mLlWithdraw;
    @BindView(R.id.ll_tab)
    LinearLayout mLlTab;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.point)
    View point;
    @BindView(R.id.user_name_tv)
    TextView mUserNameTv;
    @BindView(R.id.user_account_tv)
    TextView mUserAccountTv;
    @BindView(R.id.account_more_iv)
    ImageView accountMoreIv;
    @BindView(R.id.Logined_rl)
    RelativeLayout LoginedRl;
    @BindView(R.id.bt_cycle)
    Button btCycleing;

    public static final String RECYCING = "一键回收";
    public static final String REFRESHAPI = "一键刷新";
    private WithdrawPresenter mMWithdrwaPresenter;
    private Withdraw mMWithdraw;
    private Withdraw.AuditMapBean mAuditMapBean;
    private int mType = 1;
    private final int BANK_CARD = 1;
    private final int BITCOIN_CARD = 2;
    private CommonInputBoxDialog mMCommonInputDialog;
    private String mMOriginPwd;
    private double mActualWithdraw;
    private Context context;
    //    private UserAccount.UserBean userBean;
    private UserAccount mAccount;

    public static WithdrawMoneyFragment newInstance() {
        WithdrawMoneyFragment mWithdrawMoneyFragment = new WithdrawMoneyFragment();
        return mWithdrawMoneyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = mContext;
        View rootView = inflater.inflate(R.layout.fragment_withdraw_money, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        RxBus.get().register(this);
        initView();
        initData();
        return rootView;
    }

    private void initView() {
        mBtBankCardAccount.setSelected(true);
        mBtBankCardAccount.setOnClickListener(this);
        mBtBitcoinAccount.setOnClickListener(this);
        mTvBindBankCard.setOnClickListener(this);
        mBtDeposit.setOnClickListener(this);
        mTvLookRecord.setOnClickListener(this);
        mBtSubmit.setOnClickListener(this);
        btCycleing.setOnClickListener(this);
        mEtWithdrawalsAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    if ("0".equals(s.toString())) {
                        mEtWithdrawalsAmount.setText(null);
                        return;
                    }
                    mCountDownTimer.cancel();
                    mCountDownTimer.start();
                }
            }
        });
    }


    private void initData() {
        mMWithdrwaPresenter = new WithdrawPresenter(mContext, this);
    }

    private CountDownTimer mCountDownTimer = new CountDownTimer(1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            String num = mEtWithdrawalsAmount.getText().toString().trim();

            if (!TextUtils.isEmpty(num)) {
                mActualWithdraw = 0;
                mMWithdrwaPresenter.withdrawFee(Double.parseDouble(num));
            }
        }
    };

    @Override
    protected void loadData() {
        LogUtils.e("loadData");
      //  mMWithdrwaPresenter.getWithdraw();
       // mMWithdrwaPresenter.getAccount();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        RxBus.get().unregister(this);
        CleanLeakUtils.fixInputMethodManagerLeak(mContext.getApplicationContext());
        mMWithdrwaPresenter.onDestory();
    }


    @Override
    public void onClick(View v) {
        SoundUtil.getInstance().playVoiceOnclick();
        switch (v.getId()) {
            case R.id.bt_bank_card_account:
                mBtBankCardAccount.setSelected(true);
                mBtBitcoinAccount.setSelected(false);
                mBtBankCardAccount.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                mBtBitcoinAccount.setTextColor(ContextCompat.getColor(mContext, R.color.tab_button_blue));
                setDataToView(BANK_CARD);
                break;
            case R.id.bt_bitcoin_account:
                mBtBitcoinAccount.setSelected(true);
                mBtBankCardAccount.setSelected(false);
                mBtBankCardAccount.setTextColor(ContextCompat.getColor(mContext, R.color.tab_button_blue));
                mBtBitcoinAccount.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                setDataToView(BITCOIN_CARD);
                break;
            case R.id.tv_bind_bank_card:
                if (mType == BANK_CARD)
                    startActivity(new Intent(mContext, AddBankCardActivity.class));
                else
                    startActivity(new Intent(mContext, AddBitcoinActivity.class));
                break;
            case R.id.bt_submit:
                if (mMWithdraw == null) {
                    mMWithdrwaPresenter.getWithdraw();
                    return;
                }
                if (!mMWithdraw.isSafePassword()) {
                    ToastUtil.showResLong(mContext, R.string.set_origin_pwd);
                    startActivity(new Intent(mContext, ModifySecurityPwdActivity.class));
                    return;
                }
                if (mTvBindBankCard.getVisibility() == View.VISIBLE) {
                    showNoBankCardDialog();
                    return;
                }
                testInputAndSubmit();
                break;
            case R.id.bt_deposit:
                RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_DEPOSIT, "gotodeposit");
                //  RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOONETAB_DEPOSIT, "onclickService");
                break;
            case R.id.tv_look_record:

                startActivity(new Intent(mContext, AuditActivity.class));
//                if (mMWithdraw != null) {
//                    ActivityUtil.startWebView(DataCenter.getInstance().getIp() + mMWithdraw.getAuditLogUrl(), "", WEBVIEW_TYPE_THIRD_ORDINARY,1);
//                }
            case R.id.bt_cycle:
                Recycling();
                break;
            default:
                break;
        }

    }


    public void Recycling() {
        if (mAccount == null || mAccount.getUser() == null) return;
        if (btCycleing.getText().equals(WithdrawMoneyActivity.RECYCING)) {
            mMWithdrwaPresenter.recovery();
        } else {
            mMWithdrwaPresenter.refreshAPI();
        }
//        oneKeyBack(mAccount.getUser());
    }

    private void setDataToView(int i) {
        if (mMWithdraw == null) {
            mMWithdrwaPresenter.getWithdraw();
            return;
        }
        Withdraw.BankcardMapBean.BankCardBean bankBean;
        if (i == BANK_CARD) {
            bankBean = mMWithdraw.getBankcardMap().getBankCardBean1();
            if (bankBean == null) {
                mType = BANK_CARD;
                mTvBindBankCard.setVisibility(View.VISIBLE);
                mTvBindBankCard.setText(R.string.bind_bank_card_hind);
                LlBankCard.setVisibility(View.GONE);
            } else {
                mTvBindBankCard.setVisibility(View.GONE);
                LlBankCard.setVisibility(View.VISIBLE);
                String url = NetUtil.handleUrl(bankBean.getBankUrl());
                GlideApp.with(this).load(url).into(mIvBankIcon);
                mTvBankCard.setText(bankBean.getBankcardMasterName() + "    " + bankBean.getBankcardNumber());
            }
        } else {
            bankBean = mMWithdraw.getBankcardMap().getBankCardBean2();
            if (bankBean == null) {
                mType = BITCOIN_CARD;
                mTvBindBankCard.setVisibility(View.VISIBLE);
                mTvBindBankCard.setText(R.string.add_bitcoin_activity);
                LlBankCard.setVisibility(View.GONE);
            } else {
                mTvBindBankCard.setVisibility(View.GONE);
                LlBankCard.setVisibility(View.VISIBLE);
                mIvBankIcon.setImageResource(R.mipmap.bitcoin);
                mTvBankCard.setText("    " + bankBean.getBankcardNumber());
            }
        }
        mEtWithdrawalsAmount.setHint(mMWithdraw.getCurrencySign() + mMWithdraw.getRank().getWithdrawMinNum() + "-" + mMWithdraw.getCurrencySign() + mMWithdraw.getRank().getWithdrawMaxNum());
        mAuditMapBean = mMWithdraw.getAuditMap();
        if (mAuditMapBean == null) return;
        double conunterFee = 0;
        try {
            conunterFee = Double.parseDouble(mAuditMapBean.getCounterFee());
        } catch (Exception e) {
            Log.i("conunterFee", "conunterFee:" + conunterFee);
        }
//        mTvServiceCharge.setText("0.0".equals(Math.abs(conunterFee) + Math.abs(mAuditMapBean.getWithdrawFeeMoney())) ? "免手续费" : mMWithdraw.getCurrencySign() + (Math.abs(conunterFee) + Math.abs(mAuditMapBean.getWithdrawFeeMoney())));
//        mTvAdministrativeFee.setText(mMWithdraw.getCurrencySign() + Math.abs(mAuditMapBean.getAdministrativeFee()));
//        mTvDiscount.setText(mMWithdraw.getCurrencySign() + Math.abs(mAuditMapBean.getDeductFavorable()));
    }

    private void showNoBankCardDialog() {
        CommonHintDialog mCommonHintDialog = new CommonHintDialog(mContext, R.style.CommonHintDialog);
        mCommonHintDialog.setCanceledOnTouchOutside(false);
        mCommonHintDialog.setCancelClickListener(new CommonHintDialog.OnCancelClickListener() {
            @Override
            public void onClick(CommonHintDialog commonHintDialog) {
                mCommonHintDialog.dismiss();
            }
        });
        mCommonHintDialog.show();
        mTvBindBankCard.setVisibility(View.VISIBLE);
        LlBankCard.setVisibility(View.GONE);
    }

    private void testInputAndSubmit() {
        if (TextUtils.isEmpty(mEtWithdrawalsAmount.getText().toString().trim())) {
            return;
        }
        double num = Double.valueOf(mEtWithdrawalsAmount.getText().toString().trim());
        if (num == 0) {
            ToastUtil.showResShort(mContext, R.string.input_withdrawals_amount);
            return;
        } else if (num > mMWithdraw.getTotalBalance()) {
            DecimalFormat df = new DecimalFormat("######0.00");
            String f1 = df.format(mMWithdraw.getTotalBalance());

            ToastUtil.showToastLong(mContext, String.format(getString(R.string.withdraw_max), f1));
            return;
        } else if (num < Double.parseDouble(mMWithdraw.getRank().getWithdrawMinNum()) || num > Double.parseDouble(mMWithdraw.getRank().getWithdrawMaxNum())) {
            ToastUtil.showResLong(mContext, R.string.input_withdraw_error);
            return;
        }
        if (mActualWithdraw > 0) {
            mMCommonInputDialog = new CommonInputBoxDialog(mContext, R.style.CommonHintDialog);
            mMCommonInputDialog.show();
            mMCommonInputDialog.setOkClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMOriginPwd = mMCommonInputDialog.getOriginPwd();
                    if (TextUtils.isEmpty(mMOriginPwd))
                        ToastUtil.showResLong(mContext, R.string.input_cant_null);
                    else
                        mMWithdrwaPresenter.submitWithdraw(num, mMWithdraw.getToken(), mType, mMOriginPwd);
                }
            });

        } else ToastUtil.showResLong(mContext, R.string.withdraw_min);

    }

    @Override
    public void onWithdrawInfo(Object o) {
        WithdrawResult withdrawResult = (WithdrawResult) o;
        if (withdrawResult.getCode() == 1100) {
            mTvWithdrawState.setText(R.string.have_withdraw_order);
            mIlNoSufficientFunds.setVisibility(View.VISIBLE);
            mBtDeposit.setVisibility(View.GONE);
            btCycleing.setVisibility(View.GONE);

            return;
        }
        if (withdrawResult.getCode() == 1001) {
            ActivityUtil.gotoLogin();
            return;
        }
        //账户提现次数过多
        if (withdrawResult.getCode() == 1024) {
            ToastUtil.showToastShort(mContext.getApplicationContext(), withdrawResult.getMsg());
            return;
        }
        if (withdrawResult.getCode() == 1103) {
            showNoBankCardDialog();
            return;
        }

        // 全款玩家已被冻结，请联系客服处理
        if (withdrawResult.getCode() == 1101) {
            ToastUtil.showToastLong(mContext, withdrawResult.getMsg());
            return;
        }

        mMWithdraw = withdrawResult.getData();

        if (mMWithdraw == null) {
            return;
        }
        if (mMWithdraw.isHasBank() == false) {
            showNoBankCardDialog();
//            return;
        }

        if (withdrawResult.getCode() == 1102) {
            mTvWithdrawState.setText(String.format(getString(R.string.not_sufficient_funds_hint), withdrawResult.getMsg()));
            mIlNoSufficientFunds.setVisibility(View.VISIBLE);
            return;
        }

        mLlWithdraw.setVisibility(View.VISIBLE);

        if (mMWithdraw.isIsCash() && mMWithdraw.isIsBit()) {
            mLlTab.setVisibility(View.VISIBLE);
        }

        if (mMWithdraw.isIsCash())
            setDataToView(BANK_CARD);
        else if (mMWithdraw.isIsBit()) {
            setDataToView(BITCOIN_CARD);
        }
    }

    @Override
    public void submitWithdraw(Object o) {
        WithdrawSubmitResult withdrawSubmitResult = (WithdrawSubmitResult) o;
        if (withdrawSubmitResult != null) {
            if (withdrawSubmitResult.getCode() == 1001) {
                ActivityUtil.gotoLogin();
                return;
            }
            if (withdrawSubmitResult.getCode() == 1305) {
                ToastUtil.showResLong(mContext, R.string.origin_pwd_error);
                return;
            }

            if (withdrawSubmitResult.getData() != null && !TextUtils.isEmpty(withdrawSubmitResult.getData().getMsg())) {
                ToastUtil.showToastLong(mContext, withdrawSubmitResult.getData().getMsg());
            } else {
                ToastUtil.showToastLong(mContext, withdrawSubmitResult.getMessage());
            }


            if (withdrawSubmitResult.getCode() == 1404) {
                startActivity(new Intent(mContext, ModifySecurityPwdActivity.class));
                return;
            }


            mMWithdraw.setToken(withdrawSubmitResult.getData().getToken());
            if (withdrawSubmitResult.getCode() == 0) {
            }
        }

    }

    @Override
    public void checkSafePassword(Object o) {
        WithdrawSubmitResult withdrawSubmitResult = (WithdrawSubmitResult) o;
        if (withdrawSubmitResult != null) {
            if (withdrawSubmitResult.getCode() == 1001) {
                ActivityUtil.gotoLogin();
                return;
            }
            if (withdrawSubmitResult.getCode() == 1305) {
                ToastUtil.showResLong(mContext, R.string.origin_pwd_error);
                return;
            }

            if (withdrawSubmitResult.getData() != null && !TextUtils.isEmpty(withdrawSubmitResult.getData().getMsg())) {
                ToastUtil.showToastLong(mContext, withdrawSubmitResult.getData().getMsg());
            } else {
                ToastUtil.showToastLong(mContext, withdrawSubmitResult.getMessage());
            }

            if (withdrawSubmitResult.getCode() == 1404) {
                startActivity(new Intent(mContext, ModifySecurityPwdActivity.class));
                return;
            }
            mMWithdraw.setToken(withdrawSubmitResult.getData().getToken());
            if (withdrawSubmitResult.getCode() == 0) {
                //finish
            }
        }
    }

    @Override
    public void withdrawFee(Object o) {
        WithdrawFee withdrawFee = (WithdrawFee) o;
        if (withdrawFee != null) {
            if (withdrawFee.getCode() == 1001) {
                ActivityUtil.gotoLogin();
                return;
            }
            if (withdrawFee.getData() != null) {
                mActualWithdraw = withdrawFee.getData().getActualWithdraw();
                BigDecimal bg = new BigDecimal(withdrawFee.getData().getCounterFee());
                double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
                if (f1 == 0) {
                    mTvServiceCharge.setText("免手续费");
                } else
                    mTvServiceCharge.setText("¥" + Math.abs(f1));
                mTvAdministrativeFee.setText("¥" + Math.abs(withdrawFee.getData().getAdministrativeFee()));
                mTvDiscount.setText("¥" + Math.abs(withdrawFee.getData().getDeductFavorable()));
                mTvEndWithdrawalsAmount.setText("¥" + BigDemicalUtil.moneyFormat(withdrawFee.getData().getActualWithdraw()));
            }
        }
    }

    @Override
    public void onRecoveryResult(Object o) {
        if (o != null && o instanceof HttpResult) {
            HttpResult httpResult = (HttpResult) o;
            ToastUtil.showToastShort(context, httpResult.getMessage());
            mMWithdrwaPresenter.getAssert();
        } else {
            ToastUtil.showToastShort(context, "回收失败!");
        }
    }

    @Override
    public void onRefreshResult(Object o) {
        if (o != null && o instanceof RefreshhApis) {
            ToastUtil.showToastShort(context, "刷新成功");
            refreshApis(o);
        } else {
            ToastUtil.showToastShort(context, "刷新失败!");
        }
    }

    @Override
    public void onAssertResult(Object o) {
        Object o1 = mMWithdrwaPresenter.rePlaceData(o, mAccount);
        setPopuPWindData(o1);
    }

    @Override
    public void onAccountResult(Object o) {
        setPopuPWindData(o);
    }

    @Override
    public String toString() {
        String title = "取款";
        return title;
    }

    void refreshApis(Object o) {
        if (o != null && o instanceof RefreshhApis) {
            // 刷新apis
            // 请求刷新 account
            mMWithdrwaPresenter.getAssert();
        }
    }

    void setPopuPWindData(Object o) {
        if (o != null && o instanceof UserAccount) {
            // 设置账户
            mAccount = ((UserAccount) o);
            oneKeyBack(mAccount.getUser());
            RxBus.get().post(ConstantValue.EVENT_TYPE_MINE_LINK, mAccount);
            UserAccount.UserBean userBean = mAccount.getUser();
            mUserNameTv.setText(userBean.getUsername());
            mUserAccountTv.setText(userBean.getCurrency() + BigDemicalUtil.moneyFormat(userBean.getWalletBalance()));
            if (userBean.getWalletBalance() >= 100) {
                mIlNoSufficientFunds.setVisibility(View.GONE);
//                mLlWithdraw.setVisibility(View.VISIBLE);
                mMWithdrwaPresenter.getWithdraw();
            }
        }
    }

    private void oneKeyBack(UserAccount.UserBean userBean) {
        if (userBean.isAutoPay()) {
            btCycleing.setText(WithdrawMoneyActivity.RECYCING);
        } else {
            btCycleing.setText(WithdrawMoneyActivity.REFRESHAPI);

        }
    }

    /**
     * 刷新数据
     */
    @Subscribe(tags = {@Tag(ConstantValue.WITHDRAW_MONEY_FRAGMENT_LOAD)})
    public void gotoLoadData(String index) {
        mMWithdrwaPresenter.getWithdraw();
        mMWithdrwaPresenter.getAccount();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            gotoLoadData("");
        }
    }
}
