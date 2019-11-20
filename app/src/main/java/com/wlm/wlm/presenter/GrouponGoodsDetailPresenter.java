package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.GrouponGoodsDetailContract;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;
import com.wlm.wlm.ui.LoaddingDialog;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class GrouponGoodsDetailPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private GrouponGoodsDetailContract grouponGoodsDetailContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        grouponGoodsDetailContract = (GrouponGoodsDetailContract) view;
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

    public void getGoodsDetail(String goodsId, String SessionId) {
//        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);
        final LoaddingDialog loaddingDialog = new LoaddingDialog(mContext);
        loaddingDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsGet");
        params.put("GoodsId", goodsId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getSelfGoodDetailInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<GoodsDetailInfoBean<ArrayList<GoodsChooseBean>>, Object>() {

                    @Override
                    public void onResponse(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> objectObjectGoodsDetailBean, String status, Object page) {
                        grouponGoodsDetailContract.getDataSuccess(objectObjectGoodsDetailBean);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        grouponGoodsDetailContract.getDataFail(msg);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }

                }));

    }


    /**
     * 随机推荐商品
     *
     * @param GoodsId
     */
    public void randomGoods(String type, String GoodsId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsListRecommendVip");
        params.put("type", type);
        params.put("GoodsFlag", "2");
        params.put("GoodsId", GoodsId);
        mCompositeSubscription.add(manager.getGoodsList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GoodsListBean>, Object>() {
                    @Override
                    public void onResponse(ArrayList<GoodsListBean> goodsListBeans, String status, Object page) {
                        grouponGoodsDetailContract.getCommendGoodsSuccess(goodsListBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        grouponGoodsDetailContract.getCommendGoodsFail(msg);
                    }
                }));
    }

}
