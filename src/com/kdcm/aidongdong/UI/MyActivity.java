package com.kdcm.aidongdong.UI;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.Person;

public class MyActivity extends Activity implements OnClickListener {
	String username;
	String sex;
	String phone;
	private ListView listview;
	private Button btn_sport;
	private Button btn_friend;
	private Button btn_more;
	private Button btn_change;
	private Intent it;
	// 用户列表userList对应的适配器
	ArrayAdapter<String> simpleAdapter = null;
	private Button btn_f5;
	Person person ;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		init();
	}

	private void init() {
		listview = (ListView) findViewById(R.id.listView1);
		simpleAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData());

		listview.setAdapter(simpleAdapter);
		btn_sport = (Button) findViewById(R.id.btn_sport);
		btn_sport.setOnClickListener(this);
		btn_change = (Button) findViewById(R.id.btn_change);
		btn_change.setOnClickListener(this);
		btn_f5=(Button)findViewById(R.id.btn_f5);
		btn_f5.setOnClickListener(this);
		btn_friend=(Button)findViewById(R.id.btn_friend);
		btn_friend.setOnClickListener(this);
	}

	private List<String> getData() {
		 person = JsonTools.getPerson("data", Conf.jsonstring);
		List<String> data = new ArrayList<String>();
		data.add("昵称" + person.getNickname());
		data.add("性别" + person.getSex());
		data.add("手机号码" + person.getPhone());

		return data;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sport:
			it = new Intent(getApplicationContext(), SportCheckActivity.class);
			startActivity(it);
			break;
		case R.id.btn_change:
			it = new Intent(getApplicationContext(), ChangeActivity.class);
			startActivity(it);
			this.finish();
			break;
		case R.id.btn_f5:
			Log.i("f5	",person.getNickname());
			simpleAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_expandable_list_item_1, getData());
			simpleAdapter.notifyDataSetChanged();
			listview.setAdapter(simpleAdapter);
			break;
		case R.id.btn_friend:
			it = new Intent(getApplicationContext(), FriendActivity.class);
			startActivity(it);
			break;
		default:
			break;
		}
	}

	protected void onResume() {

		super.onResume();
	}

	@Override
	protected void onRestart() {
		
		super.onRestart();
	}
}
