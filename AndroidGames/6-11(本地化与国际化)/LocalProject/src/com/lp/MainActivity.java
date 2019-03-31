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
		//强制使用台湾语言运行此项目
		//		Locale newloc = Locale.getDefault();//获取当前默认区域
		//		Configuration conf = new Configuration();//实例配置信息
		//		conf.locale = Locale.TAIWAN;//修改本地化语言
		//		this.getResources().updateConfiguration(conf, null);//更新
		//设置全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//显示自定义的SurfaceView视图
		setContentView(new MySurfaceView(this));
	}
}