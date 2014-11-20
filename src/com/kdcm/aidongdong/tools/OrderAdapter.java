package com.kdcm.aidongdong.tools;

import java.util.List;
import java.util.Map;

import com.kdcm.aidongdong.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater layoutInflater;
	private List<Map<String, Object>> list;

	public OrderAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
		layoutInflater = LayoutInflater.from(context);

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
			convertView = layoutInflater.inflate(R.layout.item_order, null);
		}
		TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
		TextView tv_money = (TextView) convertView.findViewById(R.id.tv_money);
		TextView tv_num = (TextView) convertView.findViewById(R.id.tv_num);
		TextView tv_no = (TextView) convertView.findViewById(R.id.tv_no);
		tv_time.setText(list.get(position).get("create_time").toString());
		tv_money.setText(list.get(position).get("need_cash").toString());
		String shopping_carts=list.get(position).get("shopping_carts").toString();
		tv_num.setText("数量:"+shopping_carts);
		tv_no.setText(list.get(position).get("order_no").toString());
		return convertView;
	}

}
