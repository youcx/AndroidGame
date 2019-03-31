package com.tab;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity implements OnTabChangeListener {
	private TabSpec ts1, ts2, ts3;//����3����ҳ
	private TabHost tableHost;//��ҳ�˵�(tab������)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tableHost = this.getTabHost();//ʵ��(��ҳ)�˵�
		//����LayoutInflater���������ҳ�˵�һ����ʾ
		LayoutInflater.from(this).inflate(R.layout.main, tableHost.getTabContentView());
		ts1 = tableHost.newTabSpec("tabOne");//ʵ����һ����ҳ
		ts1.setIndicator("Tab1");//���ô˷�ҳ��ʾ�ı���
		ts1.setContent(R.id.btn);//���ô˷�ҳ����Դid
		ts2 = tableHost.newTabSpec("tabTwo");
		//���ô˷�ҳ��ʾ�ı����ͼ��
		ts2.setIndicator("Tab2", getResources().getDrawable(R.drawable.icon));
		ts2.setContent(R.id.et);
		ts3 = tableHost.newTabSpec("tabThree"); 
		ts3.setIndicator("Tab3");
		ts3.setContent(R.id.mylayout);//���ô˷�ҳ�Ĳ���id
		tableHost.addTab(ts1);//�˵������ts1��ҳ
		tableHost.addTab(ts2);
		tableHost.addTab(ts3);
		tableHost.setOnTabChangedListener(this);
	}
	@Override
	public void onTabChanged(String tabId) {
		if (tabId.equals("tabOne")) {
			Toast.makeText(this, "��ҳ1", Toast.LENGTH_LONG).show();
		}
		if (tabId.equals("tabTwo")) {
			Toast.makeText(this, "��ҳ2", Toast.LENGTH_LONG).show();
		}
		if (tabId.equals("tabThree")) {
			Toast.makeText(this, "��ҳ3", Toast.LENGTH_LONG).show();
		}
	}
}