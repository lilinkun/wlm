package com.wlm.wlm.entity;

/**
 * Created by LG on 2019/1/4.
 */
public class WxRechangeBean {
    private int bank_type;
    private String sign;
    private double Charge_Amt;
    private String Bill_No;
    private String Batch_No;
    private int Logo_ID;
    private boolean status;
    private WxInfoBean Data;

    public int getBank_type() {
        return bank_type;
    }

    public void setBank_type(int bank_type) {
        this.bank_type = bank_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public double getCharge_Amt() {
        return Charge_Amt;
    }

    public void setCharge_Amt(double charge_Amt) {
        Charge_Amt = charge_Amt;
    }

    public String getBill_No() {
        return Bill_No;
    }

    public void setBill_No(String bill_No) {
        Bill_No = bill_No;
    }

    public String getBatch_No() {
        return Batch_No;
    }

    public void setBatch_No(String batch_No) {
        Batch_No = batch_No;
    }

    public int getLogo_ID() {
        return Logo_ID;
    }

    public void setLogo_ID(int logo_ID) {
        Logo_ID = logo_ID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public WxInfoBean getData() {
        return Data;
    }

    public void setData(WxInfoBean data) {
        Data = data;
    }
}
