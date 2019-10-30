package com.wlm.wlm.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.SelfGoodsAdapter;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.base.BaseGoodsActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.SelfGoodsDetailContract;
import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.BrowseRecordBean;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.interf.OnScrollChangedListener;
import com.wlm.wlm.presenter.SelfGoodsDetailPresenter;
import com.wlm.wlm.ui.CommendRecyclerView;
import com.wlm.wlm.ui.CountdownView;
import com.wlm.wlm.ui.FullyGridLayoutManager;
import com.wlm.wlm.ui.SelfGoodsPopLayout;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.ui.TranslucentScrollView;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE;

/**
 * Created by LG on 2018/12/8.
 */

public class SelfGoodsDetailActivity extends BaseGoodsActivity implements SelfGoodsDetailContract, OnBannerListener, SelfGoodsPopLayout.OnAddCart, TbHotGoodsAdapter.OnItemClickListener, OnScrollChangedListener {

    @BindView(R.id.tv_goods_name)
    TextView mGoodsNameTv;
    @BindView(R.id.tv_groupon_price)
    TextView tv_groupon_price;
    @BindView(R.id.img_good_pic)
    Banner mBanner;
    @BindView(R.id.rl_goods)
    RelativeLayout relativeLayout;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.iv_collect)
    ImageView mCollectIv;
    @BindView(R.id.tv_collect)
    TextView mCollectTv;
    @BindView(R.id.tx_tikey)
    TextView tx_tikey;
    @BindView(R.id.rv_recommend)
    CommendRecyclerView recyclerView;
    @BindView(R.id.wv_goods_detail)
    WebView webView;
    @BindView(R.id.rl_goods_format)
    RelativeLayout goodsLayout;
    @BindView(R.id.tsv_home)
    TranslucentScrollView translucentScrollView;
    @BindView(R.id.rl_add_cart)
    RelativeLayout rl_add_cart;
    @BindView(R.id.rl_immediate_purchase)
    RelativeLayout rl_immediate_purchase;
    @BindView(R.id.rl_rush)
    RelativeLayout rl_rush;
    @BindView(R.id.ll_groupon_play)
    LinearLayout ll_groupon_play;
