package com.wlm.wlm.contract;

import com.wlm.wlm.entity.HomeHeadBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/11/27.
 */

public interface HomeContract extends IView{
        public void getUrlSuccess(UrlBean urlBean);
        public void getUrlFail(String msg);

        public void onFlashSuccess(HomeHeadBean homeHeadBean);
        public void onFlashFail(String msg);
}
