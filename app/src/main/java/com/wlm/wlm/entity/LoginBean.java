package com.wlm.wlm.entity;

import java.io.Serializable;

/**
 * 登陆实体类
 * Created by LG on 2018/11/30.
 */
public class LoginBean  implements Serializable {

    private String UserId;
    private String UserName;
    private String Mobile;
    private String Unionid;
    private String OpenId;
    private String UserLevel;
    private String UserLevelName;
    private String Portrait;
    private String NickName;
    private String LastLoginTime;
    private String VipValidity;
    private String ActivationTime;
    private String RefereesName;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getUnionid() {
        return Unionid;
    }

    public void setUnionid(String unionid) {
        Unionid = unionid;
    }

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String openId) {
        OpenId = openId;
    }

    public String getUserLevel() {
        return UserLevel;
    }

    public void setUserLevel(String userLevel) {
        UserLevel = userLevel;
    }

    public String getUserLevelName() {
        return UserLevelName;
    }

    public void setUserLevelName(String userLevelName) {
        UserLevelName = userLevelName;
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

    public String getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        LastLoginTime = lastLoginTime;
    }

    public String getVipValidity() {
        return VipValidity;
    }

    public void setVipValidity(String vipValidity) {
        VipValidity = vipValidity;
    }

    public String getActivationTime() {
        return ActivationTime;
    }

    public void setActivationTime(String activationTime) {
        ActivationTime = activationTime;
    }

    public String getRefereesName() {
        return RefereesName;
    }

    public void setRefereesName(String refereesName) {
        RefereesName = refereesName;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "UserId='" + UserId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Unionid='" + Unionid + '\'' +
                ", OpenId='" + OpenId + '\'' +
                ", UserLevel='" + UserLevel + '\'' +
                ", UserLevelName='" + UserLevelName + '\'' +
                ", Portrait='" + Portrait + '\'' +
                ", NickName='" + NickName + '\'' +
                ", LastLoginTime='" + LastLoginTime + '\'' +
                ", VipValidity='" + VipValidity + '\'' +
                ", ActivationTime='" + ActivationTime + '\'' +
                ", RefereesName='" + RefereesName + '\'' +
                '}';
    }
}