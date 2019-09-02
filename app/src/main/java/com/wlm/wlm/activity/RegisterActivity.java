package com.wlm.wlm.activity;


import android.app.ProgressDialog;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.RegisterContract;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.RegisterPresenter;
import com.wlm.wlm.ui.CustomRegisterLayout;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.util.Eyes;

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        customRegisterLayout.setVcodeLisener(this);

        mRegisterPresenter.onCreate(this,this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.login_loading));
        progressDialog.setCancelable(true);
    }


    @Override
    public void getVcode(String phoneNum) {
        mRegisterPresenter.SendSms(phoneNum,"");
    }

    @Override
    public void getOver(String account,String phone,String vcode) {
        mRegisterPresenter.register(account,phone,vcode,progressDialog);
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
        progressDialog.dismiss();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegisterPresenter.onStop();
    }

    @Override
    public void onRegisterFail(String msg) {
        progressDialog.dismiss();
        toast(msg);
    }

}
