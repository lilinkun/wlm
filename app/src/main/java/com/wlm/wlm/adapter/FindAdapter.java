package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wlm.wlm.R;
import com.wlm.wlm.util.DensityUtil;

import java.util.ArrayList;

/**
 * Created by LG on 2019/10/23.
 */
public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> strings;

    public FindAdapter(Context context,ArrayList<String> list){
        this.context = context;
        this.strings = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_find,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


//        if (strings.size() == 1) {

//            Picasso.with(context).load(strings.get(position)).into(holder.iv_adapter_find);
            Picasso.with(context).load(strings.get(position)).transform(transformation).into(holder.iv_adapter_find);

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
        return strings.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView rv_adapter_find;
        private ImageView iv_adapter_find;
        private TextView tv_store_name;
        private TextView tv_goods_time;
        private TextView tv_goods_name;
        private TextView tv_goods_detail;


        public ViewHolder(View itemView){
            super(itemView);

            rv_adapter_find = itemView.findViewById(R.id.rv_adapter_find);
            iv_adapter_find = itemView.findViewById(R.id.iv_adapter_find);
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
