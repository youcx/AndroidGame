package com.openother;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	//声明按钮
	private Button btnOpen, btnHideActivity, btnExitActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//实例按钮
		btnOpen = (Button) findViewById(R.id.btnOpen);
		btnHideActivity = (Button) findViewById(R.id.btnHideActivity);
		btnExitActivity = (Button) findViewById(R.id.btnExitActivity);
		//给每个按钮添加监听
		btnOpen.setOnClickListener(this);
		btnHideActivity.setOnClickListener(this);
		btnExitActivity.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v == btnOpen) {
			//创建一个意图，并且设置需打开的Activity
			Intent intent = new Intent(MainActivity.this, OtherActivity.class);
			//发送数据 
			intent.putExtra("Main", "我是发送的数据~娃哈哈");
			//启动另外一个Activity
			this.startActivity(intent);
		} else if (v == btnHideActivity) {
			this.finish();//退出Activity
		}else if (v == btnExitActivity) {
			System.exit(0);//退出程序
		}
	}}
