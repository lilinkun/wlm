package com.wlm.wlm.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.QRCodeUtil;
import com.wlm.wlm.util.WlmUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.internal.Util;

/**
 * Created by LG on 2019/9/18.
 */
public class MyQrCodeActivity extends BaseActivity {

    @BindView(R.id.tv_qr_name)
    TextView tv_qr_name;
    @BindView(R.id.ic_head)
    RoundImageView ic_head;
    @BindView(R.id.ic_qrcode)
    ImageView ic_qrcode;
    @BindView(R.id.ll_pic)
    LinearLayout ll_pic;

    IWXAPI iwxapi = null;

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

        Picasso.with(this).load(sharedPreferences.getString(WlmUtil.HEADIMGURL,"")).error(R.mipmap.ic_adapter_error).into(ic_head);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher1);
        Bitmap bitmap1 = QRCodeUtil.createQRCodeWithLogo("大王叫"+tv_qr_name.getText().toString()+"来巡山",bitmap);
        ic_qrcode.setImageBitmap(bitmap1);
    }


    @OnClick({R.id.tv_exit,R.id.ll_wx,R.id.ll_save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_exit:

                finish();


                break;

            case R.id.ll_wx:

                SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, Context.MODE_PRIVATE);

                WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
                miniProgramObj.webpageUrl = ProApplication.SHAREDIMG; // 兼容低版本的网页链接
                miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST;// 正式版:0，测试版:1，体验版:2
                miniProgramObj.userName = "gh_aa9e3dbf8fd0";     // 小程序原始id
                miniProgramObj.path = "/pages/Grouping/wantGrouping/wantGrouping?UserName=" + sharedPreferences.getString(WlmUtil.USERNAME,"");
                //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
                WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
                msg.title = "唯乐美商城";                    // 小程序消息title
                msg.description = "唯乐美商城";               // 小程序消息desc

                Bitmap thumbBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_adapter_error);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                msg.thumbData = baos.toByteArray();

//                msg.thumbData = getThumb();                      // 小程序消息封面图片，小于128k

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = "";
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
                iwxapi.sendReq(req);

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
        File f = new File("/sdcard/DCIM/Screenshots/", fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
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
    }

}
