package com.wlm.wlm.contract;

import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/1/6.
 */

public interface GivingContract extends IView {
    public void getAccount();

    public void getAccountFail(String msg);

    public void givingIntegralSuccess();

    public void givingIntegralFail(String msg);

    public void getPointSuccess();

    public void getPointFail();
}
