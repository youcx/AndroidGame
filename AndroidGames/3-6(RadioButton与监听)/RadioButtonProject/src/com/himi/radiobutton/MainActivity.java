package com.himi.radiobutton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
//使用状态改变监听器
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
		rg.setOnCheckedChangeListener(this);//将单选组绑定监听器
	}

	//重写监听器函数
	/** 
	 * @param group：指单选组
	 * @param group：指单选组中发生状态改变的RadioButton的内存ID！
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) { 
		if (group == rg) {//因为当前程序中只有一个RadioGroup，此步可以不进行判定
			String rbName = null; 
			if (checkedId == rb1.getId()) {
				rbName = rb1.getText().toString();
			} else if (checkedId == rb2.getId()) {
				rbName = rb2.getText().toString();
			} else if (checkedId == rb3.getId()) {
				rbName = rb3.getText().toString();
			}
			Toast.makeText(this, "选择了下标为“" + rbName + "”的单选按钮", 
					Toast.LENGTH_LONG).show();
		}
	}
}