package com.dawoo.gamebox.mvp.presenter;

import android.content.Context;

import com.dawoo.gamebox.bean.payInfo.DepositBean;
import com.dawoo.gamebox.bean.payInfo.DepositResultBean;
import com.dawoo.gamebox.bean.payInfo.PayTypeBean;
import com.dawoo.gamebox.bean.payInfo.PopPayBean;
import com.dawoo.gamebox.mvp.model.disport.DisportModel;

import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.IDisportView;
import com.dawoo.gamebox.net.rx.ProgressSubscriber;

import java.util.List;

import rx.Subscription;


/**
 * Created by rain on 18-3-23.
 */

public class DisportPresenter<T extends IBaseView> extends BasePresenter {
    private DisportModel mModel;

    public DisportPresenter(Context mContext, T view) {
        super(mContext, view);
        this.mModel = new DisportModel();
    }

    /**
     * 获取渠道大分类列表
     */
    public void getPayTypes() {
        Subscription subscription = mModel.getPayType(new ProgressSubscriber(o ->
                ((IDisportView) mView).getPayTypes((List<DepositBean>) o), mContext));
        subList.add(subscription);

    }

    /**
     * 获取二级渠道列表
     * @param code 大分类code
     */
    public void getSecTypes(String code) {
        Subscription subscription = mModel.getSecType(new ProgressSubscriber(o ->
                ((IDisportView) mView).getSecType((PayTypeBean) o), mContext), code
        );
        subList.add(subscription);
    }

    public void getPayInfo(String searchId, double rechargeAmount, String depositWay) {
        Subscription subscription = mModel.getPayInfo(new ProgressSubscriber(o ->
                        ((IDisportView) mView).getPayInfo((PopPayBean) o), mContext),
                rechargeAmount,
                depositWay,
                searchId);
        subList.add(subscription);

    }
    public void getPayInfo(String searchId, double bitCount, String depositWay,String txid) {
        Subscription subscription = mModel.getPayInfo(new ProgressSubscriber(o ->
                        ((IDisportView) mView).getPayInfo((PopPayBean) o), mContext),
                bitCount,
                depositWay,
                searchId,
                txid);
        subList.add(subscription);

    }
    /**
     * 在线支付
     *
     * @param money
     * @param type
     * @param searchId
     * @param saleId
     */
    public void postOnline(double money, String type, String searchId, long saleId,String payerBank) {
        Subscription subscription = mModel.postOnline(new ProgressSubscriber(o ->
                        ((IDisportView) mView).postPayInfo((DepositResultBean)o), mContext),
                money,
                type,
                searchId,
                saleId,
                payerBank);
        subList.add(subscription);
    }

    public void postScan(double money, String type, String searchId, long saleId, String cardCode) {
        Subscription subscription = mModel.postScan(new ProgressSubscriber(o ->
                        ((IDisportView) mView).postPayInfo((DepositResultBean) o), mContext),
                money,
                type,
                searchId,
                saleId,
                cardCode);
        subList.add(subscription);
    }

    /**
     * 网银支付
     *
     * @param money
     * @param type
     * @param searchId
     * @param saleId
     * @param payerName
     * @param address
     */
    public void postCompany(double money, String type, String searchId, long saleId, String payerName, String address) {
        Subscription subscription = mModel.postCompany(new ProgressSubscriber(o ->
                        ((IDisportView) mView).postPayInfo((DepositResultBean)o), mContext),
                money,
                type,
                searchId,
                saleId,
                payerName,
                address);
        subList.add(subscription);
    }

    /**
     * 电子支付
     *
     * @param rechargeAmount
     * @param payWay
     * @param searchId
     * @param saleId
     * @param orderNum
     * @param aliName
     * @param payerCard
     */
    public void postElectronic(double rechargeAmount, String payWay, String searchId, long saleId,
                               String orderNum, String aliName, String payerCard) {
        Subscription subscription = mModel.postElectronic(new ProgressSubscriber(o ->
                        ((IDisportView) mView).postPayInfo((DepositResultBean)o), mContext),
                rechargeAmount,
                payWay,
                searchId,
                saleId,
                orderNum,
                aliName,
                payerCard);
        subList.add(subscription);
    }

    /**
     * 比特币
     *
     * @param payWay
     * @param searchId
     * @param saleId
     * @param address
     * @param orderNum
     * @param rechargeAmount
     * @param time
     */
    public void postBitcoin(String payWay, String searchId, long saleId, String address, String orderNum, double rechargeAmount, String time) {
        Subscription subscription = mModel.postBitcoin(new ProgressSubscriber(o ->
                        ((IDisportView) mView).postPayInfo((DepositResultBean) o), mContext),
                payWay,
                searchId,
                saleId,
                address,
                orderNum,
                rechargeAmount,
                time);
        subList.add(subscription);
    }
}
