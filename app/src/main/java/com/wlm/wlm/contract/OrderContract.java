package com.wlm.wlm.contract;

import android.view.View;

import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.GoodsCartbean;
import com.wlm.wlm.entity.OrderBean;
import com.wlm.wlm.entity.OrderListBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/14.
 */

public interface OrderContract extends IView {

    public void OrderListSuccess(GoodsCartbean orderListBeans);
    public void OrderListFail(String msg);

    public void modifyOrderSuccess(CollectDeleteBean collectDeleteBean, String num, View view);
    public void modifyOrderFail(String msg);

    public void deleteGoodsSuccess(String b);
    public void deleteGoodsFail(String msg);

    public void cartOrderBuySuccess(String str);
    public void cartOrderBuyFail(String msg);

    public void isAddressSuccess(ArrayList<AddressBean> msg);
    public void isAddressFail(String msg);

}
