package com.dawoo.gamebox.mvp.model.account;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by benson on 18-2-25.
 */

public interface IAccountModel {
    Subscription getUserAssert(Subscriber subscriber);

    Subscription getWithDraw(Subscriber subscriber);

    Subscription submitWithdraw(Subscriber subscriber, double withdrawAmount, String token, int remittanceWay, String originPwd);

    Subscription getCardType(Subscriber subscriber);

    Subscription submitBankCard(Subscriber subscriber, String bankcardMasterName, String bankName, String bankcardNumber, String bankDeposit);

    Subscription submitBtc(Subscriber subscriber, String bankcardNumber);

    Subscription getBtcInfo(Subscriber subscriber);

    Subscription checkSafePassword(Subscriber subscriber, String originPwd);

    Subscription withdrawFee(Subscriber subscriber, double withdrawFee);

    Subscription recovery(Subscriber subscriber);

    Subscription refresh(Subscriber subscriber);

    Subscription getAccount(Subscriber subscriber);

    Subscription getAudit(Subscriber subscriber);

}
