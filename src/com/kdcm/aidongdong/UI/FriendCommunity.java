package com.kdcm.aidongdong.UI;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class FriendCommunity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("好友排行")
				.setContent(new Intent(this, FriendActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("众筹列表")
				.setContent(new Intent(this, GiveMoneyAvtivity.class)));
	}

}
