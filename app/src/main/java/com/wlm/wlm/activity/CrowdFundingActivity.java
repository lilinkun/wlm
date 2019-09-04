package com.wlm.wlm.activity;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.GrouponAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.transform.BannerTransform;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.CustomRoundedImageLoader;
import com.wlm.wlm.util.Eyes;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;

import java.util.ArrayList;

import butterknife.BindView;

public class CrowdFundingActivity extends BaseActivity implements OnBannerListener, IGoodsTypeListener {

    @BindView(R.id.bannerView)
    Banner banner;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;

    GrouponAdapter grouponAdapter = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_crowd_funding;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.bg_crowdfunding));

        startBanner(null);

        ll_top.setListener(this);
    }

    private void startBanner(final ArrayList<FlashBean> flashBeans) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            strings.add("111111" + i);
        }

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

    @Override
    public void getSortType(int sortType) {
        switch (sortType){
            case 1:

                break;

            case 3:

                break;

            case 4://销量下


                break;

            case 5://价格上


                break;

            case 6://价格下


                break;
        }
    }
}
