package com.dawoo.gamebox.view.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dawoo.gamebox.R;
import com.dawoo.gamebox.view.view.numberprogressbar.NumberProgressBar;

import java.util.logging.Handler;

/**
 * Created by rain on 4/15/18.
 */

public class UpdateDailog extends Dialog {
    private boolean isForce;
    private TextView update_msg;
    private Button sure_bt;
    private Button cancel_bt;
    private ImageView mCloseBtn;
    private TextView tv_title;
    private NumberProgressBar mNumberProgressBar;
    private LinearLayout ll_progress;
    private LinearLayout ll_bottom;
    private Button b_install;
    private android.os.Handler mHandler = new android.os.Handler();

    public UpdateDailog(@NonNull Context context, boolean isForce) {
        super(context, R.style.CustomDialogStyle);
        this.isForce = isForce;
        initView();
    }


    public void setForce(boolean force) {
        isForce = force;
    }

    private void initView() {
        setContentView(R.layout.dialog_update_layout);
        tv_title = findViewById(R.id.tv_title);
        update_msg = findViewById(R.id.update_msg);
        mNumberProgressBar = findViewById(R.id.numberBar);
        sure_bt = findViewById(R.id.sure_bt);
        mCloseBtn = findViewById(R.id.close_iv);
        cancel_bt = findViewById(R.id.cancel_bt);
        ll_progress = findViewById(R.id.ll_progress);
        ll_bottom = findViewById(R.id.ll_bottom);
        b_install = findViewById(R.id.b_install);
        setCancelable(false);
        Window win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        if (isForce) {
            cancel_bt.setText("退出");
        }
    }

    public void refreashLeftButtonUI() {
        if (isForce) {
            cancel_bt.setText("退出");
        }
    }

    public void setMeg(String msg, View.OnClickListener onClickListener) {
        update_msg.setText(msg + "");
        sure_bt.setOnClickListener(onClickListener);
    }

    public void setCancel(View.OnClickListener onClickListener) {
        cancel_bt.setOnClickListener(onClickListener);
    }

    public void setInstall(View.OnClickListener onClickListener) {
        b_install.setOnClickListener(onClickListener);
    }

    public void setUIMode(int type) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (type == 0) {
                    tv_title.setText("下载安装包中...");
                    ll_progress.setVisibility(View.VISIBLE);
                    ll_bottom.setVisibility(View.GONE);
                } else if (type == 1) {
                    tv_title.setText("版本更新");
                    ll_progress.setVisibility(View.GONE);
                    ll_bottom.setVisibility(View.VISIBLE);
                    sure_bt.setText("下载更新");
                } else if (type == 3) {
                    tv_title.setText("版本更新");
                    ll_progress.setVisibility(View.GONE);
                    ll_bottom.setVisibility(View.VISIBLE);
                    sure_bt.setText("重新下载");
                }
            }
        });

    }

    public void setProgress(int progress) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                tv_title.setText("下载安装包中...");
                ll_progress.setVisibility(View.VISIBLE);
                ll_bottom.setVisibility(View.GONE);
                Log.e("pro__", progress + "");
                mNumberProgressBar.setProgress(progress);
                if (progress == 100) {
                    b_install.setVisibility(View.VISIBLE);
                    tv_title.setText("安装包已经就绪");
                } else {
                    b_install.setVisibility(View.GONE);
                }
            }
        });
    }
}
