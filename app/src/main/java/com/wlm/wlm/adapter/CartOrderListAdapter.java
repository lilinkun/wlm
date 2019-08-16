package com.wlm.wlm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.ChildListBean;
import com.wlm.wlm.util.UToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/18.
 */

public class CartOrderListAdapter extends RecyclerView.Adapter<CartOrderListAdapter.ViewHolder> {

    private Context context;
    private BuyBean buyBeans;
    private String goodsIds = "";
    private String storeIds = "";
    private String spec = "";
    private int num = 0;
    private ArrayList<TextView> textViews = new ArrayList<>();
    private int point = 0;
    private OnDataGetFare onDataGetFare;

    public CartOrderListAdapter(Context context, BuyBean buyBeans,OnDataGetFare onDataGetFare){
        this.context = context;
        this.buyBeans = buyBeans;
        this.onDataGetFare = onDataGetFare;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parent_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mShopName.setText(buyBeans.getStoremodel().get(position).getShop_name());

        textViews.add(holder.mFreight);
        if (buyBeans.getStoremodel().get(position).getMax_use_Point() > buyBeans.getUsermodel().getPoint()){
            holder.mIntegral.setText(buyBeans.getUsermodel().getPoint() + "");
        }else {
            holder.mIntegral.setText(buyBeans.getStoremodel().get(position).getMax_use_Point() + "");
        }
//        holder.mFreight.setText(buyBeans.get(position).getStoremodel().getMax_use_Point());
        point += Double.valueOf(holder.mIntegral.getText().toString());
        Picasso.with(context).load(ProApplication.HEADIMG + buyBeans.getStoremodel().get(position).getShop_logo()).error(R.mipmap.ic_shop).into(holder.mShopIcon);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        ArrayList<ChildListBean> childListBeans = buyBeans.getStoremodel().get(position).getGoodsList();

        if (storeIds.equals("")){
            storeIds = buyBeans.getStoremodel().get(position).getStore_id();
        }else {
            storeIds = storeIds + buyBeans.getStoremodel().get(position).getStore_id();
        }


        OrderShopAdapter orderShopAdapter = new OrderShopAdapter(context,childListBeans);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(orderShopAdapter);
        for (ChildListBean childListBean : childListBeans){
            if (goodsIds.equals("")){
                goodsIds =  childListBean.getGoods_id()+"";
            }else {
                goodsIds = "," + childListBean.getGoods_id();
            }
            if (spec.equals("")) {
                spec = childListBean.getAttr_id();
            }else {
                spec = spec + "," + childListBean.getAttr_id();
            }

            num += childListBean.getNum();
        }

        if (position == buyBeans.getStoremodel().size()-1){
            onDataGetFare.onPoint((int)point,-1,-1);
        }

        holder.rl_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(context).inflate(R.layout.layout_edittext,null);
                final EditText editText = (EditText) view.findViewById(R.id.edit_num);
                editText.setText(holder.mIntegral.getText().toString());
                new AlertDialog.Builder(context).setMessage("修改使用积分").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editText.getText().toString().isEmpty()) {
                            if (Integer.valueOf(editText.getText().toString()) > buyBeans.getUsermodel().getPoint()) {
                                UToast.show(context, "超出可使用积分");
                            }else {
                            if (Integer.valueOf(editText.getText().toString()) > buyBeans.getStoremodel().get(position).getMax_use_Point()) {
                                UToast.show(context, "超出此商品所用最大积分");
                                return;
                            } else {
                                point += (int) (Double.valueOf(editText.getText().toString()) - Double.valueOf(holder.mIntegral.getText().toString()));
                                holder.mIntegral.setText(editText.getText().toString() + "");
                                onDataGetFare.onPoint(point, position, Integer.valueOf(editText.getText().toString()));
                            }
                        }
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });

        if (position == buyBeans.getStoremodel().size()){
            onDataGetFare.onOrderFares();
        }

    }

    public void setFreight(String price,int position){
        if (textViews.size() > position) {
            textViews.get(position).setText(price);
        }
    }

    @Override
    public int getItemCount() {
        return buyBeans.getStoremodel().size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mShopName;
        ImageView mShopIcon;
        TextView mIntegral;
        TextView mFreight;
        RecyclerView recyclerView;
        RelativeLayout rl_point;
        public ViewHolder(View itemView) {
            super(itemView);
            mShopName = itemView.findViewById(R.id.tv_shop_name);
            mShopIcon = itemView.findViewById(R.id.iv_shop_icon);
            mIntegral = itemView.findViewById(R.id.tv_integral);
            mFreight = itemView.findViewById(R.id.tv_freight);
            recyclerView = itemView.findViewById(R.id.rv_child);
            rl_point = itemView.findViewById(R.id.rl_point);
        }
    }

    public interface OnDataGetFare{
        public void onPoint(int point, int position, int changint);
        public void onOrderFares();
    }
}

