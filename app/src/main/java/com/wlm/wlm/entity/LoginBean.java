package com.wlm.wlm.entity;

import java.io.Serializable;

/**
 * 登陆实体类
 * Created by LG on 2018/11/30.
 */
public class LoginBean  implements Serializable {

    private String user_id;
    private String user_name;
    private String mobile;
    private String user_status;
    private String create_date;
    private String lastLogin_date;
    private String lastLogin_ip;
    private String thisLogin_date;
    private String thisLogin_ip;
    private String viplevel;
    private String HeadPic;
    private String nikeName;
    private String Name;
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getLastLogin_date() {
        return lastLogin_date;
    }

    public void setLastLogin_date(String lastLogin_date) {
        this.lastLogin_date = lastLogin_date;
    }

    public String getLastLogin_ip() {
        return lastLogin_ip;
    }

    public void setLastLogin_ip(String lastLogin_ip) {
        this.lastLogin_ip = lastLogin_ip;
    }

    public String getThisLogin_date() {
        return thisLogin_date;
    }

    public void setThisLogin_date(String thisLogin_date) {
        this.thisLogin_date = thisLogin_date;
    }

    public String getThisLogin_ip() {
        return thisLogin_ip;
    }

    public void setThisLogin_ip(String thisLogin_ip) {
        this.thisLogin_ip = thisLogin_ip;
    }

    public String getHeadPic() {
        return HeadPic;
    }

    public void setHeadPic(String headPic) {
        HeadPic = headPic;
    }

    public String getViplevel() {
        return viplevel;
    }

    public void setViplevel(String viplevel) {
        this.viplevel = viplevel;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    @Override
    public String toString() {
        return "LoginBean{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", user_status='" + user_status + '\'' +
                ", create_date='" + create_date + '\'' +
                ", lastLogin_date='" + lastLogin_date + '\'' +
                ", lastLogin_ip='" + lastLogin_ip + '\'' +
                ", thisLogin_date='" + thisLogin_date + '\'' +
                ", thisLogin_ip='" + thisLogin_ip + '\'' +
                ", HeadPic='" + HeadPic + '\'' +
                ", viplevel='" + viplevel + '\'' +
                ", nikeName='" + nikeName + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}