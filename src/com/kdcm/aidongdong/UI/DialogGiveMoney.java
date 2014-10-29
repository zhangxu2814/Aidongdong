package com.kdcm.aidongdong.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;

public class DialogGiveMoney extends Activity implements OnClickListener {
	/**
	 * 确认
	 */
	private Button btn_ok;
	/**
	 * 取消
	 */
	private Button btn_cancel;
	/**
	 * 金币数量
	 */
	private EditText et_num;
	/**
	 * 赠送金币URL
	 */
	private String URL_GiveMoney;
	private Intent mIntent;
	private String friend_id;
	private String rule = null;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_give);
		init();
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 6208) {
					Toast.makeText(getApplicationContext(), "金币不足",
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 1) {
					Toast.makeText(getApplicationContext(), "赠送成功",
							Toast.LENGTH_SHORT).show();
					DialogGiveMoney.this.finish();
				}
				super.handleMessage(msg);
			}

		};
	}

	private void init() {
		mIntent = this.getIntent();
		friend_id = mIntent.getStringExtra("mID");
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
		et_num = (EditText) findViewById(R.id.et_num);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			if (et_num.getText().length() > 1) {
				toGiveMoey();
			} else {
				Toast.makeText(this, "请输入您要赠送的数量", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_cancel:
			this.finish();
			Toast.makeText(this, "用户取消操作", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}

	}

	private void toGiveMoey() {
		String coins = et_num.getText().toString();
		URL_GiveMoney = Conf.APP_URL + "giveCoins&friend_id=" + friend_id
				+ "&coins=" + coins;
		new Thread(new Runnable() {

			@Override
			public void run() {
				toGetJson();
			}
		}).start();
	}

	protected void toGetJson() {
		String JsonStr = HttpUtil.getJsonContent(this, URL_GiveMoney);

		rule = JsonTools.getPhone_registered("result", JsonStr);
		Message message = new Message();
		message.what = Integer.parseInt(rule);
		mHandler.sendMessage(message);
		message = null;
		Log.i("kdcm", rule + "");
		// if(resu.equals("")){
		// Toast.makeText(this, "充值成功", Toast.LENGTH_SHORT).show();
		// }else{
		// Toast.makeText(this, "充值失败", Toast.LENGTH_SHORT).show();
		// }

	}
}
