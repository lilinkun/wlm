package com.wlm.wlm.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.PayContract;
import com.wlm.wlm.entity.BalanceBean;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.interf.IWxResultListener;
import com.wlm.wlm.interf.OnPasswordInputFinish;
import com.wlm.wlm.presenter.PayPresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.ui.PasswordView;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.wxapi.WXPayEntryActivity;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/10.
 */
public class PayActivity extends BaseActivity implements PayContract, IWxResultListener {

    private PayPresenter payPresenter = new PayPresenter();

    private String orderid;
    private String totalPrice;
    private Dialog payDialog;

    @BindView(R.id.check_wx)
    CheckBox check_wx;
    @BindView(R.id.check_self)
    CheckBox check_self;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.tv_balance_not_enough)
    TextView tv_balance_not_enough;
    @BindView(R.id.ll_pay_order)
    LinearLayout ll_pay_order;
    @BindView(R.id.titlebar)
    CustomTitleBar titlebar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        payPresenter.onCreate(this,this);

        WXPayEntryActivity.setPayListener(this);

        Bundle bundle = getIntent().getBundleExtra(WlmUtil.TYPEID);

        if (bundle != null){
            orderid = bundle.getString(WlmUtil.ORDERID);
            totalPrice = bundle.getString(WlmUtil.ORDERAMOUNT);
        }
        tv_amount.setText(totalPrice+"");
        payPresenter.getBalance(ProApplication.SESSIONID(this));

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

                final SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);

                if (check_wx.isChecked()) {

                    payPresenter.getWxPayOrderInfo(orderid, sharedPreferences.getString(WlmUtil.OPENID, ""), totalPrice + "", "11","1",ProApplication.SESSIONID(this));

                }else {
//                    toast("暂时不支持余额支付，不要点了");
                    if (tv_balance_not_enough != null && !tv_balance_not_enough.isShown()) {

                        View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_pay,null);



                        PopupWindow popupWindow = new PopupWindow(this);

                        popupWindow.setContentView(view1);
                        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());

                        popupWindow.showAsDropDown(titlebar);

                        final PasswordView passwordView = view1.findViewById(R.id.pwd_view);

                        passwordView.setOnFinishInput(new OnPasswordInputFinish() {
                            @Override
                            public void inputFinish() {
                                payPresenter.getPayOrderInfo(orderid, sharedPreferences.getString(WlmUtil.OPENID, ""), totalPrice + "", "", "0",passwordView.getStrPassword(), ProApplication.SESSIONID(PayActivity.this));
                            }

                            @Override
                            public void outfo() {

                            }

                            @Override
                            public void forgetPwd() {
                                UiHelper.launcher(PayActivity.this,ModifyPayActivity.class);
                            }
                        });

                        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                
                            }
                        });
//                        payDialog = new Dialog(this);
//                        payDialog.setContentView(view1);
//                        payDialog.show();
//
//                        payDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialog) {
//
//                            }
//                        });

//                        final EditText editText = (EditText) view1.findViewById(R.id.et_pay_psd);
//                        TextView textView = (TextView) view1.findViewById(R.id.tv_pay_price);
//                        Button btn_sure = (Button) view1.findViewById(R.id.btn_sure);
//
//                        textView.setText(totalPrice+"");
//
//                        btn_sure.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (!editText.getText().toString().isEmpty()){
//                                    payPresenter.getPayOrderInfo(orderid, sharedPreferences.getString(WlmUtil.OPENID, ""), totalPrice + "", "", "0",editText.getText().toString(), ProApplication.SESSIONID(PayActivity.this));
//                                }
//                            }
//                        });
                    }
                }

                break;

            case R.id.ll_back:

                setResult(RESULT_OK);
                finish();

                break;

        }
    }

    @Override
    public void sureWxOrderSuccess(WxInfo wxInfo) {
        WlmUtil.wxPay(wxInfo.getAppId(),wxInfo.getPartnerid(),wxInfo.getPrepayid(),wxInfo.getNonceStr(),wxInfo.getTimeStamp(),wxInfo.getPaySign(),this);
//        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
//        grouponOrderPresenter.getGoodsOrderInfo(ordersn,sharedPreferences.getString(WlmUtil.OPENID,""),totalPrice+"","11",ProApplication.SESSIONID(this));
    }

    @Override
    public void sureWxOrderFail(String msg) {
        toast(msg);
    }



    @Override
    public void sureOrderSuccess(String wxInfo) {
        payDialog.dismiss();

        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.PRICE,totalPrice);
        UiHelper.launcherBundle(this,PayResultActivity.class,bundle);

    }

    @Override
    public void sureOrderFail(String msg) {
        toast(msg);
    }

    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);

        String wlmCoin = numberFormat.format(balanceBean.getMoney5Balance());
        tv_balance.setText("唯乐币支付（剩余"+wlmCoin+"）");

        if (Double.valueOf(totalPrice) <= balanceBean.getMoney5Balance()) {
            tv_balance.setTextColor(getResources().getColor(R.color.pay_text));
            tv_balance.setTextSize(16);
            tv_balance_not_enough.setVisibility(View.GONE);

            check_wx.setChecked(false);
            check_self.setChecked(true);
        }else {

            check_wx.setChecked(true);
            check_self.setChecked(false);
        }
    }

    @Override
    public void getBalanceFail(String msg) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Bundle bundle = new Bundle();
            bundle.putInt("position",0);
            UiHelper.launcherBundle(this, OrderListActivity.class,bundle);
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setWxSuccess() {
        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.PRICE,totalPrice);
        UiHelper.launcherBundle(this,PayResultActivity.class,bundle);
    }

    @Override
    public void setWxFail() {
        toast("微信支付失败");
    }
}
