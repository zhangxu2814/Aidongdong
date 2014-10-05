package com.kdcm.aidongdong.UI;

import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;

public class ForgetActivity extends Activity implements OnClickListener {
	private Intent intent;
	private String phone;
	private Button btn_ok;
	private EditText et_psd1, et_psd2;
	/**
	 * 子线程负责联网
	 */
	private Thread mThread;
	private String URLpath;
	private boolean isSuccess=false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget);
		init();

	}

	private void init() {
		intent = getIntent();
		phone = intent.getStringExtra("phone");
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		et_psd1 = (EditText) findViewById(R.id.et_psd1);
		et_psd2 = (EditText) findViewById(R.id.et_psd2);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			if (et_psd1.getText().toString().length() < 6
					| et_psd2.getText().toString().length() < 6) {
				Toast.makeText(getApplicationContext(), "输入密码不足6位",
						Toast.LENGTH_SHORT).show();
			} else {
				if (et_psd1.getText().toString()
						.equals(et_psd2.getText().toString())) {
					toDo();
				} else {
					Toast.makeText(getApplicationContext(), "两次输入的密码不一致",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		default:
			break;
		}
	}

	private void toDo() {
		URLpath = Conf.APP_URL + "resetPassword&phone=" + phone + "&password="
				+ et_psd1.getText().toString();
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				HttpUtil.getJsonContent(URLpath);
				isSuccess=true;
				

			}
		});
		mThread.start();
		if(isSuccess){
		ForgetActivity.this.finish();}

	}

}
