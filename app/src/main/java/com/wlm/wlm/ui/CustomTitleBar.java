package com.wlm.wlm.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.interf.OnTitleBarClickListener;

/**
 * Created by LG on 2018/11/13.
 */
public class CustomTitleBar extends LinearLayout implements View.OnClickListener {

    private Context context;
    private Resources res;
    private View view;
    private TextView tv_common_title,tv_head_right;
    private ImageView iv_back,iv_head_right;
    private LinearLayout ll_back;
    private RelativeLayout mRlTopLayout;
    private OnTitleBarClickListener clickListener;

    public CustomTitleBar(Context context) {
        super(context);
        init(context);
    }

    public CustomTitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.titlebar);
        tv_common_title.setText(a.getString(R.styleable.titlebar_text_title));
        tv_common_title.setTextColor(a.getColor(R.styleable.titlebar_text_color,getResources().getColor(R.color.login_title_text)));
        mRlTopLayout.setBackgroundColor(a.getColor(R.styleable.titlebar_title_color,getResources().getColor(R.color.white)));
        iv_back.setImageDrawable(a.getDrawable(R.styleable.titlebar_img_left));
        tv_head_right.setText(a.getString(R.styleable.titlebar_text_right));
        tv_head_right.setTextColor(a.getColor(R.styleable.titlebar_right_color,getResources().getColor(R.color.white)));
        tv_head_right.setTextSize(a.getDimension(R.styleable.titlebar_right_TextSize,15));
        iv_head_right.setImageDrawable(a.getDrawable(R.styleable.titlebar_img_right));
    }

    public CustomTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context){
        this.context = context;
        res=context.getResources();
        view = LayoutInflater.from(context).inflate(R.layout.custom_titlebar,null);

        tv_common_title = (TextView) view.findViewById(R.id.tv_headtop);
        iv_back = (ImageView) view.findViewById(R.id.iv_head_left);
        ll_back = (LinearLayout) view.findViewById(R.id.ll_back);
        mRlTopLayout = (RelativeLayout) view.findViewById(R.id.rl_head_title);
        tv_head_right = (TextView) view.findViewById(R.id.tv_head_right);
        iv_head_right = (ImageView) view.findViewById(R.id.iv_head_right);

        ll_back.setOnClickListener(this);

        this.addView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    public void SetOnTitleClickListener(OnTitleBarClickListener clickListener){
        this.clickListener=clickListener;
    }

    public void setTileName(String titleName){
        tv_common_title.setText(titleName);
    }

    public void setRightText(String rightText){
        tv_head_right.setText(rightText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                if (clickListener != null){
                    clickListener.onBackClick();
                }
                break;
        }
    }
}
