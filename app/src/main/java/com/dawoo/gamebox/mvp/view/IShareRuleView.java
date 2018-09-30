package com.dawoo.gamebox.mvp.view;

public interface IShareRuleView extends IBaseView {

    void onResult(Object o);

    void chooseStartTime(String time);

    void chooseEndTime(String time);

    void LoadMore(Object o);
}
