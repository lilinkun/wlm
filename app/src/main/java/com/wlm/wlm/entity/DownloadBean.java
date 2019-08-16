package com.wlm.wlm.entity;

/**
 * Created by LG on 2018/12/28.
 */

public class DownloadBean {
    private String ver;
    private String message;
    private String url;
    private String date;
    private String isConstraintUpdate;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String file) {
        this.url = file;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIsConstraintUpdate() {
        return isConstraintUpdate;
    }

    public void setIsConstraintUpdate(String isConstraintUpdate) {
        this.isConstraintUpdate = isConstraintUpdate;
    }

    @Override
    public String toString() {
        return "DownloadBean{" +
                "ver='" + ver + '\'' +
                ", message='" + message + '\'' +
                ", file='" + url + '\'' +
                ", date='" + date + '\'' +
                ", isConstraintUpdate='" + isConstraintUpdate + '\'' +
                '}';
    }
}
