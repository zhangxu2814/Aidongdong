package com.kdcm.aidongdong.UI;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.listviewTool.GoodsReviewAdapter;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;

/**
 * 
 * @author zhangxu 商品评论
 */
public class GoodsReviewActivity extends Activity {
	private String URL_Review = "getProductComments";
	private Intent it;
	private String product_id;
	private String json_str;
	private Thread mThread;
	/**
	 * 评论的长度
	 */
	private String total_count;
	List<Map<String, Object>> data = null;
	private ListView lv_review;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodsreview);
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg != null) {
					ShowData();
				}
			}
		};
		init();

	}

	protected void ShowData() {
		GoodsReviewAdapter adapter = new GoodsReviewAdapter(this, data);
		lv_review.setAdapter(adapter);

	}

	private void getData() {
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				savaData();
			}
		});
		mThread.start();

	}

	private void init() {
		lv_review = (ListView) findViewById(R.id.lv_review);
		it = getIntent();
		product_id = it.getStringExtra("goods_id");
		URL_Review = Conf.APP_URL + URL_Review + "&product_id=" + product_id;
		getData();

	}

	protected void savaData() {
		json_str = HttpUtil.getJsonContent(this, URL_Review);
		total_count = JsonTools.getPhone_registered("total_count", json_str);
		if (!total_count.equals("0")) {
			data = JsonTools.getReview(json_str);
			Message msg = new Message();
			msg.obj = data;
			mHandler.sendMessage(msg);

		}

	}

}
