package com.wlm.wlm.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by LG on 2018/11/13.
 * 界面帮助类
 */

public class UiHelper {

    public static void launcherForResult(Activity activity, Class<?> targetActivity, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void launcherForResult(Fragment fragment, Class<?> targetActivity, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(fragment.getActivity(), targetActivity);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void launcherForResultBundle(Fragment fragment, Class<?> targetActivity, int requestCode,Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(fragment.getContext(), targetActivity);
        intent.putExtra(WlmUtil.TYPEID,bundle);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void launcherForResultBundle(Activity activity, Class<?> targetActivity, int requestCode,Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        intent.putExtra(WlmUtil.TYPEID,bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void launcherBundle(Context context, Class<?> targetActivity,Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, targetActivity);
        intent.putExtra(WlmUtil.TYPEID,bundle);
        context.startActivity(intent);
    }

    public static void launcherBundle(Activity activity, Class<?> targetActivity,Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        intent.putExtra(WlmUtil.TYPEID,bundle);
        activity.startActivity(intent);
    }

    public static void launcher(Activity activity, Class<?> targetActivity) {
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        activity.startActivity(intent);
    }

    public static void launcher(Context activity, Class<?> targetActivity) {
        Intent intent = new Intent();
        intent.setClass(activity, targetActivity);
        activity.startActivity(intent);
    }

}
