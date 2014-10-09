package com.kdcm.aidongdong.UI;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.tools.HomeColumnar;
import com.kdcm.aidongdong.tools.Score;


public class StatisticsActivity extends Activity{
	 RelativeLayout pillars,linear;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		List<Score> list = new ArrayList<Score>();;//柱状图  范围10-100
		for (int i = 0; i < 28; i++) {
			Score s = new Score();
			s.date = "2013-11-" + i;
			s.score = getRandom(10,200);
			list.add(s);
		}
		pillars= (RelativeLayout) findViewById(R.id.pillars);
		pillars.addView(new HomeColumnar(this,list));
	}
	public int getRandom(int min,int max){
		return (int) Math.round(Math.random()*(max-min)+min);
	}
}
