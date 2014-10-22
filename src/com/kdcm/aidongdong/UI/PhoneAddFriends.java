package com.kdcm.aidongdong.UI;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
		phone = et_phone.getText().toString();
		URL_addFri = Conf.APP_URL + "addFriend&friend_phone=" + phone;
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(phone.length()>3){
				new Thread(new Runnable() {

					@Override
					public void run() {
						HttpUtil.getJsonContent(URL_addFri);

					}
				}).start();}else{
					System.exit(0);
				}

			}
		});

	}

}
