package com.wlm.wlm.contract;

import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/9/10.
 */
public interface PayContract extends IView {

    /**
     * 微信支付
     */
    public void sureOrderSuccess(WxInfo ordersn);
    public void sureOrderFail(String msg);

}
