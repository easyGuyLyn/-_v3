package com.dawoo.gamebox.mvp.service;

import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.bean.GetBindUserPhoneNumberBean;
import com.dawoo.gamebox.bean.IsBindUserPhoneBean;
import com.dawoo.gamebox.bean.IsOpenPwdByPhoneBean;
import com.dawoo.gamebox.bean.Logout;
import com.dawoo.gamebox.bean.RDSMSBean;
import com.dawoo.gamebox.bean.RegisterBean;
import com.dawoo.gamebox.bean.RegisterInfoBean;
import com.dawoo.gamebox.bean.ResetSecurityPwd;
import com.dawoo.gamebox.bean.ServiceBean;
import com.dawoo.gamebox.bean.UpdateLoginPwd;
import com.dawoo.gamebox.bean.VerifyRealNameResponse;
import com.dawoo.gamebox.net.HttpResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 用户相关的接口
 * Created by benson on 17-12-21.
 */

public interface IUserService {

    /**
     * 修改登录密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/updateLoginPassword.html")
    Observable<UpdateLoginPwd> updateLoginPwd(@Field("password") String password, @Field("newPassword") String newPassword);

    /**
     * 带验证码的修改登录密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/updateLoginPassword.html")
    Observable<UpdateLoginPwd> updateLoginPwd(@Field("password") String password,
                                              @Field("newPassword") String newPassword,
                                              @Field("code") String code);


    /**
     * init安全密码
     *
     * @return
     */
    @POST("mobile-api/mineOrigin/initSafePassword.html")
    Observable<ResetSecurityPwd> initSafePassword();

    /**
     * 设置真名
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/setRealName.html")
    Observable<ResetSecurityPwd> setRealName(@Field("realName") String realName);


    /**
     * 修改安全密码
     *
     * @param name
     * @param password
     * @param newPassword
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/updateSafePassword.html")
    Observable<ResetSecurityPwd> updateSafePassword(@Field("needCaptcha") Boolean needCaptcha,
                                                    @Field("realName") String name,
                                                    @Field("originPwd") String password,
                                                    @Field("pwd1") String newPassword,
                                                    @Field("pwd2") String confirmNewPassword,
                                                    @Field("code") String code);

    /**
     * 登出
     *
     * @return
     */
    @POST("mobile-api/mineOrigin/logout.html")
    Observable<Logout> logOut();

    /**
     * 登录前请求是否需要验证码的
     *
     * @return
     */
    @POST("mobile-api/mineOrigin/loginIsOpenVerify.html")
    Observable<Boolean> isNeedVerificationCode();


    @GET(ConstantValue.GET_REGISTER_INFO)
    Call<RegisterInfoBean> getRegisterInfo();

    @FormUrlEncoded
    @POST(ConstantValue.POST_REGISTER_SAVE)
    Observable<RegisterBean> saveRegister(@FieldMap Map<String, String> params);

    /**
     * 验证真实姓名
     */
    @FormUrlEncoded
    @POST("passport/verify/verifyRealNameForApp.html")
    Observable<VerifyRealNameResponse> verifyRealName(
            @Field("gb.token") String token,
            @Field("result.realName") String realName,
            @Field("needRealName") String yes,
            @Field("result.playerAccount") String playerAccount,
            @Field("search.playerAccount") String playeAccount,
            @Field("tempPass") String tempPass,
            @Field("newPassword ") String newPassword,
            @Field("passLevel") String passLevel);

    /**
     * 注册手机短信验证码
     */
    @FormUrlEncoded
    @POST("mobile-api/origin/sendPhoneCode.html")
    Observable<RDSMSBean> postRegisterSmsCode(@Field("phone") String phone);

    /**
     * 获取客服地址信息
     */
    @POST("mobile-api/origin/getCustomerService.html")
    Observable<HttpResult<ServiceBean>> getCustomerService();

    /**
     * 绑定手机号获取用户手机号接口
     */

    @POST("mobile-api/mineOrigin/getUserPhone.html")
    Observable<GetBindUserPhoneNumberBean> getBindPhoneNumber();

    /**
     * 42.绑定用户手机号接口:
     */
    @FormUrlEncoded
    @POST("/mobile-api/mineOrigin/updateUserPhone.html")
    Observable<HttpResult> bindUserPhone(@Field("search.contactValue")String phone,@Field("code")String code,@Field("oldPhone")String oldPhone);

    @FormUrlEncoded
    @POST("/mobile-api/findPasswordOrigin/checkPhoneCode.html")
    Observable<IsBindUserPhoneBean>validationCode(@Field("code")String code);

    @FormUrlEncoded
    @POST("/mobile-api/findPasswordOrigin/findLoginPassword.html")
    Observable<IsBindUserPhoneBean> modifyLoginPassword(@Field("username")String username,@Field("newPassword")String newPassword);

    @POST("/mobile-api/findPasswordOrigin/openFindByPhone.html")
    Observable<IsOpenPwdByPhoneBean> isOpenPwdByPhone();


}
