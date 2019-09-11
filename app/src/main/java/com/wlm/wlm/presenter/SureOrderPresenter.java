package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.SureOrderContract;
import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.CartBuyBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.FaresBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.entity.WxInfo;
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
     * 购物车购买
     * @param SessionId
     */
    public void cartBuy(String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","购物车购买中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Cart");
        params.put("fun","Cart_GetLitsByAdd");
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.rightNowBuy(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<RightNowBuyBean<CartBuyBean>,Object>() {
                    @Override
                    public void onResponse(RightNowBuyBean<CartBuyBean> rightNows, String status, Object page) {
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
     * @param Num
     * @param SessionId
     */
    public void getFare(String GoodsId,String AddressID,String Num,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","更新邮费...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","OrderInfo");
        params.put("fun","GoodsBuyShippingFreeGet");
        params.put("GoodsId",GoodsId);
        params.put("AddressID",AddressID);
        params.put("Num",Num);
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
     */
    public void sureSelfOrder(String AddressID,String OrderAmount,String ShippingFree,String Integral,String PostScript,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","提交订单中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","OrderInfo");
        params.put("fun","Order_Cart_Add");
        params.put("AddressID",AddressID);
        params.put("ShippingFree",ShippingFree);
        params.put("Integral",Integral);
        params.put("OrderAmount",OrderAmount);
        params.put("PostScript",PostScript);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.sureOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String,Object>() {
                    @Override
                    public void onResponse(String orderid, String status,Object page) {
                        sureOrderContract.sureOrderSuccess(orderid);
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


}
