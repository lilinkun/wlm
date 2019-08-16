package com.wlm.wlm.contract;

import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.CountBean;
import com.wlm.wlm.entity.OrderDetailBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/20.
 */

public interface OrderListContract extends IView {
    public void InfoAccountSuccess(CountBean orderDetailBean);
    public void InfoAccountFail(String msg);

    public void selfPaySuccess(CollectDeleteBean collectDeleteBean);
    public void selfPayFail(String msg);

    public void sureReceiptSuccess(CollectDeleteBean collectDeleteBean);
    public void sureReceiptFail(String msg);

    public void wxInfoSuccess(WxRechangeBean wxRechangeBean);
    public void wxInfoFail(String msg);
}
