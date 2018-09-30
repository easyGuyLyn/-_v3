package com.dawoo.gamebox.view.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.dawoo.gamebox.R;

/**
 * Created by rain on 18-3-30.
 */

public class DepositSuccessDialog extends Dialog {
    Button recharge_history_bt, sure_bt;
    ImageView close_iv;

    public DepositSuccessDialog(@NonNull Context context) {
        super(context, R.style.CustomDialogStyle);
        initView();
    }

    void initView() {
        setContentView(R.layout.dialog_deposit_success_layout);
        setCancelable(false);
        Window win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        recharge_history_bt = findViewById(R.id.recharge_history_bt);
        sure_bt = findViewById(R.id.sure_bt);
        close_iv = findViewById(R.id.close_iv);
    }

    public void setCloseLinstener(View.OnClickListener closeLinstener) {
        close_iv.setOnClickListener(closeLinstener);
    }

    public void setRechargeLinstener(View.OnClickListener rechargeLinstener) {
        recharge_history_bt.setOnClickListener(rechargeLinstener);
    }

    public void setSureLinstener(View.OnClickListener sureLinstener) {
        sure_bt.setOnClickListener(sureLinstener);
    }

}
