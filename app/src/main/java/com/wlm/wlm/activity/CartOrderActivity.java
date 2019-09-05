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
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.FaresBean;
import com.wlm.wlm.entity.WxInfoBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.interf.IWxResultListener;
import com.wlm.wlm.presenter.SureOrderPresenter;
import com.wlm.wlm.ui.CommendRecyclerView;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.util.PhoneFormatCheckUtils;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.wxapi.WXPayEntryActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/12/18.
 */

public class CartOrderActivity extends BaseActivity implements SureOrderContract, CartOrderListAdapter.OnDataGetFare, IWxResultListener {

    @BindView(R.id.rv_order)
    CommendRecyclerView recyclerView;
    @BindView(R.id.check_zfb)
    CheckBox check_zfb;
    @BindView(R.id.check_wx)
    CheckBox check_wx;
    @BindView(R.id.check_self)
    CheckBox check_self;
    @BindView(R.id.tv_consignee_name)
    TextView tv_consignee_name;
    @BindView(R.id.tv_consignee_phone)
    TextView tv_consignee_phone;
    @BindView(R.id.tv_consignee_address)
    TextView tv_consignee_address;
    @BindView(R.id.ll_address)
    LinearLayout linearLayout;
    @BindView(R.id.tv_point)
    TextView tv_point;
    @BindView(R.id.tv_pay_self)
    TextView tv_pay_self;
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

    SureOrderPresenter sureOrderPresenter = new SureOrderPresenter();
    CartOrderListAdapter orderListAdapter;
    private AddressBean addressBean;
    private Dialog dialog;
    private BuyBean buyBean;
    private double isFare = 0;
    private String goodsids;
    private String storeIds;
    private String spec;
    private String num;
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
            loadDialog();
            cartid = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("CartId");
            sureOrderPresenter.cartBuy(cartid, ProApplication.SESSIONID(this));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//设置分割线

        recyclerView.setLayoutManager(linearLayoutManager);

