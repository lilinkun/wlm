package com.wlm.wlm.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MyQrCodeContract;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.GrouponDetailBean;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.presenter.MyQrCodePresenter;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.wlm.wlm.ui.CustomRoundImageView;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by LG on 2019/9/18.
 */
public class MyQrCodeActivity extends BaseActivity implements MyQrCodeContract {

    @BindView(R.id.tv_qr_name)
    TextView tv_qr_name;
    @BindView(R.id.ic_head)
    RoundImageView ic_head;
    @BindView(R.id.ic_qrcode)
    ImageView ic_qrcode;
    @BindView(R.id.ll_pic)
    LinearLayout ll_pic;
    @BindView(R.id.ll_shared)
    LinearLayout ll_shared;
    @BindView(R.id.ll_qrcode)
    LinearLayout ll_qrcode;
    @BindView(R.id.criv_shared)
    CustomRoundAngleImageView criv_shared;
    @BindView(R.id.tv_goods_price)
    TextView tv_goods_price;
    @BindView(R.id.tv_goods_title)
    TextView tv_goods_title;
    @BindView(R.id.iv_qrcode_goods)
    ImageView iv_qrcode_goods;

    IWXAPI iwxapi = null;

    private static final int REQUEST_CODE_CONTACT = 101;
    private MyQrCodePresenter myQrCodePresenter = new MyQrCodePresenter();
    String shared = "";
    private GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean = null;
    private GrouponDetailBean grouponDetailBean = null;
    String path = "";
    String goodsname = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(this);

