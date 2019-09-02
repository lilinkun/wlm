package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.AllOrderContract;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.CountBean;
import com.wlm.wlm.entity.OrderDetailBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
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
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        allOrderContract = (AllOrderContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }

    /**
     *　订单详情
     * @param OrderSn
     * @param SessionId
     */
    public void cartBuy(String OrderSn,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Order");
        params.put("fun","OrderInfoDetail");
        params.put("OrderSn",OrderSn);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getOrderDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<OrderDetailBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<OrderDetailBean> orderDetailBeans, String status,Object page) {
                        allOrderContract.setDataSuccess(orderDetailBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        allOrderContract.setDataFail(msg);
                    }
                }));
    }

    public void exitOrder(String OrderId,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Order");
        params.put("fun", "CancleOrder");
        params.put("OrderId", OrderId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.exitOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectDeleteBean,Object>(){

                    @Override
                    public void onResponse(CollectDeleteBean collectDeleteBean, String status,Object page) {
                        allOrderContract.exitOrderSuccess(collectDeleteBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        allOrderContract.exitOrderFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

    public void getOrderData(String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","BankBase");
        params.put("fun","BankBaseAmountAndPoint");
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getAccountInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CountBean,Object>() {
                    @Override
                    public void onResponse(CountBean countBean, String status,Object page) {
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
    public void selfPay(String PayPwd,String OrderNo,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Order");
        params.put("fun","BankNoPay");
        params.put("PayPwd",PayPwd);
        params.put("OrderNo",OrderNo);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.selfPay(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectDeleteBean,Object>() {
                    @Override
                    public void onResponse(CollectDeleteBean collectDeleteBean, String status,Object page) {
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
    public void sureReceipt(String OrderId,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Order");
        params.put("fun","ConfirmReceipt");
        params.put("OrderId",OrderId);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.sureReceipt(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectDeleteBean,Object>() {
                    @Override
                    public void onResponse(CollectDeleteBean collectDeleteBean, String status,Object page) {
                        allOrderContract.sureReceiptSuccess(collectDeleteBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        allOrderContract.sureReceiptFail(msg);
                    }
                }));
    }
/**
     * 收货订单
     */
    public void deleteOrder(String OrderId,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Order");
        params.put("fun","OrderInfoDelete");
        params.put("OrderId",OrderId);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.deleteOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectDeleteBean,Object>() {
                    @Override
                    public void onResponse(CollectDeleteBean collectDeleteBean, String status,Object page) {
                        allOrderContract.sureReceiptSuccess(collectDeleteBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        allOrderContract.sureReceiptFail(msg);
                    }
                }));
    }


    public void setWxPay(String Batch_No,String Charge_Amt,String Logo_ID,String Charge_Type,String apptype,String apppackage,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","微信支付中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","BankCharge");
        params.put("fun","BankChargeRecharge");
        params.put("Batch_No",Batch_No);
        params.put("Charge_Amt",Charge_Amt);
        params.put("Logo_ID",Logo_ID);
        params.put("Charge_Type",Charge_Type);
        params.put("apptype",apptype);
        params.put("apppackage",apppackage);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.wxPay(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<WxRechangeBean,Object>() {
                    @Override
                    public void onResponse(WxRechangeBean fareBean, String status,Object page) {
                        allOrderContract.wxInfoSuccess(fareBean);
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
