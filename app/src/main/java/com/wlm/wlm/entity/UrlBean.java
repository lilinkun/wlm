package com.wlm.wlm.entity;

/**
 * Created by LG on 2018/12/13.
 */
public class UrlBean {

    private String imgUrl;
    private String ServiesUrl;
    private String SharedWebUrl;
    private String UpgradeUrl;
    private String UpgradeToken;
    private String RegisterRequirements;
    private String KFMobile;
    private String ServiesVip;
    private int IsAuditing;
    private int IsAndroidAuditing;
    private String LogisticsUrl;
    private String ShareImg;

    public String getLogisticsUrl() {
        return LogisticsUrl;
    }

    public void setLogisticsUrl(String logisticsUrl) {
        LogisticsUrl = logisticsUrl;
    }

    public int getIsAndroidAuditing() {
        return IsAndroidAuditing;
    }

    public void setIsAndroidAuditing(int isAndroidAuditing) {
        IsAndroidAuditing = isAndroidAuditing;
    }

    public int getIsAuditing() {
        return IsAuditing;
    }

    public void setIsAuditing(int isAuditing) {
        IsAuditing = isAuditing;
    }

    public String getServiesVip() {
        return ServiesVip;
    }

    public void setServiesVip(String serviesVip) {
        ServiesVip = serviesVip;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getServiesUrl() {
        return ServiesUrl;
    }

    public void setServiesUrl(String serviesUrl) {
        ServiesUrl = serviesUrl;
    }

    public String getSharedWebUrl() {
        return SharedWebUrl;
    }

    public void setSharedWebUrl(String sharedWebUrl) {
        SharedWebUrl = sharedWebUrl;
    }

    public String getUpgradeUrl() {
        return UpgradeUrl;
    }

    public void setUpgradeUrl(String upgradeUrl) {
        UpgradeUrl = upgradeUrl;
    }

    public String getUpgradeToken() {
        return UpgradeToken;
    }

    public void setUpgradeToken(String upgradeToken) {
        UpgradeToken = upgradeToken;
    }

    public String getRegisterRequirements() {
        return RegisterRequirements;
    }

    public void setRegisterRequirements(String registerRequirements) {
        RegisterRequirements = registerRequirements;
    }

    public String getKFMobile() {
        return KFMobile;
    }

    public void setKFMobile(String KFMobile) {
        this.KFMobile = KFMobile;
    }

    public String getShareImg() {
        return ShareImg;
    }

    public void setShareImg(String shareImg) {
        ShareImg = shareImg;
    }
}
