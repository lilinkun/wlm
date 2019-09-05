package com.wlm.wlm.contract;

import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.mvp.IView;

public interface GrouponOrderContract extends IView {


    /**
     * 获取默认地址
     * @param addressBean
     */
    public void isAddressSuccess(AddressBean addressBean);
    public void isAddressFail(String msg);


    /**
     * 获取邮费
     */
    public void getOrderGetFareSuccess(FareBean fareBean);
    public void getOrderGetFareFail(String msg);

    /**
     * 团购下单
     */
    public void getRightNowBuySuccess(BuyBean buyBean);
    public void getRightNowBuyFail(String msg);
}
