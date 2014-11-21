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
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.listviewTool.DiffAdapter;
import com.kdcm.aidongdong.listviewTool.TitleFlowIndicator;
import com.kdcm.aidongdong.listviewTool.ViewFlow;
import com.kdcm.aidongdong.tools.GivedCoinsAdapter;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class GivedCoinsActivity extends Activity {
	GivedCoinsAdapter mAdapter_zengsong, mAdapter_jieshou;
	private ViewFlow viewFlow;
	private ListView lv_zengsong, lv_jieshou;
	private List<Map<String, Object>> data_zengsong = new ArrayList<Map<String, Object>>(),
			data_jieshou = new ArrayList<Map<String, Object>>();;
	private String URL_Givedcoins;
	private String jsonstring;
	JSONArray array_zongsog = null;
	/**
	 * 子线程负责联网
	 */
	private Thread mThread;
	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				mAdapter_jieshou.notifyDataSetChanged();
				
			}if(msg.what==0x321){
				mAdapter_zengsong.notifyDataSetChanged();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_givedcoins);
		init();
	}

	private void init() {
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		DiffAdapter adapter = new DiffAdapter(this);
		viewFlow.setAdapter(adapter);
		TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
		indicator.setTitleProvider(adapter);
		viewFlow.setFlowIndicator(indicator);
		lv_zengsong = (ListView) findViewById(R.id.lv_zengsong);
		lv_jieshou = (ListView) findViewById(R.id.lv_jieshou);
		mAdapter_jieshou = new GivedCoinsAdapter(this, data_jieshou);
		mAdapter_zengsong = new GivedCoinsAdapter(this, data_zengsong);
		lv_zengsong.setAdapter(mAdapter_zengsong);
		lv_jieshou.setAdapter(mAdapter_jieshou);
		HttpUtils.getGivedCoins(res_give);
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
				Toast.makeText(getApplicationContext(), "接受成功，正在解析", Toast.LENGTH_SHORT).show();
				Log.i("response", response + "");
				try {
					array_zongsog=response.getJSONArray("list");
					toZengsong(array_zongsog);
//					array = response.getJSONArray("list");
//					for (int i = array.length() - 1; i >= 0; i--) {
//						// for (int i = 0; i < array.length(); i++) {
//						Map<String, Object> listItem = new HashMap<String, Object>();
//						listItem.put("nickname", array.getJSONObject(i)
//								.getString("nickname").toString());
//						listItem.put("paid_time", array.getJSONObject(i)
//								.getString("paid_time").toString());
//						listItem.put("coins_paid", array.getJSONObject(i)
//								.getString("coins_paid").toString());
//						data_jieshou.add(listItem);
//					}
//					Message msg = new Message();
//					msg.what = 0x321;
//
//					handler.sendMessage(msg);
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
	JsonHttpResponseHandler res_give = new JsonHttpResponseHandler() {
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
						listItem.put("nickname", array.getJSONObject(i)
								.getString("nickname").toString());
						listItem.put("paid_time", array.getJSONObject(i)
								.getString("received_time").toString());
						listItem.put("coins_paid", array.getJSONObject(i)
								.getString("coins_received").toString());
						data_zengsong.add(listItem);
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

	protected void toZengsong(JSONArray array_zongsog2) {
		Log.i("zengsong", "Start");
		for (int i = array_zongsog2.length() - 1; i >= 0; i--) {
			// for (int i = 0; i < array.length(); i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			try {
				listItem.put("nickname", array_zongsog2.getJSONObject(i)
						.getString("nickname").toString());
				listItem.put("paid_time", array_zongsog2.getJSONObject(i)
						.getString("paid_time").toString());
				listItem.put("coins_paid", array_zongsog2.getJSONObject(i)
						.getString("coins_paid").toString());
				data_jieshou.add(listItem);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		Message msg = new Message();
		msg.what = 0x321;
		Log.i("zengsong", "end");
		handler.sendMessage(msg);		
	}
}