package com.kdcm.aidongdong.UI;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.frontia.api.FrontiaSocialShare;
import com.baidu.frontia.api.FrontiaSocialShareContent;
import com.baidu.frontia.api.FrontiaSocialShareListener;
import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.UI.Money.DropBallActivity;
import com.umeng.scrshot.UMScrShotController.OnScreenshotListener;
import com.umeng.scrshot.adapter.UMAppAdapter;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;

/**
 * 
 * @author zhangxu
 * 
 */

public class SportCheckActivity extends Activity implements SensorListener,
		OnClickListener {
	 private ImageView mImageView = null;
	/**
	 * 消耗按钮
	 */
	private Button btn_consume;

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
	private Timer timer_cinsume = new Timer();
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
	private Timer timeTimer = new Timer();
	/**
	 * 图标统计图
	 */
	private ImageView iv_chart;
	/**
	 * 分享
	 */
	private ImageView iv_share;
	private Intent it;
	/**
	 * 百度第三方分享
	 */
	private FrontiaSocialShare mSocialShare;
	/**
	 * 百度第三方分享
	 */
	private FrontiaSocialShareContent mImageContent = new FrontiaSocialShareContent();
	/**
	 * 个人中心
	 */
	private ImageView iv_user;
	/**
	 * 运动检测模块
	 */
	private ImageView iv_sport;
	/**
	 * 好友模块
	 */
	private ImageView iv_contact;
	private int time_h = 0;
	private int time_m = 0;
	private ProgressDialog loadingPDialog = null;
	private Handler mHandler;
	String isMoney = "null";
	private LinearLayout ll_ad;
	
	@Override
	public void onClick(View v) {
		msg = "";
		switch (v.getId()) {
		case R.id.ll_ad:
			it=new Intent(this,AD_Activity.class);
			startActivity(it);
			break;
		case R.id.btn_consume:
			int_consume = Conf.count * 5 <= 280 ? Conf.count * 5 : 280;
			if (int_consume != 0) {
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
									btn_consume
											.setOnClickListener(SportCheckActivity.this);
								}
							}

						});
					}
				};
				timer_cinsume.schedule(task_consume, 10, 10);
				Intent mIt = new Intent(this, DropBallActivity.class);
				startActivity(mIt);
				msg = "燃烧吧小宇宙.";
			} else {
				Intent mIt = new Intent(this, DropBallActivity.class);
				startActivity(mIt);
				msg = "去跑步吧亲.";
			}
			break;

		// case R.id.btnStop:
		// mySensorManager.unregisterListener(this);
		// msg = "已经停止计步器.";
		// break;
		case R.id.btnReset:
			Conf.count = 0;
			msg = "已经重置计步器.";
			break;
		case R.id.iv_share:
			ShowShare();
			break;
		case R.id.iv_chart:
			it = new Intent(this, StatisticsActivity.class);
			startActivity(it);
			break;
		case R.id.iv_user:
			it = new Intent(this, MyActivity.class);
			startActivity(it);
			break;
		case R.id.iv_contact:
			it = new Intent(this, FriendActivity.class);
			startActivity(it);
			break;

		}
		tt_num.setText(String.valueOf(Conf.count));
		if (msg.length() > 1) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
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
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_sport);

		init();

	}

	private void init() {
		mImageView=(ImageView)findViewById(R.id.scrshot_imgview);
		ll_ad=(LinearLayout)findViewById(R.id.ll_ad);
		ll_ad.setOnClickListener(this);
		iv_sport = (ImageView) this.findViewById(R.id.iv_sport);
		iv_sport.setImageResource(R.drawable.icon_sport_on);
		iv_user = (ImageView) findViewById(R.id.iv_user);
		iv_user.setOnClickListener(this);
		iv_chart = (ImageView) findViewById(R.id.iv_chart);
		iv_chart.setOnClickListener(this);
		iv_share = (ImageView) findViewById(R.id.iv_share);
		iv_share.setOnClickListener(this);
		tt_time = (TextView) findViewById(R.id.tt_time);
		btn_consume = (Button) findViewById(R.id.btn_consume);
		btn_consume.setOnClickListener(this);
		iv_contact = (ImageView) findViewById(R.id.iv_contact);
		iv_contact.setOnClickListener(this);
		btn_Reset = (Button) findViewById(R.id.btnReset);
		btn_Reset.setOnClickListener(this);
		arcProgressbar = (ArcProgressbar) findViewById(R.id.arcProgressbar);
		tt_num = (TextView) this.findViewById(R.id.tt_num);
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

							tt_time.setText("已经运动" + time_h + "小时" + time_m
									+ "分钟" + int_time + "秒");
							int_time++;
							if (int_time == 60) {
								int_time = 0;
								time_m++;
								{
									if (time_m == 60) {
										time_m = 0;
										time_h++;
									}
								}
							}
						}
					}

				});
			}
		};
		timeTimer.schedule(timeTask, 1000, 1000);

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
		tt_num = (TextView) findViewById(R.id.tt_num);
		tt_num.setText(String.valueOf(Conf.count));
		arcProgressbar.setProgress(Conf.count * 5);
		int_consume = Conf.count * 5;
	}

	private void ShowShare() {
		// mSocialShare = Frontia.getSocialShare();
		// mSocialShare.setContext(this);
		// mSocialShare.setClientId(MediaType.SINAWEIBO.toString(),
		// Conf.SINA_APP_KEY);
		// mSocialShare.setClientId(MediaType.QZONE.toString(), "100358052");
		// mSocialShare.setClientId(MediaType.QQFRIEND.toString(), "100358052");
		// mSocialShare.setClientName(MediaType.QQFRIEND.toString(), "百度");
		// mSocialShare.setClientId(MediaType.WEIXIN.toString(),
		// "wx329c742cb69b41b8");
		// mImageContent.setTitle("百度开发中心");
		// mImageContent.setContent("欢迎使用百度社会化分享组件，相关问题请邮件dev_support@baidu.com");
		// mImageContent.setLinkUrl("http://developer.baidu.com/");
		// mImageContent
		// .setImageUri(Uri
		// .parse("http://apps.bdimg.com/developer/static/04171450/developer/images/icon/terminal_adapter.png"));
		// mSocialShare.show(this.getWindow().getDecorView(), mImageContent,
		// FrontiaTheme.DARK, new ShareListener());
		final UMSocialService mController = UMServiceFactory
				.getUMSocialService("com.umeng.share");
//		// 设置分享内容
//		mController
//				.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
		// 设置分享图片, 参数2为图片的url地址
		UMAppAdapter mp=new UMAppAdapter(this);
		mController.setShareMedia(new UMImage(SportCheckActivity.this,
				mp.getBitmap()));
		
		String appID = "wxb63a8a59702e5ddb";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(SportCheckActivity.this, appID);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(SportCheckActivity.this,
				appID);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		mController.openShare(SportCheckActivity.this, false);
	}

	private class ShareListener implements FrontiaSocialShareListener {

		@Override
		public void onSuccess() {
			Log.d("Test", "share success");
		}

		@Override
		public void onFailure(int errCode, String errMsg) {
			Log.d("Test", "share errCode " + errCode);
		}

		@Override
		public void onCancel() {
			Log.d("Test", "cancel ");
		}

	}

	@Override
	public void onBackPressed() {
		this.finish();
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		Intent intenet = getIntent();
		isMoney = intenet.getStringExtra("mMoney");
		Log.i("kdcm", isMoney+"");
		super.onResume();

	}
	private OnScreenshotListener mScreenshotListener = new OnScreenshotListener() {

        @Override
        public void onComplete(Bitmap bmp) {
            if (bmp != null && mImageView != null) {
                mImageView.setImageBitmap(bmp);
            }
        }
    };
}
