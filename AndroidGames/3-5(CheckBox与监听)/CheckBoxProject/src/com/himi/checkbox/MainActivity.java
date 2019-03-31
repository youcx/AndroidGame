package com.himi.checkbox;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

//使用状态改变检查监听器
public class MainActivity extends Activity implements OnCheckedChangeListener {
	private CheckBox cb1, cb2, cb3;//创建3个CheckBox对象

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//实例化3个CheckBox
		cb1 = (CheckBox) findViewById(R.id.cb1);
		cb2 = (CheckBox) findViewById(R.id.cb2);
		cb3 = (CheckBox) findViewById(R.id.cb3);
		cb1.setOnCheckedChangeListener(this);
		cb2.setOnCheckedChangeListener(this);
		cb3.setOnCheckedChangeListener(this);
	}

	//重写监听器的抽象函数
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//buttonView 选中状态发生改变的那个按钮
		//isChecked 表示按钮新的状态（true/false）
		if (cb1 == buttonView || cb2 == buttonView || cb3 == buttonView) {
			if (isChecked) {
				//显示一个提示信息
				toastDisplay(buttonView.getText() + "选中");

			} else {
				toastDisplay(buttonView.getText() + "取消选中");
			}
		}
	}

	public void toastDisplay(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}