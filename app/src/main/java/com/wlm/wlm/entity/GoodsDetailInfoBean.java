package com.wlm.wlm.entity;

import java.io.Serializable;

/**
 * Created by LG on 2019/9/7.
 */
public class GoodsDetailInfoBean<T> implements Serializable {
    //商品id
    private String GoodsId;
    //分类ID
    private String CategoryId;
    private String CategoryPath;
    private String BrandId;
    private String GoodsSn;
    private String GoodsName;
    private String GoodsSmallName;
    private String GoodsSpec1;
    private String GoodsSpec2;
    private String GoodsUnit;
    private String GoodsGG;
    private String BrowserCount;
    private int GoodsNumber;
    private int UseNumber;
    private String GoodsWeight;
    //市场价
    private double MarketPrice;
    //折后价
    private double Price;
    private String GoodsBrief;
    private String GoodsDesc;
    private String GoodsImg;
    private String GoodsImgList;
    private String GoodsIndexType;
    private String GoodsIndexImg;
    private boolean IsCarriage;
    private boolean IsOnSale;
    private boolean IsDelete;
    private int GoodsFlag;
    private int GoodsType;
    private String GoodsTypeName;
    private double Integral;
    private String ReturnIntegral;
    private String CreateDate;
    private String LastUpdate;
    private String MobileDesc;
    private int Qty;
    private String BackSay;
    private String SortRank;
    private String BeginDate;
    private String EndDate;
    private String IsSetMeal;
    private String TeamType;
    private String TeamTypeName;
    private int UserLevel;
    private T Attr;

    public int getUserLevel() {
        return UserLevel;
    }

    public void setUserLevel(int userLevel) {
        UserLevel = userLevel;
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

    public String getGoodsUnit() {
        return GoodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        GoodsUnit = goodsUnit;
    }

    public String getGoodsGG() {
        return GoodsGG;
    }

    public void setGoodsGG(String goodsGG) {
        GoodsGG = goodsGG;
    }

    public String getBrowserCount() {
        return BrowserCount;
    }

    public void setBrowserCount(String browserCount) {
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

    public String getGoodsWeight() {
        return GoodsWeight;
    }

    public void setGoodsWeight(String goodsWeight) {
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

    public String getGoodsIndexType() {
        return GoodsIndexType;
    }

    public void setGoodsIndexType(String goodsIndexType) {
        GoodsIndexType = goodsIndexType;
    }

    public String getGoodsIndexImg() {
        return GoodsIndexImg;
    }

    public void setGoodsIndexImg(String goodsIndexImg) {
        GoodsIndexImg = goodsIndexImg;
    }

    public boolean isCarriage() {
        return IsCarriage;
    }

    public void setCarriage(boolean carriage) {
        IsCarriage = carriage;
    }

    public boolean isOnSale() {
        return IsOnSale;
    }

    public void setOnSale(boolean onSale) {
        IsOnSale = onSale;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public int getGoodsFlag() {
        return GoodsFlag;
    }

    public void setGoodsFlag(int goodsFlag) {
        GoodsFlag = goodsFlag;
    }

    public int getGoodsType() {
        return GoodsType;
    }

    public void setGoodsType(int goodsType) {
        GoodsType = goodsType;
    }

    public double getIntegral() {
        return Integral;
    }

    public void setIntegral(double integral) {
        Integral = integral;
    }

    public String getReturnIntegral() {
        return ReturnIntegral;
    }

    public void setReturnIntegral(String returnIntegral) {
        ReturnIntegral = returnIntegral;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public String getMobileDesc() {
        return MobileDesc;
    }

    public void setMobileDesc(String mobileDesc) {
        MobileDesc = mobileDesc;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getBackSay() {
        return BackSay;
    }

    public void setBackSay(String backSay) {
        BackSay = backSay;
    }

    public String getSortRank() {
        return SortRank;
    }

    public void setSortRank(String sortRank) {
        SortRank = sortRank;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(String beginDate) {
        BeginDate = beginDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getIsSetMeal() {
        return IsSetMeal;
    }

    public void setIsSetMeal(String isSetMeal) {
        IsSetMeal = isSetMeal;
    }

    public String getTeamType() {
        return TeamType;
    }

    public void setTeamType(String teamType) {
        TeamType = teamType;
    }

    public String getTeamTypeName() {
        return TeamTypeName;
    }

    public void setTeamTypeName(String teamTypeName) {
        TeamTypeName = teamTypeName;
    }

    public String getGoodsTypeName() {
        return GoodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        GoodsTypeName = goodsTypeName;
    }

    public T getAttr() {
        return Attr;
    }

    public void setAttr(T attr) {
        Attr = attr;
    }



    @Override
    public String toString() {
        return "GoodsDetailInfoBean{" +
                "GoodsId='" + GoodsId + '\'' +
                ", CategoryId='" + CategoryId + '\'' +
                ", CategoryPath='" + CategoryPath + '\'' +
                ", BrandId='" + BrandId + '\'' +
                ", GoodsSn='" + GoodsSn + '\'' +
                ", GoodsName='" + GoodsName + '\'' +
                ", GoodsSmallName='" + GoodsSmallName + '\'' +
                ", GoodsSpec1='" + GoodsSpec1 + '\'' +
                ", GoodsSpec2='" + GoodsSpec2 + '\'' +
                ", GoodsUnit='" + GoodsUnit + '\'' +
                ", GoodsGG='" + GoodsGG + '\'' +
                ", BrowserCount='" + BrowserCount + '\'' +
                ", GoodsNumber=" + GoodsNumber +
                ", UseNumber=" + UseNumber +
                ", GoodsWeight='" + GoodsWeight + '\'' +
                ", MarketPrice=" + MarketPrice +
                ", Price=" + Price +
                ", GoodsBrief='" + GoodsBrief + '\'' +
                ", GoodsDesc='" + GoodsDesc + '\'' +
                ", GoodsImg='" + GoodsImg + '\'' +
                ", GoodsImgList='" + GoodsImgList + '\'' +
                ", GoodsIndexType='" + GoodsIndexType + '\'' +
                ", GoodsIndexImg='" + GoodsIndexImg + '\'' +
                ", IsCarriage='" + IsCarriage + '\'' +
                ", IsOnSale='" + IsOnSale + '\'' +
                ", IsDelete='" + IsDelete + '\'' +
                ", GoodsFlag='" + GoodsFlag + '\'' +
                ", GoodsType='" + GoodsType + '\'' +
                ", GoodsTypeName='" + GoodsTypeName + '\'' +
                ", Integral=" + Integral +
                ", ReturnIntegral='" + ReturnIntegral + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", LastUpdate='" + LastUpdate + '\'' +
                ", MobileDesc='" + MobileDesc + '\'' +
                ", Qty='" + Qty + '\'' +
                ", BackSay='" + BackSay + '\'' +
                ", SortRank='" + SortRank + '\'' +
                ", BeginDate='" + BeginDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", IsSetMeal='" + IsSetMeal + '\'' +
                ", TeamType='" + TeamType + '\'' +
                ", TeamTypeName='" + TeamTypeName + '\'' +
                ", Attr=" + Attr +
                '}';
    }
}
