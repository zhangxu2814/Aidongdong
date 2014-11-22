package com.kdcm.aidongdong.tools;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.ImageTool.ImageLoader;

public class OrderDetailAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater layoutInflater;
	private List<Map<String, Object>> list;
	public ImageLoader imageLoader;

	public OrderDetailAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
		layoutInflater = LayoutInflater.from(context);
		imageLoader = new ImageLoader(context);
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
			convertView = layoutInflater.inflate(R.layout.item_orderdetatil, null);
		}
		TextView tv_review=(TextView)convertView.findViewById(R.id.tv_review);
		if(!list.get(position).get("status").equals("4")){
			tv_review.setVisibility(View.GONE);	
		}
		TextView tv_name=(TextView)convertView.findViewById(R.id.tv_name);
		TextView tv_comment=(TextView)convertView.findViewById(R.id.tv_comment);
		TextView tv_money=(TextView)convertView.findViewById(R.id.tv_money);
		TextView tv_num=(TextView)convertView.findViewById(R.id.tv_num);
		ImageView iv_ad=(ImageView)convertView.findViewById(R.id.iv_ad);
		tv_name.setText(list.get(position).get("name").toString());
		tv_comment.setText(list.get(position).get("comment").toString());
		tv_money.setText(list.get(position).get("price").toString());
		tv_num.setText(list.get(position).get("number").toString());
		imageLoader.DisplayImage(list.get(position).get("url")+"", iv_ad);
		return convertView;
	}

}
