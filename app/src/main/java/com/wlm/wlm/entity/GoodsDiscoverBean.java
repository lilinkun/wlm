package com.wlm.wlm.entity;

/**
 * Created by LG on 2019/10/25.
 */
public class GoodsDiscoverBean {
    private int GoodsDiscoverId;
    private String GoodsId;
    private String DiscoverName;
    private String DiscoverDesc;
    //1为图片，2为视频
    private int DiscoverType;
    private String CreateDate;
    private int DiscoverShowNum;
    private String FileUrl;
    private int DiscoverStatus;
    private String GoodsName;
    private int GoodsNumber;
    private String GoodsSn;
    private int GoodsType;
    private boolean IsOnSale;
    private double MarketPrice;
    private double Price;
    private String GoodsImg;
    private int UseNumber;
    private String VideoUrl;
    private int DiscoverHeight;
    private int DiscoverWidth;

    public int getGoodsDiscoverId() {
        return GoodsDiscoverId;
    }

    public void setGoodsDiscoverId(int goodsDiscoverId) {
        GoodsDiscoverId = goodsDiscoverId;
    }

    public String getGoodsId() {
        return GoodsId;
    }

    public void setGoodsId(String goodsId) {
        GoodsId = goodsId;
    }

    public String getDiscoverName() {
        return DiscoverName;
    }

    public void setDiscoverName(String discoverName) {
        DiscoverName = discoverName;
    }

    public String getDiscoverDesc() {
        return DiscoverDesc;
    }

    public void setDiscoverDesc(String discoverDesc) {
        DiscoverDesc = discoverDesc;
    }

    public int getDiscoverType() {
        return DiscoverType;
    }

    public void setDiscoverType(int discoverType) {
        DiscoverType = discoverType;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public int getDiscoverShowNum() {
        return DiscoverShowNum;
    }

    public void setDiscoverShowNum(int discoverShowNum) {
        DiscoverShowNum = discoverShowNum;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }

    public int getDiscoverStatus() {
        return DiscoverStatus;
    }

    public void setDiscoverStatus(int discoverStatus) {
        DiscoverStatus = discoverStatus;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public int getGoodsNumber() {
        return GoodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        GoodsNumber = goodsNumber;
    }

    public String getGoodsSn() {
        return GoodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        GoodsSn = goodsSn;
    }

    public int getGoodsType() {
        return GoodsType;
    }

    public void setGoodsType(int goodsType) {
        GoodsType = goodsType;
    }

    public boolean isOnSale() {
        return IsOnSale;
    }

    public void setOnSale(boolean onSale) {
        IsOnSale = onSale;
    }

    public double getMarketPrice() {
        return MarketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        MarketPrice = marketPrice;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getGoodsImg() {
        return GoodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        GoodsImg = goodsImg;
    }

    public int getUseNumber() {
        return UseNumber;
    }

    public void setUseNumber(int useNumber) {
        UseNumber = useNumber;
    }

    public int getDiscoverHeight() {
        return DiscoverHeight;
    }

    public void setDiscoverHeight(int discoverHeight) {
        DiscoverHeight = discoverHeight;
    }

    public int getDiscoverWidth() {
        return DiscoverWidth;
    }

    public void setDiscoverWidth(int discoverWidth) {
        DiscoverWidth = discoverWidth;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }
}
