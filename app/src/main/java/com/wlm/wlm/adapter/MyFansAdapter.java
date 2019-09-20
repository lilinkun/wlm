package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.entity.FansBean;
import com.wlm.wlm.ui.RoundImageView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/20.
 */
public class MyFansAdapter extends RecyclerView.Adapter<MyFansAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<FansBean> fansBeans;
    private OnItemClickListener mItemClickListener;


    public MyFansAdapter(Context context, ArrayList<FansBean> fansBeans){
        this.context = context;
        this.fansBeans = fansBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_fans,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    public void setData(ArrayList<FansBean> fansBeans){
        this.fansBeans = fansBeans;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(fansBeans.get(position).getPortrait() != null && !fansBeans.get(position).getPortrait().equals("")) {
            Picasso.with(context).load(fansBeans.get(position).getPortrait()).error(R.mipmap.ic_adapter_error).into(holder.riv_fans);
        }

        holder.tv_fans_name.setText(fansBeans.get(position).getNickName());
        holder.tv_create_time.setText(fansBeans.get(position).getActivationTime());
        holder.tv_status.setText(fansBeans.get(position).getUserLevelName());
    }

    @Override
    public int getItemCount() {
        return fansBeans.size();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        RoundImageView riv_fans;
        TextView tv_fans_name,tv_status,tv_create_time;


        public ViewHolder(View itemView) {
            super(itemView);
            riv_fans = itemView.findViewById(R.id.riv_fans);
            tv_fans_name = itemView.findViewById(R.id.tv_fans_name);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_create_time = itemView.findViewById(R.id.tv_create_time);

        }
    }
}
