package com.wlm.wlm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.RechargeContract;
import com.wlm.wlm.entity.BalanceBean;
import com.wlm.wlm.entity.CountBean;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.entity.WxInfoBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.interf.IWxResultListener;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.RechargePresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.util.SoftKeyboardUtil;
import com.wlm.wlm.wxapi.WXPayEntryActivity;

import org.w3c.dom.Text;

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
    @BindView(R.id.ll_edit_amount)
    LinearLayout ll_edit_amount;
    @BindView(R.id.recharge_commit)
    TextView recharge_commit;
    @BindView(R.id.et_amount)
    EditText et_amount;
    @BindView(R.id.tv_account)
    TextView tv_account;
    @BindView(R.id.tv_head)
    RoundImageView tv_head;
    @BindView(R.id.tv_integral)
    TextView tv_integral;
    @BindView(R.id.tv_charge_amount)
    TextView tv_charge_amount;

    private ArrayList<TextView> textViews = new ArrayList<>();
    private RechargePresenter rechargePresenter = new RechargePresenter();
    private BalanceBean balanceBean;

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


        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN,MODE_PRIVATE);
        balanceBean = (BalanceBean) getIntent().getBundleExtra(WlmUtil.TYPEID).getSerializable(WlmUtil.BALANCEBEAN);

        tv_account.setText(sharedPreferences.getString(WlmUtil.ACCOUNT,""));
        if (!sharedPreferences.getString(WlmUtil.HEADIMGURL,"").isEmpty()) {
            Picasso.with(this).load(sharedPreferences.getString(WlmUtil.HEADIMGURL, "")).error(R.mipmap.ic_adapter_error).into(tv_head);
        }
        tv_integral.setText((int)balanceBean.getMoney2Balance()+"");

//        rechargePresenter.getBalance(ProApplication.SESSIONID(this));

        textViews.add(text_100);
        textViews.add(text_200);
        textViews.add(text_500);


        et_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("") && s.toString().trim().length() > 0) {
                    tv_charge_amount.setText(Integer.valueOf(s.toString()) * 10 + "");
                }else {
                    tv_charge_amount.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick({R.id.recharge_commit,R.id.text_100,R.id.text_200,R.id.text_500,R.id.et_amount})
    public void onClick(View view){
        switch (view.getId()){

            case R.id.et_amount:
                SoftKeyboardUtil.openKeybord(et_amount,this);
                changeStatus(recharge_commit);
                ll_edit_amount.setBackgroundResource(R.drawable.rechange_unframe);
                break;
            case R.id.recharge_commit:

                if (!et_amount.getText().toString().trim().isEmpty() && Integer.valueOf(et_amount.getText().toString()) > 0) {
                    rechargePresenter.setWxPay("0", et_amount.getText().toString(), ProApplication.SESSIONID(this));
                }else {
                    toast("请输入金额");
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

        if (et_amount != null && et_amount.getText().toString().trim().length() > 0) {
            int a = Integer.valueOf(et_amount.getText().toString()) * 10;
            tv_charge_amount.setText(a + "");
        }

    }


    @Override
    public void onBackClick() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(WlmUtil.BALANCEBEAN,balanceBean);
        Intent intent = new Intent();
        intent.putExtra(WlmUtil.TYPEID,bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            Bundle bundle = new Bundle();
            bundle.putSerializable(WlmUtil.BALANCEBEAN,balanceBean);
            Intent intent = new Intent();
            intent.putExtra(WlmUtil.TYPEID,bundle);
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setReChargeSuccess(WxInfo wxInfoBean) {
//        WlmUtil.wxPay(wxInfo.getAppId(),wxInfo.getPartnerid(),wxInfo.getPrepayid(),wxInfo.getNonceStr(),wxInfo.getTimeStamp(),wxInfo.getPaySign(),this);
        WlmUtil.wxPay(wxInfoBean.getAppId(),wxInfoBean.getPartnerid(),wxInfoBean.getPrepayid(),wxInfoBean.getNonceStr(),wxInfoBean.getTimeStamp(),wxInfoBean.getPaySign(),this);
    }



    @Override
    public void setReChargeFail(String msg) {
        toast(msg);
    }

    @Override
    public void InfoAccountSuccess(BalanceBean countBean) {
        this.balanceBean = countBean;
        tv_integral.setText((int)countBean.getMoney2Balance()+"");
    }

    @Override
    public void InfoAccountFail(String msg) {

    }

    @Override
    public void setWxSuccess() {
        toast("充值成功");
        et_amount.setText("");
        rechargePresenter.getBalance(ProApplication.SESSIONID(this));
    }

    @Override
    public void setWxFail() {
        toast("充值失败");
    }

}
