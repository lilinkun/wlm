package com.wlm.wlm.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by LG on 2018/11/10.
 */
public class CustomViewPager extends ViewPager
{
    private boolean isCanScroll = false;

    public CustomViewPager(Context paramContext)
    {
        super(paramContext);
    }

    public CustomViewPager(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
        if (this.isCanScroll) {
            return super.onInterceptTouchEvent(paramMotionEvent);
        }
        return false;
    }

    public void setScanScroll(boolean paramBoolean)
    {
        this.isCanScroll = paramBoolean;
    }
}
