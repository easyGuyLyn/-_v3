package com.dawoo.gamebox.mvp.view;

/**
 * Created by b on 18-1-16.
 * 添加银行卡
 */

public interface IAddBankCardView extends IBaseView{

    void getCardType(Object o);

    void submitBankCard(Object o);

    void selectedBank(String bankName,int index);

}
