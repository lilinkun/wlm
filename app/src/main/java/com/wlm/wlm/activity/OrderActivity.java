package com.wlm.wlm.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.OrderListAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.SureOrderContract;
import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.FareBean;
import com.wlm.wlm.entity.FaresBean;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.entity.WxInfoBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.interf.IOrderChoosePayTypeListener;
import com.wlm.wlm.interf.IWxResultListener;
import com.wlm.wlm.presenter.SureOrderPresenter;
import com.wlm.wlm.ui.OrderPopupLayout;
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
 * Created by LG on 2018/12/13.
 */

public class OrderActivity extends BaseActivity implements SureOrderContract, OrderListAdapter.OnDataGetFare, IWxResultListener, IOrderChoosePayTypeListener {
    @BindView(R.id.rv_order)
    RecyclerView recyclerView;
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
    @BindView(R.id.iv_pay_type)
    ImageView iv_pay_type;
    @BindView(R.id.tv_pay_type)
    TextView  tv_pay_type;

    SureOrderPresenter sureOrderPresenter = new SureOrderPresenter();

    OrderListAdapter orderListAdapter ;
    private AddressBean addressBean;
    private Dialog dialog;
    private BuyBean buyBean;
    private double isFare = 0;
    private String goodsids;
    private String storeIds;
    private String spec;
    private String num ;
    private double total;
    private Dialog payDialog;
    private boolean isClickBtn = false;
    private String attr_id="";
    private double getTotal_amount = 0;
    String orderid;
    public static int pay_type_position = 1;

    private final int address_result = 0x123;
    private boolean isWxPay = false;
    private OrderPopupLayout rootView = null;
    private GoodsChooseBean goodsChooseBean = null;
    private GoodsDetailInfoBean goodsDetailInfoBean = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        sureOrderPresenter.onCreate(this,this);

