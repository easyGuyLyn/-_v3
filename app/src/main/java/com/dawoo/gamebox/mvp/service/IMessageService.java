package com.dawoo.gamebox.mvp.service;

import com.dawoo.gamebox.bean.CommonRequestResult;
import com.dawoo.gamebox.bean.GameNotice;
import com.dawoo.gamebox.bean.MessageDetail;
import com.dawoo.gamebox.bean.ResetSecurityPwd;
import com.dawoo.gamebox.bean.SiteMsgType;
import com.dawoo.gamebox.bean.SiteMsgUnReadCount;
import com.dawoo.gamebox.bean.SiteMyMsgDetailList;
import com.dawoo.gamebox.bean.SiteMyNotice;
import com.dawoo.gamebox.bean.SiteSysMsgDetail;
import com.dawoo.gamebox.bean.SiteSysNotice;
import com.dawoo.gamebox.bean.SysNotice;
import com.dawoo.gamebox.net.HttpResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 消息中心
 * Created by benson on 18-1-15.
 */

public interface IMessageService {

    /**
     * 获取游戏公告
     *
     * @param startTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @param apiId
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/getGameNotice.html")
    Observable<HttpResult<GameNotice>> getGameNotice(
            @Field("search.startTime") String startTime,
            @Field("search.endTime") String endTime,
            @Field("paging.pageNumber") int pageNumber,
            @Field("paging.pageSize") int pageSize,
            @Field("search.apiId") Integer apiId);

    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/getGameNotice.html")
    Observable<HttpResult<GameNotice>> getGameNotice(
            @Field("search.startTime") String startTime,
            @Field("search.endTime") String endTime,
            @Field("paging.pageNumber") int pageNumber,
            @Field("paging.pageSize") int pageSize);

    /**
     * 获取游戏公告
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/getGameNotice.html")
    Observable<HttpResult<GameNotice>> getGameNotice(
            @Field("paging.pageNumber") int pageNumber,
            @Field("paging.pageSize") int pageSize);

    /**
     * 获取游戏公告详情
     *
     * @param apiId
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/getGameNoticeDetail.html")
    Observable<HttpResult<MessageDetail>> getGameNoticeDetail(@Field("searchId") String apiId);


    /**
     * 系统公告
     *
     * @param startTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/getSysNotice.html")
    Observable<HttpResult<SysNotice>> getSysNotice(
            @Field("search.startTime") String startTime,
            @Field("search.endTime") String endTime,
            @Field("paging.pageNumber") int pageNumber,
            @Field("paging.pageSize") int pageSize);

    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/getSysNotice.html")
    Observable<HttpResult<SysNotice>> getSysNotice(@Field("paging.pageNumber") int pageNumber, @Field("paging.pageSize") int pageSize);

    /**
     * 系统公告详情
     *
     * @param searchId
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/getSysNoticeDetail.html")
    Observable<HttpResult<MessageDetail>> getSysNoticeDetail(@Field("searchId") String searchId);


    /**
     * 站点消息  获取系统消息列表
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/getSiteSysNotice.html")
    Observable<HttpResult<SiteSysNotice>> getSiteSysNotice(
            @Field("paging.pageNumber") int pageNumber,
            @Field("paging.pageSize") int pageSize);


    /**
     * 站点消息  标记已读
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/setSiteSysNoticeStatus.html")
    Observable<CommonRequestResult> setSiteSysNoticeStatus(@Field("ids") String pageNumber);


    /**
     * 站点消息  删除
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/deleteSiteSysNotice.html")
    Observable<CommonRequestResult> deleteSiteSysNotice(@Field("ids") String pageNumber);

    /**
     * 站点消息  获取我的消息列表
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/advisoryMessage.html")
    Observable<SiteMyNotice> getSiteMyNotice(
            @Field("paging.pageNumber") int pageNumber,
            @Field("paging.pageSize") int pageSize);


    /**
     * 站点消息  我的 标记已读
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/getSelectAdvisoryMessageIds.html")
    Observable<CommonRequestResult> setSiteMyNoticeStatus(@Field("ids") String pageNumber);


    /**
     * 站点消息 我的   删除
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/deleteAdvisoryMessage.html")
    Observable<CommonRequestResult> deleteSiteMyNotice(@Field("ids") String pageNumber);

    /**
     * 获取发送消息的类型选项
     */
    @POST("mobile-api/mineOrigin/getNoticeSiteType.html")
    Observable<HttpResult<SiteMsgType>> getSiteMsgType();


    /**
     * 提交消息 无验证码
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/addNoticeSite.html")
    Observable<ResetSecurityPwd> addNoticeSite(@Field("result.advisoryType") String advisoryType,
                                               @Field("result.advisoryTitle") String advisoryTitle,
                                               @Field("result.advisoryContent") String advisoryContent);

    /**
     * 提交消息
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/addNoticeSite.html")
    Observable<ResetSecurityPwd> addNoticeSite(@Field("result.advisoryType") String advisoryType,
                                               @Field("result.advisoryTitle") String advisoryTitle,
                                               @Field("result.advisoryContent") String advisoryContent,
                                               @Field("code") String code);

    /**
     * 获取site消息的未读数量
     */
    @POST("mobile-api/mineOrigin/getUnReadCount.html")
    Observable<HttpResult<SiteMsgUnReadCount>> getUnReadCount();

    /**
     * site 获取系统消息详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/getSiteSysNoticeDetail.html")
    Observable<HttpResult<SiteSysMsgDetail>> getSiteSysNoticeDetail(@Field("searchId") String searchId);

    /**
     * site 获取我的消息详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/mineOrigin/advisoryMessageDetail.html")
    Observable<SiteMyMsgDetailList> advisoryMessageDetail(@Field("id") String id);
}
