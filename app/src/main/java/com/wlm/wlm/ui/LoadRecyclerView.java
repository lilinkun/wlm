package com.wlm.wlm.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class LoadRecyclerView extends RecyclerView {
    private RecyclerViewScrollListener rvScrollListener;

    public LoadRecyclerView(Context context) {
        this(context, null);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    // 初始化方法
    private void init() {
        rvScrollListener = new RecyclerViewScrollListener();
        this.addOnScrollListener(rvScrollListener);
    }

    // 设置是否加载更多
    public void setLoading(boolean bool) {
        rvScrollListener.setLoadingMore(bool);
    }

    // 加载更多
    public void setOnLoadListener(RecyclerViewScrollListener.OnLoadListener onLoadListener) {
        if (rvScrollListener != null)
            rvScrollListener.setOnLoadListener(onLoadListener);
    }
}
