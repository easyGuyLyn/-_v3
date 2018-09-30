package com.dawoo.gamebox.view.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.dawoo.gamebox.R;

public class SetNewPaswSuccesDialog extends Dialog {
   ImageView close;
    private Context context;

    public SetNewPaswSuccesDialog(@NonNull Context context) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
        initView();
    }

    void initView() {
        setContentView(R.layout.dialog_set_password_succee);
        setCancelable(false);
        Window win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        close = findViewById(R.id.close_iv);

    }

    public void setCloseLinstener(View.OnClickListener closeLinstener) {
        close.setOnClickListener(closeLinstener);
    }

}
