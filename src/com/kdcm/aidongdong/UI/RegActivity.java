package com.kdcm.aidongdong.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;

public class RegActivity extends Activity {
	private TextView tt_phone;
	private String phone;
	private EditText et_psd;
	private Button btn_next;
	private Intent intent;
	private Handler mHandler;
	private String path;
	private String TAG = "RegActivity";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		intent=getIntent();
		phone = intent.getStringExtra("phone");
		tt_phone = (TextView) findViewById(R.id.tt_phone);
		tt_phone.setText(phone);
		et_psd = (EditText) findViewById(R.id.et_psd1);
		btn_next = (Button) findViewById(R.id.btn_next);
		findViewById(R.id.btn_login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				intent = new Intent(RegActivity.this, LoginActivity.class);
				intent.putExtra("phone", phone);
				startActivity(intent);
				RegActivity.this.finish();
			}
		}

		);
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 6003) {
					Toast.makeText(getApplicationContext(), "密码过短",
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 1) {
					Toast.makeText(getApplicationContext(), "帐号注册成功",
							Toast.LENGTH_SHORT).show();

					mHandler.postDelayed(new Runnable() {

						public void run() {

							intent = new Intent(RegActivity.this,
									LoginActivity.class);
							intent.putExtra("phone", phone);
							startActivity(intent);
							RegActivity.this.finish();

						}

					}, 1000);

				}

			}
		};
		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (et_psd.getText().toString().length() < 6) {
					Toast.makeText(getApplicationContext(), "密码长度要超过6位",
							Toast.LENGTH_SHORT).show();
				} else {
					path = Conf.APP_URL + "register&phone="
							+ tt_phone.getText().toString() + "&password="
							+ et_psd.getText().toString() + "&user_type=4";
					Log.i(TAG, path);
					new Thread() {
						public void run() {
							String jsonstring = HttpUtil.getJsonContent(path);
							String mResult = HttpUtil.getResult(jsonstring);
							if (mResult != null) {
								Log.i(TAG, mResult);
								Message message = new Message();
								message.what = Integer.parseInt(mResult);
								mHandler.sendMessage(message);
							}
						}
					}.start();
				}

			}

		});

	}

}
