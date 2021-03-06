package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.wlm.wlm.contract.OrderContract;
import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.GoodsCartbean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;
import com.wlm.wlm.ui.LoaddingDialog;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/14.
 */

public class OrderPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private OrderContract orderContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        orderContract = (OrderContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestory() {
        if (mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    /**
     * 获取购物车列表
     *
     * @param SessionId
     */
    public void getList(String SessionId) {
//        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);
        final LoaddingDialog loaddingDialog = new LoaddingDialog(mContext);
//        loaddingDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Cart");
        params.put("fun", "Cart_GetLitsByUserId");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getCartList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<GoodsCartbean, Object>() {
                    @Override
                    public void onResponse(GoodsCartbean orderListBeans, String status, Object page) {
                        orderContract.OrderListSuccess(orderListBeans);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderContract.OrderListFail(msg);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }
                }));
    }


    /**
     * 修改购物车信息
     *
     * @param Num
     * @param CartId
     * @param SessionId
     */
    public void modifyOrder(final String Num, String CartId, final View view, String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "修改信息中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Cart");
        params.put("fun", "CartUpdate");
        params.put("Num", Num);
        params.put("CartId", CartId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.modifyOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectDeleteBean, Object>() {
                    @Override
                    public void onResponse(CollectDeleteBean orderListBeans, String status, Object page) {
                        orderContract.modifyOrderSuccess(orderListBeans, Num, view);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderContract.OrderListFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }


    /**
     * 删除信息
     *
     * @param CartId
     * @param SessionId
     */
    public void deleteOrder(String CartId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Cart");
        params.put("fun", "CartDelete");
        params.put("CartId", CartId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.deleteGoods(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String b, String status, Object page) {
                        orderContract.deleteGoodsSuccess(b);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderContract.deleteGoodsFail(msg);
                    }
                }));
    }

    /**
     * 判断是否有地址
     *
     * @param SessionId
     */
    public void isUserAddress(String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddress_GetListIsDefault");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getIsAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<AddressBean>, Object>() {
                    @Override
                    public void onResponse(ArrayList<AddressBean> string, String status, Object page) {
                        orderContract.isAddressSuccess(string);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderContract.isAddressFail(msg);
                    }
                }));
    }

    /**
     * 购物车下单
     *
     * @param CartId
     * @param SessionId
     */
    public void getOrderInfo(String CartId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Cart");
        params.put("fun", "CartIdSaveRedis");
        params.put("CartId", CartId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getOrderInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String s, String status, Object page) {
                        orderContract.cartOrderBuySuccess(s);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderContract.cartOrderBuyFail(msg);
                    }
                }));
    }
}
