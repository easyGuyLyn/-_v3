package com.dawoo.gamebox.util;

import org.apache.http.util.TextUtils;

//特殊站点　设置引导页　不需要显示ｌｏｇｏ
public enum SpecialSiteEnum {

    mnu9r(119, "nu9r"),
    m5rdu(136,"5rdu"),
    m57h0(270, "57h0");


    private String codeName;
    private int code;

    private SpecialSiteEnum(int code, String codeName) {
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
            for (SpecialSiteEnum specialSiteEnum : SpecialSiteEnum.values()) {
                if (codeName.equals(specialSiteEnum.getCodeName())) {
                    code = specialSiteEnum.code;
                    break;
                }
            }
        }
        return code;
    }
}
