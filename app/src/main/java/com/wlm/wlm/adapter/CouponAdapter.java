package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.TbGoodsBean;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.squareup.picasso.Picasso;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2018/11/21.
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {

    Context context;
    List<TbGoodsBean> tbGoodsBeans = null;

    public CouponAdapter(Context context,List<TbGoodsBean> tbGoodsBeans){
        this.context = context;
        this.tbGoodsBeans = tbGoodsBeans;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_coupon_item,null);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(this.context).load(ProApplication.HEADIMG + tbGoodsBeans.get(position).getPic()).resize(300, 300).centerCrop().config(Bitmap.Config.RGB_565).into(holder.img_goods_icon);
        holder.tx_goods_msg.setText("¥" + tbGoodsBeans.get(position).getYedh_price() + "");
        holder.tx_goods_title.setText(tbGoodsBeans.get(position).getD_title() + "");
        holder.tv_original_price.setText(tbGoodsBeans.get(position).getYuanjia() + "");
        holder.tv_create_time.setText(tbGoodsBeans.get(position).getUpdatetime());
        holder.tv_coupon_price.setText(tbGoodsBeans.get(position).getDh_sm() + "");
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return tbGoodsBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public CustomRoundAngleImageView img_goods_icon;
        public TextView tx_goods_msg,tx_goods_title,tv_original_price,tv_create_time,tv_coupon_price;

        public ViewHolder(View itemView) {
            super(itemView);
            img_goods_icon = (CustomRoundAngleImageView) itemView.findViewById(R.id.img_goods_icon);
            tx_goods_msg = (TextView) itemView.findViewById(R.id.tx_goods_msg);
            tx_goods_title = (TextView) itemView.findViewById(R.id.tx_goods_title);
            tv_original_price = (TextView) itemView.findViewById(R.id.tv_original_price);
            tv_create_time = (TextView) itemView.findViewById(R.id.tv_create_time);
            tv_coupon_price = (TextView) itemView.findViewById(R.id.tv_coupon_price);
        }
    }

}
