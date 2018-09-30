package com.dawoo.gamebox.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.IsBindUserPhoneBean;
import com.dawoo.gamebox.mvp.presenter.UserPresenter;
import com.dawoo.gamebox.mvp.view.BindUserPhoneView;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.MyCountDownTimer;
import com.dawoo.gamebox.view.view.CustomProgressDialog;
import com.dawoo.gamebox.view.view.HeaderView;
import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasEnterCodeActivity extends BaseActivity implements BindUserPhoneView {
    @BindView(R.id.head_view)
    HeaderView headView;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_btn_code)
    TextView tvBtnCode;
    @BindView(R.id.et_enter_code)
    EditText etEnterCode;
    @BindView(R.id.bt_next)
    Button btNext;

    String encryptedId;


    private CustomProgressDialog customProgressDialog;

    private String mIp;
    private String useId;
    private String phone;

    private int recLen;
    private String cookie;
    MyCountDownTimer myCountDownTimer;

    UserPresenter userPresenter;

    public static final String ENCRYPTEDID = "encryptedId";
    public static final String PHONE = "phone";

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_forgetpassword_get_phone_code);
    }

    @Override
    protected void initViews() {
        headView.setHeader("输入验证码", true);
        myCountDownTimer = new MyCountDownTimer(tvBtnCode, 90000, 1000);
        userPresenter = new UserPresenter(ForgetPasEnterCodeActivity.this, ForgetPasEnterCodeActivity.this);
    }

    @Override
    protected void initData() {
        customProgressDialog = new CustomProgressDialog(ForgetPasEnterCodeActivity.this);
        encryptedId = getIntent().getStringExtra(ENCRYPTEDID);
        mIp = DataCenter.getInstance().getIp();
        useId = getIntent().getStringExtra(ENCRYPTEDID);
        phone = getIntent().getStringExtra(PHONE);
        tvUserPhone.setText(phone);
        cookie = NetUtil.setHeaders().get("Cookie");
        if (cookie == null) {
            cookie = "";
        }
        sendMessageCode(useId);
    }

    @OnClick({R.id.tv_btn_code, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_btn_code:
                sendMessageCode(useId);
                break;
            case R.id.bt_next:
                validationCode();
                break;
        }
    }


    //发送手机验证码
    private void sendMessageCode(String encryptedId) {
        userPresenter.sendSms(tvBtnCode, encryptedId, myCountDownTimer,cookie);
    }


    //验证手机号码  验证码
    private void validationCode() {
        String code = etEnterCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showToastLong(this, "请输入验证码！");
            return;
        }
        userPresenter.validationCode(code);
    }


    @Override
    public void bindUserPhoneSuccess(Object O) {

    }

    @Override
    public void validationCode(Object O) {
        IsBindUserPhoneBean isBindUserPhoneBean = (IsBindUserPhoneBean) O;
        if (isBindUserPhoneBean.getCode() == 0) {
            startActivity(new Intent(ForgetPasEnterCodeActivity.this, SetNewPasswordActivity.class));
            return;
        }
      ToastUtil.showToastLong(this,isBindUserPhoneBean.getMessage());
    }
}
