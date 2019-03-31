package com.imagebutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
public class MainActivity extends Activity {
	private ImageButton Ibtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Ibtn = (ImageButton)findViewById(R.id.imageBtn); 
        //为图片按钮添加触屏监听
        Ibtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//用户当前为按下
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					//设置图片按钮背景图
					Ibtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.press));
				//用户当前为抬起
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					Ibtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.nopress));
				}
				return false;
			}
		});
    }
}