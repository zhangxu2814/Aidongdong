package com.kdcm.aidongdong.ImageTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kdcm.aidongdong.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater layoutInflater;
	private ArrayList<HashMap<String, Object>> list;
	public ImageLoader imageLoader;
	private String URL;
	private String mID;

	public LazyAdapter(Context context,
			ArrayList<HashMap<String, Object>> songsList) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.list = songsList;
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
		View vi = convertView;
		if (convertView == null) {
			vi = layoutInflater.inflate(R.layout.item_getproducts, null);
		}
		Item mItem = new Item();
		mItem.tv_title = (TextView) vi.findViewById(R.id.tv_title);
		mItem.tv_num = (TextView) vi.findViewById(R.id.tv_num);
		mItem.tv_money = (TextView) vi.findViewById(R.id.tv_money);
		mItem.iv_ad = (ImageView) vi.findViewById(R.id.iv_ad);
		mItem.tv_title.setText(list.get(position).get("name").toString());
		mItem.tv_money
				.setText("￥" + list.get(position).get("price").toString());
		mItem.tv_num.setText("已售"
				+ list.get(position).get("sold_num").toString());
		String roll_pics = list.get(position).get("roll_pics").toString();

		try {
			JSONArray pics_Array = new JSONArray(roll_pics);
			for (int i = pics_Array.length()-1; i >=0 ; i--) {
				JSONObject item = pics_Array.getJSONObject(i);
				URL = "http://www.haoapp123.com/app/localuser/aidongdong/"
						+ item.get("pic");
				
			}
			Log.i("URL", URL);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		imageLoader.DisplayImage(URL, mItem.iv_ad);

		return vi;

	}
	public String getmID(int position) {
		return list.get(position).get("id").toString();
	}

	
	class Item {
		TextView tv_title;
		TextView tv_num;
		TextView tv_money;
		ImageView iv_ad;
	}

}
