package com.wlm.wlm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MyNickNameContract;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.MyNickNamePresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.LzyydUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/11/23.
 */

public class MyNickNameActivity extends BaseActivity implements OnTitleBarClickListener , MyNickNameContract {

    @BindView(R.id.titlebar)
    CustomTitleBar customTitleBar;
    @BindView(R.id.et_nick)
    EditText nickEditText;

    private MyNickNamePresenter myNickNamePresenter = new MyNickNamePresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_nickname;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        customTitleBar.SetOnTitleClickListener(this);

        myNickNamePresenter.onCreate(this,this);

        Bundle bundle = getIntent().getBundleExtra(LzyydUtil.TYPEID);
        String nickname = bundle.getString("nick");
        nickEditText.setText(nickname);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @OnClick({R.id.recommend_commit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.recommend_commit:

                if (nickEditText.getText().toString().trim().isEmpty()){
                    toast(R.string.mynick_isempty);
                }else {

                    myNickNamePresenter.modifyAccout(nickEditText.getText().toString(), ProApplication.SESSIONID(this));
                }
                break;
        }
    }
    @Override
    public void modifySuccess() {
        Intent intent = new Intent();
        intent.putExtra("account", nickEditText.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void modifyFail(String msg) {
        toast(msg);
    }
}
