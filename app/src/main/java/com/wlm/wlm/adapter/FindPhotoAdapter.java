package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wlm.wlm.R;
import com.wlm.wlm.util.DensityUtil;

import java.util.ArrayList;

/**
 * Created by LG on 2019/10/24.
 */
public class FindPhotoAdapter extends RecyclerView.Adapter<FindPhotoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> photoList;

    public FindPhotoAdapter(Context context,ArrayList<String> photoList){
        this.context = context;
        this.photoList = photoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_find_photo,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Picasso.with(context).load(photoList.get(position)).into(holder.iv_find_adapter);


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_find_adapter;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
