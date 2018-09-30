package com.dawoo.gamebox.mvp.view;

/**
 * 游戏公告
 * Created by benson on 18-1-15.
 */

public interface IGameNoticeView extends IBaseView{
    void onLoadResult(Object o);
    void onLoadMoreResult(Object o);
}
