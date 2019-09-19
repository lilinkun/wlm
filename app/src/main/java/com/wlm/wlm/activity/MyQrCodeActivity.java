package com.wlm.wlm.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.QRCodeUtil;
import com.wlm.wlm.util.WlmUtil;

import butterknife.BindView;
import butterknife.OnClick;

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(this);

        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN,MODE_PRIVATE);

        tv_qr_name.setText(sharedPreferences.getString(WlmUtil.ACCOUNT,""));

        Picasso.with(this).load(sharedPreferences.getString(WlmUtil.HEADIMGURL,"")).error(R.mipmap.ic_adapter_error).into(ic_head);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher1);
        Bitmap bitmap1 = QRCodeUtil.createQRCodeWithLogo("大王叫"+tv_qr_name.getText().toString()+"来巡山",bitmap);
        ic_qrcode.setImageBitmap(bitmap1);
    }


    @OnClick({R.id.tv_exit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_exit:

                finish();


                break;
        }
    }

}
