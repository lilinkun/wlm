package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

/**
 * Created by LG on 2018/11/16.
 */

public class TbHotGoodsAdapter extends RecyclerView.Adapter<TbHotGoodsAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<GoodsListBean> hotHomeBeans = null;
    private OnItemClickListener onItemClickListener;
    private GoodsListBean homeBean;
    private boolean isAdd_Integral = false;

    public TbHotGoodsAdapter(Context context, ArrayList<GoodsListBean> homeHeadBean, LayoutInflater mInflater) {
        this.hotHomeBeans = homeHeadBean;
        this.context = context;
        this.mInflater = mInflater;
    }

    public void setData(ArrayList<GoodsListBean> hotHomeBeans){
        this.hotHomeBeans = hotHomeBeans;
        notifyDataSetChanged();
    }

    public void setAdd_Integral(){
        isAdd_Integral = true;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.adapter_hot_goods_grid, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        view.setOnClickListener(this);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemView.setTag(position);

        if (hotHomeBeans != null) {

            homeBean = hotHomeBeans.get(position);

            holder.goodsTitleNameTv.setText(homeBean.getGoodsName());

            holder.goodsPriceTv.setText("" + homeBean.getPrice());
            holder.goodsBuyCountTv.setText(homeBean.getUseNumber() + "");

            holder.tv_add_integral.setText(homeBean.getIntegral()+"");

            holder.tv_old_price.setText("¥"+ WlmUtil.getPriceNum(homeBean.getMarketPrice()));
            holder.tv_old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

            if (isAdd_Integral){
                holder.ll_add_integral.setVisibility(View.VISIBLE);
            }

            if (Integer.valueOf(homeBean.getGoodsType()) == WlmUtil.GOODSTYPE_POINT){
                holder.ll_bottom.setVisibility(View.GONE);
                holder.ll_bottom_point.setVisibility(View.VISIBLE);
                holder.tv_point_price.setText(homeBean.getPrice()+"");
                holder.tv_point_old_price.setText("原价：¥"+homeBean.getMarketPrice());
                holder.tv_point_old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText("HOT");
            }else if (Integer.valueOf(homeBean.getGoodsType()) == WlmUtil.GOODSTYPE_BEAUTY_HEALTH){
                holder.ll_bottom.setVisibility(View.GONE);
                holder.ll_bottom_health.setVisibility(View.VISIBLE);
                holder.tv_health_price.setText(homeBean.getPrice()+"");
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText("热销款");
            }else if (Integer.valueOf(homeBean.getGoodsType()) == WlmUtil.GOODSTYPE_WLMBUY){
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText("爆款");
            }

            if (homeBean.getGoodsImg() != null && !homeBean.getGoodsImg().isEmpty()) {
                Picasso.with(context).load(ProApplication.HEADIMG + homeBean.getGoodsImg()).error(R.mipmap.ic_adapter_error).into(holder.goodsPicImg);
            }
        }
    }

    @Override
    public int getItemCount() {
        return hotHomeBeans != null ? hotHomeBeans.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener!=null){
            onItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView goodsTitleNameTv;
        private TextView goodsPriceTv;
        private TextView goodsBuyCountTv;
        private TextView tv_point_price;
        private TextView tv_point_old_price;
        private TextView tv_health_price;
        private CustomRoundAngleImageView goodsPicImg;
        private LinearLayout ll_add_integral;
        private LinearLayout ll_bottom;
        private LinearLayout ll_bottom_point;
        private LinearLayout ll_bottom_health;
        private LinearLayout ll_hot;
        private TextView tv_add_integral;
        private TextView tv_hot;
        private TextView tv_old_price;

        public MyViewHolder(View itemView) {
            super(itemView);
            goodsBuyCountTv = (TextView) itemView.findViewById(R.id.tv_buy_count);
            tv_add_integral = (TextView) itemView.findViewById(R.id.tv_add_integral);
            goodsTitleNameTv = (TextView) itemView.findViewById(R.id.tv_goods_title_name);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.tv_goods_price);
            goodsPicImg = (CustomRoundAngleImageView) itemView.findViewById(R.id.iv_goods_pic);
            ll_add_integral = (LinearLayout) itemView.findViewById(R.id.ll_add_integral);
            tv_point_old_price = (TextView) itemView.findViewById(R.id.tv_point_old_price);
            tv_health_price = (TextView) itemView.findViewById(R.id.tv_health_price);
            tv_point_price = (TextView) itemView.findViewById(R.id.tv_point_price);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);
            tv_hot = (TextView) itemView.findViewById(R.id.tv_hot);
            ll_bottom = (LinearLayout) itemView.findViewById(R.id.ll_bottom);
            ll_bottom_point = (LinearLayout) itemView.findViewById(R.id.ll_bottom_point);
            ll_bottom_health = (LinearLayout) itemView.findViewById(R.id.ll_bottom_health);
            ll_hot = (LinearLayout) itemView.findViewById(R.id.ll_hot);

        }
    }
}
