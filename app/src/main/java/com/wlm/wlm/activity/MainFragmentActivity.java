package com.wlm.wlm.activity;


import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.trycath.myupdateapklibrary.UpdateApk;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.FragmentsAdapter;
import com.wlm.wlm.adapter.MyShoppingCarAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MainFragmentContract;
import com.wlm.wlm.entity.CheckBean;
import com.wlm.wlm.entity.DownloadBean;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.fragment.HomeFragment;
import com.wlm.wlm.fragment.LzyMallFragment;
import com.wlm.wlm.fragment.MeFragment;
import com.wlm.wlm.fragment.WlmCartFragment;
import com.wlm.wlm.presenter.MainFragmentPresenter;
import com.wlm.wlm.receiver.NetReceiver;
import com.wlm.wlm.ui.DownloadingDialog;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.util.UpdateManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.update.BGADownloadProgressEvent;
import cn.bingoogolapple.update.BGAUpgradeUtil;
import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by LG on 2018/12/4.
 */

public class MainFragmentActivity extends BaseActivity implements MainFragmentContract, ViewPager.OnPageChangeListener {

    @BindView(R.id.menu_bottom_1)
    RelativeLayout menu_bottom_1;
    @BindView(R.id.menu_bottom_2)
    RelativeLayout menu_bottom_2;
    @BindView(R.id.menu_bottom_3)
    RelativeLayout menu_bottom_3;
    @BindView(R.id.bottom_bar)
    LinearLayout bottom_bar;

    @BindView(R.id.top_vp)
    ViewPager mViewPager;


    private FragmentManager manager;
    private WlmCartFragment lzyMallFragment = new WlmCartFragment();
    private MeFragment meFragment = new MeFragment();
    public static String username ="";
    private MainFragmentPresenter mainFragmentPresenter = new MainFragmentPresenter();
    /*
     * 下载文件权限请求码
     */
    private static final int RC_PERMISSION_DOWNLOAD = 1;
    /**
     * 删除文件权限请求码
     */
    private static final int RC_PERMISSION_DELETE = 2;

    private DownloadingDialog mDownloadingDialog;
    private String mNewVersion = "2";
    private String mApkUrl = "https://appapi.100zt.com/update/datalife.apk";
//    private DownloadBean downloadBean;
    private List<RelativeLayout> relativeLayouts = new ArrayList<>();
    private final SparseArray<BaseFragment> sparseArray = new SparseArray<>();
    private CheckBean bean;
    private BroadcastReceiver broadcastReceiver = new NetReceiver();

