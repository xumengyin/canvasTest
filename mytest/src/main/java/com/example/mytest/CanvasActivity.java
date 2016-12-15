package com.example.mytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CanvasActivity extends AppCompatActivity
{
	RollTextView textView;
	String[]list={"hello1,hello2,hello3,hello4"};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canvas);
		textView= (RollTextView) findViewById(R.id.text);

	}
	public void clickBtn(View view)
	{
		textView.setTextList(Arrays.asList(list));
	}
}
