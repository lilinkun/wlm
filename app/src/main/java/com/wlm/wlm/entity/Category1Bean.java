package com.wlm.wlm.entity;

import java.io.Serializable;

/**
 * Created by LG on 2018/12/16.
 */

public class Category1Bean<T> implements Serializable {
    private String cat_id;
    private String cat_name;
    private String keywords;
    private String cat_desc;
    private String parent_id;
    private String sort_order;
    private String show_in_nav;
    private String is_show;
    private String cat_icon;
    private String cat_indeximg;
    private String cat_index_order;
    private String is_leaf;
    private String cat_level;
    private String cat_type;
    private T subclass;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCat_desc() {
        return cat_desc;
    }

    public void setCat_desc(String cat_desc) {
        this.cat_desc = cat_desc;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getShow_in_nav() {
        return show_in_nav;
    }

    public void setShow_in_nav(String show_in_nav) {
        this.show_in_nav = show_in_nav;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getCat_icon() {
        return cat_icon;
    }

    public void setCat_icon(String cat_icon) {
        this.cat_icon = cat_icon;
    }

    public String getCat_indeximg() {
        return cat_indeximg;
    }

    public void setCat_indeximg(String cat_indeximg) {
        this.cat_indeximg = cat_indeximg;
    }

    public String getCat_index_order() {
        return cat_index_order;
    }

    public void setCat_index_order(String cat_index_order) {
        this.cat_index_order = cat_index_order;
    }

    public String getIs_leaf() {
        return is_leaf;
    }

    public void setIs_leaf(String is_leaf) {
        this.is_leaf = is_leaf;
    }

    public String getCat_level() {
        return cat_level;
    }

    public void setCat_level(String cat_level) {
        this.cat_level = cat_level;
    }

    public String getCat_type() {
        return cat_type;
    }

    public void setCat_type(String cat_type) {
        this.cat_type = cat_type;
    }

    public T getSubclass() {
        return subclass;
    }

    public void setSubclass(T subclass) {
        this.subclass = subclass;
    }
}

