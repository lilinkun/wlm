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
import com.wlm.wlm.ui.CustomRegisterLayout;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.LzyydUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/31.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_timer)
    TextView tv_timer;
    @BindView(R.id.ll_splash)
    LinearLayout ll_splash;

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(5000, 1000);

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initEventAndData() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
//        getSupportActionBar().hide();//隐藏标题栏

        myCountDownTimer.start();

    }

    @OnClick({R.id.ll_splash})
    public void onClick(View view) {
        if (!ButtonUtils.isFastDoubleClick(view.getId())) {
            switch (view.getId()) {
                case R.id.ll_splash:
                    myCountDownTimer.cancel();
                    Intent intent = null;
                    SharedPreferences sharedPreferences = getSharedPreferences(LzyydUtil.LOGIN, MODE_PRIVATE);
                    if (sharedPreferences.getBoolean(LzyydUtil.LOGIN, false) == true) {
                        intent = new Intent(getBaseContext(), MainFragmentActivity.class);
                    } else {
                        intent = new Intent(getBaseContext(), LoginActivity.class);
                    }
                    //启动MainActivity
                    startActivity(intent);
                    finish();

                    break;
            }
        }
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
            Intent intent = null;
            SharedPreferences sharedPreferences = getSharedPreferences(LzyydUtil.LOGIN, MODE_PRIVATE);
            if (sharedPreferences.getBoolean(LzyydUtil.LOGIN, false) == true) {
                intent = new Intent(getBaseContext(), MainFragmentActivity.class);
            } else {
                intent = new Intent(getBaseContext(), LoginActivity.class);
            }
            //启动MainActivity
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

