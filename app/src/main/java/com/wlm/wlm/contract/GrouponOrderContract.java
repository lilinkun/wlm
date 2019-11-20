package com.wlm.wlm.contract;

import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.entity.RightNowGoodsBean;
import com.wlm.wlm.mvp.IView;

public interface GrouponOrderContract extends IView {

    /**
     * 获取订单详情
     *
     * @param orderListBeans
     */
    public void getRightNowBuyInfoSuccess(RightNowBuyBean<RightNowGoodsBean> orderListBeans);

    public void getRightNowBuyInfoFail(String msg);

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

}