//    @BindView(R.id.rl_rush_org)
//    RelativeLayout rl_rush_org;
    @BindView(R.id.tv_groupon_old_price)
    TextView tv_groupon_old_price;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @BindView(R.id.titlebar)
    LinearLayout toolbar;
    @BindView(R.id.iv_turn_top)
    ImageView iv_turn_top;
    @BindView(R.id.iv_more)
    ImageView iv_more;
    @BindView(R.id.tv_no_delivery)
    TextView tv_no_delivery;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.ll_layout_integral_price)
    LinearLayout ll_layout_integral_price;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_details_goods_add_integral)
    TextView tv_details_goods_add_integral;
    @BindView(R.id.tv_details_goods_add_price)
    TextView tv_details_goods_add_price;
    @BindView(R.id.tv_macket_price)
    TextView tv_macket_price;
    @BindView(R.id.ll_service)
    LinearLayout ll_service;
    @BindView(R.id.ll_exchange)
    LinearLayout ll_exchange;
    @BindView(R.id.ll_shop_car)
    LinearLayout ll_shop_car;
    @BindView(R.id.ll_collect)
    LinearLayout ll_collect;
    @BindView(R.id.ll_groupon_time)
    LinearLayout ll_groupon_time;
    @BindView(R.id.ll_sale)
    LinearLayout ll_sale;
    @BindView(R.id.ll_groupon_info)
    LinearLayout ll_groupon_info;
    @BindView(R.id.ll_goods_layout)
    LinearLayout ll_goods_layout;
    @BindView(R.id.tv_rush_time)
    CountdownView tv_rush_time;
    @BindView(R.id.tv_cf_rush_time)
    CountdownView tv_cf_rush_time;
    @BindView(R.id.tv_distance_ends)
    TextView tv_distance_ends;
    @BindView(R.id.tv_cf_distance_ends)
    TextView tv_cf_distance_ends;
    @BindView(R.id.tv_grounon_info)
    TextView tv_grounon_info;
    @BindView(R.id.tv_rule)
    TextView tv_rule;
    @BindView(R.id.tv_crowd_price)
    TextView tv_crowd_price;
    @BindView(R.id.tv_support_count)
    TextView tv_support_count;
    @BindView(R.id.ll_crowd_funding)
    LinearLayout ll_crowd_funding;
    @BindView(R.id.ll_cf_time)
    LinearLayout ll_cf_time;


    SelfGoodsDetailPresenter selfGoodsDetailPresenter = new SelfGoodsDetailPresenter();
    private GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean;
    private boolean isCollect = false;
    private String collectId = "";
    private PopupWindow popupWindow;
    private SelfGoodsPopLayout selfGoodsPopLayout;
    private ArrayList<GoodsListBean> goodsListBeans;
    private GoodsChooseBean goodsChooseBean;
    private String num = "";
    private int self_address_result = 0x2213;
    private String type = "";
    private BrowseRecordBean collectBean;
    private int mAlpha = 0;
    private SelfGoodsAdapter selfGoodsAdapter = null;
    private String goodsid;
    private String sysTime;
    private boolean isOpen = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_self_goodsdetails;
    }


    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(this,false);

        toolbar.setAlpha(0);

        selfGoodsDetailPresenter.onCreate(this,this);
        ActivityUtil.addActivity(this);
        ActivityUtil.addHomeActivity(this);
        goodsid = getIntent().getBundleExtra(WlmUtil.TYPEID).getString(WlmUtil.GOODSID);
        /*if (getIntent().getBundleExtra(WlmUtil.TYPEID).getString(WlmUtil.TYPE) != null) {
            type = getIntent().getBundleExtra(WlmUtil.TYPEID).getString(WlmUtil.TYPE);
        }*/
        if (goodsid != null && !goodsid.isEmpty()) {
            selfGoodsDetailPresenter.getGoodsDetail(goodsid, ProApplication.SESSIONID(this));
//            selfGoodsDetailPresenter.getGoodsTest(goodsid, ProApplication.SESSIONID(this));
        }

        selfGoodsDetailPresenter.isCollect(goodsid, ProApplication.SESSIONID(this));

        selfGoodsDetailPresenter.randomGoods("2",goodsid);

        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(this, 2,GridLayoutManager.VERTICAL,false);
        fullyGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        int spanCount = 5; // 2 columns
        int spacing = 20; // 50px

        boolean includeEdge = false;
        recyclerView.addItemDecoration(new SpaceItemDecoration(spanCount, spacing,0));
        recyclerView.setLayoutManager(fullyGridLayoutManager);

        translucentScrollView.init(this);

    }


    @OnClick({R.id.rl_goods_format, R.id.ll_back, R.id.ll_collect, R.id.ll_shop_car, R.id.rl_add_cart, R.id.rl_immediate_purchase,R.id.iv_turn_top,R.id.rl_no_delivery,R.id.ll_title_back})
    public void onClick(View view) {
        if (!ButtonUtils.isFastDoubleClick(view.getId())) {
            switch (view.getId()) {
                case R.id.rl_goods_format:
                    if (popupWindow != null) {
                        iv_bg.setVisibility(View.VISIBLE);
                        selfGoodsPopLayout.setPosition(3);
                        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER | Gravity.CENTER, 0, 0);
                    }
                    break;

                case R.id.ll_back:
                    setResult(RESULT_OK);
                    finish();

                    break;

                case R.id.ll_collect:

                    if (isCollect) {
                        selfGoodsDetailPresenter.deleteCollect(goodsDetailBean.getGoodsId(),"1", ProApplication.SESSIONID(this));
                    } else {
                        selfGoodsDetailPresenter.onCollect(goodsDetailBean.getGoodsId(),"1", ProApplication.SESSIONID(this));

                    }

                    break;

                /*case R.id.ll_shop:
                    Bundle bundle = new Bundle();
                    bundle.putString("storeid", goodsDetailBean.getGoodsItem().getStore_id());
                    UiHelper.launcherForResultBundle(this, StoreActivity.class, 0x4323, bundle);

                    break;*/

                case R.id.ll_shop_car:

                    UiHelper.launcher(this, ShoppingCarActivity.class);

                    break;

                case R.id.rl_add_cart:

                    if (goodsDetailBean != null && Integer.valueOf(goodsDetailBean.getGoodsNumber()) == 0){
                        toast("库存不足，无法加入购物车");
                    }else {
                        if (goodsDetailBean.getQty() == 0) {
                            rl_add_cart.setClickable(false);
                            selfGoodsDetailPresenter.addCartAdd(goodsDetailBean.getGoodsId(), "0", "1", ProApplication.SESSIONID(this));

                        } else {
                            if (popupWindow != null) {
                                iv_bg.setVisibility(View.VISIBLE);
                                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER | Gravity.CENTER, 0, 0);
                                selfGoodsPopLayout.setPosition(1);
                            }
                        }
                    }

                    break;

                case R.id.rl_immediate_purchase:
                    if (goodsDetailBean != null && Integer.valueOf(goodsDetailBean.getGoodsNumber()) == 0){
                        toast("库存不足，无法下单");
                    }else {
                        if (type.equals(WlmUtil.VIP)){
                            if (goodsDetailBean != null) {
                                mRightNowBuy(goodsDetailBean, null, 1);
                            }
                        }else if (type.equals(WlmUtil.GROUPONGOODS)){
                            Bundle bundle = new Bundle();
                            bundle.putString(WlmUtil.GOODSID,goodsDetailBean.getGoodsId());
                            bundle.putInt(WlmUtil.GOODSNUM,1);
                            bundle.putString(WlmUtil.TYPE,WlmUtil.GROUPONGOODS);
                            bundle.putString(WlmUtil.ATTRID,"");
                            UiHelper.launcherBundle(this, GrouponOrderActivity.class,bundle);
                        }else {
                            if (popupWindow != null) {
                                iv_bg.setVisibility(View.VISIBLE);
                                selfGoodsPopLayout.setPosition(2);
                                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER | Gravity.CENTER, 0, 0);
                            }
                        }
                    }
                    break;

                case R.id.iv_turn_top:

                    translucentScrollView.scrollTo(0,0);

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
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
            finish();
            return true;
        }


        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x4323) {
                toast(data.getBundleExtra(WlmUtil.TYPEID).getString("goodsid"));
            }
        }
    }

    @Override
    public void getDataSuccess(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean) {
        this.goodsDetailBean = goodsDetailBean;
        type = WlmUtil.getType(goodsDetailBean.getGoodsType() + "");

        if (goodsDetailBean.getGoodsType() == WlmUtil.GOODSTYPE_INTERGAL){
            ll_layout_integral_price.setVisibility(View.VISIBLE);
            mGoodsNameTv.setVisibility(View.GONE);
            rl_rush.setVisibility(View.GONE);
            rl_add_cart.setBackgroundColor(getResources().getColor(R.color.integral_add_cart));
            rl_immediate_purchase.setBackgroundColor(getResources().getColor(R.color.integral_bg));
            ll_service.setVisibility(View.GONE);
            ll_exchange.setVisibility(View.VISIBLE);

            tv_title_name.setText(goodsDetailBean.getGoodsName());
            tv_details_goods_add_integral.setText(goodsDetailBean.getIntegral()+"");
            tv_details_goods_add_price.setText(goodsDetailBean.getPrice()+"");
            tv_macket_price.setText("¥"+goodsDetailBean.getMarketPrice()+"");
            tv_macket_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else if (goodsDetailBean.getGoodsType() == WlmUtil.GOODSTYPE_VIP){
            rl_add_cart.setVisibility(View.GONE);
            ll_shop_car.setVisibility(View.GONE);
            ll_collect.setVisibility(View.GONE);
        }else if (goodsDetailBean.getGoodsType() == WlmUtil.GOODSTYPE_GROUPON){
            ll_groupon_time.setVisibility(View.VISIBLE);
            ll_groupon_info.setVisibility(View.VISIBLE);
            ll_groupon_play.setVisibility(View.VISIBLE);
            tv_grounon_info.setText(goodsDetailBean.getGoodsSmallName());
            ll_sale.setVisibility(View.GONE);
            rl_add_cart.setVisibility(View.GONE);
            ll_shop_car.setVisibility(View.GONE);
            ll_collect.setVisibility(View.GONE);
            ll_goods_layout.setVisibility(View.GONE);
            tx_tikey.setText(R.string.groupon_now);
            if(WlmUtil.isCountdown(goodsDetailBean.getBeginDate(),goodsDetailBean.getEndDate(),tv_rush_time) == 0){
                rl_immediate_purchase.setVisibility(View.GONE);
                tv_distance_ends.setText("距开始");
            }else if (WlmUtil.isCountdown(goodsDetailBean.getBeginDate(),goodsDetailBean.getEndDate(),tv_rush_time) == 1){
                tv_distance_ends.setText("距结束");
            }else {
                rl_immediate_purchase.setVisibility(View.GONE);
            }
        }else if (goodsDetailBean.getGoodsType() == WlmUtil.GOODSTYPE_CROWDFUNDING){

            rl_rush.setVisibility(View.GONE);
            ll_crowd_funding.setVisibility(View.VISIBLE);
            ll_cf_time.setVisibility(View.VISIBLE);
            ll_groupon_play.setVisibility(View.VISIBLE);
            rl_add_cart.setVisibility(View.GONE);
            ll_shop_car.setVisibility(View.GONE);
            ll_collect.setVisibility(View.GONE);
            ll_goods_layout.setVisibility(View.GONE);

            rl_immediate_purchase.setBackgroundColor(getResources().getColor(R.color.red_price));

            tv_rule.setText(goodsDetailBean.getGoodsSmallName());

            tx_tikey.setText("支持￥"+ goodsDetailBean.getPrice());
            tv_crowd_price.setText(goodsDetailBean.getPrice()*goodsDetailBean.getUseNumber() + "");
            tv_support_count.setText(goodsDetailBean.getUseNumber() + "");
            if(WlmUtil.isCountdown(goodsDetailBean.getBeginDate(),goodsDetailBean.getEndDate(),tv_cf_rush_time) == 0){
                rl_immediate_purchase.setVisibility(View.GONE);
                tv_cf_distance_ends.setText("距开始");
            }else if (WlmUtil.isCountdown(goodsDetailBean.getBeginDate(),goodsDetailBean.getEndDate(),tv_cf_rush_time) == 1){
                tv_cf_distance_ends.setText("距结束");
            }else {
                rl_immediate_purchase.setVisibility(View.GONE);
            }
        }


        getpopup();

        mGoodsNameTv.setText(goodsDetailBean.getGoodsName());
        tv_groupon_price.setText(goodsDetailBean.getPrice() + "");
        tv_number.setText(goodsDetailBean.getUseNumber()+"");

        BigDecimal b = new BigDecimal(goodsDetailBean.getMarketPrice());
        double marketPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        tv_groupon_old_price.setText("¥" + marketPrice);
        tv_groupon_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        List<String> list_path = new ArrayList<>();
        if (goodsDetailBean.getQty() == 0) {
            goodsLayout.setVisibility(View.GONE);
        } else {
            goodsLayout.setVisibility(View.VISIBLE);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 让网页的内容呈单列显示
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH); // 加速显示图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString) {
                return false;
            }
        });
        String mobileDesc = goodsDetailBean.getMobileDesc();

