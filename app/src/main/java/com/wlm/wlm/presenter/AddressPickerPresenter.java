package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.AddAddressContract;
import com.wlm.wlm.contract.AddressPickerContract;
import com.wlm.wlm.entity.ProvinceBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/10.
 */

public class AddressPickerPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private AddressPickerContract addressPickerContract;

    @Override
    public void onCreate(Context context) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }

    @Override
    public void attachView(IView view) {
        addressPickerContract = (AddressPickerContract) view;
    }

    public void getLocalData(String parentId,final int localType){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Dict_Region");
        params.put("fun","DictRegionList");
        params.put("parentId",parentId);
        mCompositeSubscription.add(manager.getLocalData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<ProvinceBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<ProvinceBean> provinceBeans, String status,Object page) {
                        addressPickerContract.getDataSuccess(provinceBeans,localType);
                    }

                    @Override
                    public void onErr(String msg, String status) {

                    }
                }));
    }
}
