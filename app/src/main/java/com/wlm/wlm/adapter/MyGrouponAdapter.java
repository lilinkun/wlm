package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.GrouponListBean;
import com.wlm.wlm.util.WlmUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by LG on 2019/9/16.
 */
public class MyGrouponAdapter extends RecyclerView.Adapter<MyGrouponAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<GrouponListBean> grouponListBeans;
    private OnItemClickListener mItemClickListener;
    IWXAPI iwxapi = null;

    public MyGrouponAdapter(Context context, ArrayList<GrouponListBean> grouponListBeans){
        this.context = context;
        this.grouponListBeans = grouponListBeans;

        iwxapi = WXAPIFactory.createWXAPI(context,WlmUtil.APP_ID,true);
        iwxapi.registerApp(WlmUtil.APP_ID);

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
        holder.tv_order_amount.setText("" + grouponListBeans.get(position).getPrice());

        if (grouponListBeans.get(position).getQty() == 2) {
            holder.tv_goods_spec1.setText(grouponListBeans.get(position).getGoodsSpec1() + " , ");
            holder.tv_goods_spec2.setText(grouponListBeans.get(position).getGoodsSpec2());
        }else if (grouponListBeans.get(position).getQty() == 1) {
            holder.tv_goods_spec1.setText(grouponListBeans.get(position).getGoodsSpec1());
        }

        Picasso.with(context).load(ProApplication.HEADIMG + grouponListBeans.get(position).getGoodsImg()).into(holder.iv_goods_pic);

        holder.tv_exit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
                miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
                miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST;// 正式版:0，测试版:1，体验版:2
                miniProgramObj.userName = "gh_aa9e3dbf8fd0";     // 小程序原始id
                miniProgramObj.path = "/pages/Grouping/wantGrouping/wantGrouping?TeamId=2&UserName=";
                //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
                WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
                msg.title = "小程序消息Title";                    // 小程序消息title
                msg.description = "小程序消息Desc";               // 小程序消息desc

                Bitmap thumbBmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_adapter_error);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                msg.thumbData = baos.toByteArray();

//                msg.thumbData = getThumb();                      // 小程序消息封面图片，小于128k

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = "";
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
                iwxapi.sendReq(req);
            }
        });
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
        private TextView tv_exit_order;
        private TextView tv_order_amount;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_goods_count = (TextView) itemView.findViewById(R.id.tv_goods_count);
            tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
            iv_goods_pic = (ImageView) itemView.findViewById(R.id.iv_goods_pic);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tv_goods_spec1 = (TextView) itemView.findViewById(R.id.tv_goods_spec1);
            tv_goods_spec2 = (TextView) itemView.findViewById(R.id.tv_goods_spec2);
            tv_exit_order = (TextView) itemView.findViewById(R.id.tv_exit_order);
            tv_order_amount = (TextView) itemView.findViewById(R.id.tv_order_amount);
        }
    }

}
