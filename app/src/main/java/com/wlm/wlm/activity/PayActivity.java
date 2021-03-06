package com.wlm.wlm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.PayContract;
import com.wlm.wlm.entity.BalanceBean;
import com.wlm.wlm.entity.OrderDetailAddressBean;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.entity.WxUserInfo;
import com.wlm.wlm.interf.IWxLoginListener;
import com.wlm.wlm.interf.IWxResultListener;
import com.wlm.wlm.interf.OnPasswordInputFinish;
import com.wlm.wlm.presenter.PayPresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.ui.PasswordView;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.wxapi.WXEntryActivity;
import com.wlm.wlm.wxapi.WXPayEntryActivity;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/10.
 */
public class PayActivity extends BaseActivity implements PayContract, IWxResultListener, IWxLoginListener {

    private PayPresenter payPresenter = new PayPresenter();

    private String orderid;
    private String totalPrice;
    private String where;
    private Dialog payDialog;
    private PopupWindow popupWindow;
    private String point;
    PasswordView passwordView;

    private OrderDetailAddressBean orderDetailBeans;

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
    @BindView(R.id.tv_point)
    TextView tv_point;

    IWXAPI iwxapi = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        payPresenter.onCreate(this, this);

        iwxapi = WXAPIFactory.createWXAPI(this, WlmUtil.APP_ID, true);
        iwxapi.registerApp(WlmUtil.APP_ID);

        WXEntryActivity.wxType(WlmUtil.WXTYPE_LOGIN);
        WXEntryActivity.setLoginListener(this);
        WXPayEntryActivity.setPayListener(this);

        Bundle bundle = getIntent().getBundleExtra(WlmUtil.TYPEID);

        if (bundle != null) {
            orderid = bundle.getString(WlmUtil.ORDERID);
            totalPrice = bundle.getString(WlmUtil.ORDERAMOUNT);
            where = bundle.getString(WlmUtil.WHERE);
        }
        tv_amount.setText(totalPrice + "");
        payPresenter.getBalance(ProApplication.SESSIONID(this));

