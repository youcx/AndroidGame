package com.dialog;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

public class MainActivity extends Activity {
	private Builder builder;//����Builder���� 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//ʵ����Builder����
		builder = new Builder(MainActivity.this);
		//���öԻ����ͼ��
		builder.setIcon(android.R.drawable.ic_dialog_info);
		//���öԻ���ı���
		builder.setTitle("Dialog");
		//���öԻ������ʾ�ı�
		//builder.setMessage("Dialog�Ի���");
		//������ఴť
		builder.setPositiveButton("Yes", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		//�����Ҳఴť
		builder.setNegativeButton("No", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.setView(new CheckBox(this));
		//��ӵ�ѡ��
		//		builder.setSingleChoiceItems(new String[]{"��ѡ","��ѡ"}, 1, new OnClickListener() {
		//			@Override
		//			public void onClick(DialogInterface dialog, int which) {
		//				//which :ѡ�����±� 
		//			}
		//		});
		//��Ӹ�ѡ��
		//		builder.setMultiChoiceItems(new String[] { "��ѡ", "��ѡ" }, new boolean[] { false, true }, new OnMultiChoiceClickListener() {
		//			@Override
		//			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		//				//which��ѡ�����±�
		//				//isChecked��ѡ����Ĺ�ѡ״̬
		//			}
		//		});
		//����б�
		//		builder.setItems(new String[] { "�б���1", "�б���2", "�б���3" }, new OnClickListener() {
		//			@Override
		//			public void onClick(DialogInterface dialog, int which) {
		//				//which:ѡ�����±� 
		//			}
		//		});
		//����Զ��岼��
		//ʵ��layout����
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.dialogmain, (ViewGroup) findViewById(R.id.myLayout));
		builder.setView(layout);
		//����show()������ʾ���Ի���
		builder.show();

	}
}