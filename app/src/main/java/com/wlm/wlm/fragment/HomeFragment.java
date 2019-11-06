package com.wlm.wlm.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wlm.wlm.R;
import com.wlm.wlm.activity.BeautyHealthActivity;
import com.wlm.wlm.activity.CrowdFundingActivity;
import com.wlm.wlm.activity.FlashSaleActivity;
import com.wlm.wlm.activity.GrouponActivity;
import com.wlm.wlm.activity.GrouponGoodsDetailActivity;
import com.wlm.wlm.activity.IntegralStoreActivity;
import com.wlm.wlm.activity.ManufactureStoreActivity;
import com.wlm.wlm.activity.PointActivity;
import com.wlm.wlm.activity.SearchActivity;
import com.wlm.wlm.activity.SelfGoodsDetailActivity;
import com.wlm.wlm.activity.SelfGoodsTypeActivity;
import com.wlm.wlm.activity.VipActivity;
import com.wlm.wlm.activity.WebViewActivity;
import com.wlm.wlm.activity.WlmBuyActivity;
import com.wlm.wlm.adapter.GridHomeAdapter;
import com.wlm.wlm.adapter.HomeFragmentAdapter;
import com.wlm.wlm.adapter.HomeHotAdapter;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.HomeContract;
import com.wlm.wlm.entity.CheckBean;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.HomeBean;
import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.interf.OnScrollChangedListener;
import com.wlm.wlm.presenter.HomePresenter;
import com.wlm.wlm.transform.BannerTransform;
import com.wlm.wlm.ui.CountdownView;
import com.wlm.wlm.ui.CusPtrClassicFrameLayout;
import com.wlm.wlm.ui.CustomBannerView;
import com.wlm.wlm.ui.CustomerPtrHandler;
import com.wlm.wlm.ui.DownloadingDialog;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.ui.TranslucentScrollView;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.DensityUtil;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.UpdateManager;
import com.wlm.wlm.util.WlmUtil;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;
import com.xw.banner.loader.ImageLoaderInterface;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.update.BGAUpgradeUtil;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;

import static android.content.Context.MODE_PRIVATE;
import static in.srain.cube.views.ptr.util.PtrLocalDisplay.dp2px;

/**
 * Created by LG on 2018/11/10.
 */

