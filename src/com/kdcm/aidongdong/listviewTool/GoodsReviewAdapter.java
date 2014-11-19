package com.kdcm.aidongdong.listviewTool;

import java.util.List;
import java.util.Map;

import cn.w.song.common.T;

import com.kdcm.aidongdong.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GoodsReviewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater layoutInflater;
	private List<Map<String, Object>> list;
	public GoodsReviewAdapter(Context context, List<Map<String, Object>> list) {
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
			convertView = layoutInflater.inflate(R.layout.item_review, null);
		}
		TextView tv_name,tv_time,tv_desc;
		tv_name=(TextView)convertView.findViewById(R.id.tv_name);
		tv_time=(TextView)convertView.findViewById(R.id.tv_time);
		tv_desc=(TextView)convertView.findViewById(R.id.tv_desc);
		tv_name.setText(list.get(position).get("nickname").toString());
		tv_time.setText(list.get(position).get("create_time").toString());
		tv_desc.setText(list.get(position).get("desc").toString());
		
		return convertView;
	}

}
