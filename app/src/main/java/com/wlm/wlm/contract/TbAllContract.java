package com.wlm.wlm.contract;

import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/8.
 */

public interface TbAllContract extends IView {
    public void onSuccess(ArrayList<TbMaterielBean> tbShopBeans);
    public void onError(String msg);

}
