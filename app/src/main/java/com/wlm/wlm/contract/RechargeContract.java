package com.wlm.wlm.contract;

import com.wlm.wlm.entity.BalanceBean;
import com.wlm.wlm.entity.CountBean;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.entity.WxInfoBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.entity.WxUserInfo;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/1/5.
 */

public interface RechargeContract extends IView{
    public void setReChargeSuccess(WxInfo reChargeSuccess);
    public void setReChargeFail(String msg);

    public void InfoAccountSuccess(BalanceBean countBean);
    public void InfoAccountFail(String msg);
}
