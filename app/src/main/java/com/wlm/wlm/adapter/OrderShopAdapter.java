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
import com.wlm.wlm.entity.ChildListBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/15.
 */

public class OrderShopAdapter extends RecyclerView.Adapter<OrderShopAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<ChildListBean> childListBeans;
    private OnItemClickListener mItemClickListener;


    public OrderShopAdapter(Context context,ArrayList<ChildListBean> childListBeans){
        this.context = context;
        this.childListBeans = childListBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.child_item,null);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        holder.goodsTitle.setText(childListBeans.get(position).getGoods_name());

        holder.goodsPrice.setText("¥ " + childListBeans.get(position).getShop_price());
        holder.goodsSpec1.setText(childListBeans.get(position).getSpec1() + "");
        if (childListBeans.get(position).getSpec2() != null && !childListBeans.get(position).getSpec2().isEmpty()) {
            holder.goodsSpec2.setText("，" + childListBeans.get(position).getSpec2());
        }
        holder.goodsnum.setText("X" + childListBeans.get(position).getNum());

        holder.tv_pv.setText("pv值:" + childListBeans.get(position).getReturn_integral());
        Picasso.with(context).load(ProApplication.HEADIMG + childListBeans.get(position).getGoods_img()).into(holder.childImg);

    }

    @Override
    public int getItemCount() {
        return childListBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
        TextView tv_pv;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsTitle = itemView.findViewById(R.id.tv_goods_title);
            goodsnum = itemView.findViewById(R.id.tv_coupon_price);
            goodsPrice = itemView.findViewById(R.id.tv_goods_price);
            childImg = itemView.findViewById(R.id.iv_goods_pic);
            goodsSpec1 = itemView.findViewById(R.id.tv_goods_spec1);
            goodsSpec2 = itemView.findViewById(R.id.tv_goods_spec2);
            tv_pv = itemView.findViewById(R.id.tv_pv);
        }
    }
}
