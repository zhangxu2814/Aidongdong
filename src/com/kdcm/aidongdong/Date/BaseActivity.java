package com.kdcm.aidongdong.Date;

import android.app.Activity;
import android.content.Intent;

import com.kdcm.aidongdong.UI.LoginActivity;
import com.kdcm.aidongdong.tools.AppUtil;

public class BaseActivity extends Activity {
	Intent it;
	@Override
	protected void onResume() {
		super.onResume();
		AppUtil.isNetwork(this);
		if(Conf.isLogout){
			
			it=new Intent(getApplicationContext(),LoginActivity.class);
			
//			startActivity(it);
//			finish();
			
		}
	}

	

}
