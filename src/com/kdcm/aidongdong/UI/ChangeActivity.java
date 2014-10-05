package com.kdcm.aidongdong.UI;

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
import com.kdcm.aidongdong.tools.Person;

public class ChangeActivity extends Activity implements OnClickListener {
	private Button btn_submit;
	private Button btn_cancel;
	private String URLpath;
	private EditText et_username;
	/**
	 * 子线程负责联网
	 */
	private Thread mThread;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);
		init();

	}

	private void init() {
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
		et_username = (EditText) findViewById(R.id.et_username);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			if (et_username.getText().length() > 1) {
				submit();
			} else {
				Toast.makeText(getApplicationContext(), "请输入要修改的昵称",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_cancel:
			Intent it = new Intent(this, MyActivity.class);
			startActivity(it);
			this.finish();
			break;
		default:
			break;
		}
	}

	private void submit() {
		URLpath = Conf.APP_URL + "edit&nickname=" + et_username.getText();
		et_username.setText("");

		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				Conf.jsonstring = HttpUtil.getJsonContent(URLpath);

			}
		});
		mThread.start();

	}

}
