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
   private ListView lv ;//声明一个列表
   private List<String> list ;//声明一个List容器
   private ArrayAdapter<String> aa ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        lv = new ListView(this);//实例化列表 
        list = new ArrayList<String>();//实例化List
        //往容器中添加数据
        list.add("Item1");
        list.add("Item2");
        list.add("Item3");
        //实例适配器
        //第一个参数：Context
        //第二个参数：ListView中每一行布局样式
        //android.R.layout.simple_list_item_1：系统中每行只显示一行文字布局
        //第三个参数：列表数据容器
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        lv.setAdapter(aa);//将适配器数据映射ListView上
        //为列表添加监听
        lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Toast.makeText(MainActivity.this, "当前选中列表项的下标为："+arg2, Toast.LENGTH_SHORT).show();
			}
		});
        this.setContentView(lv);
    }
}