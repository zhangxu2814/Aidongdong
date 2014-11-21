package com.kdcm.aidongdong.UI;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

public class Activity_addReceiver extends Activity implements OnClickListener {
	private static final String[] address = { "兰山区", "河东区", "罗庄区", "沂水县",
			"郯城县", "兰陵县", "莒南县", "临沭县", "费县", "蒙阴县", "平邑县", "沂南县" };
	private Spinner sp_address;
	private ArrayAdapter<String> adapter;
	private EditText et_address;
	private Button btn_submit;
	private String str_address = "";
	/**
	 * 收货人姓名
	 */
	private String receiver, phone;
	private EditText et_name, et_phone;
	private Button btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addreceiver);
		init();
	}

	private void init() {
		btn_back=(Button)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		et_name = (EditText) findViewById(R.id.et_name);
		et_phone = (EditText) findViewById(R.id.et_phone);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
		et_address = (EditText) findViewById(R.id.et_address);
		sp_address = (Spinner) findViewById(R.id.sp_address);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, address);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_address.setAdapter(adapter);
		sp_address.setOnItemSelectedListener(new SpinnerSelectedListener());
	}

	// 使用数组形式操作
	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onNothingSelected(AdapterView<?> arg0) {
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			str_address = "山东省临沂市" + address[arg2];

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			phone = et_phone.getText().toString();
			receiver = et_name.getText().toString();
			if (receiver.length() == 0) {
				Toast.makeText(this, "请输入收货人的姓名", Toast.LENGTH_SHORT).show();
			} else if (phone.length() == 0) {
				Toast.makeText(this, "请输入收货人电话", Toast.LENGTH_SHORT).show();
			} else if (et_address.getText().toString().length() == 0) {
				Toast.makeText(this, "请填写详细收货地址", Toast.LENGTH_SHORT).show();
			} else {
				str_address+=et_address.getText().toString();
				Log.i("address", str_address
						+ "名字" + receiver + "电话" + phone);
				HttpUtils.addReceiver(res_addrec, phone, receiver, str_address);
			}
			
			break;
		case R.id.btn_back:
			this.finish();
			break;
		default:
			break;
		}
	}
	JsonHttpResponseHandler res_addrec = new JsonHttpResponseHandler() {
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
				Toast.makeText(getApplicationContext(), "添加收货人地址成功",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "请检查是否长时间未登录！",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
}
