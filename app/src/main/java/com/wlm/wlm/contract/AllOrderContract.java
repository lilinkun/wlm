package com.wlm.wlm.contract;

import com.wlm.wlm.entity.CountBean;
import com.wlm.wlm.entity.OrderDetailAddressBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/12/21.
 */

public interface AllOrderContract extends IView {
    public void setDataSuccess(OrderDetailAddressBean orderDetailBeans);

    public void setDataFail(String msg);

    public void exitOrderSuccess(String collectDeleteBean);

    public void exitOrderFail(String msg);

    public void cancelOrderSuccess(String collectDeleteBean);

    public void cancelOrderFail(String msg);

    public void InfoAccountSuccess(CountBean orderDetailBean);

    public void InfoAccountFail(String msg);

    public void selfPaySuccess(String collectDeleteBean);

    public void selfPayFail(String msg);

    public void sureReceiptSuccess(String collectDeleteBean);

    public void sureReceiptFail(String msg);

    public void wxInfoSuccess(WxRechangeBean wxRechangeBean);

    public void wxInfoFail(String msg);
}
