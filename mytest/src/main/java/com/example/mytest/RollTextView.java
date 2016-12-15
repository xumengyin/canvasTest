package com.example.mytest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */
public class RollTextView extends TextView
{
	Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	int fontSize;
	int fontColor;
	List<String> textList=new ArrayList<>();
	String testText = "hello xu";
	Paint.FontMetricsInt fontInt;

	public RollTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		float density = getResources().getDisplayMetrics().density;
		TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.RollTextView);
		fontSize = ta.getDimensionPixelSize(R.styleable.RollTextView_textSize, (int) density * 13);
		fontColor = ta.getColor(R.styleable.RollTextView_textColor, Color.BLACK);
		textPaint.setTextSize(fontSize);
		textPaint.setColor(fontColor);
		fontInt = textPaint.getFontMetricsInt();
		setWillNotDraw(false);
		initAni();
		ta.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getSize(MeasureSpec.getMode(widthMeasureSpec), MeasureSpec.getSize(widthMeasureSpec), true);
		int height = getSize(MeasureSpec.getMode(heightMeasureSpec), MeasureSpec.getSize(heightMeasureSpec), false);
		setMeasuredDimension(width, height);
	}

	private int getSize(int mode, int measureSize, boolean isWidth)
	{
		int size = 0;
		if (mode == MeasureSpec.EXACTLY)
		{
			size = measureSize;
		} else if (mode == MeasureSpec.AT_MOST)
		{
			if (isWidth)
			{
				size = 200;

			} else
			{
				size = fontInt.bottom - fontInt.top;
			}
		}
		return size;
	}

	public void setTextList(List<String> list)
	{
		indexTextList=0;
		curBaseLine=-fontInt.top;
		this.textList = list;
		//invalidate();
		requestLayout();
		startScroll();
	}

	public void setTestText(String text)
	{
		this.testText = text;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.BLUE);
		drawText(canvas);
	}

	private void drawText(Canvas canvas)
	{
		//canvas.drawText(testText, getPaddingLeft(), Math.abs(fontInt.top), textPaint);
		if(!textList.isEmpty())
		{
			canvas.drawText(textList.get(0), getPaddingLeft(), curBaseLine, textPaint);

			canvas.drawText(textList.get(0), getPaddingLeft(), curBaseLine + fontInt.top, textPaint);
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		super.dispatchDraw(canvas);
	}

	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if(mAin.isRunning())
			{
				mAin.cancel();
			}
			mAin.start();
			handler.sendEmptyMessageDelayed(0,(time+2)*1000);

		}
	};

	private void startScroll()
	{
		handler.sendEmptyMessageDelayed(0,time*1000);
	}
	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();

	}
	int indexTextList;
	ValueAnimator mAin;
	int time=3;
	float curBaseLine;
	private void initAni()
	{
		int baseLine=Math.abs(fontInt.top);
		mAin=ValueAnimator.ofFloat(baseLine,2*baseLine);
		mAin.setDuration(time*1000);
		mAin.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		{
			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				curBaseLine= (float) animation.getAnimatedValue();
				Log.e("xu", "onAnimationUpdate: "+curBaseLine);
				invalidate();
			}
		});
		mAin.addListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				super.onAnimationEnd(animation);
				indexTextList++;
			}
		});
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		handler.removeMessages(0);
	}
}
