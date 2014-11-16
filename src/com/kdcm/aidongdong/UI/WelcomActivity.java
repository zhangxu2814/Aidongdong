package com.kdcm.aidongdong.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import cn.smssdk.SMSSDK;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.api.FrontiaPush;
import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.BaseActivity;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;

/**
 * 
 * @author zhangxu 线程检测联网，获取最新版本 版本对比，如果版本号相同，则进入当前软件，如果不同则下载最新版本
 * 
 */

public class WelcomActivity extends BaseActivity {
	/**
	 * 跳转到主界面
	 */
	private Button btn_go;
	/**
	 * 子线程负责联网
	 */
	private Thread mThread;
	/**
	 * url
	 */
	private String URLpath;
	String version = null;
	Boolean isNewVersion = null;
	private Handler mHandler;
	AlertDialog.Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		SMSSDK.initSDK(this, Conf.SMSKEY, Conf.SMSSecret);
		Frontia.init(this.getApplicationContext(), Conf.APIKEY);
		Intent intent = new Intent(WelcomActivity.this,
				LoginActivity.class);
		startActivity(intent);
		WelcomActivity.this.finish();
		init();
		if (Conf.NETWORK_ON) {
			Version();
		}
		builder = new AlertDialog.Builder(this);
		// AlertDialog alert = builder.create();
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (!msg.obj.toString().equals("1.0")) {
					builder.setMessage("有新版本是否更新")
							.setCancelable(false)
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											Uri uri = Uri
													.parse("http://baidu.com");
											Intent it = new Intent(
													Intent.ACTION_VIEW, uri);
											startActivity(it);
											WelcomActivity.this.finish();
										}
									})
							.setNegativeButton("No",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.cancel();
										}
									}).show();
				}

			}
		};

		btn_go.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WelcomActivity.this,
						LoginActivity.class);
				startActivity(intent);
				WelcomActivity.this.finish();
			}
		});
	}

	void Version() {
		URLpath = Conf.APP_URL + "getAppVersion&platform=android";
		mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				saveData();
			}
		});
		mThread.start();

	}

	protected void saveData() {
		String jsonstr = HttpUtil.getJsonContent(this,URLpath);
		String data = JsonTools.getPhone_registered("data", jsonstr);
		version = JsonTools.getPhone_registered("version_name", data);

		Log.i(Conf.TAG, version);
		if (version.equals("1.0")) {
			Conf.isNewVersion = true;
		}
		Message message = new Message();
		message.obj = version;
		mHandler.sendMessage(message);		
	}

	private void init() {
		btn_go = (Button) findViewById(R.id.btn_go);

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}
