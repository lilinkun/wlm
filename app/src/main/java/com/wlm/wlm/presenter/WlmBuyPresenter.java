package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.WlmBuyContract;
import com.wlm.wlm.entity.Category1Bean;
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
 * Created by LG on 2019/11/4.
 */
public class WlmBuyPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private WlmBuyContract wlmBuyContract;


    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        wlmBuyContract = (WlmBuyContract) view;
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
     * 获取众筹信息
     *
     * @param PageIndex
     * @param PageCount
     * @param GoodsType
     */
    public void getData(String PageIndex, String PageCount, String GoodsType, String OrderBy, String CategoryId, boolean showload) {

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
        params.put("CategoryId", CategoryId);
        params.put("GoodsFlag", "2");

        mCompositeSubscription.add(manager.grouponData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GoodsListBean>, PageBean>() {
                    @Override
                    public void onResponse(ArrayList<GoodsListBean> goodsListBeans, String status, PageBean page) {
                        wlmBuyContract.getDataSuccess(goodsListBeans, page);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        wlmBuyContract.getDataFail(msg);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }
                }));
    }


    /**
     * 获取Flash
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
                        wlmBuyContract.onFlashSuccess(flashBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        wlmBuyContract.onFlashFail(msg);
                    }

                }));
    }


    /**
     * 获取分类
     *
     * @param PageIndex
     * @param PageCount
     */
    public void getCategoryList(String PageIndex, String PageCount, String CategoryLevel) {

        final LoaddingDialog loaddingDialog = new LoaddingDialog(mContext);
        loaddingDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Category");
        params.put("fun", "CategoryVipList");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("CategoryLevel", CategoryLevel);
        mCompositeSubscription.add(manager.getCategoryList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<Category1Bean>, Object>() {
                    @Override
                    public void onResponse(ArrayList<Category1Bean> integralBean, String status, Object page) {
                        wlmBuyContract.getCategorySuccess(integralBean);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        wlmBuyContract.getCategoryFail(msg);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }
                }));
    }


}
