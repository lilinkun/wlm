package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.wlm.wlm.ui.MyTextView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/27.
 */

public class MemberShipAdapter extends RecyclerView.Adapter<MemberShipAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<SelfGoodsBean> selfGoodsBeans;
    private OnItemClickListener mItemClickListener;

    public MemberShipAdapter(Context context, ArrayList<SelfGoodsBean> selfGoodsBeans) {
        this.context = context;
        this.selfGoodsBeans = selfGoodsBeans;
    }

    public void setData(ArrayList<SelfGoodsBean> selfGoodsBeans) {
        this.selfGoodsBeans = selfGoodsBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_membership, null);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        SelfGoodsBean selfGoodsBean = selfGoodsBeans.get(position);

        holder.tx_goods_title.setText(selfGoodsBean.getGoods_name());
        holder.tx_goods_msg.setText(selfGoodsBean.getShop_price() + "");
        holder.tv_goods_sales_volume.setText(selfGoodsBean.getUse_number() + "人已买");
        holder.tv_original_price.setText(selfGoodsBean.getShop_name());
        holder.tv_integral.setText("可用积分抵扣" + selfGoodsBean.getGive_integral());

        Picasso.with(context).load(ProApplication.HEADIMG + selfGoodsBean.getGoods_img()).into(holder.iv_goods_pic);

    }

    @Override
    public int getItemCount() {
        return selfGoodsBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CustomRoundAngleImageView iv_goods_pic;
        private TextView tx_goods_title, tv_original_price, tv_goods_sales_volume, tv_integral;
        private MyTextView tx_goods_msg;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_goods_pic = (CustomRoundAngleImageView) itemView.findViewById(R.id.iv_goods_pic);
            tx_goods_msg = (MyTextView) itemView.findViewById(R.id.tx_goods_msg);
            tx_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
            tv_original_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tv_goods_sales_volume = (TextView) itemView.findViewById(R.id.tv_goods_sales_volume);
            tv_integral = (TextView) itemView.findViewById(R.id.tv_integral);
        }
    }
}
