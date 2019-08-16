package com.wlm.wlm.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LG on 2018/12/14.
 */

public class CommendRecyclerView extends RecyclerView {
    public CommendRecyclerView(Context context) {
        super(context);
    }

    public CommendRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CommendRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();

        setNestedScrollingEnabled(false);

    }
}
