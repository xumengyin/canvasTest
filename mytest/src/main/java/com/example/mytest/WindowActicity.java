package com.example.mytest;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.ColorMatrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 可全屏滑动的popwindow
 */
public class WindowActicity extends AppCompatActivity {
String TAG="xu";
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
                Log.i(TAG, "netWork has lost");
            }else
            {
                Log.i(TAG, "netWork has connect");
            }

            NetworkInfo tmpInfo = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
            Log.i(TAG, tmpInfo.toString() + " {isConnected = " + tmpInfo.isConnected() + "}");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_acticity);
        IntentFilter mIntenFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,mIntenFilter);
        ColorMatrix mat =new ColorMatrix();
        //mat.setSaturation();
    }

    public void btnClick(View view) {
        MyPopWindow window = new MyPopWindow(this);
        window.showAtLocation(findViewById(R.id.root), Gravity.NO_GRAVITY, 0, 0);
        // window.showAsDropDown(findViewById(R.id.button));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

class MyPopWindow extends PopupWindow {
    View popView;
    Context ctx;
    int screenWidth;

    public MyPopWindow(Context context) {
        super(context);
        ctx = context;
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        //screenHeight = windowManager.getDefaultDisplay().getHeight();
        initParms();
    }

    private void initParms() {
        final int slop = ViewConfiguration.get(ctx).getScaledTouchSlop();
        this.setContentView(getPopView());
        this.setWidth(200);
        this.setHeight(200);
        this.setFocusable(false);
        //  this.setBackgroundDrawable(new BitmapDrawable());
        popView.setOnTouchListener(new View.OnTouchListener() {
            int x1;
            int y1;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = (int) event.getRawX();
                        y1 = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (animator != null && animator.isRunning()) {
                            animator.cancel();
                            animator = null;
                        }
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();
                        if (Math.abs(moveX - x1) > 10 || Math.abs(moveY - y1) > 10) {
                            MyPopWindow.this.update(moveX - popView.getWidth() / 2, moveY - popView.getHeight() / 2, -1, -1);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        int lastX = (int) event.getRawX();
                        int lastY = (int) event.getRawY();
                        int startX = lastX - popView.getWidth() / 2;
                        int endX = 0;
                        if (lastX > screenWidth / 2) {
                            endX = screenWidth - popView.getWidth() / 2;
                            //  MyPopWindow.this.update(screenWidth-popView.getWidth()/2,lastY-popView.getHeight()/2,-1,-1);
                        } else {
                            endX = 0;
                            //MyPopWindow.this.update(0,lastY-popView.getHeight()/2,-1,-1);
                        }
                        startAni(startX, endX, lastY - popView.getHeight() / 2);
                        break;
                }
                return true;
            }
        });
        //this.set
    }

    ValueAnimator animator;

    private void startAni(int from, int to, final int y) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animator = null;
        }
        animator = new ValueAnimator();
        animator.setDuration(2000);
        animator.setIntValues(from, to);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                MyPopWindow.this.update((int) animation.getAnimatedValue(), y, -1, -1);
            }
        });
        animator.start();
    }

    private View getPopView() {
        popView = View.inflate(ctx, R.layout.layout_pop, null);
        return popView;
    }
}
