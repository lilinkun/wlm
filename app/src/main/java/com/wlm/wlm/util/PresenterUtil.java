package com.wlm.wlm.util;

import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/11/8.
 */
public class PresenterUtil {

    public static void collectGoods(CompositeSubscription mCompositeSubscription, DataManager manager, String goodsId, String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Collect");
        params.put("fun","CollectCreate");
        params.put("goodsId",goodsId);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getCollect(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {
                    @Override
                    public void onResponse(Object o, String status,Object page) {

                    }

                    @Override
                    public void onErr(String msg, String status) {

                    }

                }));
    }
}
