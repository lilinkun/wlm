package com.wlm.wlm.contract;

import com.wlm.wlm.entity.BalanceBean;
import com.wlm.wlm.entity.OrderDetailAddressBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/11/4.
 */
public interface PayResultContract extends IView {

    public void setDataSuccess(OrderDetailAddressBean orderDetailBeans);

    public void setDataFail(String msg);


    /**
     * 剩余余额
     */
    public void getBalanceSuccess(BalanceBean balanceBean);

    public void getBalanceFail(String msg);

}
