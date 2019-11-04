package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.AddAddressContract;
import com.wlm.wlm.entity.ProvinceBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;
import com.wlm.wlm.util.UToast;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/10.
 */

public class AddAddressPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private AddAddressContract addAddressContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        addAddressContract = (AddAddressContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }

    public void getLocalData(String parentId,final int localType){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Dict_Region");
        params.put("fun","RegionListDrop");
        params.put("parentId",parentId);
        mCompositeSubscription.add(manager.getLocalData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<ProvinceBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<ProvinceBean> provinceBeans, String status,Object page) {
                        addAddressContract.getDataSuccess(provinceBeans,localType);
                    }

                    @Override
                    public void onErr(String msg, String status) {

                    }
                }));
    }

    public void getSaveAddress(String Name,String Province,String City,String District,String Address,String ZipCode,String Mobile,String IsDefault,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","ReceiptAddress");
        params.put("fun","ReceiptAddressCreate");
        params.put("Name",Name);
        params.put("prov",Province);
        params.put("city",City);
        params.put("area",District);
        params.put("Address",Address);
        params.put("Post",ZipCode);
        params.put("Mobile",Mobile);
        params.put("IsDefault",IsDefault);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getSaveAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {

                    @Override
                    public void onResponse(Object o, String status,Object page) {
                        addAddressContract.getSaveSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        addAddressContract.getSaveFail(msg);
                    }
                }));
    }

    /**
     * 修改地址
     */
    public void modifyAddress(String AddressId,String Consignee,String Province,String City,String District,String Address,String ZipCode,String Mobile,String IsDefault,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","ReceiptAddress");
        params.put("fun","ReceiptAddressUpdate");
        params.put("Name",Consignee);
        params.put("AddressID",AddressId);
        params.put("prov",Province);
        params.put("city",City);
        params.put("area",District);
        params.put("Address",Address);
        params.put("Post",ZipCode);
        params.put("Mobile",Mobile);
        params.put("IsDefault",IsDefault);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getSaveAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {

                    @Override
                    public void onResponse(Object o, String status,Object page) {
                        addAddressContract.modifySuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        addAddressContract.modifyFail(msg);
                    }
                }));
    }
}
