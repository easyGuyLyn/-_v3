package com.dawoo.gamebox.bean;

/**
 * 拆红包
 * gameNum:游戏次数  -5：已抢完 -4：条件不满足 -3:红包活动结束 -2：抽奖异常 -1：已抽完 0：已结束
 * × award:0  金额
 * × nextLotteryTime:下次抽奖时间
 * Created by benson on 18-1-12.
 */


public class GetPacket {

    /**
     * recordId : 5c88a6f4f6486cfcb880a9bd35288ab0
     * applyId : 64f8fdcab582d64e8efaf0b8f8c0e052
     * gameNum : 98
     * award : 5
     * id : e68752442b5979c57c1a455e29acf567
     * nextLotteryTime : 2018-01-12 09:20:00
     * token : 0b7d04b9-de89-4edf-88d3-19fe080bcf46
     */

    private String recordId;
    private String applyId;
    private int gameNum;
    private String award;
    private String id;
    private String nextLotteryTime;
    private String token;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public int getGameNum() {
        return gameNum;
    }

    public void setGameNum(int gameNum) {
        this.gameNum = gameNum;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
