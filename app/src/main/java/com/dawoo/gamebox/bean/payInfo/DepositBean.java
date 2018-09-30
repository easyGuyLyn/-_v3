package com.dawoo.gamebox.bean.payInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rain on 18-3-25.
 */

public class DepositBean implements Serializable{
    private String code;
    private String name;
    private String iconUrl;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String icoUrl) {
        this.iconUrl = icoUrl;
    }
}
