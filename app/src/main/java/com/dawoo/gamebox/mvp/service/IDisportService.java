package com.dawoo.gamebox.mvp.service;

import com.dawoo.gamebox.bean.payInfo.DepositBean;
import com.dawoo.gamebox.bean.payInfo.DepositResultBean;
import com.dawoo.gamebox.bean.payInfo.PayTypeBean;
import com.dawoo.gamebox.bean.payInfo.PopPayBean;
import com.dawoo.gamebox.net.HttpResult;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rain on 18-3-23.
 */

public interface IDisportService {
    /**
     * 获取付款方式
     *
     * @return
     */
    @POST("mobile-api/depositOrigin/index.html")
    Observable<HttpResult<List<DepositBean>>> getPayTypes();

    /**
     * 获取付款方式
     *
     * @return
     */
    @POST("mobile-api/depositOrigin/{code}.html")
    Observable<HttpResult<PayTypeBean>> getSecTypes(
            @Path("code") String code);

    /**
     * 获取优惠列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/depositOrigin/seachSale.html")
    Observable<HttpResult<PopPayBean>> getPayInfo(
            @Field("result.rechargeAmount") double rechargeAmount,
            @Field("depositWay") String depositWay,
            @Field("account") String searchId);

    /**
     * 获取优惠列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/depositOrigin/seachSale.html")
    Observable<HttpResult<PopPayBean>> getPayInfo(
            @Field("result.bitAmount") double bitCount,
            @Field("depositWay") String depositWay,
            @Field("account") String searchId,
            @Field("result.bankOrder") String TxId);


    /**
     * online
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/depositOrigin/onlinePay.html")
    Observable<HttpResult<DepositResultBean>> postOnline(
            @Field("result.rechargeAmount") double rechargeAmount,
            @Field("result.rechargeType") String depositWay,
            @Field("account") String searchid,
            @Field("activityId") long saleId,
            @Field("result.payerBank") String payerBank);

    /**
     * scan
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/depositOrigin/scanPay.html")
    Observable<HttpResult<DepositResultBean>> postScan(
            @Field("result.rechargeAmount") double rechargeAmount,
            @Field("result.rechargeType") String depositWay,
            @Field("account") String searchId,
            @Field("activityId") long saleId,
            @Field("Result.payerBankcard") String bankid);//只针对反扫 授权码


    /**
     * company
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/depositOrigin/companyPay.html")
    Observable<HttpResult<DepositResultBean>> postCompany(
            @Field("result.rechargeAmount") double rechargeAmount,
            @Field("result.rechargeType") String depositWay,
            @Field("account") String searchId,
            @Field("activityId") long saleId,
            @Field("result.payerName") String payName,
            @Field("result.rechargeAddress") String address);

    /**
     * electronic
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/depositOrigin/electronicPay.html")
    Observable<HttpResult<DepositResultBean>> postElectronic(
            @Field("result.rechargeAmount") double rechargeAmount,
            @Field("result.rechargeType") String depositWay,
            @Field("account") String searchId,
            @Field("activityId") long saleId,
            @Field("result.bankOrder") String orderNum,
            @Field("result.payerName") String aliName,
            @Field("result.payerBankcard") String payerCard);

    /**
     * bitCoin
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/depositOrigin/bitcoinPay.html")
    Observable<HttpResult<DepositResultBean>> postBitcoin(
            @Field("result.rechargeType") String depositWay,
            @Field("account") String searchId,
            @Field("activityId") long saleId,
            @Field("result.payerBankcard") String bitAddress,
            @Field("result.bankOrder") String payerCard,
            @Field("result.bitAmount") double rechargeAmount,
            @Field("result.returnTime") String changeTime);

}
