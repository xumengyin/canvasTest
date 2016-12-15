package com.amap.map2d.demo;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.amap.map2d.demo.basic.BaseMapFragmentActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SecondActivity extends FragmentActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        imageView = (ImageView) findViewById(R.id.image);
    }

    public void show1(View view) {
        File file1 = new File(getExternalCacheDir().getAbsolutePath() + File.separator + BaseMapFragmentActivity.CACHE_DIR, "1.png");
        try {
            imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file1)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void show2(View view) {
        File file1 = new File(getExternalCacheDir().getAbsolutePath() + File.separator + BaseMapFragmentActivity.CACHE_DIR, "2.png");
        try {
            imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file1)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
