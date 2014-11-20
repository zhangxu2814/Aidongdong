package com.kdcm.aidongdong.UI;

import org.apache.http.Header;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance);
		init();
	}

	private void init() {
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
		Log.i("zongjia", zongjia + "下一个" + dikou + "'" + LoginActivity.coins);
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
				HttpUtils.addOrder(res_order, ids, (int) coins);
			} else {
				Toast.makeText(this, "已经成功添加到订单了，请到未付款订单查看", Toast.LENGTH_SHORT)
						.show();
			}
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
				try {
					String id = response.getString("id");
					toAboutOrder(id);
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
		it = new Intent(getApplicationContext(), AboutOrderActivity.class);
		it.putExtra("id", id);
		startActivity(it);
	}
}
