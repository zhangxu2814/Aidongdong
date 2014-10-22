package com.kdcm.aidongdong.UI;

import java.util.List;
import java.util.Map;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.BaseActivity;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;
import com.kdcm.aidongdong.tools.MyAdapter;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MyRequestsActivity extends BaseActivity {
	/**
	 * 好友列表
	 */
	private ListView lv_friend;
	/**
	 * URLPATH
	 */
	private String URL_MyRequests;
	/**
	 * Json_MyRequests;
	 */
	private String json_MyRequests = null;
	private List<Map<String, Object>> data = null;
	private String msg="是否添加好友";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myrequests);
		// 如果网络连接就进行下一步
		if (Conf.NETWORK_ON != false) {
			init();
		} else {

		}
	}

	private void init() {
		lv_friend = (ListView) findViewById(R.id.lv_friend);
		URL_MyRequests = Conf.APP_URL + "getMyAccepts";
		new Thread(new Runnable() {

			@Override
			public void run() {
				json_MyRequests = HttpUtil.getJsonContent(URL_MyRequests);
				if (json_MyRequests != null) {
					data = JsonTools.getFriends(json_MyRequests);
				}

			}
		}).start();

		showFriend();
	}

	private void showFriend() {
		if (data != null) {
			MyAdapter my = new MyAdapter(this, data,msg);
			my.setBtn_name("添加");
			my.setURL(Conf.APP_URL+"addFriend&friend_id=");
			lv_friend.setAdapter(my);
		}else if(data==null){
			try {
				Thread.sleep(200);
				init();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			lv_friend.setAdapter(null);
		}		
	}
	@Override
	protected void onResume() {
		data = null;
		showFriend();
		super.onResume();
	}
}
