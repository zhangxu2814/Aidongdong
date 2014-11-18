package com.kdcm.aidongdong.UI;

import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.alipay.Fiap;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.OrderAdapter;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

public class My_Orders extends Activity {
	private String URL_Orders;
	private Thread mThread;
	private String str_json;
	private List<Map<String, Object>> data = null;
	private Handler mHandler;
	private ListView lv_order;
	private Intent it;
	private String status = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders);
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg != null) {
					ShowData();
				}
			}
		};
		init();
	}

	protected void ShowData() {
		OrderAdapter adapter = new OrderAdapter(this, data);
		lv_order.setAdapter(adapter);
	}

	private void init() {
		it = getIntent();
		status = it.getStringExtra("status");
		lv_order = (ListView) findViewById(R.id.lv_order);
		lv_order.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int i,
					long arg3) {

				String id = data.get(i).get("id").toString();
				Log.i("orderid", id);
				double cash = Double.valueOf(data.get(i).get("need_cash")
						.toString());
				if (status.equals("1")) {
					toPay(cash, id);
				}
			}
		});
		URL_Orders = Conf.APP_URL + "getOrders";
		getData();
	}

	protected void toPay(final double cash, final String id) {
		new AlertDialog.Builder(this)
				.setTitle("支付方式选择")
				.setMessage("请选择您的支付方式？")
				.setNegativeButton("余额付费",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								HttpUtils.payOrder(res_pay, id);
							}
						})
				.setPositiveButton("支付宝付费",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								Fiap fiap = new Fiap(My_Orders.this);
								// 调用支付方法，并传入支付金额
								fiap.android_pay(cash);
							}
						}).show();
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
			data = JsonTools.getOrders(str_json, status);
			if (data.size() > 0) {
				Message msg = new Message();
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

	JsonHttpResponseHandler res_pay = new JsonHttpResponseHandler() {
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
				Toast.makeText(getApplicationContext(), "付款成功",
						Toast.LENGTH_SHORT).show();
				data = null;
				getData();
			} else {
				Toast.makeText(getApplicationContext(), "扣费失败，请检查您的余额",
						Toast.LENGTH_SHORT).show();
			}

		}
	};
}
