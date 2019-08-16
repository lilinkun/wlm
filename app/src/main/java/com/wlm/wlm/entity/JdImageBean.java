package com.wlm.wlm.entity;

import java.io.Serializable;

/**
 * Created by LG on 2019/1/29.
 */
public class JdImageBean implements Serializable {
    private JdImageList[] imageList;

    public JdImageList[] getImageList() {
        return imageList;
    }

    public void setImageList(JdImageList[] imageList) {
        this.imageList = imageList;
    }

    public class JdImageList implements Serializable{
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

