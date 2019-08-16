package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.entity.TbGoodsBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.ui.MyTextView;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LG on 2018/11/21.
 */

public class TbGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<TbGoodsBean> tbGoodsBeans = new ArrayList<>();
    private LayoutInflater mInflater;
    private ArrayList<TbMaterielBean> tbShopBeans = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private TbMaterielBean tbDisCountBean;
    private HashMap<String,TbMaterielBean> mHashMap = new HashMap<>();
    private int normalType = 0;
    private int footType = 1;
    private boolean hasMore = true;
    private boolean fadeTips = false;

    public TbGoodsAdapter(Context context, ArrayList<TbMaterielBean> tbShopBeans, LayoutInflater mInflater) {
        this.context = context;
        this.tbGoodsBeans = tbGoodsBeans;
        this.mInflater = mInflater;
        this.tbShopBeans = tbShopBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType) {
            View view = mInflater.inflate(R.layout.adapter_goods_item, null);

            ViewHolder viewHolder = new ViewHolder(view);

            view.setOnClickListener(this);

            return viewHolder;

        }else {
            return new FootHolder(mInflater.inflate(R.layout.footview, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            tbDisCountBean = tbShopBeans.get(position);
            holder.itemView.setTag(position);

            mHashMap.put(position + "", tbDisCountBean);

            String pictUrl = tbDisCountBean.getPictUrl();
            if (pictUrl.startsWith("//")) {
                pictUrl = "https:" + tbDisCountBean.getPictUrl();
            }
            Picasso.with(this.context).load(pictUrl).resize(300, 300).centerCrop().config(Bitmap.Config.RGB_565).into(((ViewHolder)holder).img_goods_icon);
            ((ViewHolder)holder).tx_goods_title.setText(tbDisCountBean.getTitle() + "");
            String CouponAmount = "0";
            if (tbDisCountBean.getCouponInfo().contains("减") && tbDisCountBean.getCouponInfo().contains("元") ) {
                CouponAmount = tbDisCountBean.getCouponInfo().substring(tbDisCountBean.getCouponInfo().indexOf("减") + 1, tbDisCountBean.getCouponInfo().lastIndexOf("元"));
            }else {
                if (tbDisCountBean.getCouponInfo() != null && !tbDisCountBean.getCouponInfo().equals("")) {
                    CouponAmount = tbDisCountBean.getCouponInfo();
                }
            }
            tbDisCountBean.setCouponInfo(CouponAmount);

            String ZkFinalPrice = tbDisCountBean.getZkFinalPrice();
            if (ZkFinalPrice.equals("")){
                ZkFinalPrice = "0";
            }
            BigDecimal zkFinalPrice = new BigDecimal(ZkFinalPrice);

            BigDecimal couponInfo = new BigDecimal(CouponAmount);
            double couponPrice = zkFinalPrice.subtract(couponInfo).doubleValue();
            String price = String.valueOf(couponPrice);
            if (couponPrice > 0 && price.endsWith(".0")){
                price = price.substring(0,price.length() - 2);
            }
            /*if(Math.round(couponPrice) - couponPrice == 0){
                price = String.valueOf((long) couponPrice);
            }else {
                price = String.valueOf(couponPrice);
            }*/

            ((ViewHolder)holder).tx_goods_msg.setText("¥" + price + "");
            ((ViewHolder)holder).tv_original_price.setText("¥" + tbDisCountBean.getZkFinalPrice() + "");
            ((ViewHolder)holder).tv_goods_sales_volume.setText(tbDisCountBean.getVolume() + "人已买");
            ((ViewHolder)holder).tv_coupon_price.setText(CouponAmount + "元券");

            if (Integer.valueOf(tbDisCountBean.getUserType()) == 1) {
                ((ViewHolder)holder).tv_goods_type_price.setText("天猫价");
            } else {
                ((ViewHolder)holder).tv_goods_type_price.setText("淘宝价");
            }
        }else {
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
//            if (hasMore == true) {
            fadeTips = false;
            if (tbShopBeans.size() > 0) {
                ((FootHolder) holder).tips.setText("正在加载更多...");
            }
//            } else {
//                if (tbShopBeans.size() > 0) {
//                    ((FootHolder) holder).tips.setText("没有更多数据了");
//                    mHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            ((FootHolder) holder).tips.setVisibility(View.GONE);
//                            fadeTips = true;
//                            hasMore = true;
//                        }
//                    }, 500);
//                }
//            }
        }
    }

    @Override
    public int getItemCount() {
        return tbShopBeans.size();
    }


    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag(),mHashMap.get((Integer) v.getTag() + ""));
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position, TbMaterielBean tbDisCountBean);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_goods_icon;
        private TextView  tx_goods_title, tv_original_price, tv_coupon_price, tv_goods_type_price, tv_goods_sales_volume;
        private MyTextView tx_goods_msg;

        public ViewHolder(View itemView) {
            super(itemView);
            img_goods_icon = (ImageView) itemView.findViewById(R.id.iv_goods_pic);
            tx_goods_msg = (MyTextView) itemView.findViewById(R.id.tx_goods_msg);
            tx_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
            tv_original_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tv_coupon_price = (TextView) itemView.findViewById(R.id.tv_coupon_price);
            tv_goods_type_price = (TextView) itemView.findViewById(R.id.tv_goods_type_price);
            tv_goods_sales_volume = (TextView) itemView.findViewById(R.id.tv_goods_sales_volume);
        }
    }

    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }

    public boolean isFadeTips() {
        return fadeTips;
    }

    public void setDatas(ArrayList<TbMaterielBean> tbShopBeans) {
        this.tbShopBeans = tbShopBeans;
        notifyDataSetChanged();
    }

    public int getRealLastPosition() {
        return tbShopBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }
}
