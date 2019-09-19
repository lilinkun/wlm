package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.OrderBean;
import com.wlm.wlm.entity.OrderChildBean;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LG on 2019/9/9.
 */
public class ShoppingCarAdapter extends RecyclerView.Adapter<ShoppingCarAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<OrderBean> orderListBeans = null;
//    private CheckInterface checkInterface;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private int sumGoodsNum = 0;
    private Map<String, OrderChildBean> map;
    private OnItemClickListener mItemClickListener;


    public ShoppingCarAdapter(Context context, ArrayList<OrderBean> orderListBeans, Map<String, OrderChildBean> map){
        this.context = context;
        this.orderListBeans = orderListBeans;
        this.map = map;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_shopcat_product,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        holder.itemView.setTag(position);

        holder.goodsName.setText(orderListBeans.get(position).getGoodsName());
        holder.goodsPrice.setText("¥" + orderListBeans.get(position).getPrice() + "");
        holder.goodsNum.setText(String.valueOf(orderListBeans.get(position).getNum()));
        holder.goods_size1.setText( orderListBeans.get(position).getSpec1());
        holder.goods_size2.setText( orderListBeans.get(position).getSpec2());

        holder.singleCheckBox.setChecked(map.get(orderListBeans.get(position).getCartId()).isChoosed());


        sumGoodsNum = sumGoodsNum+ orderListBeans.get(position).getNum();
        if (position == orderListBeans.size() - 1){
            modifyCountInterface.sumGoodsNum(sumGoodsNum);
            sumGoodsNum = 0;
        }

        Picasso.with(context).load(ProApplication.HEADIMG + orderListBeans.get(position).getGoodsImg()).error(R.mipmap.ic_adapter_error).into(holder.goodsImage);

        holder.increaseGoodsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doIncrease( position, holder.goodsNum, holder.singleCheckBox.isChecked());
            }
        });
        holder.reduceGoodsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doDecrease( position, holder.goodsNum, holder.singleCheckBox.isChecked());
            }
        });

//        holder.singleCheckBox.setChecked(child.isChoosed());
        holder.singleCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                child.setChoosed(((CheckBox) v).isChecked());
                holder.singleCheckBox.setChecked(((CheckBox) v).isChecked());
                checkInterface.checkChild(position, ((CheckBox) v).isChecked());
            }
        });
        /*
        holder.goodsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(groupPosition,childPosition,childViewHolder.goodsNum,childViewHolder.singleCheckBox.isChecked(),child);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return orderListBeans.size();
    }


    public ModifyCountInterface getModifyCountInterface() {
        return modifyCountInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    public CheckInterface getCheckInterface() {
        return checkInterface;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
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


    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param position 子元素的位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease( int position, View showCountView, boolean isChecked);

        void doDecrease(int position, View showCountView, boolean isChecked);

        void doUpdate(int position, View showCountView, boolean isChecked);

        /**
         * 删除子Item
         *
         * @param position
         */
        void childDelete( int position);

        void sumGoodsNum(int num);
    }

    /**
     * 单选框
     */
    public interface CheckInterface {

        /**
         * 子选框状态改变触发的事件
         * @param position 子元素的位置
         * @param isChecked     子元素的选中与否
         */
        void checkChild( int position, boolean isChecked);
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.single_checkBox)
        CheckBox singleCheckBox;
        @BindView(R.id.goods_image)
        ImageView goodsImage;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_spec1)
        TextView goods_size1;
        @BindView(R.id.goods_spec2)
        TextView goods_size2;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.goods_data)
        RelativeLayout goodsData;
        @BindView(R.id.reduce_goodsNum)
        ImageView reduceGoodsNum;
        @BindView(R.id.goods_Num)
        TextView goodsNum;
        @BindView(R.id.increase_goods_Num)
        ImageView increaseGoodsNum;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
