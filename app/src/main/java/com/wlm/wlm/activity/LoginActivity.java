package com.wlm.wlm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.LoginContract;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.presenter.LoginPresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.LzyydUtil;
import com.wlm.wlm.util.UiHelper;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/11/13.
 */

public class LoginActivity extends BaseActivity implements LoginContract {

    @BindView(R.id.ll_wx_login)
    LinearLayout ll_wx_login;

    private LoginPresenter loginPresenter = new LoginPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        loginPresenter.onCreate(this,this);

        SharedPreferences sharedPreferences = getSharedPreferences(LzyydUtil.LOGIN, MODE_PRIVATE);
//        if (sharedPreferences.getBoolean(LzyydUtil.LOGIN,false)){
            loginPresenter.login("lilinkun","123456",ProApplication.SESSIONID(this));
//        }
    }

    @OnClick({R.id.ll_wx_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_wx_login:
                UiHelper.launcher(this, RegisterActivity.class);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == 0x1212){
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onStop();
    }

    @Override
    public void onLoginSuccess(LoginBean mLoginBean) {

        LoginBean loginBean = mLoginBean;
        String datalife = null;
        try {
            datalife = LzyydUtil.serialize(loginBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences sharedPreferences = getSharedPreferences(LzyydUtil.LOGIN, MODE_PRIVATE);
       /* sharedPreferences.edit().putString("sessionid",ProApplication.SESSIONID(this)).putBoolean(LzyydUtil.LOGIN,true)
                .putString("logininfo",datalife).putString("account",mEtLoginPhone.getText().toString().trim())
                .putString("password",mEtLoginPsd.getText().toString().trim()).commit();
*/
        UiHelper.launcher(this, MainFragmentActivity.class);
        finish();
    }


    @Override
    public void onLoginFail(String msg) {
        toast(msg);
    }

    @Override
    public void showPromptMessage(int str) {
        toast(str);
    }

}
