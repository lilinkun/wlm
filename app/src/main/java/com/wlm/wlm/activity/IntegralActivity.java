package com.wlm.wlm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.IntegralAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.IntegralContract;
import com.wlm.wlm.entity.AmountPriceBean;
import com.wlm.wlm.entity.BalanceBean;
import com.wlm.wlm.entity.BalanceDetailBean;
import com.wlm.wlm.entity.IntegralBean;
import com.wlm.wlm.entity.PointListBean;
import com.wlm.wlm.entity.UserBankBean;
import com.wlm.wlm.presenter.IntegralPresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/12/29.
 */

public class IntegralActivity extends BaseActivity implements IntegralContract{

    @BindView(R.id.rv_style)
    RecyclerView rv_style;
    @BindView(R.id.tv_balance_amount)
    TextView tv_balance_amount;
    @BindView(R.id.tv_balance_name)
    TextView tv_balance_name;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.ic_balance_status)
    LinearLayout ic_balance_status;
    @BindView(R.id.iv_head_left)
    ImageView iv_head_left;
    @BindView(R.id.tv_wait_receiver)
    TextView tv_wait_receiver;
    @BindView(R.id.line_wait_receiver)
    View line_wait_receiver;
    @BindView(R.id.line_list)
    View line_list;
    @BindView(R.id.tv_list)
    TextView tv_list;
    @BindView(R.id.rl_wait_receiver)
    RelativeLayout rl_wait_receiver;
    @BindView(R.id.rl_list)
    RelativeLayout rl_list;

    private IntegralPresenter integralPresenter = new IntegralPresenter();
    private int mListStyle = 0;
    private int PAGE_INDEX = 1;
    private int sizeInt = 0;
    private IntegralAdapter integralAdapter ;
    private int lastVisibleItem = 0;
    private BalanceBean balanceBean;
    private String type ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_integral;
    }

    @Override
    public void initEventAndData() {

        Eyes.translucentStatusBar(this);

        integralPresenter.onCreate(this,this);

        mListStyle =  getIntent().getBundleExtra(WlmUtil.TYPEID).getInt("style");
        balanceBean = (BalanceBean) getIntent().getBundleExtra(WlmUtil.TYPEID).getSerializable(WlmUtil.BALANCEBEAN);

        init();


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_style.setLayoutManager(linearLayoutManager);

        rv_style.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (integralAdapter != null) {
                        if (lastVisibleItem + 1 == integralAdapter.getItemCount()) {
//                            mHandler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
                            if (PAGE_INDEX * Integer.valueOf(WlmUtil.PAGE_COUNT) > pointListBeans.size()){
                                toast("已到末尾");
                            }else {
                                PAGE_INDEX++;

                                integralPresenter.getPriceData(PAGE_INDEX+"", WlmUtil.PAGE_COUNT,type, ProApplication.SESSIONID(IntegralActivity.this));

                            }
//                                }
//                            }, 200);
                        }

                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @OnClick({R.id.ll_back,R.id.tv_name,R.id.rl_wait_receiver,R.id.rl_list})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.ll_back:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(WlmUtil.BALANCEBEAN,balanceBean);
                intent.putExtra(WlmUtil.TYPE,bundle);
                setResult(RESULT_OK,intent);
                finish();

                break;

            case R.id.tv_name:

                integralPresenter.getBankCard(ProApplication.SESSIONID(this));

                break;

            case R.id.rl_wait_receiver:
                type = "4";
                line_wait_receiver.setVisibility(View.VISIBLE);
                tv_list.setTextColor(getResources().getColor(R.color.grey_color2));
                line_list.setVisibility(View.GONE);
                tv_wait_receiver.setTextColor(getResources().getColor(R.color.black_333333));
                integralPresenter.getPriceData(PAGE_INDEX+"", WlmUtil.PAGE_COUNT,type, ProApplication.SESSIONID(IntegralActivity.this));
                break;

            case R.id.rl_list:
                type = "5";
                line_wait_receiver.setVisibility(View.GONE);
                tv_list.setTextColor(getResources().getColor(R.color.black_333333));
                line_list.setVisibility(View.VISIBLE);
                tv_wait_receiver.setTextColor(getResources().getColor(R.color.grey_color2));
                integralPresenter.getPriceData(PAGE_INDEX+"", WlmUtil.PAGE_COUNT,type, ProApplication.SESSIONID(IntegralActivity.this));
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(WlmUtil.BALANCEBEAN,balanceBean);
            intent.putExtra(WlmUtil.TYPEID,bundle);
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private ArrayList<BalanceDetailBean> pointListBeans;


    @Override
    public void getDataSuccess(ArrayList<BalanceDetailBean> amountPriceBean) {
        rv_style.setVisibility(View.VISIBLE);
        if (integralAdapter == null) {
            pointListBeans = amountPriceBean;
            if (mListStyle == 0){
                integralAdapter = new IntegralAdapter(this, amountPriceBean, 0);
            }else {
                integralAdapter = new IntegralAdapter(this, amountPriceBean, 1);
            }
            rv_style.setAdapter(integralAdapter);
//            sizeInt = amountPriceBean.getAmount_list().size();

        }else {
            pointListBeans.addAll(amountPriceBean);
            integralAdapter.setData(pointListBeans);

        }
    }

    @Override
    public void getDataFail(String msg) {
        if (!msg.contains("查无数据")) {
            rv_style.setVisibility(View.GONE);
        }
    }

    @Override
    public void getBankSuccess(UserBankBean userBankBean) {
        if (userBankBean.getBankUserName() == null || userBankBean.getBankNo() == null){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setMessage("使用提现功能需添加一张支持提现储蓄卡");
            alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setPositiveButton("添加储蓄卡", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UiHelper.launcher(IntegralActivity.this,BindCardActivity.class);
                }
            }).show();
        }else {
            Bundle bundle = new Bundle();
            bundle.putString(WlmUtil.WLMCOIN,balanceBean.getMoney5Balance()+"");
            bundle.putSerializable(WlmUtil.USERBANKBEAN,userBankBean);
            UiHelper.launcherForResultBundle(this,GetCashActivity.class,0x222,bundle);
        }
    }

    @Override
    public void getBankFail(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setMessage("使用提现功能需添加一张支持提现储蓄卡");
        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("添加储蓄卡", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UiHelper.launcher(IntegralActivity.this,BindCardActivity.class);
            }
        }).show();
    }

    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
        this.balanceBean = balanceBean;
        init();
    }

    @Override
    public void getBalanceFail(String msg) {
        toast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == 0x222){
            integralPresenter.getBalance(ProApplication.SESSIONID(this));
            PAGE_INDEX = 1;
            init();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void init(){
        if (mListStyle == 0) {
            type = "2";
            integralPresenter.getPriceData(PAGE_INDEX+"", WlmUtil.PAGE_COUNT,type, ProApplication.SESSIONID(this));
            tv_balance_name.setText(getString(R.string.me_integral));
            ic_balance_status.setBackgroundResource(R.mipmap.ic_integral_bg);
            iv_head_left.setImageResource(R.mipmap.ic_back_white);
            tv_balance_name.setTextColor(getResources().getColor(R.color.white));
            tv_balance_amount.setTextColor(getResources().getColor(R.color.white));
            tv_name.setVisibility(View.GONE);
            tv_balance_amount.setText(balanceBean.getMoney2Balance()+"");
            rl_wait_receiver.setVisibility(View.GONE);
            rl_list.setVisibility(View.GONE);
        }else if(mListStyle == 1){
            type = "5";
            integralPresenter.getPriceData(PAGE_INDEX+"", WlmUtil.PAGE_COUNT,type, ProApplication.SESSIONID(this));
            tv_balance_name.setText(getString(R.string.me_wlm_coin));
            ic_balance_status.setBackgroundResource(R.mipmap.ic_wlm_coin_bg);
            iv_head_left.setImageResource(R.mipmap.ic_back);
            tv_balance_name.setTextColor(getResources().getColor(R.color.black_333333));
            tv_balance_amount.setTextColor(getResources().getColor(R.color.black_333333));
            tv_name.setText("提现");
            tv_balance_amount.setText(balanceBean.getMoney5Balance()+"");
        }else if (mListStyle == 2){
            type = "4";
            integralPresenter.getPriceData(PAGE_INDEX+"", WlmUtil.PAGE_COUNT,type, ProApplication.SESSIONID(this));
            tv_balance_name.setText(getString(R.string.me_wlm_coin));
            ic_balance_status.setBackgroundResource(R.mipmap.ic_wlm_coin_bg);
            iv_head_left.setImageResource(R.mipmap.ic_back);
            tv_balance_name.setTextColor(getResources().getColor(R.color.black_333333));
            tv_balance_amount.setTextColor(getResources().getColor(R.color.black_333333));
            tv_name.setText("提现");
            tv_balance_amount.setText(balanceBean.getMoney4Balance()+"");
            line_wait_receiver.setVisibility(View.VISIBLE);
            line_list.setVisibility(View.GONE);
            tv_wait_receiver.setTextColor(getResources().getColor(R.color.black_333333));
            tv_list.setTextColor(getResources().getColor(R.color.grey_color2));
        }
    }
}
