package com.dawoo.gamebox.bean;

/**
 * Created by benson on 18-1-23.
 */

public class VideoGameType {
    /**
     * key : hot_game
     * value : 热门游戏
     */

    private String key;
    private String value;

    public VideoGameType() {
    }

    public VideoGameType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
