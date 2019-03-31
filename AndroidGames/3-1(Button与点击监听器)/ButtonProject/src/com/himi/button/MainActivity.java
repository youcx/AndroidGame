package com.himi.button;//��·��
//import�������

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/* ʹ�õ���������ӿڽ��м���
public class MainActivity extends Activity implements OnClickListener {//ʹ�õ��������
	private Button btn_ok, btn_cancel;
	private TextView tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		tv = (TextView) findViewById(R.id.tv);
		btn_ok.setOnClickListener(this);//��btn_ok��ť���ڵ����������
		btn_cancel.setOnClickListener(this);//��btn_cancel��ť���ڵ����������
	}

	@Override
	public void onClick(View v) {//ʹ�ü�������Ҫ��д������� 
		if (v == btn_ok) {
			tv.setText("ȷ����ť�����¼���");
		} else if (v == btn_cancel) {
			tv.setText("ȡ����ť�����¼���");
		}
	}
}
*/
//�ڲ���ʵ�ְ�������
public class MainActivity extends Activity {//ʹ�õ��������
	private Button btn_ok, btn_cancel;
	private TextView tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		tv = (TextView) findViewById(R.id.tv);
		btn_ok.setOnClickListener(new OnClickListener() {//��btn_ok��ť���ڵ����������
					@Override
					public void onClick(View v) {
						tv.setText("ȷ����ť�����¼���");
					}
				});
		btn_cancel.setOnClickListener(new OnClickListener() {//��btn_cancel��ť���ڵ����������
					@Override
					public void onClick(View v) {
						tv.setText("ȡ����ť�����¼���");
					}
				});
	}

}