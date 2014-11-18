package com.kdcm.aidongdong.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;
import com.kdcm.aidongdong.GoodsTools.GoodsAdapter;
import com.kdcm.aidongdong.ImageTool.ImageLoader;
import com.kdcm.aidongdong.RadioGroupTool.SegmentedRadioGroup;
import com.kdcm.aidongdong.listviewTool.TitleFlowIndicator;
import com.kdcm.aidongdong.listviewTool.ViewFlow;
import com.kdcm.aidongdong.tools.HttpUtil;

/**
 * 
 * @author zhangxu Button 组设定的ID，小于10的是goods_color，大于11是goods_size；
 */

public class GoodsActivity extends Activity implements OnClickListener {
	/**
	 * 加入购物车备用
	 */
	private String goods_id, goods_color = null, goods_size = null;
	private ViewPager adViewPager;
	private LinearLayout pagerLayout;
	private List<View> pageViews;
	private ImageView[] imageViews;
	private ImageView imageView;
	private AdPageAdapter adapter;
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private boolean isContinue = true;
	public ImageLoader imageLoader;
	private Intent it;
	private TextView tv_name;
	private TextView tv_sold_num;
	private TextView tv_price;
	RadioButton btn_color[], btn_size[];
	ImageView[] img;
	/**
	 * 抵扣额
	 */
	private TextView tv_deduction;
	private SegmentedRadioGroup group_color, group_size;
	/**
	 * 加入购物车按钮
	 */
	private Button btn_SPcart;

