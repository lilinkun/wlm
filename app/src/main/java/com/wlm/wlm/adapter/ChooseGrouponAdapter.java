package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.util.GrouponType;
import com.wlm.wlm.util.MallType;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/3.
 */
public class ChooseGrouponAdapter extends RecyclerView.Adapter<ChooseGrouponAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private OnItemClickListener mItemClickListener;
    private int type = 0;


    public ChooseGrouponAdapter(Context context,int type){
        this.context = context;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_choose_groupon,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        if (type == 0) {
            holder.tv_groupon.setText(GrouponType.values()[position].getTypeName());
        }else {
            holder.tv_groupon.setText(MallType.values()[position].getTypeName());
        }
    }

    @Override
    public int getItemCount() {
        return type == 0 ? GrouponType.values().length : MallType.values().length;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_groupon;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_groupon = itemView.findViewById(R.id.tv_groupon);
        }
    }

    public void setOnItemClick(OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(int position);
    }

}
