package com.dawoo.gamebox.util;


import org.apache.http.util.TextUtils;

/**
 * 存款　订单需要输入后五位
 */
public enum InputOrderEnum {

    mnu49r(119, "nu9r"),
    m57h0(270, "57h0");

    private String codeName;
    private int code;

    private InputOrderEnum(int code, String codeName) {
        this.codeName = codeName;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public static int getCodeByCodeName(String codeName) {
        int code = 0;
        if (!TextUtils.isEmpty(codeName)) {
            for (InputOrderEnum specialSiteEnum : InputOrderEnum.values()) {
                if (codeName.equals(specialSiteEnum.getCodeName())) {
                    code = specialSiteEnum.code;
                    break;
                }
            }
        }
        return code;
    }
}
