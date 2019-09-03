package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.BrowseRecordBean;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.util.WlmUtil;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2018/11/21.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> implements View.OnClickListener {

    Context context;
    List<CollectBean> collectBeans = new ArrayList<>();
    List<BrowseRecordBean> browseRecordBeans = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private OnDeleteListener onDeleteListener;
    private boolean isDelete = false;
    private boolean isDeleteSuccess = false;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private String collectId = "";
    private int type = 0;
    private String curDate = "";
    ArrayList<Long> longs = new ArrayList<>();

    public RecordAdapter(Context context, List<CollectBean> collectBeans, OnDeleteListener onDeleteListener, List<BrowseRecordBean> browseRecordBeans,int type){
        this.context = context;
        this.collectBeans = collectBeans;
        this.onDeleteListener = onDeleteListener;
        this.browseRecordBeans = browseRecordBeans;
        this.type = type;
    }

    public void setBrowseData(List<BrowseRecordBean> browseRecordBeans){
        this.browseRecordBeans = browseRecordBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_record_item,null);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.itemView.setTag(position);
        String url = "";
        double shop_price = 0;
        String Store_name = "";
        String Goods_name = "";
        if (type == 0){
            url = collectBeans.get(position).getGoods_img();
            shop_price = collectBeans.get(position).getShop_price();
            Store_name = collectBeans.get(position).getStore_name();
            Goods_name = collectBeans.get(position).getGoods_name();

//            holder.tv_collect_num.setText(collectBeans.get(position).get);
        }else if (type == 1){
            url = browseRecordBeans.get(position).getGoods_img();
            shop_price = browseRecordBeans.get(position).getShop_price();
            Store_name = browseRecordBeans.get(position).getStore_name();
            Goods_name = browseRecordBeans.get(position).getGoods_name();
            if (!curDate.equals(browseRecordBeans.get(position).getBrowse_date())) {
                holder.ll_head.setVisibility(View.VISIBLE);
                holder.tv_record_date.setText(browseRecordBeans.get(position).getBrowse_date());
                curDate = browseRecordBeans.get(position).getBrowse_date();
            }else {
                holder.ll_head.setVisibility(View.GONE);
            }

            holder.tv_shop_name.setTextColor(context.getResources().getColor(R.color.setting_title_color));
        }
        Picasso.with(this.context).load(ProApplication.HEADIMG + url).error(R.color.line_bg).config(Bitmap.Config.RGB_565).into(holder.img_goods_icon);

        BigDecimal zkFinalPrice = new BigDecimal(shop_price);
        BigDecimal couponInfo = new BigDecimal(0.0);
        double newPrice = zkFinalPrice.subtract(couponInfo).doubleValue();

        /*BigDecimal couponInfo = new BigDecimal(collectBeans.get(position).getCouponInfo());
        double newPrice = zkFinalPrice.subtract(couponInfo).doubleValue();
        String price = "";
        if(Math.round(newPrice) - newPrice == 0){
            price = String.valueOf((long) newPrice);
        }else {
            price = String.valueOf(newPrice);
        }*/
        if (isDelete){
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }else {
            holder.mCheckBox.setVisibility(View.GONE);
        }

//        if (type == 0) {
            if (isDeleteSuccess) {
                holder.mCheckBox.setChecked(false);
            }
            if (type == 0) {
                if (position == collectBeans.size() - 1) {
                    isDeleteSuccess = false;
                }
            }else {
                if (position == browseRecordBeans.size() - 1) {
                    isDeleteSuccess = false;
                }
            }

            checkBoxes.add(holder.mCheckBox);

            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (type == 0) {
                            if (collectId.isEmpty()) {
                                if (type == 0) {
                                    collectId = collectBeans.get(position).getCollect_id();
                                }
                            } else {
                                if (type == 0) {
                                    collectId = collectId + "," + collectBeans.get(position).getCollect_id();
                                }
                            }
                            onDeleteListener.delete(collectId);
                        } else {
                            longs.add(browseRecordBeans.get(position).getId());
                            onDeleteListener.deleteBrowse(longs);
                        }
                    } else {
                        if (type == 0) {
                            if (collectId != null && collectId.contains(collectBeans.get(position).getCollect_id())) {
                                if (collectId.contains("," + collectBeans.get(position).getCollect_id())) {
                                    collectId = WlmUtil.redecuStr(collectId, "," + collectBeans.get(position).getCollect_id());
                                } else if (collectId.contains(collectBeans.get(position).getCollect_id() + ",")) {
                                    collectId = WlmUtil.redecuStr(collectId, collectBeans.get(position).getCollect_id() + ",");
                                } else if (collectId.contains(collectBeans.get(position).getCollect_id())) {
                                    collectId = WlmUtil.redecuStr(collectId, collectBeans.get(position).getCollect_id());
                                }
                            }
                            onDeleteListener.delete(collectId);
                        }else {
                            if (longs.contains(browseRecordBeans.get(position).getId())) {
                                longs.remove(browseRecordBeans.get(position).getId());
                                onDeleteListener.deleteBrowse(longs);
                            }
                        }
                    }
                }
            });
