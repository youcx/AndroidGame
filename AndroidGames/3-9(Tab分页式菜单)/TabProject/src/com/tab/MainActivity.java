package com.tab;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity implements OnTabChangeListener {
	private TabSpec ts1, ts2, ts3;//声明3个分页
	private TabHost tableHost;//分页菜单(tab的容器)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tableHost = this.getTabHost();//实例(分页)菜单
		//利用LayoutInflater将布局与分页菜单一起显示
		LayoutInflater.from(this).inflate(R.layout.main, tableHost.getTabContentView());
		ts1 = tableHost.newTabSpec("tabOne");//实例化一个分页
		ts1.setIndicator("Tab1");//设置此分页显示的标题
		ts1.setContent(R.id.btn);//设置此分页的资源id
		ts2 = tableHost.newTabSpec("tabTwo");
		//设置此分页显示的标题和图标
		ts2.setIndicator("Tab2", getResources().getDrawable(R.drawable.icon));
		ts2.setContent(R.id.et);
		ts3 = tableHost.newTabSpec("tabThree"); 
		ts3.setIndicator("Tab3");
		ts3.setContent(R.id.mylayout);//设置此分页的布局id
		tableHost.addTab(ts1);//菜单中添加ts1分页
		tableHost.addTab(ts2);
		tableHost.addTab(ts3);
		tableHost.setOnTabChangedListener(this);
	}
	@Override
	public void onTabChanged(String tabId) {
		if (tabId.equals("tabOne")) {
			Toast.makeText(this, "分页1", Toast.LENGTH_LONG).show();
		}
		if (tabId.equals("tabTwo")) {
			Toast.makeText(this, "分页2", Toast.LENGTH_LONG).show();
		}
		if (tabId.equals("tabThree")) {
			Toast.makeText(this, "分页3", Toast.LENGTH_LONG).show();
		}
	}
}