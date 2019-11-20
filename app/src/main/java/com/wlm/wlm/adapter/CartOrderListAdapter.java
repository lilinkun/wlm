package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.CartBuyBean;
import com.wlm.wlm.entity.RightNowBuyBean;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/18.
 */

public class CartOrderListAdapter extends RecyclerView.Adapter<CartOrderListAdapter.ViewHolder> {

    private Context context;
    private RightNowBuyBean<CartBuyBean> buyBeans;
    private CartBuyBean cartBuyBean;
    private String goodsIds = "";
    private String storeIds = "";
    private String spec = "";
    private int num = 0;
    private ArrayList<TextView> textViews = new ArrayList<>();
    private int point = 0;
    private OnDataGetFare onDataGetFare;

    public CartOrderListAdapter(Context context, RightNowBuyBean<CartBuyBean> buyBeans, OnDataGetFare onDataGetFare) {
        this.context = context;
        this.buyBeans = buyBeans;
        this.onDataGetFare = onDataGetFare;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_groupon_order, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        cartBuyBean = buyBeans.getList().get(position);


        holder.tv_goods_title.setText(cartBuyBean.getGoodsName());
        holder.tv_coupon_price.setText("x" + cartBuyBean.getNum());
        if (cartBuyBean.getQty() == 2) {
            holder.tv_goods_spec1.setText(cartBuyBean.getSpec1());
            holder.tv_goods_spec2.setText(" " + cartBuyBean.getSpec2());
        } else if (cartBuyBean.getQty() == 1) {
            holder.tv_goods_spec1.setText(cartBuyBean.getSpec1());
        }

        Picasso.with(context).load(ProApplication.HEADIMG + cartBuyBean.getGoodsImg()).error(R.mipmap.ic_adapter_error).into(holder.iv_goods_pic);

        holder.tv_goods_price.setText("¥" + cartBuyBean.getPrice());


    }


    @Override
    public int getItemCount() {
        return buyBeans.getList().size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_goods_pic;
        TextView tv_goods_title;
        TextView tv_goods_spec1;
        TextView tv_goods_spec2;
        TextView tv_goods_price;
        TextView tv_coupon_price;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_goods_pic = itemView.findViewById(R.id.iv_goods_pic);
            tv_goods_title = itemView.findViewById(R.id.tv_goods_title);
            tv_goods_spec1 = itemView.findViewById(R.id.tv_goods_spec1);
            tv_goods_spec2 = itemView.findViewById(R.id.tv_goods_spec2);
            tv_goods_price = itemView.findViewById(R.id.tv_goods_price);
            tv_coupon_price = itemView.findViewById(R.id.tv_coupon_price);
        }
    }

    public interface OnDataGetFare {
        public void onPoint(int point, int position, int changint);

        public void onOrderFares();
    }
}

