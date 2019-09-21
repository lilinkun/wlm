package com.wlm.wlm.contract;

import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

public interface CrowdFundingContract extends IView {

    public void onFlashSuccess(ArrayList<FlashBean> flashBeans);
    public void onFlashFail(String msg);


}
