package com.dawoo.gamebox.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.dawoo.coretool.util.LogUtils;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.bean.DataCenter;
import com.dawoo.gamebox.bean.NewUrlBean;
import com.dawoo.gamebox.bean.SiteApiRelation;
import com.dawoo.gamebox.bean.UrlBean;
import com.dawoo.gamebox.bean.UserAccount;
import com.dawoo.gamebox.bean.UserAssert;
import com.dawoo.gamebox.mvp.model.account.AccountModel;
import com.dawoo.gamebox.mvp.model.game.GameModel;
import com.dawoo.gamebox.mvp.model.home.HomeModel;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.IHomeFragmentView;
import com.dawoo.gamebox.net.rx.ProgressSubscriber;
import com.dawoo.gamebox.util.ActivityUtil;
import com.dawoo.gamebox.util.NetUtil;
import com.dawoo.gamebox.util.SingleToast;
import com.dawoo.gamebox.view.activity.VideoGameListActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscription;

import static com.dawoo.gamebox.util.AutoLogin.CheckHasMainEnanceError;
import static com.dawoo.gamebox.util.NetUtil.getOkHttpClientBuilderForHttps;


/**
 * 首页presenter
 */

public class HomeFramentPresenter<T extends IBaseView> extends BasePresenter {

    private final Context mContext;
    private T mView;
    private final HomeModel mModel;
    private final GameModel mGameModel;
    private final AccountModel mAccountModel;
    Activity mActivity;

    public HomeFramentPresenter(Context context, T mView) {
        super(context, mView);

        mContext = context;
        this.mView = mView;
        mModel = new HomeModel();
        mGameModel = new GameModel();
        mAccountModel = new AccountModel();
        mActivity = (Activity) mContext;
    }


    /**
     * 获取轮播图片
     */
    public void getBanners() {
        Subscription subscription = mModel.getBanner(new ProgressSubscriber(o -> ((IHomeFragmentView) mView).onBannerResult(o), mContext, false));
        subList.add(subscription);
    }

    /**
     * 获取公告
     */
    public void getNotice() {
        Subscription subscription = mModel.getNotice(new ProgressSubscriber(o -> ((IHomeFragmentView) mView).onNoticeResult(o), mContext, false));
        subList.add(subscription);
    }

    /**
     * 获取游戏列表
     */
    public void getSiteApiRelation() {
        Subscription subscription = mModel.getSiteApiRelation(new ProgressSubscriber(o -> ((IHomeFragmentView) mView).onSiteApiRelationResult(o), mContext, false));
        subList.add(subscription);
    }

    /**
     * 获取浮动图
     */
    public void getFAB() {
        Subscription subscription = mModel.getFAB(new ProgressSubscriber(o -> ((IHomeFragmentView) mView).onFloatResult(o), mContext, false));
        subList.add(subscription);
    }

    /**
     * 获取账户数据
     */
    public void getAccount() {
        Subscription subscription = mModel.getAccount(new ProgressSubscriber(((IHomeFragmentView) mView)::onAccountResult, mContext, false));
        subList.add(subscription);
    }

    /**
     * 获取用户资产
     */
    public void getAssert() {
        Subscription subscription = mAccountModel.getUserAssert(new ProgressSubscriber(((IHomeFragmentView) mView)::onAssertResult, mContext));
        subList.add(subscription);
    }


    /**
     * 获取游戏链接
     */
    public void getGameLink(int apiId, int apiTypeId, @Nullable int gameId, @Nullable String gameCode) {
        Subscription subscription = mGameModel.getGameLink(new ProgressSubscriber(o ->
                        ((IHomeFragmentView) mView).onLoadGameLink(o), mContext),
                apiId,
                apiTypeId,
                gameId,
                gameCode);
        subList.add(subscription);
    }

    /**
     * 获取游戏链接
     */
    public void getGameLink(int apiId, int apiTypeId) {
        Subscription subscription = mGameModel.getGameLink(new ProgressSubscriber(o ->
                        ((IHomeFragmentView) mView).onLoadGameLink(o), mContext),
                apiId,
                apiTypeId);
        subList.add(subscription);
    }

    /**
     * 获取红包次数
     */
    public void countDrawTimes(String activityMessageId) {
        Subscription subscription = mModel.countDrawTimes(new ProgressSubscriber(o ->
                        ((IHomeFragmentView) mView).onHongBaoCountResult(o), mContext),
                activityMessageId);
        subList.add(subscription);
    }

    /**
     * 打开红包
     *
     * @param activityMessageId
     * @param token
     */
    public void getPacket(String activityMessageId, String token) {
        Subscription subscription = mModel.getPacket(new ProgressSubscriber(o ->
                        ((IHomeFragmentView) mView).onGetPacketResult(o), mContext),
                activityMessageId,
                token);
        subList.add(subscription);
    }

