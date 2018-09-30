package com.dawoo.gamebox.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.dawoo.coretool.util.CleanLeakUtils;
import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.util.BackGroundUtil;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.view.view.gesture.util.AlertUtil;
import com.dawoo.gamebox.view.view.gesture.widget.EasyGestureView;
import com.dawoo.gamebox.view.view.HeaderView;
import com.hwangjr.rxbus.RxBus;

import java.util.List;

import butterknife.BindView;

import static com.dawoo.gamebox.view.view.gesture.widget.EasyGestureView.STATE_REGISTER;

/**
 * Created by Archar on 2018
 */
public class GestureActivity extends BaseActivity implements EasyGestureView.GestureCallBack {
    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.gesture)
    EasyGestureView mGesture;


    public static final String GEST_FLAG = "gestureFlg";
    public static final int SET_PWD = 0;//初设手势密码
    public static final int CLEAR_PWD = 1;//清除手势密码
    public static final int RESET_PWD = 2;//重置手势密码时的验证
    public static final int CHECK_PWD = 3;//普通的检验手势密码
    public static final int CHECK_PWD_BACKGROUND = 4;//切回前台的检验手势密码

    private int mGeatureFlg = CHECK_PWD; //默认是检验手势密码

    private Boolean isCheckBackGroudSuccess = false; //专门用来检验切回前台的手势密码的成功

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_setting_pattern_psw);
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader(getString(R.string.gesture_pwd), true);
    }

    @Override
    protected void initData() {
        mGesture.setGestureCallBack(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras() != null) {
            mGeatureFlg = getIntent().getIntExtra(GEST_FLAG, CHECK_PWD);
        }
        SharedPreferences sp = getSharedPreferences("STATE_DATA", Activity.MODE_PRIVATE);
        if (sp.getInt("state", 0) == STATE_REGISTER) {
            mGeatureFlg = SET_PWD;
        }
        initCache();
    }

    private void initCache() {
        switch (mGeatureFlg) {
            case SET_PWD:
                mHeadView.setHeader(getString(R.string.set_gesture_pwd), true);
                mGesture.clearCache();
                break;
            case CLEAR_PWD:
                mHeadView.setHeader(getString(R.string.clear_gesture_pwd), true);
                mGesture.clearCacheLogin();
                break;
            case RESET_PWD:
                mHeadView.setHeader(getString(R.string.check_gesture_pwd), true);
                break;
            case CHECK_PWD:
                mHeadView.setHeader(getString(R.string.check_gesture_pwd), true);
                break;
            case CHECK_PWD_BACKGROUND:
                mHeadView.setHeader(getString(R.string.check_gesture_pwd), false);
                break;
        }
    }


    @Override
    public void gestureVerifySuccessListener(int stateFlag, List<EasyGestureView.GestureBean> data, boolean success) {

        if (stateFlag == EasyGestureView.STATE_FIND_PSD) {
            Log.e("gest", stateFlag + "");
            if (DataCenter.getInstance().isLogin()) {
                startActivity(new Intent(GestureActivity.this, GestureFindBackActivity.class));
            } else {
                Intent intent = new Intent(GestureActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            return;
        }
        switch (mGeatureFlg) {
            case SET_PWD:
                if (stateFlag == EasyGestureView.STATE_LOGIN) {
                    SharePreferenceUtil.putGestureFlag(true);
                    AlertUtil.t(getString(R.string.set_gesture_pwd_ok));
                    ActivityStackManager.getInstance().finishToActivity(MainActivity.class, true);
                }
                break;
            case CLEAR_PWD:
                if (!success) {
                    finish();
                    return;
                }
                //删除密码
                SharePreferenceUtil.putGestureFlag(false);
                mGesture.clearCache();
                AlertUtil.t(getString(R.string.clear_gesture_pwd_ok));
                finish();
                break;
            case RESET_PWD:
                if (!success) {
                    finish();
                    return;
                }
                //删除旧密码
                SharePreferenceUtil.putGestureFlag(false);
                mGesture.clearCache();
                AlertUtil.t(getString(R.string.check_gesture_pwd_ok_andReset));
                Intent intent = getIntent();
                intent.removeExtra(GEST_FLAG);
                intent.putExtra(GEST_FLAG, SET_PWD);
                startActivity(intent);
                finish();
                break;
            case CHECK_PWD:
                if (!success) {
                    finish();
                    return;
                }
                setResult(RESULT_OK);
                finish();
                break;
            case CHECK_PWD_BACKGROUND:
                if (!success) {
                    return;
                }
                BackGroundUtil.isShouldJumpGesture = false;
                isCheckBackGroudSuccess = true;
                finish();
                break;
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        if (mGeatureFlg == CHECK_PWD_BACKGROUND && !isCheckBackGroudSuccess) {
            BackGroundUtil.isShouldJumpGesture = false;
            ActivityStackManager.getInstance().finishAllActivity();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mGesture.onDestroy();
        CleanLeakUtils.fixInputMethodManagerLeak(this);
        super.onDestroy();
    }
}
