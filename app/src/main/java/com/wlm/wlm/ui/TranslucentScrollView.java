package com.wlm.wlm.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.wlm.wlm.interf.OnScrollChangedListener;

/**
 * Created by LG on 2018/11/20.
 */

public class TranslucentScrollView extends ScrollView {

    private OnScrollChangedListener mOnScrollChangedListener;

    public TranslucentScrollView(Context context) {
        super(context);
    }

    public TranslucentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TranslucentScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(OnScrollChangedListener mOnScrollChangedListener){
        this.mOnScrollChangedListener = mOnScrollChangedListener;
    }

    private int scroll_y;  //ScrollView的滑动距离
    @Override
    protected void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, scrollX, scrollY, oldScrollX, oldScrollY);
        }
        scroll_y = scrollY;  //监听赋值，监听scrollView的滑动状态，当滑动到顶部的时候才可以下拉刷新
        if(scrollY == 0){
        }else if(scrollY+this.getMeasuredHeight() == this.getChildAt(0).getMeasuredHeight()){
            //滑动距离+scrollView的高度如果等于scrollView的内部子view的高度则证明滑动到了底部，则自动加载更多数据
            if (mOnScrollChangedListener != null) {
                mOnScrollChangedListener.loadMore();  //加载更多
            }
        }
    }
}
