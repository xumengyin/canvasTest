package com.example.mytest;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AniActivity extends AppCompatActivity implements View.OnClickListener{
    String TAG="xu";
    ImageView imageView1, imageView2, imageView3, imageView4;
    float startpoint = 0.5f;
    LinearLayout layout;
    NumberProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PropertyValuesHolder.ofFloat() Keyframe
        setContentView(R.layout.activity_ani);
        imageView4 = (ImageView) findViewById(R.id.image4);
        imageView3 = (ImageView) findViewById(R.id.image3);
        imageView2 = (ImageView) findViewById(R.id.image2);
        imageView1 = (ImageView) findViewById(R.id.image1);
        imageView4.setBackgroundColor(Color.BLUE);
        layout= (LinearLayout) findViewById(R.id.layout);
        imageView4.setOnClickListener(this);
        progressBar= (NumberProgressBar) findViewById(R.id.progress);
        View view =new View(this);
//        imageView4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                TranslateAnimation ani1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 3f, Animation.RELATIVE_TO_SELF, 0);
////                TranslateAnimation ani2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF, 0);
////                TranslateAnimation ani3 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0);
////                ani1.setDuration(1000);
////                ani1.setInterpolator(new AccelerateDecelerateInterpolator());
////                ani2.setDuration(1000);
////                ani2.setInterpolator(new AccelerateDecelerateInterpolator());
////                ani3.setDuration(1000);
////                ani3.setInterpolator(new AccelerateDecelerateInterpolator());
////                imageView3.startAnimation(ani3);
////                imageView2.startAnimation(ani2);
////                imageView1.startAnimation(ani1);
////                imageView3.setVisibility(View.VISIBLE);
////                imageView2.setVisibility(View.VISIBLE);
////                imageView1.setVisibility(View.VISIBLE);
//                Toast.makeText(getApplicationContext(), "11", 3).show();
//                imageView4.animate().scaleX(2f).scaleY(2f).setDuration(5000).setInterpolator(new AccelerateDecelerateInterpolator()).start();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "scale:" + imageView4.getScaleY(), 2000).show();
//                    }
//                }, 2000);
//            }
//        });
//        imageView4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  PropertyValuesHolder holder = PropertyValuesHolder.ofFloat("translationX", imageView4.getTranslationX(), 720);
//                PropertyValuesHolder holder2 = PropertyValuesHolder.ofInt("left", imageView4.getLeft(),imageView4.getLeft()+100);
//                PropertyValuesHolder holder3 = PropertyValuesHolder.ofInt("right", imageView4.getRight(),imageView4.getRight()+100);
//                //holder.
//                PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.1f);
//                ObjectAnimator ani = ObjectAnimator.ofPropertyValuesHolder(imageView4, holder2,holder3, holder1).setDuration(5000);
//                ani.setInterpolator(new AccelerateDecelerateInterpolator());
//                ani.start();
//            }
//        });
    }
    private void testUiThread()
    {
        Log.e(TAG, "testUiThread: start" );
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(5*1000);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Log.e(TAG, "testUiThread: end" );
                        }
                    });
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
        for (int i = 0; i <20 ; i++)
        {
            try
            {
                Thread.sleep(1000);
                Log.e(TAG, "mainThread: i--"+i );
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View v) {
        //doAni();
        testUiThread();
    }
    private void doAni()
    {
        ObjectAnimator ani =ObjectAnimator.ofObject(imageView4,"alpha",new Myevaluater(),0f,1f,0.5f);
        ani.setInterpolator(new MyInterpolator());
        ani.setDuration(5000);
        ani.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=10000;i+=100)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final float finalProgress = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(finalProgress,10000f);
                        }
                    });
                }
            }
        }).start();
    }
    class Myevaluater implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {

            Log.d(TAG,"startvalue:"+startValue+"----endvalue:"+endValue+"---fraction:"+fraction);
            return ((float)endValue-(float)startValue)*fraction+(Float) startValue;
        }
    }
    class MyInterpolator extends LinearInterpolator
    {
        @Override
        public float getInterpolation(float input) {
            Log.d(TAG,"MyInterpolator:"+input);
            return super.getInterpolation(input);
        }
    }
}
