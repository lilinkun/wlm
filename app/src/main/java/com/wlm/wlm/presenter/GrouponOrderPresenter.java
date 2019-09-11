package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.GrouponOrderContract;
import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.entity.RightNowGoodsBean;
import com.wlm.wlm.entity.WxInfo;
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
     * 确认下单
     * @param GoodsId
     * @param AddressID
     * @param Num
     * @param SessionId
     */
    public void rightNowBuy(String GoodsId,String AddressID,String Num,String OrderAmount,String ShippingFree,String Integral,String PostScript,String OrderType,String attr_id,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","下单中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","OrderInfo");
        params.put("fun","OrderInfo_Add");
        params.put("GoodsId",GoodsId);
        params.put("AddressID",AddressID);
        params.put("Num",Num);
        params.put("OrderAmount",OrderAmount);
        params.put("ShippingFree",ShippingFree);
        params.put("Integral",Integral);
        params.put("PostScript",PostScript);
        params.put("OrderType",OrderType);
        params.put("attr_id",attr_id);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.grouponRightNowBuy(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String,Object>() {
                    @Override
                    public void onResponse(String rightNows, String status,Object page) {
                        orderContract.getRightNowBuySuccess(rightNows);
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


    /**
     * 立即购买
     * @param GoodsId
     * @param AttrId
     * @param Num
     * @param SessionId
     */
    public void rightNowBuyInfo(String GoodsId,String AttrId,String Num,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","生成订单中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","OrderInfo");
        params.put("fun","GoodsBuyGet");
        params.put("GoodsId",GoodsId);
        params.put("attr_id",AttrId);
        params.put("Num",Num);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getGoodsOrderInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<RightNowBuyBean<RightNowGoodsBean>,Object>() {
                    @Override
                    public void onResponse(RightNowBuyBean<RightNowGoodsBean> rightNows, String status,Object page) {
                        orderContract.getRightNowBuyInfoSuccess(rightNows);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderContract.getRightNowBuyInfoFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));

    }


}