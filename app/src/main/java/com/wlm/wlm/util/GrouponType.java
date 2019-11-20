package com.wlm.wlm.util;

/**
 * Created by LG on 2019/9/3.
 */
public enum GrouponType {
    ThreeGroup("三人团"),
    FiveGroup("五人团"),
    All("全部");


    private String typeName;

    private GrouponType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public static GrouponType getVipByValue(String type) {
        for (GrouponType p : values()) {
            if (p.getTypeName().equals(type))
                return p;
        }
        return null;
    }
}
