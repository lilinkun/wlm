package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.activity.GrouponOrderActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.ui.CountdownView;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.wlm.wlm.ui.MyTextView;
import com.wlm.wlm.ui.PriceTextView;
import com.wlm.wlm.ui.UpdataCFProgressBar;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

/**
 * 拼团adapter
 * Created by LG on 2019/8/20.
 */
public class GrouponAdapter extends RecyclerView.Adapter<GrouponAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private OnItemClickListener mItemClickListener;
    private ArrayList<GoodsListBean> goodsListBeans;
    private int goodstype;

    public GrouponAdapter(Context context, ArrayList<GoodsListBean> goodsListBeans,int goodsType) {
        this.context = context;
        this.goodsListBeans = goodsListBeans;
        this.goodstype = goodsType;
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
        holder.tv_groupon_old_price.setText("¥"+goodsListBeans.get(position).getMarketPrice());
        holder.tv_groupon_price.setText(goodsListBeans.get(position).getPrice()+"");
        holder.tv_grounon_info.setText(goodsListBeans.get(position).getTeamTypeName());

        holder.tv_goods_title.setText(goodsListBeans.get(position).getGoodsName());

        if (goodsListBeans.get(position).getGoodsImg() != null && !goodsListBeans.get(position).getGoodsImg().isEmpty()) {
            Picasso.with(context).load(ProApplication.HEADIMG +goodsListBeans.get(position).getGoodsImg()).error(R.mipmap.ic_adapter_error).into(holder.iv_goods_pic);
        }


        if (goodstype == WlmUtil.GOODSTYPE_CROWDFUNDING){
            holder.rl_groupon.setVisibility(View.GONE);
            holder.tv_rush_time.setVisibility(View.GONE);
            holder.tv_rush_time_flash_sale.setVisibility(View.VISIBLE);
            holder.tv_grounon_info.setVisibility(View.GONE);
            countDownTime(goodsListBeans.get(position).getBeginDate(),goodsListBeans.get(position).getEndDate(),holder.tv_rush_time_flash_sale,holder.tv_grouponing,holder.tv_end_time);
            holder.ll_crowdfunding.setVisibility(View.VISIBLE);
            holder.tv_crowd_price.setText(goodsListBeans.get(position).getPrice()*goodsListBeans.get(position).getUseNumber() + "");
            holder.tv_support_count.setText(goodsListBeans.get(position).getUseNumber() + "");
        }else if (goodstype == WlmUtil.GOODSTYPE_SECKILL){
            holder.tv_grouponing.setText("立即秒杀");
            holder.tv_rush_time.setVisibility(View.GONE);
            holder.tv_rush_time_flash_sale.setVisibility(View.VISIBLE);
            holder.tv_grounon_info.setVisibility(View.GONE);
            if (goodsListBeans.get(position).getUserLevel() > 0) {
                holder.ll_vip_sale.setVisibility(View.VISIBLE);
            }
            if (goodsListBeans.get(position).getUserLevel() > ProApplication.USERLEVEL){
                holder.tv_grouponing.setEnabled(false);
                holder.tv_grouponing.setBackgroundResource(R.drawable.shape_flash_sale_unseclect);
            }else {
                holder.tv_grouponing.setBackgroundResource(R.drawable.shape_flash_sale_seclect);
            }
            countDownTime(goodsListBeans.get(position).getBeginDate(),goodsListBeans.get(position).getEndDate(),holder.tv_rush_time_flash_sale,holder.tv_grouponing,holder.tv_end_time);
        }else if (goodstype == WlmUtil.GOODSTYPE_GROUPON){
            countDownTime(goodsListBeans.get(position).getBeginDate(),goodsListBeans.get(position).getEndDate(),holder.tv_rush_time,holder.tv_grouponing,holder.tv_end_time);
        }

        holder.tv_grouponing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putSerializable(WlmUtil.GOODSID,goodsListBeans.get(position).getGoodsId());
                bundle.putInt(WlmUtil.GOODSNUM,1);
                bundle.putString(WlmUtil.ATTRID,"");
                bundle.putString(WlmUtil.TYPE,WlmUtil.GROUPONGOODS);
                UiHelper.launcherBundle(context, GrouponOrderActivity.class,bundle);
            }
        });
    }

    private void countDownTime(String startTime,String endTime,CountdownView tv_rush_time,TextView tv_grouponing,TextView tv_end_time){

        if(WlmUtil.isCountdown(startTime,endTime,tv_rush_time) == 0){
            tv_grouponing.setVisibility(View.GONE);
            tv_end_time.setText("至开始");
        }else if (WlmUtil.isCountdown(startTime,endTime,tv_rush_time) == 1){
            tv_end_time.setText("后截止");
        }else {
            tv_grouponing.setVisibility(View.GONE);
        }
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        MyTextView tv_groupon_old_price;
        TextView tv_grouponing,tv_grounon_info,tv_goods_title,tv_end_time,tv_crowd_price,tv_support_count;
        CountdownView tv_rush_time,tv_rush_time_flash_sale;
        CustomRoundAngleImageView iv_goods_pic;
        PriceTextView tv_groupon_price;
        RelativeLayout rl_groupon;
        LinearLayout ll_crowdfunding;
        LinearLayout ll_vip_sale;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_groupon_old_price = itemView.findViewById(R.id.tv_groupon_old_price);
            tv_grouponing = itemView.findViewById(R.id.tv_grouponing);
            tv_grounon_info = itemView.findViewById(R.id.tv_grounon_info);
            tv_rush_time = itemView.findViewById(R.id.tv_rush_time);
            tv_rush_time_flash_sale = itemView.findViewById(R.id.tv_rush_time_flash_sale);
            iv_goods_pic = itemView.findViewById(R.id.iv_goods_pic);
            tv_groupon_price = itemView.findViewById(R.id.tv_groupon_price);
            tv_goods_title = itemView.findViewById(R.id.tv_goods_title);
            tv_end_time = itemView.findViewById(R.id.tv_end_time);
            rl_groupon = itemView.findViewById(R.id.rl_groupon);
            ll_crowdfunding = itemView.findViewById(R.id.ll_crowd_funding);
            tv_crowd_price = itemView.findViewById(R.id.tv_crowd_price);
            tv_support_count = itemView.findViewById(R.id.tv_support_count);
            ll_vip_sale = itemView.findViewById(R.id.ll_vip_sale);
        }
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
