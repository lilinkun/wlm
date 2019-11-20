package com.wlm.wlm.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.SettingContract;
import com.wlm.wlm.entity.CheckBean;
import com.wlm.wlm.entity.DownloadBean;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.SettingPresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.ui.DownloadingDialog;
import com.wlm.wlm.util.DataCleanManager;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.UpdateManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

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
 * Created by LG on 2018/11/13.
 */

public class SettingActivity extends BaseActivity implements OnTitleBarClickListener, SettingContract {

    @BindView(R.id.custom_title)
    CustomTitleBar customTitleBar;
    @BindView(R.id.tv_clear)
    TextView tv_clear;

    /**
     * 下载文件权限请求码
     */
    private static final int RC_PERMISSION_DOWNLOAD = 1;
    /**
     * 删除文件权限请求码
     */
    private static final int RC_PERMISSION_DELETE = 2;

    private DownloadingDialog mDownloadingDialog;
    private String mNewVersion = "2";
    private String mApkUrl = "";
    //    private DownloadBean downloadBean;
    private CheckBean bean;

    private SettingPresenter settingPresenter = new SettingPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));
        customTitleBar.SetOnTitleClickListener(this);

        settingPresenter.onCreate(this, this);

        try {
            String cacheSize = DataCleanManager.getTotalCacheSize(this);
            if (!cacheSize.equals("0K")) {
                tv_clear.setText(cacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    }

    @Override
    public void onBackClick() {
        Intent intent = new Intent();
        intent.putExtra("loginout", false);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent();
            intent.putExtra("loginout", false);
            setResult(RESULT_OK, intent);
            finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.rl_personal_info, R.id.rl_modify_psd, R.id.ll_loginout, R.id.rl_modify_pay_psd, R.id.rl_clear, R.id.rl_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_personal_info:

                UiHelper.launcher(this, PersonalInfoActivity.class);

                break;

            case R.id.rl_modify_psd:

                Bundle bundle = new Bundle();
                bundle.putInt("modify", 1);
                UiHelper.launcherBundle(this, ModifyPayActivity.class, bundle);

                break;

            case R.id.ll_loginout:

                settingPresenter.LoginOut(ProApplication.SESSIONID(this));

                break;

            case R.id.rl_modify_pay_psd:

                Bundle bundle2 = new Bundle();
                bundle2.putInt("modify", 2);
                UiHelper.launcherBundle(this, ModifyPayActivity.class, bundle2);

                break;

            case R.id.rl_clear:

                DataCleanManager.clearAllCache(this);
                if (tv_clear.getText().toString().length() > 0) {
                    toast("清除缓存成功");
                }
                tv_clear.setText("");

                break;

            case R.id.rl_update:
                myPermission();

                final double code = UpdateManager.getInstance().getVersionName(this);
                String url = "http://api.fir.im/apps/latest/5c2c765a959d696162a55d44";
                OkHttpUtils.get()
                        .url(url)
                        .addParams("api_token", "51ac2356b46338ac2d5704d1a8ef446d")
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

                                if (bean.getVersionShort() > code) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                                            finish();
                                        }
                                    });
                                    builder.show();

                                } else {
                                    toast("已经是最新版本");
                                }
                            }
                        });


//                settingPresenter.update(ProApplication.SESSIONID(this));
                break;
        }
    }


    @Override
    public void LoginOutSuccess(String msg) {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        UiHelper.launcher(this, LoginActivity.class);

        Intent intent = new Intent();
        intent.putExtra("loginout", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void LoginOutFail(String msg) {

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
        } else {
            EasyPermissions.requestPermissions(this, "使用 BGAUpdateDemo 需要授权读写外部存储权限!", RC_PERMISSION_DOWNLOAD, perms);
        }
    }

    /**
     * 显示下载对话框
     */
    private void showDownloadingDialog() {
        if (mDownloadingDialog == null) {
            mDownloadingDialog = new DownloadingDialog(this);
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
        }
    }

    @Override
    public void updateSuccess(DownloadBean downloadBean) {
        /*this.downloadBean = downloadBean;
        double code = UpdateManager.getInstance().getVersionName(this);
        double as = Double.parseDouble(downloadBean.getVer());
        if (as > code) {
            mApkUrl = downloadBean.getUrl();
            deleteApkFile();
            downloadApkFile();
        }else{
            toast("已是最新版本");
        }*/
    }

    @Override
    public void updateFail(String failMsg) {
        toast("已是最新版本");
    }
}
