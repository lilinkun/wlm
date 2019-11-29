package com.wlm.wlm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.SplashContract;
import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.presenter.SplashPresenter;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.WlmUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/31.
 */

public class SplashActivity extends BaseActivity implements SplashContract {

    @BindView(R.id.tv_timer)
    TextView tv_timer;
    @BindView(R.id.ll_splash)
    LinearLayout ll_splash;

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(1000, 1000);
    SplashPresenter splashPresenter = new SplashPresenter();

    private boolean isLogin = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initEventAndData() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
//        getSupportActionBar().hide();//隐藏标题栏

        splashPresenter.onCreate(this, this);
        splashPresenter.getUrl();
        myCountDownTimer.start();

    }

    @OnClick({R.id.ll_splash})
    public void onClick(View view) {
        if (!ButtonUtils.isFastDoubleClick(view.getId())) {
            switch (view.getId()) {
                case R.id.ll_splash:
                    myCountDownTimer.cancel();
                    turnHome();

                    break;
            }
        }
    }

    @Override
    public void getUrlSuccess(UrlBean urlBean) {

        ProApplication.HEADIMG = urlBean.getImgUrl() + ProApplication.IMG_SMALL;
        ProApplication.BANNERIMG = urlBean.getImgUrl() + ProApplication.IMG_BIG;
        ProApplication.CUSTOMERIMG = urlBean.getServiesUrl();
        ProApplication.SHAREDIMG = urlBean.getSharedWebUrl();
        ProApplication.REGISTERREQUIREMENTS = urlBean.getRegisterRequirements();
        if (urlBean.getIsAndroidAuditing() == 1) {
            isLogin = true;
        }
        turnHome();
    }

    @Override
    public void getUrlFail(String msg) {
        turnHome();
    }

    /**
     * 获取验证码倒计时
     */
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {

            tv_timer.setText(l / 1000 + "");
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
//            turnHome();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void turnHome() {
        Intent intent = null;
        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(WlmUtil.LOGIN, false) == true) {
            intent = new Intent(getBaseContext(), MainFragmentActivity.class);
        } else {
            if (isLogin) {
                intent = new Intent(getBaseContext(), HaiWeiLoginActivity.class);
            } else {
                intent = new Intent(getBaseContext(), HaiWeiLoginActivity.class);
            }
        }
        //启动MainActivity
        startActivity(intent);
        finish();
    }
}

