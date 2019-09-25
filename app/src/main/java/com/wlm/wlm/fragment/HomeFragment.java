package com.wlm.wlm.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
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

import com.google.gson.Gson;
import com.wlm.wlm.R;
import com.wlm.wlm.activity.GoodsTypeActivity;
import com.wlm.wlm.activity.GrouponActivity;
import com.wlm.wlm.activity.GrouponGoodsDetailActivity;
import com.wlm.wlm.activity.IntegralStoreActivity;
import com.wlm.wlm.activity.LoginActivity;
import com.wlm.wlm.activity.MainFragmentActivity;
import com.wlm.wlm.activity.ManufactureStoreActivity;
import com.wlm.wlm.activity.OpinionActivity;
import com.wlm.wlm.activity.SearchActivity;
import com.wlm.wlm.activity.SelfGoodsDetailActivity;
import com.wlm.wlm.activity.SelfGoodsTypeActivity;
import com.wlm.wlm.activity.VipActivity;
import com.wlm.wlm.adapter.HomeFragmentAdapter;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.HomeContract;
import com.wlm.wlm.entity.CheckBean;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.interf.OnScrollChangedListener;
import com.wlm.wlm.presenter.HomePresenter;
import com.wlm.wlm.transform.BannerTransform;
import com.wlm.wlm.ui.CusPtrClassicFrameLayout;
import com.wlm.wlm.ui.CustomerPtrHandler;
import com.wlm.wlm.ui.DownloadingDialog;
import com.wlm.wlm.ui.MyGridView;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.ui.TranslucentScrollView;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.CustomRoundedImageLoader;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.UpdateManager;
import com.wlm.wlm.util.WlmUtil;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;

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

public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener, OnScrollChangedListener, HomeContract, TbHotGoodsAdapter.OnItemClickListener, OnBannerListener {

    @BindView(R.id.title_layout_search)
    LinearLayout linearLayout;
    @BindView(R.id.gv_goods_type)
    MyGridView gridView;
    @BindView(R.id.bannerView)
    Banner banner;
    @BindView(R.id.tsv_home)
    TranslucentScrollView translucentScrollView;
    @BindView(R.id.gv_hot_commodities)
    RecyclerView mHotGridView;
    @BindView(R.id.iv_home_advertisement1)
    ImageView iv_home_advertisement1;
    @BindView(R.id.iv_home_advertisement2)
    ImageView iv_home_advertisement2;
    @BindView(R.id.ll_membership)
    LinearLayout ll_membership;
    @BindView(R.id.mPtrframe)
    CusPtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.ll_strategy)
    LinearLayout ll_strategy;

    private PopupWindow popupWindow;
    private int PAGE_INDEX = 1;
    int mAlpha = 0;
    private ArrayList<GoodsListBean> hotHomeBeans;
    private HomeFragmentAdapter homeFragmentAdapter;
    private ArrayList<FlashBean> flashBeans;

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
        Eyes.translucentStatusBar(getActivity());
        translucentScrollView.init(this);
        initPtrFrame();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
        ProApplication.HEADIMG = sharedPreferences.getString(WlmUtil.IMG, "");
        ProApplication.BANNERIMG = sharedPreferences.getString(WlmUtil.BANNERIMG,"");
        ProApplication.CUSTOMERIMG = sharedPreferences.getString(WlmUtil.CUSTOMER,"");
        ProApplication.SHAREDIMG = sharedPreferences.getString(WlmUtil.SHAREDIMG,"");

        homePresenter.onCreate(getActivity(),this);
        homePresenter.getUrl(ProApplication.SESSIONID(getActivity()));

        if (ProApplication.HEADIMG != null && !ProApplication.HEADIMG.isEmpty()) {
            homePresenter.setFlash("1");
            homePresenter.getGoodsList("1");
        }

        gridView.setOnItemClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        int spanCount = 5; // 2 columns
        int spacing = 20; // 50px
