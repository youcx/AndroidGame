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
        //ΪͼƬ��ť��Ӵ�������
        Ibtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//�û���ǰΪ����
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					//����ͼƬ��ť����ͼ
					Ibtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.press));
				//�û���ǰΪ̧��
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					Ibtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.nopress));
				}
				return false;
			}
		});
    }
}