package com.wlm.wlm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
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
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.xw.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/10/30.
 */
public class FlashSaleActivity extends BaseActivity implements FlashSaleContract, CustomSortLayout.SortListerner, IGoodsTypeListener, GrouponAdapter.OnItemClickListener {

    FlashSalePresenter flashSalePresenter = new FlashSalePresenter();

    @BindView(R.id.bannerView)
    Banner bannerView;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;
    @BindView(R.id.rv_flash_sale)
    XRecyclerView rv_flash_sale;

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
        ActivityUtil.addHomeActivity(this);

        rv_flash_sale.addItemDecoration(new SpaceItemDecoration(0, 20,10));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_flash_sale.setLayoutManager(linearLayoutManager);
        rv_flash_sale.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rv_flash_sale.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        rv_flash_sale.setArrowImageView(R.drawable.iconfont_downgrey);
        rv_flash_sale.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                flashSalePresenter.getData(pageIndex+"","20",goodstype + "",orderby,false);
            }

            @Override
            public void onLoadMore() {
                if (grouponAdapter != null) {
                    if (pageIndex  >= Integer.valueOf(pageBean.getMaxPage())){
//                        rv_groupon.loadMoreComplete();
                        rv_flash_sale.setNoMore(true);
                    }else {
                        pageIndex++;
                        flashSalePresenter.getData(pageIndex+"","20",goodstype + "",orderby,false);
                    }

                }
            }
        });
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

        rv_flash_sale.refreshComplete();

        rv_flash_sale.loadMoreComplete();

        this.pageBean = pageBean;
        rv_flash_sale.setVisibility(View.VISIBLE);
        if (grouponAdapter == null) {
            this.goodsListBeans = goodsListBeans;
            grouponAdapter = new GrouponAdapter(this,goodsListBeans,goodstype);
            rv_flash_sale.setAdapter(grouponAdapter);
            grouponAdapter.setItemClickListener(this);
        }else {
//            if(swipeRefreshLayout.isRefreshing()) {
//                swipeRefreshLayout.setRefreshing(false);
//            }
            if (pageBean.getPageIndex() == 1){
                grouponAdapter.setData(goodsListBeans);
            }else {
                this.goodsListBeans.addAll(goodsListBeans);
                grouponAdapter.setData(this.goodsListBeans);
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        if (msg.contains("查无数据")){
            rv_flash_sale.setVisibility(View.GONE);
        }
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


    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.GOODSID, goodsListBeans.get(position).getGoodsId());
        UiHelper.launcherBundle(this, SelfGoodsDetailActivity.class, bundle);
    }
}