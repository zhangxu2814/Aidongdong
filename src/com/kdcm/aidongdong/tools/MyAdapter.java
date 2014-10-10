package com.kdcm.aidongdong.tools;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
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
import com.kdcm.aidongdong.UI.MyDialogActivity;

public class MyAdapter extends BaseAdapter {
	private Context context;
	private String btn_name;

	private LayoutInflater layoutInflater;

	private List<Map<String, Object>> list;
	private Intent it;

	public MyAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.list = list;
	}

	public void refresh(List<Map<String, Object>> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return this.list != null ? this.list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.item, null);
		}
		TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
		Button btn1 = (Button) convertView.findViewById(R.id.btn1);
		tv1.setText(list.get(position).get("nickname").toString());
		btn1.setText(btn_name);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				it = new Intent(context, MyDialogActivity.class);
				Toast.makeText(context,
						"验证成功" + list.get(position).get("id").toString(),
						Toast.LENGTH_SHORT).show();

				final String URLpath = Conf.APP_URL + "delFriend&friend_id="
						+ list.get(position).get("id").toString();
				it.putExtra("URL", URLpath);
				context.startActivity(it);

			}
		});
		return convertView;
	}

	public String getBtn_name() {
		return btn_name;
	}

	public void setBtn_name(String btn_name) {
		this.btn_name = btn_name;
	}

}
