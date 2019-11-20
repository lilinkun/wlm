package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.CategoryContract;
import com.wlm.wlm.entity.CategoryListBean;
import com.wlm.wlm.entity.ResultBean;
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
 * Created by LG on 2018/12/7.
 */

public class CategoryPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private CategoryContract categoryContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        categoryContract = (CategoryContract) view;
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

    public void getCategoryList(String SessionId){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("cls","Category");
        hashMap.put("fun","CategoryList");
        hashMap.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getCategory(hashMap)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new HttpResultCallBack<ArrayList<CategoryListBean<ArrayList<Object>>>, Object>() {

            @Override
            public void onResponse(ArrayList<CategoryListBean<ArrayList<Object>>> categoryListBeans, String status,Object o) {

                categoryContract.getDataSuccess(categoryListBeans);
                /*if (homeHeadBeanCategoryListBean.get(0).getSubclass().size() > 0){
                    ArrayList<Object> getSubclass = (ArrayList<Object>)homeHeadBeanCategoryListBean.get(0).getSubclass();
                    Gson gson = new Gson();
                    for (int i = 0; i < getSubclass.size();i++) {
                        String str = gson.toJson(getSubclass.get(i));
                        HomeCategoryBean homeCategoryBean = gson.fromJson(str, HomeCategoryBean.class);
                        UToast.show(mContext,homeCategoryBean.getCat_id() + "");
                    }
                }*/
            }

            @Override
            public void onErr(String msg, String status) {
                UToast.show(mContext,msg);
            }

            @Override
            public void onNext(ResultBean<ArrayList<CategoryListBean<ArrayList<Object>>>, Object> result) {
                super.onNext(result);
            }
        }));
    }
}
