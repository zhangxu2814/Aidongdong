package com.kdcm.aidongdong.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;

public class RadarActivity extends Activity implements Runnable {
	protected static final int SCAN_LODING = 1;
	protected static final int FINSH_SCAN = 2;
	private ImageView im_scan;
	private ImageView im_dian;
	private TextView tv_count;
	MediaPlayer mpMediaPlayer = new MediaPlayer();
	
	/**
	 * 子线程负责联网
	 */
	private Thread mThread;
	private Thread thread;
	private String URLpath;
	private boolean isRun = true;
	private ListView listview;
	ArrayAdapter<String> simpleAdapter = null;
	List<String> data = new ArrayList<String>();
	int dataSize = 0;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg != null) {
				tv_count.setText(data.size() + "");
				if (dataSize != data.size()) {
					listview.setAdapter(simpleAdapter);
					dataSize = data.size();
				}
			} else {
				tv_count.setText("");
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radar);
		init();
		Media();
		// openScan();

	}

	private void Media() {
		AssetManager am = getAssets();
		try {
			mpMediaPlayer.setDataSource(am.openFd("radar_hold.mp3")
					.getFileDescriptor());
			mpMediaPlayer.prepare();
			mpMediaPlayer.setLooping(true);
			mpMediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init() {
		im_scan = (ImageView) findViewById(R.id.im_scan);
		im_dian = (ImageView) findViewById(R.id.im_dian);
		tv_count = (TextView) findViewById(R.id.tv_count);
		RotateAnimation animation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setDuration(2000);
		animation.setRepeatCount(Animation.INFINITE);
		im_scan.startAnimation(animation);

		AlphaAnimation animation2 = new AlphaAnimation(0.0f, 1.0f);
		animation2.setDuration(3000);
		animation2.setRepeatCount(Animation.INFINITE);
		im_dian.startAnimation(animation2);
		thread = new Thread(this);
		thread.start();
		listview = (ListView) findViewById(R.id.listView1);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				String str=data.get(arg2);
				String s1 = str.substring(str.lastIndexOf("ID")+2);
				Toast.makeText(getApplicationContext(), "验证成功" +s1,
						Toast.LENGTH_SHORT).show();
				final String addURL=Conf.APP_URL+"addFriend&friend_id="+s1;
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						HttpUtil.getJsonContent(addURL);
					}
				}).start();

			}
		});
	}

	@Override
	protected void onPause() {
		mpMediaPlayer.stop();
		isRun = false;

		super.onPause();

	}

	@Override
	public void run() {
		data = null;
		while (isRun) {
			try {
				Thread.sleep(1000);
				URLpath = Conf.APP_URL
						+ "scan&longitude=118.350838&latitude=35.06763";
				String jsonstring = HttpUtil.getJsonContent(URLpath);
				Log.i("lihuanwang", "run" + jsonstring);
				data = JsonTools.getScan(jsonstring);
				simpleAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_expandable_list_item_1, data);
				if (data != null) {
					Message msg = Message.obtain();
					msg.obj = data;
					handler.sendMessage(msg);
				} else {
					handler.sendMessage(null);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
