package com.wlm.wlm.entity;

/**
 * Created by LG on 2018/11/27.
 */

public class TbShopBean<T> {
    private String ItemUrl;
    private String UserType;
    private T SmallImages;
    private String NumIid;
    private String PictUrl;
    private String Nick;
    private String TkRate;
    private String ReservePrice;
    private String Provcity;
    private String SellerId;
    private String ZkFinalPrice;
    private String Volume;
    private String TZkFinalPriceWap;
    private String ClickUrl;
    private String ShopTitle;
    private String Title;

    public String getItemUrl() {
        return ItemUrl;
    }

    public void setItemUrl(String itemUrl) {
        ItemUrl = itemUrl;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public T getSmallImages() {
        return SmallImages;
    }

    public void setSmallImages(T smallImages) {
        SmallImages = smallImages;
    }

    public String getNumIid() {
        return NumIid;
    }

    public void setNumIid(String numIid) {
        NumIid = numIid;
    }

    public String getPictUrl() {
        return PictUrl;
    }

    public void setPictUrl(String pictUrl) {
        PictUrl = pictUrl;
    }

    public String getNick() {
        return Nick;
    }

    public void setNick(String nick) {
        Nick = nick;
    }

    public String getTkRate() {
        return TkRate;
    }

    public void setTkRate(String tkRate) {
        TkRate = tkRate;
    }

    public String getReservePrice() {
        return ReservePrice;
    }

    public void setReservePrice(String reservePrice) {
        ReservePrice = reservePrice;
    }

    public String getProvcity() {
        return Provcity;
    }

    public void setProvcity(String provcity) {
        Provcity = provcity;
    }

    public String getSellerId() {
        return SellerId;
    }

    public void setSellerId(String sellerId) {
        SellerId = sellerId;
    }

    public String getZkFinalPrice() {
        return ZkFinalPrice;
    }

    public void setZkFinalPrice(String zkFinalPrice) {
        ZkFinalPrice = zkFinalPrice;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public String getTZkFinalPriceWap() {
        return TZkFinalPriceWap;
    }

    public void setTZkFinalPriceWap(String TZkFinalPriceWap) {
        this.TZkFinalPriceWap = TZkFinalPriceWap;
    }

    public String getClickUrl() {
        return ClickUrl;
    }

    public void setClickUrl(String clickUrl) {
        ClickUrl = clickUrl;
    }

    public String getShopTitle() {
        return ShopTitle;
    }

    public void setShopTitle(String shopTitle) {
        ShopTitle = shopTitle;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
