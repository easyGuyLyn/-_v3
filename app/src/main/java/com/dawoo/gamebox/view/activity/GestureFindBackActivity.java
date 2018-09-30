package com.dawoo.gamebox.view.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dawoo.coretool.util.CleanLeakUtils;
import com.dawoo.coretool.util.LogUtils;
import com.dawoo.coretool.util.SPTool;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SingleToast;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.view.HeaderView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.dawoo.gamebox.view.activity.GestureActivity.GEST_FLAG;
import static com.dawoo.gamebox.view.activity.GestureActivity.SET_PWD;

/**
 * Created by Archar on 2018
 */
public class GestureFindBackActivity extends BaseActivity {
    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.tv_tip_user)
    TextView mTvTipUser;
    @BindView(R.id.etPassword)
    EditText mEtPassword;

    private int mErrorTime = 0;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_find_gesture_pwd);
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader(getString(R.string.gesture_pwd_find_back), true);
    }

    @Override
    protected void initData() {
        String userName = DataCenter.getInstance().getUserName();
        int cut = 0;
        if (userName.length() > 2) {
            cut = 2;
        }
        mTvTipUser.setText(getString(R.string.tip_uesr,
                userName.substring((userName.length() - cut))));
    }


    @Override
    protected void onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this);
        super.onDestroy();
    }


    @OnClick({R.id.b_next, R.id.forget_pwd_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.b_next:
                SoundUtil.getInstance().playVoiceOnclick();
                if (TextUtils.isEmpty(mEtPassword.getText().toString())) {
                    SingleToast.showMsg(getString(R.string.pleause_input_pwd));
                    return;
                }
                if (mEtPassword.getText().toString().equals(DataCenter.getInstance().getPassword())) {
                    Intent intent = new Intent(GestureFindBackActivity.this, GestureActivity.class);
                    intent.removeExtra(GEST_FLAG);
                    intent.putExtra(GEST_FLAG, SET_PWD);
                    startActivity(intent);
                } else {
                    mErrorTime++;
                    SingleToast.showMsg(getString(R.string.pwd_error, (5 - mErrorTime) + ""));
                }
                if (mErrorTime >= 5) {
                    SingleToast.showMsg(getString(R.string.pwd_error_5));
                    DataCenter.getInstance().setLogin(false);
                    DataCenter.getInstance().setCookie("");
                    DataCenter.getInstance().setUserName("");
                    DataCenter.getInstance().setPassword("");
                    SPTool.remove(BoxApplication.getContext(), ConstantValue.KEY_PASSWORD_AUTO_LOGIN);
                    Intent intent = new Intent(GestureFindBackActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                break;
            case R.id.forget_pwd_tv:
                SoundUtil.getInstance().playVoiceOnclick();
                String customUrl = (String) SPTool.get(BoxApplication.getContext(), ConstantValue.KEY_CUSTOMER_SERVICE, "");
                if (!TextUtils.isEmpty(customUrl)) {
                    ActivityUtil.startWebView(NetUtil.handleUrl(customUrl), "", ConstantValue.WEBVIEW_TYPE_THIRD_ORDINARY,1,"");
                } else {
                    getService();
                }
                break;
        }
    }

    /**
     * 获取客服地址
     */
    private void getService() {
        OkHttpUtils.get().url(DataCenter.getInstance().getIp() + ConstantValue.SERVICE_URL)
                .headers(NetUtil.setHeaders()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e("getService error ==> " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.e("getService ==> " + response);
                SPTool.remove(getApplicationContext(), ConstantValue.KEY_CUSTOMER_SERVICE);
                SPTool.put(getApplicationContext(), ConstantValue.KEY_CUSTOMER_SERVICE, response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ActivityUtil.startWebView(NetUtil.handleUrl(response), "", ConstantValue.WEBVIEW_TYPE_THIRD_ORDINARY,1,"");
                    }
                });
            }
        });
    }
}
