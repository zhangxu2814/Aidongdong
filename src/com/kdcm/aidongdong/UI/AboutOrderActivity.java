package com.kdcm.aidongdong.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AboutOrderActivity extends Activity {
	private Intent it;
	private String id = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		it = getIntent();
		id=it.getStringExtra("id");
	}

}
