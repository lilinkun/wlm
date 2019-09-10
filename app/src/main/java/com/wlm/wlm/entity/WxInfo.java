package com.wlm.wlm.entity;

/**
 * Created by LG on 2019/9/10.
 */
public class WxInfo {
    private String appId;
    private String prepayid;
    private String timeStamp;
    private String nonceStr;
    private String paySign;
    private String partnerid;

    public String getAppId() {
        return "wx11a116ef840f67f9";
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }
}
