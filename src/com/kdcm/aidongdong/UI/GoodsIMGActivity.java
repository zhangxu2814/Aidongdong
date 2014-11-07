package com.kdcm.aidongdong.UI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.ImageTool.ImageLoader;

/**
 * 图文详情界面
 * 
 * @author zhangxu
 * 
 */
public class GoodsIMGActivity extends Activity {
	private Intent it;
	private String roll_pics;
	private String desc;
	private TextView tv_desc;
	private String URL_pics[];
	/**
	 * 最上面的土
	 */
	private ImageView img_main;
	/**
	 * 下载图片用的 imageLoader.(URL,img);
	 */
	public ImageLoader imageLoader;
	private ImageView img_1, img_2, img_0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodsimg);

		imageLoader = new ImageLoader(getApplication());
		init();
	}

	private void init() {
		img_0 = (ImageView) findViewById(R.id.img_0);
		img_1 = (ImageView) findViewById(R.id.img_1);
		img_2 = (ImageView) findViewById(R.id.img_2);
		img_main = (ImageView) findViewById(R.id.img_main);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		it = getIntent();
		roll_pics = it.getStringExtra("roll_pics");
		desc = it.getStringExtra("desc");
		// /
		try {
			JSONArray pics_Array = new JSONArray(roll_pics);
			URL_pics = new String[pics_Array.length()];
			for (int i = pics_Array.length() - 1; i >= 0; i--) {
				JSONObject item = pics_Array.getJSONObject(i);
				URL_pics[i] = "http://www.haoapp123.com/app/localuser/aidongdong/"
						+ item.get("pic");
				Log.i("URL", URL_pics[i]);
			}
			imageLoader.DisplayImage(URL_pics[0], img_main);
			imageLoader.DisplayImage(URL_pics[0], img_0);
			imageLoader.DisplayImage(URL_pics[1], img_1);
			imageLoader.DisplayImage(URL_pics[2], img_2);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		tv_desc.setText(desc);
		Log.i("desc", desc);
	}

}
