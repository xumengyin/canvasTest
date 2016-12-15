package com.example.mytest;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CviewActivity extends AppCompatActivity {

    String TAG = "xu";
    final Object obj = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cview_layout);
        Log.d(TAG, "Environment.getDataDirectory():"+ Environment.getDataDirectory().getAbsolutePath());
        Log.d(TAG, "Environment.getExternalStorageDirectory():"+ Environment.getExternalStorageDirectory().getAbsolutePath());
        Log.d(TAG, "Environment.getExternalStoragePublicDirectory(\"\"):"+ Environment.getExternalStoragePublicDirectory("").getAbsolutePath());
        Log.d(TAG, "Context.getFilesDir():"+ this.getFilesDir().getAbsolutePath());
        Log.d(TAG, "Context.getFileStreamPath(\"file1\"):"+ this.getFileStreamPath("file1").getAbsolutePath());
        Log.d(TAG, "Context.getCacheDir():"+ this.getCacheDir().getAbsolutePath());
        //lockTest();
    }

    private void lockTest() {
        synchronized (obj) {

            Log.d(TAG, "get lock1");
            //lockTest();
            synchronized (obj) {
                Log.d(TAG, "get lock2");
            }
        }
    }
}
