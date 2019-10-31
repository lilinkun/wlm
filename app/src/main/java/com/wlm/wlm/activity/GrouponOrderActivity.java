package com.wlm.wlm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.GrouponOrderContract;
import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.entity.RightNowGoodsBean;
import com.wlm.wlm.entity.WxInfo;
import com.wlm.wlm.interf.IOrderChoosePayTypeListener;
import com.wlm.wlm.interf.IWxResultListener;
import com.wlm.wlm.presenter.GrouponOrderPresenter;
import com.wlm.wlm.ui.OrderPopupLayout;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.PhoneFormatCheckUtils;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.wxapi.WXPayEntryActivity;

import java.util.ArrayList;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;

public class GrouponOrderActivity extends BaseActivity implements GrouponOrderContract, IOrderChoosePayTypeListener, IWxResultListener {

    @BindView(R.id.tv_place_order)
    TextView tv_place_order;
    @BindView(R.id.iv_pay_type)
    ImageView iv_pay_type;
    @BindView(R.id.tv_pay_type)
    TextView  tv_pay_type;
    @BindView(R.id.tv_consignee_name)
    TextView tv_consignee_name;
    @BindView(R.id.tv_consignee_phone)
    TextView tv_consignee_phone;
    @BindView(R.id.tv_consignee_address)
    TextView tv_consignee_address;
    @BindView(R.id.iv_goods_pic)
    ImageView iv_goods_pic;
    @BindView(R.id.tv_goods_title)
    TextView tv_goods_title;
    @BindView(R.id.tv_goods_price)
    TextView tv_goods_price;
    @BindView(R.id.tv_coupon_price)
    TextView tv_coupon_price;
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
    @BindView(R.id.rl_goods_detail)
    RelativeLayout rl_goods_detail;
    @BindView(R.id.tv_no_address)
    TextView tv_no_address;
    @BindView(R.id.tv_goods_spec1)
    TextView tv_goods_spec1;
    @BindView(R.id.tv_goods_spec2)
    TextView tv_goods_spec2;
    @BindView(R.id.tv_goods_consignee_name)
    TextView tv_goods_consignee_name;
    @BindView(R.id.tv_use_remarks)
    TextView tv_use_remarks;
    @BindView(R.id.tv_old_use_point)
    TextView tv_old_use_point;
    @BindView(R.id.rl_need_integral)
    RelativeLayout rl_need_integral;
    @BindView(R.id.view_need_integral)
    View view_need_integral;

    private GrouponOrderPresenter grouponOrderPresenter = new GrouponOrderPresenter();

    private OrderPopupLayout rootView = null;
    public static int pay_type_position = 1;
    private GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsListBean = null;
    private GoodsChooseBean goodsChooseBean = null;
    private AddressBean addressBean = null;
    private double totalPrice = 0;
    private FareBean fareBean = null;
    private RightNowBuyBean<RightNowGoodsBean> rightNowBuyBean = null;
    private RightNowGoodsBean rightNowGoodsBean = null;
    private int goodsnum = 1;
    private String fareStr ;
    private int type = 1;
    private double surplus_balance;
    private double surplus_integral;
    private final int address_result = 0x123;

    @Override
    public int getLayoutId() {
        return R.layout.activity_groupon_order;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarColor(this,getResources().getColor(R.color.white));

        grouponOrderPresenter.onCreate(this,this);

        rootView = new OrderPopupLayout(this);
//        grouponOrderPresenter.getAddress("1","200", ProApplication.SESSIONID(this));

        WXPayEntryActivity.setPayListener(this);

        Bundle bundle = getIntent().getBundleExtra(WlmUtil.TYPEID);

        rl_goods_detail.setBackgroundResource(R.drawable.shape_info_pop);

        if (bundle != null ){

            String goodsid = bundle.getString(WlmUtil.GOODSID);

//            type = bundle.getString(WlmUtil.TYPE);

            goodsnum = bundle.getInt(WlmUtil.GOODSNUM);

            String attrs_id = bundle.getString(WlmUtil.ATTRID);

            grouponOrderPresenter.rightNowBuyInfo(goodsid,attrs_id,goodsnum+"",ProApplication.SESSIONID(this));
        }


    }

