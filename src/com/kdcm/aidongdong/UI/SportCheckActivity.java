package com.kdcm.aidongdong.UI;

import java.util.Timer;
import java.util.TimerTask;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;

import android.app.Activity;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author zhangxu
 * 
 */

public class SportCheckActivity extends Activity implements SensorListener,
		OnClickListener {
	/**
	 * 消耗按钮
	 */
	private Button btn_consume;
	/**
	 * 开始按钮
	 */
	private Button btn_Start;
	/**
	 * 结束按钮
	 */
	private Button btn_Stop;
	/**
	 * 重置按钮
	 */
	private Button btn_Reset;
	/**
	 * 判断步数用的
	 */
	private int count = Conf.count;
	/**
	 * 用来计时线程
	 */
	private TimerTask timeTask;
	/**
	 * 用来显示时间的textview
	 */
	private TextView tt_time;
	/**
	 * 计时用的
	 */
	private int int_time = 1;
	/**
	 * message
	 */
	private String msg = "";
	/**
	 * 运动检测模块
	 */
	private SensorManager mySensorManager;
	/**
	 * 消耗线程，使环消失
	 */
	private TimerTask task_consume;
	/**
	 * 消耗线程
	 */
	private Timer timer_cinsume=new Timer();
	/**
	 * 能量环消耗线程用
	 */
	private int int_consume = Conf.count * 5;// 暂时用
	/**
	 * 弧形
	 */
	private ArcProgressbar arcProgressbar;
	/**
	 * 显示步数
	 */
	private TextView tt_num;
	/**
	 * 统计时间
	 */
	private Timer timeTimer=new Timer();
	

	@Override
	public void onClick(View v) {
		msg = "";
		switch (v.getId()) {
		case R.id.btn_consume:
			int_consume=Conf.count*5<=280?Conf.count*5:280;
			if(int_consume!=0){
			task_consume = new TimerTask() {

				public void run() {

					// 修改界面的相关设置只能在UI线程中执行
					runOnUiThread(new Runnable() {
						public void run() {
							int_consume--;

							arcProgressbar.setProgress(int_consume);
							if (int_consume <= 0) {
								task_consume.cancel();
								Conf.count = 0;
							}
						}

					});
				}
			};
			timer_cinsume.schedule(task_consume, 10, 10);
			msg = "燃烧吧小宇宙.";}
			else{
				msg = "去跑步吧亲.";
			}
			break;
		case R.id.btnStart:
			mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
			mySensorManager.registerListener(this,
					SensorManager.SENSOR_ACCELEROMETER,
					SensorManager.SENSOR_DELAY_UI);
			msg = "已经开始计步器.";
			break;
		case R.id.btnStop:
			mySensorManager.unregisterListener(this);
			msg = "已经停止计步器.";
			break;
		case R.id.btnReset:
			Conf.count = 0;
			msg = "已经重置计步器.";
			break;
		default:
			break;
		}
		tt_num.setText(String.valueOf(Conf.count));
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sport);
		init();
	}

	private void init() {
		tt_time = (TextView) findViewById(R.id.tt_time);
		btn_consume = (Button) findViewById(R.id.btn_consume);
		btn_consume.setOnClickListener(this);
		btn_Start = (Button) findViewById(R.id.btnStart);
		btn_Start.setOnClickListener(this);
		btn_Stop = (Button) findViewById(R.id.btnStop);
		btn_Stop.setOnClickListener(this);
		btn_Reset = (Button) findViewById(R.id.btnReset);
		btn_Reset.setOnClickListener(this);
		arcProgressbar = (ArcProgressbar) findViewById(R.id.arcProgressbar);
		tt_num = (TextView) findViewById(R.id.tt_num);
		mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		mySensorManager.registerListener(this,
				SensorManager.SENSOR_ACCELEROMETER,
				SensorManager.SENSOR_DELAY_UI);
		timeTask = new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (count != Conf.count) {
							count = Conf.count;
							tt_time.setText("已经运动" + int_time + "秒");
							int_time++;
						}
					}

				});
			}
		};
		timeTimer.schedule(timeTask,1000, 1000); 

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	float[] preCoordinate;
	double currentTime = 0, lastTime = 0; // 记录时间
	float WALKING_THRESHOLD = 20;

	// 方法：分析参数进行计算
	public void analyseData(float[] values) {
		// 获取当前时间
		currentTime = System.currentTimeMillis();

		// 每隔400MS 取加速度力和前一个进行比较
		if (currentTime - lastTime > 400) {
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
		tt_num.setText(String.valueOf(Conf.count));
		arcProgressbar.setProgress(Conf.count * 5);
		int_consume = Conf.count * 5;
	}
}
