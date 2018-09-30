package com.dawoo.gamebox.bean.payInfo;

import java.io.Serializable;

/**
 *
 * @author rain
 * @date 18-4-3
 */

public class CounterBean implements Serializable{
    private String code;
    private String name;

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
}
