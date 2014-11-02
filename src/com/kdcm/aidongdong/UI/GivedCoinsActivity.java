package com.kdcm.aidongdong.UI;

import java.util.List;
import java.util.Map;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.GivedCoinsAdapter;
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
	/**
	 * 子线程负责联网
	 */
	private Thread mThread;

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
			GivedCoinsAdapter mAdapter = new GivedCoinsAdapter(this, data);
			lv_context.setAdapter(mAdapter);
		}
	}

	private void getData() {
		mThread=new Thread(new Runnable() {

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
		lv_context = (ListView) findViewById(R.id.lv_context);
		URL_Givedcoins = Conf.APP_URL + "getGivedCoins";
	}

}
