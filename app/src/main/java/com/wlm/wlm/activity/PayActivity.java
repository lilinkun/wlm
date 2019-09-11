package com.wlm.wlm.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.PayContract;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.presenter.PayPresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/10.
 */
public class PayActivity extends BaseActivity implements PayContract {

    private PayPresenter payPresenter = new PayPresenter();

    private String orderid;
    private String totalPrice;

    @BindView(R.id.check_wx)
    CheckBox check_wx;
    @BindView(R.id.check_self)
    CheckBox check_self;
    @BindView(R.id.tv_amount)
    TextView tv_amount;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        payPresenter.onCreate(this,this);

        Bundle bundle = getIntent().getBundleExtra(WlmUtil.TYPEID);

        if (bundle != null){
            orderid = bundle.getString(WlmUtil.ORDERID);
            totalPrice = bundle.getString(WlmUtil.ORDERAMOUNT);
        }
        tv_amount.setText(totalPrice+"");

    }

    @OnClick({R.id.rl_wx,R.id.rl_self,R.id.tv_right_now_pay,R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_wx:

                check_wx.setChecked(true);
                check_self.setChecked(false);
                break;

            case R.id.rl_self:

                check_wx.setChecked(false);
                check_self.setChecked(true);

                break;

            case R.id.tv_right_now_pay:


                if (check_wx.isChecked()) {

                    SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);

                    payPresenter.getPayOrderInfo(orderid, sharedPreferences.getString(WlmUtil.OPENID, ""), totalPrice + "", "11", ProApplication.SESSIONID(this));

                }else {
                    toast("暂时不支持余额支付，不要点了");
                }

                break;

            case R.id.ll_back:

                finish();

                break;

        }
    }

    @Override
    public void sureOrderSuccess(WxInfo wxInfo) {
        WlmUtil.wxPay(wxInfo.getAppId(),wxInfo.getPartnerid(),wxInfo.getPrepayid(),wxInfo.getNonceStr(),wxInfo.getTimeStamp(),wxInfo.getPaySign(),this);
//        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
//        grouponOrderPresenter.getGoodsOrderInfo(ordersn,sharedPreferences.getString(WlmUtil.OPENID,""),totalPrice+"","11",ProApplication.SESSIONID(this));
    }

    @Override
    public void sureOrderFail(String msg) {
        toast(msg);
    }
}
