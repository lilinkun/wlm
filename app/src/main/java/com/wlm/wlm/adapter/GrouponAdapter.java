package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.GrouponDetailActivity;
import com.wlm.wlm.activity.OrderActivity;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.ui.CountdownView;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.wlm.wlm.ui.MyTextView;
import com.wlm.wlm.ui.PriceTextView;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.itemView.setTag(position);
        holder.tv_groupon_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_groupon_old_price.setText("￥"+goodsListBeans.get(position).getMarketPrice());
        holder.tv_groupon_price.setText(goodsListBeans.get(position).getPrice()+"");
        holder.tv_grounon_info.setText(goodsListBeans.get(position).getGoodsTypeName());

        holder.tv_goods_title.setText(goodsListBeans.get(position).getGoodsName());


        if(WlmUtil.isCountdown(goodsListBeans.get(position).getBeginDate(),goodsListBeans.get(position).getEndDate(),holder.tv_rush_time) == 0){
            holder.tv_grouponing.setVisibility(View.GONE);
            holder.tv_end_time.setText("至开始");
        }else if (WlmUtil.isCountdown(goodsListBeans.get(position).getBeginDate(),goodsListBeans.get(position).getEndDate(),holder.tv_rush_time) == 1){
            holder.tv_end_time.setText("后截止");
        }else {
            holder.tv_grouponing.setVisibility(View.GONE);
        }

//        holder.iv_goods_pic.

        holder.tv_grouponing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("groupongoods",goodsListBeans.get(position));
                UiHelper.launcherBundle(context, OrderActivity.class,bundle);
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

    public void setData(ArrayList<GoodsListBean> goodsListBeans){
        this.goodsListBeans = goodsListBeans;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        MyTextView tv_groupon_old_price;
        TextView tv_grouponing,tv_grounon_info,tv_goods_title,tv_end_time;
        CountdownView tv_rush_time;
        CustomRoundAngleImageView iv_goods_pic;
        PriceTextView tv_groupon_price;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_groupon_old_price = itemView.findViewById(R.id.tv_groupon_old_price);
            tv_grouponing = itemView.findViewById(R.id.tv_grouponing);
            tv_grounon_info = itemView.findViewById(R.id.tv_grounon_info);
            tv_rush_time = itemView.findViewById(R.id.tv_rush_time);
            iv_goods_pic = itemView.findViewById(R.id.iv_goods_pic);
            tv_groupon_price = itemView.findViewById(R.id.tv_groupon_price);
            tv_goods_title = itemView.findViewById(R.id.tv_goods_title);
            tv_end_time = itemView.findViewById(R.id.tv_end_time);
        }
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
