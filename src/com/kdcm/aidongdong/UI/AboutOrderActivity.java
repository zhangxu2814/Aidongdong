package com.kdcm.aidongdong.UI;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.alipay.Fiap;
import com.kdcm.aidongdong.tools.DataTools;
import com.kdcm.aidongdong.tools.MD5;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

public class AboutOrderActivity extends Activity implements OnClickListener {
	private Intent it;
	private String id = null;
	private TextView tv_price;
	private TextView tv_deduction;
	private TextView tv_cash;
	private LinearLayout ll_money;
	private TextView tv_balance;
	private LinearLayout ll_alipay;
	String total_price = "", need_cash = null, total_deduction = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutorder);
		init();
	}

	private void init() {
		ll_alipay = (LinearLayout) findViewById(R.id.ll_alipay);
		ll_alipay.setOnClickListener(this);
		tv_balance = (TextView) findViewById(R.id.tv_balance);
		ll_money = (LinearLayout) findViewById(R.id.ll_money);
		ll_money.setOnClickListener(this);
		it = getIntent();
		id = it.getStringExtra("id");
		HttpUtils.getOrders(res_order, "" + id);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_deduction = (TextView) findViewById(R.id.tv_deduction);
		tv_cash = (TextView) findViewById(R.id.tv_cash);
		tv_balance.setText("￥：" + DataTools.readData(this, "balance"));
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
			try {
				if (statusCode == 200 & result == 1
						& response.getInt("total_count") != 0) {
					Toast.makeText(getApplicationContext(), "成功",
							Toast.LENGTH_SHORT).show();
					try {

						JSONArray array = response.getJSONArray("list");
						total_price = array.getJSONObject(0)
								.getString("total_price").toString();
						need_cash = array.getJSONObject(0)
								.getString("need_cash").toString();
						total_deduction = array.getJSONObject(0)
								.getString("total_deduction").toString();
						tv_price.setText("￥："
								+ String.format("%.2f",
										Double.valueOf(total_price)));
						tv_cash.setText("￥："
								+ String.format("%.2f",
										Double.valueOf(need_cash)));
						tv_deduction.setText("￥："
								+ String.format("%.2f",
										Double.valueOf(total_deduction)));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					Toast.makeText(getApplicationContext(), "数据为空，请检查",
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_money:
			PayUseMoney();
			break;
		case R.id.ll_alipay:
			Fiap fiap = new Fiap(AboutOrderActivity.this);
			// 调用支付方法，并传入支付金额
			fiap.android_pay(Double.valueOf(need_cash));
			break;
		default:
			break;
		}
	}

	public void PayUseMoney() {
		Builder dialog = new AlertDialog.Builder(this);
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.editbox_layout, null);
		dialog.setView(layout);
		dialog.setTitle("请输入登陆密码");
		final EditText et_user = (EditText) layout.findViewById(R.id.et_user);
		et_user.setInputType(0x81);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String psd = et_user.getText().toString();
				if (psd.length() > 0) {
					checking(psd);
				} else {
					Toast.makeText(AboutOrderActivity.this, "请输入密码",
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

	/**
	 * 检查密码是否正确
	 * 
	 * @param psd
	 */
	protected void checking(String psd) {
		String MD5psd = MD5.getMD5(psd.getBytes());
		String password = DataTools.readData(this, "password");
		if (MD5psd.equals(password + "")) {
			Toast.makeText(AboutOrderActivity.this, "密码正确", Toast.LENGTH_SHORT)
					.show();
			HttpUtils.payOrder(res_pay, id);
		} else {
			Toast.makeText(AboutOrderActivity.this, "不正确，请重新输入",
					Toast.LENGTH_SHORT).show();
		}
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
			} else if (result == 6255) {
				Toast.makeText(getApplicationContext(), "您已经成功付过款了",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "检测到您的本次付费异常，请重新登陆",
						Toast.LENGTH_SHORT).show();
			}

		}
	};
}
