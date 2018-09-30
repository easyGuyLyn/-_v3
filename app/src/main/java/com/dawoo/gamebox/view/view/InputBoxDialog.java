package com.dawoo.gamebox.view.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dawoo.gamebox.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by b on 18-1-25.
 * 带输入框的dialog
 */

public class InputBoxDialog extends Dialog {

    @BindView(R.id.et_set_realName)
    public EditText mEtSetRealName;
    @BindView(R.id.cancel_)
    TextView mCancel;
    @BindView(R.id.ok_)
    TextView mOk;
    private Context mContext;
    private Unbinder mMBind;

    public InputBoxDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_input_box);
        mMBind = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setOkonClick(View.OnClickListener onClickListener){
        mOk.setOnClickListener(onClickListener);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mMBind.unbind();
    }
}
