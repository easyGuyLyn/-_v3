package com.dawoo.gamebox.mvp.model.disport;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by rain on 18-3-23.
 */

public interface IDesportModel {
    Subscription getPayType(Subscriber subscriber);

    Subscription getSecType(Subscriber subscription, String code);

    Subscription getPayInfo(Subscriber subscriber, double rechargeAmount, String depositWay, String searchId);

    Subscription getPayInfo(Subscriber subscriber, double bitCount, String depositWay, String searchId, String txid);

    Subscription postOnline(Subscriber subscriber, double rechargeAmount, String depositWay, String searchId, long saleId,String payerBank);

    Subscription postScan(Subscriber subscriber, double rechargeAmount, String depositWay, String searchId, long saleId, String bankCode);

    Subscription postCompany(Subscriber subscriber, double rechargeAmount, String depositWay, String searchId, long saleId,
                             String payerName, String address);

    Subscription postElectronic(Subscriber subscriber, double rechargeAmount, String payWay, String searchId, long saleId,
                                String orderNum, String aliName, String payerCard);

    Subscription postBitcoin(Subscriber subscriber, String payWay, String searchId, long saleId,
                             String address, String orderNum, double rechargeAmount, String time);
}
