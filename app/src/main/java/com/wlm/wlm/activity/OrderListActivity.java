package com.wlm.wlm.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.TabPageAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.OrderListContract;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.CountBean;
import com.wlm.wlm.entity.SelfOrderBean;
import com.wlm.wlm.entity.WxInfoBean;
import com.wlm.wlm.entity.WxRechangeBean;
import com.wlm.wlm.fragment.AllOrderFragment;
import com.wlm.wlm.fragment.CompletedOrderFragment;
import com.wlm.wlm.fragment.OverOrderFragment;
import com.wlm.wlm.fragment.WaitPayFragment;
import com.wlm.wlm.fragment.WaitReceiveFragment;
import com.wlm.wlm.interf.IPayOrderClickListener;
import com.wlm.wlm.interf.IWxResultListener;
import com.wlm.wlm.presenter.OrderListPresenter;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.util.QRCodeUtil;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.wxapi.WXPayEntryActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by LG on 2018/12/5.
 */
public class OrderListActivity extends BaseActivity implements IPayOrderClickListener,OrderListContract, IWxResultListener {

    @BindView(R.id.order_list_tablayou)
    TabLayout orderListTablayou;
    @BindView(R.id.order_list_vp)
    ViewPager orderListVp;
    @BindView(R.id.ll_choose_order)
    LinearLayout linearLayout;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.rl_order)
    RelativeLayout rl_order;

    private List<String> mTitles;
    private TextView tvSelf,tvTb,tvJd;
    private PopupWindow popupWindow;
    private PopupWindow payPopupWindow;
    private SelfOrderBean selfOrderBean;
    private Dialog payDialog;
    private IPayOrderClickListener payListener;
    private OrderListPresenter orderListPresenter = new OrderListPresenter();
    public final static String mlist = "https://wqs.jd.com/order/orderlist_merge.shtml";
    private List<Fragment> fragments= new ArrayList<>();

    AllOrderFragment allOrderFragment = new AllOrderFragment();
    WaitPayFragment waitPayFragment = new WaitPayFragment();
    WaitReceiveFragment waitReceiveFragment = new WaitReceiveFragment();
    CompletedOrderFragment completedOrderFragment = new CompletedOrderFragment();
    OverOrderFragment overOrderFragment = new OverOrderFragment();

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        }
    };

    public static final int timeOut = 15;


    @Override
    public int getLayoutId() {
        return R.layout.activity_orderlist;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        orderListPresenter.onCreate(this,this);

        ActivityUtil.addHomeActivity(this);

        initData();

        TabPageAdapter pageAdapter = new TabPageAdapter(getSupportFragmentManager(),fragments,mTitles);
        pageAdapter.setTitles(mTitles);
        orderListVp.setAdapter(pageAdapter);
        orderListTablayou.setupWithViewPager(orderListVp);
        orderListTablayou.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
        orderListVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    allOrderFragment.setData();
                }else if (position == 1){
                    waitPayFragment.setData();
                }else if (position == 2){
                    waitReceiveFragment.setData();
                }else if (position == 3){
                    completedOrderFragment.setData();
                }else if (position == 4){
                    overOrderFragment.setData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        WXPayEntryActivity.setPayListener(this);

        allOrderFragment.setPayListener(this);
        waitPayFragment.setPayListener(this);
        waitReceiveFragment.setPayListener(this);
        completedOrderFragment.setPayListener(this);


        if (getIntent() != null && getIntent().getBundleExtra(WlmUtil.TYPEID) != null ){

            int position = getIntent().getBundleExtra(WlmUtil.TYPEID).getInt("position");

            orderListVp.setCurrentItem(position,false);

        }

    }

    private void initData() {
        mTitles = new ArrayList<>();
        mTitles.add("全部");
        mTitles.add("待付款");
        mTitles.add("待发货");
        mTitles.add("待收货");
        mTitles.add("交易成功");

        fragments = new ArrayList<>();
        fragments.add(allOrderFragment);
        fragments.add(waitPayFragment);
        fragments.add(waitReceiveFragment);
        fragments.add(completedOrderFragment);
        fragments.add(overOrderFragment);
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view){
        if (!ButtonUtils.isFastDoubleClick(view.getId())) {
            switch (view.getId()) {
                case R.id.ll_back:

                    finish();

                    break;

            }
        }
    }

    @Override
    public void payMode(SelfOrderBean selfOrderBean, int mode) {
        this.selfOrderBean = selfOrderBean;

        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.ORDERID,selfOrderBean.getOrderSn());
        bundle.putString(WlmUtil.ORDERAMOUNT,selfOrderBean.getOrderAmount()+"");
        bundle.putString(WlmUtil.WHERE,"order");
        bundle.putString(WlmUtil.POINT,selfOrderBean.getIntegral()+"");
        UiHelper.launcherForResultBundle(this,PayActivity.class,0x0987,bundle);

