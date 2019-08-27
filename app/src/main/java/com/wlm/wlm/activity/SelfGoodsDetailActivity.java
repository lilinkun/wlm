package com.wlm.wlm.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.RecordAdapter;
import com.wlm.wlm.adapter.SelfGoodsAdapter;
import com.wlm.wlm.base.BaseGoodsActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.SelfGoodsDetailContract;
import com.wlm.wlm.db.DBManager;
import com.wlm.wlm.entity.BrowseRecordBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailBean;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.interf.ISlideCallback;
import com.wlm.wlm.presenter.SelfGoodsDetailPresenter;
import com.wlm.wlm.slide.SlideDetailsLayout;
import com.wlm.wlm.ui.CommendRecyclerView;
import com.wlm.wlm.ui.FullyGridLayoutManager;
import com.wlm.wlm.ui.GridSpacingItemDecoration;
import com.wlm.wlm.ui.SelfGoodsPopLayout;
import com.wlm.wlm.ui.TranslucentScrollView;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.LzyydUtil;
import com.wlm.wlm.util.UiHelper;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;
import com.xw.banner.loader.ImageLoader;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by LG on 2018/12/8.
 */

public class SelfGoodsDetailActivity extends BaseGoodsActivity implements SelfGoodsDetailContract, OnBannerListener, SelfGoodsPopLayout.OnAddCart, ISlideCallback, RecordAdapter.OnItemClickListener, SelfGoodsAdapter.OnItemClickListener, View.OnScrollChangeListener {

    @BindView(R.id.tv_goods_name)
    TextView mGoodsNameTv;
    @BindView(R.id.tv_goods_price)
    TextView mGoodsPrice;
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
    @BindView(R.id.tv_shop_name)
    TextView mShopName;
    @BindView(R.id.rv_recommend)
    CommendRecyclerView recyclerView;
    @BindView(R.id.wv_goods_detail)
    WebView webView;
    @BindView(R.id.slidedetails)
    SlideDetailsLayout mSlideDetailsLayout;
    @BindView(R.id.tv_freight)
    TextView tv_freight;
    @BindView(R.id.rl_goods_format)
    RelativeLayout goodsLayout;
    @BindView(R.id.tsv_home)
    TranslucentScrollView translucentScrollView;
    @BindView(R.id.rl_add_cart)
    RelativeLayout rl_add_cart;
    @BindView(R.id.rl_rush)
    RelativeLayout rl_rush;
    @BindView(R.id.tv_rush_time)
    CountdownView tv_rush_time;
    @BindView(R.id.rl_rush_org)
    RelativeLayout rl_rush_org;
    @BindView(R.id.tv_org_price)
    TextView tv_org_price;
    @BindView(R.id.tv_distance_ends)
    TextView tv_distance_ends;
    @BindView(R.id.tv_integral)
    TextView tv_integral;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @BindView(R.id.tv_integral_pv)
    TextView tv_integral_pv;


