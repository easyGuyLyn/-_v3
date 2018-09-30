package com.dawoo.gamebox.mvp.view;

/**
 * Created by benson on 17-12-27.
 */

public interface IHomeFragmentView extends IBaseView {
    void onBannerResult(Object o);

    void onNoticeResult(Object o);

    void onSiteApiRelationResult(Object o);

    void onFloatResult(Object o);

    void onAccountResult(Object o);

    void onAssertResult(Object o);

    void onLoadGameLink(Object o);

    void onHongBaoCountResult(Object o);

    void onGetPacketResult(Object o);

    void onRecoveryResult(Object o);

    void onRefreshResult(Object o);

    void getTimeZone(Object o);

    void onAlwaysRequestResult(Object o);

    void onGameLinkResult(Object o,int apiTypeId,int apiId);


}
