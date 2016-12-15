package com.example.mytest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2016/12/15.
 */
public class SearchView extends View implements View.OnClickListener
{
	//int []style=new int[]{R.attr.};
	static final int OPEN_STATUS = 0;
	static final int CLOSE_STATUS = 1;
	static final long time = 500;
	int width, allWidth;
	int height;
	Bitmap bitmap1, bitmap2;
	RectF rect = new RectF();
	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	ValueAnimator closeAni;
	ValueAnimator openAni;
	int status = OPEN_STATUS;

	public SearchView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		//context.obtainStyledAttributes(attrs,R.styleable)
		paint.setColor(Color.parseColor("#55ffffff"));
		paint.setStyle(Paint.Style.FILL);
		this.setOnClickListener(this);
		bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.bus);
		bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.man);
	}

	int measureOwn(boolean isWidth, int spec)
	{
		int size = 0;
		int specMode = MeasureSpec.getMode(spec);
		int specSize = MeasureSpec.getSize(spec);
		switch (specMode)
		{
			case MeasureSpec.UNSPECIFIED:
				size = 0;
				break;
			case MeasureSpec.AT_MOST:
			case MeasureSpec.EXACTLY:
				size = specSize;
				break;
		}
		if (isWidth)
		{
			allWidth = width = size;
		} else
		{
			height = size;
		}
		return size;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//int result = size;
//		int specMode = MeasureSpec.getMode(widthMeasureSpec);
//		int specSize = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(measureOwn(true, widthMeasureSpec), measureOwn(false, heightMeasureSpec));

	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		paint.setColor(Color.parseColor("#55ffffff"));
		//super.onDraw(canvas);
		//canvas.drawColor(Color.RED);
		rect.set(0, 0, width, getHeight());
		//canvas.drawArc(rect,90,180,true,paint);
		canvas.drawRoundRect(rect, getHeight() / 2, getHeight() / 2, paint);
		paint.setColor(Color.GREEN);
		//canvas.drawCircle(width - getHeight() / 2, getHeight() / 2, getHeight() / 2, paint);
		rect.set(width-getHeight(),0,width,getHeight());
		if(width>getHeight())
		{
			canvas.drawBitmap(bitmap1,null, rect,null);
		}else
		{
			canvas.drawBitmap(bitmap2,null, rect,null);
		}

	}

	@Override
	public void onClick(View v)
	{
		if ((closeAni != null && closeAni.isRunning()) || (openAni != null && openAni.isRunning()))
		{
			return;
		}
		if (status == OPEN_STATUS)
		{
			if (closeAni == null)
			{
				closeAni = ValueAnimator.ofInt(width, getHeight());
				closeAni.setDuration(time);
				closeAni.setInterpolator(new LinearInterpolator());
				closeAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
				{
					@Override
					public void onAnimationUpdate(ValueAnimator animation)
					{
						width = (int) animation.getAnimatedValue();
						invalidate();
					}
				});
			}
			closeAni.start();
			status = CLOSE_STATUS;
		} else
		{
			if (openAni == null)
			{
				openAni = ValueAnimator.ofInt(getHeight(), allWidth);
				openAni.setDuration(time);
				openAni.setInterpolator(new LinearInterpolator());
				openAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
				{
					@Override
					public void onAnimationUpdate(ValueAnimator animation)
					{
						width = (int) animation.getAnimatedValue();
						invalidate();
					}
				});
			}
			openAni.start();
			status = OPEN_STATUS;
		}
	}
}
