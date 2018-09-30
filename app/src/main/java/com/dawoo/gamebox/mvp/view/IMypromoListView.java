package com.dawoo.gamebox.mvp.view;

/**
 * Created by jack on 18-1-14.
 */

public interface IMypromoListView extends IBaseView {

    void onLoadResult(Object o);

    void loadMoreData(Object o);

    void onRefresh();
}
