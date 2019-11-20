package com.wlm.wlm.contract;

import com.wlm.wlm.entity.JdGoodsBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/1/31.
 */

public interface JdGoodsContract extends IView {

    public void getDataSuccess(ArrayList<JdGoodsBean> jdGoodsBean);

    public void getDataFail(String msg);

}
