package com.wlm.wlm.util;

/**
 * Created by LG on 2018/12/19.
 */

public enum ShipStatusEnum {
    STATUS0(0,"等待付款"),
    STATUS1(1,"未发货"),
    STATUS2(2,"已发货"),
    STATUS3(4,"交易成功"),
    STATUS4(5,"交易关闭");

    private int id;
    private String statusMsg;

    private ShipStatusEnum(int id,String statusMsg) {
        this.id = id;
        this.statusMsg = statusMsg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public static ShipStatusEnum getPageByValue(int type) {
        for (ShipStatusEnum p : values()) {
            if (p.getId() == type)
                return p;
        }
        return null;
    }
}
