package com.wlm.wlm.entity;

/**
 * 我的粉丝
 * Created by LG on 2019/9/20.
 */
public class FansBean {
    private String UserLevelName;
    private int UserId;
    private String UserName;
    private int UserStatus;
    private String Portrait;
    private int UserLevel;
    private int Referees;
    private String NickName;
    private String SurName;
    private String ActivationTime;
    private String Mobile;
    private int RefereesTeamBCount;

    public String getUserLevelName() {
        return UserLevelName;
    }

    public void setUserLevelName(String userLevelName) {
        UserLevelName = userLevelName;
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

    public int getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(int userStatus) {
        UserStatus = userStatus;
    }

    public String getPortrait() {
        return Portrait;
    }

    public void setPortrait(String portrait) {
        Portrait = portrait;
    }

    public int getUserLevel() {
        return UserLevel;
    }

    public void setUserLevel(int userLevel) {
        UserLevel = userLevel;
    }

    public int getReferees() {
        return Referees;
    }

    public void setReferees(int referees) {
        Referees = referees;
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

    public String getActivationTime() {
        return ActivationTime;
    }

    public void setActivationTime(String activationTime) {
        ActivationTime = activationTime;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public int getRefereesTeamBCount() {
        return RefereesTeamBCount;
    }

    public void setRefereesTeamBCount(int refereesTeamBCount) {
        RefereesTeamBCount = refereesTeamBCount;
    }
}