    /**
     * 一键回收
     */
    public void recovery() {
        Subscription subscription = mModel.recovery(new ProgressSubscriber(o ->
                ((IHomeFragmentView) mView).onRecoveryResult(o), mContext));
        subList.add(subscription);
    }

    /**
     * 一键刷新
     */
    public void refreshAPI() {
        Subscription subscription = mModel.refresh(new ProgressSubscriber(o ->
                ((IHomeFragmentView) mView).onRefreshResult(o), mContext));
        subList.add(subscription);
    }

    /**
     * 获取时区
     */
    public void getTimeZone() {
        Subscription subscription = mModel.getTimeZone(new ProgressSubscriber(o ->
                ((IHomeFragmentView) mView).getTimeZone(o), mContext, false));
        subList.add(subscription);
    }

    /**
     * 防止掉线
     */
    public void alwaysRequest() {
        Subscription subscription = mModel.alwaysRequest(new ProgressSubscriber(o ->
                ((IHomeFragmentView) mView).onAlwaysRequestResult(o), mContext, false));
        subList.add(subscription);
    }

    /**
     * 替换不同类型数据
     * 资产数据填充到账户类型数据
     *
     * @param o
     * @return
     */
    public Object rePlaceData(Object o, UserAccount account) {
        if (o != null && o instanceof UserAssert) {
            UserAssert userAssert = (UserAssert) o;
            UserAccount.UserBean userBean = account.getUser();
            userBean.setUsername(userAssert.getUsername());
            userBean.setCurrency(userAssert.getCurrSign());
            userBean.setTotalAssets(userAssert.getAssets());
            userBean.setWalletBalance(userAssert.getBalance());

            // 替换api
            List<UserAccount.UserBean.ApisBean> apis = userBean.getApis();
            List<UserAssert.ApisBean> apis2 = userAssert.getApis();
            UserAccount.UserBean.ApisBean api;
            UserAssert.ApisBean api2;
            for (int i = 0; i < apis.size(); i++) {
                api = new UserAccount.UserBean.ApisBean();
                api2 = apis2.get(i);
                api.setApiId(api2.getApiId());
                api.setApiName(api2.getApiName());
                api.setBalance(api2.getBalance());
                api.setStatus(api2.getStatus());
                apis.set(i, api);
            }
            userBean.setApis(apis);
            account.setUser(userBean);
        }

        return account;
    }


    public void setDataCenterUserInfo(UserAccount.UserBean userBean) {
        DataCenter.getInstance().setRealName(userBean.getRealName());
    }


    /**
     * 处理api游戏的点击事件
     *
     * @param mLevel
     * @param gameListBean
     * @param siteApisBean
     */
    public void onApiItemClick(boolean mLevel, SiteApiRelation.SiteApisBean.GameListBean gameListBean,
                               SiteApiRelation.SiteApisBean siteApisBean, List<SiteApiRelation.SiteApisBean> gameItemListUnLevel) {
        // 请求链接
        if (mLevel) {
            if (gameListBean != null) {
                // 请求链接
                dealwithItemURL(gameListBean, gameItemListUnLevel);
            }
        } else {
            // 处理非彩票
            dealwithItemURL(siteApisBean, gameItemListUnLevel);
        }
    }

    /**
     * 处理api的链接
     *
     * @param o
     */
    void dealwithItemURL(Object o, List<SiteApiRelation.SiteApisBean> gameItemListUnLevel) {
        if (o == null) {
            return;
        }
        String ip = DataCenter.getInstance().getIp();
        // 有第三列数据类型
        if (o instanceof SiteApiRelation.SiteApisBean.GameListBean) {
            SiteApiRelation.SiteApisBean.GameListBean gameListBean = (SiteApiRelation.SiteApisBean.GameListBean) o;
            dealRealGameURL_level(gameListBean.getApiTypeId(), gameListBean.getApiId(), gameListBean.getName(), ip, gameListBean.getGameLink());
        } else if (o instanceof SiteApiRelation.SiteApisBean) {
            SiteApiRelation.SiteApisBean siteApisBean = (SiteApiRelation.SiteApisBean) o;
            dealRealGameURL(siteApisBean.getApiTypeId(), siteApisBean.getApiId(), siteApisBean.getName(), ip, siteApisBean.getGameLink(), gameItemListUnLevel);
        }
    }

    private void dealRealGameURL_level(int apiTypeId, int apiId, String name, String domain, String link) {
        if (link == null) {
            return;
        }
        getRealGameURL(apiTypeId, domain, link, apiId);
    }

    private void dealRealGameURL(int apiTypeId, int apiId, String name, String ip, String link, List<SiteApiRelation.SiteApisBean> gameItemListUnLevel) {
        if (link == null) {
            return;
        }

        if (2 == apiTypeId) {
            // 电子游戏
            startVideoGameActivity(apiTypeId, apiId, name, gameItemListUnLevel);
        } else {
            getRealGameURL(apiTypeId, ip, link, apiId);
        }
    }

    private int mMCode;  //返回状态码

