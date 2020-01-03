package com.wlm.wlm.util;

import com.wlm.wlm.R;

/**
 * Created by LG on 2019/10/28.
 */
public enum HomeGridRvEnum {
    STATUS0(0, "9.9尖货", R.mipmap.home_icon_1,"icon-1@2x.png"),
    STATUS1(1, "VIP宝典", R.mipmap.home_icon_2,"icon-2@2x.png"),
    STATUS2(2, "拼团", R.mipmap.home_icon_3,"icon-3@2x.png"),
    STATUS3(3, "限时秒杀", R.mipmap.home_icon_4,"icon-4@2x.png"),
    //    STATUS4(4,"众筹", R.mipmap.home_icon_5,"icon-1@2x.png"),
    STATUS5(5, "唯乐美智造", R.mipmap.home_icon_6,"icon6@2x.png"),
    STATUS6(6, "积分兑换", R.mipmap.home_icon_7,"icon7@2x.png"),
    STATUS7(7, "唯乐购", R.mipmap.home_icon_8,"icon-8@2x.png"),
    STATUS8(8, "美雕健康", R.mipmap.home_icon_9,"icon-9@2x.png");
//    STATUS9(9,"附近的店", R.mipmap.home_icon_10,"icon-1@2x.png")

    private int id;
    private String statusMsg;
    private int srcmsg;
    private String msgAddress;

    private HomeGridRvEnum(int id, String statusMsg, int srcmsg,String msgAddress) {
        this.id = id;
        this.statusMsg = statusMsg;
        this.srcmsg = srcmsg;
        this.msgAddress = msgAddress;
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

    public int getSrcmsg() {
        return srcmsg;
    }

    public void setSrcmsg(int srcmsg) {
        this.srcmsg = srcmsg;
    }

    public String getMsgAddress() {
        return msgAddress;
    }

    public void setMsgAddress(String msgAddress) {
        this.msgAddress = msgAddress;
    }

    public static HomeGridRvEnum getPageByValue(int type) {
        for (HomeGridRvEnum p : values()) {
            if (p.getId() == type)
                return p;
        }
        return null;
    }
}
