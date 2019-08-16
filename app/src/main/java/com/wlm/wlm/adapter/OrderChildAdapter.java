package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.SelfOrderInfoBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/21.
 */

public class OrderChildAdapter extends RecyclerView.Adapter<OrderChildAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<SelfOrderInfoBean> childListBeans;
    private OnItemClickListener mItemClickListener;

    public OrderChildAdapter(Context context, ArrayList<SelfOrderInfoBean> childListBeans) {
        this.mContext = context;
        this.childListBeans = childListBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.child_item,null);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        holder.goodsTitle.setText(childListBeans.get(position).getGoods_name());

        holder.goodsPrice.setText("¥ " + childListBeans.get(position).getGoods_price());
        if (childListBeans.get(position).getGoods_attr_one() != null&& !childListBeans.get(position).getGoods_attr_one().isEmpty()) {
            holder.goodsSpec1.setText(childListBeans.get(position).getGoods_attr_one() + "");
        }
        if (childListBeans.get(position).getGoods_attr_two() != null && !childListBeans.get(position).getGoods_attr_two().isEmpty()) {
            holder.goodsSpec2.setText("，" + childListBeans.get(position).getGoods_attr_two());
        }
        holder.goodsnum.setText("X" + childListBeans.get(position).getGoods_number());
        Picasso.with(mContext).load(ProApplication.HEADIMG + childListBeans.get(position).getGoods_img()).into(holder.childImg);
    }

    @Override
    public int getItemCount() {
        return childListBeans.size();
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
        ImageView childImg;
        TextView goodsTitle;
        TextView goodsnum;
        TextView goodsPrice;
        TextView goodsSpec1;
        TextView goodsSpec2;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsTitle = itemView.findViewById(R.id.tv_goods_title);
            goodsnum = itemView.findViewById(R.id.tv_coupon_price);
            goodsPrice = itemView.findViewById(R.id.tv_goods_price);
            childImg = itemView.findViewById(R.id.iv_goods_pic);
            goodsSpec1 = itemView.findViewById(R.id.tv_goods_spec1);
            goodsSpec2 = itemView.findViewById(R.id.tv_goods_spec2);
        }
    }
}
