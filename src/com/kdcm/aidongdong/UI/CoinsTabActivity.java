package com.kdcm.aidongdong.UI;

import android.app.ActionBar.Tab;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * 赠送和接受的用第三方显示不完全
 * 
 * @author zhangxu
 * 
 */
public class CoinsTabActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("赠送的金币")
				.setContent(new Intent(this, GivedCoinActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("接收的金币")
				.setContent(new Intent(this, getRecievedCoinsActivity.class)));
	}

}
