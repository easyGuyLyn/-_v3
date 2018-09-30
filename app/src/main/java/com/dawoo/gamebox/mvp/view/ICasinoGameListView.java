package com.dawoo.gamebox.mvp.view;

/**
 * 电子游戏列表
 * Created by benson on 18-1-8.
 */

public interface ICasinoGameListView extends IBaseView {
    void onLoadResult(Object o);

    void onLoadGameTagResult(Object o);

    void loadMoreData(Object o);


    void doSearch();

    void onLoadGameLink(Object o);

}
