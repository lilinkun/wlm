package com.wlm.wlm.contract;

import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.SelfOrderBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/18.
 */

public interface SelfOrderContract extends IView {
    public void getDataSuccess(ArrayList<SelfOrderBean> selfOrderBeans);
    public void getDataFail(String msg);

    public void exitOrderSuccess(String collectDeleteBean);
    public void exitOrderFail(String smg);
}
