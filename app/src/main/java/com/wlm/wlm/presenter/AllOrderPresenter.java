package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.AllOrderContract;
import com.wlm.wlm.entity.CountBean;
import com.wlm.wlm.entity.OrderDetailAddressBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/21.
 */

public class AllOrderPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private AllOrderContract allOrderContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        allOrderContract = (AllOrderContract) view;
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
    public void cartBuy(String OrderSn, String SessionId) {
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
                        allOrderContract.setDataSuccess(orderDetailBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        allOrderContract.setDataFail(msg);
                    }
                }));
    }

    public void exitOrder(String OrderId, String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "取消订单中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoVIPCancel");
        params.put("OrderSn", OrderId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.exitOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {

                    @Override
                    public void onResponse(String collectDeleteBean, String status, Object page) {
                        allOrderContract.exitOrderSuccess(collectDeleteBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        allOrderContract.exitOrderFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

    public void getOrderData(String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "BankBase");
        params.put("fun", "BankBaseAmountAndPoint");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getAccountInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CountBean, Object>() {
                    @Override
                    public void onResponse(CountBean countBean, String status, Object page) {
                        allOrderContract.InfoAccountSuccess(countBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        allOrderContract.InfoAccountFail(msg);
                    }
                }));
    }

    /**
     * 余额支付
     */
    public void selfPay(String PayPwd, String OrderNo, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Order");
        params.put("fun", "BankNoPay");
        params.put("PayPwd", PayPwd);
        params.put("OrderNo", OrderNo);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.selfPay(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String collectDeleteBean, String status, Object page) {
                        allOrderContract.selfPaySuccess(collectDeleteBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        allOrderContract.selfPayFail(msg);
                    }
                }));
    }

    /**
     * 确认收货
     */
    public void sureReceipt(String OrderId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoVIPConfirm");
        params.put("OrderSn", OrderId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.sureReceipt(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String collectDeleteBean, String status, Object page) {
                        allOrderContract.sureReceiptSuccess(collectDeleteBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        allOrderContract.sureReceiptFail(msg);
                    }
                }));
    }


    public void setWxPay(String Batch_No, String Charge_Amt, String Logo_ID, String Charge_Type, String apptype, String apppackage, String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "微信支付中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "BankCharge");
        params.put("fun", "BankChargeRecharge");
        params.put("Batch_No", Batch_No);
        params.put("Charge_Amt", Charge_Amt);
        params.put("Logo_ID", Logo_ID);
        params.put("Charge_Type", Charge_Type);
        params.put("apptype", apptype);
        params.put("apppackage", apppackage);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.wxPay(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<WxInfo, Object>() {
                    @Override
                    public void onResponse(WxInfo fareBean, String status, Object page) {
//                        allOrderContract.wxInfoSuccess(fareBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        allOrderContract.wxInfoFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

}
