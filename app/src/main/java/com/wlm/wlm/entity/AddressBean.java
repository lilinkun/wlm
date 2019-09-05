package com.wlm.wlm.entity;

import java.io.Serializable;

/**
 * 收货地址实体类
 * Created by LG on 2018/12/9.
 */
public class AddressBean implements Serializable{
    private String AddressID;//收货人Id
    private String Name;//收货人姓名
    private String country;//国家
    private String prov;//省份
    private String city;//城市
    private String area;//地区
    private String town;//街道
    private String Address;//详细地址
    private String Post;//邮编
    private String Phone;//电话
    private String Mobile;//手机
    private String CreateDate;//创建时间
    private boolean IsDefault;//是否默认
    private String UserId;//用户id
    private String AddressName;//详细的省市区

    public String getAddressID() {
        return AddressID;
    }

    public void setAddressID(String addressID) {
        AddressID = addressID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public boolean isDefault() {
        return IsDefault;
    }

    public void setDefault(boolean aDefault) {
        IsDefault = aDefault;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAddressName() {
        return AddressName;
    }

    public void setAddressName(String addressName) {
        AddressName = addressName;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "AddressID='" + AddressID + '\'' +
                ", Name='" + Name + '\'' +
                ", country='" + country + '\'' +
                ", prov='" + prov + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", town='" + town + '\'' +
                ", Address='" + Address + '\'' +
                ", Post='" + Post + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", IsDefault=" + IsDefault +
                ", UserId='" + UserId + '\'' +
                ", AddressName='" + AddressName + '\'' +
                '}';
    }
}
