package com.himi.lv1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
   private ListView lv ;//����һ���б�
   private List<String> list ;//����һ��List����
   private ArrayAdapter<String> aa ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        lv = new ListView(this);//ʵ�����б� 
        list = new ArrayList<String>();//ʵ����List
        //���������������
        list.add("Item1");
        list.add("Item2");
        list.add("Item3");
        //ʵ��������
        //��һ��������Context
        //�ڶ���������ListView��ÿһ�в�����ʽ
        //android.R.layout.simple_list_item_1��ϵͳ��ÿ��ֻ��ʾһ�����ֲ���
        //�������������б���������
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        lv.setAdapter(aa);//������������ӳ��ListView��
        //Ϊ�б���Ӽ���
        lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Toast.makeText(MainActivity.this, "��ǰѡ���б�����±�Ϊ��"+arg2, Toast.LENGTH_SHORT).show();
			}
		});
        this.setContentView(lv);
    }
}