    public static boolean isHome = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mainfragment;
    }

    @Override
    public void initEventAndData() {

        mainFragmentPresenter.onCreate(this,this);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver,filter);


        onPageBind();

        manager = getSupportFragmentManager();
        relativeLayouts.add(menu_bottom_1);
        relativeLayouts.add(menu_bottom_2);
        relativeLayouts.add(menu_bottom_3);
        onClick(findViewById(R.id.menu_bottom_1));

        // 监听下载进度
        BGAUpgradeUtil.getDownloadProgressEventObservable()
                .compose(this.<BGADownloadProgressEvent>bindToLifecycle())
                .subscribe(new Action1<BGADownloadProgressEvent>() {
                    @Override
                    public void call(BGADownloadProgressEvent downloadProgressEvent) {
                        if (mDownloadingDialog != null && mDownloadingDialog.isShowing() && downloadProgressEvent.isNotDownloadFinished()) {
                            mDownloadingDialog.setProgress(downloadProgressEvent.getProgress(), downloadProgressEvent.getTotal());
                        }
                    }
                });

        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
        mainFragmentPresenter.login(sharedPreferences.getString(WlmUtil.OPENID, ""), sharedPreferences.getString(WlmUtil.UNIONID, ""), "2", ProApplication.SESSIONID(this));

        mainFragmentPresenter.getUrl();
    }

    @OnClick({R.id.menu_bottom_1,R.id.menu_bottom_2,R.id.menu_bottom_3})
    public void onClick(View view){
//        FragmentTransaction transaction = manager.beginTransaction();
        switch (view.getId()){
            case R.id.menu_bottom_1:
//                transaction.replace(R.id.top_bar,new HomeFragment());
                setMenuBg(menu_bottom_1);
                mViewPager.setCurrentItem(0,false);
                break;
            case R.id.menu_bottom_2:
//                transaction.replace(R.id.top_bar,lzyMallFragment);
                setMenuBg(menu_bottom_2);
                mViewPager.setCurrentItem(1,false);
                break;
            case R.id.menu_bottom_3:
//                transaction.replace(R.id.top_bar,new MeFragment());
                setMenuBg(menu_bottom_3);
                mViewPager.setCurrentItem(2,false);
                break;
        }
//        transaction.commit();
    }

    public void onPageBind() {
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
        getMenusFragments();
        adapter.setFragments(sparseArray);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(4);
    }

    private void getMenusFragments() {
        sparseArray.put(WlmUtil.PAGE_HOMEPAGE, new HomeFragment());
        sparseArray.put(WlmUtil.PAGE_MALL, lzyMallFragment);
        sparseArray.put(WlmUtil.PAGE_ME, meFragment);
    }

    private void setMenuBg(RelativeLayout layout){
        Object o = relativeLayouts.iterator();

        while (((Iterator)o).hasNext()){
            RelativeLayout relativeLayout = (RelativeLayout) ((Iterator) o).next();
            if (relativeLayout == layout){
                relativeLayout.setSelected(true);
            }else {
                relativeLayout.setSelected(false);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isHome){
            mViewPager.setCurrentItem(0,true);
            isHome = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UpdateApk.destory();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            exitByDoubleClick();
        }
        return false;
    }

    boolean isExit = false;
    private void exitByDoubleClick() {
        Timer tExit=null;
        if(!isExit){
            isExit=true;
            toast("再按一次退出程序");
            tExit=new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit=false;//取消退出
                }
            },2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        }else{
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == MyShoppingCarAdapter.child_goods_result){
                lzyMallFragment.setData();
            }else if (requestCode == RC_PERMISSION_DOWNLOAD){
                String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(this, perms)) {
                    downNewApk();
                }
            }else if (requestCode == RC_PERMISSION_DELETE){
                String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(this, perms)) {
                    // 删除之前升级时下载的老的 apk 文件
                    BGAUpgradeUtil.deleteOldApk();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void getUpdateSuccess(final DownloadBean downloadBean) {
//        UpdateKey.API_TOKEN = "51ac2356b46338ac2d5704d1a8ef446d";
//        UpdateKey.APP_ID = "5c2c765a959d696162a55d44";
//        this.downloadBean = downloadBean;
        final double code = UpdateManager.getInstance().getVersionName(this);
        double as = Double.parseDouble(downloadBean.getVer());

    }

    @Override
    public void getUpdateFail(String msg) {
        toast(msg);
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
        sharedPreferences.edit().putString("sessionid",ProApplication.SESSIONID(this)).putBoolean(WlmUtil.LOGIN,true)
                .putString(WlmUtil.ACCOUNT,loginBean.getNickName()).putString(WlmUtil.TELEPHONE,loginBean.getMobile())
                .putString(WlmUtil.USERNAME,loginBean.getUserName()).putString(WlmUtil.USERID,loginBean.getUserId())
                .putString(WlmUtil.HEADIMGURL,loginBean.getPortrait())
                .putString(WlmUtil.VIPVALIDITY,loginBean.getVipValidity()).commit();

        ProApplication.HEADIMG = sharedPreferences.getString(WlmUtil.IMG, "");
        ProApplication.BANNERIMG = sharedPreferences.getString(WlmUtil.BANNERIMG,"");
        ProApplication.CUSTOMERIMG = sharedPreferences.getString(WlmUtil.CUSTOMER,"");
        ProApplication.SHAREDIMG = sharedPreferences.getString(WlmUtil.SHAREDIMG,"");
    }

    @Override
    public void onLoginFail(String msg) {
        if (msg.equals("登录已失效")){
            UiHelper.launcher(this, LoginActivity.class);
        }
    }

    @Override
    public void getUrlSuccess(UrlBean urlBean) {
        update(urlBean);

        ProApplication.UPGRADEURL = urlBean.getUpgradeUrl();
        ProApplication.UPGRADETOKEN = urlBean.getUpgradeToken();

        if (!ProApplication.HEADIMG.equals(urlBean + ProApplication.IMG_SMALL) || !ProApplication.BANNERIMG.equals(urlBean + ProApplication.IMG_BIG) ) {
            ProApplication.HEADIMG = urlBean.getImgUrl()+ ProApplication.IMG_SMALL;
            ProApplication.BANNERIMG = urlBean.getImgUrl() + ProApplication.IMG_BIG;
            ProApplication.CUSTOMERIMG = urlBean.getServiesUrl();
            ProApplication.SHAREDIMG = urlBean.getSharedWebUrl();
            SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
            sharedPreferences.edit().putString(WlmUtil.IMG, ProApplication.HEADIMG).putString(WlmUtil.BANNERIMG,ProApplication.BANNERIMG)
                    .putString(WlmUtil.CUSTOMER,ProApplication.CUSTOMERIMG).putString(WlmUtil.SHAREDIMG,ProApplication.SHAREDIMG).commit();
        }
    }

    private CheckBean bean1;
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
                        bean1 = gson.fromJson(response, CheckBean.class);

                        if (bean1.getVersionShort() > UpdateManager.getInstance().getVersionName(MainFragmentActivity.this)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainFragmentActivity.this).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    mApkUrl = bean1.getInstall_url();
                                    deleteApkFile();
                                    downloadApkFile();
                                }
                            });
                            builder.create().setCanceledOnTouchOutside(false);
                            //  builder.setCancelable(false);
                            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    finish();
                                }
                            });
                            builder.show();

                        }
                    }
                });

    }

    @Override
    public void getUrlFail(String msg) {

    }

    /**
     * 删除之前升级时下载的老的 apk 文件
     */
    @AfterPermissionGranted(RC_PERMISSION_DELETE)
    public void deleteApkFile() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
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
        if (EasyPermissions.hasPermissions(this, perms)) {
            downNewApk();
        } else {
            EasyPermissions.requestPermissions(this, "使用 BGAUpdateDemo 需要授权读写外部存储权限!", RC_PERMISSION_DOWNLOAD, perms);
        }
    }

    /*public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case RC_PERMISSION_DOWNLOAD:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downNewApk();
                } else if (grantResults[0] == RC_PERMISSION_DELETE){
                    // 删除之前升级时下载的老的 apk 文件
                    BGAUpgradeUtil.deleteOldApk();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }*/


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
            mDownloadingDialog = new DownloadingDialog(this);
        }
        mDownloadingDialog.setUpdateMessage(bean1.getChangelog() + "");
        mDownloadingDialog.show();
    }

    /**
     * 隐藏下载对话框
     */
    private void dismissDownloadingDialog() {
        if (mDownloadingDialog != null) {
            mDownloadingDialog.dismiss();
            Dialog dialog = new Dialog(this);
            TextView textView = new TextView(this);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == 0) {
            setMenuBg(menu_bottom_1);
        }else if (position == 1){
            setMenuBg(menu_bottom_2);
            lzyMallFragment.setData();
        }else if (position == 2){
            setMenuBg(menu_bottom_3);
            meFragment.setPoint();
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



}
