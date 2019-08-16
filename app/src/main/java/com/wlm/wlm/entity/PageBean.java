package com.wlm.wlm.entity;

/**
 * Created by LG on 2019/4/2.
 */

public class PageBean {
    String SizeCount;
    String PageIndex;
    String MaxPage;
    String PageCount;

    public String getSizeCount() {
        return SizeCount;
    }

    public void setSizeCount(String sizeCount) {
        SizeCount = sizeCount;
    }

    public String getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(String pageIndex) {
        PageIndex = pageIndex;
    }

    public String getMaxPage() {
        return MaxPage;
    }

    public void setMaxPage(String maxPage) {
        MaxPage = maxPage;
    }

    public String getPageCount() {
        return PageCount;
    }

    public void setPageCount(String pageCount) {
        PageCount = pageCount;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "SizeCount='" + SizeCount + '\'' +
                ", PageIndex='" + PageIndex + '\'' +
                ", MaxPage='" + MaxPage + '\'' +
                ", PageCount='" + PageCount + '\'' +
                '}';
    }
}

