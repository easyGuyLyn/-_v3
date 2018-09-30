package com.dawoo.gamebox.view.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dawoo.gamebox.R;


/**
 * Created by jack on 18-1-26.
 */

public class IcPropmtDialog extends AlertDialog {
    private TextView msg_title;
    private Button btn_ok;
    private Button btn_cancle;
    private Context context;

    private CharSequence title = "是否退出";
    private CharSequence content;
    private boolean[] args = {true, true};
    private String[] btnNames = {"确定", "取消"};

    public IcPropmtDialog(Context context) {
        super(context);
        this.context = context;
    }

    public IcPropmtDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected IcPropmtDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_layout);
        msg_title = (TextView) findViewById(R.id.msg_title);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelListener == null)
                    dismiss();
                else {
                    onCancelListener.onClick(v);
                }
            }
        });
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(onClickListener);
        msg_title.setText(title);
        btn_ok.setText(btnNames[0]);
        btn_cancle.setText(btnNames[1]);
        btn_ok.setVisibility(args[0] ? View.VISIBLE : View.GONE);
        btn_cancle.setVisibility(args[1] ? View.VISIBLE : View.GONE);
    }

    public boolean[] getArgs() {
        return args;
    }

    public void setArgs(boolean[] args) {
        this.args = args;
    }

    public String[] getBtnNames() {
        return btnNames;
    }

    public void setBtnNames(String[] btnNames) {
        this.btnNames = btnNames;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        this.title = title;
    }


    @Override
    public void setMessage(CharSequence message) {
        super.setMessage(message);
        this.content = message;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void show() {
        super.show();
    }

    public void setOnClickListener(
            android.view.View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnCancelListener(android.view.View.OnClickListener onClickListener) {
        onCancelListener = onClickListener;
    }

    private android.view.View.OnClickListener onClickListener = new android.view.View.OnClickListener() {

        @Override
        public void onClick(View v) {
            dismiss();
        }

    };

    private android.view.View.OnClickListener onCancelListener = new android.view.View.OnClickListener() {

        @Override
        public void onClick(View v) {
            dismiss();
        }

    };
}
