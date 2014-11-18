package com.kdcm.aidongdong.UI;

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
	private List<Map<String, Object>> data = null;
	private List<Map<String, Object>> data_zengsong = null,
			data_jieshou;
	private String URL_Givedcoins;
	private String jsonstring;
	/**
	 * 子线程负责联网
	 */
	private Thread mThread;
	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {

				// 使用adapter显示服务器响应
				mAdapter_jieshou.notifyDataSetChanged();

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_givedcoins);
		init();
		showData();
	}

	private void showData() {
		if (data == null) {

			try {
				getData();
				Thread.sleep(100);
				showData();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			mAdapter_zengsong = new GivedCoinsAdapter(this, data);
			lv_zengsong.setAdapter(mAdapter_zengsong);
			lv_jieshou.setAdapter(mAdapter_zengsong);

		}
	}

	private void getData() {
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				saveData();
			}
		});

		mThread.start();
	}

	protected void saveData() {
		jsonstring = HttpUtil.getJsonContent(this, URL_Givedcoins);
		data = JsonTools.getGivedcoins(jsonstring);
		Log.i("kdcm", data + "");

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
		URL_Givedcoins = Conf.APP_URL + "getGivedCoins";
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
				try {
					array = response.getJSONArray("list");
					for (int i = 0; i < array.length(); i++) {
						Map<String, Object> listItem = new HashMap<String, Object>();
						listItem.put("nickname", array.getJSONObject(i)
								.getString("nickname").toString());
						listItem.put("phone", array.getJSONObject(i)
								.getString("phone").toString());
						listItem.put("coins_paid", array.getJSONObject(i)
								.getString("coins_paid").toString());
						listItem.put("paid_time", array.getJSONObject(i)
								.getString("paid_time").toString());
						data_jieshou.add(listItem);
						Log.i("data_jieshou", data_jieshou+"");
					}
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