package com.dialog;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

public class MainActivity extends Activity {
	private Builder builder;//声明Builder对象 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//实例化Builder对象
		builder = new Builder(MainActivity.this);
		//设置对话框的图标
		builder.setIcon(android.R.drawable.ic_dialog_info);
		//设置对话框的标题
		builder.setTitle("Dialog");
		//设置对话框的提示文本
		//builder.setMessage("Dialog对话框");
		//监听左侧按钮
		builder.setPositiveButton("Yes", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		//监听右侧按钮
		builder.setNegativeButton("No", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.setView(new CheckBox(this));
		//添加单选框
		//		builder.setSingleChoiceItems(new String[]{"单选","单选"}, 1, new OnClickListener() {
		//			@Override
		//			public void onClick(DialogInterface dialog, int which) {
		//				//which :选中项下标 
		//			}
		//		});
		//添加复选框
		//		builder.setMultiChoiceItems(new String[] { "多选", "多选" }, new boolean[] { false, true }, new OnMultiChoiceClickListener() {
		//			@Override
		//			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		//				//which：选中项下标
		//				//isChecked：选中项的勾选状态
		//			}
		//		});
		//添加列表
		//		builder.setItems(new String[] { "列表项1", "列表项2", "列表项3" }, new OnClickListener() {
		//			@Override
		//			public void onClick(DialogInterface dialog, int which) {
		//				//which:选中项下标 
		//			}
		//		});
		//添加自定义布局
		//实例layout布局
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.dialogmain, (ViewGroup) findViewById(R.id.myLayout));
		builder.setView(layout);
		//调用show()方法显示出对话框
		builder.show();

	}
}