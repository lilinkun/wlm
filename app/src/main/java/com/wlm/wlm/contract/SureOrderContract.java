package com.wlm.wlm.contract;

import com.wlm.wlm.entity.CartBuyBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/12/15.
 */

public interface SureOrderContract extends IView {
    public void getRightNowBuySuccess(RightNowBuyBean<CartBuyBean> buyBean);

    public void getRightNowBuyFail(String msg);

    public void getOrderGetFaresSuccess(RightNowBuyBean fareBean);

    public void getOrderGetFaresFail(String msg);

    public void sureOrderSuccess(String orderid);

    public void sureOrderFail(String msg);


    /**
     * 获取邮费
     */
    public void getOrderGetFareSuccess(FareBean fareBean);

    public void getOrderGetFareFail(String msg);

}
