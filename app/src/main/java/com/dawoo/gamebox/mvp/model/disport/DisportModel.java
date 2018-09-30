package com.dawoo.gamebox.mvp.model.disport;

import com.dawoo.gamebox.bean.payInfo.DepositBean;
import com.dawoo.gamebox.bean.payInfo.DepositResultBean;
import com.dawoo.gamebox.bean.payInfo.PayTypeBean;
import com.dawoo.gamebox.bean.payInfo.PopPayBean;
import com.dawoo.gamebox.mvp.model.BaseModel;
import com.dawoo.gamebox.mvp.service.IDisportService;
import com.dawoo.gamebox.net.RetrofitHelper;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 *
 * @author rain
 * @date 18-3-23
 */

public class DisportModel extends BaseModel implements IDesportModel {
    @Override
    public Subscription getPayType(Subscriber subscriber) {
        Observable<List<DepositBean>> observable = RetrofitHelper.getService(IDisportService.class).getPayTypes().map(new HttpResultFunc<List<DepositBean>>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription getSecType(Subscriber subscription, String code) {
        Observable<PayTypeBean> observable = RetrofitHelper.getService(IDisportService.class).getSecTypes(code).map(new HttpResultFunc<PayTypeBean>());
        return toSubscribe(observable, subscription);
    }


    @Override
    public Subscription getPayInfo(Subscriber subscriber, double rechargeAmount, String depositWay, String searchId) {
        Observable<PopPayBean> observable = RetrofitHelper.getService(IDisportService.class)
                .getPayInfo(rechargeAmount, depositWay, searchId)
                .map(new HttpResultFunc<PopPayBean>());
        return toSubscribe(observable, subscriber);
    }
    @Override
    public Subscription getPayInfo(Subscriber subscriber, double bitCount, String depositWay, String searchId,String txid) {
        Observable<PopPayBean> observable = RetrofitHelper.getService(IDisportService.class)
                .getPayInfo(bitCount, depositWay, searchId,txid)
                .map(new HttpResultFunc<PopPayBean>());
        return toSubscribe(observable, subscriber);
    }
    /**
     * 提交线上支付
     *
     * @param subscriber
     * @param rechargeAmount
     * @param depositWay
     * @param searchId
     * @param saleId
     * @return
     */
    @Override
    public Subscription postOnline(Subscriber subscriber, double rechargeAmount, String depositWay, String searchId, long saleId,String payerBank) {
        Observable<DepositResultBean> observable = RetrofitHelper.getService(IDisportService.class)
                .postOnline(rechargeAmount, depositWay, searchId, saleId,payerBank)
                .map(new HttpResultFunc<DepositResultBean>());
        return toSubscribe(observable, subscriber);
    }

    /**
     * 扫码支付
     *
     * @param subscriber
     * @param rechargeAmount
     * @param payType
     * @param searchId
     * @param saleId
     * @param bankCode       反扫授权码，反扫必选
     * @return
     */
    @Override
    public Subscription postScan(Subscriber subscriber, double rechargeAmount, String payType, String searchId, long saleId, String bankCode) {
        Observable<DepositResultBean> observable = RetrofitHelper.getService(IDisportService.class)
                .postScan(rechargeAmount, payType, searchId, saleId, bankCode)
                .map(new HttpResultFunc<DepositResultBean>());
        return toSubscribe(observable, subscriber);
    }

    /**
     * 电子支付
     *
     * @param subscriber
     * @param rechargeAmount
     * @param depositWay
     * @param searchId
     * @param saleId
     * @param payerName
     * @param address        柜台机必选
     * @return
     */
    @Override
    public Subscription postCompany(Subscriber subscriber, double rechargeAmount, String depositWay, String searchId, long saleId, String payerName, String address) {
        Observable<DepositResultBean> observable = RetrofitHelper.getService(IDisportService.class)
                .postCompany(rechargeAmount, depositWay, searchId, saleId, payerName, address)
                .map(new HttpResultFunc<DepositResultBean>());
        return toSubscribe(observable, subscriber);
    }

    /**
     * 电子支付
     * @param subscriber
     * @param rechargeAmount
     * @param payWay
     * @param searchId
     * @param saleId
     * @param orderNum
     * @param aliName 仅限支付宝用户名
     * @param payerCard 支付账号
     * @return
     */
    @Override
    public Subscription postElectronic(Subscriber subscriber, double rechargeAmount, String payWay, String searchId, long saleId, String orderNum, String aliName, String payerCard) {
        Observable<DepositResultBean> observable = RetrofitHelper.getService(IDisportService.class)
                .postElectronic(rechargeAmount, payWay, searchId, saleId, orderNum, aliName, payerCard)
                .map(new HttpResultFunc<DepositResultBean>());
        return toSubscribe(observable, subscriber);
    }

    /**
     * 比特币支付
     * @param subscriber
     * @param payWay
     * @param searchId
     * @param saleId
     * @param address
     * @param orderNum
     * @param rechargeAmount
     * @param time
     * @return
     */
    @Override
    public Subscription postBitcoin(Subscriber subscriber, String payWay, String searchId, long saleId, String address, String orderNum, double rechargeAmount, String time) {
        Observable<DepositResultBean> observable = RetrofitHelper.getService(IDisportService.class)
                .postBitcoin(payWay, searchId, saleId, address,orderNum, rechargeAmount, time)
                .map(new HttpResultFunc<DepositResultBean>());
        return toSubscribe(observable, subscriber);
    }

}
