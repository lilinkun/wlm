package com.wlm.wlm.contract;

import com.wlm.wlm.entity.FansBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/20.
 */
public interface MyFansContract extends IView {
    public void getFansSuccess(ArrayList<FansBean> integralBean, PageBean pageBean);
    public void getFansFail(String msg);
}
