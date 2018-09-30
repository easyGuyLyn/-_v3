package com.dawoo.gamebox.bean;

/**
 * Created by b on 18-1-12.
 * 投资明细
 */

public class BettingDetail {

    String userName;               //:玩家账号
    String betId;                  //:注单号码
    long betTime;                //:投注时间
    String singleAmount;           //:投注额
    String effectiveTradeAmount;   //:有效投注额
    String payoutTime;             //:派彩时间
    String profitAmount;           //:派彩
    String gameType;               //:游戏类型
    String matchIndex;             //:球赛编号
    String teamBetCode;            //:	玩家投注
    String Handicap;               //：让球
    String oddsTypeName;           //:盘口类型
    String wagerOdds;              //:赔率
    String Score;                  //:比分
    String matchDate;              //:球赛日期
    String apiId;                   //游戏供应商
    String apiTypeId;               //游戏种类
    String contributionAmount;      //彩池贡献金

    public String getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(String contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    public String getApiTypeId() {
        return apiTypeId;
    }

    public void setApiTypeId(String apiTypeId) {
        this.apiTypeId = apiTypeId;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public long getBetTime() {
        return betTime;
    }

    public void setBetTime(long betTime) {
        this.betTime = betTime;
    }

    public String getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(String singleAmount) {
        this.singleAmount = singleAmount;
    }

    public String getEffectiveTradeAmount() {
        return effectiveTradeAmount;
    }

    public void setEffectiveTradeAmount(String effectiveTradeAmount) {
        this.effectiveTradeAmount = effectiveTradeAmount;
    }

    public String getPayoutTime() {
        return payoutTime;
    }

    public void setPayoutTime(String payoutTime) {
        this.payoutTime = payoutTime;
    }

    public String getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(String profitAmount) {
        this.profitAmount = profitAmount;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getMatchIndex() {
        return matchIndex;
    }

    public void setMatchIndex(String matchIndex) {
        this.matchIndex = matchIndex;
    }

    public String getTeamBetCode() {
        return teamBetCode;
    }

    public void setTeamBetCode(String teamBetCode) {
        this.teamBetCode = teamBetCode;
    }

    public String getHandicap() {
        return Handicap;
    }

    public void setHandicap(String handicap) {
        Handicap = handicap;
    }

    public String getOddsTypeName() {
        return oddsTypeName;
    }

    public void setOddsTypeName(String oddsTypeName) {
        this.oddsTypeName = oddsTypeName;
    }

    public String getWagerOdds() {
        return wagerOdds;
    }

    public void setWagerOdds(String wagerOdds) {
        this.wagerOdds = wagerOdds;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }


}
