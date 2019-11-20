package com.wlm.wlm.contract;

import com.wlm.wlm.entity.ProvinceBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/10.
 */

public interface AddressPickerContract extends IView {
    public void getDataSuccess(ArrayList<ProvinceBean> provinceBeans, int id);

    public void getDataFail(String msg);
}
