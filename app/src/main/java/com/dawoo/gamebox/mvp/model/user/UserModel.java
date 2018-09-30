package com.dawoo.gamebox.mvp.model.user;

import com.dawoo.gamebox.bean.GetBindUserPhoneNumberBean;
import com.dawoo.gamebox.bean.IsBindUserPhoneBean;
import com.dawoo.gamebox.bean.IsOpenPwdByPhoneBean;
import com.dawoo.gamebox.bean.Logout;
import com.dawoo.gamebox.bean.RDSMSBean;
import com.dawoo.gamebox.bean.ResetSecurityPwd;
import com.dawoo.gamebox.bean.ServiceBean;
import com.dawoo.gamebox.bean.UpdateLoginPwd;
import com.dawoo.gamebox.bean.VerifyRealNameResponse;
import com.dawoo.gamebox.mvp.model.BaseModel;
import com.dawoo.gamebox.mvp.service.IUserService;
import com.dawoo.gamebox.net.HttpResult;
import com.dawoo.gamebox.net.RetrofitHelper;
import com.hwangjr.rxbus.annotation.Subscribe;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;


/**
 * Created by benson on 17-12-21.
 */

public class UserModel extends BaseModel implements IUserModel {


    @Override
    public Subscription modifyLoginPwd(Subscriber subscriber, String oldPwd, String newPwd) {
        Observable<UpdateLoginPwd> observable = RetrofitHelper.getService(IUserService.class)
                .updateLoginPwd(oldPwd, newPwd);
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription modifyLoginPwd(Subscriber subscriber, String oldPwd, String newPwd, String code) {
        Observable<UpdateLoginPwd> observable = RetrofitHelper.getService(IUserService.class)
                .updateLoginPwd(oldPwd, newPwd, code);
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription initSecurityPwd(Subscriber subscriber) {
        Observable<ResetSecurityPwd> observable = RetrofitHelper.getService(IUserService.class)
                .initSafePassword();
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription setRealSafeName(Subscriber subscriber, String reaName) {
        Observable<ResetSecurityPwd> observable = RetrofitHelper.getService(IUserService.class)
                .setRealName(reaName);
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription modifySecurityPwd(Subscriber subscriber, Boolean needCaptcha, String realNames, String oldPwd, String newPwd, String confrmPwd, String code) {
        Observable<ResetSecurityPwd> observable = RetrofitHelper.getService(IUserService.class)
                .updateSafePassword(needCaptcha, realNames, oldPwd, newPwd, confrmPwd, code);
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription logOut(Subscriber subscriber) {
        Observable<Logout> observable = RetrofitHelper.getService(IUserService.class).logOut();
        return toSubscribe(observable, subscriber);
    }


    @Override
    public Subscription verifyRealName(Subscriber subscriber, String token, String realName, String playerAccount, String playeAccount, String tempPass, String newPassword) {
        Observable<VerifyRealNameResponse> observable = RetrofitHelper.getService(IUserService.class).verifyRealName(token,realName,
                "yes",playerAccount,playeAccount,tempPass,newPassword,"20");
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription getCustomerService(Subscriber subscriber) {
        Observable<ServiceBean> observable = RetrofitHelper.getService(IUserService.class).getCustomerService().map(new HttpResultFunc<>());
        return toSubscribe(observable, subscriber);
    }

    @Override
    public Subscription getBindUserPhoneNUmber(Subscriber subscriber) {
        Observable<GetBindUserPhoneNumberBean> observable = RetrofitHelper.getService(IUserService.class).getBindPhoneNumber();
        return toSubscribe(observable,subscriber);
    }

    @Override
    public Subscription bindUserPhone(Subscriber subscribe, String phone, String code,String oldPhone) {
        Observable<HttpResult> observable = RetrofitHelper.getService(IUserService.class).bindUserPhone(phone, code,oldPhone);
        return toSubscribe(observable, subscribe);
    }

    @Override
    public Subscription validationCode(Subscriber subscribe, String code) {
        Observable<IsBindUserPhoneBean> observable = RetrofitHelper.getService(IUserService.class).validationCode(code);
        return toSubscribe(observable,subscribe);
    }

    @Override
    public Subscription modifyLoginPassword(Subscriber subscribe, String username, String newPassword) {
        Observable<IsBindUserPhoneBean> observable = RetrofitHelper.getService(IUserService.class).modifyLoginPassword(username, newPassword);
        return toSubscribe(observable,subscribe);
    }

    @Override
    public Subscription isOpenPwdByPhone(Subscriber subscribe) {
        Observable<IsOpenPwdByPhoneBean> observable = RetrofitHelper.getService(IUserService.class).isOpenPwdByPhone();
        return toSubscribe(observable,subscribe);
    }


}
