package com.dawoo.gamebox.bean;

/**
 * Created by benson on 18-1-16.
 */

public class FastChoose {
    private int code;
    private String name;

    public FastChoose() {
    }

    public FastChoose(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
