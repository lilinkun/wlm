package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.PayResultContract;
import com.wlm.wlm.entity.BalanceBean;
import com.wlm.wlm.entity.OrderDetailAddressBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/11/4.
 */
public class PayResultPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private PayResultContract payContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.manager = new DataManager(context);
        this.mContext = context;
        this.mCompositeSubscription = new CompositeSubscription();
        payContract = (PayResultContract) view;
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
     * 　订单详情
     *
     * @param OrderSn
     * @param SessionId
     */
    public void orderDetail(String OrderSn, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoGoodsDetail");
        params.put("OrderSn", OrderSn);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getOrderDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<OrderDetailAddressBean, Object>() {
                    @Override
                    public void onResponse(OrderDetailAddressBean orderDetailBeans, String status, Object page) {
                        payContract.setDataSuccess(orderDetailBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        payContract.setDataFail(msg);
                    }
                }));
    }

    public void getBalance(String SessionId) {
//        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "BankBase_GetBalance");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getBalance(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<BalanceBean, Object>() {
                    @Override
                    public void onResponse(BalanceBean balanceBean, String status, Object page) {
                        payContract.getBalanceSuccess(balanceBean);
//                        if (progressDialog != null && progressDialog.isShowing()) {
//                            progressDialog.dismiss();
//                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        payContract.getBalanceFail(msg);
//                        if (progressDialog != null && progressDialog.isShowing()) {
//                            progressDialog.dismiss();
//                        }
                    }
                }));
    }


}
