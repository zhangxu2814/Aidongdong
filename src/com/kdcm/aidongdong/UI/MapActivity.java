package com.kdcm.aidongdong.UI;

import com.kdcm.aidongdong.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MapActivity extends Activity {
	private WebView wv_map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		wv_map = (WebView) findViewById(R.id.wv_map);
		WebSettings webSettings = wv_map.getSettings();
		webSettings.setJavaScriptEnabled(true);
		wv_map.loadUrl("http://app.kaidechuanmei.com/lihuanwang/map.html");
	}

}
