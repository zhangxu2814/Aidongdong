package com.kdcm.aidongdong.tools;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.ImageTool.ImageLoader;
import com.kdcm.aidongdong.UI.WelcomActivity;

public class SPCarAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater layoutInflater;
	private List<Map<String, Object>> list;
	public ImageLoader imageLoader;
	MyListener myListener = null;
	Intent it;
	int num = 0;
	String shopping_id=null;

	public SPCarAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.list = list;
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

	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.item_spcar, null);
		}
		ImageView img_ad = (ImageView) convertView.findViewById(R.id.img_ad);
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		TextView tv_money = (TextView) convertView.findViewById(R.id.tv_money);
		TextView tv_num = (TextView) convertView.findViewById(R.id.tv_num);
		Button btn_add = (Button) convertView.findViewById(R.id.btn_add);
		Button btn_minus = (Button) convertView.findViewById(R.id.btn_minus);
		myListener = new MyListener(position);
		btn_add.setOnClickListener(myListener);
		btn_minus.setOnClickListener(myListener);
		tv_name.setText(list.get(position).get("name").toString());
		tv_money.setText(list.get(position).get("price").toString());
		imageLoader.DisplayImage(list.get(position).get("URL_img").toString(),
				img_ad);
		tv_num.setText(list.get(position).get("number").toString());
		return convertView;
	}

	private class MyListener implements OnClickListener {
		int position;

		public MyListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_add:
				num = Integer.parseInt(list.get(position).get("number")
						.toString());
				String shopping_id = list.get(position).get("shopping_id")
						.toString();
				num += 1;
				it = new Intent(context, SPCarDialog.class);
				it.putExtra("num", num + "");
				it.putExtra("shopping_id", shopping_id);
				context.startActivity(it);

				break;
			case R.id.btn_minus:
				int num = Integer.parseInt(list.get(position).get("number")
						.toString());
				shopping_id = list.get(position).get("shopping_id").toString();
				num -= 1;
				it = new Intent(context, SPCarDialog.class);
				it.putExtra("num", num + "");
				it.putExtra("shopping_id", shopping_id);
				context.startActivity(it);
				break;
			default:
				break;
			}
		}

	}
}
