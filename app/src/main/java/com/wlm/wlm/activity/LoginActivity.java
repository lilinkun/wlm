package com.wlm.wlm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
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

    @BindView(R.id.tv_login_register)
    TextView mSendRegister;
    @BindView(R.id.line_phone)
    View mLinePhone;
    @BindView(R.id.line_psd)
    View mLinePsd;
    @BindView(R.id.et_login_input_phone)
    EditText mEtLoginPhone;
    @BindView(R.id.et_login_input_psd)
    EditText mEtLoginPsd;

    private LoginPresenter loginPresenter = new LoginPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        mEtLoginPhone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mLinePhone.setBackgroundColor(getResources().getColor(R.color.register_vcode_bg));
                mLinePsd.setBackgroundColor(getResources().getColor(R.color.line_bg));
                return false;
            }
        });
        mEtLoginPsd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mLinePsd.setBackgroundColor(getResources().getColor(R.color.register_vcode_bg));
                mLinePhone.setBackgroundColor(getResources().getColor(R.color.line_bg));
                return false;
            }
        });

        loginPresenter.attachView(this);
        loginPresenter.onCreate(this);

        loginPresenter.isRegister("1", ProApplication.SESSIONID(this));

        SharedPreferences sharedPreferences = getSharedPreferences(LzyydUtil.LOGIN, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(LzyydUtil.LOGIN,false)){
            loginPresenter.login(sharedPreferences.getString("account",""),sharedPreferences.getString("password",""),ProApplication.SESSIONID(this));
        }
    }

    @OnClick({R.id.tv_login_register, R.id.tv_login_send_psw, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_register:
                UiHelper.launcher(this, RegisterActivity.class);
                break;

            case R.id.tv_login_send_psw:
                UiHelper.launcherForResult(this, ForgetOnePsdActivity.class,0x1212);
                break;

            case R.id.btn_login:
//                UiHelper.launcher(this, MainActivity.class);
                if(mEtLoginPhone.getText().toString().isEmpty()){
                    toast(R.string.prompt_login_name_not_empty);
                    return;
                }
                if (mEtLoginPsd.getText().toString().isEmpty()){
                    toast(R.string.prompt_login_passwrod_not_empty);
                    return;
                }
                loginPresenter.login(mEtLoginPhone.getText().toString(),mEtLoginPsd.getText().toString(), ProApplication.SESSIONID(this));
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
    public void showPromptMessage(int resId) {

    }

    @Override
    public void showPromptMessage(String message) {

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
        sharedPreferences.edit().putString("sessionid",ProApplication.SESSIONID(this)).putBoolean(LzyydUtil.LOGIN,true)
                .putString("logininfo",datalife).putString("account",mEtLoginPhone.getText().toString().trim())
                .putString("password",mEtLoginPsd.getText().toString().trim()).commit();

        UiHelper.launcher(this, MainFragmentActivity.class);
        finish();
    }


    @Override
    public void onLoginFail(String msg) {
        toast(msg);
    }

    @Override
    public void isRegisterSuccess(boolean isRegister) {
        if (isRegister){
            if (mSendRegister != null) {
                mSendRegister.setVisibility(View.VISIBLE);
            }
        }else{
            if (mSendRegister != null) {
                mSendRegister.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void isRegisterFail(String msg) {
        if (mSendRegister != null) {
            mSendRegister.setVisibility(View.GONE);
        }
    }
}
