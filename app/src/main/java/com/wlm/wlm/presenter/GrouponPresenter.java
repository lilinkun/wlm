package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.GrouponContract;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;
import com.wlm.wlm.ui.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/9/2.
 */
public class GrouponPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private GrouponContract grouponContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        grouponContract = (GrouponContract) view;
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
     * 获取团购信息
     * @param PageIndex
     * @param PageCount
     * @param GoodsType
     */
    public void getData(String PageIndex,String PageCount,String GoodsType,String OrderBy){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Goods");
        params.put("fun","GoodsListVip");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("GoodsType",GoodsType);
        params.put("OrderBy",OrderBy);
        mCompositeSubscription.add(manager.grouponData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GoodsListBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<GoodsListBean> integralBean, String status,Object page) {
                        grouponContract.getSuccess(integralBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        grouponContract.getFail(msg);
                    }
                }));
    }

}
