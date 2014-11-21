package com.kdcm.aidongdong.UI;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.BaseActivity;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;

public class PhoneAddFriends extends BaseActivity {
	private Button btn_ok;
	/**
	 * 添加好友的
	 */
	private String URL_addFri;
	private EditText et_phone;
	private String phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phoneaddfriends);
		init();
	}

	private void init() {
		et_phone = (EditText) findViewById(R.id.et_phone);

		URL_addFri = Conf.APP_URL + "addFriend&friend_phone=" + phone;
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				phone = et_phone.getText().toString();
				if (phone.length() > 3) {
					Toast.makeText(getApplicationContext(), "请求已发送！", Toast.LENGTH_SHORT)
					.show();
					toDo();
				} else {
					Toast.makeText(getApplicationContext(), "请检查号码输入是否正确",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

	}

	protected void toDo() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				saveData();

			}
		}).start();
	}

	protected void saveData() {
		HttpUtil.getJsonContent(this, URL_addFri);
		
	}

}
