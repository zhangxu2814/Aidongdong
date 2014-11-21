package com.kdcm.aidongdong.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.listviewTool.SwipeDismissListView;
import com.kdcm.aidongdong.listviewTool.SwipeDismissListView.OnDismissCallback;
import com.kdcm.aidongdong.tools.DataTools;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MyAddressActivity extends Activity {
	private SwipeDismissListView swipeDismissListView;
	// private ArrayAdapter<String> adapter;
	SimpleAdapter simpleAdapter;
	List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
	String receiver_id;
	private Button btn_submit;
	private Intent it;
	private String come="myads";
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x123) {
				simpleAdapter.notifyDataSetChanged();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myaddress);
		init();
	}

	private void init() {
		it = getIntent();
		come = it.getStringExtra("come")+"";
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				it = new Intent(MyAddressActivity.this,
						Activity_addReceiver.class);
				startActivity(it);
			}
		});
		// HttpUtils.getReceivers(res_rec);
		swipeDismissListView = (SwipeDismissListView) findViewById(R.id.swipeDismissListView);
		simpleAdapter = new SimpleAdapter(this, listItems, R.layout.item_add,
				new String[] { "address", "phone", "receiver" }, new int[] {
						R.id.tv_add, R.id.tv_phone, R.id.tv_name });
		// adapter = new ArrayAdapter<String>(this, R.layout.item_add,
		// R.id.tv_name, dataSourceList);
		swipeDismissListView.setAdapter(simpleAdapter);
		swipeDismissListView.setOnDismissCallback(new OnDismissCallback() {

			@Override
			public void onDismiss(int dismissPosition) {
				receiver_id = listItems.get(dismissPosition).get("id")
						.toString()+"";
				listItems.remove(dismissPosition);
				simpleAdapter.notifyDataSetChanged();
				HttpUtils.delReceiver(res_del, receiver_id);
			}
		});

		swipeDismissListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					if (come.equals("balance")) {
						MyAddressActivity.this.finish();
						saveData(position);
					}
				} catch (Error e) {

				}
				Toast.makeText(MyAddressActivity.this,
						listItems.get(position) + "", Toast.LENGTH_SHORT)
						.show();
			}
		});

	}

	protected void saveData(int position) {
		DataTools.saveDaTa(this, "receiver_id",
				listItems.get(position).get("id").toString());
		DataTools.saveDaTa(this, "shiping_method", "1");
		DataTools.saveDaTa(this, "ads_name",
				listItems.get(position).get("receiver").toString());
		DataTools.saveDaTa(this, "ads_phone",
				listItems.get(position).get("phone").toString());
		DataTools.saveDaTa(this, "ads_ads",
				listItems.get(position).get("address").toString());
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
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (statusCode == 200 & result == 1) {
				listItems.clear();
				try {
					JSONArray array = response.getJSONArray("list");
					for (int i = 0; i < array.length(); i++) {
						Map<String, Object> listItem = new HashMap<String, Object>();
						listItem.put("address", array.getJSONObject(i)
								.getString("address").toString());
						listItem.put("phone",
								array.getJSONObject(i).getString("phone")
										.toString());
						listItem.put("receiver", array.getJSONObject(i)
								.getString("receiver").toString());
						listItem.put("id",
								array.getJSONObject(i).getString("id")
										.toString());
						listItems.add(listItem);
					}
					Message msg = new Message();
					msg.what = 0x123;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getApplicationContext(), "请重新登录",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	JsonHttpResponseHandler res_del = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			super.onSuccess(statusCode, headers, response);
			int result = 0;
			try {
				result = Integer.valueOf(response.getString("result"));

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (statusCode == 200 & result == 1) {
				
				HttpUtils.getReceivers(res_rec);
			} else {
				Toast.makeText(getApplicationContext(), "请到检查是否长时间未登录",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onResume() {
		listItems.clear();
		HttpUtils.getReceivers(res_rec);
		super.onResume();
	};
}
