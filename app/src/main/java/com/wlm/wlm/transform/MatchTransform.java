package com.wlm.wlm.transform;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

/**
 * Created by LG on 2019/11/5.
 */
public class MatchTransform implements Transformation {
    private ImageView big_1;

    public void setView(ImageView big_1){
        this.big_1 = big_1;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        if(source.getWidth()==0){
            return source;
        }

        int a = big_1.getWidth();
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

        return source;
    }

    @Override
    public String key() {
        return null;
    }
}
