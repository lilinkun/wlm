package com.wlm.wlm.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.util.Eyes;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/11/21.
 */

public class RecommendActivity extends BaseActivity implements OnTitleBarClickListener {

    @BindView(R.id.titlebar)
    CustomTitleBar customTitleBar;
    @BindView(R.id.recommend_content)
    EditText mEtRecommendContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recommend;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this, getResources().getColor(R.color.setting_title_color));
        customTitleBar.SetOnTitleClickListener(this);
    }

    @OnClick({R.id.recommend_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recommend_commit:
                Intent localIntent = new Intent("android.intent.action.VIEW");
                localIntent.putExtra("sms_body", mEtRecommendContent.getText().toString());
                localIntent.setType("vnd.android-dir/mms-sms");
                startActivity(localIntent);

                break;
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }
}
