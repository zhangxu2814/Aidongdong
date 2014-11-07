package com.kdcm.aidongdong.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.TestAvtivity;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.ImageTool.FileCache;
import com.kdcm.aidongdong.ImageTool.LazyAdapter;
import com.kdcm.aidongdong.ImageTool.MemoryCache;
import com.kdcm.aidongdong.tools.GetProductsAdapter;
import com.kdcm.aidongdong.tools.GivedCoinsAdapter;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;
import com.umeng.message.proguard.O;

public class AD_Activity extends Activity {
	private static MemoryCache memoryCache;
	private static FileCache fileCache;
	private String URL_Products;
	private ListView lv_getproducts;
	/**
	 * 子线程负责联网
	 */
	private Thread mThread;
	private String str_json;
	private Handler mHandler;
	ArrayList<HashMap<String, Object>> data = null;
	LazyAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad);

		lv_getproducts = (ListView) findViewById(R.id.lv_getproducts);

		showData();
		lv_getproducts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int i,
					long id) {
				String roll_pics = data.get(i).get("roll_pics").toString();
				String name = data.get(i).get("name").toString();
				String price=data.get(i).get("price").toString();
				String sold_num=data.get(i).get("sold_num").toString();
				String size=data.get(i).get("size").toString();
				String color=data.get(i).get("color").toString();
				String goods_id=data.get(i).get("id").toString();
				String desc=data.get(i).get("desc").toString();

				Intent it = new Intent(getApplicationContext(),
						GoodsMainActivity.class);
				it.putExtra("roll_pics", roll_pics);
				it.putExtra("name", name);
				it.putExtra("price", price);
				it.putExtra("sold_num", sold_num);
				it.putExtra("size", size);
				it.putExtra("color", color);
				it.putExtra("goods_id", goods_id);
				it.putExtra("desc", desc);
				startActivity(it);

			}
		});

	}

	private void showData() {
		if (data == null) {

			try {
				getData();
				Thread.sleep(100);
				showData();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			adapter = new LazyAdapter(this, data);
			lv_getproducts.setAdapter(adapter);
		}
	}

	private void getData() {
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				saveData();
			}
		});

		mThread.start();
	}

	protected void saveData() {
		str_json = HttpUtil
				.getJsonContent(
						this,
						"http://www.haoapp123.com/app/localuser/aidongdong/api.php?m=user&a=getProducts");
		data = JsonTools.getProducts(str_json);
		Log.i("kdcmad", data + "");
	}

}