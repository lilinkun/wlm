package com.wlm.wlm.util;

import com.wlm.wlm.R;

/**
 * Created by LG on 2019/9/24.
 */
public enum MallType {
    MANUFACTURE("8", "制造商城", R.drawable.shape_search_manufacture),
    INTEGRAL(1 + "", "积分商城", R.drawable.shape_search_integral),
    VIP(4 + "", "VIP", R.drawable.shape_search_vip),
    GROUPON(2 + "", "拼团", R.drawable.shape_search_groupon),
    All("0", "全部商城", R.drawable.shape_search_manufacture);


    private String typeId;
    private String typeName;
    private int drawBg;

    private MallType(String typeId, String typeName, int bg) {
        this.typeName = typeName;
        this.typeId = typeId;
        this.drawBg = bg;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getDrawBg() {
        return drawBg;
    }

    public void setDrawBg(int drawBg) {
        this.drawBg = drawBg;
    }

    public static MallType getVipByValue(String type) {
        for (MallType p : values()) {
            if (p.getTypeName().equals(type))
                return p;
        }
        return null;
    }

    public static MallType getVipById(String type) {
        for (MallType p : values()) {
            if (p.getTypeId().equals(type))
                return p;
        }
        return null;
    }
}

