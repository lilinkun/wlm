package com.wlm.wlm.activity;

import android.os.Bundle;
import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.BeautyHealthContract;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.presenter.BeautyHealthPresenter;
import com.wlm.wlm.ui.CustomBannerView;
import com.wlm.wlm.ui.CustomSortLayout;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.xw.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/10/31.
 */
public class BeautyHealthActivity extends BaseActivity implements BeautyHealthContract, CustomSortLayout.SortListerner {

    @BindView(R.id.custom_sort)
    CustomSortLayout custom_sort;
    @BindView(R.id.bannerView)
    Banner bannerView;

    BeautyHealthPresenter beautyHealthPresenter = new BeautyHealthPresenter();

    private int pageIndex = 1;
    private int goodstype = WlmUtil.GOODSTYPE_BEAUTY_HEALTH;
    private String orderby = "0";

    private ArrayList<FlashBean> flashBeans;


    @Override
    public int getLayoutId() {
        return R.layout.activity_beaty_health;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarColor1(this,getResources().getColor(R.color.point_red));

        beautyHealthPresenter.onCreate(this,this);

        ActivityUtil.addHomeActivity(this);

        custom_sort.setListener(this);

        beautyHealthPresenter.setFlash("7");

        beautyHealthPresenter.getData(pageIndex+"", WlmUtil.PAGE_COUNT,goodstype + "",orderby,true);
    }

    @OnClick({R.id.ll_back,R.id.ll_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                finish();

                break;

            case R.id.ll_search:

                Bundle bundle = new Bundle();
                bundle.putString("id","1");
                UiHelper.launcherBundle(this,SearchActivity.class,bundle);

                break;
        }
    }

    @Override
    public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean pageBean) {

        custom_sort.setPageIndex(pageIndex,pageBean);
        custom_sort.setData(goodsListBeans, WlmUtil.GOODSTYPE_BEAUTY_HEALTH);
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onFlashSuccess(ArrayList<FlashBean> flashBeans) {

        this.flashBeans = flashBeans;

        CustomBannerView.startBanner(flashBeans,bannerView,this,true);
    }

    @Override
    public void onFlashFail(String msg) {

    }


    @Override
    public void onRefresh() {
        pageIndex = 1;
        beautyHealthPresenter.getData(pageIndex+"", WlmUtil.PAGE_COUNT,goodstype + "",orderby,true);
    }

    @Override
    public void onLoadding(int page) {
        pageIndex = page;
        beautyHealthPresenter.getData(pageIndex+"", WlmUtil.PAGE_COUNT,goodstype + "",orderby,true);
    }
}