        iwxapi = WXAPIFactory.createWXAPI(this, WlmUtil.APP_ID, true);
        iwxapi.registerApp(WlmUtil.APP_ID);

        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);

        tv_qr_name.setText(sharedPreferences.getString(WlmUtil.ACCOUNT, ""));

        if (sharedPreferences.getString(WlmUtil.HEADIMGURL, "") != null && !sharedPreferences.getString(WlmUtil.HEADIMGURL, "").equals("")) {
            Picasso.with(this).load(sharedPreferences.getString(WlmUtil.HEADIMGURL, "")).error(R.mipmap.ic_adapter_error).into(ic_head);
        }

        if (getIntent().getBundleExtra(WlmUtil.TYPEID) != null && getIntent().getBundleExtra(WlmUtil.TYPEID).getString("shared") != null){
            String url = "";
            shared = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("shared");
            ll_qrcode.setVisibility(View.GONE);
            ll_shared.setVisibility(View.VISIBLE);

            if (shared.equals("group")){
                grouponDetailBean = (GrouponDetailBean) getIntent().getBundleExtra(WlmUtil.TYPEID).getSerializable(WlmUtil.GOODS);
                Picasso.with(this).load(ProApplication.HEADIMG + grouponDetailBean.getGoodsImg()).into(criv_shared);
                tv_goods_price.setText("￥" + grouponDetailBean.getPrice());
                tv_goods_title.setText(grouponDetailBean.getGoodsName());

                String groupShareStr = ProApplication.SHAREDIMG + "Erm/Team?TeamId=" + grouponDetailBean.getTeamId()
                        + "&UserName=" + sharedPreferences.getString(WlmUtil.USERNAME, "");
                final String encodedURL;
                try {
                    encodedURL = URLEncoder.encode(groupShareStr, "UTF-8");
                    url = ProApplication.SHAREDIMG + "api/Qr/Get?size_url=5&type=1&url=" + encodedURL;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Picasso.with(this).load(url).into(iv_qrcode_goods);
            }else if (shared.equals("goods")){
                goodsDetailBean = (GoodsDetailInfoBean<ArrayList<GoodsChooseBean>>) getIntent().getBundleExtra(WlmUtil.TYPEID).getSerializable(WlmUtil.GOODS);

                Picasso.with(this).load(ProApplication.HEADIMG + goodsDetailBean.getGoodsImg()).into(criv_shared);
                tv_goods_price.setText("￥" + goodsDetailBean.getPrice());
                tv_goods_title.setText(goodsDetailBean.getGoodsName());

                String goodsShareStr = ProApplication.SHAREDIMG + "Erm/Goods?GoodsId=" + goodsDetailBean.getGoodsId()
                        + "&UserName=" + sharedPreferences.getString(WlmUtil.USERNAME, "");

                try {
                    final String encodedURL = URLEncoder.encode(goodsShareStr, "UTF-8");
                    url = ProApplication.SHAREDIMG + "api/Qr/Get?size_url=5&type=1&url="+encodedURL;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Picasso.with(this).load(url).into(iv_qrcode_goods);
            }
        }
        myQrCodePresenter.onCreate(this, this);
        myQrCodePresenter.getUpdataData(ProApplication.SESSIONID(this));

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher1);
//        Bitmap bitmap1 = QRCodeUtil.createQRCodeWithLogo("大王叫"+tv_qr_name.getText().toString()+"来巡山",bitmap);
//        ic_qrcode.setImageBitmap(bitmap1);

    }


    @OnClick({R.id.tv_exit, R.id.ll_wx, R.id.ll_save,R.id.ll_friend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_exit:

                finish();


                break;

            case R.id.ll_wx:

                if (shared.equals("")) {

                    SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, Context.MODE_PRIVATE);

//                    Bitmap thumbBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_shared_wx);
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    thumbBmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                    final String path = "/pages/index/index?scene=" + sharedPreferences.getString(WlmUtil.USERNAME, "");

                    Picasso.with(this).load(ProApplication.SHAREDMEIMG).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {


                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                            WlmUtil.setShared(iwxapi, path, "唯乐美商城", "唯乐美商城", baos.toByteArray(), 0);

                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });



                }else{

                    SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
                    String url = "";
                    if (shared.equals("goods")){
                        goodsname = goodsDetailBean.getGoodsName();
                        url = ProApplication.HEADIMG + goodsDetailBean.getGoodsImg();
                        path = "/pages/cart/productdetail/productdetail?GoodsId=" + goodsDetailBean.getGoodsId() + "&UserName=" + sharedPreferences.getString(WlmUtil.USERNAME, "");
                    }else if (shared.equals("group")){
                        goodsname = grouponDetailBean.getGoodsName();
                        url = ProApplication.HEADIMG + grouponDetailBean.getGoodsImg();
                        path = "/pages/Grouping/wantGrouping/wantGrouping?TeamId=" + grouponDetailBean.getTeamId() + "&UserName=" + sharedPreferences.getString(WlmUtil.USERNAME, "");
                    }

                    Picasso.with(this).load(url).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {


                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                            WlmUtil.setShared(iwxapi, path, goodsname, goodsname, baos.toByteArray(),0);

                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
                }



                break;

            case R.id.ll_save:


                if (Build.VERSION.SDK_INT >= 23) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (EasyPermissions.hasPermissions(this, perms)) {
                        saveBitmap(ll_pic, "pic_");
                    }else {
                        EasyPermissions.requestPermissions(this, "使用 BGAUpdateDemo 需要授权读写外部存储权限!", REQUEST_CODE_CONTACT, perms);
                    }

                }else {
                    saveBitmap(ll_pic, "pic_");
                }

                break;

            case R.id.ll_friend:

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        SharedPreferences sharedPreferences1 = getSharedPreferences(WlmUtil.LOGIN, Context.MODE_PRIVATE);

                        Bitmap thumbBmp1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_shared_wx);
                        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                        thumbBmp1.compress(Bitmap.CompressFormat.JPEG, 50, baos1);

                        String path1 = "/pages/index/index?scene=" + sharedPreferences1.getString(WlmUtil.USERNAME, "");
                        WlmUtil.setShared1(iwxapi, path1, "唯乐美商城", "唯乐美商城", baos1.toByteArray(),1);

                    }
                }).start();
                break;
        }
    }


    public void saveBitmap(View v, String name) {

        String fileName = name + "_"+(new Date()).getTime()+".png";


        Bitmap bm = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        v.draw(canvas);
        String TAG = "TIKTOK";
        Log.e(TAG, "保存图片");
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        File f = new File(dir, fileName);
//        File f = new File("/sdcard/DCIM/Screenshots/", fileName);
        if (f.exists()) {
            f.delete();
        }
        try {

            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        toast("保存成功");

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(f);
        intent.setData(uri);
        sendBroadcast(intent);
    }

    @Override
    public void getQrCodeSuccess(LoginBean loginBean) {
        String sharedStr = loginBean.getSharedErm();
        Picasso.with(this).load(ProApplication.BANNERIMG + loginBean.getSharedErm()).error(R.mipmap.ic_adapter_error).into(ic_qrcode);
    }

    @Override
    public void getQrCodeFail(String msg) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_CONTACT:

                saveBitmap(ll_pic, "pic");

                break;
        }
    }
}
