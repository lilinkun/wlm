package com.wlm.wlm.activity;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.GrouponGoodsDetailContract;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.entity.RightNowGoodsBean;
import com.wlm.wlm.presenter.GrouponGoodsDetailPresenter;
import com.wlm.wlm.ui.CommendRecyclerView;
import com.wlm.wlm.ui.CountdownView;
import com.wlm.wlm.ui.FullyGridLayoutManager;
import com.wlm.wlm.ui.MyTextView;
import com.wlm.wlm.ui.PriceTextView;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;
import com.xw.banner.loader.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/8/27.
 */
public class GrouponGoodsDetailActivity extends BaseActivity implements OnBannerListener, GrouponGoodsDetailContract, TbHotGoodsAdapter.OnItemClickListener {

    @BindView(R.id.banner_good_pic)
    Banner mBanner;
    @BindView(R.id.tv_groupon_old_price)
    MyTextView tv_groupon_old_price;
    @BindView(R.id.tv_groupon_price)
    PriceTextView tv_groupon_price;
    @BindView(R.id.tv_rush_time)
    CountdownView tv_rush_time;
    @BindView(R.id.tv_goods_name)
    TextView tv_goods_name;
    @BindView(R.id.tv_grounon_info)
    TextView tv_grounon_info;
    @BindView(R.id.tv_right_now_groupon)
    TextView tv_right_now_groupon;
    @BindView(R.id.tv_distance_ends)
    TextView tv_distance_ends;
    @BindView(R.id.wv_goods_detail)
    WebView wv_goods_detail;
    @BindView(R.id.rv_recommend)
    CommendRecyclerView recyclerView;

    private String goodsid = "";
    GoodsListBean goodsListBean = null;
    GrouponGoodsDetailPresenter grouponGoodsDetailPresenter = new GrouponGoodsDetailPresenter();
    private GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean;
    private ArrayList<GoodsListBean> goodsListBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_groupon_goods_details;
    }

    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(this,false);

        ActivityUtil.addHomeActivity(this);
        ActivityUtil.addActivity(this);

        Bundle bundle = getIntent().getBundleExtra(WlmUtil.TYPEID);

        if (bundle != null && bundle.getSerializable(WlmUtil.GROUPONGOODS) != null){
            goodsListBean = (GoodsListBean) bundle.getSerializable(WlmUtil.GROUPONGOODS);
            goodsid = goodsListBean.getGoodsId();
        }else if (bundle != null && bundle.getString(WlmUtil.GOODSID) != null && bundle.getString(WlmUtil.GOODSID).equals("")){
            goodsid = bundle.getString(WlmUtil.GOODSID);
        }

        grouponGoodsDetailPresenter.onCreate(this,this);
        grouponGoodsDetailPresenter.getGoodsDetail(goodsid, ProApplication.SESSIONID(this));

        grouponGoodsDetailPresenter.randomGoods("2",goodsid);


        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(this, 2);
        fullyGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        int spanCount = 5; // 2 columns
        int spacing = 20; // 50px

        boolean includeEdge = false;
        recyclerView.addItemDecoration(new SpaceItemDecoration(spanCount, spacing,0));
        recyclerView.setLayoutManager(fullyGridLayoutManager);


    }

    public void startBanner(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean){
        ArrayList<String> strings = new ArrayList<>();

        String[] str = goodsDetailBean.getGoodsImgList().split(",");

        for (int i = 0; i< str.length;i++) {
            strings.add(str[i]);
        }


        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        mBanner.setImageLoader(new GrouponGoodsDetailActivity.MyLoader());
        //设置图片网址或地址的集合
        mBanner.setImages(strings);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        mBanner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        mBanner.setBannerTitles(strings);
        //设置轮播间隔时间
        mBanner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        mBanner.isAutoPlay(false);

        //设置指示器的位置，小点点，左中右。
        mBanner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();

    }

    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    public void getDataSuccess(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean) {
        this.goodsDetailBean = goodsDetailBean;
        tv_groupon_price.setText(goodsDetailBean.getPrice()+"");

        tv_groupon_old_price.setText("¥" + goodsDetailBean.getMarketPrice());

        tv_goods_name.setText(goodsDetailBean.getGoodsName());

        tv_grounon_info.setText(goodsDetailBean.getGoodsSmallName());

        tv_groupon_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        wv_goods_detail.getSettings().setJavaScriptEnabled(true);

        wv_goods_detail.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString) {
                return false;
            }
        });
//        wv_goods_detail.loadUrl("http://manage.boos999.com/goods/mobiledetail/1345");
        wv_goods_detail.loadUrl(goodsDetailBean.getMobileDesc());

        startBanner(goodsDetailBean);

        if(WlmUtil.isCountdown(goodsDetailBean.getBeginDate(),goodsDetailBean.getEndDate(),tv_rush_time) == 0){
            tv_right_now_groupon.setVisibility(View.GONE);
            tv_distance_ends.setText("距开始");
        }else if (WlmUtil.isCountdown(goodsDetailBean.getBeginDate(),goodsDetailBean.getEndDate(),tv_rush_time) == 1){
            tv_distance_ends.setText("距结束");
        }else {
            tv_right_now_groupon.setVisibility(View.GONE);
        }
    }

    @Override
    public void getDataFail(String msg) {
        toast(msg);
    }

    @Override
    public void getCommendGoodsSuccess(ArrayList<GoodsListBean> goodsListBeans) {
        this.goodsListBeans = goodsListBeans;
        TbHotGoodsAdapter tbHotGoodsAdapter = new TbHotGoodsAdapter(this,goodsListBeans,getLayoutInflater());
        recyclerView.setAdapter(tbHotGoodsAdapter);
        tbHotGoodsAdapter.setItemClickListener(this);
    }

    @Override
    public void getCommendGoodsFail(String msg) {

    }

    @Override
    public void onItemClick(int position) {
        if (!ButtonUtils.isFastDoubleClick()) {
            Bundle bundle = new Bundle();
            bundle.putString(WlmUtil.GOODSID, goodsListBeans.get(position).getGoodsId());
            bundle.putString(WlmUtil.TYPE,goodsListBeans.get(position).getGoodsType());
            UiHelper.launcherBundle(this, SelfGoodsDetailActivity.class, bundle);

            if (ActivityUtil.activityList.size() > 2) {
                ActivityUtil.removeOldActivity();
            }
        }
    }


    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            Picasso.with(context).load(ProApplication.BANNERIMG+(String) path).error(R.mipmap.ic_adapter_error).into(imageView);
//            imageView.setImageResource(R.mipmap.ic_goods_pic);90

        }
    }

    @OnClick({R.id.ll_back,R.id.tv_right_now_groupon})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;

            case R.id.tv_right_now_groupon:

                Bundle bundle = new Bundle();
                bundle.putString(WlmUtil.GOODSID,goodsDetailBean.getGoodsId());
                bundle.putInt(WlmUtil.GOODSNUM,1);
                bundle.putString(WlmUtil.TYPE,WlmUtil.GROUPONGOODS);
                bundle.putString(WlmUtil.ATTRID,"");
                UiHelper.launcherBundle(this, GrouponOrderActivity.class,bundle);

                break;
        }
    }
}