package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LG on 2019/9/17.
 */
public class GrouponDetailAdapter extends RecyclerView.Adapter<GrouponDetailAdapter.ViewHolder> {

    private Context context;


    public GrouponDetailAdapter(Context context){
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
