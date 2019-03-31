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
	//�˺�����Ӧ��ǰ������AndroidManifest��ע��Activity��configChanges����; 
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Log.e("Himi", "��ǰ��Ļ�л��ɺ�����ʾģʽ");
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			Log.e("Himi", "��ǰ��Ļ�л���������ʾģʽ");
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