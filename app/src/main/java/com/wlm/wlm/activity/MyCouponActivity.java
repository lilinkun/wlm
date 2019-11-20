package com.wlm.wlm.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.CouponAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.entity.TbGoodsBean;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.util.Eyes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LG on 2018/11/21.
 */

public class MyCouponActivity extends BaseActivity implements OnTitleBarClickListener {

    @BindView(R.id.titlebar)
    CustomTitleBar customTitleBar;
    @BindView(R.id.rv_record)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this, getResources().getColor(R.color.setting_title_color));
        customTitleBar.SetOnTitleClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        List<TbGoodsBean> tbGoodsBeans = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            TbGoodsBean tbGoodsBean = new TbGoodsBean();
            tbGoodsBean.setD_title("Nike 耐克官方NIKETANJUN男子运动休闲鞋812654");
            tbGoodsBean.setFrom("淘宝");
            tbGoodsBean.setPic("https://img.alicdn.com/imgextra/i3/890482188/TB2v9elEMmTBuNjy1XbXXaMrVXa_!!890482188.jpg");
            tbGoodsBean.setXiaoliang(8000);
            tbGoodsBean.setJiage(390);
            tbGoodsBean.setRenqi(9000);
            tbGoodsBean.setUpdatetime("2011-02-02");
            tbGoodsBean.setDh_sm("160元券");
            tbGoodsBean.setDh_ts("zhongguo");
            tbGoodsBean.setIstmall(1);
            tbGoodsBean.setYedh_price(230);
            tbGoodsBean.setYedh(111);
            tbGoodsBeans.add(tbGoodsBean);
        }
        CouponAdapter couponAdapter = new CouponAdapter(this, tbGoodsBeans);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(couponAdapter);
    }

    @Override
    public void onBackClick() {
        finish();
    }
}
