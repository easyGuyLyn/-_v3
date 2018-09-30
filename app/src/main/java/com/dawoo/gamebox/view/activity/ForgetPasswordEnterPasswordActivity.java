package com.dawoo.gamebox.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.IsBindUserPhoneBean;
import com.dawoo.gamebox.net.TlsSniSocketFactory;
import com.dawoo.gamebox.net.TrueHostnameVerifier;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SSLUtil;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.view.view.CustomProgressDialog;
import com.dawoo.gamebox.view.view.HeaderView;
import com.google.gson.Gson;
import com.guoqi.iosdialog.IOSDialog;
import com.hwangjr.rxbus.RxBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.dawoo.gamebox.util.AutoLogin.CheckHasMainEnanceError;
import static com.dawoo.gamebox.util.NetUtil.getOkHttpClientBuilderForHttps;

public class ForgetPasswordEnterPasswordActivity extends BaseActivity {
    @BindView(R.id.head_view)
    HeaderView headView;
    @BindView(R.id.et_enter)
    EditText etEnter;
    @BindView(R.id.btnNext)
    Button btnNext;

    private CustomProgressDialog customProgressDialog;
    private String mIp;


    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_forget_password_enter_phone);

    }


    @Override
    protected void initViews() {

    }


    @Override
    protected void initData() {
        customProgressDialog = new CustomProgressDialog(ForgetPasswordEnterPasswordActivity.this);
        headView.setHeader(getString(R.string.forget_password), true);
        mIp = DataCenter.getInstance().getIp();
    }


    @OnClick(R.id.btnNext)
    public void onViewClicked() {
        String enterAccount = etEnter.getText().toString().trim();
        if (!TextUtils.isEmpty(enterAccount)) {
            SharePreferenceUtil.saveUserName(enterAccount);
            getIsBindPhone(enterAccount);
        } else {
            ToastUtil.showToastLong(this, "请输入正确的账号！");
        }
    }


    private void getIsBindPhone(String enterAccount) {
        customProgressDialog.show();
        String url = mIp + ConstantValue.FIND_PASSWOR_IS_BIND_PHONE;
        OkHttpClient.Builder builder = getOkHttpClientBuilderForHttps();

        RequestBody body = new FormBody.Builder()
                .add("username", enterAccount)
                .build();

        Request request = new Request.Builder().url(url)
                .headers(Headers.of(NetUtil.setHeaders()))
                .post(body)
                .build();

        Call call = builder.build().newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                customProgressDialog.dismiss();
                ToastUtil.showToastLong(ForgetPasswordEnterPasswordActivity.this, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                customProgressDialog.dismiss();
                if (!CheckHasMainEnanceError(response)) {
                    return;
                }
                IsBindUserPhoneBean isBindUserPhoneBean;
                isBindUserPhoneBean = new Gson().fromJson(response.body().string(), IsBindUserPhoneBean.class);
                IsBindUserPhoneBean.Data data = isBindUserPhoneBean.getData();
                if (!"null".equals(data) && data != null && !TextUtils.isEmpty(data.getPhone())) {
                    startActivity(new Intent(ForgetPasswordEnterPasswordActivity.this, ForgetPasEnterCodeActivity.class).putExtra(ForgetPasEnterCodeActivity.ENCRYPTEDID, data.getEncryptedId())
                            .putExtra(ForgetPasEnterCodeActivity.PHONE, data.getPhone()));
                } else {
                    showDialog();
                }

            }
        });

    }


    private void showDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final IOSDialog alertDialog = new IOSDialog(ForgetPasswordEnterPasswordActivity.this);
                alertDialog.builder();
                alertDialog.setCancelable(true);
                alertDialog.setTitle("温馨提示");
                alertDialog.setMsg("该账号未绑定手机号，请联系客服找回密码");
                alertDialog.setPositiveButton("联系客服", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoCustomerPage();
                    }
                });
                alertDialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });
    }


    //跳转客服页面
    public void gotoCustomerPage() {
        ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
        RxBus.get().post(ConstantValue.EVENT_TYPE_GOTOTAB_SERVICE, "gotoService");
    }

}
