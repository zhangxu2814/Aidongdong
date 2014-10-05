package com.kdcm.aidongdong.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kdcm.aidongdong.R;

public class FriendActivity extends Activity implements OnClickListener {
	private Button btn_radar;
	private Intent it;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		btn_radar = (Button) findViewById(R.id.btn_radar);
		btn_radar.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_radar:
			it = new Intent(getApplicationContext(), RadarActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}

	}
}
