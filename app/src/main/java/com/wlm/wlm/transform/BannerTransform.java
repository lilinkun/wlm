package com.wlm.wlm.transform;

import android.view.View;

import com.xw.banner.transformer.ABaseTransformer;


/**
 * Created by LG on 2019/8/13.
 */

public class BannerTransform extends ABaseTransformer {
    @Override
    protected void onTransform(View page, float position) {

    }
    @Override
    protected boolean isPagingEnabled() {
        return true;
    }
}
