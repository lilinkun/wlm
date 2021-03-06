package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.GrouponContract;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
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
 * Created by LG on 2019/9/2.
 */
public class GrouponPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private GrouponContract grouponContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        grouponContract = (GrouponContract) view;
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
     * 获取团购信息
     *
     * @param PageIndex
     * @param PageCount
     * @param GoodsType
     */
    public void getData(String PageIndex, String PageCount, String GoodsType, String OrderBy, String TeamType, boolean showload) {

        final LoaddingDialog loaddingDialog = new LoaddingDialog(mContext);
        if (showload) {
            loaddingDialog.show();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsListVip");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("GoodsType", GoodsType);
        params.put("OrderBy", OrderBy);
        params.put("GoodsFlag", "2");
        if (!TeamType.equals("0")) {
            params.put("TeamType", TeamType);
        }
        mCompositeSubscription.add(manager.grouponData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GoodsListBean>, PageBean>() {
                    @Override
                    public void onResponse(ArrayList<GoodsListBean> integralBean, String status, PageBean page) {
                        grouponContract.getSuccess(integralBean, page);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        grouponContract.getFail(msg);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }
                }));
    }

    /**
     * 团购订单
     *
     * @param OrderInfo
     * @param OrderInfoTeamAdd
     * @param GoodsId
     * @param Num
     * @param OrderAmount
     * @param ShippingFree
     * @param AddressID
     * @param Integral
     * @param SessionId
     */
    public void setOrder(String OrderInfo, String OrderInfoTeamAdd, String GoodsId, String Num, String OrderAmount, String ShippingFree, String AddressID, String Integral, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsListVip");
        params.put("OrderInfo", OrderInfo);
        params.put("OrderInfoTeamAdd", OrderInfoTeamAdd);
        params.put("GoodsId", GoodsId);
        params.put("Num", Num);
        params.put("OrderAmount", OrderAmount);
        params.put("AddressID", AddressID);
        params.put("ShippingFree", ShippingFree);
        params.put("Integral", Integral);
        params.put("GoodsFlag", "2");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.grouponOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GoodsListBean>, Object>() {
                    @Override
                    public void onResponse(ArrayList<GoodsListBean> integralBean, String status, Object page) {
//                        grouponContract.getSuccess(integralBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        grouponContract.getFail(msg);
                    }
                }));
    }

    /**
     * 获取团购flash
     *
     * @param Style
     */
    public void setFlash(String Style) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Flash");
        params.put("fun", "FlashVipList");
        params.put("Style", Style);
        mCompositeSubscription.add(manager.getFlash(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<FlashBean>, Object>() {
                    @Override
                    public void onResponse(ArrayList<FlashBean> flashBeans, String status, Object page) {
                        grouponContract.onFlashSuccess(flashBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        grouponContract.onFlashFail(msg);
                    }

                }));
    }

}
