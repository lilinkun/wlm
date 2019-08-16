package com.wlm.wlm.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuetu-develop on 2017/9/27.
 */

public class ActivityUtil {

    public static List<Activity> activityList = new ArrayList<>();

    // 添加Activity
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    // 移除Activity
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    // 结束并移除最底的Activity
    public static void removeOldActivity(){
        activityList.get(0).finish();
        activityList.remove(0);
    }

    // 移除所有Activity
    public static void finishAll(){
        for (Activity activity : activityList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
