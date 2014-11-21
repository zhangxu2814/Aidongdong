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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * 众筹 给我钱
 * 
 * @author zhangxu
 * 
 */
public class GiveMoneyAvtivity extends Activity {
	private ListView lv_givemoney;
	List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
	SimpleAdapter simpleAdapter;
	EditText et_user;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_givemoney);
		init();
	}

	private void init() {
		lv_givemoney = (ListView) findViewById(R.id.lv_givemoney);
		simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.item_givemoney, new String[] { "nickname", },
				new int[] { R.id.tv_name });
		lv_givemoney.setAdapter(simpleAdapter);
		HttpUtils.getFriends(res_getFriends);
		lv_givemoney.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int i,
					long arg3) {

				String id = listItems.get(i).get("id").toString();
				toGiveMoner(id);
				Toast.makeText(getApplicationContext(), listItems.get(i) + "",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	protected void toGiveMoner(final String id) {
		Builder dialog = new AlertDialog.Builder(this);
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.editbox_layout, null);
		dialog.setView(layout);
		dialog.setTitle("请输入赠送金额");
		et_user = (EditText) layout.findViewById(R.id.et_user);
		et_user.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String coins = et_user.getText().toString();
				if (coins.length() > 0) {
					Toast.makeText(getApplicationContext(), id+""+coins,
							Toast.LENGTH_SHORT).show();
					 HttpUtils.giveCoins(res_giveCoins, id, coins);
				} else {
					Toast.makeText(getApplicationContext(), "没有输入金币数值赠送取消",
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

	JsonHttpResponseHandler res_getFriends = new JsonHttpResponseHandler() {
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
					JSONArray array = response.getJSONArray("list");
					for (int i = 0; i < array.length(); i++) {
						Map<String, Object> listItem = new HashMap<String, Object>();
						String time = array.getJSONObject(i)
								.getString("crowdfund_expire_time").toString();
						if (!time.equals("0000-00-00 00:00:00")) {
							listItem.put("id", array.getJSONObject(i).get("id"));
							listItem.put("nickname", array.getJSONObject(i)
									.get("nickname"));
							listItems.add(listItem);
						}
					}
					Message msg = new Message();
					msg.what = 0x123;
					handler.sendMessage(msg);
					Log.i("listItems", listItems + "");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(getApplicationContext(), "获取数据成功",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "请到检查库存是否充足",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	JsonHttpResponseHandler res_giveCoins = new JsonHttpResponseHandler() {
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
				Toast.makeText(getApplicationContext(), "赠送成功",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "失败"+result,
						Toast.LENGTH_SHORT).show();
			}
		}
	};
}