    public void isAddressSuccess(AddressBean addressBean) {
        this.addressBean = addressBean;
        tv_consignee_name.setText(addressBean.getName());
        tv_consignee_phone.setText(PhoneFormatCheckUtils.phoneAddress(addressBean.getMobile()));
        tv_consignee_address.setText(addressBean.getAddressName() + addressBean.getAddress());

//        grouponOrderPresenter.getFare(rightNowGoodsBean.getGoodsId(),addressBean.getAddressID(),rightNowGoodsBean.getGoodsNumber()+"",ProApplication.SESSIONID(this));
    }

    private void getFare(String fareStr){
        this.fareStr = fareStr;
        tv_fare.setText(fareStr);
        /*if (type.equals(WlmUtil.VIP)) {
            totalPrice = rightNowGoodsBean.getPrice() * 1 - Double.valueOf(fareStr);
        }else {
            totalPrice = rightNowGoodsBean.getPrice() * 1 - Double.valueOf(fareStr) - rightNowBuyBean.getIntegral();
        }*/

        totalPrice = rightNowBuyBean.getOrderAmount();
        tv_total.setText("¥" + totalPrice);
        tv_total_price.setText(rightNowBuyBean.getOrderAmount() + "");
    }

    @Override
    public void getRightNowBuyInfoSuccess(RightNowBuyBean<RightNowGoodsBean> orderListBeans) {

        this.rightNowBuyBean = orderListBeans;
        rightNowGoodsBean = rightNowBuyBean.getList().get(0);
        type = rightNowGoodsBean.getGoodsType();
        if (rightNowGoodsBean.getGoodsAttr() != null && rightNowGoodsBean.getGoodsAttr().size() > 0){
            goodsChooseBean = rightNowGoodsBean.getGoodsAttr().get(0);
        }

        surplus_balance = rightNowBuyBean.getMoney5Balance();
        surplus_integral = rightNowBuyBean.getMoney2Balance();

        getFare(rightNowBuyBean.getShippingFree()+"");

        if (rightNowBuyBean.getAddress() != null && rightNowBuyBean.getAddress().size() != 0) {
            isAddressSuccess(rightNowBuyBean.getAddress().get(0));
            tv_no_address.setVisibility(View.GONE);
            tv_goods_consignee_name.setVisibility(View.VISIBLE);
        }

        if (addressBean != null){
            isAddressSuccess(addressBean);
        }
        tv_old_use_point.setText(surplus_integral + "");
        tv_goods_title.setText(rightNowGoodsBean.getGoodsName());
        Picasso.with(this).load(ProApplication.HEADIMG + rightNowGoodsBean.getGoodsImg()).error(R.mipmap.ic_adapter_error).into(iv_goods_pic);
        tv_goods_price.setText(rightNowGoodsBean.getPrice()+"");
        tv_coupon_price.setText("X" + goodsnum);

        String price = "¥" + rightNowGoodsBean.getPrice() + "";
        goods_total_price.setText(price);
        if (rightNowGoodsBean.getIntegral() > 0) {
            tv_use_point.setText(rightNowBuyBean.getIntegral() + "");
        }else {
            rl_need_integral.setVisibility(View.GONE);
            view_need_integral.setVisibility(View.GONE);
        }

        if (rightNowGoodsBean.getQty() == 2){
            tv_goods_spec1.setText(rightNowGoodsBean.getGoodsAttr().get(0).getSpec1() + " , ");
            tv_goods_spec2.setText(rightNowGoodsBean.getGoodsAttr().get(0).getSpec2());
        }else if (rightNowGoodsBean.getQty() == 1){
            tv_goods_spec1.setText(rightNowGoodsBean.getGoodsAttr().get(0).getSpec1());
        }
    }

    @Override
    public void getRightNowBuyInfoFail(String msg) {

    }

