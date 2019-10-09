package com.wlm.wlm.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.widget.TextView;

import com.wlm.wlm.R;

import cn.bingoogolapple.progressbar.BGAProgressBar;

/**
 * Created by LG on 2018/12/28.
 */

public class DownloadingDialog extends AppCompatDialog {
    private BGAProgressBar mProgressBar;
    private TextView textView;

    public DownloadingDialog(Context context) {
        super(context, R.style.AppDialogTheme);
        setContentView(R.layout.dialog_downloading);
        mProgressBar = (BGAProgressBar) findViewById(R.id.pb_downloading_content);
        setCancelable(false);
        textView = (TextView) findViewById(R.id.tv_update_message);
    }

    public void setProgress(long progress, long maxProgress) {
        mProgressBar.setMax((int) maxProgress/100);
        mProgressBar.setProgress((int) progress/100);
    }

    public void setUpdateMessage(String message){
        textView.setText(message);
    }

    @Override
    public void show() {
        super.show();
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
    }
}