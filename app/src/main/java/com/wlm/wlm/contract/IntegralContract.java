package com.wlm.wlm.contract;

import com.wlm.wlm.entity.AmountPriceBean;
import com.wlm.wlm.entity.IntegralBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/12/29.
 */

public interface IntegralContract extends IView {
    public void getGoodsIntegralSuccess(IntegralBean integralBean);
    public void getGoodsIntegralFail(String msg);

    public void getDataSuccess(AmountPriceBean amountPriceBean);
    public void getDataFail(String msg);
}
