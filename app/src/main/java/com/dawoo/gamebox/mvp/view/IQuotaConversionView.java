package com.dawoo.gamebox.mvp.view;

import com.dawoo.gamebox.bean.ApiBean;
import com.dawoo.gamebox.bean.BankCards;
import com.dawoo.gamebox.bean.ConversionInfoBean;
import com.dawoo.gamebox.bean.QuotaConversionBean;
import com.dawoo.gamebox.bean.RefreshhApis;
import com.dawoo.gamebox.bean.UserAccount;
import com.dawoo.gamebox.bean.UserAssert;
import com.dawoo.gamebox.net.HttpResult;

/**
 * Created by b on 18-3-26.
 */

public interface IQuotaConversionView extends IBaseView{

    void onNoAutoTransferInfo(ConversionInfoBean o);

    void onRefreshApis(UserAssert bankCards);

    void onTransfersMoney(QuotaConversionBean o);

    void onReconnectTransfer(QuotaConversionBean o);

    void onRefreshApi(ApiBean o);

    void selectedGame(String bankName,int index);

    void onRecovery(HttpResult refreshhApis);

}
