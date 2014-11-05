package com.kdcm.aidongdong.UI;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.GoodsTools.GoodsAdapter;
import com.kdcm.aidongdong.listviewTool.TitleFlowIndicator;
import com.kdcm.aidongdong.listviewTool.ViewFlow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GoodsActivity extends Activity {
	private Intent it;
	private ViewFlow viewFlow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods);
		init();
	}
	private void init() {
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		GoodsAdapter adapter=new GoodsAdapter(this);
		viewFlow.setAdapter(adapter);
		TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
		indicator.setTitleProvider(adapter);
		viewFlow.setFlowIndicator(indicator);
	}

}
