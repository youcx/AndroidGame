package com.himi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author Himi
 *
 */
public class MainActivity extends Activity implements OnClickListener {
	private Button btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.v("MainActivity", "onCreate");
		btn = (Button) findViewById(R.id.Main_btn);
		btn.setOnClickListener(this);
		//this.finish(); //½áÊøµ±Ç°MainActivity
	}

	@Override
	public void onClick(View arg0) {
		if (arg0 == btn) {
			Intent intent = new Intent();
			intent.setClass(this, OtherActivity.class);
			this.startActivity(intent);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("MainActivity", "onDestroy");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("MainActivity", "onPause");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.v("MainActivity", "onRestart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("MainActivity", "onResume");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v("MainActivity", "onStart");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("MainActivity", "onStop");
	}

}