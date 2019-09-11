package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.PayContract;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/9/11.
 */
public class PayPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private PayContract payContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.manager = new DataManager(context);
        this.mContext = context;
        this.mCompositeSubscription = new CompositeSubscription();
        payContract = (PayContract) view;
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

    public void getPayOrderInfo(String OrderSn,String OpenId,String OrderAmount,String Logo_ID,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","OrderInfo");
        params.put("fun","OrderInfoPay");
        params.put("OrderSn",OrderSn);
        params.put("OpenId",OpenId);
        params.put("OrderAmount",OrderAmount);
        params.put("Logo_ID",Logo_ID);
        params.put("payType","1");
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.sureGoodsOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<WxInfo,Object>() {
                    @Override
                    public void onResponse(WxInfo rightNows, String status, Object page) {
                        payContract.sureOrderSuccess(rightNows);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        payContract.sureOrderFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }


}
