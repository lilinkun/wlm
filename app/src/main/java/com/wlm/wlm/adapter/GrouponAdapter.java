package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wlm.wlm.R;
import com.wlm.wlm.ui.MyTextView;

/**
 * 拼团adapter
 * Created by LG on 2019/8/20.
 */
public class GrouponAdapter extends RecyclerView.Adapter<GrouponAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private OnItemClickListener mItemClickListener;

    public GrouponAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(context).inflate(R.layout.adapter_groupon,parent,false);

        ViewHolder viewHolder = new ViewHolder(v);

        v.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setTag(position);
        holder.tv_groupon_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        MyTextView tv_groupon_old_price;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_groupon_old_price = itemView.findViewById(R.id.tv_groupon_old_price);
        }
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
