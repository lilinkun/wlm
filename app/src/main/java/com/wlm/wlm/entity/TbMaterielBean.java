package com.wlm.wlm.entity;

import com.wlm.wlm.util.B_Converter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LG on 2018/11/29.
 */
@Entity
public class TbMaterielBean implements Serializable{
    private String CategoryId;
    private String CategoryName;
    private String CommissionRate;
    private String CommissionType;
    private String CouponEndTime;
    private String CouponId;
    private String CouponInfo;
    private String CouponRemainCount;
    private String CouponShareUrl;
    private String CouponStartTime;
    private String CouponTotalCount;
    private String IncludeDxjh;
    private String IncludeMkt;
    private String InfoDxjh;
    private String ItemUrl;
    private String JddNum;
    private String JddPrice;
    private String LevelOneCategoryId;
    private String LevelOneCategoryName;
    @Id
    private String NumIid;
    private String Oetime;
    private String Ostime;
    private String PictUrl;
    private String Provcity;
    private String ReservePrice;
    private String SellerId;
    private String ShopDsr;
    private String ShopTitle;
    private String ShortTitle;
    @Convert(columnType = String.class, converter = B_Converter.class)
    private List<String> SmallImages;
    private String Title;
    private String TkTotalCommi;
    private String TkTotalSales;
    private String Url;
    private String UserType;
    private String UvSumPreSale;
    private String Volume;
    private String WhiteImage;
    private String ZkFinalPrice;
    public static final long serialVersionUID = 123225;


    @Generated(hash = 2120883498)
    public TbMaterielBean() {
    }


    @Generated(hash = 1510603487)
    public TbMaterielBean(String CategoryId, String CategoryName,
            String CommissionRate, String CommissionType, String CouponEndTime,
            String CouponId, String CouponInfo, String CouponRemainCount,
            String CouponShareUrl, String CouponStartTime, String CouponTotalCount,
            String IncludeDxjh, String IncludeMkt, String InfoDxjh, String ItemUrl,
            String JddNum, String JddPrice, String LevelOneCategoryId,
            String LevelOneCategoryName, String NumIid, String Oetime,
            String Ostime, String PictUrl, String Provcity, String ReservePrice,
            String SellerId, String ShopDsr, String ShopTitle, String ShortTitle,
            List<String> SmallImages, String Title, String TkTotalCommi,
            String TkTotalSales, String Url, String UserType, String UvSumPreSale,
            String Volume, String WhiteImage, String ZkFinalPrice) {
        this.CategoryId = CategoryId;
        this.CategoryName = CategoryName;
        this.CommissionRate = CommissionRate;
        this.CommissionType = CommissionType;
        this.CouponEndTime = CouponEndTime;
        this.CouponId = CouponId;
        this.CouponInfo = CouponInfo;
        this.CouponRemainCount = CouponRemainCount;
        this.CouponShareUrl = CouponShareUrl;
        this.CouponStartTime = CouponStartTime;
        this.CouponTotalCount = CouponTotalCount;
        this.IncludeDxjh = IncludeDxjh;
        this.IncludeMkt = IncludeMkt;
        this.InfoDxjh = InfoDxjh;
        this.ItemUrl = ItemUrl;
        this.JddNum = JddNum;
        this.JddPrice = JddPrice;
        this.LevelOneCategoryId = LevelOneCategoryId;
        this.LevelOneCategoryName = LevelOneCategoryName;
        this.NumIid = NumIid;
        this.Oetime = Oetime;
        this.Ostime = Ostime;
        this.PictUrl = PictUrl;
        this.Provcity = Provcity;
        this.ReservePrice = ReservePrice;
        this.SellerId = SellerId;
        this.ShopDsr = ShopDsr;
        this.ShopTitle = ShopTitle;
        this.ShortTitle = ShortTitle;
        this.SmallImages = SmallImages;
        this.Title = Title;
        this.TkTotalCommi = TkTotalCommi;
        this.TkTotalSales = TkTotalSales;
        this.Url = Url;
        this.UserType = UserType;
        this.UvSumPreSale = UvSumPreSale;
        this.Volume = Volume;
        this.WhiteImage = WhiteImage;
        this.ZkFinalPrice = ZkFinalPrice;
    }


    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCommissionRate() {
        return CommissionRate;
    }

    public void setCommissionRate(String commissionRate) {
        CommissionRate = commissionRate;
    }

    public String getCommissionType() {
        return CommissionType;
    }

    public void setCommissionType(String commissionType) {
        CommissionType = commissionType;
    }

    public String getCouponEndTime() {
        return CouponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        CouponEndTime = couponEndTime;
    }

    public String getCouponId() {
        return CouponId;
    }

    public void setCouponId(String couponId) {
        CouponId = couponId;
    }