public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener, OnScrollChangedListener, HomeContract, HomeHotAdapter.OnItemClickListener {

    @BindView(R.id.title_layout_search)
    LinearLayout linearLayout;
    @BindView(R.id.bannerView)
    Banner banner;
    @BindView(R.id.tsv_home)
    TranslucentScrollView translucentScrollView;
    @BindView(R.id.mPtrframe)
    CusPtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.rv_home)
    RecyclerView rv_home;
    @BindView(R.id.rv_home_commodities)
    RecyclerView rv_home_commodities;
    @BindView(R.id.tv_rush_time)
    CountdownView tv_rush_time;
    @BindView(R.id.big_1)
    ImageView big_1;
    @BindView(R.id.iv_vip_icon1)
    ImageView iv_vip_icon1;
    @BindView(R.id.iv_vip_icon2)
    ImageView iv_vip_icon2;

    private PopupWindow popupWindow;
    private int PAGE_INDEX = 1;
    int mAlpha = 0;
    private ArrayList<GoodsListBean> hotHomeBeans;
    private HomeFragmentAdapter homeFragmentAdapter;
    private ArrayList<FlashBean> flashBeans;
    private HomeBean homeBean;

    /*
     * 下载文件权限请求码
     */
    private static final int RC_PERMISSION_DOWNLOAD = 1;
    /**
     * 删除文件权限请求码
     */
    private static final int RC_PERMISSION_DELETE = 2;
    private String mApkUrl = "";

    private DownloadingDialog mDownloadingDialog;
    private String mNewVersion = "2";

    @Override
    public int getlayoutId() {
        return R.layout.fragment_homepage;
    }

    HomePresenter homePresenter = new HomePresenter();

    @Override
    public void initEventAndData() {
//        Eyes.setStatusBarColor(getActivity(),getResources().getColor(R.color.home_bg));
        translucentScrollView.init(this);
        initPtrFrame();

        initRv();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
        ProApplication.HEADIMG = sharedPreferences.getString(WlmUtil.IMG, "");
        ProApplication.BANNERIMG = sharedPreferences.getString(WlmUtil.BANNERIMG,"");
        ProApplication.CUSTOMERIMG = sharedPreferences.getString(WlmUtil.CUSTOMER,"");
        ProApplication.SHAREDIMG = sharedPreferences.getString(WlmUtil.SHAREDIMG,"");

        homePresenter.onCreate(getActivity(),this);
        homePresenter.getUrl(ProApplication.SESSIONID(getActivity()));

        if (ProApplication.HEADIMG != null && !ProApplication.HEADIMG.isEmpty()) {
//            homePresenter.setFlash("1");
//            homePresenter.getGoodsList("1");
            homePresenter.getHomeData(ProApplication.SESSIONID(getActivity()));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        int spanCount = 5; // 2 columns
        int spacing = 20; // 50px
//        api.registerApp("wx3686dfb825618610");

        boolean includeEdge = false;
        rv_home_commodities.addItemDecoration(new SpaceItemDecoration(spanCount, spacing,0));
        rv_home_commodities.setLayoutManager(linearLayoutManager);
        rv_home_commodities.setHasFixedSize(true);
        rv_home_commodities.setNestedScrollingEnabled(false);
//        mBanner.setImageLoader(new PicassoImageLoader());
//        mBanner.setImages();

    }

    private void initPtrFrame() {
        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
        header.setPadding(dp2px(20), dp2px(20), 0, 0);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_ptr_head,null);

        CustomerPtrHandler customerPtrHandler = new CustomerPtrHandler(getActivity());

        mPtrFrame.setHeaderView(customerPtrHandler);
        mPtrFrame.addPtrUIHandler(customerPtrHandler);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                homePresenter.getUrl(ProApplication.SESSIONID(getActivity()));
                homePresenter.setFlash("1");

            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    private void initRv(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);

        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        rv_home.setLayoutManager(gridLayoutManager);

        GridHomeAdapter gridHomeAdapter = new GridHomeAdapter(getActivity());

        gridHomeAdapter.setItemClickListener(new GridHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    UiHelper.launcher(getActivity(), PointActivity.class);
                } else if (position == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString(WlmUtil.TYPE,"4");
                    UiHelper.launcherBundle(getActivity(), WebViewActivity.class,bundle);
                }else if (position == 2) {
                    UiHelper.launcher(getActivity(), GrouponActivity.class);
                }else if(position == 3){
                    UiHelper.launcher(getActivity(), FlashSaleActivity.class);
                }else if(position == 4){
//                    UiHelper.launcher(getActivity(), CrowdFundingActivity.class);
//                }else if (position == 5){
                    UiHelper.launcher(getActivity(), ManufactureStoreActivity.class);
                }else if (position == 5){
                    UiHelper.launcher(getActivity(), IntegralStoreActivity.class);
                }else if (position == 6){
                    UiHelper.launcher(getActivity(), WlmBuyActivity.class);
                }else if (position == 7){
                    UiHelper.launcher(getActivity(), BeautyHealthActivity.class);
//                }else if (position == 9){
                }
            }

        });

        rv_home.setAdapter(gridHomeAdapter);


    }


    @OnClick({ R.id.ll_search,R.id.big_1,R.id.iv_vip_icon1,R.id.iv_vip_icon2})
    public void onClick(View view) {
        if (!ButtonUtils.isFastDoubleClick(view.getId())) {
            switch (view.getId()) {

                case R.id.ll_search:

                    UiHelper.launcher(getActivity(), SearchActivity.class);

                    break;

                case R.id.big_1:

                    UiHelper.launcher(getActivity(), VipActivity.class);

                    break;

                case R.id.iv_vip_icon1:

                    Bundle bundle = new Bundle();
                    bundle.putString("goodsid", hotHomeBeans.get(0).getGoodsId());
                    UiHelper.launcherBundle(getActivity(), SelfGoodsDetailActivity.class, bundle);

                    break;

                case R.id.iv_vip_icon2:

                    Bundle bundle1 = new Bundle();
                    bundle1.putString("goodsid", hotHomeBeans.get(1).getGoodsId());
                    UiHelper.launcherBundle(getActivity(), SelfGoodsDetailActivity.class, bundle1);

                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (!ButtonUtils.isFastDoubleClick()) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
//            bundle.putSerializable("home", homeCategoryBeans.get(position));
            UiHelper.launcherBundle(getActivity(), SelfGoodsTypeActivity.class, bundle);
        }
    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        /**  ScrollView 滚动动态改变标题栏 */
        // 滑动的最小距离（自行定义，you happy jiu ok）
        int minHeight = 10;
        // 滑动的最大距离（自行定义，you happy jiu ok）
        int maxHeight = 150;

        // 滑动距离小于定义得最小距离
        if (scrollView.getScrollY() <= minHeight) {
            mAlpha = 0;
        }
        // 滑动距离大于定义得最大距离
        else if (scrollView.getScrollY() > maxHeight) {
            mAlpha = 255;
        }
        // 滑动距离处于最小和最大距离之间
        else {
            // （滑动距离 - 开始变化距离）：最大限制距离 = mAlpha ：255
            mAlpha = (scrollView.getScrollY() - minHeight) * 255 / (maxHeight - minHeight);
        }
        // 初始状态 标题栏/导航栏透明等
        if (mAlpha <= 0) {
            setViewBackgroundAlpha(linearLayout, 0);
            linearLayout.setBackgroundColor(Color.argb(0, 252, 55, 125));

        }
        //  终止状态：标题栏/导航栏 不在进行变化
        else if (mAlpha >= 255) {
            setViewBackgroundAlpha(linearLayout, 255);
            linearLayout.setBackgroundColor(Color.argb(255, 247, 88, 4));

        }
        // 变化中状态：标题栏/导航栏随ScrollView 的滑动而产生相应变化
        else {
            setViewBackgroundAlpha(linearLayout, mAlpha);
            linearLayout.setBackgroundColor(Color.argb(y, 247, 88, 4));
        }
    }

    @Override
    public void loadMore() {
    }


    /**
     * 设置View的背景透明度
     *
     * @param view
     * @param alpha
     */
    public void setViewBackgroundAlpha(View view, int alpha) {
        if (view == null) return;

        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable.setAlpha(alpha);
        }
    }

    @Override
    public void getUrlSuccess(UrlBean urlBean) {
//        ProApplication.BANNERIMG = urlBean.getShopImgUrl();

        update(urlBean);

        ProApplication.UPGRADEURL = urlBean.getUpgradeUrl();
        ProApplication.UPGRADETOKEN = urlBean.getUpgradeToken();
        ProApplication.PHONE = urlBean.getKFMobile();
        ProApplication.SERVIESVIP = urlBean.getServiesVip();

        if (!ProApplication.HEADIMG.equals(urlBean + ProApplication.IMG_SMALL) || !ProApplication.BANNERIMG.equals(urlBean + ProApplication.IMG_BIG) ) {
            ProApplication.HEADIMG = urlBean.getImgUrl()+ ProApplication.IMG_SMALL;
            ProApplication.BANNERIMG = urlBean.getImgUrl() + ProApplication.IMG_BIG;
            ProApplication.CUSTOMERIMG = urlBean.getServiesUrl();
            ProApplication.SHAREDIMG = urlBean.getSharedWebUrl();
//            homePresenter.setFlash("1");
//            homePresenter.getGoodsList("1");
            homePresenter.getHomeData(ProApplication.SESSIONID(getActivity()));
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
            sharedPreferences.edit().putString(WlmUtil.IMG, ProApplication.HEADIMG).putString(WlmUtil.BANNERIMG,ProApplication.BANNERIMG)
                    .putString(WlmUtil.CUSTOMER,ProApplication.CUSTOMERIMG).putString(WlmUtil.SHAREDIMG,ProApplication.SHAREDIMG).commit();
        }

    }

    private CheckBean bean;
    private void update(UrlBean urlBean){
        String url = urlBean.getUpgradeUrl();
        OkHttpUtils.get()
                .url(url)
                .addParams("api_token", urlBean.getUpgradeToken())
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Log.d("err===========", e + "");
                    }


                    @Override
                    public void onResponse(String response, int id) {

                        Log.d("ok===========", response);

                        Gson gson = new Gson();
                        bean = gson.fromJson(response, CheckBean.class);

                        if (bean.getVersionShort() > UpdateManager.getInstance().getVersionName(getActivity())) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    mApkUrl = bean.getInstall_url();
                                    deleteApkFile();
                                    downloadApkFile();
                                    dialog.dismiss();
                                }
                            });
                            builder.create().setCanceledOnTouchOutside(false);
                            //  builder.setCancelable(false);
                            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    getActivity().finish();
                                }
                            });
                            builder.show();

                        }
                    }
                });

    }

    @Override
    public void getUrlFail(String msg) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        ProApplication.HEADIMG = sharedPreferences.getString("img", ProApplication.HEADIMG);
        ProApplication.BANNERIMG = sharedPreferences.getString(WlmUtil.BANNERIMG, ProApplication.BANNERIMG);


    }

    @Override
    public void onFlashSuccess(ArrayList<FlashBean> flashBeans) {

        this.flashBeans = flashBeans;

        if (mPtrFrame != null && mPtrFrame.isRefreshing()) {
            mPtrFrame.refreshComplete();
        }

        CustomBannerView.startBanner(flashBeans,banner,getActivity(),true);

    }

    @Override
    public void onFlashFail(String msg) {
//        UToast.show(getActivity(), msg);
        if (msg.contains("网络异常")) {
        }else {
//            UiHelper.launcher(getActivity(), LoginActivity.class);
//            getActivity().finish();
        }
    }

    @Override
    public void onGoodsListSuccess(ArrayList<GoodsListBean> goodsListBeans) {
        this.hotHomeBeans = goodsListBeans;

        final HomeHotAdapter homeHotAdapter = new HomeHotAdapter(getActivity(),goodsListBeans);
        rv_home_commodities.setAdapter(homeHotAdapter);
        homeHotAdapter.setItemClickListener(this);

        // 外部对RecyclerView设置监听
//        rv_home_commodities.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                // 查看源码可知State有三种状态：SCROLL_STATE_IDLE（静止）、SCROLL_STATE_DRAGGING（上升）、SCROLL_STATE_SETTLING（下落）
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
//                    homeHotAdapter.setScrolling(false);
//                    homeHotAdapter.notifyDataSetChanged(); // notify调用后onBindViewHolder会响应调用
//                } else {
//                    homeHotAdapter.setScrolling(true);
//                super.onScrollStateChanged(recyclerView, newState);
//                }
//            }
//        });
//        rv_home_commodities.setAdapter(homeHotAdapter);
    }

    @Override
    public void onGoodsListFail(String msg) {

    }

    @Override
    public void getHomeDataSuccess(HomeBean homeBean) {

        this.homeBean = homeBean;

        onFlashSuccess(homeBean.getFlash());
        onGoodsListSuccess(homeBean.getGoodsList());

//        Picasso.with(getActivity()).load("http://wlmimg.mmibb.net:99/imgdb/91d03296ead1417bbb9e59979bad40f4.png").transform(transformation).error(R.mipmap.banner_1).into(big_1);
        Picasso.with(getActivity()).load(ProApplication.BANNERIMG+homeBean.getFlashVip().get(0).getFlashPic()).transform(transformation).error(R.mipmap.banner_1).into(big_1);
        Picasso.with(getActivity()).load(ProApplication.BANNERIMG+homeBean.getVipList().get(0).getGoodsIndexImg()).transform(transformation1).error(R.mipmap.banner_2).into(iv_vip_icon1);
        if (homeBean.getVipList().size()>=2) {
            Picasso.with(getActivity()).load(ProApplication.BANNERIMG + homeBean.getVipList().get(1).getGoodsIndexImg()).transform(transformation1).error(R.mipmap.banner_2).into(iv_vip_icon2);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date = simpleDateFormat.parse(homeBean.getTime());
            Date date1 = new Date();
            tv_rush_time.start(date.getTime() - date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getHomeDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }


    @Override
    public void onItemClick(int position) {

        if (!ButtonUtils.isFastDoubleClick()) {
                Bundle bundle = new Bundle();
                bundle.putString("goodsid", hotHomeBeans.get(position).getGoodsId());
                String type = WlmUtil.getType(hotHomeBeans.get(position).getGoodsType());

                bundle.putString(WlmUtil.TYPE, type);
                UiHelper.launcherBundle(getActivity(), SelfGoodsDetailActivity.class, bundle);

        }
    }

    /**
     * 删除之前升级时下载的老的 apk 文件
     */
    @AfterPermissionGranted(RC_PERMISSION_DELETE)
    public void deleteApkFile() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            // 删除之前升级时下载的老的 apk 文件
            BGAUpgradeUtil.deleteOldApk();
        } else {
            EasyPermissions.requestPermissions(this, "使用 BGAUpdateDemo 需要授权读写外部存储权限!", RC_PERMISSION_DELETE, perms);
        }
    }

    /**
     * 下载新版 apk 文件
     */
    @AfterPermissionGranted(RC_PERMISSION_DOWNLOAD)
    public void downloadApkFile() {

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            downNewApk();
        } else {
            EasyPermissions.requestPermissions(this, "使用 BGAUpdateDemo 需要授权读写外部存储权限!", RC_PERMISSION_DOWNLOAD, perms);
        }
    }

    private void downNewApk(){
        // 如果新版 apk 文件已经下载过了，直接 return，此时不需要开发者调用安装 apk 文件的方法，在 isApkFileDownloaded 里已经调用了安装」
        if (BGAUpgradeUtil.isApkFileDownloaded(mNewVersion)) {
            return;
        }

        // 下载新版 apk 文件
        BGAUpgradeUtil.downloadApkFile(mApkUrl, mNewVersion)
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onStart() {
                        showDownloadingDialog();
                    }

                    @Override
                    public void onCompleted() {
                        dismissDownloadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDownloadingDialog();
                    }

                    @Override
                    public void onNext(File apkFile) {
                        if (apkFile != null) {
                            BGAUpgradeUtil.installApk(apkFile);
                        }
                    }
                });
    }

    /**
     * 显示下载对话框
     */
    private void showDownloadingDialog() {
        if (mDownloadingDialog == null) {
            mDownloadingDialog = new DownloadingDialog(getActivity());
        }
        mDownloadingDialog.setUpdateMessage(bean.getChangelog() + "");
        mDownloadingDialog.show();
    }

    /**
     * 隐藏下载对话框
     */
    private void dismissDownloadingDialog() {
        if (mDownloadingDialog != null) {
            mDownloadingDialog.dismiss();
            Dialog dialog = new Dialog(getActivity());
            TextView textView = new TextView(getActivity());
            ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
            layoutParams.height = 500;
            layoutParams.width = 500;
            textView.setLayoutParams(layoutParams);
            textView.setText("请升级安装最新版本");
            dialog.setContentView(textView);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    Transformation transformation = new Transformation() {

        @Override
        public Bitmap transform(Bitmap source) {

            if(source.getWidth()==0){
                return source;
            }

            int a = big_1.getWidth();
            if (source.getWidth() > source.getHeight()){
                int h = source.getHeight()*a/source.getWidth();
                Bitmap result = Bitmap.createScaledBitmap(source, a, h, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }else if (source.getWidth() < source.getHeight()){
                int w = source.getWidth()*a/source.getHeight();
                Bitmap result = Bitmap.createScaledBitmap(source, w, a, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }else if (source.getWidth() == source.getHeight()){
                Bitmap result = Bitmap.createScaledBitmap(source, a, a, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            return source;
        }

        @Override
        public String key() {
            return "transformation" + " desiredWidth";
        }
    };

    Transformation transformation1 = new Transformation() {

        @Override
        public Bitmap transform(Bitmap source) {

            if(source.getWidth()==0){
                return source;
            }

            int a = iv_vip_icon1.getWidth();
            if (source.getWidth() > source.getHeight()){
                int h = source.getHeight()*a/source.getWidth();
                Bitmap result = Bitmap.createScaledBitmap(source, a, h, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }else if (source.getWidth() < source.getHeight()){
                int w = source.getWidth()*a/source.getHeight();
                Bitmap result = Bitmap.createScaledBitmap(source, w, a, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }else if (source.getWidth() == source.getHeight()){
                Bitmap result = Bitmap.createScaledBitmap(source, a, a, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            return source;
        }

        @Override
        public String key() {
            return "transformation" + " desiredWidth";
        }
    };

}
