package com.dawoo.gamebox.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.IsOpenPwdByPhoneBean;
import com.dawoo.gamebox.mvp.presenter.UserPresenter;
import com.dawoo.gamebox.mvp.view.IsOpenPwdByPhoneView;
import com.dawoo.gamebox.view.view.HeaderView;
import com.hwangjr.rxbus.RxBus;
import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordSelecteWayActivity extends BaseActivity implements IsOpenPwdByPhoneView {
    @BindView(R.id.head_view)
    HeaderView headView;
    @BindView(R.id.ll_phone_find)
    LinearLayout llPhoneFind;
    @BindView(R.id.ll_content_service)
    LinearLayout llContentService;

    UserPresenter userPresenter;


    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_forget_password_selecte_way);
    }

    @Override
    protected void initViews() {
        headView.setHeader(getString(R.string.forget_password),true);
    }

    @Override
    protected void initData() {
        userPresenter = new UserPresenter(this,this);
        userPresenter.isOPenPwdByPhone();
    }


    @OnClick({R.id.ll_phone_find, R.id.ll_content_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_phone_find:
                startActivity(new Intent(ForgetPasswordSelecteWayActivity.this,ForgetPasswordEnterPasswordActivity.class));
                break;
            case R.id.ll_content_service:
                gotoCustomerPage();
                break;
        }
    }

    //跳转客服页面
    public void gotoCustomerPage() {
        LogUtils.d("gotoCustomerPage");
        ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
        RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_SERVICE, "gotoService");
    }


    @Override
    public void isOpenResult(Object o) {
        IsOpenPwdByPhoneBean isOpenPwdByPhoneBean = (IsOpenPwdByPhoneBean)o;
        if ("1".equals(isOpenPwdByPhoneBean.getData())){
         llPhoneFind.setVisibility(View.VISIBLE);
        }else {
            llPhoneFind.setVisibility(View.GONE);
        }
    }
}