	/**
	 * 联网线程
	 */
	private Thread Goods_Thread;
	private String URL_AddSPCar;
	private ProgressDialog loadingPDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_baobeixiangqing);
		imageLoader = new ImageLoader(getApplication());
		group_color = (SegmentedRadioGroup) findViewById(R.id.segment_color);
		group_size = (SegmentedRadioGroup) findViewById(R.id.segment_size);
		loadingPDialog = new ProgressDialog(this);
		loadingPDialog.setMessage("正在加载....");
		loadingPDialog.setCancelable(false);
		initViewPager();
	}

	private void initViewPager() {
		tv_deduction=(TextView)findViewById(R.id.tv_deduction);
		btn_SPcart = (Button) findViewById(R.id.btn_SPcart);
		btn_SPcart.setOnClickListener(this);
		tv_sold_num = (TextView) findViewById(R.id.tv_sold_num);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_price = (TextView) findViewById(R.id.tv_price);
		// 从布局文件中获取ViewPager父容器
		pagerLayout = (LinearLayout) findViewById(R.id.view_pager_content);
		// 创建ViewPager
		adViewPager = new ViewPager(this);

		// 获取屏幕像素相关信息
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		// 根据屏幕信息设置ViewPager广告容器的宽高
		adViewPager.setLayoutParams(new LayoutParams(dm.widthPixels,
				dm.heightPixels * 2 / 5));

		// 将ViewPager容器设置到布局文件父容器中
		pagerLayout.addView(adViewPager);

		initPageAdapter();

		initCirclePoint();

		adViewPager.setAdapter(adapter);
		adViewPager.setOnPageChangeListener(new AdPageChangeListener());

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (isContinue) {
						viewHandler.sendEmptyMessage(atomicInteger.get());
						atomicOption();

					}
				}
			}
		}).start();
	}

	private void atomicOption() {
		atomicInteger.incrementAndGet();
		if (atomicInteger.get() > imageViews.length - 1) {
			atomicInteger.getAndAdd(-5);
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

		}
	}

	/*
	 * 每隔固定时间切换广告栏图片
	 */
	private final Handler viewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			adViewPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
			loadingPDialog.dismiss();

		}

	};

	private void initPageAdapter() {
		pageViews = new ArrayList<View>();

		it = getIntent();
		goods_id = it.getStringExtra("goods_id");
		String roll_pics = it.getStringExtra("roll_pics");
		String max_deduction = it.getStringExtra("max_deduction");
		Log.i("max_deduction", max_deduction);
		tv_deduction.setText("可抵扣额: "+max_deduction);
		String name = it.getStringExtra("name");
		String price = it.getStringExtra("price");
		String sold_num = it.getStringExtra("sold_num");
		String size = it.getStringExtra("size");
		String mSize[] = size.split(",");
		Log.i("size", mSize.length + size);
		String color = it.getStringExtra("color");
		String mColor[] = color.split(",");
		btn_size = new RadioButton[mSize.length];
		for (int i = 0; i < mSize.length; i++) {
			btn_size[i] = (RadioButton) LayoutInflater.from(this).inflate(
					R.drawable.rb, null);
			btn_size[i].setText(mSize[i]);
			btn_size[i].setOnClickListener(this);
			btn_size[i].setTag(mSize[i]);
			btn_size[i].setId((i + 1) * 10 + 1);
			group_size.addView(btn_size[i]);
		}
		group_size.onRef();
		btn_color = new RadioButton[mColor.length];
		for (int i = 0; i < mColor.length; i++) {
			btn_color[i] = (RadioButton) LayoutInflater.from(this).inflate(
					R.drawable.rb, null);
			btn_color[i].setText(mColor[i]);
			btn_color[i].setId(i + 2);
			btn_color[i].setOnClickListener(this);
			btn_color[i].setTag(mColor[i]);
			group_color.addView(btn_color[i]);
		}
		group_color.onRef();
		tv_price.setText("价格：" + price);
		tv_sold_num.setText("销量：" + sold_num);
		tv_name.setText(name);
		String[] URL = new String[3];
		img = new ImageView[3];
		try {
			JSONArray pics_Array = new JSONArray(roll_pics);
			for (int i = pics_Array.length() - 1; i >= 0; i--) {
				JSONObject item = pics_Array.getJSONObject(i);
				URL[i] = "http://www.haoapp123.com/app/localuser/aidongdong/"
						+ item.get("pic");
				img[i] = new ImageView(this);
				img[i].setScaleType(ScaleType.FIT_XY);
				imageLoader.DisplayImage(URL[i], img[i]);
				pageViews.add(img[i]);
				Log.i("URL", URL[i]);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		adapter = new AdPageAdapter(pageViews);
	}

	private void initCirclePoint() {
		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
		imageViews = new ImageView[pageViews.size()];
		// 广告栏的小圆点图标
		for (int i = 0; i < pageViews.size(); i++) {
			// 创建一个ImageView, 并设置宽高. 将该对象放入到数组中
			imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(10, 10));
			imageViews[i] = imageView;

			// 初始值, 默认第0个选中
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.drawable.point_focused);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.point_unfocused);
			}
			// 将小圆点放入到布局中
			group.addView(imageViews[i]);
		}
	}

	/**
	 * ViewPager 页面改变监听器
	 */
	private final class AdPageChangeListener implements OnPageChangeListener {

		/**
		 * 页面滚动状态发生改变的时候触发
		 */
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		/**
		 * 页面滚动的时候触发
		 */
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		/**
		 * 页面选中的时候触发
		 */
		@Override
		public void onPageSelected(int arg0) {
			// 获取当前显示的页面是哪个页面
			atomicInteger.getAndSet(arg0);
			// 重新设置原点布局集合
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.point_focused);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.point_unfocused);
				}
			}
		}
	}

	private final class AdPageAdapter extends PagerAdapter {
		private List<View> views = null;

		/**
		 * 初始化数据源, 即View数组
		 */
		public AdPageAdapter(List<View> views) {
			this.views = views;
		}

		/**
		 * 从ViewPager中删除集合中对应索引的View对象
		 */
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(views.get(position));
		}

		/**
		 * 获取ViewPager的个数
		 */
		@Override
		public int getCount() {
			return views.size();
		}

		/**
		 * 从View集合中获取对应索引的元素, 并添加到ViewPager中
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(views.get(position), 0);
			return views.get(position);
		}

		/**
		 * 是否将显示的ViewPager页面与instantiateItem返回的对象进行关联 这个方法是必须实现的
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btn_SPcart:
			if (goods_color != null && goods_size != null) {
				loadingPDialog.show();
				Toast.makeText(getApplicationContext(), "加入购物车成功",
						Toast.LENGTH_SHORT).show();
				addShoppingCart();
			} else {
				Toast.makeText(this,
						"颜色或大小没有选择" + goods_id + goods_color + goods_size,
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}

		if (id < 10) {
			goods_color = v.getTag().toString();

		} else if (id > 10 && id < 100) {
			goods_size = v.getTag().toString();
			Log.i("mID", v.getTag().toString());
		}

	}

	private void addShoppingCart() {
		URL_AddSPCar = Conf.APP_URL + "addShoppingCart&product_id=" + goods_id
				+ "&number=1" + "&comment=" + goods_color + goods_size;
		Goods_Thread = new Thread(new Runnable() {

			@Override
			public void run() {
				toGetJson();

			}
		});
		Goods_Thread.start();
	}

	protected void toGetJson() {

		String str_json = HttpUtil.getJsonContent(this, URL_AddSPCar);
		String str_Result = HttpUtil.getResult(str_json);
		Log.i("str_Result", str_Result);
		if (str_Result.equals("1")) {
			Message message = new Message();
			message.what = 0x11;
			viewHandler.sendMessage(message);
		}

	}
}
