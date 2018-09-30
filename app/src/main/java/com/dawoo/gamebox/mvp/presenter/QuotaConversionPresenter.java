package com.dawoo.gamebox.mvp.presenter;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dawoo.coretool.util.ToastUtil;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.ApiBean;
import com.dawoo.gamebox.bean.ConversionInfoBean;
import com.dawoo.gamebox.bean.QuotaConversionBean;
import com.dawoo.gamebox.bean.UserAssert;
import com.dawoo.gamebox.mvp.model.account.AccountModel;
import com.dawoo.gamebox.mvp.model.conversion.QuotaConversionModel;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.IQuotaConversionView;
import com.dawoo.gamebox.net.HttpResult;
import com.dawoo.gamebox.net.rx.ProgressSubscriber;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.List;

import rx.Subscription;

/**
 * Created by b on 18-3-26.
 */

public class QuotaConversionPresenter<T extends IBaseView> extends BasePresenter {

    private final QuotaConversionModel mQuotaConversionModel;
    private final AccountModel mModel;

    public QuotaConversionPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mQuotaConversionModel = new QuotaConversionModel();
        mModel = new AccountModel();
    }

    /**
     * 获取额度转换基本信息
     */
    public void getNoAutoTransferInfo() {
        Subscription subscription = mQuotaConversionModel.getNoAutoTransferInfo(new ProgressSubscriber(o ->
                ((IQuotaConversionView) mView).onNoAutoTransferInfo((ConversionInfoBean) o), mContext));
        subList.add(subscription);
    }

    /**
     * 获取用户资产信息
     */
    public void refreshAPIs() {
        Subscription subscription = mModel.getUserAssert(new ProgressSubscriber(o ->
                ((IQuotaConversionView) mView).onRefreshApis((UserAssert) o), mContext));
        subList.add(subscription);
    }


    /**
     * 额度转换提交
     */
    public void transfersMoney(String token, String transferOut, String transferInto, String transferAmount) {
        Subscription subscription = mQuotaConversionModel.transfersMoney(new ProgressSubscriber(o ->
                        ((IQuotaConversionView) mView).onTransfersMoney((QuotaConversionBean) o), mContext),
                token, transferOut, transferInto, transferAmount
        );
        subList.add(subscription);
    }

    /**
     * 额度转换异常再次请求
     */
    public void reconnectTransfer(String transactionNo) {
        Subscription subscription = mQuotaConversionModel.reconnectTransfer(new ProgressSubscriber(o ->
                        ((IQuotaConversionView) mView).onReconnectTransfer((QuotaConversionBean) o), mContext),
                transactionNo
        );
        subList.add(subscription);
    }

    /**
     * 刷新单个api：
     */
    public void refreshApi(String apiId) {
        Subscription subscription = mQuotaConversionModel.refreshApi(new ProgressSubscriber(o ->
                        ((IQuotaConversionView) mView).onRefreshApi((ApiBean) o), mContext),
                apiId
        );
        subList.add(subscription);
    }

    /**
     * 一键回收
     */
    public void recovery(String apiId) {
        Subscription subscription = mQuotaConversionModel.recovery(new ProgressSubscriber(o ->
                ((IQuotaConversionView) mView).onRecovery((HttpResult) o), mContext, false), apiId);
        subList.add(subscription);
    }


    Dialog mMDialog;
    /**
     * 游戏选择dialog
     */
    private int mIndex;

    public void initSelectGameDialog(List<String> mGames) {
        mIndex = 0;
        if (mGames.size() == 0) {
            ToastUtil.showToastShort(mContext, mContext.getString(R.string.get_game_error));
            return;
        }
        mMDialog = new Dialog(mContext, R.style.CommonHintDialog);
        Window window = mMDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        mMDialog.setContentView(R.layout.dialog_select_bank);
        LoopView loopView = mMDialog.findViewById(R.id.lp_select_bank);
        loopView.setItems(mGames);
        loopView.setInitPosition(0);
        loopView.setTextSize(20);
        loopView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bgColor));
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mIndex = index;
            }
        });

        mMDialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMDialog.dismiss();
            }
        });
        mMDialog.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IQuotaConversionView) mView).selectedGame(mGames.get(mIndex), mIndex);
                mMDialog.dismiss();
            }
        });
        mMDialog.show();
    }


    @Override
    public void onDestory() {
        super.onDestory();
        if (mMDialog != null)
            mMDialog.dismiss();
    }
}
