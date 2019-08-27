package com.wlm.wlm.util;

import com.wlm.wlm.R;

/**
 * 会员权益
 * Created by LG on 2019/8/26.
 */
public enum VipEnum {
    STATUS0(0,"拼团•拼三免一", R.mipmap.ic_vip_0,false),
    STATUS1(1,"唯乐美7.5折", R.mipmap.ic_vip_1,false),
    STATUS2(2,"众筹•享工厂价", R.mipmap.ic_vip_2,false),
    STATUS3(3,"超值大礼包", R.mipmap.ic_vip_3,true),
    STATUS4(4,"积分兑换•省钱", R.mipmap.ic_vip_4,false),
    STATUS5(5,"免费美容咨询", R.mipmap.ic_vip_5,false);

    private int id;
    private String statusMsg;
    private int drawId;
    private boolean isVisible;

    private VipEnum(int id,String statusMsg,int drawId,boolean isVisible) {
        this.id = id;
        this.statusMsg = statusMsg;
        this.drawId = drawId;
        this.isVisible = isVisible;
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

    public void setDrawId(int drawId) {
        this.drawId = drawId;
    }

    public int getDrawId() {
        return drawId;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public static VipEnum getVipByValue(int type) {
        for (VipEnum p : values()) {
            if (p.getId() == type)
                return p;
        }
        return null;
    }
}
