package com.kdcm.aidongdong.UI;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.ImageTool.ImageFileCache;
import com.kdcm.aidongdong.ImageTool.ImageGetFromHttp;
import com.kdcm.aidongdong.ImageTool.ImageMemoryCache;
import com.kdcm.aidongdong.tools.HttpUtil;
import com.kdcm.aidongdong.tools.JsonTools;

public class AD_Activity extends Activity {
	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;
	private ImageView[] img_ad1 = new ImageView[5];
	private Bitmap b;
	private Button btn_ad;
	private String URL_Products;
	private ListView listview;
	/**
	 * 子线程负责联网
	 */
	private Thread mThread;
	private String str_json;
	private List<Map<String, Object>> data = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad);
		init();
		getData();
	}

	private void getData() {
		URL_Products = Conf.APP_URL + "getProducts";
		mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				saveData();
			}
		});
		mThread.start();

	}

	protected void saveData() {
		str_json = HttpUtil.getJsonContent(this, URL_Products);
		String result = JsonTools.getPhone_registered("result", str_json);
		if (result.equals("1")) {
			data = JsonTools.getProducts(str_json);
			Log.i("kdcm", data+"");
		}
	}

	private void init() {
		memoryCache = new ImageMemoryCache(this);
		fileCache = new ImageFileCache();
		img_ad1[0] = (ImageView) findViewById(R.id.img_ad1);
		b = getBitmap("http://d.hiphotos.baidu.com/image/pic/item/8435e5dde71190ef9db93a12cc1b9d16fcfa60b9.jpg");
		btn_ad = (Button) findViewById(R.id.btn_ad);

		btn_ad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				img_ad1[0].setImageBitmap(b);
			}
		});
	}

	public Bitmap getBitmap(String url) {
		// 从内存缓存中获取图片
		Bitmap result = memoryCache.getBitmapFromCache(url);
		if (result == null) {
			// 文件缓存中获取
			result = fileCache.getImage(url);
			if (result == null) {
				// 从网络获取
				result = ImageGetFromHttp.downloadBitmap(url);
				if (result != null) {
					fileCache.saveBitmap(result, url);
					memoryCache.addBitmapToCache(url, result);
				}
			} else {
				// 添加到内存缓存
				memoryCache.addBitmapToCache(url, result);
			}
		}
		return result;
	}
}
