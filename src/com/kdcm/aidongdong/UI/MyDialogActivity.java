package com.kdcm.aidongdong.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.tools.HttpUtil;

public class MyDialogActivity extends Activity implements OnClickListener {
	private Button btn_ok, btn_cancel;
	private TextView tv_mess;
	private Intent it;
	private String URL = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mydia);
		it = this.getIntent();
		URL = it.getStringExtra("URL");
		init();

	}

	private void init() {
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
		tv_mess = (TextView) findViewById(R.id.tv_mess);
		tv_mess.setText("  " + it.getStringExtra("msg"));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			if (URL != null){
				new Thread(new Runnable() {

					@Override
					public void run() {
						toDo();
					}
				}).start();
			}
			this.finish();
				break;
		case R.id.btn_cancel:
			this.finish();
			break;

		default:
			break;
		}
	}

	protected void toDo() {
		HttpUtil.getJsonContent(this,URL);		
	}
}