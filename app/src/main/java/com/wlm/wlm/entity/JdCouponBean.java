package com.wlm.wlm.entity;

import java.io.Serializable;

/**
 * Created by LG on 2019/1/31.
 */

public class JdCouponBean implements Serializable {
    private JdCouponList[] couponList;

    public JdCouponList[] getCouponList() {
        return couponList;
    }

    public void setCouponList(JdCouponList[] couponList) {
        this.couponList = couponList;
    }


    public class JdCouponList implements Serializable{
        public int bindType;
        public int discount;
        public long getEndTime;
        public long getStartTime;
        public String link;
        public int platformType;
        public int quota;
        public long useEndTime;
        public long useStartTime;

        public int getBindType() {
            return bindType;
        }

        public void setBindType(int bindType) {
            this.bindType = bindType;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public long getGetEndTime() {
            return getEndTime;
        }

        public void setGetEndTime(long getEndTime) {
            this.getEndTime = getEndTime;
        }

        public long getGetStartTime() {
            return getStartTime;
        }

        public void setGetStartTime(long getStartTime) {
            this.getStartTime = getStartTime;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getPlatformType() {
            return platformType;
        }

        public void setPlatformType(int platformType) {
            this.platformType = platformType;
        }

        public int getQuota() {
            return quota;
        }

        public void setQuota(int quota) {
            this.quota = quota;
        }

        public long getUseEndTime() {
            return useEndTime;
        }

        public void setUseEndTime(long useEndTime) {
            this.useEndTime = useEndTime;
        }

        public long getUseStartTime() {
            return useStartTime;
        }

        public void setUseStartTime(long useStartTime) {
            this.useStartTime = useStartTime;
        }
    }
}