//        orderListPresenter.getOrderData(ProApplication.SESSIONID(this));
    }

    @Override
    public void SureReceive(String orderId) {
        orderListPresenter.sureReceipt(orderId,ProApplication.SESSIONID(this));
    }

    @Override
    public void getQrcode(String orderId) {
        Dialog dialog = new Dialog(this);
        Display display = this.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_qrcode,null);
        ImageView imageView = view.findViewById(R.id.iv_qrcode);
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(orderId, 200, 200);
        imageView.setImageBitmap(mBitmap);
        dialog.setContentView(view);
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                allOrderFragment.setData();
                if (waitReceiveFragment != null){
                    waitReceiveFragment.setData();
                }

            }
        });
    }

    @Override
    public void InfoAccountSuccess(CountBean orderDetailBean) {
        showPopup(orderDetailBean);
    }

    @Override
    public void InfoAccountFail(String msg) {

    }

    @Override
    public void selfPaySuccess(String collectDeleteBean) {
            payDialog.dismiss();
            allOrderFragment.setData();
            waitPayFragment.setData();
    }

    @Override
    public void selfPayFail(String msg) {
        toast(msg);
    }

    @Override
    public void sureReceiptSuccess(String collectDeleteBean) {
        toast("确认收货成功");
        completedOrderFragment.setData();
        allOrderFragment.setData();
    }

    @Override
    public void sureReceiptFail(String msg) {

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
    public void setWxSuccess() {
        if (allOrderFragment!=null) {
            allOrderFragment.setData();
        }
        if (waitReceiveFragment!=null ) {
            waitPayFragment.setData();
        }
    }

    @Override
    public void setWxFail() {
        toast("支付失败");
    }

    /*class TabPageAdapter extends FragmentPagerAdapter{
        List<Fragment> fragments = new ArrayList<>();
        private List<String> titles;

        public TabPageAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(allOrderFragment);
            fragments.add(waitPayFragment);
            fragments.add(waitReceiveFragment);
            fragments.add(completedOrderFragment);
            fragments.add(overOrderFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void setTitles(List<String> titles){this.titles = titles;}

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 将实例化的fragment进行显示即可。
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            Fragment fragment = fragments.get(position);// 获取要销毁的fragment
//            getSupportFragmentManager().beginTransaction().hide(fragment).commit();// 将其隐藏即可，并不需要真正销毁，这样fragment状态就得到了保存
        }
    }*/

    public static boolean checkPackage(Context paramContext, String paramString)
    {
        if ((paramString == null) || ("".equals(paramString))) {
            return false;
        }
        try
        {
            paramContext.getPackageManager().getPackageInfo(paramString, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {}
        return false;
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
        String endTime = selfOrderBean.getCreateDate();

        if ((int)selfOrderBean.getOrderAmount() == 0){
            check_wx.setClickable(false);
            check_wx.setChecked(false);
            check_wx.setEnabled(false);
            check_self.setChecked(true);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        try {
            Date str = simpleDateFormat.parse(endTime);
            long effectiveTime = str.getTime();
            Date date = new Date(System.currentTimeMillis());
            long nowTime = date.getTime();

            long time = effectiveTime - nowTime;

        int now = (int) (System.currentTimeMillis()/1000);
        tv_time.start(time);
//        tv_time.setCountdownTime((int)(time/1000)-((int) (System.currentTimeMillis()/1000)-now),1+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        tv_right_now_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_self.isChecked()){
                    payPopupWindow.dismiss();
                    View view = LayoutInflater.from(OrderListActivity.this).inflate(R.layout.dialog_pay,null);

                    payDialog = new Dialog(OrderListActivity.this);
                    payDialog.setContentView(view);
                    payDialog.show();

                    payDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                        }
                    });

//                    final EditText editText = (EditText) view.findViewById(R.id.et_pay_psd);
//                    TextView textView = (TextView) view.findViewById(R.id.tv_pay_price);
//                    Button btn_sure = (Button) view.findViewById(R.id.btn_sure);
//
//                    textView.setText(selfOrderBean.getOrderAmount()+"");
//
//                    btn_sure.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!editText.getText().toString().isEmpty()){
//                                orderListPresenter.selfPay(editText.getText().toString(),selfOrderBean.getOrderSn(),ProApplication.SESSIONID(OrderListActivity.this));
//                            }
//                        }
//                    });


                }else if (check_wx.isChecked()){
                    payPopupWindow.dismiss();
//                    toast("你瞅我干啥，暂时不能微信支付类");
                    orderListPresenter.setWxPay(selfOrderBean.getOrderSn(),selfOrderBean.getOrderAmount()+"","29","1","Android","com.wlm.wlm",ProApplication.SESSIONID(OrderListActivity.this));
                }else {
                    toast("请选择支付方式");
                }
            }
        });

        tv_price.setText("¥" + selfOrderBean.getOrderAmount()+"");

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == 0x0987){
                allOrderFragment.setData();
                waitPayFragment.setData();
                completedOrderFragment.setData();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
