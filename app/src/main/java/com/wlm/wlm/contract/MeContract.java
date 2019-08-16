package com.wlm.wlm.contract;

import com.wlm.wlm.entity.PersonalInfoBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/12/3.
 */

public interface MeContract extends IView{
    public void getInfoSuccess(PersonalInfoBean personalInfoBean);
    public void getInfoFail(String msg);
}
