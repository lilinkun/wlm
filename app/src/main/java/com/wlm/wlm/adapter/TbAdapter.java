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
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by LG on 2018/12/21.
 */

public class TbAdapter extends RecyclerView.Adapter<TbAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<TbMaterielBean> tbMaterielBeans;
    private OnItemClickListener onItemClickListener;

    public TbAdapter(Context context,ArrayList<TbMaterielBean> tbMaterielBeans){
        this.context = context;
        this.tbMaterielBeans = tbMaterielBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_tb_goods,null);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        TbMaterielBean tbMaterielBean = tbMaterielBeans.get(position);

        holder.goodsTitleNameTv.setText(tbMaterielBeans.get(position).getTitle());

        holder.goodsBuyCountTv.setText(tbMaterielBeans.get(position).getVolume() + "");

//        holder.goodsCouponPrice.setText();
        String CouponAmount = "0";
        if (tbMaterielBean.getCouponInfo().contains("减") && tbMaterielBean.getCouponInfo().contains("元") ) {
            CouponAmount = tbMaterielBean.getCouponInfo().substring(tbMaterielBean.getCouponInfo().indexOf("减") + 1, tbMaterielBean.getCouponInfo().lastIndexOf("元"));
        }else {
            if (tbMaterielBean.getCouponInfo() != null) {
                CouponAmount = tbMaterielBean.getCouponInfo();
            }
        }
        tbMaterielBean.setCouponInfo(CouponAmount);

        BigDecimal zkFinalPrice = new BigDecimal(tbMaterielBean.getZkFinalPrice());
        BigDecimal couponInfo = new BigDecimal(CouponAmount);
        double couponPrice = zkFinalPrice.subtract(couponInfo).doubleValue();

        holder.goodsCouponPrice.setText(CouponAmount+"");
        holder.goodsPriceTv.setText("¥" + couponPrice);

        String pictUrl = tbMaterielBean.getPictUrl();
        if (pictUrl.startsWith("//")) {
            pictUrl = "https:" + tbMaterielBean.getPictUrl();
        }
        Picasso.with(this.context).load(pictUrl).resize(300, 300).centerCrop().config(Bitmap.Config.RGB_565).into((holder.goodsPicImg));
    }

    @Override
    public int getItemCount() {
        return tbMaterielBeans.size();
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

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView goodsTitleNameTv;
        private TextView goodsPriceTv;
        private TextView goodsBuyCountTv;
        private CustomRoundAngleImageView goodsPicImg;
        private TextView goodsCouponPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsBuyCountTv = (TextView) itemView.findViewById(R.id.tv_buy_count);
            goodsTitleNameTv = (TextView) itemView.findViewById(R.id.tv_goods_title_name);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.tv_goods_price);
            goodsPicImg = (CustomRoundAngleImageView) itemView.findViewById(R.id.iv_goods_pic);
            goodsCouponPrice = (TextView) itemView.findViewById(R.id.tv_coupon);
        }
    }
}
