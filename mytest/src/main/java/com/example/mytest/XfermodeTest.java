package com.example.mytest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/11/29.
 */
public class XfermodeTest extends View
{
	protected Paint mPaint;
	protected Paint pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Bitmap mBmp;
	private Bitmap pathBitmap;
	Canvas pathCanvas;

	public XfermodeTest(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public XfermodeTest(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mPaint = new Paint();
		mBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
		pathBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		pathCanvas = new Canvas(pathBitmap);
		pathPaint.setARGB(128, 0, 0, 0);
		pathPaint.setStyle(Paint.Style.STROKE);
		pathPaint.setStrokeWidth(30);
		pathPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		pathCanvas.drawColor(Color.BLUE);
		setBitmap();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setStrokeWidth(20);
	}

	private void AvoidXfermodeTest(Canvas canvas)
	{
		//canvas.drawBitmap(mBmp,0,0,mPaint);
		mPaint.setColor(Color.RED);
		canvas.drawRect(new RectF(0, 0, getWidth(), 500f), mPaint);
		//AvoidXfermode
		//mPaint.setXfermode(new AvoidXfermode(0xffaa0000,255, AvoidXfermode.Mode.TARGET));
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		mPaint.setColor(Color.GREEN);
		canvas.save();
		canvas.drawRect(new RectF(0, 0, getWidth() / 2, 500), mPaint);
		canvas.restore();
	}

	private void drawActionPath(Canvas canvas)
	{
//		mPaint.setColor(Color.BLACK);
//		canvas.drawRect(new RectF(0,0,size,size),mPaint);
		mPaint.setColor(Color.RED);
		canvas.drawRect(new RectF(0, 0, size, size), mPaint);
		//mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		pathCanvas.drawPath(mPath, pathPaint);
		canvas.drawBitmap(pathBitmap, 0, 0, mPaint);
		mPaint.setXfermode(null);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		//drawPorterDuffXfermode(canvas);
		//drawActionPath(canvas);

	}

	int width, height;

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
	}

	Bitmap bitmap1, bitmap2;
	Canvas mCnavas1, mCanvas2;
	Paint mTextPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
	private void drawColorText(Canvas canvas)
	{
		if (bitmap1 == null)
		{
			bitmap1 = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		}
		if (bitmap2 == null)
		{
			bitmap2 = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		}
		if (mCnavas1 == null)
		{
			mCnavas1 = new Canvas(bitmap1);
		}
		if (mCanvas2 == null)
		{
			mCanvas2 = new Canvas(bitmap2);
		}
		//mCnavas1.drawText();
	}

	private void drawPorterDuffXfermode(Canvas canvas)
	{
		canvas.drawBitmap(mdstBitmap, 0, 0, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(mSrcBitmap, size / 2, size / 2, mPaint);
		mPaint.setXfermode(null);
	}

	Bitmap mSrcBitmap, mdstBitmap;
	int size = 500;

	private void setBitmap()
	{
		mSrcBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		mdstBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		Canvas canvasDst = new Canvas(mdstBitmap);
		Canvas canvasSrc = new Canvas(mSrcBitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(0x00000000);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.YELLOW);
		canvasDst.drawOval(new RectF(0, 0, size, size), paint);
		paint.setColor(Color.BLUE);
		canvasSrc.drawRect(new RectF(0, 0, size, size), paint);
		//canvasSrc.drawBitmap(mBmp);
	}

	Path mPath = new Path();
	PointF point = new PointF();

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				mPath.reset();
				point.set(event.getX(), event.getY());
				mPath.moveTo(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_MOVE:
				float x = (point.x + event.getX()) / 2;
				float y = (point.y + event.getY()) / 2;
				mPath.quadTo(point.x, point.y, x, y);
				point.set(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
				//mPath.reset();
				break;
		}
		postInvalidate();
		return true;
	}
}
