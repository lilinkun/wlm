package com.wlm.wlm.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.CartOrderListAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.SureOrderContract;
import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.CartBuyBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.FaresBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.entity.WxInfoBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.interf.IWxResultListener;
import com.wlm.wlm.presenter.SureOrderPresenter;
import com.wlm.wlm.ui.CommendRecyclerView;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.PhoneFormatCheckUtils;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.wxapi.WXPayEntryActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/12/18.
 */

public class CartOrderActivity extends BaseActivity implements SureOrderContract, CartOrderListAdapter.OnDataGetFare {

    @BindView(R.id.rv_order)
    CommendRecyclerView recyclerView;
    @BindView(R.id.tv_consignee_name)
    TextView tv_consignee_name;
    @BindView(R.id.tv_consignee_phone)
    TextView tv_consignee_phone;
    @BindView(R.id.tv_consignee_address)
    TextView tv_consignee_address;
    @BindView(R.id.ll_address)
    LinearLayout linearLayout;
    @BindView(R.id.goods_total_price)
    TextView goods_total_price;
    @BindView(R.id.tv_use_point)
    TextView tv_use_point;
    @BindView(R.id.tv_fare)
    TextView tv_fare;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.tv_place_order)
    TextView tv_place_order;
    @BindView(R.id.tv_no_address)
    TextView tv_no_address;
    @BindView(R.id.tv_goods_consignee_name)
    TextView tv_goods_consignee_name;
    @BindView(R.id.tv_use_remarks)
    TextView tv_use_remarks;
    @BindView(R.id.tv_old_use_point)
    TextView tv_old_use_point;

    SureOrderPresenter sureOrderPresenter = new SureOrderPresenter();
    CartOrderListAdapter orderListAdapter;
    private AddressBean addressBean;
    private Dialog dialog;
    private RightNowBuyBean buyBean;
    private double isFare = 0;
    private double total;
    private Dialog payDialog;
    private String cartid;
    private String points = "";
    private boolean isClickBtn = false;
    private ArrayList<Double> doubles;
    private String orderid = "";

    private final int address_result = 0x123;
    private double mount = 0;
    private double personPoint = 0;

    private boolean isWxPay = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        sureOrderPresenter.onCreate(this,this);

        if (getIntent().getBundleExtra(WlmUtil.TYPEID).getInt("type") == 1) {
            cartid = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("CartId");
            sureOrderPresenter.cartBuy(ProApplication.SESSIONID(this));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//设置分割线

        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @OnClick({R.id.ll_back, R.id.rl_address, R.id.tv_place_order})
    public void onClick(View view) {

        if (!ButtonUtils.isFastDoubleClick(view.getId())) {
            switch (view.getId()) {
                case R.id.ll_back:
                    finish();
                    break;

                case R.id.rl_address:

                    UiHelper.launcherForResult(this, ChooseAddressActivity.class, address_result);

                    break;


                case R.id.tv_place_order:

                    if (addressBean == null) {
                        dialog.show();
                    } else {
                        int point = Integer.valueOf(tv_use_point.getText().toString());

                        sureOrderPresenter.sureSelfOrder( addressBean.getAddressID(),mount + "",tv_fare.getText().toString(),  point + "", tv_use_remarks.getText().toString(),ProApplication.SESSIONID(this));

                    }

                    break;

            }
        }
    }

    public void isAddressSuccess(AddressBean addressBean) {
        this.addressBean = addressBean;
        tv_consignee_name.setText(addressBean.getName());
        tv_consignee_phone.setText(PhoneFormatCheckUtils.phoneAddress(addressBean.getMobile()));
        tv_consignee_address.setText(addressBean.getAddressName() + addressBean.getAddress());
        tv_no_address.setVisibility(View.GONE);
        tv_goods_consignee_name.setVisibility(View.VISIBLE);
    }


    @Override
    public void getRightNowBuySuccess(RightNowBuyBean<CartBuyBean> buyBean) {
        this.buyBean = buyBean;
        orderListAdapter = new CartOrderListAdapter(this, buyBean, this);
        recyclerView.setAdapter(orderListAdapter);

        goods_total_price.setText(buyBean.getOrderAmount()+"");

        tv_use_point.setText(buyBean.getIntegral()+"");

        tv_fare.setText(buyBean.getShippingFree() +"");

        tv_old_use_point.setText(buyBean.getMoney2Balance()+"");

        mount = buyBean.getOrderAmount();

        tv_total.setText(mount +"");
        tv_total_price.setText("" + mount);

        if (buyBean.getAddress() != null && buyBean.getAddress().size() != 0) {
            isAddressSuccess(buyBean.getAddress().get(0));
        }
    }

    @Override
    public void getRightNowBuyFail(String msg) {
        toast(msg);
    }

    @Override
    public void getOrderGetFareSuccess(FareBean fareBean) {

        tv_fare.setText(isFare + "");
        int goodsTotalPrice = Integer.valueOf(goods_total_price.getText().toString().substring(1));
        total = Double.parseDouble(goods_total_price.getText().toString()) + Double.parseDouble(tv_fare.getText().toString()) + Integer.valueOf(tv_use_point.getText().toString());
        tv_total.setText("¥" + total);
        tv_total_price.setText("" + total);

    }

    @Override
    public void getOrderGetFareFail(String msg) {
        toast(msg);
    }

    @Override
    public void getOrderGetFaresSuccess(RightNowBuyBean fareBeans) {

        goods_total_price.setText(buyBean.getOrderAmount()+"");

        tv_use_point.setText(buyBean.getIntegral()+"");

        tv_fare.setText(buyBean.getShippingFree() +"");

        mount = buyBean.getOrderAmount();

        tv_total.setText(mount +"");
        tv_total_price.setText("" + mount);
        tv_old_use_point.setText(buyBean.getMoney2Balance()+"");

    }

    @Override
    public void getOrderGetFaresFail(String msg) {
        toast(msg);
    }

    @Override
    public void sureOrderSuccess(String orderid) {

        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.ORDERID,orderid);
        bundle.putString(WlmUtil.ORDERAMOUNT,mount+"");
        UiHelper.launcherForResultBundle(this,PayActivity.class,0x144,bundle);
    }

    @Override
    public void sureOrderFail(String msg) {

        if (!tv_place_order.isClickable()) {
            tv_place_order.setClickable(true);
        }
        toast(msg);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == address_result) {
                addressBean = (AddressBean) data.getBundleExtra(WlmUtil.TYPEID).getSerializable("address");
                linearLayout.setVisibility(View.VISIBLE);
                setAddress(addressBean);
                getFare(addressBean.getProv(), addressBean.getCity());
            }

            if (requestCode == 0x144){
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private void setAddress(AddressBean address) {

        tv_consignee_name.setText(address.getName());
        tv_consignee_phone.setText(PhoneFormatCheckUtils.phoneAddress(address.getMobile()));
        tv_consignee_address.setText(address.getAddressName() + address.getAddress());
    }



    private void getFare(String provinceId, String cityId) {

        sureOrderPresenter.getFares(cartid, addressBean.getAddressID(), ProApplication.SESSIONID(this));

    }

    @Override
    public void onPoint(int point, int position, int changeInt) {
        tv_use_point.setText("-" + point);
        total = (int) mount + Double.valueOf(tv_fare.getText().toString()) + Double.valueOf(tv_use_point.getText().toString());

        BigDecimal b = new BigDecimal(total);
        total = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        if (position != -1) {
            points = "";
            doubles.set(position, (double) changeInt);
            for (int i = 0; i < doubles.size(); i++) {
                if (points.equals("")) {
                    points = doubles.get(i) + "";
                } else {
                    points = points + "," + doubles.get(i);
                }
            }
        }


        tv_total.setText("¥" + total);
        tv_total_price.setText("¥" + total);

    }

    @Override
    public void onOrderFares() {

    }


}