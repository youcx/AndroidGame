package com.himi.edittext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
  	private EditText et;//创建一个文本编辑的对象
  	private Button btn;
  	private TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        et= (EditText)findViewById(R.id.et);//实例化文本编辑
        btn= (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
        tv = (TextView)findViewById(R.id.tv);
    }
	@Override
	public void onClick(View v) {
		if(v==btn){
			//获取EditText中的文本内容
			String str = et.getText().toString();
			//让TextView将获取到的EditText内容str显示出来
			tv.setText(str);
		} 
	}
}