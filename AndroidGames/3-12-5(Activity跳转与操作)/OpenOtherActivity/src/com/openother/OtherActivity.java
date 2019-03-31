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
		//得到当前Activity的意图
		Intent intent = this.getIntent();
		//获取数据
		String str = intent.getStringExtra("Main");
		//将获取到的数据设置成TextView的文本
		tv.setText(str);
	}
}
