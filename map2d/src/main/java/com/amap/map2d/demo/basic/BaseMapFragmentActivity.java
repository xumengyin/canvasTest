package com.amap.map2d.demo.basic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.map2d.demo.R;
import com.amap.map2d.demo.SecondActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class BaseMapFragmentActivity extends FragmentActivity {
    private AMap mMap;
    public static String CACHE_DIR = "xuCache";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basemap_fragment_activity);
        setUpMapIfNeeded();
    }

    @Override
    public void recreate() {
        super.recreate();
    }

    public void shot(View view) {
        View decorView = getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bitmap1 = decorView.getDrawingCache();
        final File file = new File(getExternalCacheDir(), CACHE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
       File bit1 = new File(file, "1.png");
        final File bit2 = new File(file, "2.png");
        mMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {
                try {
                    FileOutputStream out2 =new FileOutputStream(bit2);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,out2);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        if (bit1.exists()) {
            bit1.delete();
        }
        if (bit2.exists()) {
            bit2.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(bit1);
            bitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, SecondActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
        }
    }

}
