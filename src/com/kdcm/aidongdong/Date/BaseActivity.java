package com.kdcm.aidongdong.Date;

import com.kdcm.aidongdong.tools.AppUtil;

import android.app.Activity;

public class BaseActivity extends Activity{
	@Override
	protected void onResume() {
		super.onResume();
		AppUtil.isNetwork(this);
	}

}
