package com.dawoo.gamebox.mvp.view;

import com.dawoo.gamebox.bean.RegisterBean;
import com.dawoo.gamebox.bean.RegisterInfoBean;

/**
 * Created by alex on 18-3-22.
 */

public interface IRegisterView extends IBaseView {
//    void accountRegister(String username,String password,String verificationCode);
      void getRegisterInfoSuccess(RegisterInfoBean registerInfoBean);
      void registerSuccess(RegisterBean registerBean);
      void registerError(String error);
}
