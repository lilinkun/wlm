package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.GoodsDiscoverBean;
import com.wlm.wlm.util.DensityUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by LG on 2019/10/23.
 */
public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GoodsDiscoverBean> goodsDiscoverBeans;

    public FindAdapter(Context context,ArrayList<GoodsDiscoverBean> goodsDiscoverBeans){
        this.context = context;
        this.goodsDiscoverBeans = goodsDiscoverBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_find,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodsDiscoverBean goodsDiscoverBean = goodsDiscoverBeans.get(0);

        holder.tv_goods_time.setText(goodsDiscoverBean.getCreateDate());
        holder.tv_goods_name.setText(goodsDiscoverBean.getGoodsName());
        holder.tv_goods_detail.setText(goodsDiscoverBean.getGoodsName());

//        if (position == 0) {
//            Picasso.with(context).load("http://www.uc129.com/uploads/allimg/150606/1-150606120108.jpg").transform(transformation).placeholder(R.color.black).into(holder.iv_adapter_find);
//        }else if (position == 1){
//            Picasso.with(context).load("http://attach.bbs.miui.com/forum/201109/16/142513tzfg7zslduboisnu.jpg").transform(transformation).placeholder(R.color.black).into(holder.iv_adapter_find);
//        }else if (position == 2){
//            Picasso.with(context).load("http://pic1.win4000.com/wallpaper/2018-07-06/5b3ee1900e406.jpg").transform(transformation).placeholder(R.color.black).into(holder.iv_adapter_find);
//        }else if (position == 3){
//            Picasso.with(context).load("http://img3.duitang.com/uploads/item/201205/28/20120528234603_fnRej.jpeg").transform(transformation).placeholder(R.color.black).into(holder.iv_adapter_find);
//        }else if (position == 4){
//            Picasso.with(context).load("http://5b0988e595225.cdn.sohucs.com/q_70,c_zoom,w_640/images/20171002/31ba0fdd515f4d3dbcb5fb44e026acf7.jpeg").transform(transformation).placeholder(R.color.black).into(holder.iv_adapter_find);
//        }else {
//            Picasso.with(context).load("http://5b0988e595225.cdn.sohucs.com/q_70,c_zoom,w_640/images/20171002/31ba0fdd515f4d3dbcb5fb44e026acf7.jpeg").transform(transformation).placeholder(R.color.black).into(holder.iv_adapter_find);
//        }

        if (goodsDiscoverBean.getDiscoverType() == 2){
            if (goodsDiscoverBean.getGoodsImg().contains(",")){
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                holder.rv_adapter_find.setLayoutManager(gridLayoutManager);

                ArrayList strings = new ArrayList(Arrays.asList(goodsDiscoverBean.getGoodsImg().split(",")));
                FindPhotoAdapter findPhotoAdapter = new FindPhotoAdapter(context, strings);
                holder.rv_adapter_find.setAdapter(findPhotoAdapter);
            }else {
                Picasso.with(context).load(ProApplication.BANNERIMG + goodsDiscoverBean.getGoodsImg()).transform(transformation).placeholder(R.color.black).into(holder.iv_adapter_find);
            }
        }else if (goodsDiscoverBean.getDiscoverType() == 2) {

        }

//            holder.vv_find.

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//                    Bitmap bitmap = null;
//
//                    retriever.setDataSource("http://192.168.0.144:8080/liguo/1.mp4");
//                    //retriever.setDataSource(ProApplication.BANNERIMG+goodsDiscoverBean.getFileUrl());
//
//                    bitmap = retriever.getFrameAtTime();
//                    // 缩放
//                    int PREVIEW_VIDEO_IMAGE_HEIGHT = 300; // Pixels
//                    int videoWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
//                    int videoHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
//                    int videoViewWidth = PREVIEW_VIDEO_IMAGE_HEIGHT * videoWidth / videoHeight;
//                    int videoViewHeight = PREVIEW_VIDEO_IMAGE_HEIGHT;
//                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, videoViewWidth, videoViewHeight, true);
////                    holder.iv_adapter_find.setImageBitmap(scaledBitmap);
//                }
//            }).start();

//        }
//        if (strings.size() == 1) {

//            Picasso.with(context).load(strings.get(position)).into(holder.iv_adapter_find);

//        Glide.with(context).load(strings.get(position)).into(holder.iv_adapter_find);

//        }else if (strings.size() > 1) {
//
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
//
//
//            FindPhotoAdapter findPhotoAdapter = new FindPhotoAdapter(context, strings);
//
//            holder.rv_adapter_find.setAdapter(findPhotoAdapter);
//        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 20;
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView rv_adapter_find;
        private ImageView iv_adapter_find;
        private TextView tv_store_name;
        private TextView tv_goods_time;
        private TextView tv_goods_name;
        private TextView tv_goods_detail;
        private VideoView vv_find;


        public ViewHolder(View itemView){
            super(itemView);

            rv_adapter_find = itemView.findViewById(R.id.rv_adapter_find);
            iv_adapter_find = itemView.findViewById(R.id.iv_adapter_find);
            tv_store_name = itemView.findViewById(R.id.tv_store_name);
            tv_goods_time = itemView.findViewById(R.id.tv_goods_time);
            tv_goods_name = itemView.findViewById(R.id.tv_goods_name);
            tv_goods_detail = itemView.findViewById(R.id.tv_goods_detail);
            vv_find = itemView.findViewById(R.id.vv_find);
        }
    }

    Transformation transformation = new Transformation() {

        @Override
        public Bitmap transform(Bitmap source) {

            if(source.getWidth()==0){
                return source;
            }

            int a = DensityUtil.getScreenWidth(context)/2;
            if (source.getWidth() > source.getHeight()){
                int h = source.getHeight()*a/source.getWidth();
                Bitmap result = Bitmap.createScaledBitmap(source, a, h, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }else if (source.getWidth() < source.getHeight()){
                int w = source.getWidth()*a/source.getHeight();
                Bitmap result = Bitmap.createScaledBitmap(source, w, a, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }else if (source.getWidth() == source.getHeight()){
                Bitmap result = Bitmap.createScaledBitmap(source, a, a, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

        /*//如果图片小于设置的宽度，则返回原图
        if(source.getWidth()<targetWidth){
            return source;
        }else{
           //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
           double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
           int targetHeight = (int) (targetWidth * aspectRatio);
           if (targetHeight != 0 && targetWidth != 0) {
               Bitmap result = Bitmap.createScaledBitmap(source, width, targetHeight, false);
               if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
               }
                return result;
            } else {
               return source;
            }
        }*/
            return source;
        }

        @Override
        public String key() {
            return "transformation" + " desiredWidth";
        }
    };

}
