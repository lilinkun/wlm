package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.LoginContract;
import com.wlm.wlm.contract.SelfGoodsContract;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/12.
 */

public class SelfGoodsPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private SelfGoodsContract selfGoodsContract;

    @Override
    public void onCreate(Context mContext) {
        this.mContext = mContext;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
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


    @Override
    public void attachView(IView view) {
        selfGoodsContract = (SelfGoodsContract) view;
    }


    public void getGoodList(String PageIndex,String PageCount,String CatId,String SortField,String SortType,String GoodsName,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsList");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("SortField", SortField);
        params.put("CatId", CatId);
        params.put("SortType",SortType);
        params.put("GoodsName", GoodsName);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getselfGoodList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<SelfGoodsBean>,PageBean>(){

                    @Override
                    public void onResponse(ArrayList<SelfGoodsBean> selfGoodsBeans, String status,PageBean page) {
                        if (page.getMaxPage().equals(page.getPageIndex())) {
                            selfGoodsContract.getDataSuccess(selfGoodsBeans, false);
                        }else {
                            selfGoodsContract.getDataSuccess(selfGoodsBeans, true);
                        }
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        selfGoodsContract.getDataFail(msg);
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
