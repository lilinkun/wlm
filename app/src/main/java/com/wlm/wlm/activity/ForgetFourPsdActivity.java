package com.wlm.wlm.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.ForgetFourPsdContract;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.presenter.ForgetFourPsdPresenter;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.LzyydUtil;
import com.wlm.wlm.util.UiHelper;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/12/27.
 */

public class ForgetFourPsdActivity extends BaseActivity implements ForgetFourPsdContract{

    @BindView(R.id.et_psd)
    EditText et_psd;
    @BindView(R.id.ic_input_psd)
    ImageView ic_input_psd;

    private String mobile;
    private String username;
    private String vcode;
    private boolean isClose = false;

    private ForgetFourPsdPresenter forgetFourPsdPresenter = new ForgetFourPsdPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_four;
    }

    @Override
    public void initEventAndData() {

        ActivityUtil.addActivity(this);

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        forgetFourPsdPresenter.onCreate(this,this);

        Bundle bundle = getIntent().getBundleExtra(LzyydUtil.TYPEID);
        mobile = bundle.getString("mobile");
        username = bundle.getString("username");
        vcode = bundle.getString("vcode");

    }

    @OnClick({R.id.tv_next,R.id.ll_back,R.id.ic_input_psd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:

                if (et_psd != null && et_psd.getText().toString().isEmpty()) {
                    toast("请正确输入新密码");
                } else if (et_psd.getText().toString().length() < 6) {
                    toast("请输入６位或以上的密码");
                } else {
                    forgetFourPsdPresenter.getModify(et_psd.getText().toString(),mobile,vcode,username);
                }
                break;


            case R.id.ll_back:
                finish();
                break;

            case R.id.ic_input_psd:
                if (isClose){
                    ic_input_psd.setImageResource(R.mipmap.ic_open_psd);
                    et_psd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_psd.setSelection(et_psd.getText().toString().length());
                    isClose = false;
                }else{
                    ic_input_psd.setImageResource(R.mipmap.ic_close_psd);
                    et_psd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_psd.setSelection(et_psd.getText().toString().length());
                    isClose = true;
                }
            break;
        }
    }

    @Override
    public void onModifyPsdSuccess(String message) {
        forgetFourPsdPresenter.login(username,et_psd.getText().toString(),ProApplication.SESSIONID(this));
    }

    @Override
    public void onModiftPsdFail(String msg) {
        toast(msg);
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
        sharedPreferences.edit().putString("sessionid", ProApplication.SESSIONID(this)).putBoolean(LzyydUtil.LOGIN,true)
                .putString("logininfo",datalife).putString("account",username)
                .putString("password",et_psd.getText().toString()).commit();

        UiHelper.launcher(this, MainFragmentActivity.class);

        ActivityUtil.finishAll();
    }

    @Override
    public void onLoginFail(String msg) {

    }
}
