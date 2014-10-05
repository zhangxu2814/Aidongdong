package com.kdcm.aidongdong.UI;

import java.util.Timer;
import java.util.TimerTask;
import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import android.app.Activity;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Browser.BookmarkColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SportActivity extends Activity implements SensorListener, OnClickListener {
	private TextView textView;
	TimerTask task1;
	 Timer timer = new Timer();
	 Timer timer1 = new Timer();
	String msg = "";
	private SensorManager mySensorManager;
	private boolean isStart=false;
	private ArcProgressbar ap;
	private Thread mThread;
	private TextView tt_time;
	private int count=Conf.count;
	private int int_time=0;
	private Button btn1;
	 int i=Conf.count*5;
	 TimerTask task = new TimerTask(){  
         public void run() {        	 
        	     	 
        	 //修改界面的相关设置只能在UI线程中执行
        	 runOnUiThread(new Runnable(){  
	             public void run() { 
	            	 if(count!=Conf.count){
	            		 count=Conf.count;
	            		 tt_time.setText("已经运动"+int_time+"秒");
	            		 int_time++;
	            	 }
	            	 
	             }
        	 
         });
        	 }
	 };
	 
	 

	@Override
	public void onClick(View view) {
		msg = "";
		switch (view.getId()) {
		case R.id.btnStart:
			mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
			mySensorManager.registerListener(this,
					SensorManager.SENSOR_ACCELEROMETER,
					SensorManager.SENSOR_DELAY_UI);
			msg = "已经开始计步器.";
			break;
		case R.id.btnReset:
			Conf.count = 0;
			msg = "已经重置计步器.";
			break;
		case R.id.btnStop:
			mySensorManager.unregisterListener(this);
			msg = "已经停止计步器.";
			break;
		case R.id.btn_consume:
			task1 = new TimerTask(){
				
		         public void run() {        	 
		        	     	 
		        	 //修改界面的相关设置只能在UI线程中执行
		        	 runOnUiThread(new Runnable(){  
			             public void run() { 
			            	i--;
			            	
			            	ap.setProgress(i);	
			            	if(i<=0){
			            		task1.cancel();
			            		Conf.count=0;
			            	}
			             }
		        	 
		         });
		        	 }
			 };
			timer1.schedule(task1,10, 10); 
			msg = "燃烧吧小宇宙.";
			break;
		}
		textView.setText(String.valueOf(Conf.count));
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sport);
		btn1=(Button)findViewById(R.id.btn_consume);
		btn1.setOnClickListener(this);
		Button btnStart = (Button) findViewById(R.id.btnStart);
		Button btnReset = (Button) findViewById(R.id.btnReset);
		Button btnStop = (Button) findViewById(R.id.btnStop);
		btnStart.setOnClickListener(this);
		btnReset.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		textView = (TextView) findViewById(R.id.tt_num);
		textView.setText(String.valueOf(Conf.count));
		tt_time=(TextView)findViewById(R.id.tt_time);
		 timer.schedule(task,1000, 1000); 
		mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mySensorManager.registerListener(this,
				SensorManager.SENSOR_ACCELEROMETER,
				SensorManager.SENSOR_DELAY_UI);
		ap=(ArcProgressbar)findViewById(R.id.arcProgressbar);
		
	}

	@Override
	public void onDestroy() {
//		mySensorManager.unregisterListener(this);
//		mySensorManager = null;
		super.onDestroy();
	}

	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(int sensor, float[] values) {
		if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
			analyseData(values);// 调用方法分析数据
		}
	}

	float[] preCoordinate;
	double currentTime = 0, lastTime = 0; // 记录时间
	float WALKING_THRESHOLD = 20;

	// 方法：分析参数进行计算
	public void analyseData(float[] values) {
		// 获取当前时间
		currentTime = System.currentTimeMillis();

		// 每隔200MS 取加速度力和前一个进行比较
		if (currentTime - lastTime > 200) {
			if (preCoordinate == null) {// 还未存过数据
				preCoordinate = new float[3];
				for (int i = 0; i < 3; i++) {
					preCoordinate[i] = values[i];
				}
			} else { // 记录了原始坐标的话，就进行比较
				int angle = calculateAngle(values, preCoordinate);
				if (angle >= WALKING_THRESHOLD) {
					Conf.count++; // 步数增加
					updateData(); // 更新步数
				}
				for (int i = 0; i < 3; i++) {
					preCoordinate[i] = values[i];
				}
			}
			lastTime = currentTime;// 重新计时
		}
	}

	// 方法：计算两个加速度矢量夹角的方法
	public int calculateAngle(float[] newPoints, float[] oldPoints) {
		int angle = 0;
		float vectorProduct = 0; // 向量积
		float newMold = 0; // 新向量的模
		float oldMold = 0; // 旧向量的模
		for (int i = 0; i < 3; i++) {
			vectorProduct += newPoints[i] * oldPoints[i];
			newMold += newPoints[i] * newPoints[i];
			oldMold += oldPoints[i] * oldPoints[i];
		}
		newMold = (float) Math.sqrt(newMold);
		oldMold = (float) Math.sqrt(oldMold);
		// 计算夹角的余弦
		float cosineAngle = (float) (vectorProduct / (newMold * oldMold));
		// 通过余弦值求角度
		float fangle = (float) Math.toDegrees(Math.acos(cosineAngle));
		angle = (int) fangle;
		return angle; // 返回向量的夹角
	}

	public void updateData() {
		textView.setText(String.valueOf(Conf.count));
		 setProgress(Conf.count*5);
		 i=Conf.count*5;
	}
}
