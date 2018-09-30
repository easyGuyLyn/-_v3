package com.dawoo.gamebox.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.CleanLeakUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.BankCards;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.IsBindUserPhoneBean;
import com.dawoo.gamebox.bean.IsOpenPwdByPhoneBean;
import com.dawoo.gamebox.mvp.presenter.UserPresenter;
import com.dawoo.gamebox.mvp.presenter.WithdrawPresenter;
import com.dawoo.gamebox.mvp.view.IAddBankCardView;
import com.dawoo.gamebox.mvp.view.IsOpenPwdByPhoneView;
import com.dawoo.gamebox.net.GlideApp;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SoundUtil;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.dawoo.gamebox.util.NetUtil.getOkHttpClientBuilderForHttps;

/**
 * 安全中心
 */
public class SecurityCenterActivity extends BaseActivity implements IAddBankCardView,IsOpenPwdByPhoneView {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.tip_tv)
    TextView mTipTv;
    @BindView(R.id.modify_login_pwd_rl)
    RelativeLayout mModifyLoginPwdRl;
    @BindView(R.id.modify_security_pwd_rl)
    RelativeLayout mModifySecurityPwdRl;
    @BindView(R.id.lock_screen_gisture_rl)
    RelativeLayout mLockScreenGistureRl;
    @BindView(R.id.bank_card_rl)
    RelativeLayout mBankCardRl;
    @BindView(R.id.btb_account_rl)
    RelativeLayout mBtbAccountRl;
    @BindView(R.id.iv_bank_name)
    ImageView mIvBankName;
    @BindView(R.id.tv_bank_card_number)
    TextView mTvBankCardNumber;
    @BindView(R.id.bank_btb_divider)
    View mBankBtbDivider;
    @BindView(R.id.modify_security_bind_phone)
    RelativeLayout modifySecurityBindPhone;

    private String name;

    public static final String TO_BANK_CARD_ACT = "BankcardBean";

    private BankCards.UserBean.BankcardBean mBankcardBean;
    private WithdrawPresenter mWithdrawPresenter;
    private UserPresenter userPresenter;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_security_center);
    }

    @Override
    protected void initViews() {
        name = DataCenter.getInstance().getUserName();
        mNameTv.setText(getString(R.string.name_security, name));
    }

    @Override
    protected void initData() {
        mWithdrawPresenter = new WithdrawPresenter(this, this);
        userPresenter = new UserPresenter(this,this);
        mWithdrawPresenter.getCardType();
        userPresenter.isOPenPwdByPhone();
    }

    @OnClick({R.id.iv_back, R.id.modify_login_pwd_rl, R.id.modify_security_pwd_rl, R.id.lock_screen_gisture_rl, R.id.bank_card_rl, R.id.btb_account_rl,R.id.modify_security_bind_phone})
    public void onViewClicked(View view) {
        SoundUtil.getInstance().playVoiceOnclick();
        switch (view.getId()) {
            case R.id.modify_login_pwd_rl:
                startActivity(new Intent(this, ModifyLoginPwdActivity.class));
                break;
            case R.id.modify_security_pwd_rl:
                startActivity(new Intent(this, ModifySecurityPwdActivity.class));
                break;
            case R.id.lock_screen_gisture_rl:
                startActivity(new Intent(this, GestureSettingActivity.class));
                break;
            case R.id.bank_card_rl:
                Intent intent = new Intent(this, AddBankCardActivity.class);
                intent.putExtra(TO_BANK_CARD_ACT, mBankcardBean);
                startActivityForResult(intent, AddBankCardActivity.ADD_BANK_CARD);
                break;
            case R.id.btb_account_rl:
                startActivity(new Intent(this, AddBitcoinActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.modify_security_bind_phone:
                getIsBindPhone();
                break;
            default:
        }
    }

    @Override
    public void getCardType(Object o) {
        BankCards bankCards = (BankCards) o;
        BankCards.UserBean userBean = bankCards.getUser();

        if (userBean != null) {
            if (userBean.isIsBit()) {
                mBtbAccountRl.setVisibility(View.VISIBLE);
            } else {
                mBtbAccountRl.setVisibility(View.GONE);
            }

            if (userBean.isIsCash()) {
                mBankCardRl.setVisibility(View.VISIBLE);
            } else {
                mBankCardRl.setVisibility(View.GONE);
            }

            if (userBean.isIsCash() && userBean.isIsBit()) {
                mBankBtbDivider.setVisibility(View.VISIBLE);
            } else {
                mBankBtbDivider.setVisibility(View.GONE);
            }
        }


        if (bankCards != null && bankCards.getUser() != null && bankCards.getUser().getBankcard() != null) {
            mBankcardBean = bankCards.getUser().getBankcard();
            String url = NetUtil.handleUrl(mBankcardBean.getBankUrl());
            GlideApp.with(this).load(url).into(mIvBankName);
            mTvBankCardNumber.setText(mBankcardBean.getBankcardNumber());
        } else {
            mTvBankCardNumber.setText(getString(R.string.not_card));
        }
    }

    @Override
    public void submitBankCard(Object o) {

    }

    @Override
    public void selectedBank(String bankName, int index) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AddBankCardActivity.ADD_BANK_CARD) {
            mWithdrawPresenter.getCardType();
        }
    }

    @Override
    protected void onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this);
        mWithdrawPresenter.onDestory();
      //  EasyProgressDialog.with(this).destory();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private void getIsBindPhone(){
      //  EasyProgressDialog.with(this).showProgress();
        String url =  DataCenter.getInstance().getIp() + ConstantValue.FIND_PASSWOR_IS_BIND_PHONE;
        OkHttpClient.Builder builder = getOkHttpClientBuilderForHttps();

        RequestBody body = new FormBody.Builder()
                .add("username", name)
                .build();

        Request request = new Request.Builder().url(url)
                .headers(Headers.of(NetUtil.setHeaders()))
                .post(body)
                .build();

        Call call = builder.build().newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                   //     dissmossDailog();
                        ToastUtil.showToastLong(SecurityCenterActivity.this,e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              //  dissmossDailog();
                IsBindUserPhoneBean isBindUserPhoneBean;
                isBindUserPhoneBean = new Gson().fromJson(response.body().string(), IsBindUserPhoneBean.class);
                IsBindUserPhoneBean.Data data = isBindUserPhoneBean.getData();
//                startActivity(new Intent(SecurityCenterActivity.this,ALreadyBindPhoneActivity.class)
//                        .putExtra(ALreadyBindPhoneActivity.ENCRYPTEDID,isBindUserPhoneBean.getData().getEncryptedId()));
                if (!"null".equals(data)&&data!=null&&!TextUtils.isEmpty(data.getPhone())){
                    //绑定了
                    startActivity(new Intent(SecurityCenterActivity.this,ALreadyBindPhoneActivity.class)
                    .putExtra(ALreadyBindPhoneActivity.ENCRYPTEDID,isBindUserPhoneBean.getData().getEncryptedId()));
                }else {
                    //未绑定
                    startActivity(new Intent(SecurityCenterActivity.this,RegisterUnBindPhoneActtivity.class)
                    .putExtra(RegisterUnBindPhoneActtivity.ENCRYPTEDID,isBindUserPhoneBean.getData().getEncryptedId()));
                }

            }
        });

    }

//    private void dissmossDailog(){
//        EasyProgressDialog.with(SecurityCenterActivity.this).dismissProgress();
//    }


    @Override
    public void isOpenResult(Object o) {
        IsOpenPwdByPhoneBean isOpenPwdByPhoneBean = (IsOpenPwdByPhoneBean)o;
        if ("1".equals(isOpenPwdByPhoneBean.getData())){
            modifySecurityBindPhone.setVisibility(View.VISIBLE);
        }else {
            modifySecurityBindPhone.setVisibility(View.GONE);
        }
    }
}
