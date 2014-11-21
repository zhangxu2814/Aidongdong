package com.kdcm.aidongdong.UI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.tools.HomeColumnar;
import com.kdcm.aidongdong.tools.Score;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

public class StatisticsActivity extends Activity implements OnClickListener {
	private ImageView iv_sport, iv_user, iv_more, iv_contact;
	private LinearLayout ll_ad;
	private Button btn_back;
	RelativeLayout pillars, linear;
	private TextView tv_coins;
	private Intent it;
	private String total_count;
	private TextView tv_day;
	List<Score> list = new ArrayList<Score>();
	List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		pillars = (RelativeLayout) findViewById(R.id.pillars);
		init();
	}

	private void init() {
		iv_contact=(ImageView)findViewById(R.id.iv_contact);
		iv_contact.setOnClickListener(this);
		iv_more=(ImageView)findViewById(R.id.iv_more);
		iv_more.setOnClickListener(this);
		iv_user = (ImageView) findViewById(R.id.iv_user);
		iv_user.setOnClickListener(this);
		iv_sport = (ImageView) findViewById(R.id.iv_sport);
		iv_sport.setOnClickListener(this);
		ll_ad = (LinearLayout) findViewById(R.id.ll_ad);
		ll_ad.setOnClickListener(this);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		it = getIntent();
		tv_day = (TextView) findViewById(R.id.tv_day);
		total_count = it.getStringExtra("total_count");
		tv_day.setText(total_count + "天");
		tv_coins = (TextView) findViewById(R.id.tv_coins);
		tv_coins.setText(LoginActivity.coins);
		HttpUtils.getMonthMoveDays(res, 6);
	}

	public int getRandom(int min, int max) {
		return (int) Math.round(Math.random() * (max - min) + min);
	}

	JsonHttpResponseHandler res = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			super.onSuccess(statusCode, headers, response);
			int result = 0;
			try {
				result = Integer.valueOf(response.getString("result"));

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (statusCode == 200 & result == 1) {
				Log.i("response", response + "");
				try {
					JSONArray array = response.getJSONArray("list");
					for (int i = 0; i < array.length(); i++) {
						Map<String, Object> listItem = new HashMap<String, Object>();
						listItem.put("date",
								array.getJSONObject(i).getString("date"));
						listItem.put("duration", array.getJSONObject(i)
								.getString("duration"));
						listItems.add(listItem);
					}
					Log.i("listItems", listItems + "");
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				show();
			}
		}
	};

	protected void show() {
		Log.i("show", listItems + "");

		for (int i = 6; i >= 0; i--) {
			String startTime;
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.add(Calendar.DAY_OF_MONTH, -i);
			startTime = (new SimpleDateFormat("yyyy-MM-dd")).format(calendar
					.getTime());
			Score s = new Score();
			s.date = startTime;
			for (int p = 0; p < listItems.size(); p++) {
				if (startTime.equals(listItems.get(p).get("date").toString())) {
					Log.i("listItems", "数据相等");
					s.score = Integer.valueOf(listItems.get(p).get("duration")
							.toString());
				} else {
					s.score = 0;
				}
				list.add(s);
			}
			if (listItems.size() == 0) {
				s.score = 0;
				list.add(s);
			}

		}
		pillars.addView(new HomeColumnar(this, list));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.ll_ad:
			it = new Intent(this, AD_Activity.class);
			startActivity(it);
			this.finish();
			break;
		case R.id.iv_sport:
			this.finish();
			break;
		case R.id.iv_user:
			it = new Intent(this, MyActivity.class);
			startActivity(it);
			this.finish();
			break;
		case R.id.iv_more:
			it = new Intent(this, MoreActivity.class);
			startActivity(it);
			this.finish();
			break;
		case R.id.iv_contact:
			it = new Intent(this, FriendActivity.class);
			startActivity(it);
			this.finish();
			break;
		default:
			break;
		}
	}
}
