package com.dawoo.gamebox.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by b on 18-1-26.
 */

public class VerifyRealNameRequst {

    @SerializedName("gb.token")
    private String _$GbToken180; // FIXME check this code
    @SerializedName("result.realName")
    private String _$ResultRealName266; // FIXME check this code
    private String needRealName;
    @SerializedName("result.playerAccount")
    private String _$ResultPlayerAccount303; // FIXME check this code
    @SerializedName("search.playerAccount")
    private String _$SearchPlayerAccount270; // FIXME check this code
    private String tempPass;
    private String newPassword;
    private String passLevel;

    public String get_$GbToken180() {
        return _$GbToken180;
    }

    public void set_$GbToken180(String _$GbToken180) {
        this._$GbToken180 = _$GbToken180;
    }

    public String get_$ResultRealName266() {
        return _$ResultRealName266;
    }

    public void set_$ResultRealName266(String _$ResultRealName266) {
        this._$ResultRealName266 = _$ResultRealName266;
    }

    public String getNeedRealName() {
        return needRealName;
    }

    public void setNeedRealName(String needRealName) {
        this.needRealName = needRealName;
    }

    public String get_$ResultPlayerAccount303() {
        return _$ResultPlayerAccount303;
    }

    public void set_$ResultPlayerAccount303(String _$ResultPlayerAccount303) {
        this._$ResultPlayerAccount303 = _$ResultPlayerAccount303;
    }

    public String get_$SearchPlayerAccount270() {
        return _$SearchPlayerAccount270;
    }

    public void set_$SearchPlayerAccount270(String _$SearchPlayerAccount270) {
        this._$SearchPlayerAccount270 = _$SearchPlayerAccount270;
    }

    public String getTempPass() {
        return tempPass;
    }

    public void setTempPass(String tempPass) {
        this.tempPass = tempPass;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPassLevel() {
        return passLevel;
    }

    public void setPassLevel(String passLevel) {
        this.passLevel = passLevel;
    }
}
