package com.kdcm.aidongdong;

import java.util.HashMap;

import com.baidu.frontia.Frontia;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.UI.PushActivity;
import com.kdcm.aidongdong.UI.CheckPhone;
import com.kdcm.aidongdong.UI.ShareActivity;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.Person;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btn_login;
	private String TAG = "MainActivity";
	private EditText et_username;
	private EditText et_psd;
	String mResult;
	Handler mHandler = new Handler();
	private Button btn_reg;
	private Intent intent; 
	String phone;
	private Button btn_push;
	private Button btn_share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SMSSDK.initSDK(this, Conf.SMSKEY, Conf.SMSSecret);
		Frontia.init(this.getApplicationContext(), Conf.APIKEY);
		
		setContentView(R.layout.activity_main);
		intent = getIntent();
		phone = intent.getStringExtra("phone");
		btn_reg = (Button) findViewById(R.id.btn_reg);
		et_username = (EditText) findViewById(R.id.et_username);
		et_username.setText(phone);
		et_psd = (EditText) findViewById(R.id.et_psd);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_push=(Button)findViewById(R.id.btn_push);
		btn_share=(Button)findViewById(R.id.btn_share);
		btn_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent = new Intent(getApplicationContext(), ShareActivity.class);
				startActivity(intent);
			}
		});
		btn_push.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				intent = new Intent(getApplicationContext(), PushActivity.class);
				startActivity(intent);


			}
		});

		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					Toast.makeText(getApplicationContext(), "验证成功",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "输入有误或检查网络",
							Toast.LENGTH_SHORT).show();
				}
			}
		};
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread() {
					public void run() {
						String path = Conf.APP_URL + "login&login_name="
								+ et_username.getText().toString()
								+ "&login_password="
								+ et_psd.getText().toString();
						String jsonstring = HttpUtil.getJsonContent(path);
						mResult = HttpUtil.getResult(jsonstring);
						Log.i(TAG, jsonstring);
						if (!jsonstring.equals("ERROR")) {
							Person person = JsonTools.getPerson("data",
									jsonstring);
							Log.i(TAG, "name"+person.getUsername());
						}
						if (mResult != null) {
							
							Message message = new Message();
							message.what = Integer.parseInt(mResult);
							mHandler.sendMessage(message);
						}
					}
				}.start();
			}
		});
		btn_reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				intent = new Intent(getApplicationContext(), CheckPhone.class);
				RegisterPage registerPage = new RegisterPage();
				registerPage.setRegisterCallback(new EventHandler() {
					public void afterEvent(int event, int result, Object data) { // 解析注册结果
						if (result == SMSSDK.RESULT_COMPLETE) {

							@SuppressWarnings("unchecked")
							HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
							String phone = (String) phoneMap.get("phone");

							intent.putExtra("phone", phone);
							startActivity(intent);
						}
					}
				});
				registerPage.show(getApplicationContext());

			}
		});
	}

}