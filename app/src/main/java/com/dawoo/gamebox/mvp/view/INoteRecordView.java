package com.dawoo.gamebox.mvp.view;

/**
 * 投资记录
 * Created by benson on 18-1-7.
 */

public interface INoteRecordView extends IBaseView {
    void onRecordResult(Object o);

    void loadMoreData(Object o);

    void doSearch(Object o);

    void chooseStartTime(String time);

    void chooseEndTime(String time);
}