        payPresenter.orderDetail(orderid, ProApplication.SESSIONID(this));

    }

    @OnClick({R.id.rl_wx, R.id.rl_self, R.id.tv_right_now_pay, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
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


                    if (sharedPreferences.getString(WlmUtil.OPENID, "") != null && !sharedPreferences.getString(WlmUtil.OPENID, "").equals("")) {

                        payPresenter.getWxPayOrderInfo(orderid, sharedPreferences.getString(WlmUtil.OPENID, ""), totalPrice + "", "11", "1", point, ProApplication.SESSIONID(this));

                    } else {

                        final SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";
                        req.state = "wechat_sdk_微信登录";
                        iwxapi.sendReq(req);
                    }
                } else {
//                    toast("暂时不支持余额支付，不要点了");
                    if (tv_balance_not_enough != null && !tv_balance_not_enough.isShown()) {

                        View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_pay, null);

                        popupWindow = new PopupWindow(this);

                        popupWindow.setContentView(view1);
                        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());

                        popupWindow.showAsDropDown(titlebar);

                        passwordView = view1.findViewById(R.id.pwd_view);

                        passwordView.setOnFinishInput(new OnPasswordInputFinish() {
                            @Override
                            public void inputFinish() {
                                payPresenter.getPayOrderInfo(orderid, sharedPreferences.getString(WlmUtil.OPENID, ""), totalPrice + "", "", "0", point, passwordView.getStrPassword(), ProApplication.SESSIONID(PayActivity.this));
                            }

                            @Override
                            public void outfo() {
                                popupWindow.dismiss();
                            }

                            @Override
                            public void forgetPwd() {
                                UiHelper.launcher(PayActivity.this, ModifyPayActivity.class);
                            }
                        });

                        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {

                            }
                        });
                    }
                }

                break;

            case R.id.ll_back:

                if (where.equals(WlmUtil.GOODS)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("status", 0);
                    bundle.putString("order_sn", orderid);
                    UiHelper.launcherForResultBundle(this, AllOrderActivity.class, 0x0987, bundle);
                }
                setResult(RESULT_OK);
                finish();

                break;

        }
    }

    @Override
    public void sureWxOrderSuccess(WxInfo wxInfo) {
        WlmUtil.wxPay(wxInfo.getAppId(), wxInfo.getPartnerid(), wxInfo.getPrepayid(), wxInfo.getNonceStr(), wxInfo.getTimeStamp(), wxInfo.getPaySign(), this);
//        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
//        grouponOrderPresenter.getGoodsOrderInfo(ordersn,sharedPreferences.getString(WlmUtil.OPENID,""),totalPrice+"","11",ProApplication.SESSIONID(this));
    }

    @Override
    public void sureWxOrderFail(String msg) {
        toast(msg);
    }


    @Override
    public void sureOrderSuccess(String wxInfo) {

//        payDialog.dismiss();

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.PRICE, totalPrice);
        bundle.putString(WlmUtil.ORDERID, orderid);
        bundle.putString(WlmUtil.GOODSTYPE, orderDetailBeans.getOrderType() + "");
        UiHelper.launcherForResultBundle(this, PayResultActivity.class, 0x0987, bundle);

    }

    @Override
    public void sureOrderFail(String msg) {
        toast(msg);

        /*if (passwordView != null){
            passwordView.
        }*/
    }

    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);

        String wlmCoin = numberFormat.format(balanceBean.getMoney5Balance());
        tv_balance.setText("唯乐币支付（剩余" + wlmCoin + "）");

        if (Double.valueOf(totalPrice) <= balanceBean.getMoney5Balance()) {
            tv_balance.setTextColor(getResources().getColor(R.color.pay_text));
            tv_balance.setTextSize(16);
            tv_balance_not_enough.setVisibility(View.GONE);

            check_wx.setChecked(false);
            check_self.setChecked(true);
        } else {

            check_wx.setChecked(true);
            check_self.setChecked(false);
        }
    }

    @Override
    public void getBalanceFail(String msg) {

    }

    @Override
    public void setDataSuccess(OrderDetailAddressBean orderDetailBeans) {
        this.orderDetailBeans = orderDetailBeans;
        this.point = orderDetailBeans.getIntegral() + "";
        if (orderDetailBeans.getIntegral() == 0) {
            tv_point.setVisibility(View.GONE);
        } else {
            tv_point.setText("+" + point + "积分");
        }
    }

    @Override
    public void setDataFail(String msg) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Bundle bundle = new Bundle();
//            bundle.putInt("position",0);
//            UiHelper.launcherBundle(this, OrderListActivity.class,bundle);

            Bundle bundle = new Bundle();
            bundle.putInt("status", 0);
            bundle.putString("order_sn", orderid);
            UiHelper.launcherForResultBundle(this, AllOrderActivity.class, 0x0987, bundle);
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setWxSuccess() {
        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.PRICE, totalPrice);
        bundle.putString(WlmUtil.ORDERID, orderid);
        bundle.putString(WlmUtil.GOODSTYPE, orderDetailBeans.getOrderType() + "");
        UiHelper.launcherForResultBundle(this, PayResultActivity.class, 0x0987, bundle);
    }

    @Override
    public void setWxFail() {
        toast("支付失败");
        Bundle bundle = new Bundle();
        bundle.putInt("status", 0);
        bundle.putString("order_sn", orderid);
        UiHelper.launcherForResultBundle(this, AllOrderActivity.class, 0x0987, bundle);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 0x0987) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void setWxLoginSuccess(WxUserInfo wxSuccess) {
        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
        sharedPreferences.edit().putString(WlmUtil.OPENID, wxSuccess.getOpenid()).commit();
        payPresenter.getWxPayOrderInfo(orderid, wxSuccess.getOpenid(), totalPrice + "", "11", "1", point, ProApplication.SESSIONID(this));

    }

    @Override
    public void setWxLoginFail(String msg) {
        toast("false" + msg);
    }
}
