package com.screen;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); 
		Log.e("Himi", "OnCraete");
	}
	//此函数响应的前提是在AndroidManifest中注册Activity的configChanges属性; 
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Log.e("Himi", "当前屏幕切换成横屏显示模式");
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			Log.e("Himi", "当前屏幕切换成竖屏显示模式");
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) { 
		super.onSaveInstanceState(outState);
		Log.e("Himi", "ONSAVE");
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) { 
		super.onRestoreInstanceState(savedInstanceState);
		Log.e("Himi", "ONRESTORE");
	}

}