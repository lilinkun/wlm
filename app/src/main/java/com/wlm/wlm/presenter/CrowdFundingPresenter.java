package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.CollectContract;
import com.wlm.wlm.contract.CrowdFundingContract;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class CrowdFundingPresenter extends BasePresenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private CrowdFundingContract crowdFundingContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        crowdFundingContract = (CrowdFundingContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }

    /**
     * 获取Flash
     * @param Style
     */
    public void setFlash(String Style){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Flash");
        params.put("fun","FlashVipList");
        params.put("Style",Style);
        mCompositeSubscription.add(manager.getFlash(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<FlashBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<FlashBean> flashBeans, String status,Object page) {
                        crowdFundingContract.onFlashSuccess(flashBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        crowdFundingContract.onFlashFail(msg);
                    }

                }));
    }
}
