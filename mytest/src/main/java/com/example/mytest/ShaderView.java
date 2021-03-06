package com.example.mytest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 2016/12/16.
 */
public class ShaderView extends View
{
	Paint mShaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	Bitmap mBitmap, mBgBitmap;
	boolean isDrawBG = false;

	public ShaderView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init();
		//HttpsURLConnection
	}

	public ShaderView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	private void init()
	{
		mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg);
		//mShaderPaint.setShader(new BitmapShader(mBitmap, Shader.TileMode.REPEAT,Shader.TileMode.REPEAT));
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		super.onLayout(changed, left, top, right, bottom);
	}

	int cricleX = -1, cricley = 1;
	Path path1 = new Path();
	int tempHValue=30;
	int tempVValue=45;
	int tempVValue2=35;
	@Override
	protected void onDraw(Canvas canvas)
	{
		//super.onDraw(canvas);
		canvas.drawColor(Color.RED);
		if (mBgBitmap == null)
		{
			mBgBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
			Canvas bgCanvas = new Canvas(mBgBitmap);
			bgCanvas.drawBitmap(mBitmap, null, new RectF(0, 0, getWidth(), getHeight()), mShaderPaint);
			mShaderPaint.setShader(new BitmapShader(mBgBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
		}
		if (cricleX != -1)
		{
			path1.reset();
			//canvas.drawCircle(cricleX, cricley, 100, mShaderPaint);
			path1.moveTo(cricleX,cricley-tempVValue2);
			path1.quadTo(cricleX-tempHValue,cricley-tempVValue-tempVValue2,cricleX-2*tempHValue,cricley-tempVValue2);
			//path1.quadTo();
			//path1.quadTo(cricleX-tempHValue,);
			path1.lineTo(cricleX,cricley+tempVValue);
			path1.lineTo(cricleX+2*tempHValue,cricley-tempVValue2);
			path1.quadTo(cricleX+tempHValue,cricley-tempVValue-tempVValue2,cricleX,cricley-tempVValue2);
			path1.close();
			canvas.drawPath(path1,mShaderPaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
		{
			cricleX = (int) event.getX();
			cricley = (int) event.getY();
			invalidate();
		}
		return true;
	}
}
