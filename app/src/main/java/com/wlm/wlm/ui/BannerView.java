package com.wlm.wlm.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xw.banner.Banner;


/**
 * Created by LG on 2018/12/26.
 */

public class BannerView extends Banner {

    private int lastX;
    private int lastY;

    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - lastY;
                int deltaX = x - lastX;
                if (Math.abs(deltaX) < Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
