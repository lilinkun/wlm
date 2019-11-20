package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.FindContract;
import com.wlm.wlm.entity.GoodsDiscoverBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/10/24.
 */
public class FindPresenter extends BasePresenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private FindContract findContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        findContract = (FindContract) view;
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
     * 获取find
     */
    public void getFindData(String PageIndex, String PageCount) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "GoodsDiscover");
        params.put("fun", "GoodsDiscoverListVip");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        mCompositeSubscription.add(manager.getFindData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GoodsDiscoverBean>, PageBean>() {
                    @Override
                    public void onResponse(ArrayList<GoodsDiscoverBean> goodsDiscoverBeans, String status, PageBean page) {
                        findContract.onGetDataSuccess(goodsDiscoverBeans, page);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        findContract.onGetDataFail(msg);
                    }

                }));
    }
}
