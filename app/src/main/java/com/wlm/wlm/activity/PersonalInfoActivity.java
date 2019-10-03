package com.wlm.wlm.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.PersonalInfoContract;
import com.wlm.wlm.entity.CheckBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.ImageUploadResultBean;
import com.wlm.wlm.entity.PersonalInfoBean;
import com.wlm.wlm.http.RetrofitHelper;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.PersonalInfoPresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.ui.DownloadingDialog;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.util.DataCleanManager;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.FileImageUpload;
import com.wlm.wlm.util.PhoneFormatCheckUtils;
import com.wlm.wlm.util.UiHelper;
import com.squareup.picasso.Picasso;
import com.wlm.wlm.util.UpdateManager;
import com.wlm.wlm.util.WlmUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.update.BGADownloadProgressEvent;
import cn.bingoogolapple.update.BGAUpgradeUtil;
import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;
import rx.functions.Action1;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

/**
 * Created by LG on 2018/11/19.
 */

public class PersonalInfoActivity extends BaseActivity implements OnTitleBarClickListener, View.OnClickListener,PersonalInfoContract {

    @BindView(R.id.titlebar)
    CustomTitleBar customTitleBar;
    @BindView(R.id.riv_info)
    RoundImageView roundImageView;
    @BindView(R.id.tv_nickname)
    TextView nickName;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_clear)
    TextView tv_clear;
    @BindView(R.id.rl_change_psd)
    RelativeLayout rl_change_psd;
    @BindView(R.id.tv_new_edition)
    TextView tv_new_edition;

    private PopupWindow popupWindow = null;
    private static final int IMAGEBUNDLE = 0x221;
    private static final int REQUEST_CAMERA = 0x222;
    private static final int RESULT_MYNICK = 0x112;
    private boolean isChangeSuccess = false;
    private String mFilePath;
    private  Uri cropImageUri;
    private PersonalInfoPresenter personalInfoPresenter = new PersonalInfoPresenter();

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
    private double code=0;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 123){
                String str = msg.getData().getString("str");
                if (str!= null && str.length() > 0) {
                    Gson gson = new Gson();
                    ImageUploadResultBean imageUploadResultBean = gson.fromJson(str, ImageUploadResultBean.class);

                    personalInfoPresenter.uploadImage(imageUploadResultBean.getUrl().get(0),ProApplication.SESSIONID(PersonalInfoActivity.this));
                }
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        customTitleBar.SetOnTitleClickListener(this);


        code = UpdateManager.getInstance().getVersionName(this);

        mFilePath = Environment.getExternalStorageDirectory()+ "/test/" + "temp.jpg";// 获取SD卡路径
//        mFilePath = mFilePath + "/test/" + "temp.jpg";// 指定路径

        personalInfoPresenter.onCreate(this,this);
//        personalInfoPresenter.getInfo(ProApplication.SESSIONID(this));

        try {
            String cacheSize = DataCleanManager.getTotalCacheSize(this);
            if (!cacheSize.equals("0K")) {
                tv_clear.setText(cacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN,MODE_PRIVATE);
        if (!sharedPreferences.getString(WlmUtil.HEADIMGURL,"").equals("")) {
            Picasso.with(this).load(sharedPreferences.getString(WlmUtil.HEADIMGURL, "")).into(roundImageView);
        }

        nickName.setText(sharedPreferences.getString(WlmUtil.ACCOUNT,""));
        tv_phone.setText(PhoneFormatCheckUtils.phoneAddress(sharedPreferences.getString(WlmUtil.TELEPHONE,"")));
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

        tv_new_edition.setText("V" + code);
    }

    @OnClick({R.id.rl_head_title_info,R.id.rl_nickname_info,R.id.iv_head_right,R.id.rl_clear,R.id.tv_loginout,R.id.rl_change_psd,R.id.rl_update})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_head_title_info:

                /*View v = LayoutInflater.from(this).inflate(R.layout.pop_head_info,null);

                popupWindow = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,true);

                popupWindow.showAsDropDown(customTitleBar);

                TextView tvExit = (TextView) v.findViewById(R.id.tv_pop_exit);
                TextView tvTakePhoto = (TextView) v.findViewById(R.id.tv_takephoto);
                TextView tvSystemPhoto = (TextView) v.findViewById(R.id.tv_systemphoto);

                tvExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                tvTakePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();

                        if (ContextCompat.checkSelfPermission(PersonalInfoActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(PersonalInfoActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(PersonalInfoActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(PersonalInfoActivity.this,
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);} else {

                            File outputImage  = new File(mFilePath);
                            File str = outputImage.getParentFile();
                            if (!outputImage.getParentFile().exists()){
                                outputImage.getParentFile().mkdirs();
                            }

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
                                //改变Uri  com.wlm.wlm.fileprovider注意和xml中的一致
                                Uri photoUri = FileProvider.getUriForFile(PersonalInfoActivity.this, "com.wlm.wlm.fileprovider", outputImage);
                                //添加权限
                                intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
                                intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);//增加读写权限
//                                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径  
                            }else {
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
                            }
                            startActivityForResult(intent, REQUEST_CAMERA);
                        }
                    }
                });
                tvSystemPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        //调用相册
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, IMAGEBUNDLE);
                    }
                });*/


                break;

            case R.id.rl_nickname_info:
//                Bundle bundle = new Bundle();
//                bundle.putString("nick",nickName.getText().toString());
//                UiHelper.launcherForResultBundle(this,MyNickNameActivity.class,RESULT_MYNICK,bundle);

               /* IWXAPI api = WXAPIFactory.createWXAPI(this, "wx86ed3100a8c2586f");
                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                req.userName = "gh_aa9e3dbf8fd0";
                req.path = "pages/index/index";
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST;
                api.sendReq(req);
*/

                break;

            case R.id.iv_head_right:

//                personalInfoPresenter.getInfo();

                break;

            case R.id.rl_clear:

                DataCleanManager.clearAllCache(this);
                if (tv_clear.getText().toString().length() > 0) {
                    toast("清除缓存成功");
                }
                tv_clear.setText("");

                break;

            case R.id.tv_loginout:

                personalInfoPresenter.LoginOut(ProApplication.SESSIONID(this));

                break;

            case R.id.rl_change_psd:

                UiHelper.launcher(this,ModifyPayActivity.class);

                break;

            case R.id.rl_update:
                myPermission();

                String url = ProApplication.UPGRADEURL;
                OkHttpUtils.get()
                        .url(url)
                        .addParams("api_token", ProApplication.UPGRADETOKEN)
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfoActivity.this).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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

                                }else {
                                    toast("已经是最新版本");
                                }
                            }
                        });


                break;
        }
    }

    @Override
    public void onBackClick() {
        if (isChangeSuccess) {
            setResult(RESULT_OK);
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (isChangeSuccess) {
                setResult(RESULT_OK);
            }
            finish();
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGEBUNDLE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();

            startPhotoZoom(selectedImage);
        }else if(requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri selectedImage = Uri.fromFile(new File(mFilePath));
                Bitmap bm = BitmapFactory.decodeFile(new File(mFilePath).getAbsolutePath());
//            roundImageView.setImageBitmap(bm);
                Uri photoUri = FileProvider.getUriForFile(PersonalInfoActivity.this, "com.wlm.wlm.fileprovider", new File(mFilePath));
                startPhotoZoom(photoUri);
            }else {
                Uri inputUri = Uri.fromFile(new File(mFilePath));
                startPhotoZoom(inputUri);
            }
        }else if (requestCode == 4 && resultCode == Activity.RESULT_OK){
            final File file = new File(getExternalCacheDir(), "crop.jpg");
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            roundImageView.setImageBitmap(bm);

            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {

                            String str = FileImageUpload.uploadFile(file, RetrofitHelper.ImageUrl);
                            Bundle bundle = new Bundle();
                            bundle.putString("str",str);
                            Message message = new Message();
                            message.setData(bundle);
                            message.what = 123;
                            handler.sendMessage(message);
                        }
                    }
            ).start();

        }else if (requestCode == RESULT_MYNICK && resultCode == Activity.RESULT_OK){
            String account = data.getStringExtra("account");
            nickName.setText(account);
        }
    }
    //加载图片
    private void showImage(String imagePath){
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
//        roundImageView.setImageBitmap(bm);
    }

    public void startPhotoZoom(Uri paramUri)
    {
        File headFile = new File(getExternalCacheDir(), "crop.jpg");
        try
        {
            if ((headFile).exists()) {
                (headFile).delete();
            }
            (headFile).createNewFile();
        }catch (IOException e)
            {
                    e.printStackTrace();
            }
        this.cropImageUri = Uri.fromFile(headFile);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(paramUri, "image/*");
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra("output", this.cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 4);
    }

    @Override
    public void modifySuccess() {

    }

    @Override
    public void modifyFail(String msg) {

    }

    @Override
    public void getInfoSuccess(PersonalInfoBean loginBean) {
        if (loginBean.getUser_data().getPortrait() != null){
            Picasso.with(this).load(loginBean.getUser_data().getPortrait()).into(roundImageView);
        }
        nickName.setText(loginBean.getUser_data().getNickName());
        tv_phone.setText(PhoneFormatCheckUtils.phoneAddress(loginBean.getUser_data().getMobile()));
    }

    @Override
    public void uploadImageSuccess(CollectDeleteBean msg) {
        toast(msg.getMessage());
        if (msg.getStatus() == 0) {
            isChangeSuccess = true;
        }

    }

    @Override
    public void uploadImageFail(String msg) {
        toast(msg + "");
    }

    @Override
    public void LoginOutSuccess(String msg) {
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        UiHelper.launcher(this, LoginActivity.class);

        Intent intent = new Intent();
        intent.putExtra("loginout",true);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void LoginOutFail(String msg) {
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
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
}
