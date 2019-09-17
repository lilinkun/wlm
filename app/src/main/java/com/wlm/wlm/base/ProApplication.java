package com.wlm.wlm.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.util.Log;

import com.wlm.wlm.util.DeviceData;

/**
 * Created by LG on 2018/11/29.
 */

public class ProApplication extends Application{
    private static Context mContext;
    private static ProApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        displayMetrics.scaledDensity = displayMetrics.density;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static ProApplication getInstance() {
        return instance;
    }


    public static String SESSIONID(Context mContext){
//      return  "wlm06afbb052494856945c98f32d8be2c45";
      return  "wlm" + DeviceData.getUniqueId(mContext);
    }

    public static String HEADIMG = "";

    public static String BANNERIMG = "";

    public static synchronized ProApplication context() {
        return (ProApplication) mContext;
    }
}
