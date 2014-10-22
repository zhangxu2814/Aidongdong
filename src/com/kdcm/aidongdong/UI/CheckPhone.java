package com.kdcm.aidongdong.UI;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;

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
import android.widget.Toast;

/**
 * 
 * @author zhangxu
 */
public class CheckPhone extends Activity {
	private String TAG = "CheckPhone";
	/**
	 * 下一步
	 */
	private Button btn_next;
	/**
	 * URL
	 */
	private String str_url;
	/**
	 * 电话号码
	 */
	private EditText et_phone;
	private String jsonstring;
	/**
	 * 子线程负责联网
	 */
	private Thread mThread;
	private Handler mHandler;
	private String phone_registered = "phone_registered";
	private Intent intent;
	private String type;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkphone);
		intent = this.getIntent();
		type = intent.getStringExtra("type");
		init();
		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (et_phone.getText().length() == 11) {
					// 如果输入的是11位
					str_url = Conf.APP_URL + "getPhoneInfo&phone="
							+ et_phone.getText();
					Phone_registered();

				} else {
					Toast.makeText(CheckPhone.this, "您输入的号码不正确", Toast.LENGTH_SHORT).show();
				}

			}
		});
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					if (type.equals("Reg")) {
						Toast.makeText(getApplicationContext(), "该账号已经注册过",
								Toast.LENGTH_SHORT).show();
					}else if(type.equals("Forget")){
						SMSSDK();
					}
				} else {
					if (type.equals("Reg")) {
						SMSSDK();
					}else if(type.equals("Forget")) {
						Toast.makeText(getApplicationContext(), "该账号还没有注册",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		};

	}

	/**
	 * 验证手机号是否注册过
	 */
	protected void Phone_registered() {
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				jsonstring = HttpUtil.getJsonContent(str_url);
				if (!(jsonstring == null)) {
					phone_registered = JsonTools.getPhone_registered(
							"phone_registered", jsonstring);
					Message message = new Message();
					message.what = Integer.parseInt(phone_registered);
					mHandler.sendMessage(message);
					Log.i(TAG, phone_registered);
				}
			}
		});
		mThread.start();
	}

	private void init() {
		btn_next = (Button) findViewById(R.id.btn_next);
		et_phone = (EditText) findViewById(R.id.et_write_phone);

	}

	private void SMSSDK() {
		RegisterPage registerPage = new RegisterPage();
		registerPage.setPhone(et_phone.getText().toString());
		registerPage.setRegisterCallback(new EventHandler() {
			public void afterEvent(int event, int result, Object data) { // 解析注册结果
				if (result == SMSSDK.RESULT_COMPLETE) {

					@SuppressWarnings("unchecked")
					HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
					String phone = (String) phoneMap.get("phone");
					if (type.equals("Reg")) {
						intent = new Intent(CheckPhone.this, RegActivity.class);
					}else if(type.equals("Forget")) {
						intent = new Intent(getApplicationContext(),
							ForgetActivity.class);
					}
					intent.putExtra("phone", phone);
					startActivity(intent);
					CheckPhone.this.finish();

				}
			}
		});
		registerPage.show(getApplicationContext());

	}

}
