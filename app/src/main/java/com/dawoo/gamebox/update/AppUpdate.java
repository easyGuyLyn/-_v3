package com.dawoo.gamebox.update;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.dawoo.coretool.util.activity.ActivityStackManager;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.BuildConfig;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.UpdateBean;
import com.dawoo.gamebox.util.NetTool;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SingleToast;
import com.dawoo.gamebox.util.line.UploadErrorLinesUtil;
import com.dawoo.gamebox.view.view.UpdateDailog;
import com.dawoo.pushsdk.util.GsonUtil;

import java.io.File;
import java.text.DecimalFormat;

import static com.dawoo.gamebox.update.UpdateTool.installApk;

/**
 * Created by fei on 2017/8/7.
 */
public class AppUpdate {

    private static final String TAG = "AppUpdate";
    private Activity activity;
    private UpdateBean mUpdateBean;
    private UpdateResult mUpdateResult;
    private UpdateDailog mDialog;
    private File mApk;


    public AppUpdate(Activity activity) {
        this.activity = activity;
        mDialog = new UpdateDailog(activity, false);
    }

    public interface UpdateResult {
        void callBack(boolean result);
    }

    public void setIsNeedUpdateCallBack(UpdateResult updateResult) {
        mUpdateResult = updateResult;
    }

    /**
     * 检查版本更新
     */
    public void checkUpdate() {
        UpdateTool.UpdateCallback updateCallback = new UpdateTool.UpdateCallback() {
            @Override
            public void onSuccess(UpdateBean updateInfo) {
                Log.e(TAG, "mUpdateBean.getForceVersion ==>  " + GsonUtil.GsonString(updateInfo));
                mUpdateBean = updateInfo;
                if (updateInfo.getVersionCode() <= BuildConfig.VERSION_CODE) {
                    if (mUpdateResult != null) {
                        mUpdateResult.callBack(true);
                    }
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDialog();
                        }
                    });
                }
            }

            @Override
            public void onError() {
                Log.e(TAG, "==> 暂无更新！");
                if (mUpdateResult != null) {
                    mUpdateResult.callBack(true);
                }
            }
        };

        // 版本更新秘钥（新）
        // String keyCode = ProperTool.getProperty(activity, ConstantValue.KEY_CODE);

        // 版本更新（新）
        UpdateTool.checkUpdate(activity, ConstantValue.APP_TYPE, UploadErrorLinesUtil.resolvePackgeName(), activity.getResources().getString(R.string.app_sid), updateCallback);
    }


    private void showDialog() {
        boolean isForce = false;
        Log.e(TAG, "BuildConfig.VERSION_CODE ==>  " + BuildConfig.VERSION_CODE);
        if (mUpdateBean.getForceVersion() > BuildConfig.VERSION_CODE) {
            Log.e(TAG, "isForce ==>  " + isForce);
            isForce = true;

        }
        mDialog.setForce(isForce);
        mDialog.refreashLeftButtonUI();
        mDialog.setMeg(mUpdateBean.getMemo(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyAuth();
            }
        });
        boolean finalIsForce = isForce;
        mDialog.setCancel(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                if (finalIsForce) {
                    activity.finish();
                } else {
                    dismissDL();
//                    if (mUpdateResult != null) {
//                        mUpdateResult.callBack(true);
//                    }
                }
            }
        });
        mDialog.setInstall(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mApk != null) {
                    installApk(mApk, activity);
                } else {
                    SingleToast.showMsg("apk 丢失，重新下载中....");
                    applyAuth();
                }
            }
        });
        mDialog.show();
    }

    private void applyAuth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int hasAuth = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (hasAuth != PackageManager.PERMISSION_GRANTED) {
                // 提交请求权限
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                downloadApp();
            }
        } else {
            downloadApp();
        }
    }

    private void downloadApp() {
        String apkName = String.format("app_%s_%s.apk", activity.getString(R.string.app_code), mUpdateBean.getVersionName());
        String url = String.format("%s%s%s/%s", NetUtil.replaceIp2Domain(DataCenter.getInstance().getIp()), mUpdateBean.getAppUrl(), mUpdateBean.getVersionName(), apkName);
        if (!NetTool.isConnected(activity)) {
            SingleToast.showMsg(activity.getString(R.string.unNet));
            return;
        }
        mDialog.setProgress(0);
        UpdateTool.downloadApk(url, apkName, new UpdateTool.DownLoadCallBack() {
            @Override
            public void onBefore() {
                mDialog.setUIMode(0);
            }

            @Override
            public void inProgress(float progress) {
                DecimalFormat fnum = new DecimalFormat("##0.00");
                mDialog.setProgress((int) (Double.parseDouble(fnum.format(progress)) * 100));
            }

            @Override
            public void onSuccess(File response) {
                mApk = response;
            }

            @Override
            public void onError() {
                mDialog.setUIMode(3);
                //下载失败就跳浏览器下载
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                activity.startActivity(intent);
            }
        }, activity);

    }

    private void showDL() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    private void dismissDL() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void permissionsResult(@NonNull int[] grantResults) {
        if (mUpdateBean != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadApp();
            }
        }
    }

}
