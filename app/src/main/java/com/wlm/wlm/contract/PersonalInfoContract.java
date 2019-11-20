package com.wlm.wlm.contract;

import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.PersonalInfoBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/12/3.
 */

public interface PersonalInfoContract extends IView {
    public void modifySuccess();

    public void modifyFail(String msg);

    public void getInfoSuccess(PersonalInfoBean loginBean);

    public void uploadImageSuccess(CollectDeleteBean collectDeleteBean);

    public void uploadImageFail(String msg);


    public void LoginOutSuccess(String msg);

    public void LoginOutFail(String msg);
}
