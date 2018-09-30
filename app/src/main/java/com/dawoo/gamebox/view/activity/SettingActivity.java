package com.dawoo.gamebox.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.Logout;
import com.dawoo.gamebox.mvp.presenter.UserPresenter;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.ILoginOutView;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.util.SingleToast;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.util.cache.GlideCacheUtil;
import com.dawoo.gamebox.view.view.HeaderView;
import com.guoqi.iosdialog.IOSDialog;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 设置
 */
public class SettingActivity extends BaseActivity implements ILoginOutView {

    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.voice_switch)
    Switch mVoiceSwitch;
    @BindView(R.id.setting_voice_rl)
    RelativeLayout mSettingVoiceRl;
    @BindView(R.id.gisture_switch)
    Switch mGistureSwitch;
    @BindView(R.id.setting_gisture_rl)
    RelativeLayout mSettingGistureRl;
    @BindView(R.id.logout_btn)
    Button mLogoutBtn;
    @BindView(R.id.tv_cache_size)
    TextView mTvCacheSize;

    private UserPresenter userPresenter;

    private Context context;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_setting);
        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        userPresenter.onDestory();
        RxBus.get().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        context = this;
        mHeadView.setHeader(getString(R.string.setting_acitivity), true);
        initGestureListen();
    }

    @Override
    protected void initData() {
        userPresenter = new UserPresenter(this, this);
        if (SharePreferenceUtil.getGestureFlag()) {
            mGistureSwitch.setChecked(true);
        } else {
            mGistureSwitch.setChecked(false);
        }

        if (SharePreferenceUtil.getVoiceStatus(context)) {
            mVoiceSwitch.setChecked(true);
        } else {
            mVoiceSwitch.setChecked(false);
        }


        mVoiceSwitch.setOnClickListener(v -> {
            if (mVoiceSwitch.isChecked()) {
                SoundUtil.getInstance().open();
            } else {
                SoundUtil.getInstance().close();
            }
        });
        initCacheSize("");
    }

    @Subscribe(tags = {@Tag(ConstantValue.EVENT_CLEAR_CACHE)})
    public void initCacheSize(String s) {
        mTvCacheSize.setText(getAllCache());
    }

    private void initGestureListen() {
        mGistureSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundUtil.getInstance().playVoiceOnclick();
                Intent intent = new Intent(SettingActivity.this, GestureActivity.class);
                if (SharePreferenceUtil.getGestureFlag()) {
                    intent.putExtra(GestureActivity.GEST_FLAG, GestureActivity.CLEAR_PWD);
                } else {
                    intent.putExtra(GestureActivity.GEST_FLAG, GestureActivity.SET_PWD);
                }
                startActivityForResult(intent, 1);
            }
        });
    }


    @OnClick(R.id.logout_btn)
    public void onViewClicked() {
        SoundUtil.getInstance().playVoiceOnclick();
        final IOSDialog alertDialog = new IOSDialog(context);
        alertDialog.builder();
        alertDialog.setCancelable(true);
        alertDialog.setMsg("是否确定退出账号?");
        alertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundUtil.getInstance().playVoiceOnclick();
                userPresenter.LoginOut();
                alertDialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundUtil.getInstance().playVoiceOnclick();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClickResult(Object o) {
        Logout logout = (Logout) o;
        if (logout.isSuccess()) {
            ActivityUtil.logout();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            initData();
        }
    }


    @OnClick(R.id.setting_clear_cache)
    public void onViewClicked1() {
        SoundUtil.getInstance().playVoiceOnclick();
        if (getAllCache().equals("0.0Byte")) {
            SingleToast.showMsg("缓存已清除~");
            return;
        }
        final IOSDialog alertDialog = new IOSDialog(context);
        alertDialog.builder();
        alertDialog.setCancelable(true);
        alertDialog.setMsg("是否确定清除缓存?");
        alertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundUtil.getInstance().playVoiceOnclick();
                GlideCacheUtil.getInstance().clearImageDiskCache(context);
                alertDialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundUtil.getInstance().playVoiceOnclick();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private String getAllCache() {
        long glideCache = 0;
        try {
            glideCache = GlideCacheUtil.getInstance().getFolderSize(new File(context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlideCacheUtil.getInstance().getFormatSize(glideCache);

    }

}
