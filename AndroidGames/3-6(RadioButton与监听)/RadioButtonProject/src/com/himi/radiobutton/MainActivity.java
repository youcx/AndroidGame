package com.himi.radiobutton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
//ʹ��״̬�ı������
public class MainActivity extends Activity implements OnCheckedChangeListener {
	private RadioButton rb1, rb2, rb3;
	private RadioGroup rg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		rb1 = (RadioButton) findViewById(R.id.rb1);
		rb2 = (RadioButton) findViewById(R.id.rb2);
		rb3 = (RadioButton) findViewById(R.id.rb3);
		rg = (RadioGroup) findViewById(R.id.radGrp);
		rg.setOnCheckedChangeListener(this);//����ѡ��󶨼�����
	}

	//��д����������
	/** 
	 * @param group��ָ��ѡ��
	 * @param group��ָ��ѡ���з���״̬�ı��RadioButton���ڴ�ID��
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) { 
		if (group == rg) {//��Ϊ��ǰ������ֻ��һ��RadioGroup���˲����Բ������ж�
			String rbName = null; 
			if (checkedId == rb1.getId()) {
				rbName = rb1.getText().toString();
			} else if (checkedId == rb2.getId()) {
				rbName = rb2.getText().toString();
			} else if (checkedId == rb3.getId()) {
				rbName = rb3.getText().toString();
			}
			Toast.makeText(this, "ѡ�����±�Ϊ��" + rbName + "���ĵ�ѡ��ť", 
					Toast.LENGTH_LONG).show();
		}
	}
}