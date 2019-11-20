package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.JdGoodsContract;
import com.wlm.wlm.entity.IntegralBean;
import com.wlm.wlm.entity.JdGoodsBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/1/31.
 */

public class JdGoodsPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private JdGoodsContract jdGoodsContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        jdGoodsContract = (JdGoodsContract) view;
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

    public void setData(String PageIndex,String PageCount,String Sort,String eliteId,String sortName,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","JDK");
        params.put("fun","goods_jingfen_query");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("Sort",Sort);
        params.put("eliteId",eliteId);
        params.put("sortName",sortName);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getJdGoods(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<JdGoodsBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<JdGoodsBean> jdGoodsBean, String status,Object page) {
                        jdGoodsContract.getDataSuccess(jdGoodsBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        jdGoodsContract.getDataFail(msg);
                    }
                }));
    }
}
