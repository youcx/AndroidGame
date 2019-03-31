package com.himi;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Himi
 */
public class MainActivity extends Activity implements OnClickListener {
	private Button btn_ok;
	private TextView tv;
	private final String ADDRESS = "http://www.baidu.com";
	//声明http连接
	private URLConnection urlConnect;
	//声明服务器地址
	private URL url;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		btn_ok = (Button) findViewById(R.id.Btn_commit);
		tv = (TextView) findViewById(R.id.tv);
		btn_ok.setOnClickListener(this);
	}
	public void onClick(View v) {
		DataInputStream dis = null;
		if (v == btn_ok) {
			try {
				//实例地址
				url = new URL(ADDRESS);
				//实例Http连接
				urlConnect = url.openConnection();
				//获取输入流
				dis = new DataInputStream(urlConnect.getInputStream());
				//获取输出流
				//DataOutputStream dos = new DataOutputStream(urlConnect.getOutputStream());
				//获取服务器返回的数据
				int temp = 0;
				ByteArrayBuffer baff = new ByteArrayBuffer(1000);
				while ((temp = dis.read()) != -1) {
					baff.append(temp);
				}
				//将服务器返回的信息显示在文本
				tv.setText(EncodingUtils.getString(baff.toByteArray(), "UFT-8"));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (dis != null)
						dis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
