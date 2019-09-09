package com.wlm.wlm.contract;

import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.mvp.IView;

public interface GrouponOrderContract extends IView {


    /**
     * 获取邮费
     */
    public void getOrderGetFareSuccess(FareBean fareBean);
    public void getOrderGetFareFail(String msg);

    /**
     * 团购下单
     */
    public void getRightNowBuySuccess(RightNowBuyBean buyBean);
    public void getRightNowBuyFail(String msg);
}
