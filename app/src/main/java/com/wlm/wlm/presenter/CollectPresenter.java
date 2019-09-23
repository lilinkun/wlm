package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.CollectContract;
import com.wlm.wlm.contract.HomeContract;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.HomeHeadBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.entity.TbjsonBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/11.
 */

public class CollectPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private CollectContract collectContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        collectContract = (CollectContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }

    public void getCollectDataList(String PageIndex,String PageCount,String CollectType,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Collect");
        params.put("fun","CollectList");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("CollectType",CollectType);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.GoodCollectList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<CollectBean>,PageBean>() {
                    @Override
                    public void onResponse(ArrayList<CollectBean> collectBeans, String status,PageBean page) {
                        collectContract.getCollectDataSuccess(collectBeans,page.getMaxPage());
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        collectContract.getCollectFail(msg);
                    }
                }));
    }


}
