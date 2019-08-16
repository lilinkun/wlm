package com.wlm.wlm.entity;

import java.io.Serializable;

/**
 * Created by LG on 2019/1/29.
 */

public class JdGoodsBean implements Serializable{
    private String brandCode;
    private String brandName;
    private JdCategoryInfoBean categoryInfo;
    private int comments;
    private JdCommissionInfoBean commissionInfo;
    private JdCouponBean couponInfo;
    private int goodCommentsShare;
    private JdImageBean imageInfo;
    private int inOrderCount30Days;
    private int inOrderCount30DaysSku;
    private int isHot;
    private String materialUrl;
    private JdPriceInfo priceInfo;
    private JdResourceInfo resourceInfo;
    private String spuid;
    private String skuName;
    private String skuId;
    private JdShopInfo shopInfo;
    public static final long serialVersionUID = 124337;

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public JdCategoryInfoBean getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(JdCategoryInfoBean categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public JdCommissionInfoBean getCommissionInfo() {
        return commissionInfo;
    }

    public void setCommissionInfo(JdCommissionInfoBean commissionInfo) {
        this.commissionInfo = commissionInfo;
    }

    public JdCouponBean getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(JdCouponBean couponInfo) {
        this.couponInfo = couponInfo;
    }

    public int getGoodCommentsShare() {
        return goodCommentsShare;
    }

    public void setGoodCommentsShare(int goodCommentsShare) {
        this.goodCommentsShare = goodCommentsShare;
    }

    public JdImageBean getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(JdImageBean imageInfo) {
        this.imageInfo = imageInfo;
    }

    public int getInOrderCount30Days() {
        return inOrderCount30Days;
    }

    public void setInOrderCount30Days(int inOrderCount30Days) {
        this.inOrderCount30Days = inOrderCount30Days;
    }

    public int getInOrderCount30DaysSku() {
        return inOrderCount30DaysSku;
    }

    public void setInOrderCount30DaysSku(int inOrderCount30DaysSku) {
        this.inOrderCount30DaysSku = inOrderCount30DaysSku;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    public JdPriceInfo getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(JdPriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    public JdResourceInfo getResourceInfo() {
        return resourceInfo;
    }

    public void setResourceInfo(JdResourceInfo resourceInfo) {
        this.resourceInfo = resourceInfo;
    }

    public String getSpuid() {
        return spuid;
    }

    public void setSpuid(String spuid) {
        this.spuid = spuid;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public JdShopInfo getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(JdShopInfo shopInfo) {
        this.shopInfo = shopInfo;
    }


    public class JdPriceInfo implements Serializable{
        private double price;
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
    }

    public class JdResourceInfo implements Serializable{
        private int eliteId;
        private String eliteName;
        public int getEliteId() {
            return eliteId;
        }
        public void setEliteId(int eliteId) {
            this.eliteId = eliteId;
        }
        public String getEliteName() {
            return eliteName;
        }
        public void setEliteName(String eliteName) {
            this.eliteName = eliteName;
        }
    }

    public class JdShopInfo implements Serializable{
        private String shopName;
        private int shopId;
        public String getShopName() {
            return shopName;
        }
        public void setShopName(String shopName) {
            this.shopName = shopName;
        }
        public int getShopId() {
            return shopId;
        }
        public void setShopId(int shopId) {
            this.shopId = shopId;
        }
    }
}
