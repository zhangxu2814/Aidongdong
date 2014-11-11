package com.kdcm.aidongdong.UI;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.SPCarAdapter;

public class SPCarActivity extends Activity implements OnClickListener {
	private String URL_SPCar;
	private Thread mThread;
	private String str_json;
	private String str_result;
	List<Map<String, Object>> data = null;
	private ListView lv_spcar;
	private Handler mHandler;
	private Button btn_jiesuan;
	private TextView tv_total;
	private SPCarAdapter adapter;
	private String zongjia;
	private TextView tv_dikou;
	private String str_dikou;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spcar);
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg != null) {
					ShowData();
				}
			}
		};
		init();
	}

	protected void Toast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	protected void ShowData() {
		tv_total.setText("金额：" + zongjia);
		tv_dikou.setText(str_dikou);
		adapter = new SPCarAdapter(this, data);
		lv_spcar.setAdapter(adapter);
	}

	private void init() {
		tv_dikou = (TextView) findViewById(R.id.tv_dikou);
		tv_total = (TextView) findViewById(R.id.tv_total);
		btn_jiesuan = (Button) findViewById(R.id.btn_jiesuan);
		lv_spcar = (ListView) findViewById(R.id.lv_spcar);
		URL_SPCar = Conf.APP_URL + "getShoppingCarts";
		getData();

	}

	private void getData() {
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				getJson();
			}
		});
		mThread.start();
	}

	protected void getJson() {
		str_json = HttpUtil.getJsonContent(this, URL_SPCar);

		str_result = HttpUtil.getResult(str_json);
		if (str_result.equals("1")) {
			data = JsonTools.getSPCar(str_json);

			if (data.size() > 0) {
				zongjia = (data.get(data.size() - 1).get("zongjia").toString());
				str_dikou = (data.get(data.size() - 1).get("dikou").toString());
			}
			Message msg = new Message();
			mHandler.sendMessage(msg);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_jiesuan:

			break;

		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		data = null;
		getData();
		Message msg = new Message();
		mHandler.sendMessage(msg);
		super.onResume();
	}
}
