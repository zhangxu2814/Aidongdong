package com.kdcm.aidongdong.UI;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Toast.makeText(getApplicationContext(), "" + data,
					Toast.LENGTH_SHORT).show();
			MyAdapter my = new MyAdapter(this, data);
			my.setBtn_name("删除");
			listview.setAdapter(my);
		}
	}

	private void getFriend() {

		URLpath = Conf.APP_URL + "getFriends";
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				jsonstring = HttpUtil.getJsonContent(URLpath);
				data = JsonTools.getFriends(jsonstring);

			}
		});
		mThread.start();

	}

	private void init() {
		iv_sport=(ImageView)findViewById(R.id.iv_sport);
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
			it=new Intent(this, MyActivity.class);
			startActivity(it);
			break;
		case R.id.iv_sport:
			it=new Intent(this, SportCheckActivity.class);
			startActivity(it);
			break;
	 
		}

	}

	@Override
	protected void onResume() {
		data = null;
		showFriends();
		super.onResume();
	}

}
