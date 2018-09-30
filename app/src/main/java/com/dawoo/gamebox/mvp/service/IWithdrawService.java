package com.dawoo.gamebox.mvp.service;

import com.dawoo.gamebox.bean.BankCards;
import com.dawoo.gamebox.bean.Bitcoin;
import com.dawoo.gamebox.bean.HongbaoCount;
import com.dawoo.gamebox.bean.WithdrawFee;
import com.dawoo.gamebox.bean.WithdrawResult;
import com.dawoo.gamebox.bean.WithdrawSubmitResult;
import com.dawoo.gamebox.net.HttpResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by b on 18-1-15.
 * 取款、添加银行卡、比特币
 */

public interface IWithdrawService {

    /**
     * 获取账户取款(银行卡、比特币)相关信息
     *
     * */

    @POST("mobile-api/withdrawOrigin/getWithDraw.html")
    Observable<WithdrawResult> getWithDraw();


    /**
     * 提交取款
     * */
    @FormUrlEncoded
    @POST("mobile-api/withdrawOrigin/submitWithdraw.html")
    Observable<WithdrawSubmitResult> submitWithdraw(
            @Field("withdrawAmount") double withdrawAmount,
            @Field("gb.token") String token,
            @Field("remittanceWay") int remittanceWay,
            @Field("originPwd") String originPwd
    );


    /**
     * 获取银行卡种类和已绑定银行卡信息
     *
     * 后台把数据放在同一接口，主页获取用户信息也是这个接口，因为数据太多单独写一个只解析我们需要的信息
     * */
    @POST("mobile-api/userInfoOrigin/getUserInfo.html")
    Observable<HttpResult<BankCards> > getCardAndBanksInfo();


    /**
     * 添加银行卡
     * */
    @FormUrlEncoded
    @POST("mobile-api/userInfoOrigin/submitBankCard.html")
    Observable<WithdrawSubmitResult> submitBankCard(
            @Field("result.bankcardMasterName") String bankcardMasterName,
            @Field("result.bankName") String bankName,
            @Field("result.bankcardNumber") String bankcardNumber,
            @Field("result.bankDeposit") String bankDeposit);

    /**
     * 绑定比特币
     *
     * */
    @FormUrlEncoded
    @POST("mobile-api/userInfoOrigin/submitBtc.html")
    Observable<WithdrawSubmitResult> submitBtc(@Field("result.bankcardNumber") String bankcardNumber);

    /**
     * 获取比特币信息
     * */
    @POST("mobile-api/mineOrigin/addBtc.html")
    Observable<Bitcoin> getBtcInfo();


    /**
     * 安全码验证
     * */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/checkSafePassword.html")
    Observable<WithdrawSubmitResult> checkSafePassword(@Field("originPwd") String originPwd);


    /**
     * 计算金额
     * */
    @FormUrlEncoded
    @POST("mobile-api/withdrawOrigin/withdrawFee.html")
    Observable<WithdrawFee> withdrawFee(@Field("withdrawAmount") double withdrawAmount);


}
