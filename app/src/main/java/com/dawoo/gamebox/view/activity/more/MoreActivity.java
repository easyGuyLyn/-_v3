package com.dawoo.gamebox.view.activity.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.util.SharePreferenceUtil;
import com.dawoo.gamebox.util.SingleToast;
import com.dawoo.gamebox.util.SoundUtil;
import com.dawoo.gamebox.util.cache.CacheUtils;
import com.dawoo.gamebox.util.cache.GlideCacheUtil;
import com.dawoo.gamebox.view.activity.BaseActivity;
import com.dawoo.gamebox.view.view.HeaderView;
import com.guoqi.iosdialog.IOSDialog;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.tencent.smtt.sdk.WebView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 查看更多
 */
public class MoreActivity extends BaseActivity {


    @BindView(R.id.head_view)
    HeaderView mHeadView;
    @BindView(R.id.common_problems_rl)
    RelativeLayout mCommonProblemsRl;
    @BindView(R.id.register_protocol_rl)
    RelativeLayout mRegisterProtocolRl;
    @BindView(R.id.about_us_rl)
    RelativeLayout mAboutUsRl;
    @BindView(R.id.luanguage_choice_rl)
    RelativeLayout mLuanguageChoiceRl;
    @BindView(R.id.tv_cache_size)
    TextView mTvCacheSize;

    private WebView mWebview;

    @Override
    protected void createLayoutView() {
        setContentView(R.layout.activity_more);
        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
        if (mWebview != null) {
            mWebview.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        mHeadView.setHeader(getString(R.string.more_acitivity), true);
    }

    @Override
    protected void initData() {
        initCacheSize("");
        mWebview = new WebView(this);
    }


    @OnClick({R.id.common_problems_rl, R.id.register_protocol_rl, R.id.about_us_rl, R.id.luanguage_choice_rl})
    public void onViewClicked(View view) {
        SoundUtil.getInstance().playVoiceOnclick();
        switch (view.getId()) {
            case R.id.common_problems_rl: {
                startActivity(new Intent(this, ProblemsActivity.class));
                break;
            }
            case R.id.register_protocol_rl: {
                startActivity(new Intent(this, RegisterTermsActivity.class));
                break;
            }
            case R.id.about_us_rl: {
                startActivity(new Intent(this, AboutActivity.class));
                break;
            }
            case R.id.luanguage_choice_rl:
                ToastUtil.showResShort(this, R.string.function_to_be_continue);
                break;
            default:
        }
    }


    @Subscribe(tags = {@Tag(ConstantValue.EVENT_CLEAR_CACHE)})
    public void initCacheSize(String s) {
        mTvCacheSize.setText(getAllCache());
        SharePreferenceUtil.saveDomain(this, "");
        SharePreferenceUtil.saveIp(this, "");
        SharePreferenceUtil.saveLTDomain(this, "");
    }

    @OnClick(R.id.setting_clear_cache)
    public void onViewClicked() {
        SoundUtil.getInstance().playVoiceOnclick();
        if (getAllCache().equals("0.0Byte")) {
            SingleToast.showMsg("缓存已清除~");
            return;
        }
        final IOSDialog alertDialog = new IOSDialog(this);
        alertDialog.builder();
        alertDialog.setCancelable(true);
        alertDialog.setMsg("是否确定清除缓存?");
        alertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundUtil.getInstance().playVoiceOnclick();
                CacheUtils.getInstance().clear();
                if (mWebview != null) {
                    mWebview.clearCache(true);
                }
                GlideCacheUtil.getInstance().clearImageDiskCache(MoreActivity.this);
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
            glideCache = GlideCacheUtil.getInstance().getFolderSize(new File(getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR))
                    + CacheUtils.getInstance().getCacheSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlideCacheUtil.getInstance().getFormatSize(glideCache);
    }

}
