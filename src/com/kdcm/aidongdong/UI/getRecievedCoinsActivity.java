package com.kdcm.aidongdong.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class getRecievedCoinsActivity extends ListActivity {
	 SimpleAdapter simpleAdapter;
	 List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
	 Handler handler = new Handler() {

			public void handleMessage(Message msg) {
				if (msg.what == 0x123) {

					// 使用adapter显示服务器响应
					simpleAdapter.notifyDataSetChanged();

				}
			}
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}
	private void init() {
		 simpleAdapter = new SimpleAdapter(this, listItems,
					R.layout.item_givedcoins, new String[] { "nickname","paid_time","coins_paid"},
					new int[] { R.id.tv_name,R.id.tv_time,R.id.tv_num });
		 setListAdapter(simpleAdapter);
		 HttpUtils.getReceivedCoins(res_rec);
	}
	JsonHttpResponseHandler res_rec = new JsonHttpResponseHandler() {
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
				JSONArray array = null;
				Log.i("response", response + "");
				try {
					array = response.getJSONArray("list");
					for (int i = array.length() - 1; i >= 0; i--) {
						// for (int i = 0; i < array.length(); i++) {
						Map<String, Object> listItem = new HashMap<String, Object>();
						listItem.put("nickname","接收<=="+array.getJSONObject(i)
								.getString("nickname").toString());
						listItem.put("paid_time", array.getJSONObject(i)
								.getString("received_time").toString());
						listItem.put("coins_paid", "+"+array.getJSONObject(i)
								.getString("coins_received").toString());
						listItems.add(listItem);
					}
					Message msg = new Message();
					msg.what = 0x123;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Toast.makeText(getApplicationContext(), "扣费失败，请检查您的余额",
						Toast.LENGTH_SHORT).show();
			}

		}
	};
}
