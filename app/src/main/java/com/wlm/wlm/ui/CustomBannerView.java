package com.wlm.wlm.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.activity.SelfGoodsDetailActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.transform.BannerTransform;
import com.wlm.wlm.util.CustomRoundedImageLoader;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;
import com.xw.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;

/**
 * Created by LG on 2019/10/29.
 */
public class CustomBannerView {

    /**
     *
     * @param flashBeans
     * @param banner
     * @param context
     * @param isImageLoader 是否使用自带的imageloader
     */
    public static void startBanner(final ArrayList<FlashBean> flashBeans, Banner banner,final Context context,boolean isImageLoader){
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < flashBeans.size(); i++) {
            strings.add("111111" + i);
        }

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

        if (isImageLoader){
            //设置图片加载器，图片加载器在下方
            banner.setImageLoader(new ImageLoaderInterface() {
                @Override
                public void displayImage(Context context, Object path, View imageView) {
                    Picasso.with(context).load(ProApplication.BANNERIMG + ((FlashBean) path).getFlashPic()).error(R.mipmap.ic_adapter_error).into((ImageView)imageView);
                }

                @Override
                public View createImageView(Context context) {
                    return null;
                }
            });
        }else {
            //设置图片加载器，图片加载器在下方
            banner.setImageLoader(new CustomRoundedImageLoader());
        }
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
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {

                        FlashBean flashBean = flashBeans.get(position);
                        String url = flashBean.getFlashUrl();
                        if (url.contains("GoodsId") && url.contains("GoodsType")) {
                            try {
                                JSONObject jsonObject = JSON.parseObject(url);
                                String goodsid = jsonObject.getString("GoodsId");
                                int GoodsType = jsonObject.getInteger("GoodsType");
                                Bundle bundle = new Bundle();

                                bundle.putString(WlmUtil.GOODSID, goodsid);
                                bundle.putString(WlmUtil.TYPE, WlmUtil.getType(GoodsType + ""));
                                UiHelper.launcherBundle(context, SelfGoodsDetailActivity.class, bundle);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                })
                //必须最后调用的方法，启动轮播图。
                .start();

    }


}
