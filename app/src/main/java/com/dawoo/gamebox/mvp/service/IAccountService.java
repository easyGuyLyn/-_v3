package com.dawoo.gamebox.mvp.service;

import com.dawoo.gamebox.bean.UserAssert;
import com.dawoo.gamebox.net.HttpResult;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by benson on 18-2-25.
 */

public interface IAccountService {
    /**
     * 获取用户资产信息
     *
     * @return
     */
    @POST("mobile-api/userInfoOrigin/getUserAssert.html")
    Observable<HttpResult<UserAssert>> getUserAssert();
}