    SelfGoodsDetailPresenter selfGoodsDetailPresenter = new SelfGoodsDetailPresenter();
    private GoodsDetailBean<ArrayList> goodsDetailBean;
    private SelfGoodsBean selfGoodsBean;
    private boolean isCollect = false;
    private String collectId = "";
    private PopupWindow popupWindow;
    private SelfGoodsPopLayout selfGoodsPopLayout;
    private ArrayList<SelfGoodsBean> selfGoodsBeans;
    private GoodsChooseBean goodsChooseBean;
    private String num = "";
    private int self_address_result = 0x2213;
    private String type = "";
    private BrowseRecordBean collectBean;
    private int mAlpha = 0;
    private SelfGoodsAdapter selfGoodsAdapter = null;
    private String goodsid;
    private String sysTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_self_goodsdetails;
    }


    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(this);

        selfGoodsDetailPresenter.attachView(this);
        selfGoodsDetailPresenter.onCreate(this);
        ActivityUtil.addActivity(this);
        goodsid = getIntent().getBundleExtra(LzyydUtil.TYPEID).getString("goodsid");
        type = getIntent().getBundleExtra(LzyydUtil.TYPEID).getString("type");
        if (goodsid == null || goodsid.isEmpty()) {
            selfGoodsDetailPresenter.getGoodsDetail("1139", ProApplication.SESSIONID(this));
        } else {
            selfGoodsDetailPresenter.getGoodsDetail(goodsid, ProApplication.SESSIONID(this));
        }
        selfGoodsDetailPresenter.isCollect(goodsid, ProApplication.SESSIONID(this));

        selfGoodsDetailPresenter.randomGoods(goodsid, ProApplication.SESSIONID(this));

        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(this, 3);
        fullyGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        int spanCount = 2; // 2 columns
        int spacing = 0; // 50px

        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        recyclerView.setLayoutManager(fullyGridLayoutManager);

        translucentScrollView.setOnScrollChangeListener(this);

    }


    @OnClick({R.id.rl_goods_format, R.id.ll_back, R.id.ll_collect, R.id.ll_shop, R.id.ll_shop_car, R.id.rl_add_cart, R.id.rl_immediate_purchase})
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
                        if (!collectId.isEmpty()) {
                            selfGoodsDetailPresenter.deleteCollect(collectId, ProApplication.SESSIONID(this));
                        }
                    } else {
                        selfGoodsDetailPresenter.onCollect(selfGoodsBean.getGoods_id(), ProApplication.SESSIONID(this));

                    }

                    break;

                case R.id.ll_shop:
                    Bundle bundle = new Bundle();
                    bundle.putString("storeid", goodsDetailBean.getGoodsItem().getStore_id());
                    UiHelper.launcherForResultBundle(this, StoreActivity.class, 0x4323, bundle);

                    break;

                case R.id.ll_shop_car:

                    UiHelper.launcher(this, ShoppingCarActivity.class);

                    break;

                case R.id.rl_add_cart:

                    if (goodsDetailBean != null && Integer.valueOf(goodsDetailBean.getGoodsItem().getGoods_number()) == 0){
                        toast("库存不足，无法加入购物车");
                    }else {
                        if (Integer.valueOf(goodsDetailBean.getGoodsItem().getQty()) == 0) {
                            rl_add_cart.setClickable(false);
                            selfGoodsDetailPresenter.addCartAdd(goodsDetailBean.getGoodsItem().getGoods_id(), "", "1", ProApplication.SESSIONID(this));

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
                    if (goodsDetailBean != null && Integer.valueOf(goodsDetailBean.getGoodsItem().getGoods_number()) == 0){
                        toast("库存不足，无法下单");
                    }else {
                        if (popupWindow != null) {
                            iv_bg.setVisibility(View.VISIBLE);
                            selfGoodsPopLayout.setPosition(2);
                            popupWindow.showAtLocation(relativeLayout, Gravity.CENTER | Gravity.CENTER, 0, 0);
                        }
                    }
                    break;
            }
        }
    }


    @Override
    public void showPromptMessage(int resId) {

    }

    @Override
    public void showPromptMessage(String message) {

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
                toast(data.getBundleExtra(LzyydUtil.TYPEID).getString("goodsid"));
            }
        }
    }

    @Override
    public void getDataSuccess(GoodsDetailBean<ArrayList> goodsDetailBean) {
        this.goodsDetailBean = goodsDetailBean;
        getpopup();
        selfGoodsBean = goodsDetailBean.getGoodsItem();
        mGoodsNameTv.setText(selfGoodsBean.getGoods_name());
        mGoodsPrice.setText(selfGoodsBean.getShop_price() + "");
        tv_integral_pv.setText("pv值 " + selfGoodsBean.getReturn_integral());

        tv_integral.setText("可用积分抵扣" + selfGoodsBean.getGive_integral());

        BigDecimal b = new BigDecimal(selfGoodsBean.getMarket_price());
        double marketPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        tv_org_price.setText("¥" + marketPrice);

        List<String> list_path = new ArrayList<>();
        if (goodsDetailBean.getGoodsItem().getQty() == 0) {
            goodsLayout.setVisibility(View.GONE);
        } else {
            goodsLayout.setVisibility(View.VISIBLE);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString) {
                return false;
            }
        });
        webView.loadUrl(goodsDetailBean.getGoodsItem().getMobile_desc());

        mShopName.setText(goodsDetailBean.getGoodsItem().getShop_name());

        for (int i = 0; i < selfGoodsBean.getGoods_imglist().size(); i++) {
            list_path.add(ProApplication.HEADIMG + selfGoodsBean.getGoods_imglist().get(i));
        }

        if ((selfGoodsBean.getGoods_attr() & 1) == 1) {
            tv_freight.setText("免邮");
        } else {
            tv_freight.setText("不免邮");
        }

        if ((selfGoodsBean.getGoods_attr() & 16) == 16) {
            rl_rush.setVisibility(View.VISIBLE);
//           tv_rush_time.setText(selfGoodsBean.getBegin_date());
            sysTime = selfGoodsBean.getNow_date();
//            tv_rush_time.setColor(getResources().getColor(R.color.white),1);


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            try {
                Date date = simpleDateFormat.parse(sysTime);
                long nowTime = date.getTime();
                Date date2 = simpleDateFormat.parse(selfGoodsBean.getBegin_date());
                long startTime = date2.getTime();
                Date date1 = simpleDateFormat.parse(selfGoodsBean.getEnd_date());
                long endTime = date1.getTime();
                if (startTime > nowTime ){
                    tv_distance_ends.setText("距开始仅剩");
                    tv_rush_time.start(startTime-nowTime);
//                    tv_rush_time.setCountdownTime((int)((startTime-nowTime)/1000),goodsid+"");
                    ll_bottom.setVisibility(View.GONE);
                    goodsLayout.setClickable(false);
                }else {
                    tv_distance_ends.setText("距结束仅剩");
                    tv_rush_time.start(endTime-nowTime);
//                    tv_rush_time.setCountdownTime((int)((endTime-nowTime)/1000),goodsid+"");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

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


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        collectBean = new BrowseRecordBean();
        collectBean.setGoods_img(goodsDetailBean.getGoodsItem().getGoods_img());
        collectBean.setAdd_time(goodsDetailBean.getGoodsItem().getAdd_time());
        collectBean.setGoods_id(Long.valueOf(goodsDetailBean.getGoodsItem().getGoods_id()));
        collectBean.setGoods_name(goodsDetailBean.getGoodsItem().getGoods_name());
        collectBean.setShop_price(goodsDetailBean.getGoodsItem().getShop_price());
        collectBean.setStore_name(goodsDetailBean.getGoodsItem().getShop_name());
        collectBean.setUser_id(MainFragmentActivity.username);
        collectBean.setBrowse_date(time);
        List<BrowseRecordBean> beanList =  DBManager.getInstance(this).queryBrowseBean(MainFragmentActivity.username);
        long id = 0;
        if (beanList.size() > 0){
            id =  beanList.get(beanList.size()-1).getId() + (long)1;
        }
        collectBean.setId(id);

        List<BrowseRecordBean> browseRecordBeans = DBManager.getInstance(this).queryBrowseBean(collectBean);
        if (browseRecordBeans.size() == 0) {
            DBManager.getInstance(this).insertBrowseBean(collectBean);
        } else {
            DBManager.getInstance(this).deleteOneBrowseBean(browseRecordBeans.get(0));
            DBManager.getInstance(this).insertBrowseBean(collectBean);
        }

//        if(DBManager.getInstance(this).queryGoodRecordBean(recordBean.getGoods_id()).size() == 0) {
//            DBManager.getInstance(this).insertRecordBean(recordBean);
//        }else {
//            DBManager.getInstance(this).deleteOneRecordBean(recordBean);
//            DBManager.getInstance(this).insertRecordBean(recordBean);
//        }

    }

    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    public void addShopCart(SelfGoodsBean selfGoodsBean, GoodsChooseBean goodsChooseBean, int num) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        String attr_id = String.valueOf(goodsChooseBean.getAttr_id());
        selfGoodsDetailPresenter.addCartAdd(selfGoodsBean.getGoods_id(), attr_id + "", num + "", ProApplication.SESSIONID(this));
    }

    @Override
    public void delete() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void mRightNowBuy(SelfGoodsBean selfGoodsBean, GoodsChooseBean goodsChooseBean, int num) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        String attr_id = "";
        if (goodsChooseBean != null) {
            attr_id = String.valueOf(goodsChooseBean.getAttr_id());
        }
        this.selfGoodsBean = selfGoodsBean;
        this.goodsChooseBean = goodsChooseBean;
        this.num = num + "";

        selfGoodsDetailPresenter.isUserAddress(ProApplication.SESSIONID(this));
//        selfGoodsDetailPresenter.rightNowBuy(selfGoodsBean.getGoods_id(),attr_id,num+"",ProApplication.SESSIONID(this));
    }


    @Override
    public void openDetails(boolean smooth) {
        mSlideDetailsLayout.smoothOpen(smooth);
    }

    @Override
    public void closeDetails(boolean smooth) {
        mSlideDetailsLayout.smoothClose(smooth);
    }

    @Override
    public void onItemClick(int position) {
//        Intent intent = new Intent(SelfGoodsDetailActivity.this,SelfGoodsDetailActivity.class);
//        intent.putExtra("data",mPage++);
//        intent.getBundleExtra()
//        startActivity(intent);

        if (!ButtonUtils.isFastDoubleClick()) {
            Bundle bundle = new Bundle();
            bundle.putString("goodsid", selfGoodsBeans.get(position).getGoods_id());
            UiHelper.launcherBundle(this, SelfGoodsDetailActivity.class, bundle);

            if (ActivityUtil.activityList.size() > 1) {
                ActivityUtil.removeOldActivity();
            }
        }
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            Picasso.with(context).load((String) path).into(imageView);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void addCollectSuccess(CollectBean collectBean) {
        toast("收藏成功");
        setCollectStatus(true);
        isCollect = true;
        collectId = collectBean.getCollect_id();
    }

    @Override
    public void addCollectFail(String msg) {
        isCollect = false;
    }

    @Override
    public void isGoodsCollectSuccess(String s) {
        if (!s.equals("-1")) {
            setCollectStatus(true);
            this.collectId = s;
        } else {
            setCollectStatus(false);
        }
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
    public void getCommendGoodsSuccess(ArrayList<SelfGoodsBean> selfGoodsBean) {
        this.selfGoodsBeans = selfGoodsBean;
        selfGoodsAdapter = new SelfGoodsAdapter(this, selfGoodsBean, 3);
        recyclerView.setAdapter(selfGoodsAdapter);
        selfGoodsAdapter.setItemClickListener(this);
    }

    @Override
    public void getCommendGoodsFail(String msg) {
        toast(msg);
    }

    @Override
    public void getRightNowBuySuccess(BuyBean buyBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", buyBean);
        UiHelper.launcherBundle(this, OrderActivity.class, bundle);
    }

    @Override
    public void getRightNowBuyFail(String msg) {
        toast(msg);
    }

    @Override
    public void isAddressSuccess(String msg) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("goodsChooseBean", goodsChooseBean);
        bundle.putSerializable("selfGoodsBean", selfGoodsBean);
        bundle.putInt("type", 0);
        bundle.putString("num", num + "");
        UiHelper.launcherBundle(this, OrderActivity.class, bundle);

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
            if (DBManager.getInstance(this).queryGoodCollectBean(collectBean.getGoods_id()).size() == 0) {
//                DBManager.getInstance(this).insertCollectBean(collectBean);
            }
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

        selfGoodsPopLayout.setData(goodsDetailBean, popupWindow);
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
