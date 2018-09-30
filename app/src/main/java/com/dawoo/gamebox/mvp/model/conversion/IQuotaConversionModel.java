package com.dawoo.gamebox.mvp.model.conversion;

import retrofit2.http.Field;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by b on 18-3-26.
 */

public interface IQuotaConversionModel {

    Subscription getNoAutoTransferInfo(Subscriber subscriber);

    Subscription transfersMoney(Subscriber subscriber ,String token, String transferOut, String transferInto,String transferAmount);

    Subscription reconnectTransfer(Subscriber subscriber,String transactionNo);

    Subscription refreshApi(Subscriber subscriber,String apiId);

    Subscription recovery(Subscriber subscriber, String apiId);
}
