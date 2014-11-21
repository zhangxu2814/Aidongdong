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
import com.kdcm.aidongdong.Date.BaseActivity;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.listviewTool.AddFriendAdapter;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.MyAdapter;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MyRequestsActivity extends BaseActivity {
	/**
	 * 好友列表
	 */
	private ListView lv_friend;
	/**
	 * URLPATH
	 */
	private String URL_MyRequests;
	/**
	 * Json_MyRequests;
	 */
	private String json_MyRequests = null;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();;
	List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
	private String msg = "是否添加好友";
	AddFriendAdapter adf;
	MyAdapter my;
	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {

				// 使用adapter显示服务器响应
				adf.notifyDataSetChanged();

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myrequests);
		// 如果网络连接就进行下一步
		if (Conf.NETWORK_ON != false) {
			init();
		} else {

		}
	}

	private void init() {
		lv_friend = (ListView) findViewById(R.id.lv_friend);
		adf = new AddFriendAdapter(this, data);
		lv_friend.setAdapter(adf);
		HttpUtils.getMyAccepts(res_req);

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

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
				JSONArray jsonArray;
				try {
					jsonArray = response.getJSONArray("list");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jo = (JSONObject) jsonArray.opt(i);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("nickname", jo.get("nickname").toString());
						map.put("id", jo.get("id"));
						map.put("phone", jo.getString("phone"));
						data.add(map);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = 0x123;
				handler.sendMessage(msg);
			} else {
				Toast.makeText(getApplicationContext(), "请到检查库存是否充足",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
}
