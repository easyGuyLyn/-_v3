package com.dawoo.gamebox.view.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.dawoo.gamebox.R;


public class CustomProgressDialog extends Dialog {

    private final TextView mTvMsg;
    private boolean mIsDeFaultText;

    public CustomProgressDialog(Context context) {
        this(context, "");
    }


    public CustomProgressDialog(Context context, String strMessage) {
        this(context, R.style.CustomProgressDialog, strMessage);
    }


    public CustomProgressDialog(Context context, int theme, String strMessage) {
        super(context, theme);
        this.setContentView(R.layout.custom_progress_dialog);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        mTvMsg = findViewById(R.id.tvLoading);
        setLoadingText(strMessage);
    }

    public void setCancleAble(boolean cancleAble){
        setCancelable(cancleAble);
    }

    public void setDeFaultText(boolean b) {
        mIsDeFaultText = b;
    }

    public void setLoadingText(String loadingText) {
        if (!mIsDeFaultText && mTvMsg != null && !TextUtils.isEmpty(loadingText)) {
            mTvMsg.setText(loadingText);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            dismiss();
        }
    }

}  