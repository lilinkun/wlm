package com.wlm.wlm.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.ChooseGrouponAdapter;
import com.wlm.wlm.adapter.GrouponAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.GrouponContract;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.presenter.GrouponPresenter;
import com.wlm.wlm.transform.BannerTransform;
import com.wlm.wlm.ui.AutoSwipeRefreshLayout;
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
public class GrouponActivity extends BaseActivity implements GrouponContract, OnBannerListener, GrouponAdapter.OnItemClickListener, IGoodsTypeListener, RecyclerViewScrollListener.OnLoadListener {

    @BindView(R.id.bannerView)
    Banner banner;
    @BindView(R.id.rv_groupon)
    LoadRecyclerView rv_groupon;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    GrouponPresenter groupon = new GrouponPresenter();
    GrouponAdapter grouponAdapter = null;
    ArrayList<GoodsListBean> goodsListBeans = null;

    private int goodstype = 2;
    private String orderby = "0";
    private String mTeamType = "0";
    private boolean isGrouponType = false;

    private int pageIndex = 1;


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

        rv_groupon.setOnLoadListener(this);

        groupon.getData(pageIndex+"","20",goodstype + "",orderby,mTeamType);

        groupon.setFlash("2");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                groupon.getData(pageIndex+"","20",goodstype + "",orderby,mTeamType);
            }
        });

    }

    private void startBanner(final ArrayList<FlashBean> flashBeans) {
        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < flashBeans.size(); i++) {
            strings.add("asdads" + i);
        }

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new CustomRoundedImageLoader());
        //设置图片网址或地址的集合
        banner.setImages(flashBeans);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.RotateDown);

        banner.setPageTransformer(true,new BannerTransform());

        //设置轮播图的标题集合
        banner.setBannerTitles(strings);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();

    }

    @Override
    public void OnBannerClick(int position) {

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
        bundle.putSerializable(WlmUtil.GROUPONGOODS,goodsListBeans.get(position));
        UiHelper.launcherBundle(this,GrouponGoodsDetailActivity.class,bundle);
    }

    @Override
    public void getSortType(int sortType) {
        switch (sortType){
            case 1://默认排序
                isGrouponType = false;
                groupon.getData(pageIndex+"","20",goodstype+"",orderby,"0");
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
                            }
                            ll_top.setText(GrouponType.values()[position].getTypeName());
                            orderby = "0";
                            groupon.getData("1", "20", goodstype + "", orderby, mTeamType);
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
                groupon.getData(pageIndex+"","20",goodstype+"",orderby,mTeamType);

                break;


            case 4://销量下

                orderby = "2";
                isGrouponType = false;
                groupon.getData(pageIndex+"","20",goodstype+"",orderby,mTeamType);

                break;

            case 5://价格上

                orderby = "3";
                isGrouponType = false;
                groupon.getData(pageIndex+"","20",goodstype+"",orderby,mTeamType);

                break;

            case 6://价格下

                orderby = "4";
                isGrouponType = false;
                groupon.getData(pageIndex+"","20",goodstype+"",orderby,mTeamType);

                break;
        }
    }

    @Override
    public void getSuccess(ArrayList<GoodsListBean> goodsListBeans) {
        rv_groupon.setVisibility(View.VISIBLE);
        if (grouponAdapter == null) {
            this.goodsListBeans = goodsListBeans;
            grouponAdapter = new GrouponAdapter(this,goodsListBeans);
            rv_groupon.setAdapter(grouponAdapter);
            grouponAdapter.setItemClickListener(this);
        }else {
            if(swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            this.goodsListBeans.addAll(goodsListBeans);
            grouponAdapter.setData(goodsListBeans);
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

        startBanner(flashBeans);
    }

    @Override
    public void onFlashFail(String msg) {

    }

    @Override
    public void onLoad() {
        pageIndex++;
        groupon.getData(pageIndex+"","20",goodstype+"",orderby,mTeamType);
//        toast("asdasdads");
    }
}
