package com.kdcm.aidongdong.UI;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.MyAdapter;
import com.kdcm.aidongdong.tools.Person;

public class FriendActivity extends Activity implements OnClickListener {
	private Button btn_radar,btn_2;
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
//	List<String> data = null;
	private List<Map<String, Object>> data=null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		init();
		showFriends();

	}

	private void showFriends() {
		if (data == null) {
			getFriend();
			showFriends();
		}else{
			Toast.makeText(getApplicationContext(), ""+data,
					Toast.LENGTH_SHORT).show();
//			simpleAdapter = new ArrayAdapter<String>(this,
//					android.R.layout.simple_expandable_list_item_1, data);
			MyAdapter my=new MyAdapter(this, data);

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
		btn_radar = (Button) findViewById(R.id.btn_radar);
		btn_radar.setOnClickListener(this);
		listview = (ListView) findViewById(R.id.lv_f);
		btn_2=(Button)findViewById(R.id.btn_2);
		btn_2.setOnClickListener(this);
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

		default:
			break;
		}

	}
	
	}
