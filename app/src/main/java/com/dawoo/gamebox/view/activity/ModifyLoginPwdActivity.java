package com.dawoo.gamebox.view.activity;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.UpdateLoginPwd;
import com.dawoo.gamebox.mvp.presenter.UserPresenter;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.IModifyLoginPwdView;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.view.HeaderView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 修改登录密码
 */
public class ModifyLoginPwdActivity extends BaseActivity implements IModifyLoginPwdView, IBaseView {


    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.old_pwd_et)
    EditText mOldPwdEt;
    @BindView(R.id.new_pwd_et)
    EditText mNewPwdEt;
    @BindView(R.id.confirm_pwd_et)
    EditText mConfirmPwdEt;
    @BindView(R.id.logout_btn)
    Button mLogoutBtn;
    @BindView(R.id.error_tv)
    TextView mErrorTv;
    @BindView(R.id.etCaptcha)
    EditText mEtCaptcha;
    @BindView(R.id.ivCaptcha)
    ImageView mIvCaptcha;
    @BindView(R.id.rlCaptcha)
    RelativeLayout mRlCaptcha;
    private UserPresenter mPresenter;
    private Boolean mIsNeedCheckCode = false;
    private int mCount = 0;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_modify_login_pwd);
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader(getString(R.string.modify_login_pwd), true);
    }

    @Override
    protected void initData() {
        mPresenter = new UserPresenter(this, this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }

    @OnClick({R.id.logout_btn, R.id.ivCaptcha})
    public void onViewClicked(View view) {
        SoundUtil.getInstance().playVoiceOnclick();
        switch (view.getId()) {
            case R.id.logout_btn:
                doModify();
                break;
            case R.id.ivCaptcha:
                getCaptcha();
                break;
        }

    }

    @Override
    public void onModifyResult(Object o) {
        UpdateLoginPwd updateLoginPwd = (UpdateLoginPwd) o;
        if (updateLoginPwd == null) {
            ToastUtil.showToastShort(this, getString(R.string.http_code_default));
            return;
        }
        if (updateLoginPwd.getCode() != 0) {
            ToastUtil.showToastShort(this, updateLoginPwd.getMessage());
        } else {
            ToastUtil.showToastShort(this, getString(R.string.action_success));
            finish();
        }
        if (updateLoginPwd.getData() != null && updateLoginPwd.getData().getIsOpenCaptcha() != null) {
            if (updateLoginPwd.getData().getIsOpenCaptcha().equals("true")) {
                mRlCaptcha.setVisibility(View.VISIBLE);
                mIsNeedCheckCode = true;
                getCaptcha();
            }
        }
        if (updateLoginPwd.getCode() == 1308) {
            mRlCaptcha.setVisibility(View.VISIBLE);
            mIsNeedCheckCode = true;
            getCaptcha();
        }

        if (updateLoginPwd.getCode() == 1001) {
            ActivityUtil.gotoLogin();
        }
    }

    @Override
    public void doModify() {
        mCount ++;

        if(mCount > 2) {
            mErrorTv.setVisibility(View.VISIBLE);
        }

        String oldPwd = mOldPwdEt.getText().toString().trim();
        String newPwd = mNewPwdEt.getText().toString().trim();
        String confirmpwd = mConfirmPwdEt.getText().toString().trim();
        String code = mEtCaptcha.getText().toString().trim();

        if (validate(oldPwd, newPwd, confirmpwd, code)) {
            if (mIsNeedCheckCode) {
                mPresenter.modifyLoginPwdWithCode(oldPwd, newPwd, code);
            } else {
                mPresenter.modifyLoginPwd(oldPwd, newPwd);
            }
        }
    }

    private boolean validate(String oldPwd, String newPwd, String confirmpwd, String code) {
        if (TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(confirmpwd)) {
            ToastUtil.showToastShort(this, getString(R.string.input_cant_null));
            return false;
        }

        if (newPwd.length() < 6) {
            ToastUtil.showToastShort(this, getString(R.string.pwd_at_lest_six));
            return false;
        }

        if (!newPwd.equals(confirmpwd)) {
            ToastUtil.showToastShort(this, getString(R.string.pwd_input_different));
            return false;
        }
        if (mIsNeedCheckCode) {
            if (TextUtils.isEmpty(code)) {
                ToastUtil.showToastShort(this, getString(R.string.captcha_hint));
                return false;
            }
        }
        return true;
    }

    //获取验证码
    private void getCaptcha() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.get().url(DataCenter.getInstance().getIp() + ConstantValue.CAPTCHA_URL).addParams("_t", String.valueOf(new Date().getTime()))
                        .headers(NetUtil.setHeaders()).build().execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e("captcha error ==> " + e.getMessage());
                        ToastUtil.showResShort(ModifyLoginPwdActivity.this, R.string.getCaptchaFail);
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mIvCaptcha != null)
                                    mIvCaptcha.setImageBitmap(response);
                            }
                        });
                    }
                });
            }
        }).start();
    }
}
