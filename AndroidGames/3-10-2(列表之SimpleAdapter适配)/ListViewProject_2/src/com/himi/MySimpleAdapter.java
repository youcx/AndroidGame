/**
 * 
 */
package com.himi;
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author Himi
 * 
 */
public class MySimpleAdapter extends BaseAdapter {
	//����һ��LayoutInflater����������������ʵ�������֣�
	private LayoutInflater mInflater;
	private List<Map<String, Object>> list;//����List��������
	private int layoutID; //��������ID
	private String flag[];//����ListView�����������ӳ������
	private int ItemIDs[];//����ListView�����������ID����
	public MySimpleAdapter(Context context, List<Map<String, Object>> list,
			int layoutID, String flag[], int ItemIDs[]) {
		//���ù�����ʵ������Ա��������
		this.mInflater = LayoutInflater.from(context);
		this.list = list;
		this.layoutID = layoutID;
		this.flag = flag;
		this.ItemIDs = ItemIDs;
	}
	@Override
	public int getCount() {
		return list.size();//����ListView��ĳ���
	}

	@Override
	public Object getItem(int arg0) {
		return 0;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	//ʵ��������������Լ������������
	//getView(int position, View convertView, ViewGroup parent)
	//��һ�����������Ƶ�����
	//�ڶ������������Ƶ���ͼ����ָ����ListView��ÿһ��Ĳ���
	//������������view�ĺϼ������ﲻ��Ҫ
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//������ͨ��mInflater����ʵ����Ϊһ��view
		convertView = mInflater.inflate(layoutID, null);
		for (int i = 0; i < flag.length; i++) {//����ÿһ����������
			//ÿ���������ƥ���жϣ��õ��������ȷ����
			if (convertView.findViewById(ItemIDs[i]) instanceof ImageView) {
				//findViewById()����������ʵ���������е����
				//�����ΪImageView���ͣ���Ϊ��ʵ����һ��ImageView����
				ImageView iv = (ImageView) convertView.findViewById(ItemIDs[i]);
				//Ϊ�������������
				iv.setBackgroundResource((Integer) list.get(position).get(
						flag[i]));
			} else if (convertView.findViewById(ItemIDs[i]) instanceof TextView) {
				//�����ΪTextView���ͣ���Ϊ��ʵ����һ��TextView����
				TextView tv = (TextView) convertView.findViewById(ItemIDs[i]);
				//Ϊ�������������
				tv.setText((String) list.get(position).get(flag[i]));
			} 
		} 
		//Ϊ��ť���ü���
		((Button)convertView.findViewById(R.id.btn)).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						//���ﵯ��һ���Ի��򣬺�������ϸ����
						new AlertDialog.Builder(MainActivity.ma)
						.setTitle("�Զ���SimpleAdapter")
						.setMessage("��ť�ɹ����������¼���")
						.show();
					} 
				});
		//Ϊ��ѡ�����ü���
		((CheckBox)convertView.findViewById(R.id.cb)).
		setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//���ﵯ��һ���Ի��򣬺�������ϸ����
				new AlertDialog.Builder(MainActivity.ma)
				.setTitle("�Զ���SimpleAdapter")
				.setMessage("CheckBox�ɹ�����״̬�ı�����¼���")
				.show();
			}
		});
		return convertView;
	} 
}