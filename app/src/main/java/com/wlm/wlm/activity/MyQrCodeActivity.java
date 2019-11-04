package com.wlm.wlm.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MyQrCodeContract;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.presenter.MyQrCodePresenter;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

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

    IWXAPI iwxapi = null;

    private MyQrCodePresenter myQrCodePresenter = new MyQrCodePresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(this);

        iwxapi = WXAPIFactory.createWXAPI(this,WlmUtil.APP_ID,true);
        iwxapi.registerApp(WlmUtil.APP_ID);

        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN,MODE_PRIVATE);

        tv_qr_name.setText(sharedPreferences.getString(WlmUtil.ACCOUNT,""));

        if (sharedPreferences.getString(WlmUtil.HEADIMGURL,"") != null && !sharedPreferences.getString(WlmUtil.HEADIMGURL,"").equals("")) {
            Picasso.with(this).load(sharedPreferences.getString(WlmUtil.HEADIMGURL, "")).error(R.mipmap.ic_adapter_error).into(ic_head);
        }

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher1);
//        Bitmap bitmap1 = QRCodeUtil.createQRCodeWithLogo("大王叫"+tv_qr_name.getText().toString()+"来巡山",bitmap);
//        ic_qrcode.setImageBitmap(bitmap1);

        myQrCodePresenter.onCreate(this,this);
        myQrCodePresenter.getUpdataData(ProApplication.SESSIONID(this));
    }


    @OnClick({R.id.tv_exit,R.id.ll_wx,R.id.ll_save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_exit:

                finish();


                break;

            case R.id.ll_wx:

                SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, Context.MODE_PRIVATE);

                Bitmap thumbBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_shared_wx);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbBmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                String path = "/pages/index/index?scene=" + sharedPreferences.getString(WlmUtil.USERNAME,"");
                WlmUtil.setShared(iwxapi,path,"唯乐美商城","唯乐美商城",baos.toByteArray());

                break;

            case R.id.ll_save:

                saveBitmap(ll_pic,"pic");

                break;
        }
    }


    public void saveBitmap(View v, String name) {


        String fileName = name + ".png";


        Bitmap bm = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        v.draw(canvas);
        String TAG = "TIKTOK";
        Log.e(TAG, "保存图片");
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() +  "/tencent/MicroMsg/WeiXin/";
        File f = new File( dir,fileName);
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
}
