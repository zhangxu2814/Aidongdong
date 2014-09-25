package com.kdcm.aidongdong.UI;

import cn.smssdk.SMSSDK;

import com.baidu.frontia.Frontia;
import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.BaseActivity;
import com.kdcm.aidongdong.Date.Conf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomActivity extends BaseActivity {
	/**
	 * 页面跳转线程
	 */
	private Runnable mRunnable;

	private Handler handler = new Handler();
	private Button btn_go;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		SMSSDK.initSDK(this, Conf.SMSKEY, Conf.SMSSecret);
		Frontia.init(this.getApplicationContext(), Conf.APIKEY);
		
		btn_go=(Button)findViewById(R.id.btn_go);
		// 定义WelcomeActivity秒跳转 LoginActivity
		mRunnable = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
					Intent intent = new Intent(WelcomActivity.this, LoginActivity.class);
					startActivity(intent);
					WelcomActivity.this.finish();
			}

		};
		btn_go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				handler.postDelayed(mRunnable,10);
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		handler.removeCallbacks(mRunnable);
	}

}
