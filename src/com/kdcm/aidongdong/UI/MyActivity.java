package com.kdcm.aidongdong.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.ActivityTools;
import com.kdcm.aidongdong.tools.DataTools;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.Person;

public class MyActivity extends Activity implements OnClickListener {
	/**
	 * 众筹
	 */
	private TextView tv_community;
	String username;
	String sex;
	String phone;
	private ListView listview;
	private ImageView iv_more;
	private Button btn_change;
	private Intent it;
	// 用户列表userList对应的适配器
	ArrayAdapter<String> simpleAdapter = null;
	private Button btn_f5;
	Person person;
	/**
	 * 运动模块
	 */
	private ImageView iv_sport;
	/**
	 * 通讯录好友模块
	 */
	private ImageView iv_contact;
	/**
	 * 个人中心
	 */
	private ImageView iv_user;
	/**
	 * 个人数据
	 */
	private List<Map<String, Object>> data = null;
	/**
	 * 手机号
	 */
	private TextView tv_phone;
	/**
	 * 昵称
	 */
	private TextView tv_nikename;
	/**
	 * 性别
	 */
	private TextView tv_sex;
	/**
	 * 金币
	 */
	private TextView tv_coins;
	/**
	 * 余额
	 */
	private TextView tv_balance;
	/**
	 * 注销
	 */
	private TextView tv_logout;
	/**
	 * 用户详情
	 */
	private TextView tv_detail;
	/**
	 * 购物车
	 */
	private TextView tv_spcar;
	/**
	 * 订单
	 */
	private TextView tv_orders;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);

		init();
	}

	private void init() {
		iv_more=(ImageView)findViewById(R.id.iv_more);
		iv_more.setOnClickListener(this);
		tv_community=(TextView)findViewById(R.id.tv_community);
		tv_community.setOnClickListener(this);
		tv_orders=(TextView)findViewById(R.id.tv_orders);
		tv_orders.setOnClickListener(this);
		tv_spcar = (TextView) findViewById(R.id.tv_spcar);
		tv_spcar.setOnClickListener(this);
		tv_detail = (TextView) findViewById(R.id.tv_detail);
		tv_detail.setOnClickListener(this);
		tv_logout = (TextView) findViewById(R.id.tv_logout);
		tv_logout.setOnClickListener(this);
		tv_balance = (TextView) findViewById(R.id.tv_balance);
		tv_coins = (TextView) findViewById(R.id.tv_coins);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		tv_nikename = (TextView) findViewById(R.id.tv_nikename);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		iv_user = (ImageView) findViewById(R.id.iv_user);
		iv_user.setImageResource(R.drawable.icon_user_on);
		listview = (ListView) findViewById(R.id.listView1);
		simpleAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData());

		listview.setAdapter(simpleAdapter);
		btn_change = (Button) findViewById(R.id.btn_change);
		btn_change.setOnClickListener(this);
		btn_f5 = (Button) findViewById(R.id.btn_f5);
		btn_f5.setOnClickListener(this);
		iv_sport = (ImageView) findViewById(R.id.iv_sport);
		iv_sport.setOnClickListener(this);
		iv_contact = (ImageView) findViewById(R.id.iv_contact);
		iv_contact.setOnClickListener(this);
	}

	private List<String> getData() {
		person = JsonTools.getPerson("data",
				DataTools.readData(this, "login_message"));

		data = JsonTools.getMy(DataTools.readData(this, "login_message"));
		Log.i(Conf.TAG, data.get(0).get("nickname") + "");
		tv_phone.setText(data.get(0).get("phone").toString());
		tv_nikename.setText(data.get(0).get("nickname").toString());
		String sex = data.get(0).get("sex").toString();
		Log.i(Conf.TAG, sex);
		if (sex.equals("0")) {
			tv_sex.setText("未填写");
		} else if (sex.equals("1")) {
			tv_sex.setText("男");
		} else {
			tv_sex.setText("女");
		}
		String coins = data.get(0).get("coins").toString();
		tv_coins.setText(coins);
		String balance = data.get(0).get("balance").toString();
		tv_balance.setText(balance);
		List<String> data = new ArrayList<String>();
		data.add("昵称" + person.getNickname());
		data.add("性别" + person.getSex());
		data.add("手机号码" + person.getPhone());

		return data;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_community:
			it = new Intent(this, GivedCoinsActivity.class);
			startActivity(it);
			break;
		case R.id.btn_change:
			it = new Intent(getApplicationContext(), ChangeActivity.class);
			startActivity(it);
			this.finish();
			break;
		case R.id.btn_f5:
			Log.i("f5	", person.getNickname());
			simpleAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_expandable_list_item_1, getData());
			simpleAdapter.notifyDataSetChanged();
			listview.setAdapter(simpleAdapter);
			break;
		case R.id.iv_contact:
			it = new Intent(getApplicationContext(), FriendCommunity.class);
			startActivity(it);
			break;
		case R.id.iv_sport:
			it = new Intent(this, SportCheckActivity.class);
			startActivity(it);
			break;
		case R.id.tv_logout:
			// Conf.username = null;
			// Conf.jsonstring = null;
			Conf.isLogout = true;
			it = new Intent(this, LoginActivity.class);
			startActivity(it);
			this.finish();
			break;
		case R.id.tv_detail:
//			it = new Intent(this, GivedCoinsActivity.class);
			it = new Intent(this, ChangeActivity.class);
			startActivity(it);
			break;
		case R.id.tv_spcar:
			it = new Intent(this, SPCarActivity.class);
			startActivity(it);
			break;
		case R.id.tv_orders:
			ActivityTools.mIntent(this,Activity_Orders.class);
			break;
		case R.id.iv_more:
			ActivityTools.mIntent(this, MoreActivity.class);
			break;
		}
	}
	
}
