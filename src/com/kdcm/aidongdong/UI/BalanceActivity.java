package com.kdcm.aidongdong.UI;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.tools.DataTools;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

public class BalanceActivity extends Activity implements OnClickListener {
	private Button btn_submit;
	private String ids;
	private Intent it;
	private String zongjia;
	private double dikou = 0;
	private Button btn_back;
	private View inc_add;
	private TextView tv_zongjia;
	private TextView tv_dikou;
	private EditText et_dikou;
	private TextView tv_dikoued;
	private TextView tv_money;
	/**
	 * 添加订单是否成功
	 */
	private Boolean isSuccess = false;
	/**
	 * 实付金额
	 */
	private double d_money = 0.00;
	/**
	 * 金币数量
	 */
	private double coins = 0;
	private Button btn_address;
	private TextView tv_name, tv_phone, tv_add, tv_coins;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance);
		init();
	}

	private void init() {
		tv_coins = (TextView) findViewById(R.id.tv_coins);
		tv_coins.setText(DataTools.readData(this, "coins")+"个");
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_add = (TextView) findViewById(R.id.tv_add);
		btn_address = (Button) findViewById(R.id.btn_address);
		btn_address.setOnClickListener(this);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
		tv_money = (TextView) findViewById(R.id.tv_money);
		tv_dikoued = (TextView) findViewById(R.id.tv_dikoued);
		et_dikou = (EditText) findViewById(R.id.et_dikou);
		et_dikou.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable et) {

				try {
					coins = Integer.valueOf(et.toString());
				} catch (Exception e) {

				}

				if (coins * 0.01 > dikou) {
					Toast.makeText(getApplicationContext(), "金币超过最大抵扣额",
							Toast.LENGTH_SHORT).show();
				} else {
					tv_dikoued.setText("￥："
							+ String.format("%.2f", coins * 0.01));
					d_money = Double.valueOf(zongjia.toString()) - coins * 0.01;
					tv_money.setText("￥：" + String.format("%.2f", d_money));
				}
			}
		});
		tv_dikou = (TextView) findViewById(R.id.tv_dikou);
		tv_zongjia = (TextView) findViewById(R.id.tv_zongjia);
		inc_add = (View) findViewById(R.id.inc_add);
		inc_add.setVisibility(View.INVISIBLE);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		it = getIntent();
		zongjia = it.getStringExtra("zongjia");
		ids = it.getStringExtra("ids");
		// dikou = Integer.valueOf(it.getStringExtra("str_dikou").toString());
		dikou = Double.valueOf(it.getStringExtra("str_dikou").toString())
				.doubleValue();
		et_dikou.setText(String.format("%.0f", dikou * 100));
		d_money = Double.valueOf(zongjia.toString()) - Double.valueOf(dikou);
		tv_money.setText("￥：" + String.format("%.2f", d_money));
		Log.i("d_money", d_money + "");
		tv_zongjia.setText("￥：" + zongjia);
		tv_dikoued.setText("￥：" + zongjia);
		tv_dikou.setText("￥：" + String.format("%.2f", dikou));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.btn_submit:
			if (!isSuccess) {
				if (Double.valueOf(zongjia.toString()) > 39) {
					String receiver_id = DataTools
							.readData(this, "receiver_id");
					String shiping_method = DataTools.readData(this,
							"shiping_method");
					HttpUtils.addOrder(res_order, ids, (int) coins,
							receiver_id, shiping_method);
				} else {
					HttpUtils.addOrder(res_order, ids, (int) coins, null, null);
				}

			} else {
				Toast.makeText(this, "已经成功添加到订单了，请到未付款订单查看", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.btn_address:
			if (Double.valueOf(zongjia.toString()) > 39) {
				it = new Intent(this, MyAddressActivity.class);
				it.putExtra("come", "balance");
				startActivity(it);
			} else {
				Toast.makeText(this, "购买数额没有超过39元，无法选择收货地址", Toast.LENGTH_SHORT)
						.show();
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
				Toast.makeText(getApplicationContext(), "添加购物车成功",
						Toast.LENGTH_SHORT).show();
				isSuccess = true;
				savaData();
				Log.i("response", response + "");

				try {
					String id = "";
					String data = response.getString("data");
					id = new JSONObject(data).getString("id");
					Log.i("mid", id + data);
					toAboutOrder(id);

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

	protected void toAboutOrder(String id) {
		it = new Intent(BalanceActivity.this, AboutOrderActivity.class);
		it.putExtra("id", id);
		startActivity(it);
	}

	protected void savaData() {
		DataTools.saveDaTa(this, "receiver_id", null);
		DataTools.saveDaTa(this, "shiping_method", null);
		DataTools.saveDaTa(this, "ads_name", null);
		DataTools.saveDaTa(this, "ads_phone", null);
		DataTools.saveDaTa(this, "ads_ads", null);
	}

	@Override
	protected void onResume() {
		String phone = DataTools.readData(this, "ads_phone");
		String name = DataTools.readData(this, "ads_name");
		String ads_ads = DataTools.readData(this, "ads_ads");
		if (phone != null) {
			Log.i("phone", phone);
			tv_phone.setText(phone);
			tv_name.setText(name);
			tv_add.setText(ads_ads);
			inc_add.setVisibility(View.VISIBLE);
		} else {
			inc_add.setVisibility(View.INVISIBLE);
		}
		super.onResume();
	}
}
