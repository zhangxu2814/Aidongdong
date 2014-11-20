package com.kdcm.aidongdong.UI;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class Activity_Orders extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost
				.newTabSpec("tab1")
				.setIndicator("未付款")
				.setContent(
						new Intent(this, My_Orders.class).putExtra("status",
								"1")));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("待发货")
				.setContent(new Intent(this, My_Orders.class).putExtra("status",
						"2")));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("待收货")
				.setContent(new Intent(this, My_Orders.class).putExtra("status",
						"3")));
		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("已收货")
				.setContent(new Intent(this, My_Orders.class).putExtra("status",
						"4")));

	}

}
