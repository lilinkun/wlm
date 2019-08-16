package com.wlm.wlm.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by LG on 2018/12/19.
 */
@Entity
public class SearchBean {
    private String username;
    private String searchname;

    @Generated(hash = 153234401)
    public SearchBean(String username, String searchname) {
        this.username = username;
        this.searchname = searchname;
    }

    @Generated(hash = 562045751)
    public SearchBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSearchname() {
        return searchname;
    }

    public void setSearchname(String searchname) {
        this.searchname = searchname;
    }
}
