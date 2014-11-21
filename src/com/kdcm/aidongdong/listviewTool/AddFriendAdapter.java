package com.kdcm.aidongdong.listviewTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

public class AddFriendAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private LayoutInflater layoutInflater;
	private Context context;
	MyOclick mOclick;

	public AddFriendAdapter(Context context, List<Map<String, Object>> list) {
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
			convertView = layoutInflater.inflate(R.layout.item_reqfriend, null);
		}
		/**
		 * 拒绝
		 */
		Button btn_reject, btn_accept;
		mOclick = new MyOclick(position);
		btn_reject = (Button) convertView.findViewById(R.id.btn_reject);
		btn_accept = (Button) convertView.findViewById(R.id.btn_accept);
		btn_accept.setOnClickListener(mOclick);
		btn_reject.setOnClickListener(mOclick);
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		TextView tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
		tv_name.setText(list.get(position).get("nickname").toString());
		tv_phone.setText(list.get(position).get("phone").toString());
		return convertView;
	}

	class MyOclick implements OnClickListener {
		int position;
		String id = null;
		int type = 0;

		public MyOclick(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.btn_reject:
				Toast.makeText(context, "拒绝", Toast.LENGTH_SHORT).show();
				type = 0;
				id = list.get(position).get("id").toString();
				HttpUtils.acceptreject(res_apj, type, id);
				break;
			case R.id.btn_accept:
				Toast.makeText(context, "接受", Toast.LENGTH_SHORT).show();
				type = 1;
				id = list.get(position).get("id").toString();
				HttpUtils.acceptreject(res_apj, type, id);
				break;
			default:

				break;
			}
		}

	}

	JsonHttpResponseHandler res_apj = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			super.onSuccess(statusCode, headers, response);
			int result = 0;
			try {
				result = Integer.valueOf(response.getString("result"));
				Log.i("response", response + "");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (statusCode == 200 & result == 1) {
				Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
				list.clear();
				notifyDataSetChanged();
				HttpUtils.getMyAccepts(res_req);
			} else {

			}
		}
	};
	public JsonHttpResponseHandler res_req = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			super.onSuccess(statusCode, headers, response);
			int result = 0;
			try {
				result = Integer.valueOf(response.getString("result"));

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (statusCode == 200 & result == 1) {
				Toast.makeText(context, "成功2次", Toast.LENGTH_SHORT).show();
				JSONArray jsonArray;
				list.clear();
				try {
					jsonArray = response.getJSONArray("list");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jo = (JSONObject) jsonArray.opt(i);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("nickname", jo.get("nickname").toString());
						map.put("id", jo.get("id"));
						map.put("phone", jo.getString("phone"));
						list.add(map);
					}
					notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
			}
		}
	};
}
