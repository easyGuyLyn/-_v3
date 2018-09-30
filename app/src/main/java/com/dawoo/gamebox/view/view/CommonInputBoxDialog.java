package com.dawoo.gamebox.view.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.dawoo.gamebox.R;
import com.dawoo.gamebox.view.activity.AddBankCardActivity;
import com.dawoo.gamebox.view.activity.WithdrawMoneyActivity;

/**
 * Created by b on 18-1-30.
 */

public class CommonInputBoxDialog extends Dialog{

    private Context mContext;
    private PayPsdInputView mMEtOriginPwd;

    public CommonInputBoxDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_common_input);
        initView();
    }

    private void initView() {
        mMEtOriginPwd = findViewById(R.id.et_origin_pwd);
        mMEtOriginPwd.setFocusable(true);
        mMEtOriginPwd.setFocusableInTouchMode(true);
        mMEtOriginPwd.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        mMEtOriginPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }
            }
        });
        this.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public void setOkClick(View.OnClickListener onClickListener){
        this.findViewById(R.id.ok_).setOnClickListener(onClickListener);
    }

    public String getOriginPwd(){
        String pwd = mMEtOriginPwd.getText().toString().trim();
        return pwd;
    }
}
