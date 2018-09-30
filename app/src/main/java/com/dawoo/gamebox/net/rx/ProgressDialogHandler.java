package com.dawoo.gamebox.net.rx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.dawoo.gamebox.R;

public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private volatile ProgressDialog pd;

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog() {
        if (pd == null) {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    return;
                }
                if (((Activity) context).isDestroyed()) {
                    return;
                }
                pd = new ProgressDialog(context);
                pd.setMessage(context.getString(R.string.loading_tip));
                pd.setCancelable(cancelable);

                if (cancelable) {
                    pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            mProgressCancelListener.onCancelProgress();
                        }
                    });
                }
                if (!pd.isShowing() && pd != null) {
                    pd.show();
                }
            }
        }
    }

    private void dismissProgressDialog() {
        if (pd != null && context != null && pd.isShowing()) {
            if (context instanceof Activity) {

                if (((Activity) context).isFinishing()) {
                    pd = null;
                    return;
                }
                if (((Activity) context).isDestroyed()) {
                    pd = null;
                    return;
                }

                pd.dismiss();
                pd = null;

            }
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

}
