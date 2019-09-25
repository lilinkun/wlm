package com.wlm.wlm.contract;

import com.wlm.wlm.entity.ErrorBean;
import com.wlm.wlm.entity.OpinionBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/2.
 */

public interface OpinionContract extends IView {
    public void onUploadSuccess();
    public void onFail(String msg);

    public void getTypeSuccess(ArrayList<ErrorBean> errorBeans);
    public void getTypeFail(String msg);

    public void getOpinionListSuccess(ArrayList<OpinionBean> opinionBeans);
    public void getOpinionListFail(String msg);
}
