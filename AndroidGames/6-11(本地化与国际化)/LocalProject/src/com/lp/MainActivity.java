package com.lp;

import java.util.Locale;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ǿ��ʹ��̨���������д���Ŀ
		//		Locale newloc = Locale.getDefault();//��ȡ��ǰĬ������
		//		Configuration conf = new Configuration();//ʵ��������Ϣ
		//		conf.locale = Locale.TAIWAN;//�޸ı��ػ�����
		//		this.getResources().updateConfiguration(conf, null);//����
		//����ȫ��
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//��ʾ�Զ����SurfaceView��ͼ
		setContentView(new MySurfaceView(this));
	}
}