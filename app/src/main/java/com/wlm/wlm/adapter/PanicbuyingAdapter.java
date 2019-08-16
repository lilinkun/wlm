package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.RushBuyActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.RushBuyBean;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by LG on 2019/1/2.
 */

public class PanicbuyingAdapter extends RecyclerView.Adapter<PanicbuyingAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<RushBuyBean> rushBuyBeans;
    private int isType = 0;
    private OnItemClickListener mItemClickListener;

    public PanicbuyingAdapter(Context context,ArrayList<RushBuyBean> rushBuyBeans,int isType){
        this.mContext = context;
        this.rushBuyBeans = rushBuyBeans;
        this.isType = isType;
    }

    public void setData(ArrayList<RushBuyBean> rushBuyBeans){
        this.rushBuyBeans = rushBuyBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_panicbuying,null);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setTag(position);
        RushBuyBean rushBuyBean = rushBuyBeans.get(position);

        double shopPrice = rushBuyBean.getShop_price();
        double marketPrice = rushBuyBean.getMarket_price();
        BigDecimal b = new BigDecimal(shopPrice);
        shopPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        BigDecimal c = new BigDecimal(marketPrice);
        marketPrice = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        holder.tv_goods_price.setText("¥" + shopPrice);
        holder.tv_goods_title.setText(rushBuyBean.getGoods_name());
        holder.tv_integral.setText("可用积分抵扣" + rushBuyBean.getGive_integral());
        if (isType == 0) {
            if (rushBuyBean.getGoods_number() == 0) {
                holder.tv_rush_buy.setText("已售罄");
                holder.tv_rush_buy.setSelected(false);
                holder.iv_sell_out.setVisibility(View.VISIBLE);
            } else {
                holder.tv_rush_buy.setText("立即抢购");
                holder.tv_rush_buy.setSelected(true);
                holder.iv_sell_out.setVisibility(View.GONE);
            }
            holder.tv_endTime.setText("结束时间:" + rushBuyBean.getEnd_date());
        }else {
            holder.tv_endTime.setText("开始时间:" + rushBuyBean.getBegin_date());
            holder.tv_rush_buy.setVisibility(View.GONE);
        }

        Picasso.with(mContext).load(ProApplication.HEADIMG + rushBuyBeans.get(position).getGoods_img()).into(holder.roundAngleImageView);
    }

    @Override
    public int getItemCount() {
        return rushBuyBeans.size();
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

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_goods_price;
        private CustomRoundAngleImageView roundAngleImageView;
        private TextView tv_goods_title;
        private TextView tv_endTime;
        private TextView tv_integral;
        private TextView tv_rush_buy;
        private ImageView iv_sell_out;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            roundAngleImageView = (CustomRoundAngleImageView) itemView.findViewById(R.id.iv_goods_pic);
            tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
            tv_endTime = (TextView) itemView.findViewById(R.id.tv_endTime);
            tv_integral = (TextView) itemView.findViewById(R.id.tv_integral);
            tv_rush_buy = (TextView) itemView.findViewById(R.id.tv_rush_buy);
            iv_sell_out = (ImageView) itemView.findViewById(R.id.iv_sell_out);
        }
    }
}
