package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.SelfSearchContract;
import com.wlm.wlm.contract.TbAllContract;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.entity.TbjsonBean;
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
 * Created by LG on 2018/12/19.
 */

public class SelfSearchPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private SelfSearchContract selfSearchContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        selfSearchContract = (SelfSearchContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }


    public void setList(String PageIndex,String PageCount,String adzone_id,String q,String sort,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","TaobaoTbk");
        params.put("fun","dgMaterialOptional");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("adzone_id",adzone_id);
        params.put("has_coupon","1");
        params.put("sort",sort);
        params.put("SessionId",SessionId);
        if (q.equals("文体车品")){
            q = "汽车";
        }
        params.put("q",q);

        mCompositeSubscription.add(manager.tbList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<TbjsonBean<ArrayList<TbMaterielBean>>,Object>(){

                    @Override
                    public void onResponse(TbjsonBean<ArrayList<TbMaterielBean>> arrayListTbjsonBean, String status,Object page) {
                        selfSearchContract.onSuccess(arrayListTbjsonBean.getResultList());
//                        homeContract.onSuccess(arrayListTbjsonBean.getResults()));
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        selfSearchContract.onError(msg);
                    }

                    @Override
                    public void onNext(ResultBean<TbjsonBean<ArrayList<TbMaterielBean>>, Object> result) {
                        super.onNext(result);
                    }
                }));
    }

    public void selfSearch(String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "Serach_Goods");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getselfSearch(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<String>,Object>(){

                    @Override
                    public void onResponse(ArrayList<String> selfGoodsBeans, String status,Object page) {
                        selfSearchContract.onSelfSuccess(selfGoodsBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        selfSearchContract.onSelfFail(msg);
                    }

                })
        );
    }

    /**
     * 获取查询商品信息
     * @param PageIndex
     * @param PageCount
     * @param GoodsType
     */
    public void getData(String PageIndex,String PageCount,String GoodsType,String OrderBy,String GoodsName){

        final LoaddingDialog loaddingDialog = new LoaddingDialog(mContext);
        loaddingDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Goods");
        params.put("fun","GoodsListVip");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("GoodsName",GoodsName);
        if (!GoodsType.equals("")) {
            params.put("GoodsType", GoodsType);
        }
        params.put("OrderBy",OrderBy);
        mCompositeSubscription.add(manager.grouponData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GoodsListBean>, PageBean>() {
                    @Override
                    public void onResponse(ArrayList<GoodsListBean> integralBean, String status,PageBean page) {
                        selfSearchContract.getSearchResultSuccess(integralBean);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        selfSearchContract.getSearchResultFail(msg);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }
                }));
    }
}
