package com.wlm.wlm.entity;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 收藏实体类
 * Created by LG on 2018/12/11.
 */
public class CollectBean implements Serializable{
    private String CollectId;
    private String GoodsId;
    private String CategoryId;
    private String CategoryPath;
    private String BrandId;
    private String GoodsSn;
    private String GoodsName;
    private String GoodsSmallName;
    private String GoodsSpec1;
    private String GoodsSpec2;
    private int BrowserCount;
    private int GoodsNumber;
    private int UseNumber;
    private double GoodsWeight;
    private double MarketPrice;
    private double Price;
    private String GoodsBrief;
    private String GoodsDesc;
    private String GoodsImg;
    private String GoodsImgList;
    private int Integral;
    private int GoodsType;
    public static final long serialVersionUID = 123237;

    public CollectBean(String collectId, String goodsId, String categoryId, String categoryPath, String brandId, String goodsSn, String goodsName, String goodsSmallName, String goodsSpec1, String goodsSpec2, int browserCount, int goodsNumber, int useNumber, double goodsWeight, double marketPrice, double price, String goodsBrief, String goodsDesc, String goodsImg, String goodsImgList, int integral, int goodsType) {
        CollectId = collectId;
        GoodsId = goodsId;
        CategoryId = categoryId;
        CategoryPath = categoryPath;
        BrandId = brandId;
        GoodsSn = goodsSn;
        GoodsName = goodsName;
        GoodsSmallName = goodsSmallName;
        GoodsSpec1 = goodsSpec1;
        GoodsSpec2 = goodsSpec2;
        BrowserCount = browserCount;
        GoodsNumber = goodsNumber;
        UseNumber = useNumber;
        GoodsWeight = goodsWeight;
        MarketPrice = marketPrice;
        Price = price;
        GoodsBrief = goodsBrief;
        GoodsDesc = goodsDesc;
        GoodsImg = goodsImg;
        GoodsImgList = goodsImgList;
        Integral = integral;
        GoodsType = goodsType;
    }


    public String getCollectId() {
        return CollectId;
    }

    public void setCollectId(String collectId) {
        CollectId = collectId;
    }

    public String getGoodsId() {
        return GoodsId;
    }

    public void setGoodsId(String goodsId) {
        GoodsId = goodsId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryPath() {
        return CategoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        CategoryPath = categoryPath;
    }

    public String getBrandId() {
        return BrandId;
    }

    public void setBrandId(String brandId) {
        BrandId = brandId;
    }

    public String getGoodsSn() {
        return GoodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        GoodsSn = goodsSn;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public String getGoodsSmallName() {
        return GoodsSmallName;
    }

    public void setGoodsSmallName(String goodsSmallName) {
        GoodsSmallName = goodsSmallName;
    }

    public String getGoodsSpec1() {
        return GoodsSpec1;
    }

    public void setGoodsSpec1(String goodsSpec1) {
        GoodsSpec1 = goodsSpec1;
    }

    public String getGoodsSpec2() {
        return GoodsSpec2;
    }

    public void setGoodsSpec2(String goodsSpec2) {
        GoodsSpec2 = goodsSpec2;
    }

    public int getBrowserCount() {
        return BrowserCount;
    }

    public void setBrowserCount(int browserCount) {
        BrowserCount = browserCount;
    }

    public int getGoodsNumber() {
        return GoodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        GoodsNumber = goodsNumber;
    }

    public int getUseNumber() {
        return UseNumber;
    }

    public void setUseNumber(int useNumber) {
        UseNumber = useNumber;
    }

    public double getGoodsWeight() {
        return GoodsWeight;
    }

    public void setGoodsWeight(double goodsWeight) {
        GoodsWeight = goodsWeight;
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

    public String getGoodsBrief() {
        return GoodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        GoodsBrief = goodsBrief;
    }

    public String getGoodsDesc() {
        return GoodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        GoodsDesc = goodsDesc;
    }

    public String getGoodsImg() {
        return GoodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        GoodsImg = goodsImg;
    }

    public String getGoodsImgList() {
        return GoodsImgList;
    }

    public void setGoodsImgList(String goodsImgList) {
        GoodsImgList = goodsImgList;
    }

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public int getGoodsType() {
        return GoodsType;
    }

    public void setGoodsType(int goodsType) {
        GoodsType = goodsType;
    }
}
