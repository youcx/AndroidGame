package com.himi.edittext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
  	private EditText et;//����һ���ı��༭�Ķ���
  	private Button btn;
  	private TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        et= (EditText)findViewById(R.id.et);//ʵ�����ı��༭
        btn= (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
        tv = (TextView)findViewById(R.id.tv);
    }
	@Override
	public void onClick(View v) {
		if(v==btn){
			//��ȡEditText�е��ı�����
			String str = et.getText().toString();
			//��TextView����ȡ����EditText����str��ʾ����
			tv.setText(str);
		} 
	}
}