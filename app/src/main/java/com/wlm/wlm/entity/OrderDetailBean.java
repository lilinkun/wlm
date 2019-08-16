package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/20.
 */

public class OrderDetailBean {
    private double shipping_fee;
    private double money_payid;
    private double useIntegral;
    private double order_amount;
    private String order_status;
    private String order_id;
    private String order_sn;
    private String confirm_time;
    private String pay_time;
    private String shipping_time;
    private String address;
    private String addressName;
    private ArrayList<SelfOrderInfoBean> OrderGoodsItem;
    private StoreModelBean StoreModel;
    private String Effective_Payment_Time;
    private String consignee;
    private String mobile;
    private String lgs_id;
    private String lgs_name;
    private String lgs_number;
    private String lgs_say;
    private String take_time;

    public double getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(double shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public double getMoney_payid() {
        return money_payid;
    }

    public void setMoney_payid(double money_payid) {
        this.money_payid = money_payid;
    }

    public double getUseIntegral() {
        return useIntegral;
    }

    public void setUseIntegral(double useIntegral) {
        this.useIntegral = useIntegral;
    }

    public double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(double order_amount) {
        this.order_amount = order_amount;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public ArrayList<SelfOrderInfoBean> getOrderGoodsItem() {
        return OrderGoodsItem;
    }

    public void setOrderGoodsItem(ArrayList<SelfOrderInfoBean> orderGoodsItem) {
        OrderGoodsItem = orderGoodsItem;
    }

    public StoreModelBean getStoreModel() {
        return StoreModel;
    }

    public void setStoreModel(StoreModelBean storeModel) {
        StoreModel = storeModel;
    }

    public String getEffective_Payment_Time() {
        return Effective_Payment_Time;
    }

    public void setEffective_Payment_Time(String effective_Payment_Time) {
        Effective_Payment_Time = effective_Payment_Time;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLgs_id() {
        return lgs_id;
    }

    public void setLgs_id(String lgs_id) {
        this.lgs_id = lgs_id;
    }

    public String getLgs_name() {
        return lgs_name;
    }

    public void setLgs_name(String lgs_name) {
        this.lgs_name = lgs_name;
    }

    public String getLgs_number() {
        return lgs_number;
    }

    public void setLgs_number(String lgs_number) {
        this.lgs_number = lgs_number;
    }

    public String getLgs_say() {
        return lgs_say;
    }

    public void setLgs_say(String lgs_say) {
        this.lgs_say = lgs_say;
    }

    public String getTake_time() {
        return take_time;
    }

    public void setTake_time(String take_time) {
        this.take_time = take_time;
    }
}
