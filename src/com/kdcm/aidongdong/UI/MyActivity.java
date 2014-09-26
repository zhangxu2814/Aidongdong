package com.kdcm.aidongdong.UI;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.Person;

public class MyActivity extends Activity {
	String username;
	String sex;
	String phone;
	private ListView listview;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		init();
	}

	private void init() {
		listview=(ListView)findViewById(R.id.listView1);	
		listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));

		
	}

	private List<String> getData() {
		Person person = JsonTools.getPerson("data",
				Conf.jsonstring);
		List<String> data = new ArrayList<String>();
		data.add("昵称"+person.getNickname());
		data.add("性别"+person.getSex());
		data.add("手机号码"+person.getPhone());

		return data;
	}
}
