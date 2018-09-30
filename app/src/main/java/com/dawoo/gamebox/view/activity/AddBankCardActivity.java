package com.dawoo.gamebox.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.CleanLeakUtils;
import com.dawoo.coretool.util.StringTool;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.BankCards;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.WithdrawSubmitResult;
import com.dawoo.gamebox.mvp.presenter.WithdrawPresenter;
import com.dawoo.gamebox.mvp.view.IAddBankCardView;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.view.HeaderView;
import com.hwangjr.rxbus.RxBus;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by b on 18-1-15.
 * 添加银行卡
 */

public class AddBankCardActivity extends BaseActivity implements IAddBankCardView {
    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.tv_real_name_hint)
    TextView mHintRealName;
    @BindView(R.id.et_real_name)
    EditText mEtRealName;
    @BindView(R.id.iv_select)
    ImageView mIvSelect;
    @BindView(R.id.ll_select_bank)
    LinearLayout mLlSelectBank;
    @BindView(R.id.tv_bank)
    TextView mTvBank;
    @BindView(R.id.et_card_number)
    EditText mEtCardNumber;
    @BindView(R.id.et_open_account_bank)
    EditText mEtOpenAccountBank;
    @BindView(R.id.tv_open_account_bank_info)
    TextView mTvOpenAccountInfo;
    @BindView(R.id.bt_submit)
    Button mBtSubmit;


    private List<String> mBanks = new ArrayList<>();
    private String mBankName;
    private WithdrawPresenter mMWithdrawPresenter;
    public static final int ADD_BANK_CARD = 8008;
    private List<BankCards.BankListBean> mBankList;
    private int mIndex = 3;


    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_add_bank_card);
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader(getString(R.string.add_bank_card_activity), true);
    }

    @Override
    protected void initData() {
        mMWithdrawPresenter = new WithdrawPresenter(this, this);
        BankCards.UserBean.BankcardBean bankcardBean = getIntent().getParcelableExtra(SecurityCenterActivity.TO_BANK_CARD_ACT);
        if (bankcardBean != null)
            initView(bankcardBean);
        else
            mMWithdrawPresenter.getCardType();
    }


    @Override
    public void getCardType(Object o) {
        BankCards bankCards = (BankCards) o;
        if (bankCards == null)
            return;
        BankCards.UserBean.BankcardBean bankcardBean = bankCards.getUser().getBankcard();
        mBankList = bankCards.getBankList();

        if (mBankList.size() != 0) {
            for (BankCards.BankListBean bank : mBankList) {
                mBanks.add(bank.getText());
            }
        }
        if (bankcardBean != null) {
            initView(bankcardBean);
        } else {
            String realName = DataCenter.getInstance().getRealName();
            if (!TextUtils.isEmpty(realName)) {
                mEtRealName.setFocusable(false);
                mEtRealName.setText(StringTool.getCoverRealName(realName));
            }
        }
    }

    private void initView(BankCards.UserBean.BankcardBean bankcardBean) {
        mHeadView.setHeader(getString(R.string.look_bank_card_activity), true);
        mEtRealName.setFocusable(false);
        mEtCardNumber.setFocusable(false);
        mEtOpenAccountBank.setFocusable(false);
        mHintRealName.setVisibility(View.GONE);
        mIvSelect.setVisibility(View.GONE);
        mBtSubmit.setVisibility(View.GONE);
        mTvOpenAccountInfo.setVisibility(View.GONE);
        mEtRealName.setText(bankcardBean.getBankcardMasterName());
        mTvBank.setText(bankcardBean.getBankName());
        mEtCardNumber.setText(bankcardBean.getBankcardNumber());
        if (TextUtils.isEmpty(bankcardBean.getBankDeposit())) {
            mEtOpenAccountBank.setHint("");
        } else
            mEtOpenAccountBank.setText(bankcardBean.getBankDeposit());
        mLlSelectBank.setEnabled(false);
    }


    public void onSelectBank(View view) {
        mMWithdrawPresenter.initSelectBankDialog(mBanks);
    }

    @Override
    public void selectedBank(String bankName, int index) {
        mBankName = bankName;
        this.mIndex = index;
        mTvBank.setText(mBankName);
    }

    public void onSubmit(View view) {
        SoundUtil.getInstance().playVoiceOnclick();
        if (mEtCardNumber.getText().toString().trim().length() < 10) {
            ToastUtil.showResLong(this, R.string.card_lenght_hint);
            return;
        }
        String mRealName;
        String realName = DataCenter.getInstance().getRealName();
        if (!TextUtils.isEmpty(realName)) {
            mRealName = realName;
        } else {
            mRealName = mEtRealName.getText().toString().trim();
        }


        String mCardNumber = mEtCardNumber.getText().toString().trim();
        String mOpenAccountBank = mEtOpenAccountBank.getText().toString().trim();
        if (TextUtils.isEmpty(mRealName)) {
            ToastUtil.showResLong(this, R.string.card_name_hint);
            return;
        }
        if (TextUtils.isEmpty(mBankName)) {
            ToastUtil.showResLong(this, R.string.card_bank_type_hint);
            return;
        }else if (mBankList.get(mIndex).getValue().equals("other_bank")){
            if (TextUtils.isEmpty(mOpenAccountBank)){
                ToastUtil.showResLong(this, R.string.please_input_open_account_bank_info);
                return;
            }
        }
        mMWithdrawPresenter.submitBankCard(mRealName, mBankList.get(mIndex).getValue(), mCardNumber, mOpenAccountBank);
    }

    @Override
    public void submitBankCard(Object o) {
        WithdrawSubmitResult withdrawSubmitResult = (WithdrawSubmitResult) o;
        if (withdrawSubmitResult.getCode() == 0 && withdrawSubmitResult.getError() == 0) {
            ToastUtil.showToastLong(this, "绑定成功");
            RxBus.get().post(ConstantValue.EVENT_TYPE_ACCOUNT, "bank update");
            setResult(ADD_BANK_CARD);
            finish();
            return;
        }
        ToastUtil.showToastLong(this, withdrawSubmitResult.getMessage());
    }

    @Override
    protected void onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this);
        mMWithdrawPresenter.onDestory();
        super.onDestroy();
    }
}
