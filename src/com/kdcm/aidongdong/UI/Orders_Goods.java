package com.kdcm.aidongdong.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Orders_Goods extends Activity {
	private Intent it;
	private String order_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		it = getIntent();
		order_id = it.getStringExtra("id");
		Log.i("mid", order_id);
	}

}
