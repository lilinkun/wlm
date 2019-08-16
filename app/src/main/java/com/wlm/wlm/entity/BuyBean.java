package com.wlm.wlm.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LG on 2018/12/15.
 */
public class BuyBean implements Serializable{
    private ArrayList<OrderListBean<ArrayList<ChildListBean>>> storemodel;
    private UserModelBean usermodel;
    private AddressBean useraddressmodel;

    public ArrayList<OrderListBean<ArrayList<ChildListBean>>> getStoremodel() {
        return storemodel;
    }

    public void setStoremodel(ArrayList<OrderListBean<ArrayList<ChildListBean>>> storemodel) {
        this.storemodel = storemodel;
    }

    public UserModelBean getUsermodel() {
        return usermodel;
    }

    public void setUsermodel(UserModelBean usermodel) {
        this.usermodel = usermodel;
    }

    public AddressBean getUseraddressmodel() {
        return useraddressmodel;
    }

    public void setUseraddressmodel(AddressBean useraddressmodel) {
        this.useraddressmodel = useraddressmodel;
    }
}
