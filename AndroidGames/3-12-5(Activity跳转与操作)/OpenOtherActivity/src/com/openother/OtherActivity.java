/**
 * 
 */
package com.openother;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author Himi
 *
 */
public class OtherActivity extends Activity {
	private TextView tv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tv = new TextView(this); 
		setContentView(tv); 
		//�õ���ǰActivity����ͼ
		Intent intent = this.getIntent();
		//��ȡ����
		String str = intent.getStringExtra("Main");
		//����ȡ�����������ó�TextView���ı�
		tv.setText(str);
	}
}
