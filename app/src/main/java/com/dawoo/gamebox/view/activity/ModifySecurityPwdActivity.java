package com.dawoo.gamebox.view.activity;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.ResetSecurityPwd;
import com.dawoo.gamebox.mvp.presenter.UserPresenter;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.IModifySecurityPwdView;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.view.HeaderView;
import com.hwangjr.rxbus.RxBus;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 修改安全密码
 */
public class ModifySecurityPwdActivity extends BaseActivity implements IModifySecurityPwdView, IBaseView {


    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.old_pwd_et)
    EditText mOldPwdEt;
    @BindView(R.id.real_name_et)
    EditText mRrealNameEt;
    @BindView(R.id.new_pwd_et)
    EditText mNewPwdEt;
    @BindView(R.id.confirm_pwd_et)
    EditText mConfirmPwdEt;
    @BindView(R.id.logout_btn)
    Button mLogoutBtn;
    @BindView(R.id.pwd_et)
    EditText mPwdEt;
    @BindView(R.id.etCaptcha)
    EditText mEtCaptcha;
    @BindView(R.id.ivCaptcha)
    ImageView mIvCaptcha;
    @BindView(R.id.rlCaptcha)
    RelativeLayout mRlCaptcha;
    private UserPresenter mPresenter;

    private int mUploadType = 1; //0 设置安全密码  1 修改安全密码
    private Boolean mNeedCapcha = false;
    private String mCachaCode = "";
    private String mFirstRealName = "";
    private Boolean mIsSucessSetRealName = false;
    private String mCapChaUrl;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_modify_security_pwd);
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader(getString(R.string.modify_security_pwd), true);
    }

    @Override
    protected void initData() {
        mPresenter = new UserPresenter(this, this);
        initSafePwd();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }

    @OnClick(R.id.logout_btn)
    public void onViewClicked() {
        SoundUtil.getInstance().playVoiceOnclick();
        doModify();
    }

    @OnClick(R.id.ivCaptcha)

    public void onCaptcha() {
        SoundUtil.getInstance().playVoiceOnclick();
        getCaptcha(mCapChaUrl);
    }


    @Override
    public void onInitResult(Object o) {
        ResetSecurityPwd resetSecurityPwd = (ResetSecurityPwd) o;
        if (resetSecurityPwd == null) return;
        if (resetSecurityPwd.getData() != null) {
            if (!resetSecurityPwd.getData().getHasPermissionPwd()) {
                ((ViewGroup) mOldPwdEt.getParent()).setVisibility(View.GONE);
                ((ViewGroup) mRrealNameEt.getParent()).setVisibility(View.GONE);
                ((ViewGroup) mNewPwdEt.getParent()).setVisibility(View.GONE);
                ((ViewGroup) mPwdEt.getParent()).setVisibility(View.VISIBLE);
                ((ViewGroup) mConfirmPwdEt.getParent()).setVisibility(View.VISIBLE);
                mHeadView.setHeader(getString(R.string.set_security_pwd), true);
                mUploadType = 0;
                if (!resetSecurityPwd.getData().getHasRealName()) {
                    setRealName();
                }
            } else if (!resetSecurityPwd.getData().getHasRealName()) {
                setRealName();
            }
        }
    }


    /**
     * 设置真实姓名返回
     *
     * @param o
     */
    @Override
    public void onSetRealNameResult(Object o) {
        ResetSecurityPwd resetSecurityPwd = (ResetSecurityPwd) o;
        if (resetSecurityPwd == null) {
            mPresenter.dimissRealNameDialog();
            return;
        }
        mPresenter.dimissRealNameDialog();
        if (resetSecurityPwd.getCode().equals("0")) {

            if ("请求成功".equals(resetSecurityPwd.getMessage())) {
                ToastUtil.showToastShort(this, getString(R.string.action_success));
            } else {
                ToastUtil.showToastShort(this, resetSecurityPwd.getMessage());
            }


            if (!TextUtils.isEmpty(mFirstRealName) && TextUtils.isEmpty(DataCenter.getInstance().getRealName())) {
                DataCenter.getInstance().setRealName(mFirstRealName);
            }
        } else {
            mIsSucessSetRealName = false;
        }
    }

    @Override
    public void onModifyResult(Object o) {
        ResetSecurityPwd resetSecurityPwd = (ResetSecurityPwd) o;
        if (resetSecurityPwd == null) return;
        if (mUploadType == 0) {
            if (resetSecurityPwd.getCode().equals("0")){
                if (!TextUtils.isEmpty(mFirstRealName) && TextUtils.isEmpty(DataCenter.getInstance().getRealName())) {
                    DataCenter.getInstance().setRealName(mFirstRealName);
                }
                ToastUtil.showToastShort(this, getString(R.string.action_success));
                finish();

            }else {
                ToastUtil.showToastLong(this,resetSecurityPwd.getMessage());

            }
        }
        if (mUploadType == 1) {
            if (resetSecurityPwd.getData() != null) {
                if (null != resetSecurityPwd.getData().getIsOpenCaptcha()
                        && resetSecurityPwd.getData().getIsOpenCaptcha().equals("true")) {
                    mRlCaptcha.setVisibility(View.VISIBLE);
                    mNeedCapcha = true;
                    mCapChaUrl = resetSecurityPwd.getData().getCaptChaUrl();
                    getCaptcha(mCapChaUrl);
                }
            }
            if (resetSecurityPwd.getCode().equals("1308")) {
                mRlCaptcha.setVisibility(View.VISIBLE);
                mNeedCapcha = true;
                mCapChaUrl = resetSecurityPwd.getData().getCaptChaUrl();
                getCaptcha(mCapChaUrl);
            }
            if (resetSecurityPwd.getCode().equals("0")) {
                if (!TextUtils.isEmpty(mFirstRealName) && TextUtils.isEmpty(DataCenter.getInstance().getRealName())) {
                    DataCenter.getInstance().setRealName(mFirstRealName);
                }
                ToastUtil.showToastShort(this, getString(R.string.action_success));
                finish();
            } else if ("1303".equals(resetSecurityPwd.getCode())) {
                // 原始密码有误 remindTimes 4
                ResetSecurityPwd.UpdateLoginData data = resetSecurityPwd.getData();
                if (data == null) {
                    return;
                }
                if (0 != data.getRemindTimes()) {
                    ToastUtil.showToastShort(this, resetSecurityPwd.getMessage() + ",你还有" + data.getRemindTimes() + "次机会");
                } else {
                    // 跳转客服
                    ToastUtil.showToastLong(this, "余额已被冻结，请联系客服找回~");
                    ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
                    RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_SERVICE, "gotoService");
                }

            } else {

                if ("请求成功".equals(resetSecurityPwd.getMessage())) {
                    ToastUtil.showToastShort(this, getString(R.string.action_success));
                } else {
                    ToastUtil.showToastShort(this, resetSecurityPwd.getMessage());
                }

            }
        }
        if (resetSecurityPwd.getCode().equals("1001")) {
            ActivityUtil.gotoLogin();
        }
    }

    @Override
    public void doModify() {
        if (mUploadType == 0) {
            mFirstRealName = DataCenter.getInstance().getRealName();
            if (TextUtils.isEmpty(mFirstRealName)) {
                ToastUtil.showToastShort(this, getString(R.string.set_real_name_error));
                mPresenter.setRealNameDialog();
                return;
            }
            String firstPwd = mPwdEt.getText().toString().trim();
            String confirmpwd = mConfirmPwdEt.getText().toString().trim();
            if (validate(firstPwd, confirmpwd)) {
                mPresenter.modifySecurityPwd(mNeedCapcha, mFirstRealName, "", firstPwd, confirmpwd, mCachaCode);
            }
        } else if (mUploadType == 1) {
            String realName = mRrealNameEt.getText().toString().trim();
            String oldPwd = mOldPwdEt.getText().toString().trim();
            String newPwd = mNewPwdEt.getText().toString().trim();
            String confirmpwd = mConfirmPwdEt.getText().toString().trim();
            mCachaCode = mEtCaptcha.getText().toString().trim();

            if (validate(realName, oldPwd, newPwd, confirmpwd)) {
                mPresenter.modifySecurityPwd(mNeedCapcha, realName, oldPwd, newPwd, confirmpwd, mCachaCode);
            }
        }
    }

    @Override
    public void initSafePwd() {
        mPresenter.initSecurityPwd();
    }

    @Override
    public void setRealName() {
        mPresenter.setRealNameDialog();
    }

    @Override
    public void backRealName(String name) {
        mFirstRealName = name;
    }

    private boolean validate(String realName, String oldPwd, String newPwd, String confirmpwd) {
        if (TextUtils.isEmpty(realName) || TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(confirmpwd)) {
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

        return true;
    }

    private boolean validate(String newPwd, String confirmpwd) {
        if (TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(confirmpwd)) {
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

        return true;
    }

    //获取验证码
    private void getCaptcha(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(url)) return;
                OkHttpUtils.get().url(DataCenter.getInstance().getIp() + url).addParams("_t", String.valueOf(new Date().getTime()))
                        .headers(NetUtil.setHeaders()).build().execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e("captcha error ==> " + e.getMessage());
                        ToastUtil.showResShort(ModifySecurityPwdActivity.this, R.string.getCaptchaFail);
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
