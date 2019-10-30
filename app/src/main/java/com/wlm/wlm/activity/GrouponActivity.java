package com.wlm.wlm.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.ChooseGrouponAdapter;
import com.wlm.wlm.adapter.GrouponAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.GrouponContract;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.presenter.GrouponPresenter;
import com.wlm.wlm.transform.BannerTransform;
import com.wlm.wlm.ui.CustomBannerView;
import com.wlm.wlm.ui.LoadRecyclerView;
import com.wlm.wlm.ui.RecyclerViewScrollListener;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.CustomRoundedImageLoader;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.GrouponType;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 拼团
 * Created by LG on 2019/8/19.
 */
public class GrouponActivity extends BaseActivity implements GrouponContract,GrouponAdapter.OnItemClickListener, IGoodsTypeListener, RecyclerViewScrollListener.OnLoadListener {

    @BindView(R.id.bannerView)
    Banner banner;
    @BindView(R.id.rv_groupon)
    XRecyclerView rv_groupon;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;
//    @BindView(R.id.swipe_container)
//    SwipeRefreshLayout swipeRefreshLayout;

    GrouponPresenter groupon = new GrouponPresenter();
    GrouponAdapter grouponAdapter = null;
    ArrayList<GoodsListBean> goodsListBeans = null;
    private ArrayList<FlashBean> flashBeans;

    private int goodstype = WlmUtil.GOODSTYPE_GROUPON;
    private String orderby = "0";
    private String mTeamType = "0";
    private boolean isGrouponType = false;

    private int pageIndex = 1;
    private PageBean pageBean = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_groupon;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.setting_title_color));

        ActivityUtil.addHomeActivity(this);

        ll_top.setListener(this);

        groupon.onCreate(this,this);


        rv_groupon.addItemDecoration(new SpaceItemDecoration(0, 20,10));

//        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_groupon.setLayoutManager(linearLayoutManager);

//        rv_groupon.setOnLoadListener(this);

        groupon.getData(pageIndex+"","20",goodstype + "",orderby,mTeamType,true);

        groupon.setFlash("2");

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                groupon.getData(pageIndex+"","20",goodstype + "",orderby,mTeamType);
//            }
//        });
        rv_groupon.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rv_groupon.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        rv_groupon.setArrowImageView(R.drawable.iconfont_downgrey);
        rv_groupon.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                groupon.getData(pageIndex+"","20",goodstype + "",orderby,mTeamType,false);
            }

            @Override
            public void onLoadMore() {
                if (grouponAdapter != null) {
                    if (pageIndex  >= Integer.valueOf(pageBean.getMaxPage())){
//                        rv_groupon.loadMoreComplete();
                        rv_groupon.setNoMore(true);
                    }else {
                        pageIndex++;
                        groupon.getData(pageIndex+"","20",goodstype + "",orderby,mTeamType,false);
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
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
//        bundle.putSerializable(WlmUtil.GROUPONGOODS,goodsListBeans.get(position));
//        UiHelper.launcherBundle(this,GrouponGoodsDetailActivity.class,bundle);

        bundle.putString(WlmUtil.GOODSID, goodsListBeans.get(position).getGoodsId());
//        bundle.putString(WlmUtil.TYPE, WlmUtil.getType(GoodsType + ""));
        UiHelper.launcherBundle(this, SelfGoodsDetailActivity.class, bundle);
    }

    @Override
    public void getSortType(int sortType) {
        pageIndex = 1;
        switch (sortType){
            case 1://默认排序
                isGrouponType = false;
                groupon.getData(pageIndex+"","20",goodstype+"",orderby,"0",true);
                ll_top.setText(getString(R.string.groupon_all));
                break;

            case 2://几人团

                if (isGrouponType) {
                    View view = LayoutInflater.from(this).inflate(R.layout.pop_layout, null);
                    RecyclerView recyclerView = view.findViewById(R.id.rv_groupon);

                    ChooseGrouponAdapter chooseGrouponAdapter = new ChooseGrouponAdapter(this,0);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recyclerView.setLayoutManager(linearLayoutManager);

                    recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    });

                    recyclerView.setAdapter(chooseGrouponAdapter);

                    final PopupWindow popupWindow = new PopupWindow(view,
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.showAsDropDown(ll_top);
                    chooseGrouponAdapter.setOnItemClick(new ChooseGrouponAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            if (position == 0) {
                                mTeamType = "1";
                            } else if (position == 1) {
                                mTeamType = "2";
                            } else if (position == 2){
                                mTeamType = "0";
                            }
                            ll_top.setText(GrouponType.values()[position].getTypeName());
                            orderby = "0";
                            groupon.getData("1", "20", goodstype + "", orderby, mTeamType,true);
                            popupWindow.dismiss();
                        }
                    });
                }else {
                    isGrouponType = true;
                }
                break;


            case 3://销量上
                orderby = "1";
                isGrouponType = false;
                groupon.getData(pageIndex+"","20",goodstype+"",orderby,mTeamType,true);

                break;


            case 4://销量下

                orderby = "2";
                isGrouponType = false;
                groupon.getData(pageIndex+"","20",goodstype+"",orderby,mTeamType,true);

                break;

            case 5://价格上

                orderby = "3";
                isGrouponType = false;
                groupon.getData(pageIndex+"","20",goodstype+"",orderby,mTeamType,true);

                break;

            case 6://价格下

                orderby = "4";
                isGrouponType = false;
                groupon.getData(pageIndex+"","20",goodstype+"",orderby,mTeamType,true);

                break;
        }
    }

    @Override
    public void getSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean page) {

        rv_groupon.refreshComplete();

        rv_groupon.loadMoreComplete();

        this.pageBean = page;
        rv_groupon.setVisibility(View.VISIBLE);
        if (grouponAdapter == null) {
            this.goodsListBeans = goodsListBeans;
            grouponAdapter = new GrouponAdapter(this,goodsListBeans,goodstype);
            rv_groupon.setAdapter(grouponAdapter);
            grouponAdapter.setItemClickListener(this);
        }else {
//            if(swipeRefreshLayout.isRefreshing()) {
//                swipeRefreshLayout.setRefreshing(false);
//            }
            if (page.getPageIndex() == 1){
                grouponAdapter.setData(goodsListBeans);
            }else {
                this.goodsListBeans.addAll(goodsListBeans);
                grouponAdapter.setData(this.goodsListBeans);
            }
        }
    }

    @Override
    public void getFail(String msg) {
        if (msg.contains("查无数据")){
            rv_groupon.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFlashSuccess(ArrayList<FlashBean> flashBeans) {
        this.flashBeans = flashBeans;
        CustomBannerView.startBanner(flashBeans,banner,this,false);
    }

    @Override
    public void onFlashFail(String msg) {

    }

    @Override
    public void onLoad() {
        pageIndex++;
        groupon.getData(pageIndex+"","20",goodstype+"",orderby,mTeamType,false);
//        toast("asdasdads");
    }
}
