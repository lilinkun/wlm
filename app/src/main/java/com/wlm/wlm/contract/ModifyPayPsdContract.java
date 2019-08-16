package com.wlm.wlm.contract;

import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/12/24.
 */

public interface ModifyPayPsdContract extends IView {
    public void onSendVcodeSuccess();
    public void onSendVcodeFail(String str);

    public void modifySuccess();
    public void modifyFail(String str);

    public void modifyPsdSuccess();
    public void modifyPsdFail(String msg);

}
