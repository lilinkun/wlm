package com.wlm.wlm.activity;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.interf.OnScrollChangedListener;
import com.wlm.wlm.ui.FullyGridLayoutManager;
import com.wlm.wlm.ui.GridSpacingItemDecoration;
import com.wlm.wlm.ui.MyTextView;
import com.wlm.wlm.ui.TranslucentScrollView;
import com.wlm.wlm.util.Eyes;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;
import com.xw.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/8/21.
 */

public class GoodsDetailActivity extends BaseActivity implements OnBannerListener, OnScrollChangedListener {

    @BindView(R.id.banner_good_pic)
    Banner mBanner;
    @BindView(R.id.tv_groupon_old_price)
    MyTextView tv_groupon_old_price;
    @BindView(R.id.tv_no_delivery)
    TextView tv_no_delivery;
    @BindView(R.id.rv_goods_detail)
    RecyclerView rv_goods_detail;
    @BindView(R.id.titlebar)
    LinearLayout toolbar;
    @BindView(R.id.tsv_home)
    TranslucentScrollView tsv_home;
    @BindView(R.id.wv_goods_detail)
    WebView wv_goods_detail;
    @BindView(R.id.iv_turn_top)
    ImageView iv_turn_top;
    @BindView(R.id.iv_more)
    ImageView iv_more;
    @BindView(R.id.rl_goods)
    RelativeLayout rl_goods;

    private boolean isOpen = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_details;
    }

    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(this,false);

        tsv_home.init(this);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("adssasd");
        strings.add("afsdf");

        toolbar.setAlpha(0);

        tv_groupon_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        mBanner.setImageLoader(new MyLoader());
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
        int spanCount = 2; // 2 columns
        int spacing = 20; // 50px
        FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(this,2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        boolean includeEdge = false;
        rv_goods_detail.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        TbHotGoodsAdapter tbHotGoodsAdapter = new TbHotGoodsAdapter(this,null,getLayoutInflater());

        rv_goods_detail.setLayoutManager(layoutManager);
        rv_goods_detail.setAdapter(tbHotGoodsAdapter);

        wv_goods_detail.getSettings().setJavaScriptEnabled(true);
        wv_goods_detail.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString) {
                return false;
            }
        });
        wv_goods_detail.loadUrl("http://manage.boos999.com/goods/mobiledetail/1316");
    }

    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        //Y轴偏移量
        float scrollY = scrollView.getScrollY();

        //变化率
        float headerBarOffsetY = 250;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);
        toolbar.setAlpha(offset);

        if (wv_goods_detail.getTop() <= (int)scrollY){
            if (iv_turn_top != null && !iv_turn_top.isShown()) {
                iv_turn_top.setVisibility(View.VISIBLE);
            }
        }else if (wv_goods_detail.getTop() > (int)scrollY){
            if (iv_turn_top != null && iv_turn_top.isShown()) {
                iv_turn_top.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void loadMore() {

    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

//            Picasso.with(context).load((String) path).into(imageView);
            imageView.setImageResource(R.mipmap.ic_goods_pic);

        }
    }

    @OnClick({R.id.ll_back,R.id.rl_no_delivery,R.id.ll_title_back,R.id.iv_turn_top})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;

            case R.id.rl_no_delivery:

                if (isOpen){
                    isOpen = !isOpen;
                    tv_no_delivery.setMaxLines(1);
                    iv_more.setRotation(0);
                }else {
                    isOpen = !isOpen;
                    tv_no_delivery.setMaxLines(10);
                    iv_more.setRotation(90);
                }


                break;


            case R.id.ll_title_back:

                finish();

                break;

            case R.id.iv_turn_top:

                tsv_home.scrollTo(0,0);

                break;

        }
    }
}
