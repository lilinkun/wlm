package com.wlm.wlm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.util.UiHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/12/20.
 */

public class PayResultActivity extends BaseActivity {

    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_see_order)
    TextView tv_see_order;
    @BindView(R.id.tv_back_home)
    TextView tv_back_home;

    private String orderid = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_result;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.setting_title_color));
        ActivityUtil.addHomeActivity(this);

        String price = getIntent().getBundleExtra(WlmUtil.TYPEID).getString(WlmUtil.PRICE);
        orderid = getIntent().getBundleExtra(WlmUtil.TYPEID).getString(WlmUtil.ORDERID);
        tv_price.setText("¥ "  + price);

    }

    @OnClick({R.id.tv_see_order,R.id.tv_back_home,R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_see_order:

//                Bundle bundle = new Bundle();
//                bundle.putString("goodsname", "");
//                UiHelper.launcherBundle(this, OrderListActivity.class,bundle);

                Bundle bundle = new Bundle();
                bundle.putInt("status", 1);
                bundle.putString("order_sn", orderid);
                UiHelper.launcherBundle(this, AllOrderActivity.class, bundle);
                setResult(RESULT_OK);
                finish();

                break;

            case R.id.tv_back_home:

                MainFragmentActivity.isHome = true;

                setResult(RESULT_OK);
                finish();
                ActivityUtil.finishHomeAll();


//                Intent intent = new Intent(PayResultActivity.this, MainFragmentActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                break;

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
}
