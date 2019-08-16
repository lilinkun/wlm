package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.MyNickNameContract;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/26.
 */

public class MyNickNamePresenter extends BasePresenter {

    private Context context;
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private MyNickNameContract myNickNameContract;

    @Override
    public void onCreate(Context context) {
        this.context = context;
        manager = new DataManager(context);
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
        myNickNameContract = (MyNickNameContract) view;
    }

    public void modifyAccout(String NikeName,String SessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UploadNikeName");
        params.put("NikeName", NikeName);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack(){

                    @Override
                    public void onResponse(Object o, String status,Object page) {
                        myNickNameContract.modifySuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        myNickNameContract.modifyFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

}
