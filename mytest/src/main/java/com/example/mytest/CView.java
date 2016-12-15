package com.example.mytest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2016/10/26.
 */
public class CView extends View {
    final static String TAG = "xu";
    int fgColor;
    int bgColor;
    int cricleWidth;
    int textMargin;
    Bitmap bitmap;
    Rect rect = new Rect();
    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint4 = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arrary = context.obtainStyledAttributes(attrs, R.styleable.CView_test);
        bgColor = arrary.getColor(R.styleable.CView_test_bgColor, Color.BLACK);
        fgColor = arrary.getColor(R.styleable.CView_test_forColor, Color.GREEN);
        cricleWidth = arrary.getDimensionPixelOffset(R.styleable.CView_test_cricleWidth, 100);
        Log.d(TAG, "fgcolor: " + fgColor + "bgcolor:" + bgColor);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        init();

    }

    private void init() {
        ColorMatrix mat = new ColorMatrix(new float[]{1.5f, 0, 0, 0, 0, 0, 1.5f, 0, 0, 0, 0, 0, 1.5f, 0, 0, 0, 0, 0, 1,0});

        paint1.setColor(fgColor);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeCap(Paint.Cap.ROUND);
        // 设置路径结合处样式
        paint1.setStrokeJoin(Paint.Join.ROUND);
        paint1.setStrokeWidth(cricleWidth);
        paint2.setColor(bgColor);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        // 设置路径结合处样式
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeWidth(cricleWidth);
        paint3.setTextSize(100);
        paint3.setColor(Color.BLUE);
        paint3.setTextAlign(Paint.Align.CENTER);
        paint4.setColor(Color.RED);
        paint4.setStrokeWidth(5);
        // textMargin= (int) (paint3.getFontMetrics().top-paint3.getFontMetrics().bottom);
        paint3.getTextBounds("test", 0, "test".length(), rect);
        textMargin = rect.bottom - rect.top + 20;
        //paint4.setColorFilter(new ColorMatrixColorFilter(mat));
        paint4.setColorFilter(new LightingColorFilter(0xffffff,0x000000));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    int width;
    int height;
    int radius;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        radius = width / 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawRect(new RectF(0,0,getWidth(),getHeight()),paint1);
        canvas.translate(width / 2, height / 2);
        canvas.drawArc(new RectF(-radius, -radius, radius, radius), 120, 300, false, paint1);
        canvas.drawArc(new RectF(-radius, -radius, radius, radius), 120, 200, false, paint2);
        canvas.drawText("abcgge", 0, paint3.getFontMetrics().descent, paint3);
        canvas.drawText("abcgge", 0, paint3.getFontMetrics().descent - textMargin, paint3);
        canvas.drawText("abcgge", 0, paint3.getFontMetrics().descent + textMargin, paint3);
        canvas.drawLine(-width / 2, 0, width / 2, 0, paint4);
        canvas.drawBitmap(bitmap, null, new Rect(0, 0, 500, 500), paint4);
    }
}
