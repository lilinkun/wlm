package com.wlm.wlm.contract;

import android.view.View;

import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.OrderBean;
import com.wlm.wlm.entity.OrderListBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/14.
 */

public interface OrderContract extends IView {

    public void OrderListSuccess(ArrayList<OrderBean> orderListBeans);
    public void OrderListFail(String msg);

    public void modifyOrderSuccess(CollectDeleteBean collectDeleteBean, String num, View view);
    public void modifyOrderFail(String msg);

    public void deleteGoodsSuccess(CollectDeleteBean b);
    public void deleteGoodsFail(String msg);

    public void cartOrderBuySuccess();
    public void cartOrderBuyFail();

    public void isAddressSuccess(String msg);
    public void isAddressFail(String msg);

}
