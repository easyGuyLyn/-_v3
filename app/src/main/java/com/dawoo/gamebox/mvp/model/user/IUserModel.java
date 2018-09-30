package com.dawoo.gamebox.mvp.model.user;



import com.hwangjr.rxbus.annotation.Subscribe;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by benson on 17-12-21.
 */

public interface IUserModel {
    Subscription modifyLoginPwd(Subscriber subscriber, String oldPwd, String newPwd);

    Subscription modifyLoginPwd(Subscriber subscriber, String oldPwd, String newPwd, String code);

    Subscription initSecurityPwd(Subscriber subscriber);

    Subscription setRealSafeName(Subscriber subscriber, String reaName);

    Subscription modifySecurityPwd(Subscriber subscriber, Boolean needCaptcha, String realNames,
                                   String oldPwd, String newPwd, String confrmPwd, String code);

    Subscription logOut(Subscriber subscriber);


    Subscription verifyRealName(Subscriber subscriber,
                                String token,
                                String realName,
                                String playerAccount,
                                String playeAccount,
                                String tempPass ,
                                String newPassword);

    Subscription getCustomerService(Subscriber subscriber);

    Subscription getBindUserPhoneNUmber(Subscriber subscriber);

    Subscription bindUserPhone(Subscriber subscribe,String phone,String code,String oldPhone);

    Subscription validationCode(Subscriber subscribe,String code);

    Subscription modifyLoginPassword(Subscriber subscribe,String username,String newPassword);

    Subscription isOpenPwdByPhone(Subscriber subscribe);


}
