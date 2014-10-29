package com.kdcm.aidongdong.UI;

import java.util.List;
import java.util.Map;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class GivedCoinsActivity extends Activity {
	/**
	 * listview
	 */
	private ListView lv_context;
	private List<Map<String, Object>> data = null;
	private String URL_Givedcoins;
	private String jsonstring;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		getData();
	}

	private void getData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				saveData();
			}
		}).start();

	}

	protected void saveData() {
		jsonstring = HttpUtil.getJsonContent(this, URL_Givedcoins);
		data = JsonTools.getFriends(jsonstring);
		Log.i("kdcm", data+"");

	}

	private void init() {
		lv_context = (ListView) findViewById(R.id.lv_context);
		URL_Givedcoins = Conf.APP_URL + "getGivedCoins";
	}

}
