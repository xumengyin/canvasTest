package com.example.mytest;

/**
 * Created by Administrator on 2016/11/10.
 */
public class JniTest
{
	static
	{
		System.loadLibrary("jintestapp");
	}

	public native String testxu();


}
