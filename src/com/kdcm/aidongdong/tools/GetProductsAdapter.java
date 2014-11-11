package com.kdcm.aidongdong.tools;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.ImageTool.ImageLoader;
import com.kdcm.aidongdong.tools.GivedCoinsAdapter.Item;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GetProductsAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater layoutInflater;
	private List<Map<String, Object>> list;
	public ImageLoader imageLoader;

	public GetProductsAdapter(Context context, List<Map<String, Object>> list) {
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
		String roll_pics=list.get(position).get("roll_pics").toString();
		JSONArray pics_Array=null;
		try {
			pics_Array = new JSONArray(roll_pics);
			JSONObject item = pics_Array.getJSONObject(1);
			Log.i("URL", item.get("pic")+"");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String URL="http://www.haoapp123.com/app/localuser/aidongdong/"+list.get(position).get("URL").toString();
	
		
		imageLoader.DisplayImage(
				"http://www.haoapp123.com/app/localuser/aidongdong/Uploads/images/2014-11-04/5458201aeda6f.jpg", mItem.iv_ad);

		return vi;

	}

	class Item {
		TextView tv_title;
		TextView tv_num;
		TextView tv_money;
		ImageView iv_ad;
	}

}