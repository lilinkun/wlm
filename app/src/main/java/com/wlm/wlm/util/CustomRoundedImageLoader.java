package com.wlm.wlm.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.wlm.wlm.R;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.xw.banner.loader.ImageLoaderInterface;

/**
 * Created by LG on 2019/8/20.
 */
public class CustomRoundedImageLoader implements ImageLoaderInterface {

    @Override
    public void displayImage(Context context, Object path, View imageView) {
//            Picasso.with(context).load(ProApplication.BANNERIMG + ((FlashBean) path).getFlashpic()).error(R.mipmap.ic_family_girl).into((ImageView)imageView);

        try {
            if (((String) path).contains("asdads")) {
                ((ImageView) imageView).setImageResource(R.mipmap.ic_groupon_banner);
            }
        }catch (Exception e){
            ((ImageView) imageView).setImageResource(R.mipmap.ic_banner_test);
        }

    }

    @Override
    public ImageView createImageView(Context context) {
        CustomRoundAngleImageView roundedImg = new CustomRoundAngleImageView(context);

        return roundedImg;
    }
}
