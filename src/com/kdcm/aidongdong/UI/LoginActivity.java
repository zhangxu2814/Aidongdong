package com.kdcm.aidongdong.UI;

import android.app.Activity;
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
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.Person;

public class LoginActivity extends BaseActivity implements OnClickListener {
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
	private TextView tt_forger;
	/**
	 * 免费注册
	 */
	private Button btn_reg;
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
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loadingPDialog = new ProgressDialog(this);
		loadingPDialog.setMessage("正在加载....");
		loadingPDialog.setCancelable(true);
		init();
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					Toast.makeText(getApplicationContext(), "验证成功",
							Toast.LENGTH_SHORT).show();
					postDelayed(mRunnable, 1000);
				} else {
					Toast.makeText(getApplicationContext(), "密码错误",
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
		tt_forger = (TextView) findViewById(R.id.tt_forget);
		tt_forger.setOnClickListener(this);
		btn_reg = (Button) findViewById(R.id.btn_reg);
		btn_reg.setOnClickListener(this);
		mRunnable = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, MyActivity.class);
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
			login();
			break;
		case R.id.tt_forget:
			break;
		case R.id.btn_reg:
			toReg();
			break;
		default:
			break;
		}
	}

	private void toReg() {
		mIntent=new Intent(LoginActivity.this, CheckPhone.class);
		startActivity(mIntent);
		
	}

	/**
	 * 登陆方法
	 */
	private void login() {
		jsonstring = null;
		mResult =null;
		URLpath = Conf.APP_URL + "login&login_name="
				+ et_username.getText().toString() + "&login_password="
				+ et_password.getText().toString();

		mThread = new Thread(new Runnable() {

			@Override
			public void run() {

				jsonstring = HttpUtil.getJsonContent(URLpath);
				mResult = HttpUtil.getResult(jsonstring);
				if (mResult != null) {
					Message message = new Message();
					message.what = Integer.parseInt(mResult);
					mHandler.sendMessage(message);
					Log.i(TAG, mResult);
					message=null;
				} else {
					mHandler.sendEmptyMessage(0);
				}
				if (!jsonstring.equals("ERROR")) {
					Person person = JsonTools.getPerson("data", jsonstring);
					Conf.jsonstring = jsonstring;
					Conf.username = "login_name=" + person.getUsername();
				}

			}
		});
		mThread.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mHandler.removeCallbacks(mRunnable);
		

	}
}
