package com.wlm.wlm.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.RechargeContract;
import com.wlm.wlm.entity.CountBean;
import com.wlm.wlm.entity.WxInfoBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.interf.IWxResultListener;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.RechargePresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.LzyydUtil;
import com.wlm.wlm.util.SoftKeyboardUtil;
import com.wlm.wlm.wxapi.WXPayEntryActivity;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/11/21.
 */

public class RechargeActivity extends BaseActivity implements OnTitleBarClickListener ,RechargeContract, IWxResultListener {

    @BindView(R.id.titlebar)
    CustomTitleBar customTitleBar;
    @BindView(R.id.text_100)
    TextView text_100;
    @BindView(R.id.text_200)
    TextView text_200;
    @BindView(R.id.text_500)
    TextView text_500;
    @BindView(R.id.text_1000)
    TextView text_1000;
    @BindView(R.id.text_2000)
    TextView text_2000;
    @BindView(R.id.text_5000)
    TextView text_5000;
    @BindView(R.id.check_box)
    CheckBox checkBox;
    @BindView(R.id.ll_edit_amount)
    LinearLayout ll_edit_amount;
    @BindView(R.id.recharge_commit)
    TextView recharge_commit;
    @BindView(R.id.et_amount)
    EditText et_amount;
    @BindView(R.id.tv_account)
    TextView tv_account;
    @BindView(R.id.tv_integral_balance)
    TextView tv_integral_balance;

    private ArrayList<TextView> textViews = new ArrayList<>();
    private RechargePresenter rechargePresenter = new RechargePresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        customTitleBar.SetOnTitleClickListener(this);

        rechargePresenter.onCreate(this,this);

        WXPayEntryActivity.setPayListener(this);
        /*final File file = new File(getExternalCacheDir(), "crop.jpg");
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            roundImageView.setImageBitmap(bm);
        }*/

        rechargePresenter.getOrderData(ProApplication.SESSIONID(this));

        tv_account.setText(MainFragmentActivity.username);

        textViews.add(text_100);
        textViews.add(text_200);
        textViews.add(text_500);
        textViews.add(text_1000);
        textViews.add(text_2000);
        textViews.add(text_5000);

        checkBox.setChecked(true);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

    }

    @OnClick({R.id.recharge_commit,R.id.text_100,R.id.text_200,R.id.text_500,R.id.text_1000,R.id.text_2000,R.id.text_5000,R.id.et_amount})
    public void onClick(View view){
        switch (view.getId()){

            case R.id.et_amount:
                SoftKeyboardUtil.openKeybord(et_amount,this);
                changeStatus(recharge_commit);
                ll_edit_amount.setBackgroundResource(R.drawable.rechange_unframe);
                break;
            case R.id.recharge_commit:

                if (!et_amount.getText().toString().trim().isEmpty() && Integer.valueOf(et_amount.getText().toString()) > 0) {
                    rechargePresenter.setWxPay("", et_amount.getText().toString(),"29","0","Android","com.wlm.wlm", ProApplication.SESSIONID(this));
                }

                break;

            case R.id.text_100:


                changeStatus(text_100);

                break;

            case R.id.text_200:

                changeStatus(text_200);
                break;

            case R.id.text_500:

                changeStatus(text_500);
                break;

            case R.id.text_1000:

                changeStatus(text_1000);
                break;

            case R.id.text_2000:

                changeStatus(text_2000);
                break;

            case R.id.text_5000:

                changeStatus(text_5000);
                break;
        }
    }

    private void changeStatus(TextView textView){
        ll_edit_amount.setBackgroundResource(R.drawable.rechange_frame);
        for (int i = 0;i<textViews.size();i++){
            if (textViews.get(i).getId() == textView.getId()){
                textViews.get(i).setSelected(true);
                et_amount.setText(textViews.get(i).getText().toString().substring(0,textViews.get(i).getText().length()-1));
            }else {
                textViews.get(i).setSelected(false);
            }
        }
    }


    @Override
    public void onBackClick() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            setResult(RESULT_OK);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setReChargeSuccess(WxRechangeBean wxRechangeBean) {
        WxInfoBean wxInfoBean = wxRechangeBean.getData();
        LzyydUtil.wxPay(wxInfoBean.getAppid(),wxInfoBean.getPartnerid(),wxInfoBean.getPrepayid(),wxInfoBean.getNoncestr(),wxInfoBean.getTimestamp(),wxInfoBean.getSign(),this);
    }



    @Override
    public void setReChargeFail(String msg) {
        toast(msg);
    }

    @Override
    public void InfoAccountSuccess(CountBean countBean) {
        tv_integral_balance.setText("¥"+countBean.getAmount() + "元");
    }

    @Override
    public void InfoAccountFail(String msg) {

    }

    @Override
    public void setWxSuccess() {
        toast("充值成功");
        rechargePresenter.getOrderData(ProApplication.SESSIONID(this));
    }

    @Override
    public void setWxFail() {
        toast("充值失败");
    }
}
