package com.dawoo.gamebox.mvp.view;

import com.dawoo.gamebox.bean.payInfo.DepositBean;
import com.dawoo.gamebox.bean.payInfo.DepositResultBean;
import com.dawoo.gamebox.bean.payInfo.PayTypeBean;
import com.dawoo.gamebox.bean.payInfo.PopPayBean;

import java.util.List;

/**
 * Created by rain on 18-3-23.
 */

public interface IDisportView extends IBaseView {
    void getPayTypes(List<DepositBean> o);

    void getSecType(PayTypeBean o);

    void getPayInfo(PopPayBean o);

    void postPayInfo(DepositResultBean o);
}