    @Override
    public void getOrderGetFareSuccess(FareBean fareBean) {
        this.fareBean = fareBean;
        this.fareStr = fareBean.getFare()+"";
        tv_fare.setText(fareBean.getFare()+"");
        if (type == WlmUtil.GOODSTYPE_VIP) {
            totalPrice = rightNowGoodsBean.getPrice() * 1 - Double.valueOf(fareStr);
        }else {
            totalPrice = rightNowGoodsBean.getPrice() * 1 - Double.valueOf(fareStr) - rightNowBuyBean.getIntegral();
        }
        tv_total.setText("¥" + totalPrice);
        tv_total_price.setText(totalPrice + "");
    }

    @Override
    public void getOrderGetFareFail(String msg) {

    }

    @Override
    public void getRightNowBuySuccess(String orderid) {

        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.ORDERID,orderid);
        bundle.putString(WlmUtil.ORDERAMOUNT,totalPrice+"");
        bundle.putString(WlmUtil.WHERE,WlmUtil.GOODS);
        UiHelper.launcherBundle(this,PayActivity.class,bundle);
        finish();

    }

    @Override
    public void getRightNowBuyFail(String msg) {
        toast(msg);
    }


    @OnClick({R.id.rl_pay_layout,R.id.tv_place_order,R.id.ll_back,R.id.rl_address})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_pay_layout:

                rootView.setListener(this);
                rootView.showAsDropDown(tv_place_order,0,0);

                break;

            case R.id.tv_place_order:
                String orderType = "";
                if (type == WlmUtil.GOODSTYPE_GROUPON){
                    orderType = "2";
                }else if (type == WlmUtil.GOODSTYPE_INTEGRAL || type == WlmUtil.GOODSTYPE_WLM){
                    orderType = "1";
                }else if (type == WlmUtil.GOODSTYPE_VIP){
                    orderType = "4";
                }else if (type == WlmUtil.GOODSTYPE_CROWDFUNDING){
                    orderType = "16";
                }else if (type == WlmUtil.GOODSTYPE_POINT || type == WlmUtil.GOODSTYPE_SECKILL){
                    orderType = "32";
                }else if (type == WlmUtil.GOODSTYPE_WLMBUY){
                    orderType = "64";
                }

                if (addressBean == null){
                    toast("请您填写收货地址");
                    return;
                }


                String sttr_id = "";
                if (goodsChooseBean != null && goodsChooseBean.getAttr_id() != 0){
                    sttr_id = goodsChooseBean.getAttr_id() + "";
                }

                grouponOrderPresenter.rightNowBuy(rightNowGoodsBean.getGoodsId(),addressBean.getAddressID(),goodsnum+"",totalPrice+""
                        ,fareStr,rightNowBuyBean.getIntegral()+"",tv_use_remarks.getText().toString(),orderType,sttr_id,ProApplication.SESSIONID(this));


                break;

            case R.id.ll_back:

                finish();

                break;

            case R.id.rl_address:

                UiHelper.launcherForResult(this,ChooseAddressActivity.class,address_result);

                break;
        }
    }

    @Override
    public void chooseType(int type) {
        if (type == 1){
            iv_pay_type.setImageResource(R.mipmap.ic_order_wx);
            tv_pay_type.setText(getString(R.string.pay_wx));
            pay_type_position=1;
        }else if (type == 2){
            iv_pay_type.setImageResource(R.mipmap.ic_order_self);
            tv_pay_type.setText(getString(R.string.pay_self));
            pay_type_position=2;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == address_result && resultCode == RESULT_OK){
            AddressBean addressBean = (AddressBean) data.getBundleExtra(WlmUtil.TYPEID).get("address");
            if (addressBean != null && !addressBean.getAddressID().isEmpty()) {
                isAddressSuccess(addressBean);
                tv_goods_consignee_name.setVisibility(View.VISIBLE);
                if (tv_no_address != null) {
                    tv_no_address.setVisibility(View.GONE);
                }
                grouponOrderPresenter.getFare(rightNowGoodsBean.getGoodsId(), addressBean.getAddressID(), rightNowGoodsBean.getGoodsNumber() + "", ProApplication.SESSIONID(this));
            }
        }
    }

    @Override
    public void setWxSuccess() {
    }

    @Override
    public void setWxFail() {
    }
}
