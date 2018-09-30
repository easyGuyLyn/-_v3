package com.dawoo.gamebox.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.BankCards;
import com.dawoo.gamebox.bean.WithdrawSubmitResult;
import com.dawoo.gamebox.mvp.presenter.WithdrawPresenter;
import com.dawoo.gamebox.mvp.view.IAddBitcoinView;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.view.HeaderView;

import butterknife.BindView;

/**
 * Created by b on 18-1-17.
 * 添加比特币
 */

public class AddBitcoinActivity extends BaseActivity implements IAddBitcoinView{

    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.bitcoin_card_ll)
    LinearLayout mLLBitcoinCard;
    @BindView(R.id.tv_bitcoin_card)
    TextView mTvBitcoinCard;
    @BindView(R.id.ll_add_btc)
    LinearLayout mLlAddBtc;
    @BindView(R.id.et_bitcoin_number)
    EditText mEtBitcoinNumber;
    @BindView(R.id.bt_again)
    Button mBtAgain;
    @BindView(R.id.bt_bind)
    Button mBtBind;
    private WithdrawPresenter mMWithdrawPresenter;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_add_bitcoin);
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader(getString(R.string.add_bitcoin_activity),true);
    }

    @Override
    protected void initData() {
        mMWithdrawPresenter = new WithdrawPresenter(this,this);
        mMWithdrawPresenter.getBtcInfo();
    }

    public void onReset(View view){SoundUtil.getInstance().playVoiceOnclick();mEtBitcoinNumber.setText("");}

    public void onBind(View view){
        SoundUtil.getInstance().playVoiceOnclick();
        String bitcoinNumber = mEtBitcoinNumber.getText().toString().trim();
        if (TextUtils.isEmpty(bitcoinNumber))
            return;
        mMWithdrawPresenter.submitBtc(bitcoinNumber);
    }

    @Override
    public void submitBtc(Object o) {
        WithdrawSubmitResult withdrawSubmitResult = (WithdrawSubmitResult)o;
        if (withdrawSubmitResult.getCode() == 1310){
            ToastUtil.showResLong(this,R.string.bitcoin_address_error);
            return;
        }

        if (withdrawSubmitResult.getCode() == 0 && withdrawSubmitResult.getData() != null && !TextUtils.isEmpty(withdrawSubmitResult.getData().getBtcNumber())){
            ToastUtil.showToastLong(this,getString(R.string.bind_bit_ok));
            finish();
            return;
        }
        ToastUtil.showToastLong(this,withdrawSubmitResult.getMessage());

    }

    @Override
    public void getBtcInfo(Object o) {
        BankCards bitcoin = (BankCards)o;
        if (bitcoin !=null && bitcoin.getUser() != null && bitcoin.getUser().getBtc()!=null){
            mHeadView.setHeader(getString(R.string.look_bitcoin_card_activity),true);
            mLLBitcoinCard.setVisibility(View.VISIBLE);
            mLlAddBtc.setVisibility(View.GONE);
            mTvBitcoinCard.setText(bitcoin.getUser().getBtc().getBtcNumber());
        }
    }

    @Override
    protected void onDestroy() {
        mMWithdrawPresenter.onDestory();
        super.onDestroy();
    }
}
