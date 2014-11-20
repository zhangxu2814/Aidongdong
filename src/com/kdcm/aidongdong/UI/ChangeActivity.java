package com.kdcm.aidongdong.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.Person;

public class ChangeActivity extends Activity implements OnClickListener {
	private String uID;
	private Intent it;
	private Button btn_submit;
	private Button btn_cancel;
	private EditText et_username;
	private String phone;
	private TextView tv_phone;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);
		init();

	}

	private void init() {
		it = getIntent();
		// uID=it.getStringExtra("uID");
		phone = it.getStringExtra("phone");
		tv_phone=(TextView)findViewById(R.id.tv_phone);
		tv_phone.setText(phone);
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
			this.finish();
			break;
		default:
			break;
		}
	}

	private void submit() {
	}
}