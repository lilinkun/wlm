package com.wlm.wlm.base;

import android.support.v7.widget.RecyclerView;

/**
 * Created by LG on 2019/11/4.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected boolean isScrolling = false;


    public void setScrolling(boolean scrolling){
        isScrolling = scrolling;
    }

}
