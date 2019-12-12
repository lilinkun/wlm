package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.wlm.wlm.util.HomeGridRvEnum;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.RESULT;

/**
 * Created by LG on 2019/10/29.
 */
public class HomeHotAdapter extends RecyclerView.Adapter<HomeHotAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<GoodsListBean> hotHomeBeans = null;
    private OnItemClickListener onItemClickListener;
    private GoodsListBean homeBean;
    private boolean isAdd_Integral = false;
    private int isNum = 0;
    private int isShowNum = 0;

    protected boolean isScrolling = false;


    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }


    public HomeHotAdapter(Context context, ArrayList<GoodsListBean> homeHeadBean,int num) {
        this.hotHomeBeans = homeHeadBean;
        this.context = context;
        isShowNum = num;
    }

    public void setData(ArrayList<GoodsListBean> hotHomeBeans) {
        this.hotHomeBeans = hotHomeBeans;
        notifyDataSetChanged();
    }

    public void setShowNum(int showNum){
        isShowNum = showNum;
        notifyDataSetChanged();
    }

    public void setAdd_Integral() {
        isAdd_Integral = true;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_home_hot, null);

        ViewHolder myViewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        if (hotHomeBeans != null) {

            homeBean = hotHomeBeans.get(position);

            holder.goodsTitleNameTv.setText(homeBean.getGoodsName());

            holder.goodsPriceTv.setText("" + homeBean.getPrice());
            holder.goodsBuyCountTv.setText(homeBean.getUseNumber() + "");

            holder.tv_add_integral.setText(homeBean.getIntegral() + "");

            holder.tv_old_price.setText("¥" + WlmUtil.getPriceNum(homeBean.getMarketPrice()));
            holder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            if (isAdd_Integral) {
                holder.ll_add_integral.setVisibility(View.VISIBLE);
            }

            if (homeBean.getGoodsType().equals(WlmUtil.GOODSTYPE_POINT + "")) {
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText(HomeGridRvEnum.STATUS0.getStatusMsg());
            } else if (homeBean.getGoodsType().equals(WlmUtil.GOODSTYPE_SECKILL + "")) {
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText(HomeGridRvEnum.STATUS3.getStatusMsg());
            } else if (homeBean.getGoodsType().equals(WlmUtil.GOODSTYPE_WLMBUY + "")) {
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText(HomeGridRvEnum.STATUS7.getStatusMsg());
            } else if (homeBean.getGoodsType().equals(WlmUtil.GOODSTYPE_INTEGRAL + "")) {
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText(HomeGridRvEnum.STATUS6.getStatusMsg());
            } else if (homeBean.getGoodsType().equals(WlmUtil.GOODSTYPE_WLM + "")) {
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText(HomeGridRvEnum.STATUS5.getStatusMsg());
            } else if (homeBean.getGoodsType().equals(WlmUtil.GOODSTYPE_GROUPON + "")) {
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText(HomeGridRvEnum.STATUS2.getStatusMsg());
            } else if (homeBean.getGoodsType().equals(WlmUtil.GOODSTYPE_BEAUTY_HEALTH + "")) {
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText(HomeGridRvEnum.STATUS8.getStatusMsg());
            } else if (homeBean.getGoodsType().equals(WlmUtil.GOODSTYPE_VIP + "")) {
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText("VIP");
            }


            if (homeBean.getGoodsImg() != null && !homeBean.getGoodsImg().isEmpty()) {

//                Picasso.with(context).load(ProApplication.BANNERIMG + homeBean.getGoodsIndexImg()).error(R.mipmap.ic_adapter_error).into(holder.goodsPicImg);
//                if (position < 5) {
                    Glide.with(context).load(ProApplication.BANNERIMG + homeBean.getGoodsIndexImg()).error(R.mipmap.ic_adapter_error).diskCacheStrategy(RESULT).into(holder.goodsPicImg);
//                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (isShowNum == 0) {
            return hotHomeBeans != null ? hotHomeBeans.size() : 0;
        }else {
            return isShowNum;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView goodsTitleNameTv;
        private TextView goodsPriceTv;
        private TextView goodsBuyCountTv;
        private CustomRoundAngleImageView goodsPicImg;
        private LinearLayout ll_add_integral;
        private TextView tv_add_integral;
        private TextView tv_old_price;
        private LinearLayout ll_hot;
        private TextView tv_hot;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsBuyCountTv = (TextView) itemView.findViewById(R.id.tv_buy_count);
            tv_add_integral = (TextView) itemView.findViewById(R.id.tv_add_integral);
            goodsTitleNameTv = (TextView) itemView.findViewById(R.id.tv_goods_title_name);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.tv_goods_price);
            goodsPicImg = (CustomRoundAngleImageView) itemView.findViewById(R.id.iv_goods_pic);
            ll_add_integral = (LinearLayout) itemView.findViewById(R.id.ll_add_integral);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);
            tv_hot = (TextView) itemView.findViewById(R.id.tv_hot);
            ll_hot = (LinearLayout) itemView.findViewById(R.id.ll_hot);

            isNum = goodsPicImg.getWidth();
        }
    }


    //在不加载图片情况下获取图片大小
    public static int[] getImageWidthHeight(String path)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth,options.outHeight};
    }


}
