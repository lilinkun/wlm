package com.wlm.wlm.contract;

import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.mvp.IView;

public interface GrouponOrderContract extends IView {


    /**
     * 获取邮费
     */
    public void getOrderGetFareSuccess(FareBean fareBean);
    public void getOrderGetFareFail(String msg);

    /**
     * 团购支付
     */
    public void getRightNowBuySuccess(String ordersn);
    public void getRightNowBuyFail(String msg);

    /**
     * 团购确认订单
     */
    public void sureOrderSuccess(WxInfo ordersn);
    public void sureOrderFail(String msg);
}
