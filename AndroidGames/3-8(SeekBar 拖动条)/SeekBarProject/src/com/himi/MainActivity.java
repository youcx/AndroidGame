package com.himi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	private SeekBar seekBar;
	private TextView tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		seekBar = (SeekBar) findViewById(R.id.seekbar);
		tv = (TextView) findViewById(R.id.tv);
		seekBar.setSecondaryProgress(20);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			//当用户对拖动条的拖动的动作完成时触发
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				tv.setText("<拖动条>完成拖动"); 
			}
			//当用户对拖动条进行拖动时触发
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				tv.setText("<拖动条>拖动中...");  
			}
			//当拖动条的值发生改变的时触发
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				tv.setText("当前<拖动条>的值为："+progress);
			}
		});
	}
}