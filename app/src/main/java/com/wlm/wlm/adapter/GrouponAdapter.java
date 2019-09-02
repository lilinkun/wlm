package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.GrouponDetailActivity;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.ui.CountdownView;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.wlm.wlm.ui.MyTextView;
import com.wlm.wlm.util.UiHelper;

import java.util.ArrayList;

/**
 * 拼团adapter
 * Created by LG on 2019/8/20.
 */
public class GrouponAdapter extends RecyclerView.Adapter<GrouponAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private OnItemClickListener mItemClickListener;
    private ArrayList<GoodsListBean> goodsListBeans;

    public GrouponAdapter(Context context, ArrayList<GoodsListBean> goodsListBeans) {
        this.context = context;
        this.goodsListBeans = goodsListBeans;
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
        holder.tv_groupon_old_price.setText("￥"+goodsListBeans.get(position).getMarketPrice());
        holder.tv_groupon_price.setText(goodsListBeans.get(position).getPrice());

        holder.tv_rush_time.start(1572310800);

//        holder.iv_goods_pic.

        holder.tv_grouponing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.launcher(context, GrouponDetailActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsListBeans.size();
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
        TextView tv_grouponing;
        CountdownView tv_rush_time;
        CustomRoundAngleImageView iv_goods_pic;
        TextView tv_groupon_price;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_groupon_old_price = itemView.findViewById(R.id.tv_groupon_old_price);
            tv_grouponing = itemView.findViewById(R.id.tv_grouponing);
            tv_rush_time = itemView.findViewById(R.id.tv_rush_time);
            iv_goods_pic = itemView.findViewById(R.id.iv_goods_pic);
            tv_groupon_price = itemView.findViewById(R.id.tv_groupon_price);
        }
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
