package com.kdcm.aidongdong.UI;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * 用户详情界面
 * 
 * @author zhangxu
 * 
 */
public class AboutUserActivity extends Activity {
	/**
	 * 数据读取进度条
	 */
	private ProgressDialog loadingPDialog = null;
	private Intent it;
	/**
	 * user ID
	 */
	private String uID, phone, balance, coins, nickname, age, s_taddress;
	private int sex;
	private TextView tv_phone;
	private TextView tv_balance;
	private TextView tv_coins;
	private TextView tv_nickname;
	private TextView tv_age;
	private TextView tv_sex, tv_taddress;
	private Button btn_submit;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			tv_phone.setText(phone);
			tv_balance.setText(balance);
			tv_coins.setText(coins);
			tv_nickname.setText(nickname);
			tv_age.setText(age);
			if (sex == 0) {
				tv_sex.setText("未填写");
			} else if (sex == 1) {
				tv_sex.setText("男");
			} else {
				tv_sex.setText("女");
			}
			tv_taddress.setText(s_taddress);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutuser);
		init();
	}

	private void init() {
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				it = new Intent(getApplicationContext(), ChangeActivity.class);
//				it.putExtra("uID", uID);
				it.putExtra("phone", phone);
				startActivity(it);

			}
		});
		loadingPDialog = new ProgressDialog(this);
		loadingPDialog.setMessage("正在加载....");
		loadingPDialog.setCancelable(false);
		loadingPDialog.show();
		tv_taddress = (TextView) findViewById(R.id.tv_taddress);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_balance = (TextView) findViewById(R.id.tv_balance);
		tv_coins = (TextView) findViewById(R.id.tv_coins);
		tv_nickname = (TextView) findViewById(R.id.tv_nickname);
		tv_age = (TextView) findViewById(R.id.tv_age);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		it = getIntent();
		uID = it.getStringExtra("uID");
		HttpUtils.searchUser(res_search, uID);
	}

	JsonHttpResponseHandler res_search = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			super.onSuccess(statusCode, headers, response);
			int result = 0;
			try {
				result = Integer.valueOf(response.getString("result"));

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (statusCode == 200 & result == 1) {
				try {
					JSONArray array = response.getJSONArray("list");
					s_taddress = array.getJSONObject(0).getString(
							"training_address");
					phone = array.getJSONObject(0).getString("phone");
					balance = array.getJSONObject(0).getString("balance");
					coins = array.getJSONObject(0).getString("coins");
					nickname = array.getJSONObject(0).getString("nickname");
					age = array.getJSONObject(0).getString("age");
					sex = array.getJSONObject(0).getInt("sex");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Message msg = new Message();
				handler.sendMessage(msg);
				Toast.makeText(getApplicationContext(), "获取数据成功",
						Toast.LENGTH_SHORT).show();
				Log.i("response", response + "");
			} else {
				Toast.makeText(getApplicationContext(), "获取内容失败，请检查您的网络",
						Toast.LENGTH_SHORT).show();
			}
			loadingPDialog.dismiss();
		}
	};
}
