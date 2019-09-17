package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.SelfOrderContract;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.entity.SelfOrderBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/18.
 */

public class SelfOrderPresenter extends BasePresenter{
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private SelfOrderContract selfOrderContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        selfOrderContract = (SelfOrderContract) view;
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

    public void getOrderData(String PageIndex,String PageCount,String OrderStatus,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoVipList");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("OrderStatus", OrderStatus);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getSelfOrderList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<SelfOrderBean>,Object>(){

                    @Override
                    public void onResponse(ArrayList<SelfOrderBean> selfGoodsBeans, String status,Object page) {
                        selfOrderContract.getDataSuccess(selfGoodsBeans);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        selfOrderContract.getDataFail(msg);
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

    /**
     * 取消订单
     * @param OrderId
     * @param SessionId
     */
    public void exitOrder(String OrderId,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","取消订单中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoVIPCancel");
        params.put("OrderSn", OrderId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.exitOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String,Object>(){

                    @Override
                    public void onResponse(String collectDeleteBean, String status,Object page) {
                        selfOrderContract.exitOrderSuccess(collectDeleteBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        selfOrderContract.exitOrderFail(msg);
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

}
