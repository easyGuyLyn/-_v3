package com.dawoo.gamebox.view.activity;

import android.content.Intent;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.GetBindUserPhoneNumberBean;
import com.dawoo.gamebox.mvp.presenter.UserPresenter;
import com.dawoo.gamebox.mvp.view.AlreadyBindPhoneView;
import com.dawoo.gamebox.util.DepositUtil;
import com.dawoo.gamebox.view.view.HeaderView;
import com.hwangjr.rxbus.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ALreadyBindPhoneActivity extends BaseActivity implements AlreadyBindPhoneView {
    @BindView(R.id.head_view)
    HeaderView headView;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.btnChange)
    Button btnChange;
    @BindView(R.id.tv_tips)
    TextView tvTips;

    public static final String ENCRYPTEDID = "EncryptedId";


    private UserPresenter userPresenter;



    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_already_bind_phone);
    }

    @Override
    protected void initViews() {
        headView.setHeader("手机绑定",true);
        changeNote();

    }

    @Override
    protected void initData() {
        userPresenter = new UserPresenter(this,this);
        userPresenter.getUserBIndPhone();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnChange)
    public void onViewClicked() {
        startActivity(new Intent(this,BinNewPhoneActivity.class)
                .putExtra(ENCRYPTEDID,getIntent().getStringExtra(ENCRYPTEDID)));
    }

    @Override
    public void getPhoneNumber(Object O) {
        GetBindUserPhoneNumberBean rdsmsBean = (GetBindUserPhoneNumberBean) O;
        if (!TextUtils.isEmpty(rdsmsBean.getData())){
            tvPhone.setText(rdsmsBean.getData());
        }
    }

    void changeNote() {
        String notes = "";
        notes = DepositUtil.getNoteByCode("bindPhone", false, 2, true);
        notes = notes.replaceAll("<br>", "\n");
        SpannableStringBuilder mspan = new SpannableStringBuilder("温馨提示：\n" + notes);
        if (notes.contains("客服")) {
            SpannableString colorSpan = new SpannableString("点击联系在线客服");
            colorSpan.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ALreadyBindPhoneActivity.this.getResources().getColor(R.color.colorPrimary));
                    ds.setUnderlineText(false);
                    ds.clearShadowLayer();
                }

                @Override
                public void onClick(View widget) {
                   // RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_SERVICE, "gotoService");
                    ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
                    RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_SERVICE, "gotoService");
                }
            }, 0, colorSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mspan.append(colorSpan);
        }
        tvTips.setText(mspan);
        tvTips.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
