package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.SureOrderContract;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.FaresBean;
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
 * Created by LG on 2018/12/15.
 */

public class SureOrderPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private SureOrderContract sureOrderContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        sureOrderContract = (SureOrderContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 立即购买
     * @param GoodsId
     * @param AttrId
     * @param Num
     * @param SessionId
     */
    public void rightNowBuy(String GoodsId,String AttrId,String Num,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Order");
        params.put("fun","Buy");
        params.put("GoodsId",GoodsId);
        params.put("AttrId",AttrId);
        params.put("Num",Num);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.rightNowBuy(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<BuyBean,Object>() {
                    @Override
                    public void onResponse(BuyBean rightNows, String status,Object page) {
                        sureOrderContract.getRightNowBuySuccess(rightNows);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        sureOrderContract.getRightNowBuyFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

    /**
     * 购物车购买
     * @param CartId
     * @param SessionId
     */
    public void cartBuy(String CartId,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","购物车购买中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Order");
        params.put("fun","CartOrderBuy");
        params.put("CartId",CartId);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.rightNowBuy(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<BuyBean,Object>() {
                    @Override
                    public void onResponse(BuyBean rightNows, String status,Object page) {
                        sureOrderContract.getRightNowBuySuccess(rightNows);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        sureOrderContract.getRightNowBuyFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }


    /**
     * 获取单个邮费
     * @param GoodsId
     * @param AttrId
     * @param Num
     * @param Provice
     * @param City
     * @param StoreId
     * @param SessionId
     */
    public void getFare(String GoodsId,String AttrId,String Num,String Provice,String City,String StoreId,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","更新邮费...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Order");
        params.put("fun","GetFare");
        params.put("GoodsId",GoodsId);
        params.put("AttrId",AttrId);
        params.put("Num",Num);
        params.put("Provice",Provice);
        params.put("City",City);
        params.put("LgsId","");
        params.put("StoreId",StoreId);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getfare(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<FareBean,Object>() {
                    @Override
                    public void onResponse(FareBean fareBean, String status,Object page) {
                        sureOrderContract.getOrderGetFareSuccess(fareBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        sureOrderContract.getOrderGetFareFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

    /**
     * 获取多个邮费
     * @param SessionId
     */
    public void getFares(String CartId ,String AddressId ,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","更新邮费...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Order");
        params.put("fun","GetCartFare");
        params.put("CartId",CartId);
        params.put("AddressId",AddressId);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getfares(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<FaresBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<FaresBean> fareBean, String status,Object page) {
                        sureOrderContract.getOrderGetFaresSuccess(fareBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        sureOrderContract.getOrderGetFaresFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }


    /**
     * 确认订单
     * @param GoodsId
     * @param AttrId
     * @param Num
     * @param Fare
     * @param UseIntegral
     * @param PayAmount
     * @param AddressId
     * @param GoodsAmount
     * @param SessionId
     */
    public void sureOrder(String GoodsId,String AttrId,String Num,String Fare,String UseIntegral,String PayAmount,String AddressId,String GoodsAmount,String SessionId){

        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","提交订单中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Order");
        params.put("fun","BuySave");
        params.put("GoodsId",GoodsId);
        params.put("AttrId",AttrId);
        params.put("Num",Num);
        params.put("Fare",Fare);
        params.put("UseIntegral",UseIntegral);
        params.put("OrderAmount",PayAmount);
        params.put("AddressId",AddressId);
        params.put("GoodsAmount",GoodsAmount);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.sureOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectDeleteBean,Object>() {
                    @Override
                    public void onResponse(CollectDeleteBean fareBean, String status,Object page) {
                        sureOrderContract.sureOrderSuccess(fareBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        sureOrderContract.getOrderGetFareFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

 /**
     * 确认订单
     * @param Point
     * @param CartId
     * @param OrderAmount
     * @param Frares
     * @param UseIntegral
     * @param ShippingFee
     * @param AddressId
     * @param GoodsAmount
     * @param SessionId
     */
    public void sureSelfOrder(String Point,String CartId,String ShippingFee,String Frares,String UseIntegral,String OrderAmount,String AddressId,String GoodsAmount,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","提交订单中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Order");
        params.put("fun","CartBuySave");
        params.put("Point",Point);
        params.put("CartId",CartId);
        params.put("ShippingFee",ShippingFee);
        params.put("Frares",Frares);
        params.put("UseIntegral",UseIntegral);
        params.put("OrderAmount",OrderAmount);
        params.put("AddressId",AddressId);
        params.put("GoodsAmount",GoodsAmount);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.sureOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectDeleteBean,Object>() {
                    @Override
                    public void onResponse(CollectDeleteBean fareBean, String status,Object page) {
                        sureOrderContract.sureOrderSuccess(fareBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        sureOrderContract.getOrderGetFareFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

    /**
     * 余额支付
     */
    public void selfPay(String PayPwd,String OrderNo,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","余额支付中...",true);
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
                    public void onResponse(CollectDeleteBean fareBean, String status,Object page) {
                        sureOrderContract.selfPaySuccess(fareBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        sureOrderContract.selfPayFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
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
                        sureOrderContract.wxInfoSuccess(fareBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        sureOrderContract.wxInfoFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }
}
