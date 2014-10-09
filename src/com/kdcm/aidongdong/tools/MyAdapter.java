package com.kdcm.aidongdong.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;

public class MyAdapter extends BaseAdapter {
	private Context context;

	private LayoutInflater layoutInflater;

	private List<Map<String, Object>> list;

	public MyAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.list = list;
	}
	public void refresh(List<Map<String, Object>> list) {
		list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list != null ? this.list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.item, null);
		}
		TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
		Button btn1 = (Button) convertView.findViewById(R.id.btn1);
		tv1.setText(list.get(position).get("nickname").toString());
		btn1.setTag( position);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "验证成功"+list.get(position).get("id").toString(),
						Toast.LENGTH_SHORT).show();
				int position = Integer.parseInt(v.getTag().toString());
				final String URLpath=Conf.APP_URL+"delFriend&friend_id="+list.get(position).get("id").toString();
				new Thread(new Runnable() {

					@Override
					public void run() {
						HttpUtil.getJsonContent(URLpath);
						
						

					}
				}).start();
				
			}
		});
		return convertView;
	}

}
