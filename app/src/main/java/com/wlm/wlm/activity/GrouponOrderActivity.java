package com.wlm.wlm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.wlm.wlm.interf.IOrderChoosePayTypeListener;
import com.wlm.wlm.presenter.GrouponOrderPresenter;
import com.wlm.wlm.ui.OrderPopupLayout;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.PhoneFormatCheckUtils;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GrouponOrderActivity extends BaseActivity implements GrouponOrderContract, IOrderChoosePayTypeListener {

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

    private GrouponOrderPresenter grouponOrderPresenter = new GrouponOrderPresenter();

    private OrderPopupLayout rootView = null;
    public static int pay_type_position = 1;
    GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsListBean = null;
    private AddressBean addressBean = null;
    private double totalPrice = 0;
    private FareBean fareBean = null;
    private RightNowBuyBean rightNowBuyBean = null;
    private RightNowGoodsBean rightNowGoodsBean = null;

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

        Bundle bundle = getIntent().getBundleExtra(WlmUtil.TYPEID);

        if (bundle != null && bundle.getSerializable(WlmUtil.GROUPONGOODS) != null){
            goodsListBean = (GoodsDetailInfoBean<ArrayList< GoodsChooseBean >>) bundle.getSerializable(WlmUtil.GROUPONGOODS);
        }

        if (goodsListBean != null && goodsListBean.getAttr() == null){
            grouponOrderPresenter.getGoodsOrderInfo(goodsListBean.getGoodsId(),"","1",ProApplication.SESSIONID(this));
        }



    }

    public void isAddressSuccess(AddressBean addressBean) {
        this.addressBean = addressBean;
        tv_consignee_name.setText(addressBean.getName());
        tv_consignee_phone.setText(PhoneFormatCheckUtils.phoneAddress(addressBean.getMobile()));
        tv_consignee_address.setText(addressBean.getAddressName() + addressBean.getAddress());

        grouponOrderPresenter.getFare(rightNowGoodsBean.getGoodsId(),addressBean.getAddressID(),rightNowGoodsBean.getGoodsNumber()+"",ProApplication.SESSIONID(this));
    }

    @Override
    public void getOrderGetFareSuccess(FareBean fareBean) {
        this.fareBean = fareBean;
        tv_fare.setText(fareBean.getFare()+"");
        totalPrice = rightNowGoodsBean.getPrice() * 1-fareBean.getFare()-rightNowGoodsBean.getIntegral();
        tv_total.setText("¥" + totalPrice);
        tv_total_price.setText(totalPrice + "");
    }

    @Override
    public void getOrderGetFareFail(String msg) {

    }

    @Override
    public void getRightNowBuySuccess(RightNowBuyBean buyBean) {

        this.rightNowBuyBean = buyBean;
        this.addressBean = rightNowBuyBean.getAddress().get(0);
        this.rightNowGoodsBean = rightNowBuyBean.getList().get(0);


        if (addressBean != null){
            isAddressSuccess(addressBean);
        }

        tv_goods_title.setText(buyBean.getList().get(0).getGoodsName());
        Picasso.with(this).load(ProApplication.HEADIMG + goodsListBean.getGoodsImg()).error(R.mipmap.ic_adapter_error).into(iv_goods_pic);
        tv_goods_price.setText(buyBean.getList().get(0).getPrice()+"");
        tv_coupon_price.setText("X" + buyBean.getList().get(0).getUseNumber());

        String price = "¥" + buyBean.getList().get(0).getPrice() * 1 + "";
        goods_total_price.setText(price);
        tv_use_point.setText(buyBean.getList().get(0).getIntegral() + "");
    }

    @Override
    public void getRightNowBuyFail(String msg) {

    }

    @OnClick({R.id.rl_pay_layout,R.id.tv_place_order})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_pay_layout:

                rootView.setListener(this);
                rootView.showAsDropDown(tv_place_order,0,0);

                break;

            case R.id.tv_place_order:

                grouponOrderPresenter.rightNowBuy(rightNowGoodsBean.getGoodsId(),addressBean.getAddressID(),"1",totalPrice+""
                        ,fareBean.getFare()+"",rightNowGoodsBean.getIntegral()+"","",ProApplication.SESSIONID(this));


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
}
