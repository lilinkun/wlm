package com.wlm.wlm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.GrouponAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.CrowdFundingContract;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.presenter.CrowdFundingPresenter;
import com.wlm.wlm.ui.CustomBannerView;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.xw.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CrowdFundingActivity extends BaseActivity implements IGoodsTypeListener, CrowdFundingContract, GrouponAdapter.OnItemClickListener {

    @BindView(R.id.bannerView)
    Banner banner;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;
    @BindView(R.id.rv_crowd_funding)
    XRecyclerView rv_crowd_funding;

    GrouponAdapter grouponAdapter = null;

    private int pageIndex = 1;
    private int goodstype = WlmUtil.GOODSTYPE_CROWDFUNDING;
    private String orderby = "0";
    private PageBean pageBean;

    private ArrayList<GoodsListBean> goodsListBeans;

    CrowdFundingPresenter crowdFundingPresenter = new CrowdFundingPresenter();


    @Override
    public int getLayoutId() {
        return R.layout.activity_crowd_funding;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.bg_crowdfunding));

//        startBanner(null);
        crowdFundingPresenter.onCreate(this,this);
        crowdFundingPresenter.setFlash("3");
        crowdFundingPresenter.getData(pageIndex+"", WlmUtil.PAGE_COUNT,goodstype + "",orderby,true);
        ll_top.setListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_crowd_funding.setLayoutManager(linearLayoutManager);

        rv_crowd_funding.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                crowdFundingPresenter.getData(pageIndex+"", WlmUtil.PAGE_COUNT,goodstype + "",orderby,true);
            }

            @Override
            public void onLoadMore() {
                if (grouponAdapter != null) {
                    if (pageIndex  >= Integer.valueOf(pageBean.getMaxPage())){
//                        rv_groupon.loadMoreComplete();
                        rv_crowd_funding.setNoMore(true);
                    }else {
                        pageIndex++;
                        crowdFundingPresenter.getData(pageIndex+"", WlmUtil.PAGE_COUNT,goodstype + "",orderby,false);
                    }

                }
            }
        });
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
        crowdFundingPresenter.getData(pageIndex+"", WlmUtil.PAGE_COUNT,goodstype + "",orderby,true);
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
    public void onFlashSuccess(ArrayList<FlashBean> flashBeans) {

        CustomBannerView.startBanner(flashBeans,banner,this,false);

    }

    @Override
    public void onFlashFail(String msg) {

    }

    @Override
    public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean pageBean) {

        this.pageBean = pageBean;

        rv_crowd_funding.loadMoreComplete();
        rv_crowd_funding.refreshComplete();

        if (grouponAdapter == null) {
            this.goodsListBeans = goodsListBeans;
            grouponAdapter = new GrouponAdapter(this, goodsListBeans,goodstype);

            rv_crowd_funding.setAdapter(grouponAdapter);

            grouponAdapter.setItemClickListener(this);

        }else {
            if (pageBean.getPageIndex() == 1){
                this.goodsListBeans = goodsListBeans;
                grouponAdapter.setData(goodsListBeans);
            }else {
                this.goodsListBeans.addAll(goodsListBeans);
                grouponAdapter.setData(this.goodsListBeans);
            }
        }

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onItemClick(int position) {

        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.GOODSID, goodsListBeans.get(position).getGoodsId());
        UiHelper.launcherBundle(this, SelfGoodsDetailActivity.class, bundle);
    }
}
