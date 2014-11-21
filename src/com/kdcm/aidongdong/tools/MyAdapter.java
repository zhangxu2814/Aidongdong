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
import com.kdcm.aidongdong.UI.DialogGiveMoney;
import com.kdcm.aidongdong.UI.MyDialogActivity;

public class MyAdapter extends BaseAdapter {
	private Context context;
	private String btn_name;
	private LayoutInflater layoutInflater;
	private List<Map<String, Object>> list;
	private Intent it;
	private String URLpath;
	private String msg = "";
	private Button btn_give;
	/**
	 * 赠送按钮，只有在好友界面能用到，默认隐藏。
	 */
	private int isGone = View.GONE;

	public MyAdapter(Context context, List<Map<String, Object>> list, String msg) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.list = list;
		this.msg = msg;
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
		TextView tv_nickname = (TextView) convertView
				.findViewById(R.id.tv_nickname);
		TextView tv_duration = (TextView) convertView
				.findViewById(R.id.tv_duration);
		TextView tv_coins = (TextView) convertView.findViewById(R.id.tv_coins);
		TextView tv_day=(TextView)convertView.findViewById(R.id.tv_day);
		Button btn_del = (Button) convertView.findViewById(R.id.btn_del);
		btn_give = (Button) convertView.findViewById(R.id.btn_give);
		btn_give.setVisibility(isGone);
		TextView tv_num=(TextView)convertView.findViewById(R.id.tv_num);
		tv_num.setText(position+1+"");
		
		tv_nickname.setText(list.get(position).get("nickname").toString());
		String duration = list.get(position).get("duration").toString();
		if (!duration.equals("null")) {
			tv_day.setText(list.get(position).get("month_move_days").toString());
			tv_coins.setText(list.get(position).get("coins").toString());
			int time=Integer.valueOf(duration);
			tv_duration.setText("运动时间："+time/60+"h"+time%60+"s");
		} else {
			tv_duration.setVisibility(View.INVISIBLE);
		}
		btn_del.setText(btn_name);
		btn_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				it = new Intent(context, MyDialogActivity.class);
				Toast.makeText(context,
						"验证成功" + list.get(position).get("id").toString(),
						Toast.LENGTH_SHORT).show();

				URLpath = URLpath + list.get(position).get("id").toString();
				it.putExtra("URL", URLpath);
				it.putExtra("msg", msg);
				context.startActivity(it);

			}
		});
		btn_give.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				it = new Intent(context, DialogGiveMoney.class);
				it.putExtra("mID", list.get(position).get("id").toString());
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

	public void setURL(String URLpath) {
		this.URLpath = URLpath;
	}

	public int getIsGone() {
		return isGone;
	}

	public void setIsGone(int isGone) {
		this.isGone = isGone;
	}
}
