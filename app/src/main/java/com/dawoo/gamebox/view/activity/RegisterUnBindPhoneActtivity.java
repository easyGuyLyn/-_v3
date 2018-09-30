package com.dawoo.gamebox.view.activity;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.RegisterBean;
import com.dawoo.gamebox.bean.RegisterInfoBean;
import com.dawoo.gamebox.mvp.presenter.RegisterPresenter;
import com.dawoo.gamebox.mvp.presenter.UserPresenter;
import com.dawoo.gamebox.mvp.view.BindUserPhoneView;
import com.dawoo.gamebox.mvp.view.IRegisterView;
import com.dawoo.gamebox.net.HttpResult;
import com.dawoo.gamebox.util.DepositUtil;
import com.dawoo.gamebox.util.MyCountDownTimer;
import com.dawoo.gamebox.view.view.CustomProgressDialog;
import com.dawoo.gamebox.view.view.HeaderView;
import com.hwangjr.rxbus.RxBus;
import butterknife.BindView;
import butterknife.OnClick;

public class RegisterUnBindPhoneActtivity extends BaseActivity implements BindUserPhoneView,IRegisterView {
    @BindView(R.id.head_view)
    HeaderView headView;
    @BindView(R.id.img_phone)
    ImageView imgPhone;
    @BindView(R.id.ll_content_service)
    RelativeLayout llContentService;
    @BindView(R.id.img_verification_code)
    ImageView imgVerificationCode;
    @BindView(R.id.tv_btn_code)
    TextView tvBtnCode;
    @BindView(R.id.ll_verification_code)
    RelativeLayout llVerificationCode;
    @BindView(R.id.bt_next)
    Button btNext;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.et_enter_phone)
    EditText etEnterPhone;
    @BindView(R.id.et_enter_code)
    EditText etEnterCode;

    private CustomProgressDialog customProgressDialog;
    private MyCountDownTimer myCountDownTimer;
    private UserPresenter userPresenter;
    private RegisterPresenter registerPresenter;

    private String code;
    private String phone;
    private String encryptedId;

    public static final String ENCRYPTEDID = "encryptedId";


    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_register_unbind_phone);
    }

    @Override
    protected void initViews() {
        headView.setHeader("手机绑定", true);
        customProgressDialog = new CustomProgressDialog(RegisterUnBindPhoneActtivity.this);
        userPresenter = new UserPresenter(this,this);
        registerPresenter = new RegisterPresenter(this,this);
        myCountDownTimer = new MyCountDownTimer(tvBtnCode, 90000, 1000);

    }

    @Override
    protected void initData() {
        encryptedId = getIntent().getStringExtra(ENCRYPTEDID);
        changeNote();
    }


    @OnClick({R.id.ll_content_service, R.id.tv_btn_code, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_content_service:
                break;
            case R.id.tv_btn_code:
                sendMessageCode();
                break;
            case R.id.bt_next:
                trySumbit();
                break;
        }
    }


    private void trySumbit() {
        code = etEnterCode.getText().toString().trim();
        phone = etEnterPhone.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showToastLong(this, "请输入验证码！");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showToastLong(this, "请输入手机号码！");
            return;
        }
        bindPhone();
    }


    //发送手机验证码
    private void sendMessageCode() {
        if (TextUtils.isEmpty(etEnterPhone.getText().toString().trim())){
            ToastUtil.showToastLong(this,"请输入手机号码！");
            return;
        }
        registerPresenter.getSms(tvBtnCode,etEnterPhone.getText().toString().trim());
    }


    //绑定手机号
    private void bindPhone() {
        userPresenter.binUserPhone(phone,code,"");
    }

    void changeNote() {
        String notes = "";
        notes = DepositUtil.getNoteByCode("registerBindPhone", false, 2, true);
        notes = notes.replaceAll("<br>", "\n");
        SpannableStringBuilder mspan = new SpannableStringBuilder("温馨提示：\n" + notes);
        if (notes.contains("客服")) {
            SpannableString colorSpan = new SpannableString("点击联系在线客服");
            colorSpan.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(RegisterUnBindPhoneActtivity.this.getResources().getColor(R.color.colorPrimary));
                    ds.setUnderlineText(false);
                    ds.clearShadowLayer();
                }

                @Override
                public void onClick(View widget) {
                    ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
                    RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_SERVICE, "gotoService");
                }
            }, 0, colorSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mspan.append(colorSpan);
        }
        tvTips.setText(mspan);
        tvTips.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void bindUserPhoneSuccess(Object O) {
        HttpResult httpResult = (HttpResult) O;
        if (httpResult.getCode() == 0){
            myCountDownTimer.finish();
            //跳转页面
            ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
            RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_MINE, "gotoMine");
            return;
        }
        ToastUtil.showToastLong(this,httpResult.getMessage());
    }

    @Override
    public void validationCode(Object O) {

    }

    @Override
    public void getRegisterInfoSuccess(RegisterInfoBean registerInfoBean) {

    }

    @Override
    public void registerSuccess(RegisterBean registerBean) {

    }

    @Override
    public void registerError(String error) {

    }
}
