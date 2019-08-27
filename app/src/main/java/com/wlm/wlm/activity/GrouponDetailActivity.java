package com.wlm.wlm.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.util.Eyes;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/8/21.
 */

public class GrouponDetailActivity extends BaseActivity {

    @BindView(R.id.tv_grouponing)
    TextView tv_grouponing;
    @BindView(R.id.rv_groupon_list)
    RecyclerView rv_groupon_list;

    @Override
    public int getLayoutId() {
        return R.layout.activity_groupon_detail;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.setting_title_color));

        tv_grouponing.setBackground(null);
        tv_grouponing.setText("拼团中");


    }

    @OnClick({R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                finish();

                break;
        }
    }
}
