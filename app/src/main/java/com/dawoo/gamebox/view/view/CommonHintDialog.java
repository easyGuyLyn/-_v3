package com.dawoo.gamebox.view.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.dawoo.gamebox.R;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.view.activity.AddBankCardActivity;
import com.dawoo.gamebox.view.activity.LoginActivity;
import com.dawoo.gamebox.view.activity.WithdrawMoneyActivity;

/**
 * Created by b on 18-1-15.
 * 双按鍵提示彈框
 */

public class CommonHintDialog extends Dialog{

    private Context mContext;
    OnCancelClickListener mCancelClickListener = null; //取消

    public CommonHintDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_common_hint);
        initView();
    }

    private void initView() {
        this.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelClickListener!=null){
                    mCancelClickListener.onClick(CommonHintDialog.this);
                }
                dismiss();
            }
        });
        this.findViewById(R.id.ok_).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddBankCardActivity.class);
                mContext.startActivity(intent);
                ((WithdrawMoneyActivity)mContext).finish();
            }
        });
    }
    public void setOkClick(View.OnClickListener onClickListener){
        this.findViewById(R.id.ok_).setOnClickListener(onClickListener);
    }
    public void setCancelClickListener(OnCancelClickListener listener) {
        mCancelClickListener = listener;
    }
    public interface OnCancelClickListener{
         void onClick(CommonHintDialog commonHintDialog);
    }
}
