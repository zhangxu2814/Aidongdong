package com.kdcm.aidongdong.UI;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
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
import com.kdcm.aidongdong.tools.ActivityTools;
import com.kdcm.aidongdong.tools.CloseActivityClass;
import com.kdcm.aidongdong.tools.DataTools;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.umeng.scrshot.UMScrShotController.OnScreenshotListener;
import com.umeng.scrshot.adapter.UMAppAdapter;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

/**
 * 
 * @author zhangxu
 * 
 */

public class SportCheckActivity extends Activity implements SensorListener,
		OnClickListener {
	/**
	 * 消耗用的圆点
	 */
	private ImageView iv_consume;
	private int end_time = 0;
	private int star_time = 0;
	private ImageView mImageView = null;
	private TextView tv_coins;
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
	/**
	 * 更多
	 */
	private ImageView iv_more;
	private int time_m = 0;
	private ProgressDialog loadingPDialog = null;
	private Handler mHandler;
	String isMoney = "null";
	private LinearLayout ll_ad;
	private TextView tv_day;
	private int total_count=0;
	private TextView tv_cal;

	@Override
	public void onClick(View v) {
		msg = "";
		switch (v.getId()) {
		case R.id.ll_ad:
			it = new Intent(this, AD_Activity.class);
			startActivity(it);
			break;
		case R.id.iv_consume:
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
			it.putExtra("total_count", total_count+"");
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
		case R.id.iv_more:
			ActivityTools.mIntent(this, MoreActivity.class);
			break;
		}
		tt_num.setText(String.valueOf(Conf.count) + "歩");
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
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
		wl.acquire();
		wl.release();
		CloseActivityClass.activityList.add(this);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_sport);
		Log.i("mType", LoginActivity.coins);
		init();

	}

	private void init() {
		tv_cal=(TextView)findViewById(R.id.tv_cal);
		tv_day=(TextView)findViewById(R.id.tv_day);
		HttpUtils.getMonthMoveDays(res_getMMD, 31);
		RotateAnimation animation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setDuration(1000);
		animation.setRepeatCount(Animation.INFINITE);
		iv_consume = (ImageView) findViewById(R.id.iv_consume);
		iv_consume.setOnClickListener(this);
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(this);
		// iv_consume.startAnimation(animation);
		tv_coins = (TextView) findViewById(R.id.tv_coins);
		tv_coins.setText(LoginActivity.coins);
		mImageView = (ImageView) findViewById(R.id.scrshot_imgview);
		ll_ad = (LinearLayout) findViewById(R.id.ll_ad);
		ll_ad.setOnClickListener(this);
		iv_sport = (ImageView) this.findViewById(R.id.iv_sport);
		iv_sport.setImageResource(R.drawable.icon_sport_on);
		iv_user = (ImageView) findViewById(R.id.iv_user);
		iv_user.setOnClickListener(this);
		iv_chart = (ImageView) findViewById(R.id.iv_chart);
		iv_chart.setOnClickListener(this);
		iv_share = (ImageView) findViewById(R.id.iv_share);
		iv_share.setOnClickListener(this);
		tt_time = (TextView) findViewById(R.id.tv_time);

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
						if (end_time - star_time > 180) {
							// System.exit(0);
						}
						if (count != Conf.count) {
							count = Conf.count;
							tv_cal.setText(time_m+"千卡");
							tt_time.setText(time_m + "m" + int_time % 60 + "s");
							star_time = end_time;
							int_time++;
							if (int_time % 60 == 0) {
								time_m++;
							}
						} else {
							tv_cal.setText(time_m+"千卡");
							tt_time.setText(time_m + "m" + int_time % 60 + "s");
							end_time++;
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
		if (currentTime - lastTime > 500) {
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
		tt_num.setText(String.valueOf(Conf.count) + "歩");
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
		// // 设置分享内容
		// mController
		// .setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
		// 设置分享图片, 参数2为图片的url地址
		UMAppAdapter mp = new UMAppAdapter(this);
		mController.setShareMedia(new UMImage(SportCheckActivity.this, mp
				.getBitmap()));

		String appID = "wxb63a8a59702e5ddb";
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(
				SportCheckActivity.this, "1103374241", "ODe0qJKSqfWItJph");
		qqSsoHandler.addToSocialSDK();

		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(SportCheckActivity.this, appID);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(SportCheckActivity.this,
				appID);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT);
		mController.getConfig().removePlatform(SHARE_MEDIA.QZONE);
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this)
					.setTitle("确认退出？")
					.setMessage("缺认要退出吗？")
					.setNegativeButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									CloseActivityClass
											.exitClient(SportCheckActivity.this);
									// 再杀
									SportCheckActivity.this.finish();
									// 杀杀杀
									System.exit(0);
								}
							}).setPositiveButton("否", null).show();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	protected void onResume() {
		String coins = DataTools.readData(this, "coins");
		tt_num.setText(Conf.count + "歩");
		tv_coins.setText(coins);
		String str_time = DataTools.readData(this, "time_sport");
		if (str_time != null) {
			tt_time.setText(str_time);
			int_time = Integer.valueOf(str_time);
			tt_time.setText((int) (int_time / 60) + "m" + int_time % 60 + "s");

		}
		DataTools.readData(this, "time_sport");
		super.onResume();

	}

	@Override
	protected void onPause() {
		DataTools.saveDaTa(this, "time_sport", int_time + "");
		super.onPause();
	}

	private OnScreenshotListener mScreenshotListener = new OnScreenshotListener() {

		@Override
		public void onComplete(Bitmap bmp) {
			if (bmp != null && mImageView != null) {
				mImageView.setImageBitmap(bmp);
			}
		}
	};
	JsonHttpResponseHandler res_getMMD = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			super.onSuccess(statusCode, headers, response);
			int result = 0;
			try {
				result = Integer.valueOf(response.getString("result"));
				total_count=Integer.valueOf(response.getString("total_count"));
				tv_day.setText(total_count+"天");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (statusCode == 200 & result == 1) {
				Toast.makeText(getApplicationContext(), "添加购物车成功",
						Toast.LENGTH_SHORT).show();
				
			} else {
				Toast.makeText(getApplicationContext(), "请到检查库存是否充足",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
}
