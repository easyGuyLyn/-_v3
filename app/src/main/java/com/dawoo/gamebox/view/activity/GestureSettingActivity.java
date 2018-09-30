package com.dawoo.gamebox.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.dawoo.gamebox.R;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.view.view.HeaderView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Archar on 2018
 */
public class GestureSettingActivity extends BaseActivity {
    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.iv_hand_switch)
    Switch mIvHandSwitch;
    @BindView(R.id.ll_setting_hand)
    LinearLayout mLlSettingHand;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_gresture_setting);
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader(getString(R.string.gesture_pwd_setting), true);
    }

    @Override
    protected void initData() {
        if (SharePreferenceUtil.getGestureFlag()) {
            mLlSettingHand.setVisibility(View.VISIBLE);
            mIvHandSwitch.setChecked(true);
        } else {
            mLlSettingHand.setVisibility(View.GONE);
            mIvHandSwitch.setChecked(false);
        }
    }


    @OnClick({R.id.iv_hand_switch, R.id.ll_setting_hand})
    public void onViewClicked(View view) {
        SoundUtil.getInstance().playVoiceOnclick();
        switch (view.getId()) {
            case R.id.iv_hand_switch:
                Intent intent = new Intent(GestureSettingActivity.this, GestureActivity.class);
                if (SharePreferenceUtil.getGestureFlag()) {
                    intent.putExtra(GestureActivity.GEST_FLAG, GestureActivity.CLEAR_PWD);
                } else {
                    intent.putExtra(GestureActivity.GEST_FLAG, GestureActivity.SET_PWD);
                }
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_setting_hand:
                Intent intentSettingPwd = new Intent(GestureSettingActivity.this, GestureActivity.class);
                intentSettingPwd.putExtra(GestureActivity.GEST_FLAG, GestureActivity.RESET_PWD);
                startActivityForResult(intentSettingPwd, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            initData();
        }
    }
}
