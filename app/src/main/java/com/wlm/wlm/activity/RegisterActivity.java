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
public class RegisterActivity extends BaseActivity implements OnTitleBarClickListener, CustomRegisterLayout.OnRegisterListener, RegisterContract {


    @BindView(R.id.custom_title)
    CustomTitleBar customTitleBar;
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
        customTitleBar.SetOnTitleClickListener(this);

        customRegisterLayout.setVcodeLisener(this);

        mRegisterPresenter.onCreate(this);
        mRegisterPresenter.attachView(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.login_loading));
        progressDialog.setCancelable(true);
    }


    @Override
    public void onBackClick() {
        finish();
    }


    @Override
    public void getVcode(String phoneNum) {
        mRegisterPresenter.SendSms(phoneNum,"");
    }

    @Override
    public void getOver(String account,String phone,String vcode, String psd,String username) {
        mRegisterPresenter.register(account,phone,vcode,psd,progressDialog,username);
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

    @Override
    public void showPromptMessage(int resId) {

    }

    @Override
    public void showPromptMessage(String message) {

    }
}