    private void getRealGameURL(int apiTypeId, String ip, String link, int apiId) {
        if (link == null) {
            return;
        }

        if (link.startsWith("/mobile-api")) {
            // link含有 /mobile-api
            // 非电子游戏 请求链接
            String url = NetUtil.handleUrl(link);
            // getGameLink(url, apiTypeId, apiId);
            SingleToast.showMsg("加载中..");
            OkHttpClient.Builder builder = getOkHttpClientBuilderForHttps();


            Request request = new Request.Builder().url(url)
                    .headers(Headers.of(NetUtil.setHeaders()))
                    .get()
                    .build();

            Call call = builder.build().newCall(request);

            try {

                call.enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        SingleToast.showMsg("网络异常，请点击重试！");
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!CheckHasMainEnanceError(response)) {
                            return;
                        }
                        final String jsonData = response.body().string();
                        mMCode = response.code();
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mMCode != 200) {
                                    return;
                                }
                                Log.e("游戏api返回的游戏链接：", jsonData);
                                if (!TextUtils.isEmpty(jsonData)) {
                                    try {
                                        NewUrlBean newUrlBean = new Gson().fromJson(jsonData, NewUrlBean.class);
                                        ((IHomeFragmentView) mView).onGameLinkResult(newUrlBean.getData(), apiTypeId, apiId);
                                    } catch (Exception e) {
                                        LogUtils.e("游戏api  jsonError ==> " + e.getLocalizedMessage());
                                    }
                                }
                            }
                        });
                    }
                });
            } catch (Exception e) {
                LogUtils.e("游戏api  Error ==> " + e.getLocalizedMessage());
                SingleToast.showMsg("网络异常，请点击重试！");
            }

        } else {
            //如果是彩票和体彩
            if (apiTypeId == 3 || apiTypeId == 4) {
                ActivityUtil.startWebView(ip + link, "", ConstantValue.WEBVIEW_TYPE_GAME, 1, apiId + "");
            } else {
                ActivityUtil.startWebView(ip + link, "", ConstantValue.WEBVIEW_TYPE_GAME, 3, apiId + "");
            }

        }
    }


    /**
     * 进入电子游戏
     */
    public void startVideoGameActivity(int apiTypeId, int apiId, String name, List<SiteApiRelation.SiteApisBean> gameItemListUnLevel) {
        Intent intent = new Intent(mContext, VideoGameListActivity.class);
        intent.putExtra(VideoGameListActivity.API_TYPE_ID, apiTypeId);
        intent.putExtra(VideoGameListActivity.API_ID, apiId);
        intent.putExtra(VideoGameListActivity.GEME_NAME, name);
        intent.putExtra(VideoGameListActivity.GEME_LIST, (Serializable) gameItemListUnLevel);
        mContext.startActivity(intent);
    }

    /**
     * 获取游戏link
     */
    public void getGameLink(String link, int apiTypeId, int apiId) {
        Subscription subscription = mModel.getAPILink(new ProgressSubscriber(o ->
                        ((IHomeFragmentView) mView).onGameLinkResult(o, apiTypeId, apiId), mContext),
                link);
        subList.add(subscription);
    }


    /**
     * 处理返回的游戏链接
     *
     * @param o
     */
    public void doResultGameLink(Object o, int apiTypeId, int apiId) {
        if (o != null && o instanceof UrlBean) {
            UrlBean urlBean = (UrlBean) o;
            if (TextUtils.isEmpty(urlBean.getGameLink())) {
                SingleToast.showMsg(urlBean.getGameMsg());
                return;
            }

            String url = urlBean.getGameLink();
            //如果是彩票
            if (apiTypeId == 4) {
                if (apiId == 22) { //如果是LT彩票
                    ActivityUtil.startLtDomainWebview(url, mContext, ConstantValue.WEBVIEW_TYPE_GAME, apiId + "");
                } else {//如果是其他彩票
                    url = NetUtil.handleUrl(url);
                    ActivityUtil.startWebView(url, "", ConstantValue.WEBVIEW_TYPE_GAME, 1, apiId + "");
                }
            }
            //如果是体育
            else if (apiTypeId == 3) {
                url = NetUtil.handleUrl(url);
                ActivityUtil.startWebView(url, "", ConstantValue.WEBVIEW_TYPE_GAME, 1, apiId + "");
            } else {
                url = NetUtil.handleUrl(url);
                ActivityUtil.startWebView(url, "", ConstantValue.WEBVIEW_TYPE_GAME, 3, apiId + "");
            }

        }
    }

    /**
     * 处理站点id
     *
     * @return
     */
    private static String resolvePackgeName() {
        String packageName = BoxApplication.getContext().getPackageName();

        String subStr = packageName.substring(packageName.indexOf("sid"));
        if (subStr.contains("debug")) {
            return subStr.substring(3, subStr.lastIndexOf("."));
        } else {
            return subStr.substring(3);
        }
    }


    @Override
    public void onDestory() {
        super.onDestory();
    }
}
