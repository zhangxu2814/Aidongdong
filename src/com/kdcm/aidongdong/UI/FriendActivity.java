package com.kdcm.aidongdong.UI;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.gui.ContactsPage;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.BaseActivity;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.BadgeView;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.MyAdapter;

public class FriendActivity extends BaseActivity implements OnClickListener {
	private Button btn_radar, btn_2, btn_3;
	private Intent it;
	private ListView listview;
	private Handler mHandler;
	ArrayAdapter<String> simpleAdapter = null;
	/**
	 * 子线程负责联网
	 */
	private Thread mThread;
	private String URLpath;
	private String jsonstring;
	// List<String> data = null;
	private List<Map<String, Object>> data = null;

	/**
	 * 好友模块
	 */
	private ImageView iv_contact;
	/**
	 * 个人中心
	 */
	private ImageView iv_user;
	/**
	 * 运动检测模块
	 */
	private ImageView iv_sport;
	/**
	 * 好友请求
	 */
	private TextView tv_MyRequests;
	/**
	 * 点击button后的提示框
	 */
	private String msg = "确定删除好友吗";
	/**
	 * 添加好友
	 */
	private TextView tv_add;
	/**
	 * 添加好友的dialog
	 */
	AlertDialog dlg_add;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		init();
		showFriends();

	}

	private void showFriends() {
		if (data == null) {
			try {
				getFriend();
				Thread.sleep(100);
				showFriends();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else {
//			Toast.makeText(getApplicationContext(), "" + data,
//					Toast.LENGTH_SHORT).show();
			MyAdapter my = new MyAdapter(this, data, msg);
			my.setBtn_name("删除");
			my.setIsGone(View.VISIBLE);
			my.setURL(Conf.APP_URL + "delFriend&friend_id=");
			listview.setAdapter(my);
		}
	}

	private void getFriend() {

		URLpath = Conf.APP_URL + "getFriends";
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
			saveData();
			}
		});
		mThread.start();

	}

	protected void saveData() {
		jsonstring = HttpUtil.getJsonContent(this,URLpath);
		data = JsonTools.getFriends(jsonstring);		
	}

	private void init() {
		tv_add = (TextView) findViewById(R.id.tv_add);
		tv_add.setOnClickListener(this);
		iv_sport = (ImageView) findViewById(R.id.iv_sport);
		iv_sport.setOnClickListener(this);
		iv_user = (ImageView) findViewById(R.id.iv_user);
		iv_user.setOnClickListener(this);
		iv_contact = (ImageView) findViewById(R.id.iv_contact);
		iv_contact.setImageResource(R.drawable.icon_contact_on);
		btn_radar = (Button) findViewById(R.id.btn_radar);
		btn_radar.setOnClickListener(this);
		BadgeView badge = new BadgeView(this, btn_radar);
		badge.setText("1");
		badge.show();
		listview = (ListView) findViewById(R.id.lv_f);
		btn_2 = (Button) findViewById(R.id.btn_2);
		btn_2.setOnClickListener(this);
		btn_3 = (Button) findViewById(R.id.btn_3);
		btn_3.setOnClickListener(this);
		tv_MyRequests = (TextView) findViewById(R.id.tv_MyRequests);
		tv_MyRequests.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_radar:
			it = new Intent(getApplicationContext(), RadarActivity.class);
			startActivity(it);
			break;
		case R.id.btn_2:
			it = new Intent(getApplicationContext(), StatisticsActivity.class);
			startActivity(it);
			break;
		case R.id.btn_3:
			ContactsPage contactsPage = new ContactsPage();
			contactsPage.setUsername(Conf.username);
			contactsPage.show(this);
			break;
		case R.id.iv_user:
			it = new Intent(this, MyActivity.class);
			startActivity(it);
			break;
		case R.id.iv_sport:
			it = new Intent(this, SportCheckActivity.class);
			startActivity(it);
			break;
		case R.id.tv_MyRequests:
			it = new Intent(this, MyRequestsActivity.class);
			startActivity(it);
			break;
		case R.id.tv_add:
			toAddFriends();
			break;

		}

	}

	private void toAddFriends() {
		dlg_add = new AlertDialog.Builder(this).create();
		dlg_add.show();
		Window window = dlg_add.getWindow();
		window.setContentView(R.layout.dlg_addfriend);
		Button btn_radar = (Button) window.findViewById(R.id.btn_radar);
		btn_radar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				it = new Intent(getApplicationContext(), RadarActivity.class);
				startActivity(it);
				dlg_add.dismiss();
			}
		});
		Button btn_contact = (Button) window.findViewById(R.id.btn_contact);
		btn_contact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ContactsPage contactsPage = new ContactsPage();
				contactsPage.setUsername(Conf.username);
				contactsPage.show(getApplicationContext());
				dlg_add.dismiss();
			}
		});
		Button btn_phone = (Button) window.findViewById(R.id.btn_phone);
		btn_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				it = new Intent(getApplicationContext(), PhoneAddFriends.class);
				startActivity(it);
				dlg_add.dismiss();
			}
		});
	}

	@Override
	protected void onResume() {
		data = null;
		init();
		showFriends();
		super.onResume();
	}

}
