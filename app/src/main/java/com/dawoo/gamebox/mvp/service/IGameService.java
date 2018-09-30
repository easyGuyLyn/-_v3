package com.dawoo.gamebox.mvp.service;

import android.support.annotation.NonNull;

import com.dawoo.gamebox.bean.GameLink;
import com.dawoo.gamebox.bean.VideoGame;
import com.dawoo.gamebox.bean.VideoGameType;
import com.dawoo.gamebox.net.HttpResult;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 游戏相关接口
 * Created by benson on 18-1-8.
 */

public interface IGameService {

    /**
     * 获取电子游戏列表
     *
     * @param apiId
     * @param apiTypeId
     * @param pageNumber
     * @param pageSize
     * @param name
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/origin/getCasinoGame.html")
    Observable<HttpResult<VideoGame>> getCasinoGame(
            @Field("search.apiId") int apiId,
            @Field("search.apiTypeId") int apiTypeId,
            @Field("paging.pageNumber") int pageNumber,
            @Field("paging.pageSize") int pageSize,
            @Field("search.name") String name);

    /**
     *
     * 获取电子游戏列表
     *
     * @param apiId
     * @param apiTypeId
     * @param pageNumber
     * @param pageSize
     * @param name
     * @param tagId
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/origin/getCasinoGame.html")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加请求头防止乱码
    Observable<HttpResult<VideoGame>> getCasinoGame(
            @Field("search.apiId") int apiId,
            @Field("search.apiTypeId") int apiTypeId,
            @Field("paging.pageNumber") int pageNumber,
            @Field("paging.pageSize") int pageSize,
            @Field("search.name") String name,
            @Field("tagId") String tagId);

    /**
     * 获取单个进入游戏的链接
     * @param apiId
     * @param apiTypeId
     * @param pageSize
     * @param name
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/origin/getGameLink.html")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加请求头防止乱码
    Observable<HttpResult<GameLink>> getGameLink(
            @Field("apiId") @NonNull int apiId,
            @Field("apiTypeId") @NonNull int apiTypeId,
            @Field("gameId") int pageSize,
            @Field("gameCode") String name);

    /**
     * 获取单个进入游戏的链接
     * @param apiId
     * @param apiTypeId
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/origin/getGameLink.html")
    Observable<HttpResult<GameLink>> getGameLink(
            @Field("apiId") @NonNull int apiId,
            @Field("apiTypeId") @NonNull int apiTypeId);


    /**
     * 获取电子游戏分类
     * @return
     */
    @FormUrlEncoded
    @POST("mobile-api/origin/getGameTag.html")
    Observable<HttpResult<List<VideoGameType>>> getGameTag(
            @Field("search.apiId") int apiId,
            @Field("search.apiTypeId") int apiTypeId);
}
