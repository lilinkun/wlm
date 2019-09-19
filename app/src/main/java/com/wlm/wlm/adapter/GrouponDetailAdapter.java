package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.entity.JoinGrouponBean;
import com.wlm.wlm.ui.RoundImageView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/17.
 */
public class GrouponDetailAdapter extends RecyclerView.Adapter<GrouponDetailAdapter.ViewHolder> {

    private Context context;
    private ArrayList<JoinGrouponBean> joinGrouponBeans;

    public GrouponDetailAdapter(Context context, ArrayList<JoinGrouponBean> joinGrouponBeans){
        this.context = context;
        this.joinGrouponBeans = joinGrouponBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_groupondetail,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (joinGrouponBeans.get(position).getPortrait() != null && !joinGrouponBeans.get(position).getPortrait().isEmpty()) {
            Picasso.with(context).load(joinGrouponBeans.get(position).getPortrait()).error(R.mipmap.ic_adapter_error).into(holder.roundImageView);
        }
    }

    @Override
    public int getItemCount() {
        return joinGrouponBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private RoundImageView roundImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            roundImageView = itemView.findViewById(R.id.riv_groupon);
        }
    }

}
