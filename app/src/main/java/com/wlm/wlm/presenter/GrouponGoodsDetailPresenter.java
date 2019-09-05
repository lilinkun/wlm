package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.GrouponGoodsDetailContract;
import com.wlm.wlm.entity.GoodsDetailBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class GrouponGoodsDetailPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private GrouponGoodsDetailContract grouponGoodsDetailContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        grouponGoodsDetailContract = (GrouponGoodsDetailContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void getGoodsDetail(String goodsId,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Goods");
        params.put("fun","GoodsDetail");
        params.put("goodsId",goodsId);
        params.put("SessionId",SessionId);

        mCompositeSubscription.add(manager.getGrouponGoodDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<GoodsListBean, Object>() {

                    @Override
                    public void onResponse(GoodsListBean objectObjectGoodsDetailBean, String status,Object page) {
                        grouponGoodsDetailContract.getDataSuccess(objectObjectGoodsDetailBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        grouponGoodsDetailContract.getDataFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                }));

    }
}
