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
			//���û����϶������϶��Ķ������ʱ����
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				tv.setText("<�϶���>����϶�"); 
			}
			//���û����϶��������϶�ʱ����
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				tv.setText("<�϶���>�϶���...");  
			}
			//���϶�����ֵ�����ı��ʱ����
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				tv.setText("��ǰ<�϶���>��ֵΪ��"+progress);
			}
		});
	}
}