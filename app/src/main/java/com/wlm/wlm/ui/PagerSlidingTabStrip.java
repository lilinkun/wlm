package com.wlm.wlm.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlm.wlm.R;

import org.w3c.dom.Attr;

import java.util.ArrayList;

/**
 * Created by LG on 2018/11/14.
 */
//public class PagerSlidingTabStrip extends HorizontalScrollView {
public class PagerSlidingTabStrip extends HorizontalScrollView implements ViewPager.OnPageChangeListener{

    private static final int COLOR_TEXT_NORMAL = 0xFF747474;
    private static final int COLOR_INDICATOR_COLOR = Color.BLACK;

    private Context context;
    private  int tabWidth;
    private ArrayList<String> titles;
    private int count;
    private Paint mPaint;
    private float mTranslationX;
    private ViewPager viewPager;
    private int SCREEN_WIDTH;
    private float lineheight = 4.0f;
    private int position = 0;
    private Handler handler;
    private LinearLayout linearLayout;
    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private int mUnderlineColor;
    private int mClickColor;
    private int mUnclickColor;

    private int tabCount;

    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.PagerSlidingTabStrip);
        mUnderlineColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsUnderlineColor,getResources().getColor(R.color.main_app_color));
        mClickColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsTabTextClickColor,getResources().getColor(R.color.main_app_color));
        mUnclickColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsTabTextUnclickColor,COLOR_TEXT_NORMAL);



        mPaint = new Paint();
        mPaint.setColor(mUnderlineColor);
        mPaint.setStrokeWidth(lineheight);//底部指示线的宽度
        setHorizontalScrollBarEnabled(false);
        SCREEN_WIDTH = context.getResources().getDisplayMetrics().widthPixels;
    }

    public void setLineheight(float height){
        this.lineheight = height;
        mPaint.setStrokeWidth(lineheight);//底部指示线的宽度
    }

    public void setViewPager(ViewPager viewPager){
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(position,false);
    }

    public void setTitles(ArrayList<String> titles, int position, Handler handler){
        this.position = position;
        this.handler = handler;
        this.titles = titles;
        count = titles.size();
        tabWidth = (int)(SCREEN_WIDTH/5.5);
        generateTitleView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        tabWidth = (int)(SCREEN_WIDTH/5.5);
    }

    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(mTranslationX, getHeight() - lineheight+20);
        canvas.drawLine(0, 0, tabWidth, 0, mPaint);//（startX, startY, stopX, stopY, paint）
        canvas.restore();
    }

    public void scroll(int position, float offset)
    {
        mTranslationX = tabWidth * (position + offset);
        scrollTo((int)mTranslationX-(SCREEN_WIDTH-tabWidth)/2, 0);
        invalidate();
    }

    private void generateTitleView()
    {
        if (getChildCount() > 0)
            this.removeAllViews();
        count = titles.size();
        tabCount = count;

        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        for (int i = 0; i < count; i++)
        {
            TextView tv = new TextView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            tv.setGravity(Gravity.CENTER);
            if (i == position){
                tv.setTextColor(mClickColor);
            }else {
                tv.setTextColor(mUnclickColor);
            }
            tv.setText(titles.get(i));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);//字体大小
            tv.setPadding(25,15,25,15);
            tv.setLayoutParams(lp);
            final int finalI = i;
            tv.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("position",finalI);
                    message.setData(bundle);
                    message.what = 0x110;
                    handler.sendMessage(message);
                    for (int i = 0; i < count; i++) {
                        if ( i == finalI){
                            String s = ((TextView) linearLayout.getChildAt(finalI)).getText().toString();
                            ((TextView) linearLayout.getChildAt(i)).setTextColor(mClickColor);
                        }else {
                            ((TextView) linearLayout.getChildAt(i)).setTextColor(mUnclickColor);
                        }
                    }

                    if(viewPager!=null){
                        viewPager.setCurrentItem(finalI,false);
                    }else {
                        currentPosition = finalI;
                        invalidate();
                    }
                }
            });
            linearLayout.addView(tv);
        }
        addView(linearLayout);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentPosition = position;
        currentPositionOffset = positionOffset;
        scroll(position, positionOffset);
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || tabCount == 0) {
            return;
        }

        final int height = getHeight();

        // draw indicator line

        // default: line below current tab
        View currentTab = linearLayout.getChildAt(currentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();

        // if there is an offset, start interpolating left and right coordinates between current and next tab
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

            View nextTab = linearLayout.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();

            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
            lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
        }
        canvas.drawLine(lineLeft+25, height - 25, lineRight-25, height - 25, mPaint);
    }
}
