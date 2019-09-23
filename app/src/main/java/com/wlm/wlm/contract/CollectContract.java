package com.wlm.wlm.contract;

import com.wlm.wlm.entity.BrowseRecordBean;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/11.
 */

public interface CollectContract extends IView {
    public void getCollectDataSuccess(ArrayList<CollectBean> collectBeans, String pageCount);
    public void getCollectFail(String msg);

}
