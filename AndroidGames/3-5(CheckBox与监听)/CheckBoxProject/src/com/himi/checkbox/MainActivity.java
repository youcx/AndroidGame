package com.himi.checkbox;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

//ʹ��״̬�ı��������
public class MainActivity extends Activity implements OnCheckedChangeListener {
	private CheckBox cb1, cb2, cb3;//����3��CheckBox����

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//ʵ����3��CheckBox
		cb1 = (CheckBox) findViewById(R.id.cb1);
		cb2 = (CheckBox) findViewById(R.id.cb2);
		cb3 = (CheckBox) findViewById(R.id.cb3);
		cb1.setOnCheckedChangeListener(this);
		cb2.setOnCheckedChangeListener(this);
		cb3.setOnCheckedChangeListener(this);
	}

	//��д�������ĳ�����
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//buttonView ѡ��״̬�����ı���Ǹ���ť
		//isChecked ��ʾ��ť�µ�״̬��true/false��
		if (cb1 == buttonView || cb2 == buttonView || cb3 == buttonView) {
			if (isChecked) {
				//��ʾһ����ʾ��Ϣ
				toastDisplay(buttonView.getText() + "ѡ��");

			} else {
				toastDisplay(buttonView.getText() + "ȡ��ѡ��");
			}
		}
	}

	public void toastDisplay(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}