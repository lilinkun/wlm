package com.wlm.wlm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.LoginContract;
import com.wlm.wlm.db.DBManager;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.entity.WxUserInfo;
import com.wlm.wlm.interf.IWxLoginListener;
import com.wlm.wlm.interf.IWxResultListener;
import com.wlm.wlm.presenter.LoginPresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.wxapi.WXEntryActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/11/13.
 */

public class LoginActivity extends BaseActivity implements LoginContract, IWxLoginListener {

    @BindView(R.id.ll_wx_login)
    LinearLayout ll_wx_login;
    @BindView(R.id.tv_service_agreement)
    TextView tv_service_agreement;

    private LoginPresenter loginPresenter = new LoginPresenter();
    IWXAPI iwxapi = null;
    WxUserInfo wxUserInfo = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));


        iwxapi = WXAPIFactory.createWXAPI(this,WlmUtil.APP_ID,true);
        iwxapi.registerApp(WlmUtil.APP_ID);
        WXEntryActivity.wxType(WlmUtil.WXTYPE_LOGIN);
        WXEntryActivity.setLoginListener(this);

        loginPresenter.onCreate(this,this);
        loginPresenter.getUrl();
    }

    @OnClick({R.id.ll_wx_login,R.id.tv_service_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_wx_login:
//                UiHelper.launcher(this, RegisterActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
                if (sharedPreferences.getBoolean(WlmUtil.LOGIN,false)){
                    if (!sharedPreferences.getString(WlmUtil.OPENID,"").equals("")) {
                        loginPresenter.login(sharedPreferences.getString(WlmUtil.OPENID, ""), sharedPreferences.getString(WlmUtil.UNIONID, ""), "2", ProApplication.SESSIONID(this));
                    }else {
                        final SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";
                        req.state = "wechat_sdk_微信登录";
                        iwxapi.sendReq(req);
                    }
                }else {
//                    loginPresenter.login("o-Pjfvyivj1VphDd0OZYQH5Str3A","obdLtwjcQ-yXsVEhggAJnxrNu4A4","2",ProApplication.SESSIONID(this));
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_微信登录";
                    iwxapi.sendReq(req);
                }
                break;

            case R.id.tv_service_agreement:

                Bundle bundle = new Bundle();
                bundle.putString("type","2");
                UiHelper.launcherBundle(this,WebViewActivity.class,bundle);


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == 0x1212){
                finish();
            }else if(requestCode == 0x1233){
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

        /*LoginBean loginBean = mLoginBean;
        String datalife = "";
        try {
            datalife = WlmUtil.serialize(loginBean);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
        sharedPreferences.edit().putString("sessionid",ProApplication.SESSIONID(this)).putBoolean(WlmUtil.LOGIN,true)
                .putString(WlmUtil.ACCOUNT,mLoginBean.getNickName()).putString(WlmUtil.TELEPHONE,mLoginBean.getMobile())
                .putString(WlmUtil.USERNAME,mLoginBean.getUserName()).putString(WlmUtil.USERID,mLoginBean.getUserId())
                .putString(WlmUtil.HEADIMGURL,wxUserInfo.getHeadimgurl())
                .putString(WlmUtil.VIPVALIDITY,mLoginBean.getVipValidity())
                .putString(WlmUtil.USERLEVEL,mLoginBean.getUserLevel())
                .putString(WlmUtil.USERLEVELNAME,mLoginBean.getUserLevelName()).commit();

        UiHelper.launcher(this, MainFragmentActivity.class);
        finish();

    }


    @Override
    public void onLoginFail(String msg) {
        if (wxUserInfo == null){
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_微信登录";
            iwxapi.sendReq(req);
        }else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("wxinfo", wxUserInfo);
            UiHelper.launcherForResultBundle(this, RegisterActivity.class,0x1233, bundle);
        }
    }

    @Override
    public void showPromptMessage(int str) {
        toast(str);
    }

    @Override
    public void getUrlSuccess(UrlBean urlBean) {

            ProApplication.HEADIMG = urlBean.getImgUrl()+ ProApplication.IMG_SMALL;
            ProApplication.BANNERIMG = urlBean.getImgUrl() + ProApplication.IMG_BIG;
            ProApplication.CUSTOMERIMG = urlBean.getServiesUrl();
            ProApplication.SHAREDIMG = urlBean.getSharedWebUrl();
            ProApplication.REGISTERREQUIREMENTS = urlBean.getRegisterRequirements();
            SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
            sharedPreferences.edit().putString(WlmUtil.IMG, ProApplication.HEADIMG).putString(WlmUtil.BANNERIMG,ProApplication.BANNERIMG)
                    .putString(WlmUtil.CUSTOMER,ProApplication.CUSTOMERIMG).putString(WlmUtil.SHAREDIMG,ProApplication.SHAREDIMG).commit();

    }

    @Override
    public void getUrlFail(String msg) {

    }

    @Override
    public void setWxLoginSuccess(WxUserInfo wxSuccess) {
        this.wxUserInfo = wxSuccess;

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("wxinfo",wxUserInfo);
//        UiHelper.launcherBundle(this, RegisterActivity.class,bundle);
        loginPresenter.login(wxSuccess.getOpenid(),wxSuccess.getUnionid(),"2",ProApplication.SESSIONID(this));
    }

    @Override
    public void setWxLoginFail(String msg) {
        toast(msg);
    }
}
