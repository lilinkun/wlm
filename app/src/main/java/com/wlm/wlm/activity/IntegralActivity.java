package com.wlm.wlm.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.wlm.wlm.presenter.IntegralPresenter;
import com.wlm.wlm.util.Eyes;
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
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.tv_pay_balance)
    TextView tv_pay_balance;
    @BindView(R.id.tv_balance_amount)
    TextView tv_balance_amount;
    @BindView(R.id.tv_balance_name)
    TextView tv_balance_name;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.ic_balance_status)
    LinearLayout ic_balance_status;
    @BindView(R.id.rl_income_pay)
    RelativeLayout rl_income_pay;
    @BindView(R.id.iv_head_left)
    ImageView iv_head_left;

    private IntegralPresenter integralPresenter = new IntegralPresenter();
    private int mListStyle = 0;
    private int PAGE_INDEX = 1;
    private int sizeInt = 0;
    private IntegralAdapter integralAdapter ;
    private int lastVisibleItem = 0;
    private BalanceBean balanceBean;

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

        if (mListStyle == 0) {
            integralPresenter.getPriceData(PAGE_INDEX+"", WlmUtil.PAGE_COUNT,"5", ProApplication.SESSIONID(this));
            rl_income_pay.setVisibility(View.GONE);
            tv_balance_name.setText(getString(R.string.me_integral));
            ic_balance_status.setBackgroundResource(R.mipmap.ic_integral_bg);
            iv_head_left.setImageResource(R.mipmap.ic_back_white);
            tv_balance_name.setTextColor(getResources().getColor(R.color.white));
            tv_pay_balance.setTextColor(getResources().getColor(R.color.white));
            tv_balance_amount.setTextColor(getResources().getColor(R.color.white));
            tv_name.setTextColor(getResources().getColor(R.color.white));
            tv_name.setText("可用积分");
        }else if(mListStyle == 1){
            integralPresenter.getPriceData(PAGE_INDEX+"", WlmUtil.PAGE_COUNT,"2", ProApplication.SESSIONID(this));
            tv_balance.setText("可提现：");
            tv_balance_name.setText(getString(R.string.me_wlm_coin));
            ic_balance_status.setBackgroundResource(R.mipmap.ic_wlm_coin_bg);
            iv_head_left.setImageResource(R.mipmap.ic_back);
            tv_balance_name.setTextColor(getResources().getColor(R.color.black_333333));
            tv_pay_balance.setTextColor(getResources().getColor(R.color.black_333333));
            tv_balance_amount.setTextColor(getResources().getColor(R.color.black_333333));
            tv_name.setTextColor(getResources().getColor(R.color.black_333333));
            tv_name.setText(R.string.me_wlm_coin);
        }

        tv_balance_amount.setText(balanceBean.getMoney2Balance()+"");


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
                                if (mListStyle == 0) {
                                    integralPresenter.getPriceData(PAGE_INDEX + "", WlmUtil.PAGE_COUNT, "5",ProApplication.SESSIONID(IntegralActivity.this));
                                }else {
                                    integralPresenter.getPriceData(PAGE_INDEX+"", WlmUtil.PAGE_COUNT,"2", ProApplication.SESSIONID(IntegralActivity.this));
                                }
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

    @OnClick({R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.ll_back:

                finish();

                break;
        }
    }


    private ArrayList<BalanceDetailBean> pointListBeans;


    @Override
    public void getDataSuccess(ArrayList<BalanceDetailBean> amountPriceBean) {
        if (integralAdapter == null) {
            pointListBeans = amountPriceBean;
            integralAdapter = new IntegralAdapter(this, amountPriceBean, 1);
            rv_style.setAdapter(integralAdapter);
//            sizeInt = amountPriceBean.getAmount_list().size();
            if (mListStyle == 1) {
                tv_pay_balance.setText(WlmUtil.getPriceNum(balanceBean.getMoney2Balance()));
            }
        }else {
            pointListBeans.addAll(amountPriceBean);
            integralAdapter.setData(pointListBeans);

        }
    }

    @Override
    public void getDataFail(String msg) {
        if (!msg.contains("查无数据")) {
            toast(msg);
        }
    }
}
