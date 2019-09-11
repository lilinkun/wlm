package com.wlm.wlm.activity;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.RegisterContract;
import com.wlm.wlm.entity.WxUserInfo;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.RegisterPresenter;
import com.wlm.wlm.ui.CustomRegisterLayout;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import butterknife.BindView;

/**
 * Created by LG on 2018/11/13.
 * 注册
 */
public class RegisterActivity extends BaseActivity implements CustomRegisterLayout.OnRegisterListener, RegisterContract {


    @BindView(R.id.custom_register)
    CustomRegisterLayout customRegisterLayout;

    private RegisterPresenter mRegisterPresenter = new RegisterPresenter();
    ProgressDialog progressDialog = null;
    WxUserInfo wxUserInfo = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        Bundle bundle = getIntent().getBundleExtra(WlmUtil.TYPEID);
        if (bundle != null && bundle.getSerializable("wxinfo") != null){

            wxUserInfo = (WxUserInfo) bundle.getSerializable("wxinfo");

        }

        customRegisterLayout.setVcodeLisener(this);

        mRegisterPresenter.onCreate(this,this);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage(getResources().getString(R.string.login_loading));
//        progressDialog.setCancelable(true);getVcode
    }


    @Override
    public void getVcode(String phoneNum) {
        mRegisterPresenter.SendSms(phoneNum,"");
    }

    @Override
    public void getOver(String phone,String vcode,String Referees) {
        if (wxUserInfo != null) {
            mRegisterPresenter.register(phone,wxUserInfo.getNickname(), vcode, Referees,wxUserInfo.getUnionid(),wxUserInfo.getOpenid(),wxUserInfo.getHeadimgurl(),"2", ProApplication.SESSIONID(this));
        }
    }

    @Override
    public void getModify(String phone, String vcode, String psd) {
    }

    @Override
    public void onSendVcodeSuccess() {

    }

    @Override
    public void onSendVcodeFail(String msg) {

    }

    @Override
    public void onRegisterSuccess() {
//        progressDialog.dismiss();
//        setResult(RESULT_OK);
//        finish();
        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
        sharedPreferences.edit().putString("sessionid",ProApplication.SESSIONID(this)).putBoolean(WlmUtil.LOGIN,true)
                .putString(WlmUtil.OPENID,wxUserInfo.getOpenid()).putString(WlmUtil.UNIONID,wxUserInfo.getUnionid()).commit();

        UiHelper.launcher(this,MainFragmentActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRegisterFail(String msg) {
        toast(msg);
    }

}
