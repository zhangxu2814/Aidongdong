package com.kdcm.aidongdong.tools;

import java.util.List;
import java.util.Map;

import com.kdcm.aidongdong.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GivedCoinsAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater layoutInflater;
	private List<Map<String, Object>> list;

	public GivedCoinsAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.list = list;

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
			convertView = layoutInflater
					.inflate(R.layout.item_givedcoins, null);
		}
		Item mItem = new Item();
		mItem.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		mItem.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
		mItem.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
		mItem.tv_name.setText(list.get(position).get("nickname").toString());
		mItem.tv_time.setText(list.get(position).get("paid_time").toString());
		mItem.tv_num.setText(list.get(position).get("coins_paid").toString());
		return convertView;
	}

	class Item {
		TextView tv_time;
		TextView tv_name;
		TextView tv_num;
	}
}
