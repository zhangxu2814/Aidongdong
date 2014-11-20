package com.kdcm.aidongdong.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.tools.ActivityTools;

public class MoreActivity extends Activity implements OnClickListener {
	private TextView tv_map;
	private Intent it;
	/**
	 * 企业介绍
	 */
	private TextView tv_qyjs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		init();
	}

	private void init() {
		tv_qyjs=(TextView)findViewById(R.id.tv_qyjs);
		tv_qyjs.setOnClickListener(this);
		tv_map = (TextView) findViewById(R.id.tv_map);
		tv_map.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_map:
			it = new Intent(this, MapActivity.class);
			startActivity(it);
			break;
		case R.id.tv_qyjs:
			ActivityTools.mIntent(this, CompanyActivity.class);
			break;
		default:
			break;
		}
	}

}
