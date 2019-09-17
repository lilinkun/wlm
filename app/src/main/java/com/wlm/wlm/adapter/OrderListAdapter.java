package com.wlm.wlm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.activity.StoreActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.ChildListBean;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by LG on 2018/12/15.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private Context context;
    private BuyBean buyBeans;
    private String goodsIds = "";
    private String storeIds = "";
    private String spec = "";
    private OnDataGetFare onDataGetFare;
    private int num = 0;
    private ArrayList<TextView> textViews = new ArrayList<>();
    private int point = 0;

    public OrderListAdapter(Context context, BuyBean buyBeans,OnDataGetFare onDataGetFare){
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


        orderShopAdapter.setItemClickListener(new OrderShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int posId) {

//                Bundle bundle = new Bundle();
//                bundle.putString("goodsid",buyBeans.getStoremodel().get(position).getGoodsList().get(posId).getGoods_id() +"");
//                UiHelper.launcherBundle(context, SelfGoodsDetailActivity.class,bundle);
            }
        });
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
            onDataGetFare.onGetFare(goodsIds,storeIds,spec,num+"");
            onDataGetFare.onPoint(point);
        }



    }

    public void setFreight(String price){
        textViews.get(0).setText(price);
    }

    @Override
    public int getItemCount() {
        return buyBeans.getStoremodel().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rv_child);
        }
    }

    public interface OnDataGetFare{
        public void onGetFare(String goodsids, String storeIds, String spec, String num);
        public void onPoint(int point);
    }
}
