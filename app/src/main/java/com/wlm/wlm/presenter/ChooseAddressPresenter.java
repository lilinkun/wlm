package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.ChooseAddressContract;
import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;
import com.wlm.wlm.ui.LoaddingDialog;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/9.
 */

public class ChooseAddressPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ChooseAddressContract chooseAddressContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        chooseAddressContract = (ChooseAddressContract) view;
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

    /**
     * 获取收货地址
     *
     * @param PageIndex
     * @param PageCount
     * @param SessionId
     */
    public void getAddress(String PageIndex, String PageCount, String SessionId) {
        final LoaddingDialog loaddingDialog = new LoaddingDialog(mContext);
        loaddingDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressList");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.getConsigneeAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<AddressBean>, Object>() {

                    @Override
                    public void onResponse(ArrayList<AddressBean> addressBeans, String status, Object page) {
                        chooseAddressContract.setDataSuccess(addressBeans);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        chooseAddressContract.setDataFail(msg);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }
                })
        );
    }


    /**
     * 删除地址
     *
     * @param userAddressId
     */
    public void deletAddress(String userAddressId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressDelete");
        params.put("AddressID", userAddressId);
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.getDeleteAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {

                    @Override
                    public void onResponse(Object o, String status, Object page) {
                        chooseAddressContract.deleteSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        chooseAddressContract.deleteFail(msg);
                    }
                })
        );
    }


    /**
     * 设置默认地址
     *
     * @param UserAddressId
     * @param SessionId
     */
    public void isDefault(String UserAddressId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressSetDefault");
        params.put("AddressID", UserAddressId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.isDefault(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {

                    @Override
                    public void onResponse(String isDefaultStr, String status, Object page) {
                        chooseAddressContract.isDefaultSuccess(isDefaultStr);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        chooseAddressContract.isDefaultFail(msg);
                    }
                }));
    }
}
