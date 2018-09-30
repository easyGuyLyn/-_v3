package com.dawoo.gamebox.mvp.view;

/**
 * Created by benson on 17-12-27.
 */

public interface IPromoFragmentView extends IBaseView {

    void onPromoResult(Object o);//加载spinner的数据

    void onPromoListResult(Object o); //加载下面列表的数据

    void loadMoreListDate(Object o);//加载更多数据


}
