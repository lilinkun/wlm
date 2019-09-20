package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.ModifyPayPsdContract;
import com.wlm.wlm.contract.MyFansContract;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/9/20.
 */
public class MyFansPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private MyFansContract myFansContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        myFansContract = (MyFansContract) view;
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


    public void getFansData(String PageIndex,String PageCount,String sessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseRefereesList_Vip");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("SessionId", sessionId);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack(){

                    @Override
                    public void onResponse(Object o, String status,Object page) {
                        myFansContract.getFansSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        myFansContract.getFansFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }
}
