package com.dawoo.gamebox.mvp.view;

/**
 * 站点--系统消息
 * Created by archar on 18-1-15.
 */

public interface ISiteSysNoticeView extends IBaseView {
    void onLoadResult(Object o);

    void onLoadMoreResult(Object o);

    void onReadMsgsResult(Object o);

    void onReadMsgResult(Object o);

    void onDeleteMsgsResult(Object o);
}
