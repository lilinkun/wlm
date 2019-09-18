package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.IntegralStoreContract;
import com.wlm.wlm.contract.ManufactureStoreContract;
import com.wlm.wlm.entity.Category1Bean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import org.greenrobot.greendao.annotation.OrderBy;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/9/12.
 */
public class ManufactureStorePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ManufactureStoreContract manufactureStoreContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        manufactureStoreContract = (ManufactureStoreContract) view;
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
     * 获取分类
     * @param PageIndex
     * @param PageCount
     */
    public void getCategoryList(String PageIndex,String PageCount){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Category");
        params.put("fun","CategoryVipList");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        mCompositeSubscription.add(manager.getCategoryList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<Category1Bean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<Category1Bean> integralBean, String status, Object page) {
                        manufactureStoreContract.getCategorySuccess(integralBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        manufactureStoreContract.getCategoryFail(msg);
                    }
                }));
    }

    /**
     * 获取类别下商品列表
     * @param PageIndex
     * @param PageCount
     * @param GoodsType
     */
    public void getData(String PageIndex, String PageCount, String GoodsType, String CategoryId,String OrderBy){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Goods");
        params.put("fun","GoodsListVip");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("GoodsType",GoodsType);
        params.put("CategoryId",CategoryId);
        params.put("OrderBy",OrderBy);
        mCompositeSubscription.add(manager.grouponData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GoodsListBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<GoodsListBean> integralBean, String status,Object page) {
                        manufactureStoreContract.getSuccess(integralBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        manufactureStoreContract.getFail(msg);
                    }
                }));
    }
}