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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.tools.OrderAdapter;
import com.kdcm.aidongdong.tools.OrderDetailAdapter;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

public class OrderDetailsActivity extends Activity implements OnClickListener {
	private Intent it;
	private String id;
	private String status = "";
	private LinearLayout ll_back;
	private Button btn_sub;
	private ListView lv_order;
	OrderDetailAdapter simpleAdapter;
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
		setContentView(R.layout.activity_orderdetails);
		it = getIntent();
		id = it.getStringExtra("id");
		status = it.getStringExtra("status");
		Log.i("show", id + "   " + status);
		init();
	}

	private void init() {
		lv_order = (ListView) findViewById(R.id.lv_order);
		lv_order.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int i,
					long arg3) {
				if (status.equals("4")) {
					String product_id = listItems.get(i).get("id").toString();
					addProductComment(product_id);
				}
			}
		});
		simpleAdapter = new OrderDetailAdapter(this, listItems);
		lv_order.setAdapter(simpleAdapter);
		btn_sub = (Button) findViewById(R.id.btn_sub);
		if (status.equals("2")) {
			btn_sub.setVisibility(View.GONE);
		} else if (status.equals("3")) {
			btn_sub.setText("确认收货");
		} else if (status.equals("4")) {
			btn_sub.setVisibility(View.GONE);
		}
		btn_sub.setOnClickListener(this);
		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(this);
		HttpUtils.getOrders(res_order, id);

	}

	protected void addProductComment(final String product_id) {
		Builder dialog = new AlertDialog.Builder(this);
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.editbox_layout, null);
		dialog.setView(layout);
		dialog.setTitle("请输入评价");
		final EditText et_user = (EditText) layout.findViewById(R.id.et_user);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String desc = et_user.getText().toString();
				if (desc.length() > 0) {
					Log.i("show", product_id + desc);
					HttpUtils.addProductComment(res_addP, product_id, desc);
				} else {
					Toast.makeText(getApplicationContext(), "输入内容为空",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}

		});
		dialog.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_back:
			this.finish();
			break;
		case R.id.btn_sub:
			if (status.equals("1")) {
				it = new Intent(this, AboutOrderActivity.class);
				it.putExtra("id", id);
				startActivity(it);
				this.finish();
			} else if (status.equals("3")) {
				HttpUtils.confirmReceipt(res_confirm, id);
			}
			break;
		default:
			break;
		}
	}

	JsonHttpResponseHandler res_order = new JsonHttpResponseHandler() {
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
				try {
					JSONArray array = response.getJSONArray("list")
							.getJSONObject(0).getJSONArray("shopping_carts");
					for (int i = 0; i < array.length(); i++) {
						Map<String, Object> listItem = new HashMap<String, Object>();
						listItem.put("comment", array.getJSONObject(i)
								.getString("comment").toString());
						listItem.put("status", status);
						listItem.put("id",
								array.getJSONObject(i).getString("id")
										.toString());
						listItem.put("name",
								array.getJSONObject(i).getString("name")
										.toString());
						listItem.put("price",
								array.getJSONObject(i).getString("price")
										.toString());
						listItem.put("number", "x"
								+ array.getJSONObject(i).getString("number")
										.toString());
						String pic = array.getJSONObject(i).getString("pics")
								.toString();
						JSONArray p_url = new JSONArray(pic);
						String url = p_url.getJSONObject(0).getString("pic");
						listItem.put("url",
								"http://www.haoapp123.com/app/localuser/aidongdong/"
										+ url);
						listItems.add(listItem);
					}
					Message msg = new Message();
					msg.what = 0x123;
					handler.sendMessage(msg);
					Log.i("show", listItems + "");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Toast.makeText(getApplicationContext(), "请到检查库存是否充足",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	JsonHttpResponseHandler res_confirm = new JsonHttpResponseHandler() {
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
				Toast.makeText(getApplicationContext(), "确认收货成功",
						Toast.LENGTH_SHORT).show();
				listItems.clear();
				simpleAdapter.notifyDataSetChanged();
			} else {
				Toast.makeText(getApplicationContext(), "已经付过款了哟",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	JsonHttpResponseHandler res_addP = new JsonHttpResponseHandler() {
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
				Toast.makeText(getApplicationContext(), "评论成功",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "请到检查库存是否充足",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
}
