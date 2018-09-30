package com.dawoo.gamebox.mvp.view;

/**
 * Created by benson on 18-1-17.
 */

public interface IMessageDetailView extends IBaseView{
    void onLoadGameDetailResult(Object o);
    void onLoadSysDetailResult(Object o);
    void onLoadSiteSysDetailResult(Object o);
    void onLoadSiteMyDetailResult(Object o);
}
