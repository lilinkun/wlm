package com.wlm.wlm.entity;

/**
 * Created by LG on 2019/9/19.
 */
public class JoinGrouponBean {
    private int GoodsTeamApplyId;
    private int TeamId;
    private int UserId;
    private String UserName;
    private String CreateDate;
    private String OrderSn;
    private String Portrait;
    private String NickName;
    private String SurName;
    private String UserSex;

    public int getGoodsTeamApplyId() {
        return GoodsTeamApplyId;
    }

    public void setGoodsTeamApplyId(int goodsTeamApplyId) {
        GoodsTeamApplyId = goodsTeamApplyId;
    }

    public int getTeamId() {
        return TeamId;
    }

    public void setTeamId(int teamId) {
        TeamId = teamId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getOrderSn() {
        return OrderSn;
    }

    public void setOrderSn(String orderSn) {
        OrderSn = orderSn;
    }

    public String getPortrait() {
        return Portrait;
    }

    public void setPortrait(String portrait) {
        Portrait = portrait;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public String getUserSex() {
        return UserSex;
    }

    public void setUserSex(String userSex) {
        UserSex = userSex;
    }
}
