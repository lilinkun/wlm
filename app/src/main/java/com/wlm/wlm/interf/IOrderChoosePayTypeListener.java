package com.wlm.wlm.interf;

/**
 * Created by LG on 2019/8/30.
 */
public interface IOrderChoosePayTypeListener {
    //选择是那种付款方式 1、微信支付 2、余额支付
    public void chooseType(int type);
}
