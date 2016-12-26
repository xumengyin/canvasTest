package com.example.mytest;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * 横竖屏切换实验的activity
 */
public class PortAndLandActivity extends AppCompatActivity
{
	private static final String TAG = "PortAndLandActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
		setContentView(R.layout.activity_port_and_land);

	}

	@Override
	public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
	{
		super.onSaveInstanceState(outState, outPersistentState);
		Log.e(TAG, "onSaveInstanceState: PersistableBundle");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		Log.e(TAG, "onSaveInstanceState: ");
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState)
	{
		super.onRestoreInstanceState(savedInstanceState, persistentState);
		Log.e(TAG, "onRestoreInstanceState:has PersistableBundle" );
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		Log.e(TAG, "onRestoreInstanceState: ");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		Log.e(TAG, "onConfigurationChanged: " );
	}
}
