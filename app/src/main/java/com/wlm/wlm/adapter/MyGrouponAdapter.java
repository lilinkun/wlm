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
import com.wlm.wlm.entity.GrouponListBean;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/16.
 */
public class MyGrouponAdapter extends RecyclerView.Adapter<MyGrouponAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<GrouponListBean> grouponListBeans;
    private OnItemClickListener mItemClickListener;

    public MyGrouponAdapter(Context context, ArrayList<GrouponListBean> grouponListBeans){
        this.context = context;
        this.grouponListBeans = grouponListBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_mygroupon,null);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.itemView.setTag(position);

        holder.tv_goods_count.setText("X 1");
        holder.tv_goods_title.setText("" + grouponListBeans.get(position).getGoodsName());
        holder.tv_goods_price.setText("¥" + grouponListBeans.get(position).getPrice());

        if (grouponListBeans.get(position).getQty() == 2) {
            holder.tv_goods_spec1.setText(grouponListBeans.get(position).getGoodsSpec1() + " , ");
            holder.tv_goods_spec2.setText(grouponListBeans.get(position).getGoodsSpec2());
        }else if (grouponListBeans.get(position).getQty() == 1) {
            holder.tv_goods_spec1.setText(grouponListBeans.get(position).getGoodsSpec1());
        }

        Picasso.with(context).load(ProApplication.HEADIMG + grouponListBeans.get(position).getGoodsImg()).into(holder.iv_goods_pic);
    }

    @Override
    public int getItemCount() {
        return grouponListBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setData(ArrayList<GrouponListBean> selfOrderBeans){
        this.grouponListBeans = selfOrderBeans;
        notifyDataSetChanged();
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

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_goods_count;
        private TextView tv_goods_title;
        private ImageView iv_goods_pic;
        private TextView tv_goods_price;
        private TextView tv_goods_spec1;
        private TextView tv_goods_spec2;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_goods_count = (TextView) itemView.findViewById(R.id.tv_goods_count);
            tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
            iv_goods_pic = (ImageView) itemView.findViewById(R.id.iv_goods_pic);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tv_goods_spec1 = (TextView) itemView.findViewById(R.id.tv_goods_spec1);
            tv_goods_spec2 = (TextView) itemView.findViewById(R.id.tv_goods_spec2);
        }
    }

}
