package com.dawoo.gamebox.bean;

/**
 * API
 * Created by fei on 17-3-25.
 */
public enum ApiEnum {
    DS("1", "DS真人"),
    KG("2", "KG彩票"),
    MG("3", "MG真人/电子"),
    IM("4", "IM体育"),
    GD("5", "GD真人"),
    PT("6", "PT电子"),
    OG("7", "OG真人"),
    DW("8", "DW真人"),
    AG("9", "AG真人/电子"),
    BB("10", "BB真人/电子/体育/彩票"),
    PG("11", "传奇彩票"),
    HG("12", "皇冠体育"),
    NYX("14", "NYX电子"),
    HABA("15", "HABA电子"),
    EBET("16", "EBET真人"),
    SA("17", "SA真人"),
    SB("19", "沙巴体育"),
    XHG("21", "新皇冠"),
    TT("22", "天天彩票"),
    OUPS_SPORT("23", "欧普斯体育"),
    OUPS_LIVE("24", "欧普斯体育");

    private String code;
    private String memo;

    ApiEnum(String code, String memo) {
        this.code = code;
        this.memo = memo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
