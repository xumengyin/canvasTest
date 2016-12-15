package com.example.mytest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/11/24.
 */
public class NumberProgressBar extends View {
    Paint mTextPaint;
    Paint mProgressPaint;
    Paint mBgPaint;
    int mTextColor= Color.BLUE;
    int mProgressColor= Color.GRAY;
    int mBgColor= Color.GREEN;
    //dp
    int mTextSize=16;
    public NumberProgressBar(Context context) {
        this(context, null);
    }

    public NumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.actionBarStyle);
    }

    public NumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
         // TODO: 2016/11/24   处理自定义xml属性
        initPaint();
    }
    private void initPaint()
    {
        mTextPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mProgressPaint.setColor(mProgressColor);
        mBgPaint.setColor(mBgColor);
        mTextPaint.setTextSize(dp2px(mTextSize));
    }

    float progress;
    String mCurText;
    float maxProgress;
    RectF progressRect1=new RectF();
    RectF progressRect2=new RectF();
    float mCurTextWidth;
    public float getProgress()
    {
        return progress;
    }
    public void setProgress(float curProgress,float max)
    {
        this.maxProgress=max;
        this.progress=curProgress;
        invalidate();

    }
    @Override
    protected void onDraw(Canvas canvas) {
        mCurText=(progress*100/maxProgress)+"%";
        mCurTextWidth= mTextPaint.measureText(mCurText);
        drawProgress1(canvas);
        drawTextProgress(canvas);
        drawProgress2(canvas);
    }
    private void drawTextProgress(Canvas canvas)
    {
            canvas.drawText(mCurText,progressRect1.right,getHeight()/2f,mTextPaint);
    }
    private void drawProgress1(Canvas canvas)
    {
        progressRect1.left=getPaddingLeft();
        progressRect1.top=getHeight()/3f;
        progressRect1.right=(getWidth()-mCurTextWidth)*progress/maxProgress;
        progressRect1.bottom=getHeight()/3f*2;
        canvas.drawRect(progressRect1,mProgressPaint);
    }
    private void drawProgress2(Canvas canvas)
    {
        progressRect2.left=progressRect1.right+mCurTextWidth;
        progressRect2.top=getHeight()/3f;
        progressRect2.right=getWidth();
        progressRect2.bottom=getHeight()/3f*2;
        canvas.drawRect(progressRect2,mBgPaint);
    }
    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=0,height=0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if(widthMode==MeasureSpec.EXACTLY)
        {
            width=widthSize;
        }else
        {
            width=Math.max(widthSize,500);
        }
        if(heightMode==MeasureSpec.EXACTLY)
        {
            height=heightSize;
        }else
        {
            height=Math.max(heightSize,getTextHeight());
        }

        setMeasuredDimension(width,height);
    }
    public int getTextHeight()
    {
        return (int)(mTextPaint.getFontMetrics().bottom-mTextPaint.getFontMetrics().top);
    }
}
