package com.wlm.wlm.contract;

import com.wlm.wlm.entity.BalanceBean;
import com.wlm.wlm.entity.OrderDetailAddressBean;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/9/10.
 */
public interface PayContract extends IView {

    /**
     * 微信支付
     */
    public void sureWxOrderSuccess(WxInfo ordersn);
    public void sureWxOrderFail(String msg);

    /**
     * 余额支付
     */
    public void sureOrderSuccess(String str);
    public void sureOrderFail(String msg);


    /**
     * 剩余余额
     */
    public void getBalanceSuccess(BalanceBean balanceBean);
    public void getBalanceFail(String msg);



    public void setDataSuccess(OrderDetailAddressBean orderDetailBeans);
    public void setDataFail(String msg);
}
