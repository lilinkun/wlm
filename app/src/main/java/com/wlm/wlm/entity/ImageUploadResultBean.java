package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/29.
 */

public class ImageUploadResultBean {
    private String status;
    private ArrayList<String> url;
    private ArrayList<String> file;
    private String reson;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }

    public ArrayList<String> getFile() {
        return file;
    }

    public void setFile(ArrayList<String> file) {
        this.file = file;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }
}
