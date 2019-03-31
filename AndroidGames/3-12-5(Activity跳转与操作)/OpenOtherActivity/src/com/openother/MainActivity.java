package com.openother;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	//������ť
	private Button btnOpen, btnHideActivity, btnExitActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//ʵ����ť
		btnOpen = (Button) findViewById(R.id.btnOpen);
		btnHideActivity = (Button) findViewById(R.id.btnHideActivity);
		btnExitActivity = (Button) findViewById(R.id.btnExitActivity);
		//��ÿ����ť��Ӽ���
		btnOpen.setOnClickListener(this);
		btnHideActivity.setOnClickListener(this);
		btnExitActivity.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v == btnOpen) {
			//����һ����ͼ������������򿪵�Activity
			Intent intent = new Intent(MainActivity.this, OtherActivity.class);
			//�������� 
			intent.putExtra("Main", "���Ƿ��͵�����~�޹���");
			//��������һ��Activity
			this.startActivity(intent);
		} else if (v == btnHideActivity) {
			this.finish();//�˳�Activity
		}else if (v == btnExitActivity) {
			System.exit(0);//�˳�����
		}
	}}
