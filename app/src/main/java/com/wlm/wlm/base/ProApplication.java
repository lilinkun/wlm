package com.wlm.wlm.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;

import com.wlm.wlm.util.DeviceData;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by LG on 2018/11/29.
 */

public class ProApplication extends Application {
    private static Context mContext;
    private static ProApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        displayMetrics.scaledDensity = displayMetrics.density;
        disableAPIDialog();
    }

    /**
     * 反射 禁止弹窗
     */
    private void disableAPIDialog() {
        if (Build.VERSION.SDK_INT < 28) return;
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    public static String SESSIONID(Context mContext) {
//      return  "uihxp1el51ygggixaa413zps";
        return "wlm" + DeviceData.getUniqueId(mContext);
    }

    public static String HEADIMG = "";

    public static String BANNERIMG = "";

    public static String HOMEADDRESS = "";

    public static String CUSTOMERIMG = "";

    public static String SHAREDIMG = "";

    public static String SHAREDMEIMG = "";

    public static String REGISTERREQUIREMENTS = "";

    public static String UPGRADEURL = "";

    public static String UPGRADETOKEN = "";

    public static String VIPVALIDITY = "";

    public static int USERLEVEL = 0;

    public static String PHONE = "";

    public static String SERVIESVIP = "";

    public static String LOGISTICSURL = "";

    public static Boolean isAudinLogin = false;


    public static final String IMG_BIG = "imgdb/";
    public static final String IMG_HOME_ADDRESS = "imgdb/velet/";
    public static final String IMG_SMALL = "img/300/300/";
    public static final String IMG_SMALL_ = "img/150/150/";


    public static synchronized ProApplication context() {
        return (ProApplication) mContext;
    }
}