//        }

        holder.tv_shop_name.setText(Store_name);

        holder.tx_goods_msg.setText("¥" + newPrice + "");
        holder.tx_goods_title.setText(Goods_name + "");

    }

    public void setDelete(boolean delete){
        this.isDelete = delete;
        if (checkBoxes.size() > 0) {
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (isDelete){
                    checkBoxes.get(i).setVisibility(View.VISIBLE);
                }else {
                    checkBoxes.get(i).setVisibility(View.GONE);
                }
            }
        }
//        notifyDataSetChanged();
    }

    public void onAllClick(){
        if (checkBoxes.size() > 0) {
            for (int i = 0; i < checkBoxes.size(); i++) {
                checkBoxes.get(i).setChecked(true);
            }

            if (type == 0) {
                for (int i = 0; i < collectBeans.size(); i++) {
                    collectId = collectBeans.get(i) + ",";
                }

                collectId = collectId.substring(0, collectId.length());
            }
        }
    }

    public void onClick(int position){

        if (type == 0) {
            if (collectId.isEmpty()) {
                collectId = collectBeans.get(position).getCollect_id();
                if (checkBoxes.size() > 0) {
                    checkBoxes.get(position).setChecked(true);
                }
            } else {
                if (!collectId.contains(collectBeans.get(position).getCollect_id())) {
                    collectId = collectId + "," + collectBeans.get(position).getCollect_id();
                    if (checkBoxes.size() > 0) {
                        checkBoxes.get(position).setChecked(true);
                    }
                } else {
                    String[] strs = collectId.split(",");
                    String str = "";
                    checkBoxes.get(position).setChecked(false);
                    for (int i = 0; i < strs.length; i++) {
                        if (!strs[i].equals(collectBeans.get(position).getCollect_id())) {
                            str += strs[i] + ",";
                        }
                    }
                    collectId = str.substring(0, str.length());
                }
            }
        }else {
            if (longs.contains(browseRecordBeans.get(position).getId())){
                checkBoxes.get(position).setChecked(false);
            }else {
                checkBoxes.get(position).setChecked(true);
            }
        }
    }

    public void onAllUnSecletClick(){
        collectId = "";
        if (checkBoxes.size() > 0) {
            for (int i = 0; i < checkBoxes.size(); i++) {
                checkBoxes.get(i).setChecked(false);
            }
        }
    }

    public void setData(List<CollectBean> collectBeans){
        this.collectBeans = collectBeans;
        checkBoxes.clear();
        notifyDataSetChanged();
    }


    public void setDeleteData(List<CollectBean> collectBeans){
        this.collectBeans = collectBeans;
        collectId = "";
        checkBoxes.clear();
        isDeleteSuccess = true;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (type == 0) {
            return collectBeans.size();
        }else {
            return browseRecordBeans.size();
        }
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


//    public void setDeleteData(){
//        collectBeans.clear();
//        notifyDataSetChanged();
//    }


    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img_goods_icon;
        public TextView tx_goods_msg;
        public TextView tx_goods_title;
        public TextView tv_shop_name;
        public CheckBox mCheckBox;
        public TextView tv_record_date;
        public TextView tv_collect_num;
        public LinearLayout ll_head;

        public ViewHolder(View itemView) {
            super(itemView);
            img_goods_icon = (ImageView) itemView.findViewById(R.id.img_goods_icon);
            tx_goods_msg = (TextView) itemView.findViewById(R.id.tx_goods_msg);
            tx_goods_title = (TextView) itemView.findViewById(R.id.tx_goods_title);
            tv_shop_name = (TextView) itemView.findViewById(R.id.tv_shop_name);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.store_checkBox);
            tv_record_date = (TextView) itemView.findViewById(R.id.tv_record_date);
            tv_collect_num = (TextView) itemView.findViewById(R.id.tv_collect_num);
            ll_head = (LinearLayout) itemView.findViewById(R.id.ll_head);
        }
    }



    public interface OnDeleteListener{
        public void delete(String collectId);
        public void deleteBrowse(ArrayList<Long> longs);
    }
}
