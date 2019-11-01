package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.health.UidHealthStats;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wlm.wlm.R;
import com.wlm.wlm.activity.SelfGoodsDetailActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.GoodsDiscoverBean;
import com.wlm.wlm.util.DensityUtil;
import com.wlm.wlm.util.UiHelper;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;
import com.xw.banner.loader.ImageLoaderInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by LG on 2019/10/23.
 */
public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> implements OnBannerListener {

    private Context context;
    private ArrayList<GoodsDiscoverBean> goodsDiscoverBeans;
    private Bitmap scaledBitmap;
    private ArrayList<ViewHolder> viewHolders = new ArrayList<>();
    private View item ;
    private ArrayList<String> strings = new ArrayList<>();
    private PopupWindow popupWindow;
    private MediaPlayer mPlayer;
    public static final int UPDATE_TIME = 0x0003;
    public static final int HIDE_CONTROL = 0x0002;
    private boolean isShow = false;
    private int mPosition = 0;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int pos = msg.getData().getInt("pos");
            switch (msg.what) {
                case 0x001:
                    if (viewHolders != null) {
                        viewHolders.get(pos).jcVideoPlayerStandard.thumbImageView.setImageBitmap((Bitmap) (msg.getData().getParcelable("bit")));
                    }
                    break;
            }
        }
    };

    public FindAdapter(Context context,ArrayList<GoodsDiscoverBean> goodsDiscoverBeans,View view){
        this.context = context;
        this.goodsDiscoverBeans = goodsDiscoverBeans;
        this.item = view;
    }

    public void setData(ArrayList<GoodsDiscoverBean> goodsDiscoverBeans){
        this.goodsDiscoverBeans = goodsDiscoverBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_find,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GoodsDiscoverBean goodsDiscoverBean = goodsDiscoverBeans.get(position);

        viewHolders.add(holder);

        holder.tv_goods_time.setText(goodsDiscoverBean.getCreateDate());
        holder.tv_goods_name.setText(goodsDiscoverBean.getDiscoverName());
        holder.tv_goods_detail.setText(goodsDiscoverBean.getDiscoverDesc());
        holder.tv_goods_find_price.setText("￥" + goodsDiscoverBean.getPrice());
        holder.tv_goods_find_title.setText(goodsDiscoverBean.getGoodsName());

        Picasso.with(context).load(ProApplication.BANNERIMG + goodsDiscoverBean.getGoodsImg()).into(holder.iv_goods_find);


        if (goodsDiscoverBean.getDiscoverType() == 1){

            if (goodsDiscoverBean.getFileUrl().contains("，") || goodsDiscoverBean.getFileUrl().contains(",")){
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                holder.rv_adapter_find.setLayoutManager(gridLayoutManager);

                strings = new ArrayList(Arrays.asList(goodsDiscoverBean.getFileUrl().split(",")));
                FindPhotoAdapter findPhotoAdapter = new FindPhotoAdapter(context, strings);
                holder.rv_adapter_find.setAdapter(findPhotoAdapter);

                findPhotoAdapter.setItemClickListener(new FindPhotoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        ArrayList<String> list = new ArrayList(Arrays.asList(goodsDiscoverBeans.get(position).getFileUrl().split(",")));
                        initBanner(list,pos+1);
                    }
                });

            }else {
                strings.clear();
                strings.add(goodsDiscoverBean.getFileUrl());
                Picasso.with(context).load(ProApplication.BANNERIMG + goodsDiscoverBean.getFileUrl()).transform(transformation).placeholder(R.color.black).into(holder.iv_adapter_find);
            }
        }else if (goodsDiscoverBean.getDiscoverType() == 2) {
            holder.jcVideoPlayerStandard.setVisibility(View.VISIBLE);
            holder.jcVideoPlayerStandard.setUp(ProApplication.BANNERIMG + goodsDiscoverBean.getFileUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST,"");


            try {
                mPlayer = new MediaPlayer();
                mPlayer.setDataSource(ProApplication.BANNERIMG+goodsDiscoverBean.getFileUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{

                        int a = DensityUtil.getScreenWidth(context)/2;
                        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                        Bitmap bitmap = null;
                        retriever.setDataSource(ProApplication.BANNERIMG+goodsDiscoverBean.getFileUrl(),new HashMap());
                        bitmap = retriever.getFrameAtTime(0);
                        int PREVIEW_VIDEO_IMAGE_HEIGHT = a; // Pixels
                        int videoWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                        int videoHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
                        int videoViewWidth = PREVIEW_VIDEO_IMAGE_HEIGHT * videoWidth / videoHeight;
                        int videoViewHeight = PREVIEW_VIDEO_IMAGE_HEIGHT;
                        scaledBitmap = Bitmap.createScaledBitmap(bitmap, videoViewWidth, videoViewHeight, true);
                        retriever.release();

                        Message message = new Message();
                        message.what = 0x001;
                        Bundle bundle = new Bundle();
                        bundle.putInt("pos",position);
                        bundle.putParcelable("bit",scaledBitmap);
                        message.setData(bundle);
                        handler.sendMessage(message);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();


//            holder.vv_find.setMediaController(new MediaController(context));
//            holder.vv_find.setVideoURI(uri);
//            holder.vv_find.requestFocus();
//            holder.vv_find.start();
        }

        holder.iv_adapter_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goodsDiscoverBean.getDiscoverType() == 1){


                    ArrayList<String> list = new ArrayList<>();
                    list.add(goodsDiscoverBean.getFileUrl());
                    initBanner(list,1);


                }else if (goodsDiscoverBean.getDiscoverType() == 2){
                    /*holder.vv_find.setVisibility(View.VISIBLE);
//                    Uri uri = Uri.parse("http://192.168.0.144:8080/liguo/2.mp4");
                    Uri uri = Uri.parse(ProApplication.BANNERIMG+goodsDiscoverBean.getFileUrl());
                    holder.vv_find.setMediaController(new MediaController(context));
                    holder.vv_find.setVideoURI(uri);
                    holder.vv_find.requestFocus();
                    holder.vv_find.start();
                    holder.vv_find.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.start();
                        }
                    });*/
                    holder.iv_adapter_find.setVisibility(View.GONE);
                }
            }
        });

        holder.ll_goods_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("goodsid", goodsDiscoverBean.getGoodsId());
                UiHelper.launcherBundle(context, SelfGoodsDetailActivity.class, bundle);
            }
        });

    }

    private void initBanner(ArrayList<String> list,int pos){
        popupWindow = new PopupWindow(context);
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_find_show_photo, null, false);

        Banner banner = rootView.findViewById(R.id.bannerView);

        startBanner(banner,list,pos);

        popupWindow.setContentView(rootView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(item);
    }



    private void startBanner(Banner banner , final ArrayList<String> list,int pos) {
       //设置内置样式，共有六种可以点入方法内逐一体验使用。

        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);

         //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new ImageLoaderInterface() {
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                Picasso.with(context).load(ProApplication.BANNERIMG + path).error(R.mipmap.ic_adapter_error).into((ImageView)imageView);
            }

            @Override
            public View createImageView(Context context) {
                return null;
            }
        });

        //设置图片网址或地址的集合

        banner.setImages(list);

        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验

         banner.setBannerAnimation(Transformer.Default);

         //设置轮播图的标题集合

         banner.setBannerTitles(list);

        //设置轮播间隔时间

         banner.setDelayTime(3000);

        //设置是否为自动轮播，默认是“是”。

         banner.isAutoPlay(false);


         banner.onPageScrollStateChanged(2);
        //设置指示器的位置，小点点，左中右。

         banner.setIndicatorGravity(BannerConfig.CENTER)

        //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。

        .setOnBannerListener(this)

        //必须最后调用的方法，启动轮播图。

        .start(pos);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return goodsDiscoverBeans.size();
    }

    @Override
    public void OnBannerClick(int position) {
        if (popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView rv_adapter_find;
        private ImageView iv_adapter_find;
        private ImageView iv_goods_find;
        private TextView tv_store_name;
        private TextView tv_goods_time;
        private TextView tv_goods_name;
        private TextView tv_goods_detail;
        private TextView tv_goods_find_price;
        private TextView tv_goods_find_title;
        private LinearLayout ll_goods_find;
        private JCVideoPlayerStandard jcVideoPlayerStandard;

        public ViewHolder(View itemView){
            super(itemView);

            rv_adapter_find = itemView.findViewById(R.id.rv_adapter_find);
            iv_adapter_find = itemView.findViewById(R.id.iv_adapter_find);
            iv_goods_find = itemView.findViewById(R.id.iv_goods_find);
            tv_store_name = itemView.findViewById(R.id.tv_store_name);
            tv_goods_time = itemView.findViewById(R.id.tv_goods_time);
            tv_goods_name = itemView.findViewById(R.id.tv_goods_name);
            tv_goods_detail = itemView.findViewById(R.id.tv_goods_detail);
            tv_goods_find_price = itemView.findViewById(R.id.tv_goods_find_price);
            tv_goods_find_title = itemView.findViewById(R.id.tv_goods_find_title);
            ll_goods_find = itemView.findViewById(R.id.ll_goods_find);
            jcVideoPlayerStandard = itemView.findViewById(R.id.jc_player);
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
