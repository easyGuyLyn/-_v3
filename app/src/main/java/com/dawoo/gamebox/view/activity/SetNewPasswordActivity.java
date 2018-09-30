package com.dawoo.gamebox.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dawoo.coretool.util.SPTool;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.IsBindUserPhoneBean;
import com.dawoo.gamebox.mvp.presenter.UserPresenter;
import com.dawoo.gamebox.mvp.view.BindUserPhoneView;
import com.dawoo.gamebox.mvp.view.SetNewPasswordView;
import com.dawoo.gamebox.net.HttpResult;
import com.dawoo.gamebox.net.TlsSniSocketFactory;
import com.dawoo.gamebox.net.TrueHostnameVerifier;
import com.dawoo.gamebox.util.RexUtils;
import com.dawoo.gamebox.util.SSLUtil;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.view.view.CustomProgressDialog;
import com.dawoo.gamebox.view.view.HeaderView;
import com.dawoo.gamebox.view.view.SetNewPaswSuccesDialog;
import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SetNewPasswordActivity extends BaseActivity implements SetNewPasswordView {
    @BindView(R.id.head_view)
    HeaderView headView;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.tv_makesure_password)
    TextView tvMakesurePassword;
    @BindView(R.id.et_makesure_password)
    EditText etMakesurePassword;
    @BindView(R.id.btnNext)
    Button btnNext;

    public static final String USER_NAME ="username";
    private String newPsw;

    private CustomProgressDialog customProgressDialog;
    private UserPresenter userPresenter;

    private String userNmae;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_set_new_password);
    }

    @Override
    protected void initViews() {
        headView.setHeader("设置登录密码",true);

    }

    @Override
    protected void initData() {
        userPresenter = new UserPresenter(this,this);
        customProgressDialog = new CustomProgressDialog(SetNewPasswordActivity.this);
        userNmae = SharePreferenceUtil.getUserName();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_password, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_password:
                break;
            case R.id.btnNext:
                trySubmit();
                break;
        }
    }

    private void trySubmit(){
        newPsw = etNewPassword.getText().toString().trim();
        String newPswSure = etMakesurePassword.getText().toString().trim();
        if (TextUtils.isEmpty(userNmae)){
            ToastUtil.showToastLong(this,"账号为空！");
            return;
        }
        if (TextUtils.isEmpty(newPsw)){
            ToastUtil.showToastLong(this,"请输入密码！");
            return;
        }
        if (TextUtils.isEmpty(newPswSure)){
            ToastUtil.showToastLong(this,"请输入密码！");
            return;
        }
        if (!newPsw.equals(newPswSure)){
            ToastUtil.showToastLong(this,"请前后密码保持一致！");
            return;
        }

        if (!RexUtils.isLoginPWD(newPsw)){
            ToastUtil.showToastShort(this, getString(R.string.bad_account_code));
            return;

        }
        userPresenter.modifyLoginPassword(userNmae,newPsw);
    }


    private void showDialog(){

        SetNewPaswSuccesDialog setNewPaswSuccesDialog = new SetNewPaswSuccesDialog(SetNewPasswordActivity.this);
        setNewPaswSuccesDialog.show();
        setNewPaswSuccesDialog.setCloseLinstener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetNewPasswordActivity.this, LoginActivity.class));
            }
        });

    }

    @Override
    public void modifyLoginPassword(Object O) {
        IsBindUserPhoneBean isBindUserPhoneBean = (IsBindUserPhoneBean) O;
        if (isBindUserPhoneBean.getCode() == 0){
            //返回自动登录
            DataCenter.getInstance().getUser().setUsername(userNmae);
            DataCenter.getInstance().getUser().setPassword(newPsw);
            SPTool.put(this, ConstantValue.KEY_REMEMBER_PWD, true);
            SPTool.put(this, ConstantValue.KEY_USERNAME, userNmae);
            SPTool.put(this, ConstantValue.KEY_PASSWORD, newPsw);
            setResult(ConstantValue.KEY_FIND_PASSWORD);
            showDialog();
            return;
        }
      ToastUtil.showToastLong(this,isBindUserPhoneBean.getMessage());
    }

    @Override
    public void bindPhoneState(Object O) {

    }
}
