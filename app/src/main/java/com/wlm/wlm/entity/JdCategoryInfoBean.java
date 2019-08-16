package com.wlm.wlm.entity;

import java.io.Serializable;

/**
 * Created by LG on 2019/1/31.
 */

public class JdCategoryInfoBean implements Serializable {
    private String cid1;
    private String cid1Name;
    private String cid2;
    private String cid2Name;
    private String cid3;
    private String cid3Name;

    public String getCid1() {
        return cid1;
    }

    public void setCid1(String cid1) {
        this.cid1 = cid1;
    }

    public String getCid1Name() {
        return cid1Name;
    }

    public void setCid1Name(String cid1Name) {
        this.cid1Name = cid1Name;
    }

    public String getCid2() {
        return cid2;
    }

    public void setCid2(String cid2) {
        this.cid2 = cid2;
    }

    public String getCid2Name() {
        return cid2Name;
    }

    public void setCid2Name(String cid2Name) {
        this.cid2Name = cid2Name;
    }

    public String getCid3() {
        return cid3;
    }

    public void setCid3(String cid3) {
        this.cid3 = cid3;
    }

    public String getCid3Name() {
        return cid3Name;
    }

    public void setCid3Name(String cid3Name) {
        this.cid3Name = cid3Name;
    }
}
