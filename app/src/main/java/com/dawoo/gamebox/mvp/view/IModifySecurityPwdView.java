package com.dawoo.gamebox.mvp.view;

/**
 * 修改安全密码
 * Created by benson on 18-1-7.
 */

public interface IModifySecurityPwdView {
    void onInitResult(Object o);

    void onSetRealNameResult(Object o);

    void onModifyResult(Object o);

    void doModify();

    void initSafePwd();

    void setRealName();

    void backRealName(String name);
}
