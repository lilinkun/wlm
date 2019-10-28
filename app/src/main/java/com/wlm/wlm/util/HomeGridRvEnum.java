package com.wlm.wlm.util;

import com.wlm.wlm.R;

/**
 * Created by LG on 2019/10/28.
 */
public enum HomeGridRvEnum {
    STATUS0(0,"9.9尖货", R.mipmap.home_icon_1),
    STATUS1(1,"VIP宝典", R.mipmap.home_icon_2),
    STATUS2(2,"拼团", R.mipmap.home_icon_3),
    STATUS3(3,"限时秒杀", R.mipmap.home_icon_4),
    STATUS4(4,"众筹", R.mipmap.home_icon_5),
    STATUS5(5,"唯乐美制造", R.mipmap.home_icon_6),
    STATUS6(6,"积分兑换", R.mipmap.home_icon_7),
    STATUS7(7,"唯乐购", R.mipmap.home_icon_8),
    STATUS8(8,"医美健康", R.mipmap.home_icon_9),
    STATUS9(9,"附近的店", R.mipmap.home_icon_10);

    private int id;
    private String statusMsg;
    private int srcmsg;

    private HomeGridRvEnum(int id,String statusMsg,int srcmsg) {
        this.id = id;
        this.statusMsg = statusMsg;
        this.srcmsg = srcmsg;
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

    public static HomeGridRvEnum getPageByValue(int type) {
        for (HomeGridRvEnum p : values()) {
            if (p.getId() == type)
                return p;
        }
        return null;
    }
}
