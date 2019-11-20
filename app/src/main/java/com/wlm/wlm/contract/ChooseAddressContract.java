package com.wlm.wlm.contract;

import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/9.
 */

public interface ChooseAddressContract extends IView {
    public void setDataSuccess(ArrayList<AddressBean> addressBeanArrayList);

    public void setDataFail(String msg);

    public void deleteSuccess();

    public void deleteFail(String msg);

    public void isDefaultSuccess(String isDefaultStr);

    public void isDefaultFail(String msg);
}
