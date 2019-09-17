package com.wlm.wlm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.ui.MyTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/15.
 */

public class MyExtendableListViewAdapter extends BaseExpandableListAdapter {
    private Context mcontext;
    private BuyBean orderGroupBeans;

    public MyExtendableListViewAdapter(Context context, BuyBean orderGroupBeans){
        this.mcontext = context;
        this.orderGroupBeans = orderGroupBeans;
    }

    @Override
    public int getGroupCount() {
        return orderGroupBeans.getStoremodel().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return orderGroupBeans.getStoremodel().get(groupPosition).getGoodsList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return orderGroupBeans.getStoremodel().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return orderGroupBeans.getStoremodel().get(groupPosition).getGoodsList().get(childPosition);
    }

    //获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupViewHolder groupViewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_item,parent,false);
            groupViewHolder = new GroupViewHolder();
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder = (GroupViewHolder)convertView.getTag();
        }
        groupViewHolder.tvShopName.setText(orderGroupBeans.getStoremodel().get(groupPosition).getShop_name());
//        groupViewHolder.tvFreight.setText(orderGroupBeans.get(groupPosition).getFreight());
        Picasso.with(mcontext).load(ProApplication.HEADIMG + orderGroupBeans.getStoremodel().get(groupPosition).getShop_logo()).into(groupViewHolder.ivShopIc);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item,parent,false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.ivGoodsImg = (ImageView) convertView.findViewById(R.id.iv_goods_pic);
            childViewHolder.tvGoodsTitle = (TextView) convertView.findViewById(R.id.tv_goods_title);
            childViewHolder.tvGoodsPrice = (MyTextView) convertView.findViewById(R.id.tv_goods_price);
            childViewHolder.tvGoodsSpec1 = (TextView) convertView.findViewById(R.id.tv_goods_spec1);
            childViewHolder.tvGoodsSpec2 = (TextView) convertView.findViewById(R.id.tv_goods_spec2);
            convertView.setTag(childViewHolder);

        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tvGoodsTitle.setText(orderGroupBeans.getStoremodel().get(groupPosition).getGoodsList().get(childPosition).getGoods_name());
        childViewHolder.tvGoodsPrice.setText(orderGroupBeans.getStoremodel().get(groupPosition).getGoodsList().get(childPosition).getShop_price() + "");
        childViewHolder.tvGoodsSpec1.setText(orderGroupBeans.getStoremodel().get(groupPosition).getGoodsList().get(childPosition).getSpec1() + "");
        childViewHolder.tvGoodsSpec2.setText(orderGroupBeans.getStoremodel().get(groupPosition).getGoodsList().get(childPosition).getSpec2() + "");
        Picasso.with(mcontext).load(ProApplication.HEADIMG + orderGroupBeans.getStoremodel().get(groupPosition).getGoodsList().get(childPosition).getGoods_img()).into(childViewHolder.ivGoodsImg);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvShopName;
        TextView tvFreight;
        ImageView ivShopIc;
    }

    static class ChildViewHolder {
        TextView tvGoodsTitle;
        ImageView ivGoodsImg;
        MyTextView tvGoodsPrice;
        TextView tvGoodsSpec1;
        TextView tvGoodsSpec2;

    }
}