    public String getCouponInfo() {
        return CouponInfo;
    }

    public void setCouponInfo(String couponInfo) {
        CouponInfo = couponInfo;
    }

    public String getCouponRemainCount() {
        return CouponRemainCount;
    }

    public void setCouponRemainCount(String couponRemainCount) {
        CouponRemainCount = couponRemainCount;
    }

    public String getCouponShareUrl() {
        return CouponShareUrl;
    }

    public void setCouponShareUrl(String couponShareUrl) {
        CouponShareUrl = couponShareUrl;
    }

    public String getCouponStartTime() {
        return CouponStartTime;
    }

    public void setCouponStartTime(String couponStartTime) {
        CouponStartTime = couponStartTime;
    }

    public String getCouponTotalCount() {
        return CouponTotalCount;
    }

    public void setCouponTotalCount(String couponTotalCount) {
        CouponTotalCount = couponTotalCount;
    }

    public String getIncludeDxjh() {
        return IncludeDxjh;
    }

    public void setIncludeDxjh(String includeDxjh) {
        IncludeDxjh = includeDxjh;
    }

    public String getIncludeMkt() {
        return IncludeMkt;
    }

    public void setIncludeMkt(String includeMkt) {
        IncludeMkt = includeMkt;
    }

    public String getInfoDxjh() {
        return InfoDxjh;
    }

    public void setInfoDxjh(String infoDxjh) {
        InfoDxjh = infoDxjh;
    }

    public String getItemUrl() {
        return ItemUrl;
    }

    public void setItemUrl(String itemUrl) {
        ItemUrl = itemUrl;
    }

    public String getJddNum() {
        return JddNum;
    }

    public void setJddNum(String jddNum) {
        JddNum = jddNum;
    }

    public String getJddPrice() {
        return JddPrice;
    }

    public void setJddPrice(String jddPrice) {
        JddPrice = jddPrice;
    }

    public String getLevelOneCategoryId() {
        return LevelOneCategoryId;
    }

    public void setLevelOneCategoryId(String levelOneCategoryId) {
        LevelOneCategoryId = levelOneCategoryId;
    }

    public String getLevelOneCategoryName() {
        return LevelOneCategoryName;
    }

    public void setLevelOneCategoryName(String levelOneCategoryName) {
        LevelOneCategoryName = levelOneCategoryName;
    }

    public String getNumIid() {
        return NumIid;
    }

    public void setNumIid(String numIid) {
        NumIid = numIid;
    }

    public String getOetime() {
        return Oetime;
    }

    public void setOetime(String oetime) {
        Oetime = oetime;
    }

    public String getOstime() {
        return Ostime;
    }

    public void setOstime(String ostime) {
        Ostime = ostime;
    }

    public String getPictUrl() {
        return PictUrl;
    }

    public void setPictUrl(String pictUrl) {
        PictUrl = pictUrl;
    }

    public String getProvcity() {
        return Provcity;
    }

    public void setProvcity(String provcity) {
        Provcity = provcity;
    }

    public String getReservePrice() {
        return ReservePrice;
    }

    public void setReservePrice(String reservePrice) {
        ReservePrice = reservePrice;
    }

    public String getSellerId() {
        return SellerId;
    }

    public void setSellerId(String sellerId) {
        SellerId = sellerId;
    }

    public String getShopDsr() {
        return ShopDsr;
    }

    public void setShopDsr(String shopDsr) {
        ShopDsr = shopDsr;
    }

    public String getShopTitle() {
        return ShopTitle;
    }

    public void setShopTitle(String shopTitle) {
        ShopTitle = shopTitle;
    }

    public String getShortTitle() {
        return ShortTitle;
    }

    public void setShortTitle(String shortTitle) {
        ShortTitle = shortTitle;
    }

    public List<String> getSmallImages() {
        return SmallImages;
    }

    public void setSmallImages(List<String> smallImages) {
        SmallImages = smallImages;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTkTotalCommi() {
        return TkTotalCommi;
    }

    public void setTkTotalCommi(String tkTotalCommi) {
        TkTotalCommi = tkTotalCommi;
    }

    public String getTkTotalSales() {
        return TkTotalSales;
    }

    public void setTkTotalSales(String tkTotalSales) {
        TkTotalSales = tkTotalSales;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getUvSumPreSale() {
        return UvSumPreSale;
    }

    public void setUvSumPreSale(String uvSumPreSale) {
        UvSumPreSale = uvSumPreSale;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public String getWhiteImage() {
        return WhiteImage;
    }

    public void setWhiteImage(String whiteImage) {
        WhiteImage = whiteImage;
    }

    public String getZkFinalPrice() {
        return ZkFinalPrice;
    }

    public void setZkFinalPrice(String zkFinalPrice) {
        ZkFinalPrice = zkFinalPrice;
    }
}
