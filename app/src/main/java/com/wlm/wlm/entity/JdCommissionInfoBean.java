package com.wlm.wlm.entity;

import java.io.Serializable;

/**
 * Created by LG on 2019/1/31.
 */

public class JdCommissionInfoBean implements Serializable {
    private String commission;
    private int commissionShare;

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public int getCommissionShare() {
        return commissionShare;
    }

    public void setCommissionShare(int commissionShare) {
        this.commissionShare = commissionShare;
    }
}
