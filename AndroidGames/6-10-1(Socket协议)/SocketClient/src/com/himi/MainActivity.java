package com.himi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Himi
 */
public class MainActivity extends Activity implements OnClickListener {
	private Button btn_ok;
	private EditText edit;
	private TextView tv;
	//Socket用于连接服务器获取输入输出流
	private Socket socket;
	//服务器server/IP地址
	private final String ADDRESS = "192.168.1.100";
	//服务器端口
	private final int PORT = 8888;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		btn_ok = (Button) findViewById(R.id.Btn_commit);
		tv = (TextView) findViewById(R.id.tv);
		edit = (EditText) findViewById(R.id.edit);
		btn_ok.setOnClickListener(this);
	}
	public void onClick(View v) {
		if (v == btn_ok) {
			DataInputStream dis = null;
			DataOutputStream dos = null;
			try {
				//阻塞函数，正常连接后才会向下继续执行
				socket = new Socket(ADDRESS, PORT);
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				//向服务器写数据
				dos.writeUTF(edit.getText().toString());
				String temp = "I say:";
				temp += edit.getText().toString();
				temp += "\n";
				temp += "Server say:";
				//读取服务器发来的数据
				temp += dis.readUTF();
				tv.setText(temp);
			} catch (IOException e) {
				Log.e("Himi", "Stream error!");
				e.printStackTrace();
			} finally {
				try {
					if (dis != null)
						dis.close();
					if (dos != null)
						dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
