package com.wlm.wlm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.OrderChildAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.AllOrderContract;
import com.wlm.wlm.entity.CountBean;
import com.wlm.wlm.entity.OrderDetailAddressBean;
import com.wlm.wlm.entity.WxInfoBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.interf.IWxResultListener;
import com.wlm.wlm.presenter.AllOrderPresenter;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.wxapi.WXPayEntryActivity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by LG on 2018/12/19.
 */

public class AllOrderActivity extends BaseActivity implements AllOrderContract, IWxResultListener {

    @BindView(R.id.order_sn)
    TextView order_sn;
    @BindView(R.id.order_date)
    TextView order_date;
    @BindView(R.id.goods_total_price)
    TextView goods_total_price;
    @BindView(R.id.tv_use_point)
    TextView tv_use_point;
    @BindView(R.id.tv_fare)
    TextView tv_fare;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.rv_order)
    RecyclerView recyclerView;
    @BindView(R.id.tv_consignee_address)
    TextView tv_consignee_address;
    @BindView(R.id.tv_consignee_name)
    TextView tv_consignee_name;
    @BindView(R.id.tv_consignee_phone)
    TextView tv_consignee_phone;
    @BindView(R.id.tv_no_address)
    TextView tv_no_address;
    @BindView(R.id.tv_exit_order)
    TextView tv_exit_order;
    @BindView(R.id.tv_pay_order)
    TextView tv_pay_order;
    @BindView(R.id.tv_order_pay_price)
    TextView tv_order_pay_price;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.rl_order)
    RelativeLayout rl_order;
    @BindView(R.id.ll_price_status)
    LinearLayout ll_price_status;
    @BindView(R.id.rl_bottom)
    RelativeLayout rl_bottom;
    @BindView(R.id.tv_pay_style)
    TextView tv_pay_style;
    @BindView(R.id.tv_pay_message)
    TextView tv_pay_message;
    @BindView(R.id.pay_date)
    TextView tv_pay_date;
    @BindView(R.id.send_out_date)
    TextView send_out_date;
    @BindView(R.id.logistics_information)
    TextView logistics_information;
    @BindView(R.id.iv_order_status)
    ImageView iv_order_status;
    @BindView(R.id.rl_need_integral)
    RelativeLayout rl_need_integral;
    @BindView(R.id.view_need_integral)
    View view_need_integral;
    @BindView(R.id.ll_point)
    LinearLayout ll_point;

    AllOrderPresenter allOrderPresenter = new AllOrderPresenter();
//    private OrderDetailBean orderDetailBean;
    private OrderDetailAddressBean orderDetailBeans;
    private PopupWindow payPopupWindow;
    private String orderId = "";
    private String orderSn = "";
    private int status = 0;
    private Dialog payDialog ;
    double payid = 0;
    double useIntegral = 0;
    double shipping_fee = 0;
    double order_amount = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_order;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        orderSn = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("order_sn");
        status = getIntent().getBundleExtra(WlmUtil.TYPEID).getInt("status");
        order_sn.setText(orderSn);

        ActivityUtil.addHomeActivity(this);

        allOrderPresenter.onCreate(this,this);

        allOrderPresenter.cartBuy(orderSn, ProApplication.SESSIONID(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        WXPayEntryActivity.setPayListener(this);

        if (status == 1 ){
            tv_pay_style.setText("未发货");
            tv_pay_message.setText("买家已付款，等待发货");
            rl_bottom.setVisibility(View.GONE);
        }else if (status == 2){
            tv_exit_order.setVisibility(View.GONE);
            tv_pay_order.setText("确认收货");
            tv_pay_style.setText("已发货");
            tv_pay_message.setText("您的商品正在运输中");
            iv_order_status.setImageResource(R.mipmap.ic_order_status_unover);
        }else if (status == 0){
            tv_exit_order.setText("取消订单");
            tv_pay_order.setText("立即付款");
            tv_pay_style.setText("未付款");
            tv_pay_message.setText("您的订单已提交，请尽快完成支付，确保宝贝早日到达您的身边。");
            iv_order_status.setImageResource(R.mipmap.ic_order_status_unpay);
        } else if(status == 4){
            tv_pay_style.setText("交易完成");
            tv_pay_message.setText("您的交易已经完成");
            tv_pay_order.setText("删除订单");
            ll_price_status.setVisibility(View.GONE);
            tv_exit_order.setVisibility(View.GONE);
            iv_order_status.setImageResource(R.mipmap.ic_order_status_over);
        } else if(status == 5){
            tv_pay_style.setText("交易失效");
            tv_pay_message.setText("");
            rl_bottom.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.ll_back)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                setResult(RESULT_OK);
                finish();

                break;
        }
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
    public void setDataSuccess(final OrderDetailAddressBean orderDetailBeans) {
//        this.orderDetailBean = orderDetailBeans.get(0);
        this.orderDetailBeans = orderDetailBeans;
//        OrderAdapter orderAdapter = new OrderAdapter(this,orderDetailBeans);

        OrderChildAdapter orderChildAdapter = new OrderChildAdapter(this,orderDetailBeans.getOrderDetail());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
        orderChildAdapter.setItemClickListener(new OrderChildAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int positionId) {
                Bundle bundle = new Bundle();
                bundle.putString("goodsid",orderDetailBeans.getOrderDetail().get(positionId).getGoodsId());
                UiHelper.launcherBundle(AllOrderActivity.this,SelfGoodsDetailActivity.class,bundle);
            }
        });
