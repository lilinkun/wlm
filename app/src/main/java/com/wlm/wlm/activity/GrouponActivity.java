package com.wlm.wlm.activity;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.util.Eyes;

/**
 * Created by LG on 2019/8/19.
 */

public class GrouponActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_groupon;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.setting_title_color));
    }
}
