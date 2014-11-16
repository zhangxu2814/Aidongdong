package com.kdcm.aidongdong.UI;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
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
import com.kdcm.aidongdong.Date.BaseActivity;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.DataTools;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.Person;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

public class LoginActivity extends BaseActivity implements OnClickListener {
	public String json;
	public static String coins="1";
	private String login_name="login_name=";
	Person person;
	String TAG = "LoginActivity";
	/**
	 * 用户名
	 */
	private EditText et_username;
	/**
	 * 密码
	 */
	private EditText et_password;
	/**
	 * 登陆按钮
	 */
	private Button btn_login;
	/**
	 * 忘记密码
	 */
	private TextView tt_forget;
	/**
	 * 免费注册
	 */
	private TextView tv_reg;
	/**
	 * 子线程负责联网
	 */
	private Thread mThread;
	/**
	 * url
	 */
	private String URLpath;
	/**
	 * 返回的规则号
	 */
	private String mResult;
	private Handler mHandler;
	/**
	 * 页面跳转线程
	 */
	private Runnable mRunnable;
	/**
	 * 数据读取进度条
	 */
	private ProgressDialog loadingPDialog = null;
	private String jsonstring;
	private Intent mIntent;
	private int NET_ERROR = 404;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loadingPDialog = new ProgressDialog(this);
		loadingPDialog.setMessage("正在加载....");
		loadingPDialog.setCancelable(false);
		AsyncHttpClient request = HttpUtils.getClient();
		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
		request.setCookieStore(myCookieStore);
		init();
		PushAgent mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
		String device_token = UmengRegistrar.getRegistrationId(this);
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					Toast.makeText(getApplicationContext(), "验证成功",
							Toast.LENGTH_SHORT).show();
					Conf.isLogout = false;

					postDelayed(mRunnable, 100);
				} else if (msg.what == NET_ERROR) {
					Toast.makeText(getApplicationContext(), "连接服务器失败，请检查网络连接",
							Toast.LENGTH_SHORT).show();
				} else {
					Log.i("kdcm", msg.what + "");
					Toast.makeText(getApplicationContext(), "请检查您的用户名或密码",
							Toast.LENGTH_SHORT).show();
				}
				loadingPDialog.dismiss();
			}
		};

	}

	/**
	 * 初始化
	 */
	private void init() {
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
		tt_forget = (TextView) findViewById(R.id.tt_forget);
		tt_forget.setOnClickListener(this);
		tv_reg = (TextView) findViewById(R.id.tv_reg);
		tv_reg.setOnClickListener(this);
		mRunnable = new Runnable() {
			@Override
			public void run() {

				Intent intent = new Intent(LoginActivity.this,
						SportCheckActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
				mThread.interrupt();
			}

		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			loadingPDialog.show();
			myLogin();
			// login();写的太乱 废除了。
			break;
		case R.id.tt_forget:
			forget();

			break;
		case R.id.tv_reg:
			toReg();
			break;
		default:
			break;
		}
	}

	private void myLogin() {
		HttpUtils.login(et_username.getText().toString(), et_password.getText().toString(), res);

	}
	JsonHttpResponseHandler res = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			Log.i("response",response+"");
			json=response+"";
			super.onSuccess(statusCode, headers, response);
			try {
				
				String data=response.getString("data");
				JSONObject jsonObj = new JSONObject(data);
				coins=jsonObj.get("coins").toString();
				login_name+=jsonObj.get("username").toString();
				Log.i("login_name", login_name);
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int result = 0;
			try {
				data();
				result = Integer.valueOf(response.getString("result"));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (statusCode == 200 & result == 1) {
			
				Toast.makeText(getApplicationContext(), "登陆成功",
						Toast.LENGTH_SHORT).show();
				loadingPDialog.dismiss();
				Intent intent = new Intent(LoginActivity.this,
						SportCheckActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			} else {
				Toast.makeText(getApplicationContext(), "请检查您的账号或者密码是否正确",
						Toast.LENGTH_SHORT).show();
				loadingPDialog.dismiss();
			}
		}
	};
		

	private void forget() {
		// RegisterPage registerPage = new RegisterPage();
		// registerPage.setRegisterCallback(new EventHandler() {
		// public void afterEvent(int event, int result, Object data) { //
		// 解析注册结果
		// if (result == SMSSDK.RESULT_COMPLETE) {
		//
		// @SuppressWarnings("unchecked")
		// HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
		// String phone = (String) phoneMap.get("phone");
		// Intent intent = new Intent(getApplicationContext(),
		// ForgetActivity.class);
		// intent.putExtra("phone", phone);
		// startActivity(intent);
		//
		//
		// }
		// }
		// });
		// registerPage.show(getApplicationContext());
		mIntent = new Intent(LoginActivity.this, CheckPhone.class);
		mIntent.putExtra("type", "Forget");
		startActivity(mIntent);

	}

	protected void data() {
		DataTools.saveDaTa(this, "login_message", json);	
		DataTools.saveDaTa(this, "username", login_name);
	}

	private void toReg() {
		mIntent = new Intent(LoginActivity.this, CheckPhone.class);
		mIntent.putExtra("type", "Reg");
		startActivity(mIntent);

	}

	/**
	 * 登陆方法
	 */
	private void login() {
		jsonstring = null;
		mResult = null;
		URLpath = Conf.APP_URL + "login&login_name="
				+ et_username.getText().toString() + "&login_password="
				+ et_password.getText().toString();

		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				saveData();

			}
		});
		mThread.start();
	}

	protected void saveData() {
		jsonstring = HttpUtil.getJsonContent(this, URLpath);
		mResult = HttpUtil.getResult(jsonstring);
		if (mResult != null) {
			Message message = new Message();
			message.what = Integer.parseInt(mResult);
			mHandler.sendMessage(message);
			Log.i(TAG, mResult);
			message = null;
		} else {
			mHandler.sendEmptyMessage(NET_ERROR);
		}
		if (!jsonstring.equals("ERROR")) {
			person = JsonTools.getPerson("data", jsonstring);
			DataTools.saveDaTa(this, "login_message", jsonstring);
			DataTools.saveDaTa(this, "username",
					"login_name=" + person.getUsername());
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mHandler.removeCallbacks(mRunnable);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
