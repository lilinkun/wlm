package com.wlm.wlm.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.wlm.wlm.R;

/**
 * Created by LG on 2019/9/19.
 */
public class LoaddingDialog extends Dialog {

    public LoaddingDialog(@NonNull Context context) {
        super(context,R.style.BaseDialog);
    }

    public LoaddingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loadding);
    }
}
