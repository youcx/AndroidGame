package com.himi.button;//包路径
//import导入类库

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/* 使用点击监听器接口进行监听
public class MainActivity extends Activity implements OnClickListener {//使用点击监听器
	private Button btn_ok, btn_cancel;
	private TextView tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		tv = (TextView) findViewById(R.id.tv);
		btn_ok.setOnClickListener(this);//将btn_ok按钮绑定在点击监听器上
		btn_cancel.setOnClickListener(this);//将btn_cancel按钮绑定在点击监听器上
	}

	@Override
	public void onClick(View v) {//使用监听器就要重写其抽象函数 
		if (v == btn_ok) {
			tv.setText("确定按钮触发事件！");
		} else if (v == btn_cancel) {
			tv.setText("取消按钮触发事件！");
		}
	}
}
*/
//内部类实现按键监听
public class MainActivity extends Activity {//使用点击监听器
	private Button btn_ok, btn_cancel;
	private TextView tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		tv = (TextView) findViewById(R.id.tv);
		btn_ok.setOnClickListener(new OnClickListener() {//将btn_ok按钮绑定在点击监听器上
					@Override
					public void onClick(View v) {
						tv.setText("确定按钮触发事件！");
					}
				});
		btn_cancel.setOnClickListener(new OnClickListener() {//将btn_cancel按钮绑定在点击监听器上
					@Override
					public void onClick(View v) {
						tv.setText("取消按钮触发事件！");
					}
				});
	}

}