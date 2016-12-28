package com.example.mytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class JniTestActivity extends AppCompatActivity
{
	JniTest jni = new JniTest();
	TextView mTextvView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jni_test);
		mTextvView= (TextView) findViewById(R.id.text);
		mTextvView.setText(jni.testxu());
	}
}
