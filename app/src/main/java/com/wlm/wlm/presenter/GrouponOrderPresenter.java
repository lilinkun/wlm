package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.GrouponOrderContract;
import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class GrouponOrderPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private GrouponOrderContract orderContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        orderContract = (GrouponOrderContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    /**
     * 获取收货地址
     * @param PageIndex
     * @param PageCount
     * @param SessionId
     */
    public void getAddress(String PageIndex,String PageCount,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","ReceiptAddress");
        params.put("fun","ReceiptAddressList");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("SessionId",SessionId);

        mCompositeSubscription.add(manager.getConsigneeAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<AddressBean>, Object>() {
                    @Override
                    public void onResponse(ArrayList<AddressBean> addressBeans, String status,Object page) {
                        for (AddressBean addressBean : addressBeans){

                            if (addressBean.isDefault()){
                                orderContract.isAddressSuccess(addressBean);
                            }
                        }
                    }
                    @Override
                    public void onErr(String msg, String status) {
                        orderContract.isAddressFail(msg);
                    }
                })
        );
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
                        orderContract.getOrderGetFareSuccess(fareBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderContract.getOrderGetFareFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

    /**
     * 团购下单
     * @param GoodsId
     * @param AddressID
     * @param Num
     * @param SessionId
     */
    public void rightNowBuy(String GoodsId,String AddressID,String Num,String OrderAmount,String ShippingFree,String Integral ,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","下单中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","OrderInfo");
        params.put("fun","OrderInfoTeamAdd");
        params.put("GoodsId",GoodsId);
        params.put("AddressID",AddressID);
        params.put("Num",Num);
        params.put("OrderAmount",OrderAmount);
        params.put("ShippingFree",ShippingFree);
        params.put("Integral",Integral);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.grouponRightNowBuy(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String,Object>() {
                    @Override
                    public void onResponse(String String, String status,Object page) {
//                        orderContract.getRightNowBuySuccess(rightNows);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderContract.getRightNowBuyFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }




}