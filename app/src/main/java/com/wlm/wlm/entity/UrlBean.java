package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/13.
 */
public class UrlBean {

    private String imgUrl;
    private String ServiesUrl;
    private String SharedWebUrl;
    private String UpgradeUrl;
    private String UpgradeToken;

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
}
