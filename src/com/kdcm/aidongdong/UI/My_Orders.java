package com.kdcm.aidongdong.UI;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.OrderAdapter;

public class My_Orders extends Activity {
	private String URL_Orders;
	private Thread mThread;
	private String str_json;
	private List<Map<String, Object>> data = null;
	private Handler mHandler;
	private ListView lv_order;
	private Intent it;
	private String status="1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders);
		mHandler=new Handler(){
			public void handleMessage(Message msg) {
				if (msg != null) {
					ShowData();
				}
			}
		};
		init();
	}

	protected void ShowData() {
		OrderAdapter adapter=new OrderAdapter(this, data);
		lv_order.setAdapter(adapter);
	}

	private void init() {
		it=getIntent();
		status=it.getStringExtra("status");
		lv_order=(ListView)findViewById(R.id.lv_order);
		URL_Orders = Conf.APP_URL + "getOrders";
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
		
		str_json = HttpUtil.getJsonContent(this, URL_Orders);
		String str_result = HttpUtil.getResult(str_json);
		if (str_result.equals("1")) {
			data=JsonTools.getOrders(str_json, status);
			if(data.size()>0){
				Message msg=new Message();
				mHandler.sendMessage(msg);
			}
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
