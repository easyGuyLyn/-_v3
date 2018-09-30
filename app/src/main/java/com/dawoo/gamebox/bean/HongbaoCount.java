package com.dawoo.gamebox.bean;

/**
 * Created by benson on 18-1-2.
 */

/**
 * isEnd:是否结束
 * drawTimes:次数  -1：活动已结束  -5：红包已抢光
 * nextLotteryTime:下次开奖时间
 * token:防重
 */
public class HongbaoCount {


    /**
     * isEnd : false
     * drawTimes : 99
     * nextLotteryTime : 2018-01-11 20:10:00
     * token : 76cd9b1a-d19b-493b-ab27-2b6838df7ad8
     */

    private String isEnd;
    private int drawTimes;
    private String nextLotteryTime;
    private String token;

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public int getDrawTimes() {
        return drawTimes;
    }

    public void setDrawTimes(int drawTimes) {
        this.drawTimes = drawTimes;
    }

    public String getNextLotteryTime() {
        return nextLotteryTime;
    }

    public void setNextLotteryTime(String nextLotteryTime) {
        this.nextLotteryTime = nextLotteryTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
