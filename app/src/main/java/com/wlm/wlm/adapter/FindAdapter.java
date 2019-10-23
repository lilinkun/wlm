package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by LG on 2019/10/23.
 */
public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> strings;

    public FindAdapter(Context context,ArrayList<String> list){
        this.context = context;
        this.strings = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView){

        }
    }
}