        WXPayEntryActivity.setPayListener(this);
    }

    @OnClick({R.id.ll_back, R.id.rl_address, R.id.rl_zfb, R.id.rl_wx, R.id.rl_self, R.id.tv_place_order})
    public void onClick(View view) {

        if (!ButtonUtils.isFastDoubleClick(view.getId())) {
            switch (view.getId()) {
                case R.id.ll_back:
                    finish();
                    break;

                case R.id.rl_address:
                    if (!isWxPay) {
                        UiHelper.launcherForResult(this, ChooseAddressActivity.class, address_result);
                    }
                    break;

                case R.id.rl_zfb:
                    if (!isWxPay) {
                        check_zfb.setChecked(true);
                        check_wx.setChecked(false);
                        check_self.setChecked(false);
                    }
                    break;

                case R.id.rl_wx:
                    if (!isWxPay) {
                        if ( total != 0) {
                            check_wx.setChecked(true);
                            check_zfb.setChecked(false);
                            check_self.setChecked(false);
                        }
                    }
                    break;

                case R.id.rl_self:

                    if (!isWxPay) {
                        check_self.setChecked(true);
                        check_wx.setChecked(false);
                        check_zfb.setChecked(false);
                    }
                    break;

                case R.id.tv_place_order:

                    if (addressBean == null) {
                        dialog.show();
                    } else {
                        int point = Integer.valueOf(tv_use_point.getText().toString()) * -1;
                        if (!isWxPay) {
                            sureOrderPresenter.sureSelfOrder(points + "", cartid, tv_fare.getText().toString(), isFare + "", point + "", total + "", addressBean.getAddressID(), mount + "", ProApplication.SESSIONID(this));
                        } else {
                            sureOrderPresenter.setWxPay(orderid, total + "", "29", "1", "Android", "com.wlm.wlm", ProApplication.SESSIONID(this));
                        }
                    }

                    break;

            }
        }
    }

    @Override
    public void getRightNowBuySuccess(BuyBean buyBean) {
        this.buyBean = buyBean;
        orderListAdapter = new CartOrderListAdapter(this, buyBean, this);
        recyclerView.setAdapter(orderListAdapter);
        this.personPoint = buyBean.getUsermodel().getPoint();

        if (buyBean.getUseraddressmodel() == null) {
            dialog.show();
            linearLayout.setVisibility(View.GONE);
        } else {
            addressBean = buyBean.getUseraddressmodel();
            setAddress(addressBean);
        }

        double personPo = personPoint;
        doubles = new ArrayList<>();
        for (int i = 0; i < buyBean.getStoremodel().size(); i++) {
            double s = 0;
            mount += buyBean.getStoremodel().get(i).getTotal_amount();
            for (int j = 0; j < buyBean.getStoremodel().get(i).getGoodsList().size(); j++) {
                s += buyBean.getStoremodel().get(i).getGoodsList().get(j).getGive_integral() * buyBean.getStoremodel().get(i).getGoodsList().get(j).getNum();
            }
            if (s < personPo) {
                doubles.add(s);
                personPo = personPo - s;
            }else {
                doubles.add(personPo);
                personPo = 0;
            }
        }

        for (int i = 0; i < doubles.size(); i++) {
            if (points.equals("")) {
                points = doubles.get(i) + "";
            } else {
                points = points + "," + doubles.get(i);
            }
        }

        goods_total_price.setText("¥" + mount);
        String addressid = buyBean.getUseraddressmodel().getAddressID();

        sureOrderPresenter.getFares(cartid, addressid, ProApplication.SESSIONID(this));

        tv_point.setText(buyBean.getUsermodel().getPoint() + "积分");
        tv_pay_self.setText(buyBean.getUsermodel().getAmount() + "");

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
        if (buyBean.getUsermodel().getAmount() >= total) {
            check_self.setChecked(true);
            check_wx.setChecked(false);
        } else {
            check_self.setChecked(false);
            check_wx.setChecked(true);
        }
    }

    @Override
    public void getOrderGetFareFail(String msg) {
        toast(msg);
    }

    @Override
    public void getOrderGetFaresSuccess(ArrayList<FaresBean> fareBeans) {
        double farePrice = 0;
        for (int i = 0; i < fareBeans.size(); i++) {
            farePrice += fareBeans.get(i).getFare();
            if (fareBeans.get(i).getFare() == 0) {
                orderListAdapter.setFreight("免邮", i);
            } else {
                isFare = fareBeans.get(i).getFare();
                orderListAdapter.setFreight(isFare + "", i);
            }
        }
        tv_fare.setText(farePrice + "");
        total = mount + Double.parseDouble(tv_fare.getText().toString()) + Integer.valueOf(tv_use_point.getText().toString());

        BigDecimal b = new BigDecimal(total);
        total = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        tv_total.setText("¥" + total);
        tv_total_price.setText("¥" + total);
        if (buyBean.getUsermodel().getAmount() > total) {
            check_self.setChecked(true);
            check_wx.setChecked(false);
        } else {
            check_self.setChecked(false);
            check_wx.setChecked(true);
        }
    }

    @Override
    public void getOrderGetFaresFail(String msg) {
        toast(msg);
    }

    @Override
    public void sureOrderSuccess(CollectDeleteBean collectDeleteBean) {

        if (collectDeleteBean.getStatus() == 0) {

            orderid = collectDeleteBean.getMessage();

            if (check_wx.isChecked()) {
                isWxPay = true;
                tv_place_order.setText("正在提交");
                sureOrderPresenter.setWxPay(orderid, total + "", "29", "1", "Android", "com.wlm.wlm", ProApplication.SESSIONID(this));

            } else {
                View view = LayoutInflater.from(this).inflate(R.layout.dialog_pay, null);

                payDialog = new Dialog(this);
                payDialog.setContentView(view);
                payDialog.show();

                payDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (!isClickBtn) {
                            Bundle bundle = new Bundle();
                            bundle.putString("order_sn", orderid);
                            bundle.putInt("status", 0);
                            UiHelper.launcherBundle(CartOrderActivity.this, OrderListActivity.class, bundle);
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });

                final EditText editText = (EditText) view.findViewById(R.id.et_pay_psd);
                TextView textView = (TextView) view.findViewById(R.id.tv_pay_price);
                Button btn_sure = (Button) view.findViewById(R.id.btn_sure);

                textView.setText(tv_total_price.getText().toString());

                btn_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText.getText().toString().isEmpty()) {
                            sureOrderPresenter.selfPay(editText.getText().toString(), orderid, ProApplication.SESSIONID(CartOrderActivity.this));
                        }
                    }
                });

            }
        } else {
            toast(collectDeleteBean.getMessage());
        }

        if (!tv_place_order.isClickable()) {
            tv_place_order.setClickable(true);
        }
    }

    @Override
    public void sureOrderFail(String msg) {

        if (!tv_place_order.isClickable()) {
            tv_place_order.setClickable(true);
        }
        toast(msg);
    }

    @Override
    public void selfPaySuccess(CollectDeleteBean collectDeleteBean) {
        if (payDialog != null && payDialog.isShowing()) {
            if (collectDeleteBean.getStatus() == 0) {
                isClickBtn = true;
                if (payDialog != null && payDialog.isShowing()) {
                    payDialog.dismiss();
                }
//                toast(collectDeleteBean.getMessage());
                Bundle bundle = new Bundle();
                bundle.putString("price", total + "");
                UiHelper.launcherBundle(this, PayResultActivity.class, bundle);
                setResult(RESULT_OK);
                finish();
            } else {
                toast(collectDeleteBean.getMessage());
            }
        }
    }

    @Override
    public void selfPayFail(String msg) {
        toast(msg);
    }

    @Override
    public void cartBuySuccess(BuyBean buyBean) {

    }

    @Override
    public void cartBuyFail(String msg) {
        toast(msg);
    }

    @Override
    public void wxInfoSuccess(WxRechangeBean wxRechangeBean) {
        WxInfoBean wxInfoBean = wxRechangeBean.getData();
        WlmUtil.wxPay(wxInfoBean.getAppid(), wxInfoBean.getPartnerid(), wxInfoBean.getPrepayid(), wxInfoBean.getNoncestr(), wxInfoBean.getTimestamp(), wxInfoBean.getSign(), this);
    }

    @Override
    public void wxInfoFail(String msg) {
        toast(msg);
    }


    private void loadDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_address, null);
        dialog = new Dialog(this);
        dialog.setContentView(view);

        Button btn_add = (Button) view.findViewById(R.id.btn_add);
        Button btn_exit = (Button) view.findViewById(R.id.btn_exit);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.launcherForResult(CartOrderActivity.this, ChooseAddressActivity.class, address_result);
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
        }
    }

    private void setAddress(AddressBean address) {

        tv_consignee_name.setText(address.getName());
        tv_consignee_phone.setText(PhoneFormatCheckUtils.phoneAddress(address.getMobile()));
        tv_consignee_address.setText(address.getAddressName() + address.getAddress());
    }

    /*@Override
    public void onGetFare(String goodsids, String storeIds,String spec,String num) {
        this.goodsids = goodsids;
        this.storeIds = storeIds;
        this.spec = spec;
        this.num = num;
        getFare(buyBean.getUseraddressmodel().getProvince(),buyBean.getUseraddressmodel().getCity());
    }*/

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
        if (buyBean.getUsermodel().getAmount() > total) {
            check_self.setChecked(true);
            check_wx.setChecked(false);
        } else {
            check_self.setChecked(false);
            check_wx.setChecked(true);
        }
    }

    @Override
    public void onOrderFares() {

    }

    @Override
    public void setWxSuccess() {
        isClickBtn = true;
        if (payDialog != null && payDialog.isShowing()) {
            payDialog.dismiss();
        }
//                toast(collectDeleteBean.getMessage());
        Bundle bundle = new Bundle();
        bundle.putString("price", total + "");
        UiHelper.launcherBundle(this, PayResultActivity.class, bundle);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setWxFail() {
        toast("支付失败");
        Bundle bundle = new Bundle();
        bundle.putString("order_sn", orderid);
        bundle.putInt("status", 0);
        UiHelper.launcherBundle(CartOrderActivity.this, OrderListActivity.class, bundle);
        setResult(RESULT_OK);
        finish();
    }
}