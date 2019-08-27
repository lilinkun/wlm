package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.util.VipEnum;

/**
 * Created by LG on 2019/8/26.
 */

public class VipAdapter extends RecyclerView.Adapter<VipAdapter.ViewHolder> {

    private Context context;

    public VipAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_vip,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(VipEnum.getVipByValue(position).getStatusMsg());
        holder.imageView.setImageResource(VipEnum.getVipByValue(position).getDrawId());
        if (VipEnum.getVipByValue(position).isVisible()) {
            holder.sangImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return VipEnum.values().length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        ImageView sangImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_adapter_vip);
            textView = itemView.findViewById(R.id.tv_adapter_vip);
            sangImageView = itemView.findViewById(R.id.iv_triangle);

        }
    }
}
