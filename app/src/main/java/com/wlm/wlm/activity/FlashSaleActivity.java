package com.wlm.wlm.activity;

import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.GrouponAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.FlashSaleContract;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.presenter.FlashSalePresenter;
import com.wlm.wlm.ui.CustomBannerView;
import com.wlm.wlm.ui.CustomSortLayout;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;
import com.xw.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/10/30.
 */
public class FlashSaleActivity extends BaseActivity implements FlashSaleContract, CustomSortLayout.SortListerner, IGoodsTypeListener {

    FlashSalePresenter flashSalePresenter = new FlashSalePresenter();

    @BindView(R.id.bannerView)
    Banner bannerView;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;
    @BindView(R.id.custom_sort)
    CustomSortLayout custom_sort;

    GrouponAdapter grouponAdapter = null;

    private int pageIndex = 1;
    private int goodstype = WlmUtil.GOODSTYPE_SECKILL;
    private String orderby = "0";
    private PageBean pageBean;
    private ArrayList<FlashBean> flashBeans;

    private ArrayList<GoodsListBean> goodsListBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_flash_sale;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarColor1(this,getResources().getColor(R.color.point_red));
        flashSalePresenter.onCreate(this,this);
        flashSalePresenter.setFlash("3");
        flashSalePresenter.getData(pageIndex+"", WlmUtil.PAGE_COUNT,goodstype + "",orderby,true);
        ll_top.setListener(this);
        custom_sort.setListener(this);

    }


    @OnClick({R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                finish();

                break;
        }
    }

    @Override
    public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean pageBean) {

        custom_sort.setPageIndex(pageIndex,pageBean);
        custom_sort.setData(goodsListBeans, WlmUtil.GOODSTYPE_POINT);
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
    public void getSortType(int sortType) {
        pageIndex = 1;
        switch (sortType){
            case 1:
                orderby = "0";

                break;

            case 3://销量上
                orderby = "1";

                break;

            case 4://销量下

                orderby = "2";

                break;

            case 5://价格上

                orderby = "3";

                break;

            case 6://价格下

                orderby = "4";

                break;
        }
        flashSalePresenter.getData(pageIndex+"", WlmUtil.PAGE_COUNT,goodstype + "",orderby,true);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        flashSalePresenter.getData(pageIndex+"", WlmUtil.PAGE_COUNT,goodstype + "",orderby,true);
    }

    @Override
    public void onLoadding(int page) {
        pageIndex = page;
        flashSalePresenter.getData(pageIndex+"", WlmUtil.PAGE_COUNT,goodstype + "",orderby,true);
    }


}