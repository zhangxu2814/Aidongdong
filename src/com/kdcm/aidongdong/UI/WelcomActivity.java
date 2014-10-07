package com.kdcm.aidongdong.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.smssdk.SMSSDK;

import com.baidu.frontia.Frontia;
import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.BaseActivity;
import com.kdcm.aidongdong.Date.Conf;

public class WelcomActivity extends BaseActivity {
	private Button btn_go;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		SMSSDK.initSDK(this, Conf.SMSKEY, Conf.SMSSecret);
		Frontia.init(this.getApplicationContext(), Conf.APIKEY);
		init();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("有新版本是否更新")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Uri uri = Uri.parse("http://baidu.com");
								Intent it = new Intent(Intent.ACTION_VIEW, uri);
								startActivity(it);
								WelcomActivity.this.finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
		AlertDialog alert = builder.create();

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

	private void init() {
		btn_go = (Button) findViewById(R.id.btn_go);

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
	}

}
