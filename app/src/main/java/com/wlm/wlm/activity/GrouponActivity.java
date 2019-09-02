package com.wlm.wlm.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.GrouponAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.GrouponContract;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.presenter.GrouponPresenter;
import com.wlm.wlm.transform.BannerTransform;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.CustomRoundedImageLoader;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
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
public class GrouponActivity extends BaseActivity implements GrouponContract, OnBannerListener, GrouponAdapter.OnItemClickListener, IGoodsTypeListener {

    @BindView(R.id.bannerView)
    Banner banner;
    @BindView(R.id.rv_groupon)
    RecyclerView rv_groupon;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;

    GrouponPresenter groupon = new GrouponPresenter();
    GrouponAdapter grouponAdapter = null;


    @Override
    public int getLayoutId() {
        return R.layout.activity_groupon;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.setting_title_color));
        startBanner(null);

        ll_top.setListener(this);

        groupon.onCreate(this,this);


        rv_groupon.addItemDecoration(new SpaceItemDecoration(0, 20,10));

//        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_groupon.setLayoutManager(linearLayoutManager);


        grouponAdapter.setItemClickListener(this);

        groupon.getData("1","20","2");

    }

    private void startBanner(final ArrayList<FlashBean> flashBeans) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            strings.add("asdads" + i);
        }
//        for (int i = 0; i < flashBeans.size(); i++) {
//            strings.add("asdads" + i);
//        }


        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new CustomRoundedImageLoader());
        //设置图片网址或地址的集合
        banner.setImages(strings);
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
        UiHelper.launcher(this,GrouponGoodsDetailActivity.class);
    }

    @Override
    public void getSortType(int sortType) {
        switch (sortType){
            case 1://默认排序

                toast("默认排序");

                break;

            case 2://几人团

                toast("几人团");

                break;


            case 3://销量上

                toast("销量上");

                break;


            case 4://销量下

                toast("销量下");


                break;

            case 5://价格上


                toast("价格上");

                break;

            case 6://价格下


                toast("价格下");

                break;
        }
    }

    @Override
    public void getSuccess(ArrayList<GoodsListBean> goodsListBeans) {
        if (grouponAdapter == null) {
            grouponAdapter = new GrouponAdapter(this,goodsListBeans);
            rv_groupon.setAdapter(grouponAdapter);
        }
    }

    @Override
    public void getFail(String msg) {

    }

}
