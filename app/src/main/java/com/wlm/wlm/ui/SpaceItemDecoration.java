package com.wlm.wlm.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置recyclerview的间距
 * Created by LG on 2019/8/9.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;//声明间距 //使用构造函数定义间距
    private int bottomSpace;//声明底部间距
    private int topSpace;//声明头部间距

    public SpaceItemDecoration(int space, int bottomSpace, int topSpace) {
        this.space = space;
        this.bottomSpace = bottomSpace;
        this.topSpace = topSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //获得当前item的位置
        int position = parent.getChildAdapterPosition(view);
        //根据position确定item需要留出的位置
        switch (position % 2) {
            case 0:
                //位于左侧的item
                outRect.right = this.space;
                break;
            case 1:
                //位于右侧的item
                outRect.left = this.space;
                break;
            default:
                break;
        }
        //设置底部边距
        outRect.bottom = this.bottomSpace;

        if (position == 0) {
            outRect.top = this.topSpace;
        }

    }
}