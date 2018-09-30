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
import com.dawoo.gamebox.bean.BankCards;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.UserAccount;
import com.dawoo.gamebox.bean.UserAssert;
import com.dawoo.gamebox.mvp.model.account.AccountModel;
import com.dawoo.gamebox.mvp.view.AuditView;
import com.dawoo.gamebox.mvp.view.IAddBankCardView;
import com.dawoo.gamebox.mvp.view.IAddBitcoinView;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.IHomeFragmentView;
import com.dawoo.gamebox.mvp.view.IWithdrawView;
import com.dawoo.gamebox.net.rx.ProgressSubscriber;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.List;

import rx.Subscription;

/**
 * Created by b on 18-1-15.
 * 取款
 */

public class WithdrawPresenter<T extends IBaseView> extends BasePresenter {

    private final AccountModel mMWithdrawModel;
    private Dialog mMDialog;

    public WithdrawPresenter(Context mContext, T view) {
        super(mContext, view);
        mMWithdrawModel = new AccountModel();
    }

    /**
     * 获取账户取款相关信息
     */
    public void getWithdraw() {
        Subscription subscription = mMWithdrawModel.getWithDraw(
                new ProgressSubscriber(o -> ((IWithdrawView) mView).onWithdrawInfo(o), mContext));
        subList.add(subscription);
    }

    /**
     * 提交取款申请
     */
    public void submitWithdraw(double withdrawAmount, String token, int remittanceWay, String originPwd) {
        Subscription subscription = mMWithdrawModel.submitWithdraw(
                new ProgressSubscriber(o -> ((IWithdrawView) mView).submitWithdraw(o), mContext),
                withdrawAmount,
                token,
                remittanceWay,
                originPwd);
        subList.add(subscription);
    }


    /**
     * 验证安全码
     */
    public void checkSafePassword(String originPwd) {
        Subscription subscription = mMWithdrawModel.checkSafePassword(
                new ProgressSubscriber(o -> ((IWithdrawView) mView).checkSafePassword(o), mContext),
                originPwd);
        subList.add(subscription);
    }


    /**
     * 获取银行卡种类和已绑定银行卡信息
     */
    public void getCardType() {
        Subscription subscription = mMWithdrawModel.getCardType(
                new ProgressSubscriber(o -> ((IAddBankCardView) mView).getCardType((BankCards) o), mContext));
        subList.add(subscription);
    }

    /**
     * 添加银行卡
     */
    public void submitBankCard(String bankcardMasterName, String bankName, String bankcardNumber, String bankDeposit) {
        Subscription subscription = mMWithdrawModel.submitBankCard(
                new ProgressSubscriber(o -> ((IAddBankCardView) mView).submitBankCard(o), mContext),
                bankcardMasterName,
                bankName,
                bankcardNumber,
                bankDeposit);
        subList.add(subscription);
    }


    /**
     * 获取比特币信息
     */
    public void getBtcInfo() {
        Subscription subscription = mMWithdrawModel.getCardType(
                new ProgressSubscriber<>(o -> ((IAddBitcoinView) mView).getBtcInfo(o), mContext));
        subList.add(subscription);
    }

    ;

    /**
     * 添加比特币
     */

    public void submitBtc(String bankcardNumber) {
        Subscription subscription = mMWithdrawModel.submitBtc(
                new ProgressSubscriber(o -> ((IAddBitcoinView) mView).submitBtc(o), mContext),
                bankcardNumber);
        subList.add(subscription);
    }

    /**
     * 结算金额
     */

    public void withdrawFee(double withdrawAmount) {
        Subscription subscription = mMWithdrawModel.withdrawFee(
                new ProgressSubscriber(o -> ((IWithdrawView) mView).withdrawFee(o), mContext),
                withdrawAmount);
        subList.add(subscription);
    }


    /**
     * 银行选择dialog
     */
    private int mIndex = 3;

    public void initSelectBankDialog(List<String> mBanks) {
        mIndex = 3;
        if (mBanks.size() == 0) {
            ToastUtil.showToastShort(mContext, mContext.getString(R.string.get_card_error));
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
        loopView.setItems(mBanks);
        loopView.setInitPosition(3);
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
                ((IAddBankCardView) mView).selectedBank(mBanks.get(mIndex), mIndex);
                mMDialog.dismiss();
            }
        });
        mMDialog.show();
    }

    public String getMosaicString(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (str == null) {
            return "";
        } else if (1 < str.length() && str.length() < 10) {
            sb.replace(1, str.length(), "*");
            return sb.toString();
        } else if (12 < str.length()) {
            sb.replace(8, 12, "****");
            return sb.toString();
        }
        return "****";

    }


    /**
     * 一键回收
     */
    public void recovery() {
        Subscription subscription = mMWithdrawModel.recovery(new ProgressSubscriber(o ->
                ((IWithdrawView) mView).onRecoveryResult(o), mContext));
        subList.add(subscription);
    }


    /**
     * 一键刷新
     */
    public void refreshAPI() {
        Subscription subscription = mMWithdrawModel.refresh(new ProgressSubscriber(o ->
                ((IWithdrawView) mView).onRefreshResult(o), mContext));
        subList.add(subscription);
    }


    /**
     * 获取账户数据
     */
    public void getAccount() {
        Subscription subscription = mMWithdrawModel.getAccount(new ProgressSubscriber(((IWithdrawView) mView)::onAccountResult, mContext, false));
        subList.add(subscription);
    }


    /**
     * 获取用户资产
     */
    public void getAssert() {
        Subscription subscription = mMWithdrawModel.getUserAssert(new ProgressSubscriber(((IWithdrawView) mView)::onAssertResult, mContext));
        subList.add(subscription);
    }


    @Override
    public void onDestory() {
        super.onDestory();
        if (mMDialog != null) {
            mMDialog.dismiss();
        }
        mMDialog = null;
    }

    /**
     * 替换不同类型数据
     * 资产数据填充到账户类型数据
     *
     * @param o
     * @return
     */
    public Object rePlaceData(Object o, UserAccount account) {
        if (o != null && o instanceof UserAssert) {
            UserAssert userAssert = (UserAssert) o;
            UserAccount.UserBean userBean = account.getUser();
            userBean.setUsername(userAssert.getUsername());
            userBean.setCurrency(userAssert.getCurrSign());
            userBean.setTotalAssets(userAssert.getAssets());
            userBean.setWalletBalance(userAssert.getBalance());

            // 替换api
            List<UserAccount.UserBean.ApisBean> apis = userBean.getApis();
            List<UserAssert.ApisBean> apis2 = userAssert.getApis();
            UserAccount.UserBean.ApisBean api;
            UserAssert.ApisBean api2;
            for (int i = 0; i < apis.size(); i++) {
                api = new UserAccount.UserBean.ApisBean();
                api2 = apis2.get(i);
                api.setApiId(api2.getApiId());
                api.setApiName(api2.getApiName());
                api.setBalance(api2.getBalance());
                api.setStatus(api2.getStatus());
                apis.set(i, api);
            }
            userBean.setApis(apis);
            account.setUser(userBean);
        }

        return account;
    }

    public void setDataCenterUserInfo(UserAccount.UserBean userBean) {
        DataCenter.getInstance().setRealName(userBean.getRealName());
    }

    public void getAudit(){
        Subscription subscription = mMWithdrawModel.getAudit(new ProgressSubscriber(o ->
                ((AuditView) mView).getAuditState(o), mContext));
        subList.add(subscription);
    }

}
