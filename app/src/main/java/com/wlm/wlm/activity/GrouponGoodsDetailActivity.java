package com.wlm.wlm.activity;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.ui.CountdownView;
import com.wlm.wlm.ui.MyTextView;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;
import com.xw.banner.loader.ImageLoader;

import java.util.ArrayList;

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
    @BindView(R.id.tv_rush_time)
    CountdownView tv_rush_time;


    @Override
    public int getLayoutId() {
        return R.layout.activity_groupon_goods_details;
    }

    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(this,false);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("adssasd");
        strings.add("afsdf");

        tv_groupon_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);


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

        tv_rush_time.start(1572310800);
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

                UiHelper.launcher(this,GrouponDetailActivity.class);

                break;
        }
    }
}