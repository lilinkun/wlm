package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.CrowdFundingContract;
import com.wlm.wlm.contract.FlashSaleContract;
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
 * Created by LG on 2019/10/30.
 */
public class FlashSalePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private FlashSaleContract flashSaleContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        flashSaleContract = (FlashSaleContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }

    /**
     * 获取Flash
     * @param Style
     */
    public void setFlash(String Style){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Flash");
        params.put("fun","FlashVipList");
        params.put("Style",Style);
        mCompositeSubscription.add(manager.getFlash(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<FlashBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<FlashBean> flashBeans, String status,Object page) {
                        flashSaleContract.onFlashSuccess(flashBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        flashSaleContract.onFlashFail(msg);
                    }

                }));
    }

    /**
     * 获取众筹信息
     * @param PageIndex
     * @param PageCount
     * @param GoodsType
     */
    public void getData(String PageIndex,String PageCount,String GoodsType,String OrderBy,boolean showload){

        final LoaddingDialog loaddingDialog = new LoaddingDialog(mContext);
        if (showload) {
            loaddingDialog.show();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Goods");
        params.put("fun","GoodsListVip");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("GoodsType",GoodsType);
        params.put("OrderBy",OrderBy);
        params.put("GoodsFlag", "2");

        mCompositeSubscription.add(manager.grouponData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GoodsListBean>, PageBean>() {
                    @Override
                    public void onResponse(ArrayList<GoodsListBean> goodsListBeans, String status,PageBean page) {
                        flashSaleContract.getDataSuccess(goodsListBeans,page);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        flashSaleContract.getDataFail(msg);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }
                }));
    }
}