//        webView.loadUrl("http://192.168.0.106:8080/liguo/liguo.html");
//        webView.loadUrl("http://manage.boos999.com/goods/mobiledetail/1345");
        webView.loadUrl(mobileDesc);

        String[] strings ;
        if (goodsDetailBean.getGoodsImgList().contains(",")) {
            strings = goodsDetailBean.getGoodsImgList().split(",");
        }else {
            strings = new String[1];
            strings[0] = goodsDetailBean.getGoodsImgList();
        }

        for (int i = 0; i < strings.length; i++) {
            list_path.add(strings[i]);
        }

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        mBanner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        mBanner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        mBanner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        mBanner.setBannerTitles(list_path);
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
    public void addShopCart(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean, GoodsChooseBean goodsChooseBean, int num) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        String attr_id = String.valueOf(goodsChooseBean.getAttr_id());
        selfGoodsDetailPresenter.addCartAdd(goodsDetailBean.getGoodsId(), attr_id + "", num + "", ProApplication.SESSIONID(this));
    }

    @Override
    public void delete() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void mRightNowBuy(GoodsDetailInfoBean selfGoodsBean, GoodsChooseBean goodsChooseBean, int num) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        String attr_id = "";
        if (goodsChooseBean != null) {
            attr_id = String.valueOf(goodsChooseBean.getAttr_id());
        }
        this.goodsChooseBean = goodsChooseBean;
        this.num = num + "";

        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.GOODSID, selfGoodsBean.getGoodsId());
        bundle.putInt(WlmUtil.GOODSNUM,Integer.valueOf(num));
        bundle.putString(WlmUtil.ATTRID,attr_id);
        bundle.putString(WlmUtil.TYPE,type);
        UiHelper.launcherBundle(this, GrouponOrderActivity.class, bundle);

    }


    @Override
    public void onItemClick(int position) {
//        Intent intent = new Intent(SelfGoodsDetailActivity.this,SelfGoodsDetailActivity.class);
//        intent.putExtra("data",mPage++);
//        intent.getBundleExtra()
//        startActivity(intent);

        if (!ButtonUtils.isFastDoubleClick()) {
            Bundle bundle = new Bundle();
            bundle.putString(WlmUtil.GOODSID, goodsListBeans.get(position).getGoodsId());
            bundle.putString(WlmUtil.TYPE,WlmUtil.getType(goodsListBeans.get(position).getGoodsType()));
            UiHelper.launcherBundle(this, SelfGoodsDetailActivity.class, bundle);

            if (ActivityUtil.activityList.size() > 2) {
                ActivityUtil.removeOldActivity();
            }
        }
    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        //Y轴偏移量
        float scrollY = scrollView.getScrollY();

        //变化率
        float headerBarOffsetY = 250;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);
        toolbar.setAlpha(offset);

        if (webView.getTop() <= (int)scrollY){
            if (iv_turn_top != null && !iv_turn_top.isShown()) {
                iv_turn_top.setVisibility(View.VISIBLE);
            }
        }else if (webView.getTop() > (int)scrollY){
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

            Picasso.with(context).load(ProApplication.BANNERIMG+(String) path).error(R.mipmap.ic_adapter_error).into(imageView);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void addCollectSuccess(String collectBean) {
        toast("已收藏");
        setCollectStatus(true);
        isCollect = true;
//        collectId = collectBean.getCollect_id();
    }

    @Override
    public void addCollectFail(String msg) {
        isCollect = false;
    }

    @Override
    public void isGoodsCollectSuccess(String s) {
            isCollect = true;
            setCollectStatus(true);
    }

    @Override
    public void deleteCollectSuccess(String msg) {
        toast("取消宝贝收藏成功");
        setCollectStatus(false);
    }

    @Override
    public void addCartSuccess(String msg) {
        toast("加入购物车成功");
        if (!rl_add_cart.isClickable()) {
            rl_add_cart.setClickable(true);
        }
    }

    @Override
    public void addCartFail(String msg) {
        toast(msg);
        if (!rl_add_cart.isClickable()) {
            rl_add_cart.setClickable(true);
        }
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
        toast(msg);
    }

    @Override
    public void isAddressSuccess(ArrayList<AddressBean> addressBeans) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(WlmUtil.GOODSCHOOSEBEAN, goodsChooseBean);
        bundle.putSerializable(WlmUtil.GOODSDETAILINFOBEAN,goodsDetailBean);
        bundle.putInt("type", 0);
        bundle.putString("num", num + "");
        UiHelper.launcherBundle(this, GrouponOrderActivity.class, bundle);

    }

    @Override
    public void isAddressFail(String msg) {
        new AlertDialog.Builder(this).setMessage("您还没有收货地址，请填写").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                UiHelper.launcherForResult(SelfGoodsDetailActivity.this, ChooseAddressActivity.class, self_address_result);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void setCollectStatus(boolean collectStatus) {
        this.isCollect = collectStatus;
        if (collectStatus) {
            mCollectIv.setImageResource(R.mipmap.ic_collection);
            mCollectTv.setText(R.string.goods_collect);
        } else {
            mCollectIv.setImageResource(R.mipmap.ic_uncollection);
            mCollectTv.setText(R.string.goods_uncollect);
        }
    }

    @Override
    protected void onDestroy() {

        if (collectBean != null) {
//            if (DBManager.getInstance(this).queryGoodCollectBean(collectBean.getGoods_id()).size() == 0) {
//                DBManager.getInstance(this).insertCollectBean(collectBean);
//            }
        }

        super.onDestroy();
        ActivityUtil.removeActivity(this);
    }

    /**
     * 初始化popup
     */
    private void getpopup() {
        selfGoodsPopLayout = new SelfGoodsPopLayout(this);
        popupWindow = new PopupWindow(selfGoodsPopLayout, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);

        selfGoodsPopLayout.setData(goodsDetailBean,type);
        selfGoodsPopLayout.setListener(this);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (iv_bg != null) {
                    iv_bg.setVisibility(View.GONE);
                }
            }
        });
    }

}