//
//        holder.recyclerView.setLayoutManager(linearLayoutManager);
//        holder.recyclerView.setAdapter(orderChildAdapter);

        recyclerView.setAdapter(orderChildAdapter); //添加自定义分割线

//        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.custom_divider));
//        recyclerView.addItemDecoration(divider);
        order_date.setText(orderDetailBeans.getCreateDate());


        order_amount += orderDetailBeans.getOrderAmount();

        BigDecimal b = new BigDecimal(order_amount);
        order_amount = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        useIntegral+= orderDetailBeans.getIntegral();
        shipping_fee += orderDetailBeans.getShippingFree();
        payid+= orderDetailBeans.getOrderAmount();

        if (useIntegral == 0){
            rl_need_integral.setVisibility(View.GONE);
            view_need_integral.setVisibility(View.GONE);
        }

        if (ll_point != null){
            ll_point.setVisibility(View.GONE);
        }

        goods_total_price.setText("¥"+payid + "");
        tv_use_point.setText("" + useIntegral);
        tv_fare.setText(shipping_fee + "");
        tv_total.setText("¥"+ order_amount + "");
        tv_order_pay_price.setText("¥"+ order_amount+"");

        tv_consignee_address.setText(orderDetailBeans.getAddressName() + orderDetailBeans.getAddress());
        tv_consignee_name.setText(orderDetailBeans.getConsignee());
        tv_consignee_phone.setText(orderDetailBeans.getMobile());
        tv_no_address.setVisibility(View.GONE);

        tv_pay_date.setText(orderDetailBeans.getPayDate()+"");
        send_out_date.setText(orderDetailBeans.getShippingFree()+"");
        logistics_information.setText(orderDetailBeans.getLgsName() + " " + orderDetailBeans.getLgsNumber());

        tv_exit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ButtonUtils.isFastDoubleClick()) {



                    new AlertDialog.Builder(AllOrderActivity.this).setTitle("温馨提示").setMessage("您确定要取消订单？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            allOrderPresenter.exitOrder(orderId, ProApplication.SESSIONID(AllOrderActivity.this));
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }

            }
        });
        tv_pay_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ButtonUtils.isFastDoubleClick()) {
                    tv_pay_order.setClickable(false);
                    if (status == 0) {

                        Bundle bundle = new Bundle();
                        bundle.putString(WlmUtil.ORDERID,orderDetailBeans.getOrderSn()+"");
                        bundle.putString(WlmUtil.ORDERAMOUNT,orderDetailBeans.getOrderAmount()+"");
                        bundle.putString(WlmUtil.WHERE,"allorder");
                        UiHelper.launcherForResultBundle(AllOrderActivity.this,PayActivity.class,0x1231,bundle);
//                        allOrderPresenter.getOrderData(ProApplication.SESSIONID(AllOrderActivity.this));
                    } else if (status == 2) {
                        allOrderPresenter.sureReceipt(orderDetailBeans.getOrderId()+"", ProApplication.SESSIONID(AllOrderActivity.this));
                    } else if (status == 4) {
                        allOrderPresenter.deleteOrder(orderDetailBeans.getOrderId()+"", ProApplication.SESSIONID(AllOrderActivity.this));
                    }
                }
            }
        });

    }

    @Override
    public void setDataFail(String msg) {
        toast(msg);
    }

    @Override
    public void exitOrderSuccess(String collectDeleteBean) {
        if (!tv_pay_order.isClickable()){
            tv_pay_order.setClickable(true);
        }
            toast("取消订单成功");
            setResult(RESULT_OK);
            finish();

    }

    @Override
    public void exitOrderFail(String msg) {
        if (!tv_pay_order.isClickable()){
            tv_pay_order.setClickable(true);
        }
        toast(msg);
    }

    @Override
    public void InfoAccountSuccess(CountBean orderDetailBean) {
        showPopup(orderDetailBean);
    }

    @Override
    public void InfoAccountFail(String msg) {
        toast(msg);
    }

    @Override
    public void selfPaySuccess(String collectDeleteBean) {
        if (!tv_pay_order.isClickable()){
            tv_pay_order.setClickable(true);
        }
            payDialog.dismiss();
            toast("支付成功");
            setResult(RESULT_OK);
            finish();
    }

    @Override
    public void selfPayFail(String msg) {
        if (!tv_pay_order.isClickable()){
            tv_pay_order.setClickable(true);
        }
        toast(msg);
    }

    @Override
    public void sureReceiptSuccess(String collectDeleteBean) {
        if (!tv_pay_order.isClickable()){
            tv_pay_order.setClickable(true);
        }
            toast("收货成功");
            setResult(RESULT_OK);
            finish();
    }

    @Override
    public void sureReceiptFail(String msg) {
        if (!tv_pay_order.isClickable()){
            tv_pay_order.setClickable(true);
        }
        toast(msg);
    }

    @Override
    public void wxInfoSuccess(WxRechangeBean wxRechangeBean) {
        WxInfoBean wxInfoBean = wxRechangeBean.getData();
        WlmUtil.wxPay(wxInfoBean.getAppid(),wxInfoBean.getPartnerid(),wxInfoBean.getPrepayid(),wxInfoBean.getNoncestr(),wxInfoBean.getTimestamp(),wxInfoBean.getSign(),this);
    }

    @Override
    public void wxInfoFail(String msg) {
        toast(msg);
    }


    public void showPopup(CountBean countBean){
        iv_bg.setVisibility(View.VISIBLE);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_order,null);
        payPopupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT,true);
        payPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        payPopupWindow.setFocusable(true);
        payPopupWindow.setOutsideTouchable(true);
        payPopupWindow.setAnimationStyle(R.style.popwin_anim_style);

        payPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                if (iv_bg != null){
                    iv_bg.setVisibility(View.GONE);
                }
            }
        });

        ImageView imageView = view.findViewById(R.id.iv_right_delete);
        final CheckBox check_self = view.findViewById(R.id.check_self);
        final CheckBox check_wx = view.findViewById(R.id.check_wx);

        RelativeLayout rl_self = view.findViewById(R.id.rl_self);
        RelativeLayout rl_wx = view.findViewById(R.id.rl_wx);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_pay_self = view.findViewById(R.id.tv_pay_self);
        CountdownView tv_time = view.findViewById(R.id.tv_time);
        TextView tv_right_now_pay = view.findViewById(R.id.tv_right_now_pay);
        tv_pay_self.setText(countBean.getAmount() + "");
        String endTime = orderDetailBeans.getPayDate();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        try {
            Date str = simpleDateFormat.parse(endTime);
            long effectiveTime = str.getTime();
            Date date = new Date(System.currentTimeMillis());
            long nowTime = date.getTime();

            long time = effectiveTime - nowTime;

//            int now = (int) (System.currentTimeMillis()/1000);
//            tv_time.setCountdownTime((int)(time/1000)-((int) (System.currentTimeMillis()/1000)-now),1+"");
            tv_time.start(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tv_right_now_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_self.isChecked()){
                    payPopupWindow.dismiss();
                    View view = LayoutInflater.from(AllOrderActivity.this).inflate(R.layout.dialog_pay,null);

                    payDialog = new Dialog(AllOrderActivity.this);
                    payDialog.setContentView(view);
                    payDialog.show();

                    payDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                        }
                    });

                }else if (check_wx.isChecked()){
                    payPopupWindow.dismiss();
//                    toast("你瞅我干啥，暂时不能微信支付类");
                    allOrderPresenter.setWxPay(orderDetailBeans.getOrderSn()+"",order_amount+"","29","1","Android","com.wlm.wlm",ProApplication.SESSIONID(AllOrderActivity.this));
                }else {
                    toast("请选择支付方式");
                }
            }
        });

        tv_price.setText("¥" + order_amount+"");

        rl_self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_self.setChecked(true);
                check_wx.setChecked(false);
            }
        });

        rl_wx.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                check_wx.setChecked(true);
                check_self.setChecked(false);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                payPopupWindow.dismiss();
            }
        });

        payPopupWindow.showAtLocation(rl_order, Gravity.CENTER | Gravity.CENTER, 0, 0);
    }

    @Override
    public void setWxSuccess() {
        if (!tv_pay_order.isClickable()){
            tv_pay_order.setClickable(true);
        }
        if (payDialog!= null && payDialog.isShowing()) {
            payDialog.dismiss();
        }
            toast("支付成功");
            setResult(RESULT_OK);
            finish();

    }

    @Override
    public void setWxFail() {
        if (!tv_pay_order.isClickable()){
            tv_pay_order.setClickable(true);
        }

        toast("支付失败");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == 0x1231){
            setResult(RESULT_OK);
            finish();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
