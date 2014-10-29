package com.kdcm.aidongdong.UI.Money;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class DropBallActivity extends Activity {

	BallView ballView;
	LinearLayout root;
	Display display;
	Timer timer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		display = getWindowManager().getDefaultDisplay();
		ballView = new BallView(this, display.getWidth());
		final String myPid = Process.myPid() + "";
		timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				
				System.exit(0);
				// final Intent it = new Intent(DropBallActivity.this,
				// SportCheckActivity.class);
				// it.putExtra("mMoney",myPid);
				//
				// startActivity(it);
				// DropBallActivity.this.finish();// 执行
				
			}
		};
		setContentView(ballView);
		timer.schedule(task, 3500);
	}

	@Override
	protected void onDestroy() {
		timer.cancel();
		// android.os.Process.killProcess(android.os.Process.myPid());
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		timer.cancel();
		// android.os.Process.killProcess(Process.myPid());
		super.onStop();
	}

	@Override
	protected void onResume() {

		super.onResume();

	}

	@Override
	protected void onPause() {
		timer.cancel();
		// android.os.Process.killProcess(Process.myPid());
		super.onPause();
	}
	@Override    
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	if(keyCode == KeyEvent.KEYCODE_BACK){      
	return  true;
	}  
	return  super.onKeyDown(keyCode, event);     

	} 
}