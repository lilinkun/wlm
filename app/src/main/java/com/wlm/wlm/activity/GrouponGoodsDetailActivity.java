package com.wlm.wlm.activity;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.ui.CountdownView;
import com.wlm.wlm.ui.MyTextView;
import com.wlm.wlm.ui.PriceTextView;
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
public class GrouponGoodsDetailActivity extends BaseActivity implements OnBannerListener {

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

    GoodsListBean goodsListBean = null;


    @Override
    public int getLayoutId() {
        return R.layout.activity_groupon_goods_details;
    }

    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(this,false);

        Bundle bundle = getIntent().getBundleExtra(WlmUtil.TYPEID);

        if (bundle != null && bundle.getSerializable("groupongoods") != null){
            goodsListBean = (GoodsListBean) bundle.getSerializable("groupongoods");
        }


        tv_groupon_price.setText(goodsListBean.getPrice()+"");

        tv_groupon_old_price.setText("￥" + goodsListBean.getMarketPrice());

        tv_goods_name.setText(goodsListBean.getGoodsName());

        tv_grounon_info.setText(goodsListBean.getGoodsTypeName());

        ArrayList<String> strings = new ArrayList<>();
        strings.add("adssasd");
        strings.add("afsdf");

        tv_groupon_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        if(WlmUtil.isCountdown(goodsListBean.getBeginDate(),goodsListBean.getEndDate(),tv_rush_time) == 0){
            tv_right_now_groupon.setVisibility(View.GONE);
            tv_distance_ends.setText("距开始");
        }else if (WlmUtil.isCountdown(goodsListBean.getBeginDate(),goodsListBean.getEndDate(),tv_rush_time) == 1){
            tv_distance_ends.setText("距结束");
        }else {
            tv_right_now_groupon.setVisibility(View.GONE);
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

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

//            Picasso.with(context).load((String) path).into(imageView);
            imageView.setImageResource(R.mipmap.ic_goods_pic);

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
                bundle.putSerializable("groupongoods",goodsListBean);
                UiHelper.launcherBundle(this, GrouponDetailActivity.class,bundle);

                break;
        }
    }
}