//        api.registerApp("wx3686dfb825618610");

        boolean includeEdge = false;
        mHotGridView.addItemDecoration(new SpaceItemDecoration(spanCount, spacing,0));
        mHotGridView.setLayoutManager(gridLayoutManager);
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

    @OnClick({R.id.lin_list, R.id.text_search, R.id.iv_home_tb, R.id.iv_home_tm, R.id.iv_home_advertisement1,R.id.iv_vip
            ,R.id.iv_home_advertisement2,R.id.ll_groupon,R.id.ll_crowd_funding,R.id.ll_strategy,R.id.ll_home_opinion})
    public void onClick(View view) {
        if (!ButtonUtils.isFastDoubleClick(view.getId())) {
            switch (view.getId()) {
                case R.id.lin_list:

//                    UiHelper.launcher(getActivity(), Category1Activity.class);

                    break;

                case R.id.text_search:

                    UiHelper.launcher(getActivity(), SearchActivity.class);

                    break;

                case R.id.iv_home_tb:
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", 0);
                    bundle.putInt("isMall",0);
                    UiHelper.launcherBundle(getActivity(), GoodsTypeActivity.class, bundle);

                    break;

                case R.id.iv_home_tm:
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("position", 0);
                    bundle1.putInt("isMall",1);
                    UiHelper.launcherBundle(getActivity(), GoodsTypeActivity.class, bundle1);

                    break;

                case R.id.iv_home_advertisement1:


                    UiHelper.launcher(getActivity(), ManufactureStoreActivity.class);

                    break;

                case R.id.iv_vip:

                    UiHelper.launcher(getActivity(), VipActivity.class);


                    break;

                case R.id.iv_home_advertisement2:

                    UiHelper.launcher(getActivity(), IntegralStoreActivity.class);

                    break;

                case R.id.ll_groupon:

                    UiHelper.launcher(getActivity(), GrouponActivity.class);


                    break;

                case R.id.ll_crowd_funding:

//                    UiHelper.launcher(getActivity(), CrowdFundingActivity.class);

                    break;

                case R.id.ll_strategy:

//                    UiHelper.launcher(getActivity(), StrategyActivity.class);


                    break;

                case R.id.ll_home_opinion:

                    UiHelper.launcher(getActivity(), OpinionActivity.class);

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

        if (!ProApplication.HEADIMG.equals(urlBean + ProApplication.IMG_SMALL) || !ProApplication.BANNERIMG.equals(urlBean + ProApplication.IMG_BIG) ) {
            ProApplication.HEADIMG = urlBean.getImgUrl()+ ProApplication.IMG_SMALL;
            ProApplication.BANNERIMG = urlBean.getImgUrl() + ProApplication.IMG_BIG;
            ProApplication.CUSTOMERIMG = urlBean.getServiesUrl();
            ProApplication.SHAREDIMG = urlBean.getSharedWebUrl();
            homePresenter.setFlash("1");
            homePresenter.getGoodsList("1");
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


        startBanner(flashBeans);

//        DBManager.getInstance(getActivity()).deleteCategoryListBean();
//        DBManager.getInstance(getActivity()).insertCategoryList(homeCategoryBeans);

//        hotHomeBeans = (ArrayList<HotHomeBean>) homeHeadBean.getHot_goods();
//        tbHotGoodsAdapter = new TbHotGoodsAdapter(getActivity(), null, getLayoutInflater());
//        mHotGridView.setAdapter(tbHotGoodsAdapter);
//        tbHotGoodsAdapter.setItemClickListener(this);
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
        TbHotGoodsAdapter tbHotGoodsAdapter = new TbHotGoodsAdapter(getActivity(),goodsListBeans,getLayoutInflater());
        mHotGridView.setAdapter(tbHotGoodsAdapter);
        tbHotGoodsAdapter.setItemClickListener(this);
    }

    @Override
    public void onGoodsListFail(String msg) {

    }

    private void startBanner(final ArrayList<FlashBean> flashBeans) {
        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < flashBeans.size(); i++) {
            strings.add("asdads" + i);

//            FlashBean flashBean  = flashBeans.get(i);
//            flashBeans.add(flashBean);
        }

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new CustomRoundedImageLoader());
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
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();

    }


    @Override
    public void onItemClick(int position) {

        if (!ButtonUtils.isFastDoubleClick()) {
            if (!hotHomeBeans.get(position).getGoodsType().equals("2")) {
                Bundle bundle = new Bundle();
                bundle.putString("goodsid", hotHomeBeans.get(position).getGoodsId());
                String type = WlmUtil.getType(hotHomeBeans.get(position).getGoodsType());

                bundle.putString(WlmUtil.TYPE, type);
                UiHelper.launcherBundle(getActivity(), SelfGoodsDetailActivity.class, bundle);
            }else {
                Bundle bundle = new Bundle();
                bundle.putSerializable(WlmUtil.GROUPONGOODS,hotHomeBeans.get(position));
                UiHelper.launcherBundle(getActivity(), GrouponGoodsDetailActivity.class,bundle);
            }
        }
    }

    @Override
    public void OnBannerClick(int position) {
        FlashBean flashBean = flashBeans.get(position);
//        if (flashBean.getUrl() != null && flashBean.getUrl().length() > 0  && PhoneFormatCheckUtils.isNumeric(flashBean.getUrl())){
//            if (Integer.valueOf(flashBean.getUrl()) > 0){
//                Bundle bundle = new Bundle();
//                bundle.putString("goodsid",flashBean.getUrl());
//                UiHelper.launcherBundle(getActivity(),SelfGoodsDetailActivity.class,bundle);
//            }
//        }

//        UiHelper.launcher(getActivity(), GoodsDetailActivity.class);
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

}
