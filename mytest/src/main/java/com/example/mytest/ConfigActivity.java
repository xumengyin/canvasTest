package com.example.mytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class ConfigActivity extends AppCompatActivity {

    // LinearLayout mLandlayout,mPortLayout;
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_config);
        frame = (FrameLayout) findViewById(R.id.fragment_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new TestFragent()).commitAllowingStateLoss();
        registerReceiver(receiver, new IntentFilter("haha"));
        Log.d("xu","onCreate");
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isLand = intent.getBooleanExtra("land", false);
            if (isLand) {
                  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
//                findViewById(R.id.fragment_layout).setLayoutParams(new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                getSupportActionBar().setDisplayShowTitleEnabled(true);
//                findViewById(R.id.fragment_layout).setLayoutParams(new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }
        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("xu","onConfigurationChanged");
    }
}
