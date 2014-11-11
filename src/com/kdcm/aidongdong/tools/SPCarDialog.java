package com.kdcm.aidongdong.tools;

import com.kdcm.aidongdong.Date.Conf;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SPCarDialog extends Activity {
	private ProgressDialog loadingPDialog = null;
	private Handler mHandler;
	private Intent it;
	private String num;
	private String SPCar_URL;
	private Thread mThread;
	String shopping_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		mHandler = new Handler() {
		public void	handleMessage(Message msg){
			try {
				Thread.sleep(1000);
				SPCarDialog.this.finish();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		};
	}

	private void init() {
		loadingPDialog = new ProgressDialog(this);
		loadingPDialog.setMessage("正在加载....");
		loadingPDialog.setCancelable(false);
		loadingPDialog.show();
		it = getIntent();
		num = it.getStringExtra("num");
		shopping_id = it.getStringExtra("shopping_id");
		SPCar_URL = Conf.APP_URL + "modifyShoppingCart&shopping_id=" + shopping_id
				+ "&number=" + num;
		Log.i("SPCar_URL", SPCar_URL);
		mThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				getData();
			}
		});
		mThread.start();
	}

	protected void getData() {
		HttpUtil.getJsonContent(this, SPCar_URL);
		Message msg=new Message();
		mHandler.sendMessage(msg);
		
	}

}