        rootView = new OrderPopupLayout(this);
        WXPayEntryActivity.setPayListener(this);
        if (getIntent().getBundleExtra(WlmUtil.TYPEID).getInt("type")==0) {
            goodsChooseBean = (GoodsChooseBean) getIntent().getBundleExtra(WlmUtil.TYPEID).getSerializable(WlmUtil.GOODSCHOOSEBEAN);
            goodsDetailInfoBean = (GoodsDetailInfoBean) getIntent().getBundleExtra(WlmUtil.TYPEID).getSerializable(WlmUtil.GOODSDETAILINFOBEAN);
            String num = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("num");
            attr_id = "";
            if (goodsChooseBean != null) {
                attr_id = String.valueOf(goodsChooseBean.getAttr_id());
            }

            loadDialog();
            sureOrderPresenter.getAddress("1","200", ProApplication.SESSIONID(this));
            sureOrderPresenter.rightNowBuy(goodsDetailInfoBean.getGoodsId(), attr_id, num, ProApplication.SESSIONID(this));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @OnClick({R.id.ll_back,R.id.rl_address,R.id.rl_zfb,R.id.rl_wx,R.id.rl_self,R.id.tv_place_order,R.id.rl_pay_layout})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;

            case R.id.rl_address:
                if (!isWxPay) {
                    UiHelper.launcherForResult(this, ChooseAddressActivity.class, address_result);
                }
                break;

            case R.id.tv_place_order:

                    if (addressBean == null) {
                        dialog.show();
                    } else {
                        int point = Integer.valueOf(tv_use_point.getText().toString()) * -1;


                        if (!isWxPay) {
//                            sureOrderPresenter.sureOrder(goodsids, attr_id, num, isFare + "",
//                                    point + "", total + "", addressBean.getAddressID(),
//                                    buyBean.getStoremodel().get(0).getTotal_amount() + "", ProApplication.SESSIONID(this));
                        }else {
                            sureOrderPresenter.setWxPay(orderid,total+"","29","1","Android","com.wlm.wlm",ProApplication.SESSIONID(this));
                        }

//                        }
                }
                break;

            case R.id.rl_pay_layout:

                rootView.setListener(this);
                rootView.showAsDropDown(tv_place_order,0,0);

                break;
        }
    }


    @Override
    public void getRightNowBuySuccess(BuyBean buyBean) {
        this.buyBean = buyBean;
        orderListAdapter = new OrderListAdapter(this,buyBean,this);
        recyclerView.setAdapter(orderListAdapter);

        if(buyBean.getUseraddressmodel() == null){
            dialog.show();
            linearLayout.setVisibility(View.GONE);
        }else {
            addressBean = (AddressBean) buyBean.getUseraddressmodel();
            setAddress(addressBean);
        }

        getTotal_amount = buyBean.getStoremodel().get(0).getTotal_amount();
        goods_total_price.setText("¥" + getTotal_amount + "");

        tv_point.setText(buyBean.getUsermodel().getPoint() + "积分");
        tv_pay_self.setText(buyBean.getUsermodel().getAmount() + "");

    }
   @Override
    public void getRightNowBuyFail(String msg) {

    }

    @Override
    public void getOrderGetFareSuccess(FareBean fareBean) {
        isFare = fareBean.getFare();
        if (fareBean.getFare() == 0){
            if (orderListAdapter != null) {
                orderListAdapter.setFreight("免邮");
            }
        }else {
            orderListAdapter.setFreight(fareBean.getFare()+"");
        }
        tv_fare.setText(isFare + "");

        total = getTotal_amount + Double.parseDouble(tv_fare.getText().toString()) +  Integer.valueOf(tv_use_point.getText().toString());

        BigDecimal b = new BigDecimal(total);
        total = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        tv_total.setText("¥" + total);
        tv_total_price.setText("¥" + total);

    }

    @Override
    public void getOrderGetFareFail(String msg) {
        toast(msg);
    }

    @Override
    public void getOrderGetFaresSuccess(ArrayList<FaresBean> fareBean) {

    }

    @Override
    public void getOrderGetFaresFail(String msg) {

    }

    @Override
    public void sureOrderSuccess(CollectDeleteBean collectDeleteBean) {

        if (!tv_place_order.isClickable()){
            tv_place_order.setClickable(true);
        }
        if (collectDeleteBean.getStatus() == 0){

            orderid = collectDeleteBean.getMessage();
            if (pay_type_position == 1){
                isWxPay = true;
                tv_place_order.setText("正在提交");
                sureOrderPresenter.setWxPay(orderid,total+"","29","1","Android","com.wlm.wlm",ProApplication.SESSIONID(this));
            }else {
                isWxPay = false;
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
                            UiHelper.launcherBundle(OrderActivity.this, AllOrderActivity.class, bundle);
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
                            sureOrderPresenter.selfPay(editText.getText().toString(), orderid, ProApplication.SESSIONID(OrderActivity.this));
                        }
                    }
                });
            }

        }else {
            toast("购买失败" + collectDeleteBean.getMessage());
        }
    }

    @Override
    public void sureOrderFail(String msg) {
        if (!tv_place_order.isClickable()){
            tv_place_order.setClickable(true);
        }
        toast(msg);
    }

    @Override
    public void selfPaySuccess(CollectDeleteBean collectDeleteBean) {
        if (payDialog != null && payDialog.isShowing()) {
            if (collectDeleteBean.getStatus() == 0) {
                isClickBtn = true;
                payDialog.dismiss();
//                toast(collectDeleteBean.getMessage());
                Bundle bundle = new Bundle();
                bundle.putString("price",total+"");
                UiHelper.launcherBundle(this,PayResultActivity.class,bundle);
                setResult(RESULT_OK);
                finish();
            }else {
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

    @Override
    public void isAddressSuccess(AddressBean addressBean) {
        this.addressBean = addressBean;
        setAddress(addressBean);
//        sureOrderPresenter.getFare(goodsDetailInfoBean.getGoodsId(),addressBean.getAddressID(),goodsDetailInfoBean.getGoodsNumber()+"",goodsChooseBean.getAttr_id()+"",ProApplication.SESSIONID(this));
    }

    @Override
    public void isAddressFail(String msg) {
        toast(msg);
    }

    private void loadDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_address,null);
        dialog = new Dialog(this);
        dialog.setContentView(view);

        Button btn_add = (Button) view.findViewById(R.id.btn_add);
        Button btn_exit = (Button) view.findViewById(R.id.btn_exit);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.launcherForResult(OrderActivity.this,ChooseAddressActivity.class,address_result);
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
        if (resultCode == RESULT_OK){
            if (requestCode == address_result){
                addressBean = (AddressBean) data.getBundleExtra(WlmUtil.TYPEID).getSerializable("address");
                linearLayout.setVisibility(View.VISIBLE);
                setAddress(addressBean);
                getFare(addressBean.getProv(),addressBean.getCity());
            }
        }
    }

    private void setAddress(AddressBean address){

        tv_consignee_name.setText(address.getName());
        tv_consignee_phone.setText(PhoneFormatCheckUtils.phoneAddress(address.getMobile()));
        tv_consignee_address.setText(address.getAddressName() + address.getAddress());

    }

    @Override
    public void onGetFare(String goodsids, String storeIds,String spec,String num) {
        this.goodsids = goodsids;
        this.storeIds = storeIds;
        this.spec = spec;
        this.num = num;
//        getFare(buyBean.getUseraddressmodel().getProv(),buyBean.getUseraddressmodel().getCity());
    }

    private void getFare(String provinceId,String cityId){
//        sureOrderPresenter.getFare(goodsids,spec,num,provinceId,cityId,storeIds,ProApplication.SESSIONID(this));
    }

    @Override
    public void onPoint(int point) {
        tv_use_point.setText("-" + point);
        total = (int)getTotal_amount + Double.parseDouble(tv_fare.getText().toString()) +  Double.valueOf(tv_use_point.getText().toString());

        BigDecimal b = new BigDecimal(total);
        total = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        tv_total.setText("¥" + total);
        tv_total_price.setText("" + total);

    }

    @Override
    public void setWxSuccess() {
        isClickBtn = true;
        if (payDialog != null && payDialog.isShowing()) {
            payDialog.dismiss();
        }
        Bundle bundle = new Bundle();
        bundle.putString("price",total+"");
        UiHelper.launcherBundle(this,PayResultActivity.class,bundle);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setWxFail() {
        toast("支付失败");
        Bundle bundle = new Bundle();
        bundle.putString("order_sn", orderid);
        bundle.putInt("status", 0);
        UiHelper.launcherBundle(OrderActivity.this, AllOrderActivity.class, bundle);
        setResult(RESULT_OK);
        finish();
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
