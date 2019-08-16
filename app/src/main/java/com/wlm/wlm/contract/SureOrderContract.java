package com.wlm.wlm.contract;

import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.FaresBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/15.
 */

public interface SureOrderContract extends IView {
    public void getRightNowBuySuccess(BuyBean buyBean);
    public void getRightNowBuyFail(String msg);

    public void getOrderGetFareSuccess(FareBean fareBean);
    public void getOrderGetFareFail(String msg);

    public void getOrderGetFaresSuccess(ArrayList<FaresBean> fareBean);
    public void getOrderGetFaresFail(String msg);

    public void sureOrderSuccess(CollectDeleteBean collectDeleteBean);
    public void sureOrderFail(String msg);

    public void selfPaySuccess(CollectDeleteBean collectDeleteBean);
    public void selfPayFail(String msg);

    public void cartBuySuccess(BuyBean buyBean);
    public void cartBuyFail(String msg);

    public void wxInfoSuccess(WxRechangeBean wxRechangeBean);
    public void wxInfoFail(String msg);
}
